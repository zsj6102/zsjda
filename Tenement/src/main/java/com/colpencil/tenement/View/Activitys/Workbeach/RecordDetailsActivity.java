package com.colpencil.tenement.View.Activitys.Workbeach;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.Building;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.ReadingNum;
import com.colpencil.tenement.Bean.Room;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Bean.Unit;
import com.colpencil.tenement.Bean.UtilitiesRecord;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Present.Home.UtilitiesPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Tools.ListUtils;
import com.colpencil.tenement.Ui.BottomDialog;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Adpaters.RecordDetailsAdapter;
import com.colpencil.tenement.View.Imples.UtilitiesView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ScreenUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.UITools;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.DividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * @Description: 抄水表的记录详情
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/18
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_recorddetails
)
public class RecordDetailsActivity extends ColpencilActivity
        implements OnClickListener, UtilitiesView, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

    @Bind(R.id.list_recorddetails)
    AutoLoadRecylerView list_recorddetails;

    @Bind(R.id.bga_recorddetails)
    SwipeRefreshLayout bga_recorddetails;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.tv_gotoAction)
    TextView tv_gotoAction;

    @Bind(R.id.has_record)
    TextView tv_hasrecord;

    @Bind(R.id.none_record)
    TextView tv_norecord;

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

    private UtilitiesPresent present;

    private List<UtilitiesRecord> records = new ArrayList<>();

    private RecordDetailsAdapter adapter;

    private boolean isRefresh = false;

    private int page = 1;

    private int pageSize = 20;



    private int flag = -1;  //  -1 查询所有     1 按小区查询   2 按楼宇查询   3 单元查询     4按房间号查询

    private boolean isSee = false;  // false  不可以  true  可以

    private int isWater = -1;  //  -1  不分类   0  电表分   1  水表分

    private String communityId = "";
    private String dan = "";
    private String buildId = "";
    private String hourseId = "";
    private String type = "0,1";
    private int self = 0;

    private LinearLayoutManager layoutManager;
    private DialogPlus dialogPlus;
    private TextView tv_village;
    private TextView tv_building;
    private TextView tv_room;
    private TextView tv_dan;
    private Observable<RxBusMsg> msgObservable;
    private int user_id;
    private String poild;
    private String build;
    private String danYuan;
    private String hourse;

    @Override
    protected void initViews(View view) {
        bga_recorddetails.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        tv_rigth.setVisibility(View.VISIBLE);
        tv_rigth.setText("筛选");
        tv_title.setText("抄表管理");

//        setStatecolor(getResources().getColor(R.color.colorPrimary));
        setStatusBaropen(false);
        //初始化下拉刷新控件
        bga_recorddetails.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        bga_recorddetails.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        list_recorddetails.setLayoutManager(layoutManager);
        list_recorddetails.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.TRANSPARENT)
                .sizeResId(R.dimen.divider)
                .build());
        list_recorddetails.setLoadMoreListener(this);
        // 初始化点击事件
        ll_left.setOnClickListener(this);
        ll_rigth.setOnClickListener(this);

        tv_gotoAction.setOnClickListener(this);
        //获取抄表数
        present.loadNum(communityId,buildId,dan,hourseId,type);
        adapter = new RecordDetailsAdapter(this, records, R.layout.item_recorddetails);
        list_recorddetails.setAdapter(adapter);
        user_id = SharedPreferencesUtil.getInstance(this).getInt("user_id", 0);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                ColpencilLogger.e("当前时间："+TimeUtil.getTimeStrFromMillisHour(System.currentTimeMillis()).substring(0,7));
                if (records.get(position).getEmpId()==user_id){
                    if (records.get(position).getDate().substring(0,7).equals(TimeUtil.getTimeStrFromMillisHour(System.currentTimeMillis()).substring(0,7))){
                        Intent intent =  new Intent(RecordDetailsActivity.this,WriteWatermeterActivity.class);
                        intent.getIntExtra("flag",0);
                        intent.putExtra("roomNo",records.get(position).getRoomNo());
                        intent.putExtra("roomId",records.get(position).getRoomId()+"");
                        intent.putExtra("ownerName",records.get(position).getOwnerName());
                        intent.putExtra("waterId",records.get(position).getWaterId());
                        intent.putExtra("type",records.get(position).getType());
                        intent.putExtra("communityId",records.get(position).getCommunityId()+"");
                        intent.putExtra("degrees",records.get(position).getDegrees()+"");
                        intent.putExtra("lastRecord",records.get(position).getLastRecord()+"");
                        startActivity(intent);
                    }else {
                        ToastTools.showShort(RecordDetailsActivity.this,"该表已出账单，不能修改！");
                    }
                }else {
//                    ToastTools.showShort(RecordDetailsActivity.this,"您没有改条记录的修改权限！");
                }
            }
        });

        loadData();

        msgObservable = RxBus.get().register("wwa", RxBusMsg.class);
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
                        onRefresh();
                        break;
                }
            }
        };
        msgObservable.subscribe(subscriber);
    }



    @Override
    public ColpencilPresenter getPresenter() {
        present = new UtilitiesPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_gotoAction://进行抄表操作
                finish();
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_rigth: // 筛选
                View view = View.inflate(this, R.layout.dialog_select_water, null);
                dialogPlus = DialogPlus.newDialog(this)
                        .setContentHolder(new ViewHolder(view))
                        .setCancelable(true)
                        .setGravity(Gravity.TOP)
                        .setExpanded(true, UITools.convertDpToPixel(340))
                        .create();
                dialogPlus
                        .show();
                tv_village = (TextView) view.findViewById(R.id.tv_village);
                tv_village.setText(poild);
                tv_building = (TextView) view.findViewById(R.id.tv_building);
                tv_building.setText(build);
                tv_room = (TextView) view.findViewById(R.id.tv_room);
                tv_room.setText(hourse);
                tv_dan = (TextView) view.findViewById(R.id.tv_dan);
                tv_dan.setText(danYuan);
                RelativeLayout rl_select_villige = (RelativeLayout) view.findViewById(R.id.rl_select_villige);
                RelativeLayout rl_select_building = (RelativeLayout) view.findViewById(R.id.rl_select_building);
                RelativeLayout rl_select_dan = (RelativeLayout) view.findViewById(R.id.rl_select_dan);
                RelativeLayout rl_select_room = (RelativeLayout) view.findViewById(R.id.rl_select_room);
                RelativeLayout ll_is_see = (RelativeLayout) view.findViewById(R.id.ll_is_see);
                LinearLayout ll_ele = (LinearLayout) view.findViewById(R.id.ll_ele);
                LinearLayout ll_water = (LinearLayout) view.findViewById(R.id.ll_water);
                TextView iv_is_see = (TextView) view.findViewById(R.id.iv_is_see);
                ImageView iv_electri = (ImageView) view.findViewById(R.id.iv_electri);
                ImageView iv_water = (ImageView) view.findViewById(R.id.iv_water);
                Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
                Button btn_clear = (Button) view.findViewById(R.id.btn_clear);
                rl_select_villige.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogTools.showLoding(RecordDetailsActivity.this,"温馨提示","正在获取全部小区");
                        present.loadVillage();
                    }
                });
                rl_select_building.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(communityId)) {
                            ToastTools.showShort(RecordDetailsActivity.this,"请先选择小区");
                            return;
                        }
                        DialogTools.showLoding(RecordDetailsActivity.this,"温馨提示","正在获取全部楼宇");
                        present.loadBuilds(communityId);
                    }
                });
                rl_select_room.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(dan)) {
                            ToastTools.showShort(RecordDetailsActivity.this, "请先选择单元");
                            return;
                        }
                        DialogTools.showLoding(RecordDetailsActivity.this,"温馨提示","正在获取房间号");
                        ColpencilLogger.e("------------选择房间号");
                        present.loadRooms(dan);
                    }
                });
                rl_select_dan.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(buildId)) {
                            ToastTools.showShort(RecordDetailsActivity.this, "请先选择楼宇");
                            return;
                        }
                        DialogTools.showLoding(RecordDetailsActivity.this,"温馨提示","正在获取单元");
                        present.loadUnit(buildId);
                    }
                });
                if (isSee==false){
                    iv_is_see.setBackground(getResources().getDrawable(R.mipmap.un_open));
                }else {
                    iv_is_see.setBackground(getResources().getDrawable(R.mipmap.open));
                }
                ll_is_see.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (isSee==true){
                            iv_is_see.setBackground(getResources().getDrawable(R.mipmap.un_open));
                            isSee = false;
                            self = 0;
                        }else {
                            iv_is_see.setBackground(getResources().getDrawable(R.mipmap.open));
                            isSee = true;
                            self = 1;
                        }
                    }
                });
                if ("1".equals(type)){
                    iv_electri.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                    iv_water.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                }else if ("0".equals(type)){
                    iv_electri.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                    iv_water.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                }
                ll_ele.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            iv_electri.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                            iv_water.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                            type = "1";
                    }
                });
                ll_water.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            iv_electri.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                            iv_water.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                            type = "0";
                    }
                });
                btn_ok.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogPlus.dismiss();
                        ColpencilLogger.e("self="+self+",houseCode="+hourseId+",type="+type+",unitId="+dan
                                            +",buildingId="+buildId+",communityId="+communityId);
                        page=1;
                        loadData();
                        present.loadNum(communityId,buildId,dan,hourseId,type);
                    }
                });
                btn_clear.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_village.setText("");
                        tv_building.setText("");
                        tv_room.setText("");
                        tv_dan.setText("");
                        poild = "";
                        build = "";
                        danYuan = "";
                        hourse = "";
                        communityId = "";
                        buildId = "";
                        dan = "";
                        hourseId = "";
                        isSee = false;
                        iv_water.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                        iv_electri.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                        type = "0,1";
                    }
                });
                break;
        }
    }

    private void loadData() {
        DialogTools.showLoding(RecordDetailsActivity.this,"温馨提示","获取抄表记录中。。。");
        present.loadRecord(self,type,dan, communityId, buildId, hourseId, page, pageSize);

    }

    @Override
    public void loadCommunity(ListCommonResult<Village> result) {
        DialogTools.dissmiss();
        int code = result.getCode();
        String message = result.getMessage();
        switch (code){
            case 0:
                List<String> villageList = new ArrayList<>();
                for (Village village:result.getData()){
                    villageList.add(village.plot);
                }
                new BottomDialog.Builder(this)
                        .setTitle("选择小区")
                        .setDataList(villageList)
                        .setPositiveText("确认")
                        .setNegativeText("取消")
                        .onPositive(dialog -> {
                            communityId = result.getData().get(dialog.position).plotId;
                            poild = villageList.get(dialog.position);
                            tv_village.setText(villageList.get(dialog.position));
                        })
                        .onNegative(dialog -> {

                        }).show();
                break;
            case 1:
                ToastTools.showShort(this,false,message);
                break;
            case 2:
                ToastTools.showShort(this,false,message);
                break;
            case 3:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }

    @Override
    public void loadBuilds(ListCommonResult<Building> result) {
        DialogTools.dissmiss();
        int code = result.getCode();
        String message = result.getMessage();
        switch (code){
            case 0:
                List<String> buildings = new ArrayList<>();
                for (Building building : result.getData()) {
                    buildings.add(building.getBuildName());
                }
                new BottomDialog.Builder(this)
                        .setTitle("选择楼宇")
                        .setDataList(buildings)
                        .setPositiveText("确认")
                        .setNegativeText("取消")
                        .onPositive(dialog -> {
                            buildId = result.getData().get(dialog.position).getBuildId();
                            build = buildings.get(dialog.position);
                            tv_building.setText(buildings.get(dialog.position));
                        })
                        .onNegative(dialog -> {

                        }).show();
                break;
            case 1:
                ToastTools.showShort(this,false,message);
                break;
            case 2:
                ToastTools.showShort(this,false,message);
                break;
        }
    }

    @Override
    public void loadUnit(ListCommonResult<Unit> result) {
        DialogTools.dissmiss();
        int code = result.getCode();
        String message = result.getMessage();
        switch (code){
            case 0:
                List<String> unitList = new ArrayList<>();
                for (Unit unit:result.getData()){
                    unitList.add(unit.unitName);
                }
                new BottomDialog.Builder(this)
                        .setTitle("选择单元")
                        .setDataList(unitList)
                        .setPositiveText("确认")
                        .setNegativeText("取消")
                        .onPositive(dialog -> {
                            dan = result.getData().get(dialog.position).unitId;
                            danYuan = unitList.get(dialog.position);
                            tv_dan.setText(unitList.get(dialog.position));
                        })
                        .onNegative(dialog -> {

                        }).show();
                break;
            case 1:
                ToastTools.showShort(this,false,message);
                break;
            case 2:
                ToastTools.showShort(this,false,message);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("wwa",msgObservable);
    }

    @Override
    public void loadRooms(ListCommonResult<Room> result) {
        DialogTools.dissmiss();
        int code = result.getCode();
        String message = result.getMessage();
        switch (code){
            case 0:
                List<String> roomNames = new ArrayList<>();
                for (Room room : result.getData()) {
                    roomNames.add(room.getHouseCode());
                }
                new BottomDialog.Builder(this)
                        .setTitle("选择房间号")
                        .setDataList(roomNames)
                        .setPositiveText("确认")
                        .setNegativeText("取消")
                        .onPositive(dialog -> {
                            hourseId = result.getData().get(dialog.position).getHouseId();
                            hourse = roomNames.get(dialog.position);
                            tv_room.setText(roomNames.get(dialog.position));
                        })
                        .onNegative(dialog -> {

                        }).show();
                break;
            case 1:
                ToastTools.showShort(this,false,message);
                break;
            case 2:
                ToastTools.showShort(this,false,message);
                break;
        }
        ColpencilLogger.e("房间的数据：" + result.getData().size());
    }

    @Override
    public void loadMore(ListCommonResult<UtilitiesRecord> list) {
        if (list.getData().size()==0){
        }else {
            records.addAll(list.getData());
            adapter.notifyDataSetChanged();
        }
        list_recorddetails.setLoading(false);
    }

    @Override
    public void refresh(ListCommonResult<UtilitiesRecord> list) {
        DialogTools.dissmiss();
        if (list.getCode()==2){
            // 数据为空
            bga_recorddetails.setVisibility(View.GONE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.VISIBLE);
            btnReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    page = 1;
                    loadData();
                }
            });
        }else if (list.getCode()==3){
            startActivity(new Intent(this, LoginActivity.class));
        }else {
            bga_recorddetails.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
        }
        records.clear();
        records.addAll(list.getData());
        adapter.notifyDataSetChanged();
        bga_recorddetails.setRefreshing(false);
    }

    @Override
    public void loadNum(ReadingNum num) {
        tv_hasrecord.setText(num.getRecorded() + "");
        tv_norecord.setText(num.getNotRecorded() + "");
    }

    @Override
    public void loadError(String msg) {
        // 加载出错
        bga_recorddetails.setVisibility(View.GONE);
        rlProgress.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
        rlEmpty.setVisibility(View.GONE);
        btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                loadData();
            }
        });

    }

    @Override
    public void loadVilError() {
        DialogTools.dissmiss();

    }

    @Override
    public void onRefresh() {
        page = 1;
        loadData();
    }

    @Override
    public void onLoadMore() {
            page++;
        present.loadRecord(self,type,dan, communityId, buildId, hourseId, page, pageSize);
    }
}
