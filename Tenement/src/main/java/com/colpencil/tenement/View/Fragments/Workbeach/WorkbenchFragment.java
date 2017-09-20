package com.colpencil.tenement.View.Fragments.Workbeach;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Bean.WorkBeach;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.Present.Home.WorkBeachPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.ToayTask.SignListActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.AdviceActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.EquipmentControlActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.GreenCleanActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.NeighborsActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.OwnerRepairActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.ScanCodeActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.SelectActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.SettingActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.VisitorActivity;
import com.colpencil.tenement.View.Adpaters.WorkBeachAdapter;
import com.colpencil.tenement.View.Imples.WorkbeachView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ClickUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;



/**
 * @author 汪 亮
 * @Description: 工作台
 * @Email DramaScript@outlook.com
 * @date 2016/6/30
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_workbench
)
public class WorkbenchFragment extends ColpencilFragment implements WorkbeachView {

    @Bind(R.id.ll_select)
    LinearLayout ll_select;

    @Bind(R.id.tv_say)
    TextView tv_say;

    @Bind(R.id.tv_village)
    TextView tv_village;

    @Bind(R.id.gl_workbeach)
    GridView gl_workbeach;

    @Bind(R.id.btn_open_door)
    Button btn_open_door;

    @Bind(R.id.iv_setting)
    ImageView iv_setting;

    private WorkBeachPresent present;
    private WorkBeachAdapter workBeachAdapter;
    private Intent intent;

    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private String greet;

    @Override
    public ColpencilPresenter getPresenter() {
        present = new WorkBeachPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mPresenter == null) return;
        ((WorkBeachPresent) mPresenter).loadWorkBeach();
    }

    @Override
    protected void initViews(View view) {
        greet = SharedPreferencesUtil.getInstance(getActivity()).getString("nick_name");
        if (!TextUtils.isEmpty(greet)){
            tv_say.setText("尊敬的"+greet+"，您好！");
        }
        tv_village.setText(SharedPreferencesUtil.getInstance(TenementApplication.getInstance()).getString(StringConfig.PLOTNAME));
        gl_workbeach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://水电抄表
                        intent = new Intent(getActivity(), ScanCodeActivity.class);
                        intent.putExtra("where",0);
                        startActivity(intent);
                        break;
                    case 1:  // 设备管理
                        intent = new Intent(getActivity(), EquipmentControlActivity.class);
                        startActivity(intent);
                        break;
                    case 2://绿化保洁
                        intent = new Intent(getActivity(), GreenCleanActivity.class);
                        intent.putExtra(StringConfig.TYPE, 0);
                        startActivity(intent);
                        break;
                    case 3://巡逻保安
                        intent = new Intent(getActivity(), GreenCleanActivity.class);
                        intent.putExtra(StringConfig.TYPE, 1);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(getActivity(), OwnerRepairActivity.class);
                        startActivity(intent);
                        break;
                    case 5: // 投诉建议
                        intent = new Intent(getActivity(), AdviceActivity.class);
                        startActivity(intent);
                        break;
                    case 6: // 访客管理
                        intent = new Intent(getActivity(), VisitorActivity.class);
                        startActivity(intent);
                        break;
                    case 7:  // 物业客服
                        ToastTools.showShort(getActivity(),"敬请期待");
                        break;
                    case 8:// 视频
                        ToastTools.showShort(getActivity(),"暂无摄像头");
                        break;
                    case 9:// 考勤签到
                        intent = new Intent(getActivity(), SignListActivity.class);
                        startActivity(intent);
                        break;
                    case 10: // 附近员工
                        intent = new Intent(getActivity(), NeighborsActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });

        ll_select.setOnClickListener((v -> {
            startActivity(new Intent(getActivity(), SelectActivity.class));
        }));

        observable = RxBus.get().register(StringConfig.FRESHDATA, RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 0) {
                    tv_village.setText(msg.getName());
                    tv_say.setText(msg.getGreet());
                }
            }
        };
        observable.subscribe(subscriber);

        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/9/29 设置界面
                boolean click = ClickUtils.isFastDoubleClick();
                if (click==false){
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                }
            }
        });
    }


    @Override
    public void loadWorkBeach(List<WorkBeach> workBeachList) {
        workBeachAdapter = new WorkBeachAdapter(getActivity(), workBeachList, R.layout.item_workbeach);
        gl_workbeach.setAdapter(workBeachAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(StringConfig.FRESHDATA,observable);
    }
}