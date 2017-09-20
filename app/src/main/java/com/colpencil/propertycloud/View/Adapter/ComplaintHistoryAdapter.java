package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.ComplaintHistroy;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.CloseManager.ComplainDetailActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.NewFeedBackActivity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

import static com.colpencil.propertycloud.R.id.ll_phone;

/**
 * @author 汪 亮
 * @Description: 历史投诉
 * @Email DramaScript@outlook.com
 * @date 2016/10/8
 */
public class ComplaintHistoryAdapter extends SuperAdapter<ComplaintHistroy> {

    private int type;
    private PlayerListener listener;

    public ComplaintHistoryAdapter(Context context, List<ComplaintHistroy> items, int layoutResId, int type) {
        super(context, items, layoutResId);
        this.type = type;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final ComplaintHistroy item) {
        holder.setText(R.id.tv_order_id, "处理单号：" + item.getSugcode());
        holder.setText(R.id.repair_record_time, "记录时间：" + item.getSugtm());
        holder.setText(R.id.tv_complain_type, "类型：" + item.getClassname());
        holder.setText(R.id.tv_descible, "描述：" + item.getDetaildesc());
        if (((item.getAudio_duration() / 60) % 60) > 0) {
            holder.setText(R.id.tv_music, (item.getAudio_duration() / 60) % 60 + "." + item.getAudio_duration() % 60 + "”");
        } else {
            holder.setText(R.id.tv_music, (item.getAudio_duration() % 60 + "”"));
        }
        final ImageView iv_music = holder.getView(R.id.iv_music);
        TextView tv_state = holder.getView(R.id.tv_state);
        tv_state.setText(item.getStatu());
        int state = item.getState();
        if (state == 0) {       //待指派
            tv_state.setTextColor(mContext.getResources().getColor(R.color.main_red));
            holder.setVisibility(R.id.tv_chu, View.GONE);
            holder.setVisibility(R.id.tv_phone, View.GONE);
            holder.setVisibility(R.id.divide, View.GONE);
            holder.setVisibility(R.id.divide1, View.GONE);
            holder.setVisibility(R.id.ll_property, View.GONE);
            holder.setVisibility(R.id.ll_owner, View.GONE);
            holder.setVisibility(R.id.ll_phone, View.GONE);
        } else if (state == 1) {       //已指派
            tv_state.setTextColor(mContext.getResources().getColor(R.color.text_red));
            holder.setText(R.id.tv_chu, "处理员工：" + item.getEmpnm());
            holder.setText(R.id.tv_phone, "手机号：" + item.getTel());
            holder.setVisibility(R.id.tv_chu, View.VISIBLE);
            holder.setVisibility(R.id.tv_phone, View.VISIBLE);
            holder.setVisibility(R.id.divide, View.GONE);
            holder.setVisibility(R.id.divide1, View.GONE);
            holder.setVisibility(R.id.ll_property, View.GONE);
            holder.setVisibility(R.id.ll_owner, View.GONE);
            holder.setVisibility(R.id.ll_phone, View.VISIBLE);
        } else if (state == 2) {       //处理中
            tv_state.setTextColor(mContext.getResources().getColor(R.color.color_ffb651));
            holder.setText(R.id.tv_chu, "处理员工：" + item.getEmpnm());
            holder.setText(R.id.tv_phone, "手机号：" + item.getTel());
            holder.setVisibility(R.id.tv_chu, View.VISIBLE);
            holder.setVisibility(R.id.tv_phone, View.VISIBLE);
            holder.setVisibility(R.id.divide, View.GONE);
            holder.setVisibility(R.id.divide1, View.GONE);
            holder.setVisibility(R.id.ll_property, View.GONE);
            holder.setVisibility(R.id.ll_owner, View.GONE);
            holder.setVisibility(R.id.ll_phone, View.VISIBLE);
        } else {       //已完成
            tv_state.setTextColor(mContext.getResources().getColor(R.color.text_green));
            holder.setText(R.id.tv_chu, "处理员工：" + item.getEmpnm());
            holder.setText(R.id.tv_phone, "手机号：" + item.getTel());
            holder.setText(R.id.property_agree, "物业意见：" + item.getProperty_opinion());
            holder.setVisibility(R.id.ll_phone, View.VISIBLE);
            holder.setVisibility(R.id.tv_chu, View.VISIBLE);
            holder.setVisibility(R.id.tv_phone, View.VISIBLE);
            holder.setVisibility(R.id.divide, View.VISIBLE);
            holder.setVisibility(R.id.divide1, View.VISIBLE);
            holder.setVisibility(R.id.ll_property, View.VISIBLE);
            holder.setVisibility(R.id.ll_owner, View.VISIBLE);
            if (item.getProperty_opinion().length() > 18) {
                holder.setVisibility(R.id.property_more, View.VISIBLE);
            } else {
                holder.setVisibility(R.id.property_more, View.GONE);
            }
            if (item.getIsfedbck() == 0) {      //未反馈
                holder.setText(R.id.owner_agree, "");
                holder.setVisibility(R.id.tv_kui, View.VISIBLE);
                holder.setVisibility(R.id.owner_more, View.GONE);
                holder.setVisibility(R.id.owner_agree, View.GONE);
                holder.setOnClickListener(R.id.tv_kui, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, NewFeedBackActivity.class);
                        intent.putExtra("orderid", item.getOrderid() + "");
                        intent.putExtra("type", "1");
                        mContext.startActivity(intent);
                        listener.stop();
                    }
                });
            } else {        //已反馈
                holder.setText(R.id.owner_agree, item.getDetail_description());
                holder.setVisibility(R.id.tv_kui, View.GONE);
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
                Intent intent = new Intent(mContext, ComplainDetailActivity.class);
                intent.putExtra("data", item);
                intent.putExtra("type", type);
                mContext.startActivity(intent);
            }
        });
        if (TextUtils.isEmpty(item.getUrl_audio())) {
            holder.getView(R.id.ll_rudio).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.ll_rudio).setVisibility(View.VISIBLE);
        }
        holder.setOnClickListener(R.id.ll_rudio, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.play(item.getUrl_audio(), iv_music);
            }
        });
        holder.setOnClickListener(R.id.ll_phone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + item.getTel()));
                mContext.startActivity(intent);
            }
        });
    }

    public void setListener(PlayerListener listener) {
        this.listener = listener;
    }

    public interface PlayerListener {
        void play(String path, ImageView view);

        void stop();
    }

}
