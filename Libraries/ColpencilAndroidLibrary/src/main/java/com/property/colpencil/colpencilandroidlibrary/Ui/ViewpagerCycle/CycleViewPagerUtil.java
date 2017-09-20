package com.property.colpencil.colpencilandroidlibrary.Ui.ViewpagerCycle;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ImgTool;
import com.property.colpencil.colpencilandroidlibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * <p/>
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p/>
 * 作者：ZengFeng
 * <p/>
 * 描述：轮播方法
 */
public class CycleViewPagerUtil {

    private String[] mimageUrls;

    private CycleViewPager mcycleViewPager;

    private Context mcontext;

    private List<ADInfo> infos = new ArrayList<ADInfo>();

    private List<ImageView> views = new ArrayList<ImageView>();

    private ImgTool imgTool;

    public CycleViewPagerUtil(String[] imageUrls, CycleViewPager cycleViewPager, Context context) {
        this.mimageUrls = imageUrls;
        this.mcycleViewPager=cycleViewPager;
        this.mcontext=context;
        imgTool=ImgTool.getImgToolInstance(mcontext);
        initialize();
    }
    private void initialize() {//将获取的url数据储存成对象集合
        for(int i = 0; i < mimageUrls.length; i ++){
            ADInfo info = new ADInfo();
            info.setUrl(mimageUrls[i]);
            info.setContent("图片-->" + i );
            infos.add(info);
        }

        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(mcontext, infos.get(infos.size() - 1).getUrl(),imgTool));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(mcontext, infos.get(i).getUrl(),imgTool));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(mcontext, infos.get(0).getUrl(),imgTool));

        // 设置循环，在调用setData方法前调用
        mcycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        mcycleViewPager.setData(views, infos, mAdCycleViewListener);
        //设置轮播
        mcycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        mcycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        mcycleViewPager.setIndicatorCenter();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (mcycleViewPager.isCycle()) {
                position = position - 1;
//                Toast.makeText(MainActivity.this,
//                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
//                        .show();
            }

        }

    };

}
