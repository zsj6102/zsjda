package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.EstateInfo;
import com.colpencil.propertycloud.Bean.HouseInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.CityPickersActivity;
import com.colpencil.propertycloud.View.Adapter.CellSelectAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 修改上门服务地址
 * @Email DramaScript@outlook.com
 * @date 2016/9/9
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_change_adress
)
public class ChangeAdressActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.listview)
    ColpencilListView listview;
    @Bind(R.id.submit)
    Button submit;
    @Bind(R.id.tv_city)
    TextView tv_city;
    @Bind(R.id.tv_cell)
    TextView tv_cell;
    @Bind(R.id.tv_building)
    TextView tv_building;
    @Bind(R.id.tv_unit)
    TextView tv_unit;
    @Bind(R.id.tv_room)
    TextView tv_room;
    @Bind(R.id.et_constant)
    EditText et_constant;
    @Bind(R.id.ll_select_city)
    LinearLayout ll_city;
    @Bind(R.id.ll_select_estate)
    LinearLayout ll_estate;

    private CellSelectAdapter adapter;
    private List<HouseInfo> mlist = new ArrayList<>();
    private EstateInfo info;

    @Override
    protected void initViews(View view) {
        tv_title.setText("修改服务地址");
        info = (EstateInfo) getIntent().getSerializableExtra("data");
        tv_city.setText(info.getCityName());
        tv_cell.setText(info.getCellName());
        tv_building.setText(info.getBuildName() + "栋");
        tv_unit.setText(info.getUnitName() + "单元");
        tv_room.setText(info.getHourseName() + "号");
        et_constant.setText(SharedPreferencesUtil.getInstance(this).getString("mobile"));
        adapter = new CellSelectAdapter(this, mlist, R.layout.item_cell_select);
        listview.setAdapter(adapter);
        ll_left.setOnClickListener(this);
        submit.setOnClickListener(this);
        ll_city.setOnClickListener(this);
        ll_estate.setOnClickListener(this);
        loadData();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < mlist.size(); i++) {
                    if (i == position) {
                        info.setCityName(mlist.get(i).getCity_name());
                        info.setCellId(mlist.get(i).getCommunity_id());
                        info.setCellName(mlist.get(i).getShort_name());
                        info.setUnitId(mlist.get(i).getUnit_id());
                        info.setUnitName(mlist.get(i).getUnit_name());
                        info.setHouseId(mlist.get(i).getHouse_id());
                        info.setHourseName(mlist.get(i).getHouse_name());
                        info.setPropertytel(mlist.get(i).getPropertytel());
                        tv_city.setText(info.getCityName());
                        tv_cell.setText(info.getCellName());
                        tv_building.setText(info.getBuildName() + "栋");
                        tv_unit.setText(info.getUnitName() + "单元");
                        tv_room.setText(info.getHourseName() + "号");
                        adapter.setComId(mlist.get(i).getHouse_id());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
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
        int id = v.getId();
        if (id == R.id.ll_left) {
            finish();
        } else if (id == R.id.submit) {
            if (info.getBuildId() == 0 || info.getUnitId() == 0 || info.getHouseId() == 0) {
                ToastTools.showShort(this, "请选择完整的房产");
            } else {
                info.setMobile(et_constant.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("data", info);
                setResult(1, intent);
                finish();
            }
        } else if (id == R.id.ll_select_city) {
            Intent intent = new Intent(this, CityPickersActivity.class);
            intent.putExtra("data", info);
            startActivityForResult(intent, 1);
        } else if (id == R.id.ll_select_estate) {
            if (TextUtils.isEmpty(info.getCityName())) {
                ToastTools.showShort(this, "请先选择城市");
            } else {
                Intent intent = new Intent(this, SelectEstateActivity.class);
                intent.putExtra("data", info);
                startActivityForResult(intent, 2);
            }
        }
    }

    private void loadData() {
        DialogTools.showLoding(this, "温馨提示", "正在加载中...");
        HashMap<String, String> params = new HashMap<>();
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadEstate(params)
                .map(new Func1<ResultListInfo<HouseInfo>, ResultListInfo<HouseInfo>>() {
                    @Override
                    public ResultListInfo<HouseInfo> call(ResultListInfo<HouseInfo> info) {
                        return info;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<HouseInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogTools.dissmiss();
                    }

                    @Override
                    public void onNext(ResultListInfo<HouseInfo> info) {
                        DialogTools.dissmiss();
                        if (info.code == 0) {
                            adapter.setComId(ChangeAdressActivity.this.info.getHouseId());
                            mlist.addAll(info.data);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                info = (EstateInfo) data.getSerializableExtra("data");
                info.setCellName("");
                info.setCellId(0);
                info.setBuildName("");
                info.setBuildId(0);
                info.setUnitName("");
                info.setUnitId(0);
                info.setHourseName("");
                info.setHouseId(0);
                info.setPropertytel("");
                tv_city.setText(info.getCityName());
                tv_cell.setText(info.getCellName());
                tv_building.setText(info.getBuildName());
                tv_unit.setText(info.getUnitName());
                tv_room.setText(info.getHourseName());
                adapter.setComId(info.getHouseId());
                adapter.notifyDataSetChanged();
            }
        } else if (requestCode == 2) {
            if (data != null) {
                info = (EstateInfo) data.getSerializableExtra("data");
                tv_city.setText(info.getCityName());
                tv_cell.setText(info.getCellName());
                tv_building.setText(info.getBuildName() + "栋");
                tv_unit.setText(info.getUnitName() + "单元");
                tv_room.setText(info.getHourseName() + "号");
                adapter.setComId(info.getHouseId());
                adapter.notifyDataSetChanged();
            }
        }
    }

}
