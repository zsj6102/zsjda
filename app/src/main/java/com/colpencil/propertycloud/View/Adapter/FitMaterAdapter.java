package com.colpencil.propertycloud.View.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * @Description: 材料管理
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/12/20
 */
public class FitMaterAdapter extends BaseAdapter {

    private List<FitmentStatue> materList;
    private Activity activity;
    private int wholeprogress;
    private int approveid;
    private String flag;

    public FitMaterAdapter(List<FitmentStatue> materList, Activity activity) {
        this.materList = materList;
        this.activity = activity;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.item_mater_child,null);
            viewHolder.chil_vv = convertView.findViewById(R.id.chil_vv);
            viewHolder.iv_check = (ImageView) convertView.findViewById(R.id.iv_check);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            viewHolder.tv_warn = (TextView) convertView.findViewById(R.id.tv_warn);
            viewHolder.ll_pic = (LinearLayout) convertView.findViewById(R.id.ll_pic);
            viewHolder.cg_pic = (ColpencilGridView) convertView.findViewById(R.id.cg_pic);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FitmentStatue fitmentStatue = materList.get(position);
        viewHolder.tv_content.setText(fitmentStatue.name);
        FitmentPicAdapter adapter = new FitmentPicAdapter(activity,fitmentStatue.listPic,R.layout.fit_pic,wholeprogress);
        viewHolder.cg_pic.setAdapter(adapter);
        if (wholeprogress<=1){
            viewHolder.tv_state.setVisibility(View.VISIBLE);
            if (position==3){
                flag = "qua";
                viewHolder.cg_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
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
            }else if (position==4){
                flag = "design";
                viewHolder.cg_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
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
            }

        }else {
            viewHolder.tv_state.setVisibility(View.INVISIBLE);
        }
        if (fitmentStatue.status==1){
            viewHolder.iv_check.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_check));
            viewHolder.tv_state.setText("修改");
        }else {
            viewHolder.iv_check.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_uncheck));
            viewHolder.tv_state.setText("上传");
        }

        if (position==3){
            viewHolder.ll_pic.setVisibility(View.VISIBLE);
            viewHolder.tv_warn.setVisibility(View.VISIBLE);
        }else if (position == 4){
            viewHolder.ll_pic.setVisibility(View.VISIBLE);
            viewHolder.tv_warn.setVisibility(View.GONE);
        }else {
            viewHolder.ll_pic.setVisibility(View.GONE);
            viewHolder.tv_warn.setVisibility(View.GONE);
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
                                 RxBus.get().post("update", rxBusMsg);
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

    public class ViewHolder{
        public View chil_vv;
        public ImageView iv_check;
        public TextView tv_content;
        public TextView tv_state;
        public TextView tv_warn;
        public ColpencilGridView cg_pic;
        public LinearLayout ll_pic;
    }

}
