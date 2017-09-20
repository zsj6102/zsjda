package com.colpencil.tenement.View.Fragments.Workbeach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.Equipment;
import com.colpencil.tenement.Bean.Event;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Bean.RxEquiment;
import com.colpencil.tenement.Present.Home.EquipmentPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.EquipmentControlActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.ScanCodeActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.StartPolingActivity;
import com.colpencil.tenement.View.Adpaters.EquipmentAdapter;
import com.colpencil.tenement.View.Imples.EquipmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 设备管理
 * @Email DramaScript@outlook.com
 * @date 2016/8/23
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_equipment
)
public class EquipmentFragment extends ColpencilFragment implements EquipmentView, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

    @Bind(R.id.tv_keep)
    TextView tv_keep;

    @Bind(R.id.tv_count)
    TextView tv_count;

    @Bind(R.id.tv_start)
    TextView tv_start;

    @Bind(R.id.bg_equipment)
    SwipeRefreshLayout bg_equipment;

    @Bind(R.id.lv_equipment)
    AutoLoadRecylerView lv_equipment;

    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;

    @Bind(R.id.rlEmpty)
    RelativeLayout rlEmpty;

    @Bind(R.id.rlError)
    RelativeLayout rlError;

    @Bind(R.id.ll_show)
    LinearLayout ll_show;

    @Bind(R.id.btnReloadEmpty)
    Button btnReloadEmpty;

    @Bind(R.id.btnReload)
    Button btnReload;

    private LinearLayoutManager layoutManager;

    private int mType = EquipmentControlActivity.EQUIPMENT_KEEP;
    private EquipmentPresent present;
    private EquipmentAdapter adapter;

    private int page = 1;
    private int pageSize = 20;
    private List<Equipment> equipments = new ArrayList<>();
    private Observable<RxEquiment> register;

    private int self = 0;
    private String devCode="";
    private String devName="";
    private String communityId="";
    private String beginTime = "";
    private String endTime = "";
    private Observable<Event> start;
    private Observable<RxBusMsg> msgObservable;

    public static EquipmentFragment newInstance(int type) {
        Bundle args = new Bundle();
        EquipmentFragment fragment = new EquipmentFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        ll_show.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        mType = getArguments().getInt("type");
        switch (mType) {
            case 0:
                tv_start.setText("开始巡检");
                tv_keep.setText("今日巡检设备数：");
                break;
            case 1:
                tv_start.setText("开始维修");
                tv_keep.setText("今日维修设备数：");
                break;
            case 2:
                tv_start.setText("开始保养");
                tv_keep.setText("今日保养设备数：");
                break;
        }
        //初始化下拉刷新控件
        bg_equipment.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        bg_equipment.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getActivity());
        lv_equipment.setLayoutManager(layoutManager);
        lv_equipment.setLoadMoreListener(this);

        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanCodeActivity.class);
                intent.putExtra("flag", mType + 1);
                intent.putExtra("where",1);
                startActivity(intent);
            }
        });

        register = RxBus.get().register("eq", RxEquiment.class);
        Subscriber<RxEquiment> subscriber = new Subscriber<RxEquiment>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxEquiment rxEquiment) {
                if (rxEquiment.getFlag()==0){
                    self = rxEquiment.getSelf();
                    devCode = rxEquiment.getDevCode();
                    devName = rxEquiment.getDevName();
                    communityId = rxEquiment.getCommunityId();
                    beginTime = rxEquiment.getStartDate();
                    endTime = rxEquiment.getEndDate();
                    page=1;
                    loadData();
                }
            }
        };
        register.subscribe(subscriber);

        start = RxBus.get().register("start", Event.class);
        Subscriber<Event> eventSubscriber = new Subscriber<Event>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Event event) {
                int flag = event.getFlag();
                String devCode = event.getDevCode();
                switch (flag){
                    case 1:
                        page=1;
                        present.getEquipmentList(communityId, mType+1, page, pageSize,devCode,devName,self,beginTime,endTime);
                        break;
                    case 2:
                        page=1;
                        present.getEquipmentList(communityId, mType+1, page, pageSize,devCode,devName,self,beginTime,endTime);
                        break;
                    case 3:
                        page=1;
                        present.getEquipmentList(communityId, mType+1, page, pageSize,devCode,devName,self,beginTime,endTime);
                        break;
                }
            }
        };
        start.subscribe(eventSubscriber);

        msgObservable = RxBus.get().register("spa", RxBusMsg.class);
        Subscriber<RxBusMsg> msgSubscriber = new Subscriber<RxBusMsg>() {
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
        msgObservable.subscribe(msgSubscriber);

        adapter = new EquipmentAdapter(getActivity(), equipments, R.layout.item_equipment, mType + 1);
        lv_equipment.setAdapter(adapter);
        loadData();
        int user_id = SharedPreferencesUtil.getInstance(mContext).getInt("user_id", 0);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                if (equipments.get(position).current==user_id){
                    Intent intent = new Intent(getActivity(), StartPolingActivity.class);
                    intent.putExtra("flag", mType + 1);
                    intent.putExtra("isCode",0);
                    intent.putExtra("eq_name",equipments.get(position).eq_name);
                    intent.putExtra("date",equipments.get(position).date);
                    intent.putExtra("eq_code",equipments.get(position).eq_code);
                    intent.putExtra("record",equipments.get(position).record);
                    intent.putExtra("maintainId",equipments.get(position).maintain_id+"");
                    intent.putExtra("eq_location",equipments.get(position).eq_location);
                    intent.putExtra("equipmentId",equipments.get(position).equipment_id+"");
                    intent.putExtra("communityId",communityId);
                    intent.putExtra("last_emp",equipments.get(position).last_emp);
                    intent.putExtra("eqtype",equipments.get(position).eq_type+"");
                    startActivity(intent);
                }else {
                    ToastTools.showShort(getActivity(),false,"您没有该条记录的修改权限!");
                }

            }
        });
    }

    private void loadData(){
        present.getEquipmentList(communityId, mType+1, page, pageSize,devCode,devName,self,beginTime,endTime);

    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new EquipmentPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void refresh(ListCommonResult<Equipment> result) {
        ColpencilLogger.e("设备管理的信息：" + result.toString());
        int code = result.getCode();
        String message = result.getMessage();
        switch (code){
            case 0:
                ll_show.setVisibility(View.VISIBLE);
                rlProgress.setVisibility(View.GONE);
                rlError.setVisibility(View.GONE);
                rlEmpty.setVisibility(View.GONE);
                break;
            case 1:
                ToastTools.showShort(getActivity(),false,message);
                break;
            case 2:
                ll_show.setVisibility(View.GONE);
                rlProgress.setVisibility(View.GONE);
                rlError.setVisibility(View.GONE);
                rlEmpty.setVisibility(View.VISIBLE);
                btnReload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        page = 1;
                        present.getEquipmentList(communityId, mType+1, page, pageSize,devCode,devName,self,beginTime,endTime);
                    }
                });
                break;
            case 3:
                startActivity(new Intent(getActivity(),LoginActivity.class));
                break;
        }
        equipments.clear();
        equipments.addAll(result.getData());
        adapter.notifyDataSetChanged();
        bg_equipment.setRefreshing(false);
    }

    @Override
    public void loadMore(ListCommonResult<Equipment> result) {
        if (result.getCode()==2){

        }else {
            equipments.addAll(result.getData());
            adapter.notifyDataSetChanged();
        }
        lv_equipment.setLoading(false);
    }

    @Override
    public void loadError(String msg) {  // 应该显示异常的界面
        ll_show.setVisibility(View.GONE);
        rlProgress.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
        rlEmpty.setVisibility(View.GONE);
        btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                present.getEquipmentList(communityId, mType+1, page, pageSize,devCode,devName,self,beginTime,endTime);
            }
        });
    }

    @Override
    public void count(int count) {
        tv_count.setText(count+"");
    }

    @Override
    public void code(int code) {
        switch (code){
            case 3:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        present.getEquipmentList(communityId, mType+1, page, pageSize,devCode,devName,self,beginTime,endTime);
    }

    @Override
    public void onLoadMore() {
            page++;
        present.getEquipmentList(communityId, mType+1, page, pageSize,devCode,devName,self,beginTime,endTime);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("eq",register);
        RxBus.get().unregister("start",start);
        RxBus.get().unregister("spa",msgObservable);

    }
}
