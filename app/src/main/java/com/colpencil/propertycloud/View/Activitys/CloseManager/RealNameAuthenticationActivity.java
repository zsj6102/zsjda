package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Present.CloseManager.RealNameAuthenticationPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Imples.RealNameAuthenticationView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * @author 汪 亮
 * @Description: 实名认证
 * @Email DramaScript@outlook.com
 * @date 2016/9/9
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_authen_real
)
public class RealNameAuthenticationActivity extends ColpencilActivity implements View.OnClickListener, RealNameAuthenticationView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.et_true_name)
    EditText et_true_name;

    @Bind(R.id.et_identy)
    EditText et_identy;

    @Bind(R.id.ll_man)
    LinearLayout ll_man;

    @Bind(R.id.iv_man)
    ImageView iv_man;

    @Bind(R.id.ll_women)
    LinearLayout ll_women;

    @Bind(R.id.iv_women)
    ImageView iv_women;

    @Bind(R.id.tv_chose_image1)
    TextView tv_chose_image1;

    @Bind(R.id.iv_shen)
    ImageView iv_shen;

    @Bind(R.id.tv_chose_image2)
    TextView tv_chose_image2;

    @Bind(R.id.iv_shou)
    ImageView iv_shou;

    @Bind(R.id.tv_submit)
    TextView tv_submit;

    private ImagePicker imagePicker;

    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ArrayList<ImageItem> selImageList = new ArrayList<>(); //当前选择的所有图片
    private ArrayList<ImageItem> selImageList2 = new ArrayList<>(); //当前选择的所有图片
    private Intent intent;
    private Intent intentPreview;

    private boolean flag = false;
    private RealNameAuthenticationPresent present;

    @Override
    protected void initViews(View view) {
        imagePicker = CluodApplaction.getInstance().getImagePicker();
        imagePicker.setSelectLimit(1);
        tv_title.setText("实名认证");
        ll_left.setOnClickListener(this);
        ll_man.setOnClickListener(this);
        ll_women.setOnClickListener(this);
        tv_chose_image1.setOnClickListener(this);
        tv_chose_image2.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        iv_shen.setOnClickListener(this);
        iv_shou.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new RealNameAuthenticationPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_man: //
                iv_man.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                iv_women.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                break;
            case R.id.ll_women: //
                iv_man.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                iv_women.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                break;
            case R.id.tv_chose_image1: //  选择身份证照片或者拍照
                intent = new Intent(this, ImageGridActivity.class);
                flag = false;
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            case R.id.iv_shen: //  这个只有选择后 预览用的
                //打开预览
                intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, selImageList);
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
                flag = false;
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
            case R.id.tv_chose_image2: //  选择手持身份证照片或者拍照
                intent = new Intent(this, ImageGridActivity.class);
                flag = true;
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            case R.id.iv_shou://  这个只有选择后 预览用的
                //打开预览
                intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, selImageList2);
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
                flag = true;
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
            case R.id.tv_submit: // 提交

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (flag == false) {
                    selImageList.addAll(images);
                } else {
                    selImageList2.addAll(images);
                }


                if (flag == false) { // 身份证的
                    if (selImageList.size() != 0) {
                        tv_chose_image1.setVisibility(View.GONE);
                        iv_shen.setVisibility(View.VISIBLE);
                        imagePicker.getImageLoader().displayImage(RealNameAuthenticationActivity.this, selImageList.get(0).path, iv_shen, 0, 0);
                    } else {
                        NotificationTools.show(RealNameAuthenticationActivity.this, "温馨提示", R.color.main_green, "您还未选择图片哦！");
                    }

                } else { // 手持身份证的
                    if (selImageList2.size() != 0) {
                        tv_chose_image2.setVisibility(View.GONE);
                        iv_shou.setVisibility(View.VISIBLE);
                        imagePicker.getImageLoader().displayImage(RealNameAuthenticationActivity.this, selImageList2.get(0).path, iv_shou, 0, 0);
                    } else {
                        NotificationTools.show(RealNameAuthenticationActivity.this, "温馨提示", R.color.main_green, "您还未选择图片哦！");
                    }

                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (flag == false) {
                    selImageList.clear();
                    selImageList.addAll(images);
                } else {
                    selImageList2.clear();
                    selImageList2.addAll(images);
                }
                if (flag == false) {
                    if (selImageList.size() != 0) {
                        imagePicker.getImageLoader().displayImage(RealNameAuthenticationActivity.this, selImageList.get(0).path, iv_shen, 0, 0);
                    } else {
                        tv_chose_image1.setVisibility(View.VISIBLE);
                        iv_shen.setVisibility(View.GONE);
                    }

                } else {
                    if (selImageList2.size() != 0) {
                        imagePicker.getImageLoader().displayImage(RealNameAuthenticationActivity.this, selImageList2.get(0).path, iv_shou, 0, 0);
                    } else {
                        tv_chose_image2.setVisibility(View.VISIBLE);
                        iv_shou.setVisibility(View.GONE);
                    }

                }
            }
        }
    }

    @Override
    public void info(boolean isSuccess) {

    }
}
