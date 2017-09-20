package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.colpencil.propertycloud.Bean.ModuleInfo;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.CommonAdapter;
import com.colpencil.propertycloud.Tools.CommonViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.colpencil.propertycloud.R.id.image;
import static com.colpencil.propertycloud.R.id.item;

/**
 * Created by xiaohuihui on 2017/1/12.
 */

public class ModuleAdapter extends CommonAdapter<ModuleInfo> {

    private ImageView imageView;

    public ModuleAdapter(Context context, List<ModuleInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, ModuleInfo item, int position) {
        imageView = helper.getView(R.id.item_image);
        ImageLoader.getInstance().displayImage(item.icon, imageView);
        helper.setText(R.id.item_text, item.name);
        if (item.code.equals("_pay") || item.code.equals("_huibao")) {
            if (item.feesLstNum != 0) {
                helper.getView(R.id.item_point).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.item_point).setVisibility(View.GONE);
            }
        } else {
            helper.getView(R.id.item_point).setVisibility(View.GONE);
        }
    }

    public void shake() {
        for (int i = 0; i < mDatas.size(); i++) {
            ModuleInfo item = mDatas.get(i);
            if (item.code.equals("_choujiang")) {
                Animation rotate = new RotateAnimation(-30, 30,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(20);
                rotate.setRepeatMode(Animation.REVERSE);
                rotate.setRepeatCount(Animation.INFINITE);
                imageView.startAnimation(rotate);
                CountDownTimer timer = new CountDownTimer(120000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        imageView.clearAnimation();
                    }
                };
                timer.start();
                notifyDataSetChanged();
            }
        }
    }

    public void clearAnim() {
        for (int i = 0; i < mDatas.size(); i++) {
            if (imageView != null) {
                imageView.clearAnimation();
            }
        }
    }
}
