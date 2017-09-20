package com.colpencil.propertycloud.View.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.FitmentStatue;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;

import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MaterProgressAdapter extends BaseAdapter {

    private Activity activity;
    private List<FitmentStatue> materList;
    private int wholeprogress;
    private String flag;
    private int approveid;
    private int posit;

    public MaterProgressAdapter(Activity activity, List<FitmentStatue> materList) {
        this.activity = activity;
        this.materList = materList;
    }

    public void setApproveid(int approveid) {
        this.approveid = approveid;
    }

    public void setWholeprogress(int wholeprogress) {
        this.wholeprogress = wholeprogress;
    }

    @Override
    public int getCount() {
        return materList.size();
    }

    @Override
    public Object getItem(int position) {
        return materList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position2, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.item_mater_progress,null);
            viewHolder.iv_check = (ImageView) convertView.findViewById(R.id.iv_check);
            viewHolder.tv_do = (TextView) convertView.findViewById(R.id.tv_do);
            viewHolder.tv_materail = (TextView) convertView.findViewById(R.id.tv_materail);
            viewHolder.tv_warn = (TextView) convertView.findViewById(R.id.tv_warn);
            viewHolder.ll_fit_bottom = (LinearLayout) convertView.findViewById(R.id.ll_fit_bottom);
            viewHolder.cl_mater1 = (ColpencilGridView) convertView.findViewById(R.id.cl_mater1);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FitmentStatue fitmentStatue = materList.get(position2);
        FitmentPicAdapter adapter = new FitmentPicAdapter(activity,fitmentStatue.listPic,R.layout.fit_pic,wholeprogress);
        viewHolder.cl_mater1.setAdapter(adapter);
        viewHolder.tv_materail.setText(fitmentStatue.name);
        if (wholeprogress<=1){
            viewHolder.tv_do.setVisibility(View.VISIBLE);
            viewHolder.cl_mater1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    ColpencilLogger.e("position2="+position2);
                    if (position2==3){
                        flag = "qua";
                        ColpencilLogger.e("flag="+flag);
                    }else {
                        flag = "design";
                        ColpencilLogger.e("flag="+flag);
                    }
                    MaterialDialog show = new MaterialDialog.Builder(activity)
                            .title("温馨提示")
                            .content("您确定要删除改张图片吗？")
                            .positiveText("是")
                            .negativeText("否")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    delPic(approveid+"",flag,position+1);
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            })
                            .show();
                }
            });
        }else {
            viewHolder.tv_do.setVisibility(View.INVISIBLE);
        }
        if (fitmentStatue.status==1){
            viewHolder.iv_check.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_check));
            viewHolder.tv_do.setText("修改");
        }else {
            viewHolder.iv_check.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_uncheck));
            viewHolder.tv_do.setText("填写");
        }
        if (position2==4){
            viewHolder.ll_fit_bottom.setVisibility(View.VISIBLE);
            viewHolder.tv_warn.setVisibility(View.GONE);
        }else if (position2==3){
            viewHolder.tv_warn.setVisibility(View.VISIBLE);
            viewHolder.ll_fit_bottom.setVisibility(View.VISIBLE);
        }else {
            viewHolder.ll_fit_bottom.setVisibility(View.GONE);
        }
        return convertView;
    }

    private void delPic(String approveId,String flag,int imageIndex){
        HashMap<String, String> map = new HashMap<>();
        map.put("approveId", approveId);
        map.put("flag", flag);
        map.put("imageIndex", imageIndex+"");
        RetrofitManager.getInstance(1, activity, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .delAttachment(map)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        String message = resultInfo.message;
                        switch (resultInfo.code) {
                            case 0:
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("mater", rxBusMsg);
                                break;
                            case 1:
                                ToastTools.showShort(activity,message);
                                break;
                            case 3:
                                activity.startActivity(new Intent(activity, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    class ViewHolder{
        public ImageView iv_check;
        public TextView tv_materail;
        public TextView tv_do;
        public TextView tv_warn;
        public LinearLayout ll_fit_bottom;
        public ColpencilGridView cl_mater1;
    }
}