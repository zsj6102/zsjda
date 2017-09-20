package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.Ad;
import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.PayFees;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.Present.Home.PayFeesPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.GlideLoader;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Imples.PayFeesView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.badgeview.BGABadgeView;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 物业缴费
 * @Email DramaScript@outlook.com
 * @date 2016/9/6
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_payfees
)
public class PayFeesActivity extends ColpencilActivity implements PayFeesView, View.OnClickListener {

    @Bind(R.id.banner)
    Banner banner;

    @Bind(R.id.ll_scuess)
    LinearLayout ll_scuess;

    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;

    @Bind(R.id.rlEmpty)
    RelativeLayout rlEmpty;

    @Bind(R.id.rlError)
    RelativeLayout rlError;

    @Bind(R.id.btnReloadEmpty)
    Button btnReloadEmpty;

    @Bind(R.id.btnReload)
    Button btnReload;

    // 电费
    @Bind(R.id.bg_dian)
    BGABadgeView bg_dian;

    @Bind(R.id.tv_dian)
    TextView tv_dian;

    @Bind(R.id.rl_dian)
    RelativeLayout rl_dian;

    // 水费
    @Bind(R.id.bg_water)
    BGABadgeView bg_water;

    @Bind(R.id.tv_water)
    TextView tv_water;

    @Bind(R.id.rl_water)
    RelativeLayout rl_water;

    // 物业费
    @Bind(R.id.bg_wuye)
    BGABadgeView bg_wuye;

    @Bind(R.id.tv_wuye)
    TextView tv_wuye;

    @Bind(R.id.rl_wuye)
    RelativeLayout rl_wuye;

    //垃圾处理费
    @Bind(R.id.bg_laji)
    BGABadgeView bg_laji;

    @Bind(R.id.tv_laji)
    TextView tv_laji;

    @Bind(R.id.rl_laji)
    RelativeLayout rl_laji;

    //房屋出租费
    @Bind(R.id.bg_zu)
    BGABadgeView bg_zu;

    @Bind(R.id.tv_zu)
    TextView tv_zu;

    @Bind(R.id.rl_zu)
    RelativeLayout rl_zu;

    //充电费
    @Bind(R.id.bg_chong)
    BGABadgeView bg_chong;

    @Bind(R.id.tv_chong)
    TextView tv_chong;

    @Bind(R.id.rl_chong)
    RelativeLayout rl_chong;

    // 押金管理费用
    @Bind(R.id.bg_ya)
    BGABadgeView bg_ya;

    @Bind(R.id.tv_ya)
    TextView tv_ya;

    @Bind(R.id.rl_ya)
    RelativeLayout rl_ya;

    // 车位出租费
    @Bind(R.id.bg_che)
    BGABadgeView bg_che;

    @Bind(R.id.tv_chezu)
    TextView tv_chezu;

