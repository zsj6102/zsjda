package com.colpencil.propertycloud.View.Activitys.Common;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.colpencil.propertycloud.Bean.ImagePreview;
import com.colpencil.propertycloud.R;
import com.google.gson.Gson;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 汪 亮
 * @Description: 图片预览界面
 * @Email DramaScript@outlook.com
 * @date 2016/9/23
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_imagepreview
)
public class ImagePreviewActivity extends ColpencilActivity {

    @Bind(R.id.banner_main_accordion)
    ViewPager banner_main_accordion;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;
    @Bind(R.id.tv_rigth)
    TextView tv_rigth;
    private ImagePreview imagePreview;
    private Gson gson;
    private String imageList;
    private int position;

    private int resultCode = 101;
    private int resultCode2 = 102;
    private Intent intent;
    private String toJson;
    private MyViewPagerAdapter adapter;

    private List<View> listView = new ArrayList<>();

    @OnClick(R.id.ll_left)
    void backOnClick() {
        finish();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case 1:
//                    banner_main_accordion.notifyAll();
                    break;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        tv_rigth.setVisibility(View.VISIBLE);
        tv_rigth.setText("");
        tv_rigth.setBackground(getResources().getDrawable(R.mipmap.del));
        imageList = getIntent().getStringExtra("imageList");
        position = getIntent().getIntExtra("position", 0);
        gson = new Gson();
        imagePreview = gson.fromJson(imageList, ImagePreview.class);
        tv_title.setText(position + 1 + "/" + imagePreview.imageList.size());
        for (int i = 0; i < imagePreview.imageList.size(); i++) {
            listView.add(View.inflate(this, R.layout.item_imageview, null));
        }
        adapter = new MyViewPagerAdapter(imagePreview.imageList);
        banner_main_accordion.setAdapter(adapter);
        banner_main_accordion.setCurrentItem(position);
        banner_main_accordion.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tv_title.setText(position + 1 + "/" + imagePreview.imageList.size());
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        ll_rigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog show = new MaterialDialog.Builder(ImagePreviewActivity.this)
                        .title("温馨提示")
                        .content("要删除这张照片吗?")
                        .positiveText("是")
                        .negativeText("否")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (imagePreview.imageList.size() == 1) {
                                    imagePreview.imageList.remove(banner_main_accordion.getVerticalScrollbarPosition());
                                    intent = new Intent();
                                    toJson = gson.toJson(imagePreview);
                                    intent.putExtra("change", toJson);
                                    setResult(resultCode, intent);
                                    finish();
                                } else {
                                    imagePreview.imageList.remove(banner_main_accordion.getVerticalScrollbarPosition());
                                    ColpencilLogger.e("size=" + imagePreview.imageList.size());
                                    adapter.notifyDataSetChanged();
                                    intent = new Intent();
                                    toJson = gson.toJson(imagePreview);
                                    intent.putExtra("change", toJson);
                                    setResult(resultCode, intent);
                                }

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
        intent = new Intent();
        setResult(resultCode2, intent);

    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    class MyViewPagerAdapter extends PagerAdapter {

        private List<String> imageList;

        public MyViewPagerAdapter(List<String> imageList) {
            this.imageList = imageList;
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = listView.get(position);
            ImageView iv = (ImageView) view.findViewById(R.id.iv);
            Glide.with(ImagePreviewActivity.this)
                    .load(imageList.get(position))
                    .placeholder(R.mipmap.default_image)
                    .into(iv);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(listView.get(position));//删除页卡
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
