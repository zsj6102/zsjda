package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Bean.orm.RegsitForm;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.CloseManager.MenberManagerActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.RegsitFormAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 装修人员登记列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/14
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_registylist
)
public class RegistryFormListActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.regsit_list)
    ListView regsit_list;

    @Bind(R.id.tv_ok)
    TextView tv_ok;

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

    private List<RegsitForm> regsitFormList = new ArrayList<>();
    private RegsitFormAdapter adapter;
    private int approveid;
    private Intent intent;
    private Observable<RxBusMsg> register;
    private RxBusMsg rxBusMsg;

    private boolean isClickOnce = true;
    private int type;// 0  新增   1 ： 修改

    private boolean isClick = true;
    private int houseId;

    @Override
    protected void initViews(View view) {
        approveid = getIntent().getIntExtra("approveid", 0);
        houseId = getIntent().getIntExtra("houseId", 0);
        type = getIntent().getIntExtra("type", 0);
        regsit_list.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        tv_rigth.setVisibility(View.VISIBLE);
        tv_rigth.setText("删除");
        tv_title.setText("装修人员登记表");
        ll_left.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        ll_rigth.setOnClickListener(this);

        adapter = new RegsitFormAdapter(this,regsitFormList);
        regsit_list.setAdapter(adapter);
        getInfo();

        register = RxBus.get().register("del", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                switch (rxBusMsg.getType()){
                    case 0:
                        approveid = rxBusMsg.getId();
                        getInfo();
                        rxBusMsg = new RxBusMsg();
                        rxBusMsg.setType(0);
                        RxBus.get().post("mater", rxBusMsg);
                        RxBusMsg rxBusMsg1 = new RxBusMsg();
                        rxBusMsg1.setMsg("add");
                        rxBusMsg1.setType(3);
                        rxBusMsg1.setId(approveid);
                        RxBus.get().post("MaterialManagement", rxBusMsg1);
                        break;
                    case 1:
                        regsitFormList.clear();
                        getInfo();
                        break;
                }
            }
        };
        register.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_ok:
                intent = new Intent(this, AddRegistryFormActivity.class);
                intent.putExtra("approveid",approveid);
                intent.putExtra("houseId",houseId);
                startActivity(intent);
                break;
            case R.id.ll_rigth:
                if (isClickOnce){
                    for (int i=0;i<regsitFormList.size();i++){
                        regsitFormList.get(i).type = true;
                    }
                    tv_rigth.setText("完成");
                    isClickOnce = false;
                }else {
                    for (int i=0;i<regsitFormList.size();i++){
                        regsitFormList.get(i).type = false;
                    }
                    tv_rigth.setText("删除");
                    isClickOnce = true;
                }

                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("del",register);
    }

    /**
     * 获取人员列表
     */
    private void getInfo(){
        HashMap<String,String> map = new HashMap<>();
        map.put("approveid",approveid+"");
        RetrofitManager.getInstance(1,this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .queryPersonInfo(map)
                .map(new Func1<ResultListInfo<RegsitForm>, ResultListInfo<RegsitForm>>() {
                    @Override
                    public ResultListInfo<RegsitForm> call(ResultListInfo<RegsitForm> regsitFormResultListInfo) {
                        return regsitFormResultListInfo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<RegsitForm>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息："+e.getMessage());
                    }

                    @Override
                    public void onNext(ResultListInfo<RegsitForm> regsitFormResultListInfo) {
                        ColpencilLogger.e("regsitFormResultListInfo="+regsitFormResultListInfo.toString());
                        int code = regsitFormResultListInfo.code;
                        String message = regsitFormResultListInfo.message;
                        switch (code){
                            case 0:
                                regsit_list.setVisibility(View.VISIBLE);
                                rlProgress.setVisibility(View.GONE);
                                rlError.setVisibility(View.GONE);
                                rlEmpty.setVisibility(View.GONE);

                                regsitFormList.clear();
                                regsitFormList.addAll(regsitFormResultListInfo.data);
                                adapter.notifyDataSetChanged();
                                tv_rigth.setText("删除");
                                break;
                            case 1:
                                regsit_list.setVisibility(View.GONE);
                                rlProgress.setVisibility(View.GONE);
                                rlError.setVisibility(View.VISIBLE);
                                rlEmpty.setVisibility(View.GONE);
                                btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getInfo();
                                    }
                                });
                                break;
                            case 2:
                                if (type == 0){
                                    intent = new Intent(RegistryFormListActivity.this, AddRegistryFormActivity.class);
                                    intent.putExtra("approveid",approveid);
                                    intent.putExtra("houseId",houseId);
                                    startActivity(intent);
                                }
                                regsit_list.setVisibility(View.GONE);
                                rlProgress.setVisibility(View.GONE);
                                rlError.setVisibility(View.GONE);
                                rlEmpty.setVisibility(View.VISIBLE);
                                btnReload.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getInfo();
                                    }
                                });
                                break;
                            case 3:
                                startActivity(new Intent(RegistryFormListActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });

    }
}
