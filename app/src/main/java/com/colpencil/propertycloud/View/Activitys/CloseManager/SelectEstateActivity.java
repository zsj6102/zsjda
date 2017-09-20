package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.Building;
import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.EstateInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RoomInfo;
import com.colpencil.propertycloud.Bean.Unit;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Adapter.EstateBuildAdapter;
import com.colpencil.propertycloud.View.Adapter.EstateCellAdapter;
import com.colpencil.propertycloud.View.Adapter.EstateRoomAdapter;
import com.colpencil.propertycloud.View.Adapter.EstateUnitAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈 宝
 * @Description:选择房产
 * @Email 1041121352@qq.com
 * @date 2016/12/2
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_select_estate
)
public class SelectEstateActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.listview1)
    ListView listview1;
    @Bind(R.id.listview2)
    ListView listview2;
    @Bind(R.id.listview3)
    ListView listview3;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_title)
    TextView tv_title;

    private List<CellInfo> cells = new ArrayList<>();
    private EstateCellAdapter cellAdapter;
    private int communityId;

    private List<Building> builds = new ArrayList<>();
    private EstateBuildAdapter buildAdapter;
    private int buildId;

    private List<Unit> units = new ArrayList<>();
    private EstateUnitAdapter unitAdapter;
    private int unitId;

    private List<RoomInfo> rooms = new ArrayList<>();
    private EstateRoomAdapter roomAdapter;
    private int roomId = -1;
    private EstateInfo info;

    @Override
    protected void initViews(View view) {
        tv_title.setText("选择房间");
        ll_left.setOnClickListener(this);
        info = (EstateInfo) getIntent().getSerializableExtra("data");
        initAdapter();
        loadCell();
    }

    private void initAdapter() {
        cellAdapter = new EstateCellAdapter(this, cells, R.layout.item_select_city);
        listview.setAdapter(cellAdapter);
        buildAdapter = new EstateBuildAdapter(this, builds, R.layout.item_select_city);
        listview1.setAdapter(buildAdapter);
        unitAdapter = new EstateUnitAdapter(this, units, R.layout.item_select_city);
        listview2.setAdapter(unitAdapter);
        roomAdapter = new EstateRoomAdapter(this, rooms, R.layout.item_select_city);
        listview3.setAdapter(roomAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < cells.size(); i++) {
                    if (i == position) {
                        communityId = cells.get(i).getCommunity_id();
                        cells.get(i).setSelect(true);
                        info.setCellId(communityId);
                        info.setCellName(cells.get(i).getShortname());
                        info.setPropertytel(cells.get(i).getPropertytel());
                    } else {
                        cells.get(i).setSelect(false);
                    }
                }
                cellAdapter.notifyDataSetChanged();
                loadBuilding();
            }
        });
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < builds.size(); i++) {
                    if (i == position) {
                        buildId = builds.get(i).getBuildId();
                        builds.get(i).setSelect(true);
                        info.setBuildId(buildId);
                        info.setBuildName(builds.get(i).getBuildName());
                    } else {
                        builds.get(i).setSelect(false);
                    }
                }
                buildAdapter.notifyDataSetChanged();
                loadUnit();
            }
        });
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < units.size(); i++) {
                    if (i == position) {
                        unitId = units.get(i).getUnitId();
                        units.get(i).setSelect(true);
                        info.setUnitId(unitId);
                        info.setUnitName(units.get(i).getUnitName());
                    } else {
                        units.get(i).setSelect(false);
                    }
                }
                unitAdapter.notifyDataSetChanged();
                loadRoom();
            }
        });
        listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < rooms.size(); i++) {
                    if (i == position) {
                        roomId = rooms.get(i).getHouseId();
                        rooms.get(i).setSelect(true);
                        info.setHouseId(roomId);
                        info.setHourseName(rooms.get(i).getHouseCode());
                    } else {
                        rooms.get(i).setSelect(false);
                    }
                }
                roomAdapter.notifyDataSetChanged();
                if (roomId != -1) {
                    Intent intent = new Intent();
                    intent.putExtra("data", info);
                    setResult(1, intent);
                    finish();
                }
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

    private void loadCell() {
        HashMap<String, String> params = new HashMap<>();
        params.put("regionName", info.getCityName());
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadCellByName(params)
                .map(new Func1<ResultListInfo<CellInfo>, ResultListInfo<CellInfo>>() {
                    @Override
                    public ResultListInfo<CellInfo> call(ResultListInfo<CellInfo> villageResultListInfo) {
                        return villageResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<CellInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultListInfo<CellInfo> resultListInfo) {
                        if (resultListInfo.code == 0) {
                            cells.addAll(resultListInfo.data);
                            cellAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void loadBuilding() {
        HashMap<String, String> params = new HashMap<>();
        params.put("communityId", communityId + "");
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadBuildingById(params)
                .map(new Func1<ResultListInfo<Building>, ResultListInfo<Building>>() {
                    @Override
                    public ResultListInfo<Building> call(ResultListInfo<Building> info) {
                        return info;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<Building>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultListInfo<Building> resultListInfo) {
                        if (resultListInfo.code == 0) {
                            builds.clear();
                            builds.addAll(resultListInfo.data);
                            buildAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void loadUnit() {
        HashMap<String, String> params = new HashMap<>();
        params.put("buildingId", buildId + "");
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadUnitById(params)
                .map(new Func1<ResultListInfo<Unit>, ResultListInfo<Unit>>() {
                    @Override
                    public ResultListInfo<Unit> call(ResultListInfo<Unit> unitResultListInfo) {
                        return unitResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<Unit>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultListInfo<Unit> resultListInfo) {
                        if (resultListInfo.code == 0) {
                            units.clear();
                            units.addAll(resultListInfo.data);
                            unitAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void loadRoom() {
        HashMap<String, String> params = new HashMap<>();
        params.put("unitId", unitId + "");
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadRoomsById(params)
                .map(new Func1<ResultListInfo<RoomInfo>, ResultListInfo<RoomInfo>>() {
                    @Override
                    public ResultListInfo<RoomInfo> call(ResultListInfo<RoomInfo> roomInfoResultListInfo) {
                        return roomInfoResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<RoomInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultListInfo<RoomInfo> resultListInfo) {
                        if (resultListInfo.code == 0) {
                            rooms.clear();
                            rooms.addAll(resultListInfo.data);
                            roomAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_left) {
            finish();
        }
    }
}
