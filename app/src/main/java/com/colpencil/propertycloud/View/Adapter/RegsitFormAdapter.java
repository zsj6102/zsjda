package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Bean.orm.RegsitForm;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Home.MaterialManagementActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description:  装修人员登记表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/14
 */
public class RegsitFormAdapter extends BaseAdapter {

    private Context context;
    private List<RegsitForm> formList;

    public RegsitFormAdapter(Context context, List<RegsitForm> formList) {
        this.context = context;
        this.formList = formList;
    }

    @Override
    public int getCount() {
        return formList.size();
    }

    @Override
    public Object getItem(int position) {
        return formList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context,R.layout.item_regsitform,null);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_tel = (TextView) convertView.findViewById(R.id.tv_tel);
            viewHolder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            viewHolder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
            viewHolder.iv_zhu = (ImageView) convertView.findViewById(R.id.iv_zhu);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final RegsitForm regsitForm = formList.get(position);
        viewHolder.tv_name.setText(regsitForm.name);
        viewHolder.tv_tel.setText(regsitForm.tel);
        viewHolder.tv_id.setText(regsitForm.idNum);
        ImageLoader.getInstance().displayImage(regsitForm.photo,viewHolder.iv_pic);
        if (regsitForm.type){
            viewHolder.iv_zhu.setVisibility(View.VISIBLE);
            viewHolder.iv_zhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    zhuXiao(regsitForm.wkId);
                }
            });
        }else {
            viewHolder.iv_zhu.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder{
        public TextView tv_name;
        public TextView tv_tel;
        public TextView tv_id;
        public ImageView iv_pic;
        public ImageView iv_zhu;
    }

    /**
     * 注销
     */
    private void zhuXiao(int wkId){
        HashMap<String,String> map = new HashMap<>();
        map.put("wkId",wkId+"");
        RetrofitManager.getInstance(1,context,HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .delPersonInfoById(map)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("请求错误日志："+e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        int code = resultInfo.code;
                        String message = resultInfo.message;
                        switch (code){
                            case 0:
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(1);
                                RxBus.get().post("del",rxBusMsg);
                                break;
                            case 1:
                                ToastTools.showShort(context,message);
                                break;
                            case 3:
                                context.startActivity(new Intent(context, LoginActivity.class));
                                break;
                        }
                    }
                });
    }


}
