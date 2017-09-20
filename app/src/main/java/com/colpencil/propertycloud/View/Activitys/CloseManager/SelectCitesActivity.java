package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.AreaInfo;
import com.colpencil.propertycloud.Bean.CityInfo;
import com.colpencil.propertycloud.Bean.EstateInfo;
import com.colpencil.propertycloud.Bean.Province;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Present.CloseManager.SelectCityPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Home.VilageSelectActivity;
import com.colpencil.propertycloud.View.Adapter.AreaAdapter;
import com.colpencil.propertycloud.View.Adapter.CityAdapter;
import com.colpencil.propertycloud.View.Adapter.ProvinceAdapter;
import com.colpencil.propertycloud.View.Imples.SelectCityView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author 陈 宝
 * @Description:选择城市
 * @Email 1041121352@qq.com
 * @date 2016/11/17
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_select_city
)
public class SelectCitesActivity extends ColpencilActivity implements SelectCityView {

    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.provice_listview)
    ListView provice_listview;
    @Bind(R.id.city_listview)
    ListView city_listview;
    @Bind(R.id.area_listview)
    ListView area_listview;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    private ProvinceAdapter provinceAdapter;    //省
    private CityAdapter cityAdapter;    //市
    private AreaAdapter areaAdapter;    //区

    private List<Province> provinces = new ArrayList<>();
    private List<CityInfo> citys = new ArrayList<>();
    private List<AreaInfo> areas = new ArrayList<>();

    private SelectCityPresent present;
    private int provinceId;
    private String provinceName;
    private int cityId;
    private String cityName;
    private int areaId;
    private String areaName;

    @Override
    protected void initViews(View view) {
        tv_title.setText("选择城市");
        provinceAdapter = new ProvinceAdapter(this, provinces);
        cityAdapter = new CityAdapter(this, citys);
        areaAdapter = new AreaAdapter(this, areas);
        provice_listview.setAdapter(provinceAdapter);
        city_listview.setAdapter(cityAdapter);
        area_listview.setAdapter(areaAdapter);
        provice_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < provinces.size(); i++) {
                    if (i == position) {
                        provinceId = provinces.get(i).getRegion_id();
                        provinceName = provinces.get(i).getLocal_name();
                        provinces.get(i).setSelect(true);
                    } else {
                        provinces.get(i).setSelect(false);
                    }
                }
                provinceAdapter.notifyDataSetChanged();
                citys.clear();
                cityAdapter.notifyDataSetChanged();
                areas.clear();
                areaAdapter.notifyDataSetChanged();
                present.loadCity(provinces.get(position).getRegion_id());
            }
        });
        city_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < citys.size(); i++) {
                    if (i == position) {
                        cityId = citys.get(i).getRegion_id();
                        citys.get(i).setSelect(true);
                        cityName = citys.get(i).getLocal_name();
                    } else {
                        citys.get(i).setSelect(false);
                    }
                }
                cityAdapter.notifyDataSetChanged();
                areas.clear();
                areaAdapter.notifyDataSetChanged();
                present.loadArea(citys.get(position).getRegion_id());
            }
        });
        area_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < areas.size(); i++) {
                    if (i == position) {
                        areaId = areas.get(i).getRegion_id();
                        areas.get(i).setSelect(true);
                        areaName = areas.get(i).getLocal_name();
                    } else {
                        areas.get(i).setSelect(false);
                    }
                }
                areaAdapter.notifyDataSetChanged();
                EstateInfo info = (EstateInfo) getIntent().getSerializableExtra("data");
                info.setCityName(cityName);
                Intent intent = new Intent();
                intent.putExtra("data", info);
                setResult(1, intent);
                finish();
            }
        });
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        present.loadProvince();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new SelectCityPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loadProvince(ResultListInfo<Province> resultListInfo) {
        if (resultListInfo.code == 0) {
            provinces.clear();
            provinces.addAll(resultListInfo.data);
            provinceAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadCitys(ResultListInfo<CityInfo> resultListInfo) {
        DialogTools.dissmiss();
        if (resultListInfo.code == 0) {
            citys.clear();
            citys.addAll(resultListInfo.data);
            cityAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadArea(ResultListInfo<AreaInfo> resultListInfo) {
        DialogTools.dissmiss();
        if (resultListInfo.code == 0) {
            if (resultListInfo.data != null && resultListInfo.data.size() > 0) {
                areas.clear();
                areas.addAll(resultListInfo.data);
                areaAdapter.notifyDataSetChanged();
            } else {

            }
        }
    }

}
