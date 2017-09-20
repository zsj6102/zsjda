package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.FindGoodsId;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.SelectFees;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.colpencil.propertycloud.View.Activitys.Home.PayFeesActivity;
import com.colpencil.propertycloud.View.Activitys.Home.PaySureActivity;
import com.colpencil.propertycloud.View.Adapter.MyExpandableListViewAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.colpencil.propertycloud.R.id.iv_cat;
import static com.colpencil.propertycloud.R.id.ll_search;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_payfees_select
)
public class PayFeesSelectActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.lv_select)
    ExpandableListView lv_select;

    @Bind(R.id.tv_comit)
    TextView tv_comit;

    @Bind(R.id.ll_show)
    LinearLayout ll_show;

    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;

    @Bind(R.id.rlEmpty)
    RelativeLayout rlEmpty;

    @Bind(R.id.rlError)
    RelativeLayout rlError;

    @Bind(R.id.btnReloadEmpty)
    Button btnReloadEmpty;

    @Bind(R.id.btnReload)
    Button btnReload;

    private MyExpandableListViewAdapter adapter;
    private Intent intent;

    private List<SelectFees> selectFees = new ArrayList<>();

    private List<String> checkList = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        ll_show.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        tv_title.setText("物业缴费");
        tv_rigth.setText("分类查看");
        tv_rigth.setVisibility(View.VISIBLE);
        lv_select.setIndicatorBounds(5, 25);
        lv_select.setDivider(null);
        lv_select.setGroupIndicator(null);
        adapter = new MyExpandableListViewAdapter(this, selectFees);
        lv_select.setAdapter(adapter);
        lv_select.setItemsCanFocus(true);
        lv_select.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        tv_comit.setOnClickListener(this);
        ll_rigth.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        getFees();
        lv_select.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });
    }

    public void onButtonClick() {
        if (adapter.getCheckSet().size()!=0){
            for (String s:adapter.getCheckSet()){
                checkList.add(s);
            }
            intent = new Intent(this, PaySureActivity.class);
            intent.putExtra("billIds", TextStringUtils.listToString(checkList,','));
            startActivity(intent);
            ColpencilLogger.e("billIds="+TextStringUtils.listToString(checkList,','));
        }else {
            ToastTools.showShort(this,"请选择要缴的费用！");
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    private void getFees() {
        HashMap<String, String> params = new HashMap<>();
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .payFees(params)
                .map(new Func1<ResultListInfo<SelectFees>, ResultListInfo<SelectFees>>() {
                    @Override
                    public ResultListInfo<SelectFees> call(ResultListInfo<SelectFees> findGoodsIdResultListInfo) {
                        return findGoodsIdResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<SelectFees>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final ResultListInfo<SelectFees> findGoodsIdResultListInfo) {
                        int code = findGoodsIdResultListInfo.code;
                        String message = findGoodsIdResultListInfo.message;
                        switch (code) {
                            case 0:
                                selectFees.addAll(findGoodsIdResultListInfo.data);
                                ll_show.setVisibility(View.VISIBLE);
                                rlProgress.setVisibility(View.GONE);
                                rlEmpty.setVisibility(View.GONE);
                                adapter.notifyDataSetChanged();
                                if (findGoodsIdResultListInfo.data.size() != 0) {
                                    for (int i = 0; i < adapter.getGroupCount(); i++) {
                                        lv_select.expandGroup(i);
                                    }
                                }
                                break;
                            case 2:
                                ll_show.setVisibility(View.GONE);
                                rlProgress.setVisibility(View.GONE);
                                rlEmpty.setVisibility(View.VISIBLE);
                                btnReload.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getFees();
                                    }
                                });
                                break;
                            case 1:
                                ll_show.setVisibility(View.GONE);
                                rlProgress.setVisibility(View.VISIBLE);
                                rlEmpty.setVisibility(View.GONE);
                                btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getFees();
                                    }
                                });
                                ToastTools.showShort(PayFeesSelectActivity.this, false, message);
                                break;
                            case 3:
                                break;
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_rigth:
                intent = new Intent(this, PayFeesActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_comit:
                onButtonClick();
                break;
        }
    }
}