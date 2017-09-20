package com.colpencil.tenement.View.Adpaters.TodayTask;

import android.app.Activity;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Bean.TodayTask.TodayTaskItem;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 今日任务
 * @Email DramaScript@outlook.com
 * @date 2016/9/21
 */
public class TodayTaskItemAdapter extends SuperAdapter<TodayTaskItem> {

    private Activity context;
    private int mType;
    private EditText et_bei;
    private Button btn_tiao;
    private Button btn_submit;
    private LinearLayout ll_add;
    private Button btn_ok;
    private TextView tv_title;
    private DialogPlus dialogPlus;
    private static long currentTimeMillis;

    public TodayTaskItemAdapter(Activity context, List<TodayTaskItem> mDatas, int itemLayoutId, int type) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        this.mType = type;
        currentTimeMillis = System.currentTimeMillis();
    }


    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, TodayTaskItem item) {

        holder.setText(R.id.tv_time, item.getDate());
        holder.setText(R.id.tv_content, item.getTask());

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(item.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeInMillis = c.getTimeInMillis();
        if (mType == 0) {
            holder.setVisibility(R.id.tv_what, View.VISIBLE);
            holder.setText(R.id.tv_what, item.getPubTime() + "   " + item.getPubName());
        } else {
            holder.setVisibility(R.id.tv_what, View.VISIBLE);
            holder.setText(R.id.tv_what, item.getPubTime() + "   " + item.getPubName());
        }


        if (item.getType() == 0) {  // 标识未完成
            holder.setVisibility(R.id.btn_state, View.VISIBLE);
            holder.setVisibility(R.id.tv_statue, View.INVISIBLE);
            // 一定没有备注
            holder.setVisibility(R.id.ll_bei, View.GONE);
            holder.setVisibility(R.id.view, View.GONE);
            holder.setText(R.id.btn_state, "我已完成");
            if (timeInMillis < currentTimeMillis) { // 已经逾时了
                holder.setVisibility(R.id.tv_statue, View.VISIBLE);
                holder.setImageResource(R.id.tv_statue, R.mipmap.yushi);
            } else {
                holder.setVisibility(R.id.tv_statue, View.INVISIBLE);
            }
            holder.setBackgroundResource(R.id.btn_state, R.drawable.rect_line_green);
            holder.setTextColor(R.id.btn_state, mContext.getResources().getColor(R.color.color_5dc890));
            if (mType == 0) {  // 上级任务
                holder.setOnClickListener(R.id.btn_state, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showBei(0, item.getTaskId());
                    }
                });
            } else {
                holder.setOnClickListener(R.id.btn_state, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogTools.showLoding(context, "温馨提示", "正在加载中。。。");
                        taskFinsh(item.getTaskId() + "", "");
                    }
                });
            }

        } else {
            // 完成状态 一定是没有完成按钮  标记状态 是已完成
            holder.setVisibility(R.id.btn_state, View.INVISIBLE);
            holder.setImageResource(R.id.tv_statue, R.mipmap.fish);
            holder.setVisibility(R.id.tv_statue, View.VISIBLE);
            if (mType == 0) {
//                if (TextUtils.isEmpty(item.getFinishDesc())) {  // 判断有没有添加备注
//                    holder.setVisibility(R.id.ll_bei, View.GONE);
//                    holder.setVisibility(R.id.view, View.GONE);
//                    holder.setVisibility(R.id.btn_state, View.VISIBLE);
//                    holder.setText(R.id.btn_state, "添加备注");
//                    holder.setBackgroundResource(R.id.btn_state, R.drawable.rect_line_blue);
//                    holder.setTextColor(R.id.btn_state, mContext.getResources().getColor(R.color.mian_blue));
//                    holder.setOnClickListener(R.id.btn_state, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            showBei(0, item.getTaskId());
//                        }
//                    });
//                } else {
                SpannableString ss1;
                TextView tv_bei = holder.getView(R.id.tv_bei);
                if (TextUtils.isEmpty(item.getFinishDesc())) {
                    holder.setVisibility(R.id.ll_bei, View.VISIBLE);
                    holder.setVisibility(R.id.view, View.VISIBLE);
                    ss1 = new SpannableString("备        注：" + "暂无备注！");
                    ss1.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_drak3)),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_bei.setText(ss1);
                } else {
                    holder.setVisibility(R.id.ll_bei, View.VISIBLE);
                    holder.setVisibility(R.id.view, View.VISIBLE);
                    ss1 = new SpannableString("备        注：" + item.getFinishDesc());
                    ss1.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_drak3)),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_bei.setText(ss1);
                }

//                }
            } else { // 备忘录是没有备注的
                holder.setVisibility(R.id.ll_bei, View.GONE);
                holder.setVisibility(R.id.view, View.GONE);
            }
        }


    }

    private void taskFinsh(String remindId, String finishDesc) {
        HashMap<String, String> params = new HashMap<>();
        params.put("remindId", remindId);
        params.put("finishDesc", finishDesc);
        Observable<EntityResult> taskObser = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .taskFinsh(params)
                .map(new Func1<EntityResult, EntityResult>() {
                    @Override
                    public EntityResult call(EntityResult entityResult) {
                        return entityResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscriber<EntityResult> subscriber = new Subscriber<EntityResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误：" + e.getMessage());
                ToastTools.showShort(context, false, "请求失败！");
                DialogTools.dissmiss();
            }

            @Override
            public void onNext(EntityResult entityResult) {
                int code = entityResult.getCode();
                String message = entityResult.getMessage();
                switch (code) {
                    case 0:
                        RxBusMsg rxBusMsg = new RxBusMsg();
                        rxBusMsg.setType(0);
                        RxBus.get().post("ata", rxBusMsg);
                        break;
                    case 1:
                        ToastTools.showShort(context, false, message);
                        break;
                    case 3:
                        context.startActivity(new Intent(context, LoginActivity.class));
                        break;
                }
                if (mType == 0) {
                    dialogPlus.dismiss();
                }
                DialogTools.dissmiss();

            }


        };
        taskObser.subscribe(subscriber);
    }

    /**
     * 添加备注
     *
     * @param flag 0： 是添加备注   1 ： 查看备注
     */
    private void showBei(int flag, int taskId) {
        View view = View.inflate(mContext, R.layout.dialog_beizhu, null);
        dialogPlus = DialogPlus.newDialog(mContext)
                .setContentHolder(new ViewHolder(view))
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .create();
        dialogPlus.show();
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        et_bei = (EditText) view.findViewById(R.id.et_bei);
        btn_tiao = (Button) view.findViewById(R.id.btn_tiao);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        btn_ok = (Button) view.findViewById(R.id.btn_ok);
        ll_add = (LinearLayout) view.findViewById(R.id.ll_add);
        if (flag == 1) { // 查看备注
            ll_add.setVisibility(View.GONE);
            btn_ok.setVisibility(View.VISIBLE);
            tv_title.setText("查看备注");
            et_bei.setEnabled(false);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogPlus.dismiss();
                }
            });
        } else {
            ll_add.setVisibility(View.VISIBLE);
            btn_ok.setVisibility(View.GONE);
            tv_title.setText("添加备注");
            et_bei.setEnabled(true);
            btn_tiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogTools.showLoding(context, "温馨提示", "正在加载中。。。");
                    taskFinsh(taskId + "", "");
                }
            });
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2016/10/26 提交备注
                    String content = et_bei.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        ToastTools.showShort(context, false, "您还未填写备注！");
                        return;
                    }
                    DialogTools.showLoding(context, "温馨提示", "正在加载中。。。");
                    taskFinsh(taskId + "", content);
                }
            });
        }
    }

}
