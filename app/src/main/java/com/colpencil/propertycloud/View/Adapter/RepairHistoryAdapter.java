package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.RepairDetailVo;
import com.colpencil.propertycloud.Bean.RepairHistory;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.CloseManager.NewFeedBackActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.RepairDetailActivity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 报修历史
 * @Email DramaScript@outlook.com
 * @date 2016/10/11
 */
public class RepairHistoryAdapter extends SuperAdapter<RepairHistory> {

    private PlayListener listener;

    public RepairHistoryAdapter(Context context, List<RepairHistory> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final RepairHistory item) {
        TextView tv_state = holder.getView(R.id.tv_state);
        holder.setText(R.id.tv_order_id, "报修单号：" + item.getRepair_code());
        if (item.getRepair_type() == 0) {
            holder.setText(R.id.repair_type, "报修类型：家庭报修");
        } else {
            holder.setText(R.id.repair_type, "报修类型：公共报修");
        }
        holder.setText(R.id.tv_time, "报修时间：" + item.getAddtime());
        holder.setText(R.id.tv_descible, "描述：" + item.getDescription());
        holder.setText(R.id.tv_state, item.getStatu());
        holder.setText(R.id.property_agree, "物业意见：" + item.getProperty_opinion());
        if (TextUtils.isEmpty(item.getUrl_audio())) {
            holder.setVisibility(R.id.ll_rudio, View.GONE);
        } else {
            holder.setVisibility(R.id.ll_rudio, View.VISIBLE);
        }
        if (((item.getAudio_duration() / 60) % 60) > 0) {
            holder.setText(R.id.tv_music, (item.getAudio_duration() / 60) % 60 + "." + item.getAudio_duration() % 60 + "”");
        } else {
            holder.setText(R.id.tv_music, (item.getAudio_duration() % 60 + "”"));
        }
        int state = item.getState();
        if (state == 0) {
            tv_state.setTextColor(mContext.getResources().getColor(R.color.main_red));
            holder.setVisibility(R.id.ll_property, View.GONE);
            holder.setVisibility(R.id.ll_owner, View.GONE);
            holder.setVisibility(R.id.tv_phone, View.GONE);
            holder.setVisibility(R.id.tv_chu, View.GONE);
            holder.setVisibility(R.id.ll_phone, View.GONE);
        } else if (state == 1) {
            tv_state.setTextColor(mContext.getResources().getColor(R.color.text_red));
            holder.setVisibility(R.id.ll_property, View.GONE);
            holder.setVisibility(R.id.ll_owner, View.GONE);
            holder.setVisibility(R.id.tv_phone, View.VISIBLE);
            holder.setVisibility(R.id.tv_chu, View.VISIBLE);
            holder.setVisibility(R.id.ll_phone, View.VISIBLE);
            holder.setText(R.id.tv_chu, "处理员工：" + item.getEmpnm());
            holder.setText(R.id.tv_phone, "手机号：" + item.getMobile());
        } else if (state == 2) {
            tv_state.setTextColor(mContext.getResources().getColor(R.color.color_ffb651));
            holder.setVisibility(R.id.ll_property, View.GONE);
            holder.setVisibility(R.id.ll_owner, View.GONE);
            holder.setVisibility(R.id.tv_phone, View.VISIBLE);
            holder.setVisibility(R.id.tv_chu, View.VISIBLE);
            holder.setVisibility(R.id.ll_phone, View.VISIBLE);
            holder.setText(R.id.tv_chu, "处理员工：" + item.getEmpnm());
            holder.setText(R.id.tv_phone, "手机号：" + item.getMobile());
        } else {
            tv_state.setTextColor(mContext.getResources().getColor(R.color.text_green));
            holder.setVisibility(R.id.ll_property, View.VISIBLE);
            holder.setVisibility(R.id.ll_owner, View.VISIBLE);
            holder.setVisibility(R.id.tv_phone, View.VISIBLE);
            holder.setVisibility(R.id.tv_chu, View.VISIBLE);
            holder.setVisibility(R.id.ll_phone, View.VISIBLE);
            holder.setText(R.id.tv_chu, "处理员工：" + item.getEmpnm());
            holder.setText(R.id.tv_phone, "手机号：" + item.getMobile());
            if (item.getProperty_opinion().length() > 18) {
                holder.setVisibility(R.id.property_more, View.VISIBLE);
            } else {
                holder.setVisibility(R.id.property_more, View.GONE);
            }
            if (item.getIsfedbck() == 0) {       //未反馈
                holder.setVisibility(R.id.tv_kui, View.VISIBLE);
                holder.setVisibility(R.id.owner_agree, View.GONE);
                holder.setVisibility(R.id.owner_more, View.GONE);
                holder.setText(R.id.owner_agree, "");
                // TODO: 2016/10/12 意见反馈界面
                holder.setOnClickListener(R.id.tv_kui, new View.OnClickListener() {     //业主反馈
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, NewFeedBackActivity.class);
                        intent.putExtra("orderid", item.getOrderid() + "");
                        intent.putExtra("type", "0");
                        mContext.startActivity(intent);
                    }
                });
            } else {       //已反馈
                holder.setVisibility(R.id.tv_kui, View.GONE);
                holder.setText(R.id.owner_agree, item.getDetail_description());
                holder.setVisibility(R.id.owner_agree, View.VISIBLE);
                if (item.getDetail_description().length() > 18) {
                    holder.setVisibility(R.id.owner_more, View.VISIBLE);
                } else {
                    holder.setVisibility(R.id.owner_more, View.GONE);
                }
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RepairDetailActivity.class);
                intent.putExtra("data", item);
                mContext.startActivity(intent);
                listener.stop();
            }
        });
        final ImageView imageView = holder.getView(R.id.iv_music);
        holder.setOnClickListener(R.id.ll_rudio, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.play(item.getUrl_audio(), imageView);
            }
        });
        holder.setOnClickListener(R.id.ll_phone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + item.getMobile()));
                mContext.startActivity(intent);
            }
        });
    }

    public void setListener(PlayListener listener) {
        this.listener = listener;
    }

    public interface PlayListener {
        void play(String path, ImageView imageView);

        void stop();
    }
}
