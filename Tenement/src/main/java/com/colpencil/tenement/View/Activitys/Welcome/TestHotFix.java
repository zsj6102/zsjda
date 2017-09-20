package com.colpencil.tenement.View.Activitys.Welcome;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.colpencil.tenement.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import okhttp3.OkHttpClient;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_hotfix
)
public class TestHotFix extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.btn_update)
    Button btn_update;

    @Bind(R.id.btn_show)
    Button btn_show;

    @Bind(R.id.tv_info)
    TextView tv_info;

    @Override
    protected void initViews(View view) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);

        btn_show.setOnClickListener(this);
        btn_update.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_update:
                update();
                break;
            case R.id.btn_show:
//                Snackbar.make(getView(), "你被逮捕了！", Snackbar.LENGTH_LONG)
//                        .setAction("Action", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ToastTools.showShort(TestHotFix.this,"我是第一个版本。");
//                            }
//                        }).show();
                ToastTools.showShort(TestHotFix.this,"我是第二个版本。");
                break;
        }
    }

    private void update(){
//        DialogTools.showLoding(this,"温馨提示","正在检查更新中。。。");
//        HashMap<String,String> map = new HashMap<>();
//        Observable<EntityResult<AnFixBean>> observable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
//                .createApi(HostApi.class)
//                .update(map)
//                .map(new Func1<EntityResult<AnFixBean>, EntityResult<AnFixBean>>() {
//                    @Override
//                    public EntityResult<AnFixBean> call(EntityResult<AnFixBean> anFixBeanEntityResult) {
//                        return anFixBeanEntityResult;
//                    }
//                }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//
//        Subscriber<EntityResult<AnFixBean>> subscriber = new Subscriber<EntityResult<AnFixBean>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(EntityResult<AnFixBean> anFixBeanEntityResult) {
//                tv_info.setText(anFixBeanEntityResult.toString());
//                int code = anFixBeanEntityResult.getCode();
//                AnFixBean data = anFixBeanEntityResult.getData();
//                switch (code){
//                    case 0:
//                        if (TextUtils.isEmpty(data.md5)){
//                            ToastTools.showShort(TestHotFix.this,"当前暂无更新！");
//                            DialogTools.dissmiss();
//                        }else {
//                            download(data.path);
//                            ColpencilLogger.e("下载地址："+data.path);
//                        }
//                        break;
//                    case 1:
//                        DialogTools.dissmiss();
//                        ToastTools.showShort(TestHotFix.this,false,"请求失败！");
//                        break;
//
//                }
//            }
//        };
//
//        observable.subscribe(subscriber);
    }

    //下载具体操作
    private void download(String downloadUrl) {
//        OkHttpUtils//
//                .get()//
//                .url(downloadUrl)//
//                .build()//
//                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "android_patch.apatch")//
//                {
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(File response, int id) {
//                        DialogTools.dissmiss();
//                        ToastTools.showShort(TestHotFix.this,"更新成功，重新进入app，获得更好的体验！");
//                        if (new File(response.getAbsolutePath()).exists()) {
//                            ColpencilLogger.e("have some patch");
//                            try {
//                                TenementApplication.getInstance().getPatchManager().addPatch(response.getAbsolutePath());
//                            } catch (Exception e) {
//                                ColpencilLogger.e("Test="+ e);
//                            }
//                        } else {
//                            ColpencilLogger.e("have no patch");
//                        }
//                    }
//                });
    }
}
