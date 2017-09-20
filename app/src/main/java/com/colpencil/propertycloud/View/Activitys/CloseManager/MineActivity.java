package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.Mine;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.Present.CloseManager.MinePresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.Ui.BottomDialog;
import com.colpencil.propertycloud.View.Activitys.BaseActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.ChangePwdActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Imples.MineView;
import com.jph.takephoto.model.TResult;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.yinghe.whiteboardlib.bean.EventBean;

import org.feezu.liuli.timeselector.TimeSelector;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;
import retrofit2.http.Url;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 我的界面
 * @Email DramaScript@outlook.com
 * @date 2016/9/9
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_mine
)
public class MineActivity extends BaseActivity implements View.OnClickListener, MineView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_login_out)
    TextView tv_login_out;

    @Bind(R.id.rl_head)
    RelativeLayout rl_head;

    @Bind(R.id.ll_name)
    RelativeLayout ll_name;

    @Bind(R.id.ll_ni)
    RelativeLayout ll_ni;

    @Bind(R.id.rl_age)
    RelativeLayout rl_age;

    @Bind(R.id.rl_sex)
    RelativeLayout rl_sex;

    @Bind(R.id.rl_ture)
    RelativeLayout rl_ture;

    @Bind(R.id.rl_ju)
    RelativeLayout rl_ju;

    @Bind(R.id.cv_head)
    CircleImageView cv_head;

    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.tv_ni)
    TextView tv_ni;

    @Bind(R.id.tv_age)
    TextView tv_age;

    @Bind(R.id.tv_sex)
    TextView tv_sex;

    @Bind(R.id.tv_ture)
    TextView tv_ture;

    @Bind(R.id.tv_ju)
    TextView tv_ju;

    @Bind(R.id.rl_phone)
    RelativeLayout rl_phone;

    @Bind(R.id.tv_phone)
    TextView tv_phone;

    @Bind(R.id.rl_hoby)
    RelativeLayout rl_hoby;

    @Bind(R.id.tv_hoby)
    TextView tv_hoby;

    @Bind(R.id.rl_work)
    RelativeLayout rl_work;

    @Bind(R.id.tv_work)
    TextView tv_work;

    @Bind(R.id.rl_min)
    RelativeLayout rl_min;

    @Bind(R.id.tv_min)
    TextView tv_min;

    @Bind(R.id.rl_jin_phone)
    RelativeLayout rl_jin_phone;

    @Bind(R.id.tv_jin_phone)
    TextView tv_jin_phone;

    @Bind(R.id.rl_adre)
    RelativeLayout rl_adre;

    @Bind(R.id.tv_adre)
    TextView tv_adre;

    @Bind(R.id.rl_email)
    RelativeLayout rl_email;

    @Bind(R.id.tv_email)
    TextView tv_email;

    @Bind(R.id.rl_chan_adre)
    RelativeLayout rl_chan_adre;

    @Bind(R.id.tv_chan_adre)
    TextView tv_chan_adre;

    private Intent intent;

    private MinePresent present;

    private Observable<EventBean> change;

    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ArrayList<ImageItem> selImageList = new ArrayList<>(); //当前选择的所有图片

    private ImagePicker imagePicker;
    private boolean isProprietor;
    private String propertytel;
    private String servicetel;

    @Override
    protected void initViews(View view) {
        isProprietor = SharedPreferencesUtil.getInstance(this).getBoolean("isProprietor", false);
        propertytel = SharedPreferencesUtil.getInstance(this).getString("propertytel");
        servicetel = SharedPreferencesUtil.getInstance(this).getString("servicetel");
        imagePicker = CluodApplaction.getInstance().getImagePicker();
        imagePicker.setCrop(false);
        imagePicker.setSelectLimit(1);
        setStatecolor(getResources().getColor(R.color.white));
        tv_title.setText("资料设置");
        ll_left.setOnClickListener(this);
        rl_head.setOnClickListener(this);
        ll_name.setOnClickListener(this);
        ll_ni.setOnClickListener(this);
        rl_age.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_ture.setOnClickListener(this);
        rl_ju.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_hoby.setOnClickListener(this);
        rl_work.setOnClickListener(this);
        rl_min.setOnClickListener(this);
        rl_jin_phone.setOnClickListener(this);
        rl_adre.setOnClickListener(this);
        rl_email.setOnClickListener(this);
        tv_login_out.setOnClickListener(this);
        rl_chan_adre.setOnClickListener(this);

        present.getMineInfo();

        change = RxBus.get().register("change", EventBean.class);
        Subscriber<EventBean> subscriber = new Subscriber<EventBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EventBean eventBean) {
                if (eventBean.getFlag() == 0) {
                    present.getMineInfo();
                } else if (eventBean.getFlag() == 1) {
                    cheangeInfo(5, "1");
                } else if (eventBean.getFlag() == 2) {
                    cheangeInfo(5, "0");
                }else if (eventBean.getFlag() == 3){
                    cheangeInfo(6, eventBean.getAddress());
                }
            }
        };
        change.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new MinePresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
