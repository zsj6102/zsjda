package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ApplaInfo;
import com.colpencil.propertycloud.Bean.Approveid;
import com.colpencil.propertycloud.Bean.ReadInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.Present.Home.ReadInfoPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Imples.ReadView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.colpencil.propertycloud.R.id.iv_check;

/**
 * @author 汪 亮
 * @Description: 一些阅读类的界面
 * @Email DramaScript@outlook.com
 * @date 2016/9/7
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_read
)
public class ReadActivity extends ColpencilActivity implements ReadView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.web_read)
    WebView web_read;

    @Bind(R.id.tv_read1)
    TextView tv_read1;

    @Bind(R.id.tv_read2)
    TextView tv_read2;

    @Bind(R.id.progressbar)
    ProgressBar progressBar;

    @Bind(R.id.iv_check1)
    ImageView iv_check1;

    @Bind(R.id.iv_check2)
    ImageView iv_check2;

    private int flag;
    private ReadInfoPresent present;
    private String name;
    private Intent intent;

    private boolean once = false;
    private String community_id;
    private int type;
    private int houseid;
    private String aprovid;
    private Observable<RxBusMsg> register;
    private File file1;
    private File file2;
    private File file3;

    @Override
    protected void initViews(View view) {
        flag = getIntent().getIntExtra("flag", 0);
        name = getIntent().getStringExtra("name");
        community_id = getIntent().getStringExtra("community_id");
        type = getIntent().getIntExtra("type",0);
        houseid = getIntent().getIntExtra("houseid",0);
        aprovid = getIntent().getIntExtra("aprovid",0)+"";
        ColpencilLogger.e("aprovid2="+aprovid);
        if (flag == 1) {
            tv_read1.setVisibility(View.GONE);
            getUrl(0);
        } else {
            tv_read1.setVisibility(View.VISIBLE);
            getUrl(1);
        }
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText(name);
        SharedPreferencesUtil.getInstance(ReadActivity.this).setBoolean("once", false);
        ColpencilLogger.e("flag=" + flag);
        tv_read1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ReadActivity.this, SignatureActivity.class);
                if (type==0){ // 新申请进来
                    if (flag == 1) {
                        intent.putExtra("type", 0);
                        intent.putExtra("flag", 0);
                    } else {
                        once = SharedPreferencesUtil.getInstance(ReadActivity.this).getBoolean("once", false);
                        if (once == false) {
                            intent.putExtra("type", 1);
                            intent.putExtra("flag", 0);
                        } else {
                            intent.putExtra("type", 2);
                            intent.putExtra("flag", 0);
                        }
                    }
                }else {
                    if (flag == 1) {
                        intent.putExtra("type", 0);
                        intent.putExtra("flag", 1);
                    } else {
                        once = SharedPreferencesUtil.getInstance(ReadActivity.this).getBoolean("once", false);
                        if (once == false) {
                            intent.putExtra("type", 1);
                            intent.putExtra("flag", 1);
                        } else {
                            intent.putExtra("type", 2);
                            intent.putExtra("flag", 1);
                        }
                    }
                }

                startActivity(intent);
            }
        });
        tv_read2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ReadActivity.this, SignatureActivity.class);
                if (type==0){
                    if (flag == 1) {
                        intent.putExtra("type", 0);
                        intent.putExtra("flag", 0);
                    } else {
                        once = SharedPreferencesUtil.getInstance(ReadActivity.this).getBoolean("once", false);
                        if (once == false) {
                            intent.putExtra("type", 1);
                            intent.putExtra("flag", 0);
                        } else {
                            intent.putExtra("type", 2);
                            intent.putExtra("flag", 0);
                        }
                    }
                }else {
                    if (flag == 1) {
                        intent.putExtra("type", 0);
                        intent.putExtra("flag", 1);
                    } else {
                        once = SharedPreferencesUtil.getInstance(ReadActivity.this).getBoolean("once", false);
                        if (once == false) {
                            intent.putExtra("type", 1);
                            intent.putExtra("flag", 1);
                        } else {
                            intent.putExtra("type", 2);
                            intent.putExtra("flag", 1);
                        }
                    }
                }

                startActivity(intent);
            }
        });
        web_read.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        web_read.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

        register = RxBus.get().register("MaterialManagement", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                switch (rxBusMsg.getType()) {
                    case 0:
                        String pic1 = rxBusMsg.getMsg();
                        ColpencilLogger.e("装修规定的图片：" + pic1);
                        if (!TextUtils.isEmpty(pic1)) {
                            file1 = new File(pic1);
                            iv_check1.setVisibility(View.VISIBLE);
                        }
                        change(houseid+"",aprovid,file1,null,null);
                        break;
                    case 1:
                        String pic2 = rxBusMsg.getMsg();
                        String pic3 = SharedPreferencesUtil.getInstance(ReadActivity.this).getString("imagePath");
                        ColpencilLogger.e("装修承诺书1：" + pic2);
                        ColpencilLogger.e("装修承诺书2：" + pic3);
//                        iv_check1.setVisibility(View.VISIBLE);
//                        iv_check2.setVisibility(View.VISIBLE);
                        if (!TextUtils.isEmpty(pic2)) {
                            file2 = new File(pic2);
                            iv_check1.setVisibility(View.VISIBLE);
                        }
                        if (!TextUtils.isEmpty(pic3)) {
                            file3 = new File(pic3);
                            iv_check2.setVisibility(View.VISIBLE);
                        }
                        change(houseid+"",aprovid,null,file2,file3);
                        break;
                }
            }
        };
        register.subscribe(subscriber);
    }

    /**
     *
     */
    private void change(String houseId,String approveid,File decortManReguSign, File decortCommitBookSign, File decortCommitBookSign2){
        ColpencilLogger.e("---------------我执行啦！");
        Map<String, RequestBody> map = new HashMap<>();
        map.put("houseId", OkhttpUtils.toRequestBody(houseId));
        map.put("approveid", OkhttpUtils.toRequestBody(approveid));
        if (decortManReguSign != null) {
            map.put("decortManReguSign\";filename=\"" + "imageList" + 2 + ".jpg", RequestBody.create(MediaType.parse("image/png"), decortManReguSign));
        }
        if (decortCommitBookSign != null) {
            map.put("decortCommitBookSign1\";filename=\"" + "imageList" + 3 + ".jpg", RequestBody.create(MediaType.parse("image/png"), decortCommitBookSign));
        }
        if (decortCommitBookSign2 != null) {
            map.put("decortCommitBookSign2\";filename=\"" + "imageList" + 4 + ".jpg", RequestBody.create(MediaType.parse("image/png"), decortCommitBookSign2));
        }
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .decortApprove(map)
                .map(new Func1<ResultInfo<Approveid>, ResultInfo<Approveid>>() {
                    @Override
                    public ResultInfo call(ResultInfo<Approveid> resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<Approveid>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("请求错误日志：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo<Approveid> resultInfo) {
                        int code = resultInfo.code;
                        String message = resultInfo.message;
                        switch (code) {
                            case 0:
                                if (resultInfo.data.approveid==-1){
                                    NotificationTools.show(ReadActivity.this,"改房间已提交申请。");
                                    return;
                                }
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("mater", rxBusMsg);
                                finish();
                                ToastTools.showShort(ReadActivity.this, true, "提交成功！");
                                break;
                            case 1:
                                ToastTools.showShort(ReadActivity.this, false, message);
                                break;
                            case 2:
                                startActivity(new Intent(ReadActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    /**
     * 获取装修管理规定的h5
     *
     * @param type
     */
    private void getUrl(int type) {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", type + "");
        map.put("communityId", community_id);
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getFitUrl(map)
                .map(new Func1<ResultInfo<String>, ResultInfo<String>>() {
                    @Override
                    public ResultInfo call(ResultInfo<String> resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("请求错误日志：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo<String> resultInfo) {
                        int code = resultInfo.code;
                        String message = resultInfo.message;
                        switch (code) {
                            case 0:
                                web_read.loadUrl(resultInfo.data);
                                break;
                            case 1:
                                ToastTools.showShort(ReadActivity.this, false, message);
                                break;
                            case 3:
                                startActivity(new Intent(ReadActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new ReadInfoPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mPresenter == null)
            return;
        ((ReadInfoPresent) mPresenter).getInfo();
    }

    @Override
    public void readInfo(ReadInfo readInfo) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("MaterialManagement",register);
    }
}
