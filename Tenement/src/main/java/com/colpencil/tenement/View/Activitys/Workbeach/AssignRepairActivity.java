package com.colpencil.tenement.View.Activitys.Workbeach;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.AssignMsg;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OnlineListUser;
import com.colpencil.tenement.Bean.RepairOrderAssign;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.Present.Home.AssignEmployeePresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Adpaters.AssignEmployeeListAdapter;
import com.colpencil.tenement.View.Imples.AssignEmployeeListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/2/10.
 */

@ActivityFragmentInject(contentViewId = R.layout.activity_assignemployee)
public class AssignRepairActivity extends ColpencilActivity implements AssignEmployeeListView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tvEmpty)
    TextView tv_empty;

    @Bind(R.id.expand_assign_view)
    ExpandableListView expand_assign_view;

    @Bind(R.id.rlProgress)
    RelativeLayout rl_progress;

    @Bind(R.id.rlEmpty)
    RelativeLayout rl_empty;

    @Bind(R.id.rlError)
    RelativeLayout rl_error;

    @Bind(R.id.btnReload)
    Button btnReload;

    @Bind(R.id.btnReloadEmpty)
    Button btnReloadEmpty;

    @Bind(R.id.tv_rigth)
    TextView tv_right;

    private String communityId;
    private int empId;
    private int orderId;
    private int type;
    private List<OnlineListUser> list = new ArrayList<>();
    private AssignEmployeeListAdapter assignEmployeeListAdapter;
    private AssignEmployeePresent mPresenter;

    @Override
    protected void initViews(View view) {
        communityId = getIntent().getIntExtra("communityId", 0) + "";
        orderId = getIntent().getIntExtra("orderId",0);
        type = getIntent().getIntExtra(StringConfig.TYPE,0);
        expand_assign_view.setVisibility(View.VISIBLE);
        rl_progress.setVisibility(View.GONE);
        tv_title.setText("人员名单");
        expand_assign_view.setGroupIndicator(null);
        loadData();
        expand_assign_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                empId = list.get(i).members.get(i1).emp_id;
                if(type == 0){
                    Observable<RepairOrderAssign> observable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                            .createApi(HostApi.class)
                            .loadEmployeeList(empId, orderId)
                            .map(new Func1<RepairOrderAssign, RepairOrderAssign>() {
                                @Override
                                public RepairOrderAssign call(RepairOrderAssign repairOrderAssignListCommonResult) {
                                    return repairOrderAssignListCommonResult;
                                }
                            }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                    Subscriber<RepairOrderAssign> subscriber = new Subscriber<RepairOrderAssign>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ColpencilLogger.e("服务器错误" + e.getMessage());
                        }

                        @Override
                        public void onNext(RepairOrderAssign repairOrderAssign) {
                            int code = repairOrderAssign.getCode();
                            String message = repairOrderAssign.getMessage();
                            AssignMsg msg = new AssignMsg();
                            if(code == 0){
                                msg.setSuccess(true);
                                msg.setMessage(message);
                            } else {
                                msg.setSuccess(false);
                                msg.setMessage(message);
                            }
                            RxBus.get().post("assignSuccess", msg);
                        }
                    };
                    observable.subscribe(subscriber);
                } else if(type == 1){
                    Observable<RepairOrderAssign> observable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                            .createApi(HostApi.class)
                            .assignEmployee(empId, orderId)
                            .map(new Func1<RepairOrderAssign, RepairOrderAssign>() {
                                @Override
                                public RepairOrderAssign call(RepairOrderAssign repairOrderAssignListCommonResult) {
                                    return repairOrderAssignListCommonResult;
                                }
                            }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                    Subscriber<RepairOrderAssign> subscriber = new Subscriber<RepairOrderAssign>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ColpencilLogger.e("服务器错误" + e.getMessage());
                        }

                        @Override
                        public void onNext(RepairOrderAssign repairOrderAssign) {
                            int code = repairOrderAssign.getCode();
                            String message = repairOrderAssign.getMessage();
                            AssignMsg msg = new AssignMsg();
                            if(code == 0){
                                msg.setSuccess(true);
                                msg.setMessage(message);
                            } else {
                                msg.setSuccess(false);
                                msg.setMessage(message);
                            }
                            RxBus.get().post("assignSuccess", msg);
                        }
                    };
                    observable.subscribe(subscriber);
                }
                finish();
                return false;
            }
        });
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        mPresenter = new AssignEmployeePresent();
        return mPresenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    public void loadData(){
        mPresenter.getAssignEmployeeList(communityId);
    }


    @Override
    public void getAssignEmployeeList(ListCommonResult<OnlineListUser> assignEmployeeList) {
        int code = assignEmployeeList.getCode();
        ColpencilLogger.d("code = " + code);
        if(code == 2){
            //数据为空
            expand_assign_view.setVisibility(View.GONE);
            rl_empty.setVisibility(View.VISIBLE);
            rl_error.setVisibility(View.GONE);
            rl_progress.setVisibility(View.GONE);
            tv_empty.setText("未找到相关人员");
            btnReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadData();
                }
            });
        } else if(code == 3){
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }  else if(code == 6){
            ToastTools.showShort(this, false, assignEmployeeList.getMessage());
        } else {
            expand_assign_view.setVisibility(View.VISIBLE);
            rl_progress.setVisibility(View.GONE);
            rl_error.setVisibility(View.GONE);
            rl_empty.setVisibility(View.GONE);
        }
        list.addAll(assignEmployeeList.getData());
        assignEmployeeListAdapter = new AssignEmployeeListAdapter(list, this);
        expand_assign_view.setAdapter(assignEmployeeListAdapter);
        assignEmployeeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        expand_assign_view.setVisibility(View.GONE);
        rl_progress.setVisibility(View.GONE);
        rl_error.setVisibility(View.VISIBLE);
        rl_empty.setVisibility(View.GONE);
        btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
    }
}