    @Bind(R.id.rl_chezu)
    RelativeLayout rl_chezu;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.tv_ok)
    TextView tv_ok;
    private PayFeesPresent present;
    private Intent intent;

    private List<PayFees> waterList, electricList, tenementList, rubbishList, roomList, cheList, batteryList, yaList;
    private PayFees fees;

    private int pay = 0;

    private List<String> billIds = new ArrayList<>();
    private String billidss = "";

    private List<String> payId = new ArrayList<>();

    List<String> imageList = new ArrayList<>();

    private Observable<RxBusMsg> observable;

    @Override
    protected void initViews(View view) {
        ll_scuess.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        tv_title.setText("物业缴费");
        tv_rigth.setVisibility(View.VISIBLE);
        tv_rigth.setText("缴费记录");
        // 设置广告栏
        banner.setImageLoader(new GlideLoader());
        present.getAd("0", "11");
        //小圆点的设置
        /*bg_dian.showTextBadge("8");
        bg_dian.setDragDismissDelegage(new BGADragDismissDelegate() {
            @Override
            public void onDismiss(BGABadgeable badgeable) {
                ToastTools.showLong(PayFeesActivity.this,"消失了");
            }
        });*/
        ll_left.setOnClickListener(this);
        tv_rigth.setOnClickListener(this);
        rl_dian.setOnClickListener(this);
        rl_water.setOnClickListener(this);
        rl_wuye.setOnClickListener(this);
        rl_laji.setOnClickListener(this);
        rl_zu.setOnClickListener(this);
        rl_chezu.setOnClickListener(this);
        rl_chong.setOnClickListener(this);
        rl_ya.setOnClickListener(this);
        tv_ok.setOnClickListener(this);

        electricList = new ArrayList<>();
        waterList = new ArrayList<>();
        tenementList = new ArrayList<>();
        rubbishList = new ArrayList<>();
        roomList = new ArrayList<>();
        cheList = new ArrayList<>();
        batteryList = new ArrayList<>();
        yaList = new ArrayList<>();

        present.getInfo();

        observable = RxBus.get().register("refreshPay", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 4) {
                    present.getInfo();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new PayFeesPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }


    private String payItems = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left: // 返回
                finish();
                break;
            case R.id.tv_rigth:  //  缴费历史

                break;
            case R.id.rl_dian:  //  电费
                intent = new Intent(this, PayListActivity.class);
                intent.putExtra("flag", 1);
                intent.putExtra("payItemsId", 2 + "");
                if (electricList.size() != 0) {
                    for (int i = 0; i < electricList.size(); i++) {
                        if (TextUtils.isEmpty(billidss)) {
                            billidss = electricList.get(i).billid + "";
                            continue;
                        } else {
                            billidss = billidss + "," + electricList.get(i).billid;
                        }
                    }
                    intent.putExtra("billIds", billidss);
                }
                startActivity(intent);
                ColpencilLogger.e("billidss=" + billidss);
                billidss = "";
                break;
            case R.id.rl_water:  //  水费
                intent = new Intent(this, PayListActivity.class);
                intent.putExtra("flag", 2);
                intent.putExtra("payItemsId", 1 + "");
                if (waterList.size() != 0) {
                    for (int i = 0; i < waterList.size(); i++) {
                        if (TextUtils.isEmpty(billidss)) {
                            billidss = waterList.get(i).billid + "";
                            continue;
                        } else {
                            billidss = billidss + "," + waterList.get(i).billid;
                        }
                    }
                    intent.putExtra("billIds", billidss);
                }
                startActivity(intent);
                ColpencilLogger.e("billidss=" + billidss);
                billidss = "";
                break;
            case R.id.rl_wuye:  // 物业费
                intent = new Intent(this, PayListActivity.class);
                intent.putExtra("flag", 3);
                intent.putExtra("payItemsId", 0 + "");
                if (tenementList.size() != 0) {
                    for (int i = 0; i < tenementList.size(); i++) {
                        if (TextUtils.isEmpty(billidss)) {
                            billidss = tenementList.get(i).billid + "";
                            continue;
                        } else {
                            billidss = billidss + "," + tenementList.get(i).billid;
                        }
                    }
                    intent.putExtra("billIds", billidss);
                }
                startActivity(intent);
                ColpencilLogger.e("billidss=" + billidss);
                billidss = "";
                break;
            case R.id.rl_laji:  //  垃圾处理费
                intent = new Intent(this, PayListActivity.class);
                intent.putExtra("flag", 4);
                intent.putExtra("payItemsId", 3 + "");
                if (rubbishList.size() != 0) {
                    for (int i = 0; i < rubbishList.size(); i++) {
                        if (TextUtils.isEmpty(billidss)) {
                            billidss = rubbishList.get(i).billid + "";
                            continue;
                        } else {
                            billidss = billidss + "," + rubbishList.get(i).billid;
                        }
                    }
                    intent.putExtra("billIds", billidss);
                }
                startActivity(intent);
                ColpencilLogger.e("billidss=" + billidss);
                billidss = "";
                break;
            case R.id.rl_zu:  //  房屋出租费
                intent = new Intent(this, PayListActivity.class);
                intent.putExtra("flag", 5);
                intent.putExtra("payItemsId", 5 + "");
                if (roomList.size() != 0) {
                    for (int i = 0; i < roomList.size(); i++) {
                        if (TextUtils.isEmpty(billidss)) {
                            billidss = roomList.get(i).billid + "";
                            continue;
                        } else {
                            billidss = billidss + "," + roomList.get(i).billid;
                        }
                    }
                    intent.putExtra("billIds", billidss);
                }
                startActivity(intent);
                ColpencilLogger.e("billidss=" + billidss);
                billidss = "";
                break;
            case R.id.rl_chezu:  // 车位出租费
                intent = new Intent(this, PayListActivity.class);
                intent.putExtra("flag", 6);
                intent.putExtra("payItemsId", 6 + "");
                if (cheList.size() != 0) {
                    for (int i = 0; i < cheList.size(); i++) {
                        if (TextUtils.isEmpty(billidss)) {
                            billidss = cheList.get(i).billid + "";
                            continue;
                        } else {
                            billidss = billidss + "," + cheList.get(i).billid;
                        }
                    }
                    intent.putExtra("billIds", billidss);
                }
                startActivity(intent);
                ColpencilLogger.e("billidss=" + billidss);
                billidss = "";
                break;
            case R.id.rl_chong:  // 充电位出租费
                intent = new Intent(this, PayListActivity.class);
                intent.putExtra("flag", 7);
                intent.putExtra("payItemsId", 7 + "");
                if (batteryList.size() != 0) {
                    for (int i = 0; i < batteryList.size(); i++) {
                        if (TextUtils.isEmpty(billidss)) {
                            billidss = batteryList.get(i).billid + "";
                            continue;
                        } else {
                            billidss = billidss + "," + batteryList.get(i).billid;
                        }
                    }
                    intent.putExtra("billIds", billidss);
                }
                startActivity(intent);
                ColpencilLogger.e("billidss=" + billidss);
                billidss = "";
                break;
            case R.id.rl_ya:// 押金管理费用
                intent = new Intent(this, PayListActivity.class);
                intent.putExtra("flag", 8);
                if (payId.size() != 0) {
                    //清除重复的元素
                    ArrayList arr2 = new ArrayList();
                    //遍历payId容器
                    for (Iterator it = payId.iterator(); it.hasNext(); ) {
                        Object obj = it.next();
                        //如果arr2容器中不包含当前的payId容器的元素
                        if (!(arr2.contains(obj))) {
                            arr2.add(obj);
                        }
                    }
                    //清空payId容器中的元素
                    payId.clear();
                    //把arr2中的元素赋给payId容器中
                    payId.addAll(arr2);
                    //将集合变成字符串
                    for (int i = 0; i < payId.size(); i++) {
                        if (TextUtils.isEmpty(payItems)) {
                            payItems = payId.get(i);
                        } else {
                            payItems = payItems + "," + payId.get(i);
                        }
                    }
                    intent.putExtra("payItemsId", payItems);
                }

                if (yaList.size() != 0) {
                    for (int i = 0; i < yaList.size(); i++) {
                        if (TextUtils.isEmpty(billidss)) {
                            billidss = yaList.get(i).billid + "";
                            break;
                        } else {
                            billidss = billidss + "," + yaList.get(i).billid;
                        }
                    }
                    intent.putExtra("billIds", billidss);
                }
                startActivity(intent);
                ColpencilLogger.e("billidss=" + billidss);
                billidss = "";
                break;
            case R.id.tv_ok:  // 全缴
                if (billIds.size() != 0) {
                    ColpencilLogger.e("size=" + billIds.size());
                    for (int i = 0; i < billIds.size(); i++) {
                        if (TextUtils.isEmpty(billidss)) {
                            billidss = billIds.get(i);
                            break;
                        } else {
                            billidss = billidss + "," + billIds.get(i);
                        }
                    }
                }
                intent = new Intent(this, PaySureActivity.class);
                intent.putExtra("billIds", billidss);
                startActivity(intent);
                ColpencilLogger.e("billidss=" + billidss);
                billidss = "";
                break;
        }
    }

    @Override
    public void getInfo(final ListBean<PayFees> list) {
        int code = list.code;
        String message = list.message;
        tv_rigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(PayFeesActivity.this, ApiCloudActivity.class);
//                        intent.putExtra("title", "缴费历史");
                intent.putExtra("startUrl", list.paymentHistory);
                startActivity(intent);
            }
        });
        switch (code) {
            case 0:// 成功
                ll_scuess.setVisibility(View.VISIBLE);
                rlProgress.setVisibility(View.GONE);
                rlError.setVisibility(View.GONE);
                rlEmpty.setVisibility(View.GONE);
                classify(list.data);

                //电费
                if (electricList.size() != 0) {
                    bg_dian.showTextBadge(electricList.size() + "");
                    tv_dian.setText(caculateFees(electricList) + "元待缴");
                }
                //水费
                if (waterList.size() != 0) {
                    bg_water.showTextBadge(waterList.size() + "");
                    tv_water.setText(caculateFees(waterList) + "元待缴");
                }
                //物业费
                if (tenementList.size() != 0) {
                    bg_wuye.showTextBadge(tenementList.size() + "");
                    tv_wuye.setText(caculateFees(tenementList) + "元待缴");
                }
                //垃圾处理费
                if (rubbishList.size() != 0) {
                    bg_laji.showTextBadge(rubbishList.size() + "");
                    tv_laji.setText(caculateFees(rubbishList) + "元待缴");
                }
                //房屋出租费
                if (roomList.size() != 0) {
                    bg_zu.showTextBadge(roomList.size() + "");
                    tv_zu.setText(caculateFees(roomList) + "元待缴");
                }
                //车位出租费
                if (cheList.size() != 0) {
                    bg_che.showTextBadge(cheList.size() + "");
                    tv_chezu.setText(caculateFees(cheList) + "元待缴");
                }
                //充电位费
                if (batteryList.size() != 0) {
                    bg_chong.showTextBadge(batteryList.size() + "");
                    tv_chong.setText(caculateFees(batteryList) + "元待缴");
                }
                //押金管理费用
                if (yaList.size() != 0) {
                    bg_ya.showTextBadge(yaList.size() + "");
                    tv_ya.setText(caculateFees(yaList) + "元待缴");
                }
                WarnDialog.showInfo(this, "尊敬的业主您好！您有" + pay + "笔待缴记录，麻烦您尽快处理，以免给您的生活带来不方便。");
                break;
            case 1://  失败
                ll_scuess.setVisibility(View.GONE);
                rlProgress.setVisibility(View.GONE);
                rlError.setVisibility(View.VISIBLE);
                rlEmpty.setVisibility(View.GONE);
                btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        present.getInfo();
                    }
                });
                ToastTools.showShort(this, message);
                break;
            case 2://  无数据
                ll_scuess.setVisibility(View.VISIBLE);
                rlProgress.setVisibility(View.GONE);
                rlError.setVisibility(View.GONE);
                rlEmpty.setVisibility(View.GONE);
                btnReload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        present.getInfo();
                    }
                });
                WarnDialog.showInfo(this, "尊敬的业主您好！你暂时无欠费记录。");
                tv_ok.setVisibility(View.GONE);
                break;
            case 3:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    @Override
    public void getAd(final ListBean<Ad> list) {
        int code = list.code;
        String message = list.message;
        switch (code) {
            case 0:
                imageList.clear();
                for (int i = 0; i < list.data.size(); i++) {
                    imageList.add(list.data.get(i).files);
                    ColpencilLogger.e("广告图片=" + list.data.get(i).files);
                }

                if (imageList.size() == 0) {
                    imageList.add("ddd");
                    //设置图片集合
                    banner.setImages(imageList);
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();
                } else {
                    //设置图片集合
                    banner.setImages(imageList);
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();
                    banner.setOnBannerClickListener(new OnBannerClickListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            intent = new Intent(PayFeesActivity.this, ApiCloudActivity.class);
                            intent.putExtra("startUrl", list.data.get(position - 1).target);
                            present.adCount(list.data.get(position - 1).aid);
                            startActivity(intent);
                        }
                    });
                }
                break;
            case 1:
                ToastTools.showShort(this, false, message);
                break;
            case 2:

                break;
            case 3:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    @Override
    public void addCount(ResultInfo resultInfo) {
        ColpencilLogger.e("广告点击量=" + resultInfo.message);
    }

    /**
     * 将集合进行分类
     *
     * @param list
     */
    private void classify(List<PayFees> list) {
        for (PayFees payFees : list) {
            fees = new PayFees();
            fees.billid = payFees.billid;
            fees.payitemsid = payFees.payitemsid;
            fees.billamout = payFees.billamout;
            switch (payFees.payitemsid) {
                case "0":  // 物业费
                    tenementList.add(fees);
                    pay++;
                    billIds.add(payFees.billid + "");
                    break;
                case "1": //  水费
                    waterList.add(fees);
                    pay++;
                    billIds.add(payFees.billid + "");
                    break;
                case "2": //  电费
                    electricList.add(fees);
                    pay++;
                    billIds.add(payFees.billid + "");
                    break;
                case "3"://  垃圾清理费
                    rubbishList.add(fees);
                    pay++;
                    billIds.add(payFees.billid + "");
                    break;
                case "4": //  充电位租赁费
                    batteryList.add(fees);
                    pay++;
                    billIds.add(payFees.billid + "");
                    break;
                case "5":  //  房屋出租费
                    roomList.add(fees);
                    pay++;
                    billIds.add(payFees.billid + "");
                    break;
                case "6": //  车位费
                    cheList.add(fees);
                    pay++;
                    billIds.add(payFees.billid + "");
                    break;
                default:
                    String[] strings = payFees.payitemsid.split(",");
                    if (strings.length != 0) {
                        if (strings.length == 1) { // 一个算一笔
                            switch (strings[0]) {
                                case "10":
                                    yaList.add(fees);
                                    pay++;
                                    billIds.add(payFees.billid + "");
                                    payId.add(payFees.payitemsid + "");
                                    break;
                                case "13":
                                    yaList.add(fees);
                                    pay++;
                                    billIds.add(payFees.billid + "");
                                    payId.add(payFees.payitemsid + "");
                                    break;
                                case "14":
                                    yaList.add(fees);
                                    pay++;
                                    billIds.add(payFees.billid + "");
                                    payId.add(payFees.payitemsid + "");
                                    break;
                                case "15":
                                    yaList.add(fees);
                                    pay++;
                                    billIds.add(payFees.billid + "");
                                    payId.add(payFees.payitemsid + "");
                                    break;
                            }
                        } else {
                            yaList.add(fees);
                            pay++;
                            billIds.add(payFees.billid + "");
                            payId.add(payFees.payitemsid + "");
                        }
                    }
                    break;
            }
        }
    }


    /**
     * 获取某项的总金额
     *
     * @param list
     * @return
     */
    private String caculateFees(List<PayFees> list) {
        double fees = 0;
        for (int i = 0; i < list.size(); i++) {
            fees = list.get(i).billamout + fees;
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(fees);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("refreshPay",observable);
    }
}