//        ToastTools.showShort(this,"文件大小="+new File(result.getImages().get(0).getCompressPath()).length());
        present.changeHead(new File(result.getImages().get(0).getCompressPath()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.rl_head:  // 头像
//                if (selImageList.size() != 0) {
//                    selImageList.clear();
//                }
//                intent = new Intent(this, ImageGridActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_SELECT);
                openSelect(true, 1);
                break;
            case R.id.ll_name: // 姓名
                intent = new Intent(this, ChangeMineInfoActivity.class);
                intent.putExtra("flag", 0);
                intent.putExtra("content", tv_name.getText().toString());
                startActivity(intent);
                break;
            case R.id.ll_ni: // 昵称
                intent = new Intent(this, ChangeMineInfoActivity.class);
                intent.putExtra("flag", 1);
                intent.putExtra("content", tv_ni.getText().toString());
                startActivity(intent);
                break;
//            case R.id.rl_change_pwd: // 改密码
//                intent = new Intent(this, ChangePwdActivity.class);
//                intent.putExtra("flag",2);
//                startActivity(intent);
//                break;
            case R.id.rl_age: // 年龄
                TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {

                    private Date date;

                    @Override
                    public void handle(String time) {
                        String birthday = time.substring(0, 9).replace("-", "-");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            date = sdf.parse(birthday);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        int age = caculateAge(date);
                        tv_age.setText(time.substring(0, 10));
                        cheangeInfo(2, time.substring(0, 10));
                    }
                }, "1915-11-29 21:54", TimeUtil.getTimeStrFromMillis(System.currentTimeMillis()));
                timeSelector.setMode(TimeSelector.MODE.YMD);
                timeSelector.show();
                break;
            case R.id.rl_sex: // 性别
                intent = new Intent(this, SelectSexActivity.class);
                intent.putExtra("sex", tv_sex.getText().toString());
                startActivity(intent);
              /*  final List<String> list = new ArrayList<>();
                list.add("男");
                list.add("女");
                new BottomDialog.Builder(this)
                        .setTitle("选择性别")
                        .setDataList(list)
                        .setPositiveText("确认")
                        .setNegativeText("取消")
                        .onPositive(new BottomDialog.ButtonCallback() {
                            @Override
                            public void onClick(@NonNull BottomDialog dialog) {
                                tv_sex.setText(list.get(dialog.position));
                                cheangeInfo(5, list.get(dialog.position));
                            }
                        })
                        .onNegative(new BottomDialog.ButtonCallback() {
                            @Override
                            public void onClick(@NonNull BottomDialog dialog) {

                            }
                        }).show();*/
                break;
            case R.id.rl_ture: // 实名认证
                intent = new Intent(this, RealNameAuthenticationActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_ju:  // 居住认证
                if (isProprietor) {
                    intent = new Intent(this, LiveInfoActivity.class);
                    startActivity(intent);
                } else {
                    WarnDialog.show(this, propertytel, servicetel);
                }
                break;
            case R.id.tv_login_out: // 退出登录
                String memberId = SharedPreferencesUtil.getInstance(this).getString("memberId");
                String comuId = SharedPreferencesUtil.getInstance(this).getString("comuid");
                if (TextUtils.isEmpty(comuId)) {
                    NotificationTools.show(this, "您还未选择小区！");
                } else {
                    present.loginOut(memberId, comuId);
                }
                break;
            case R.id.rl_hoby:// 爱好
                intent = new Intent(this, ChangeMineInfoActivity.class);
                intent.putExtra("flag", 4);
                intent.putExtra("content", tv_hoby.getText().toString());
                startActivity(intent);
                break;
            case R.id.rl_work:// 工作单位
                intent = new Intent(this, ChangeMineInfoActivity.class);
                intent.putExtra("flag", 5);
                intent.putExtra("content", tv_work.getText().toString());
                startActivity(intent);
                break;
            case R.id.rl_min:// 民族
//                intent = new Intent(this, ChangeMineInfoActivity.class);
//                intent.putExtra("flag", 6);
//                intent.putExtra("content", tv_jin_phone.getText().toString());
//                startActivity(intent);
//                String[] array = getResources().getStringArray(R.array.nation);
//                final List<String> arrayList = new ArrayList<>();
//                for (int i = 0; i < array.length; i++) {
//                    arrayList.add(array[i]);
//                }
//                new BottomDialog.Builder(this)
//                        .setTitle("选择民族")
//                        .setDataList(arrayList)
//                        .setPositiveText("确认")
//                        .setNegativeText("取消")
//                        .onPositive(new BottomDialog.ButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull BottomDialog dialog) {
//                                tv_min.setText(arrayList.get(dialog.position));
//                                cheangeInfo(6, arrayList.get(dialog.position));
//                            }
//                        })
//                        .onNegative(new BottomDialog.ButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull BottomDialog dialog) {
//
//                            }
//                        }).show();
                intent = new Intent(this, NationSelectActivity.class);
                intent.putExtra("content", tv_min.getText().toString());
                startActivity(intent);
                break;
            case R.id.rl_jin_phone:// 紧急电话
                intent = new Intent(this, ChangeMineInfoActivity.class);
                intent.putExtra("flag", 7);
                intent.putExtra("content", tv_jin_phone.getText().toString());
                startActivity(intent);
                break;
            case R.id.rl_adre:// 通讯地址
                intent = new Intent(this, ChangeMineInfoActivity.class);
                intent.putExtra("flag", 8);
                intent.putExtra("content", tv_adre.getText().toString());
                startActivity(intent);
                break;
            case R.id.rl_email:// 邮箱
                intent = new Intent(this, ChangeMineInfoActivity.class);
                intent.putExtra("flag", 9);
                intent.putExtra("content", tv_email.getText().toString());
                startActivity(intent);
                break;
            case R.id.rl_chan_adre:// 常住地址
                intent = new Intent(this, ChangeMineInfoActivity.class);
                intent.putExtra("flag", 10);
                intent.putExtra("content", tv_chan_adre.getText().toString());
                startActivity(intent);
                break;
        }
    }

    /**
     * 计算年龄   格式：1980-01-01
     *
     * @param birthday
     * @return
     */
    private int caculateAge(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

    @Override
    public void loginOut(ResultInfo resultInfo) {
        int code = resultInfo.code;
        String message = resultInfo.message;
        if (code == 0) {
            // TODO: 2016/9/13 退出成功
            SharedPreferencesUtil.getInstance(this).setBoolean("isLogin", false);
            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("mobile", "");
            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("passWord", "");
            SharedPreferencesUtil.getInstance(this).setBoolean("isProprietor", false);
            ColpencilFrame.getInstance().finishAllActivity();
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            NotificationTools.show(this, message);
        }
    }

    @Override
    public void getMineInfo(ResultInfo<Mine> mineResultInfo) {
        int code = mineResultInfo.code;
        Mine data = mineResultInfo.data;
        String message = mineResultInfo.message;
        switch (code) {
            case 0:
                //创建默认的ImageLoader配置参数
//                ImageLoader.getInstance().displayImage(data.face, cv_head);
                Glide.with(this)
                        .load(data.face)
                        .into(cv_head);
                tv_name.setText(data.name);
                tv_ni.setText(data.nickname);
                tv_age.setText(data.birthday);
                if (data.sex == 0) {
                    tv_sex.setText("女");
                } else {
                    tv_sex.setText("男");
                }
                if (data.isApproved == true) {
                    tv_ture.setText("已认证");
                } else {
                    tv_ture.setText("未验证");
                }
                tv_phone.setText(data.mobile);
                tv_hoby.setText(data.hobby);
                tv_work.setText(data.workunit);
                tv_min.setText(data.nation);
                tv_jin_phone.setText(data.urgent_tel);
                tv_adre.setText(data.address);
                tv_email.setText(data.email);
                tv_chan_adre.setText(data.live_address);
                tv_ju.setText(data.my_house);
                break;
            case 1:
                ToastTools.showShort(this, false, message);
                break;
            case 3:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    @Override
    public void changeHead(ResultInfo resultInfo) {
        int code = resultInfo.code;
        String message = resultInfo.message;
        switch (code) {
            case 0:
//                ToastTools.showShort(this, "修改成功！");
                present.getMineInfo();
                break;
            case 1:
                NotificationTools.show(this, message);
                break;
            case 3:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("change", change);
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(0);
        RxBus.get().post("mine", rxBusMsg);
        System.gc();
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selImageList.addAll(images);
                if (selImageList.size() != 0) {
                    present.changeHead(selImageList.get(0), new File(selImageList.get(0).path));
                    *//*Luban.get(this)
                            .load(new File(selImageList.get(0).path))
                            .putGear(Luban.THIRD_GEAR)
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                    ColpencilLogger.e("开始压缩中。。。");
                                }

                                @Override
                                public void onSuccess(File file) {


                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            }).launch();
//                    present.changeHead(selImageList.get(0), new File(selImageList.get(0).path));
                  *//**//*  Bitmap bitmap = BitmapFactory.decodeFile(selImageList.get(0).path);
                    cv_head.setImageBitmap(bitmap);*//*
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {

            }
        }
    }*/

    private void cheangeInfo(int flag, String content) {
        HashMap<String, String> map = new HashMap<>();
        switch (flag) {
            case 2: //  年龄
                map.put("birthday", content);
                break;
            case 6://  民族
                map.put("nation", content);
                break;
            case 5:
                map.put("sex", content);
                break;
        }
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .changeInfo(map)
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
                        ColpencilLogger.e("服务器错误信息" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        int code = resultInfo.code;
                        String message = resultInfo.message;
                        switch (code) {
                            case 0:
//                                ToastTools.showShort(MineActivity.this, "修改成功！");
                                present.getMineInfo();
                                break;
                            case 1:
                                NotificationTools.show(MineActivity.this, message);
                                break;
                            case 3:
                                startActivity(new Intent(MineActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }
}
