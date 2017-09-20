package com.colpencil.propertycloud.View.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.Current;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Home.MaterialManagementActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @Description: 装修当前进度
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public class CurrentAdpater extends SuperAdapter<Current>{

    private Context context;

    private boolean check1 = false;
    private boolean check2 = false;

    public CurrentAdpater(Context context, List<Current> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBind(final SuperViewHolder holder, int viewType, int layoutPosition, Current item) {
        switch (item.state){
            case -1: // 已取消

                break;
            case 0:  // 待审核

                //状态的显示
                holder.setText(R.id.tv_state,"待审核");
                holder.setTextColor(R.id.tv_state,context.getResources().getColor(R.color.main_red));
                //原因的显示
                holder.setVisibility(R.id.tv_fees, View.GONE);
                //支付选择
                holder.setVisibility(R.id.rl_zhi_check, View.GONE);
                // tv1的显示
                holder.setVisibility(R.id.tv1, View.INVISIBLE);
                //tv2的显示
                holder.setVisibility(R.id.tv2, View.VISIBLE);
                holder.setText(R.id.tv2,"查看材料");
                holder.setBackgroundResource(R.id.tv2,R.drawable.rect_line);
                holder.setTextColor(R.id.tv2,context.getResources().getColor(R.color.text_dark33));
                holder.setOnClickListener(R.id.tv2, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MaterialManagementActivity.class);
                        context.startActivity(intent);
                    }
                });
                //tv3的显示
                holder.setText(R.id.tv3,"取消申请");
                holder.setBackgroundResource(R.id.tv3,R.drawable.rect_green);
                holder.setTextColor(R.id.tv3,context.getResources().getColor(R.color.white));
                //下面图片的显示
                holder.setVisibility(R.id.gl_pic,View.GONE);
                //最后一根线的显示
                holder.setVisibility(R.id.vv,View.GONE);
                // 下面的操作按钮显示隐藏
                holder.setVisibility(R.id.ll_do,View.VISIBLE);
                break;
            case 1:  // 已通过审核

                //状态的显示
                holder.setText(R.id.tv_state,"已审核");
                holder.setTextColor(R.id.tv_state,context.getResources().getColor(R.color.main_green));
                //原因的显示
                holder.setVisibility(R.id.tv_fees, View.GONE);
                //支付选择
                holder.setVisibility(R.id.rl_zhi_check, View.VISIBLE);
                holder.setOnClickListener(R.id.ll_mange_fees, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //管理费
                        if (check1==false){
                            holder.setImageDrawable(R.id.iv_check1,context.getDrawable(R.mipmap.check1));
                            holder.setTextColor(R.id.tv_manager,context.getResources().getColor(R.color.text_dark99));
                            check1 = true;
                        }else {
                            holder.setImageDrawable(R.id.iv_check1,context.getDrawable(R.mipmap.un_check));
                            holder.setTextColor(R.id.tv_manager,context.getResources().getColor(R.color.text_dark33));
                            check1 = false;
                        }

                    }
                });
                holder.setOnClickListener(R.id.ll_ya_fees, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 押金费
                        if (check2==false){
                            holder.setImageDrawable(R.id.iv_check2,context.getDrawable(R.mipmap.check1));
                            holder.setTextColor(R.id.tv_ya,context.getResources().getColor(R.color.text_dark99));
                            check2=true;
                        }else {
                            holder.setImageDrawable(R.id.iv_check2,context.getDrawable(R.mipmap.un_check));
                            holder.setTextColor(R.id.tv_ya,context.getResources().getColor(R.color.text_dark33));
                            check2=false;
                        }

                    }
                });
                // tv1的显示
                holder.setVisibility(R.id.tv1, View.INVISIBLE);
                //tv2的显示
                holder.setVisibility(R.id.tv2, View.INVISIBLE);
                holder.setText(R.id.tv2,"管理费支付");
                holder.setBackgroundResource(R.id.tv2,R.drawable.rect_green);
                holder.setTextColor(R.id.tv2,context.getResources().getColor(R.color.white));
                //tv3的显示
                holder.setVisibility(R.id.tv3, View.VISIBLE);
                holder.setText(R.id.tv3,"支付");
                holder.setBackgroundResource(R.id.tv3,R.drawable.rect_green);
                holder.setTextColor(R.id.tv3,context.getResources().getColor(R.color.white));
                //下面图片的显示
                holder.setVisibility(R.id.gl_pic,View.GONE);
                //最后一根线的显示
                holder.setVisibility(R.id.vv,View.GONE);
                // 下面的操作按钮显示隐藏
                holder.setVisibility(R.id.ll_do,View.VISIBLE);
                break;
            case 2: // 未通过审核

                //状态的显示
                holder.setText(R.id.tv_state,"未通过审核");
                holder.setTextColor(R.id.tv_state,context.getResources().getColor(R.color.main_red));
                //原因的显示
                holder.setVisibility(R.id.tv_fees, View.VISIBLE);
                //支付选择
                holder.setVisibility(R.id.rl_zhi_check, View.GONE);
                // tv1的显示
                holder.setVisibility(R.id.tv1, View.INVISIBLE);
                //tv2的显示
                holder.setVisibility(R.id.tv2, View.INVISIBLE);
                //tv3的显示
                holder.setText(R.id.tv3,"查看材料");
                holder.setBackgroundResource(R.id.tv3,R.drawable.rect_green);
                holder.setTextColor(R.id.tv3,context.getResources().getColor(R.color.white));
                //下面图片的显示
                holder.setVisibility(R.id.gl_pic,View.GONE);
                //最后一根线的显示
                holder.setVisibility(R.id.vv,View.GONE);
                // 下面的操作按钮显示隐藏
                holder.setVisibility(R.id.ll_do,View.VISIBLE);
                break;
            case 3:// 已支付

                //状态的显示
                holder.setText(R.id.tv_state,"已支付");
                holder.setTextColor(R.id.tv_state,context.getResources().getColor(R.color.main_green));
                //原因的显示
                holder.setVisibility(R.id.tv_fees, View.VISIBLE);
                //支付选择
                holder.setVisibility(R.id.rl_zhi_check, View.GONE);
                //下面图片的显示
                holder.setVisibility(R.id.gl_pic,View.GONE);
                //最后一根线的显示
                holder.setVisibility(R.id.vv,View.GONE);
                // 下面的操作按钮显示隐藏
                holder.setVisibility(R.id.ll_do,View.GONE);
                break;
            case 4:  // 装修中

                //状态的显示
                holder.setText(R.id.tv_state,"装修中");
                holder.setTextColor(R.id.tv_state,context.getResources().getColor(R.color.main_red));
                //原因的显示
                holder.setVisibility(R.id.tv_fees, View.GONE);
                //支付选择
                holder.setVisibility(R.id.rl_zhi_check, View.GONE);
                // tv1的显示
                holder.setVisibility(R.id.tv1, View.VISIBLE);
                holder.setText(R.id.tv1,"查看出入证");
                holder.setBackgroundResource(R.id.tv1,R.drawable.rect_line);
                //tv2的显示
                holder.setVisibility(R.id.tv2, View.VISIBLE);
                holder.setText(R.id.tv2,"添加材料");
                holder.setBackgroundResource(R.id.tv2,R.drawable.rect_line);
                holder.setTextColor(R.id.tv2,context.getResources().getColor(R.color.text_dark33));
                //tv3的显示
                holder.setVisibility(R.id.tv3,View.VISIBLE);
                holder.setText(R.id.tv3,"申请离场");
                holder.setBackgroundResource(R.id.tv3,R.drawable.rect_green);
                holder.setTextColor(R.id.tv3,context.getResources().getColor(R.color.white));
                //下面图片的显示
                holder.setVisibility(R.id.gl_pic,View.VISIBLE);
                //最后一根线的显示
                holder.setVisibility(R.id.vv,View.VISIBLE);
                // 下面的操作按钮显示隐藏
                holder.setVisibility(R.id.ll_do,View.VISIBLE);

                ColpencilGridView gl_pic = holder.getView(R.id.gl_pic);
                ColpencilLogger.e("size="+item.picList.size());
                CurrentPicAdapter adapter = new CurrentPicAdapter(context,item.picList,R.layout.item_pic);
                gl_pic.setAdapter(adapter);

                break;
            case 5:  // 已离场

                //状态的显示
                holder.setText(R.id.tv_state,"已离场");
                holder.setTextColor(R.id.tv_state,context.getResources().getColor(R.color.main_green));
                //原因的显示
                holder.setVisibility(R.id.tv_fees, View.GONE);
                //支付选择
                holder.setVisibility(R.id.rl_zhi_check, View.GONE);
                // tv1的显示
                holder.setVisibility(R.id.tv1, View.INVISIBLE);
                //tv2的显示
                holder.setVisibility(R.id.tv2, View.INVISIBLE);
                //tv3的显示
                holder.setText(R.id.tv3,"查看材料");
                holder.setBackgroundResource(R.id.tv3,R.drawable.rect_green);
                holder.setTextColor(R.id.tv3,context.getResources().getColor(R.color.white));
                //下面图片的显示
                holder.setVisibility(R.id.gl_pic,View.GONE);
                //最后一根线的显示
                holder.setVisibility(R.id.vv,View.GONE);
                // 下面的操作按钮显示隐藏
                holder.setVisibility(R.id.ll_do,View.VISIBLE);

                break;
        }
    }
}
