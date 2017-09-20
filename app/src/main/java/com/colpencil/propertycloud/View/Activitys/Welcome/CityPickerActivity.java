package com.colpencil.propertycloud.View.Activitys.Welcome;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.CityBean;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Bean.TownInfo;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.Overall.LocateState;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Service.LocationService;
import com.colpencil.propertycloud.Ui.SideLetterBar;
import com.colpencil.propertycloud.View.Activitys.CheckPermissionsActivity;
import com.colpencil.propertycloud.View.Activitys.Home.VilageSelectActivity;
import com.colpencil.propertycloud.View.Adapter.CityListAdapter;
import com.colpencil.propertycloud.View.Adapter.ResultListAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * @author 陈 宝
 * @Description:城市选择
 * @Email 1041121352@qq.com
 * @date 2016/12/6
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_city_list
)
public class CityPickerActivity extends CheckPermissionsActivity implements View.OnClickListener {

    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.listview_all_city)
    ListView mListView;
    @Bind(R.id.listview_search_result)
    ListView mResultListView;
    @Bind(R.id.side_letter_bar)
    SideLetterBar mLetterBar;
    @Bind(R.id.et_search)
    EditText searchBox;
    @Bind(R.id.empty_view)
    ViewGroup emptyView;

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<TownInfo> mAllCities = new ArrayList<>();

    private LinkedHashMap<String, ArrayList<TownInfo>> mMap = new LinkedHashMap<>();
    private Observable<RxBusMsg> observable;

    @Override
    protected void initViews(View view) {
        ll_left.setOnClickListener(this);
        tv_title.setText("修改城市");
        if (getIntent().getIntExtra("flag", 1) == 1) {
            ll_left.setVisibility(View.INVISIBLE);
        } else {
            ll_left.setVisibility(View.VISIBLE);
        }
        initData();
        initWidget();
        addMap();
        loadCities();
        initBus();
    }

    private void initData() {
        mCityAdapter = new CityListAdapter(this, mAllCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                back(name);
            }

            @Override
            public void onLocateClick() {
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                Intent intent = new Intent(CityPickerActivity.this, LocationService.class);
                intent.putExtra("type", 1);
                startService(intent);
            }
        });
        mResultAdapter = new ResultListAdapter(this, null);
    }

    private void initWidget() {
        mListView.setAdapter(mCityAdapter);
        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar.setOverlay(overlay);
        if (mAllCities.size() != 0) {
            mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
                @Override
                public void onLetterChanged(String letter) {
                    int position = mCityAdapter.getLetterPosition(letter);
                    mListView.setSelection(position);
                }
            });
        }

        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                back(mResultAdapter.getItem(position).getLocal_name());
            }
        });
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    mResultListView.setVisibility(View.VISIBLE);
                    List<TownInfo> result = search(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });
    }

    private void back(String city) {
        SharedPreferencesUtil.getInstance(this).setString("cityName", city);
        loadCityId();
        Intent intent = new Intent(CityPickerActivity.this, VilageSelectActivity.class);
        intent.putExtra("flag", getIntent().getIntExtra("flag", 1));
        intent.putExtra("isOnce", getIntent().getIntExtra("isOnce", 1));
        intent.putExtra("isFirstIn", false);
        intent.putExtra("cityName", city);
        startActivity(intent);
    }

    private void initBus() {
        Intent intent = new Intent(this, LocationService.class);
        intent.putExtra("type", 1);
        startService(intent);
        observable = RxBus.get().register(VilageSelectActivity.class.getSimpleName(), RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 6) {
                    mCityAdapter.updateLocateState(msg.getId(), msg.getMsg());
                }
            }
        };
        observable.subscribe(subscriber);
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
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(VilageSelectActivity.class.getSimpleName(), observable);
    }

    private void loadCities() {
        HashMap<String, String> params = new HashMap<>();
        DialogTools.showLoding(this, "温馨提示", "正在获取城市列表...");
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadCities(params)
                .map(new Func1<ResultListInfo<TownInfo>, ResultListInfo<TownInfo>>() {
                    @Override
                    public ResultListInfo<TownInfo> call(ResultListInfo<TownInfo> townInfoResultListInfo) {
                        return townInfoResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<TownInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogTools.dissmiss();
                    }

                    @Override
                    public void onNext(ResultListInfo<TownInfo> info) {
                        DialogTools.dissmiss();
                        if (info.code == 0) {
                            mAllCities.addAll(info.data);
                            for (String key : mMap.keySet()) {
                                ArrayList<TownInfo> list = mMap.get(key);
                                for (int i = 0; i < mAllCities.size(); i++) {
                                    if (key.equals(mAllCities.get(i).getFirst_name())) {
                                        list.add(mAllCities.get(i));
                                    }
                                }
                            }
                            mAllCities.clear();
                            mAllCities.add(0, new TownInfo("定位", "0"));
                            for (String key : mMap.keySet()) {
                                ArrayList<TownInfo> list = mMap.get(key);
                                mAllCities.addAll(list);
                            }
                            mCityAdapter.setLetters();
                            mCityAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void addMap() {
        for (int i = 65; i <= 90; i++) {
            char a = (char) i;
            mMap.put(a + "", new ArrayList<TownInfo>());
        }
    }

    private List<TownInfo> search(String name) {
        List<TownInfo> mList = new ArrayList<>();
        for (int i = 0; i < mAllCities.size(); i++) {
            if (mAllCities.get(i).getLocal_name().contains(name)) {
                mList.add(mAllCities.get(i));
            }
        }
        return mList;
    }

    private void loadCityId() {
        HashMap<String, String> params = new HashMap<>();
        params.put("cityName", SharedPreferencesUtil.getInstance(this).getString("cityName"));
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadCityId(params)
                .map(new Func1<ResultInfo<CityBean>, ResultInfo<CityBean>>() {
                    @Override
                    public ResultInfo call(ResultInfo<CityBean> loginInfoResultInfo) {
                        return loginInfoResultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<CityBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultInfo<CityBean> result) {
                        if (result.code == 0) {
                            SharedPreferencesUtil.getInstance(CityPickerActivity.this).setInt("cityId", result.data.cityId);
                        }
                    }
                });
    }

}
