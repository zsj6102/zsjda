package com.colpencil.propertycloud.View.Activitys.Home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.View.Activitys.OpenDoor.OpenDoorActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import butterknife.Bind;

/**
 * @author 汪 亮
 * @Description： 加载网页的界面
 * @Email DramaScript@outlook.com
 * @date 2016/10/12
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_webview
)
public class LoadUriActivity extends ColpencilActivity {

    @Bind(R.id.webview)
    WebView webview;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.iv_shen)
    ImageView iv_shen;

    @Bind(R.id.progressbar)
    ProgressBar progressbar;

    @Override
    protected void initViews(View view) {
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webview.canGoBack()) {
                    webview.goBack();
                } else {
                    finish();
                }
            }
        });
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        int type = getIntent().getIntExtra("type", 0);
        tv_title.setText(title);
        progressbar.setVisibility(View.VISIBLE);
        ColpencilLogger.e("url:" + url);
        if (type == 1) {
            iv_shen.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            tv_rigth.setVisibility(View.VISIBLE);
            tv_rigth.setText("申请钥匙");
            ll_rigth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2016/11/23 申请钥匙
                    WarnDialog.show(LoadUriActivity.this, SharedPreferencesUtil.getInstance(LoadUriActivity.this).getString("propertytel"),
                            SharedPreferencesUtil.getInstance(LoadUriActivity.this).getString("servicetel"), "请持本人有效身份证件到物业客服中心进行居住情况认证后，客服人员将发放相关门禁的手机钥匙给您。");
                }
            });
        }
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
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
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressbar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressbar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        webview.loadUrl(url);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (webview.canGoBack()) {
                webview.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
