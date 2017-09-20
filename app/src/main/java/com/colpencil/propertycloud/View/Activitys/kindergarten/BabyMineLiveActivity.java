package com.colpencil.propertycloud.View.Activitys.kindergarten;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Ui.CustomSurfaceView;
import com.wholeally.audio.AudioPlay;
import com.wholeally.qysdk.QYLoginReturnInfo;
import com.wholeally.qysdk.QYSDK;
import com.wholeally.qysdk.QYSession;
import com.wholeally.qysdk.QYView;



/**
 * Created by Administrator on 2017/3/9.
 */
public class BabyMineLiveActivity extends Activity implements View.OnClickListener{

    LinearLayout ll_left;

    TextView tv_title;

    LinearLayout layoutSurface;

    SurfaceView surfaceView;

    CustomSurfaceView surface_video_view;

    TextView text_channel;

    Toolbar toolbar;

    private ImageView img_back;

    private FrameLayout frameLayout;

    private QYSession session;
    private QYView qyViewWatch = null;
    private int ret;
    private String auth_code;
    private String channelId;
    private String serverIp;
    private String appId;
    private String port;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_live);
        initView();
        if(tv_title != null){
            tv_title.setText("我的直播");
        }
        session = QYSDK.CreateSession(getApplicationContext());
        initData();
        if(surface_video_view != null){
            initView_H();
            SurfaceHolder holder = surface_video_view.getHolder();
            holder.setFixedSize(800, 800);
            holder.setKeepScreenOn(true);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        if(text_channel != null){
            text_channel.setText(channelId);
        }
        qyViewWatchVideo();
    }

    private void initView_H(){
        frameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                surface_video_view.setFatherW_H(frameLayout.getWidth(), frameLayout.getHeight());
                surface_video_view.setFatherTopAndBottom(frameLayout.getTop(), frameLayout.getBottom());
            }
        },100);
    }

    private void initView(){
        text_channel = (TextView) findViewById(R.id.text_channel);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_left = (LinearLayout) findViewById(R.id.ll_left);
        layoutSurface = (LinearLayout) findViewById(R.id.layout_surface);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        surface_video_view = (CustomSurfaceView) findViewById(R.id.surface_video_view);
        img_back = (ImageView) findViewById(R.id.back);
        frameLayout = (FrameLayout) findViewById(R.id.frame);
        img_back.setOnClickListener(this);
        if(ll_left != null){
            ll_left.setOnClickListener(this);
        }
    }

    private void initData(){
        if(getIntent().getExtras() != null){
            auth_code = getIntent().getStringExtra("auth_code");
            channelId = getIntent().getStringExtra("channelID");
            serverIp = getIntent().getStringExtra("serverIP");
            appId = getIntent().getStringExtra("appID");
            port = getIntent().getStringExtra("port");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        AudioPlay.getInstance().stop();
        if (null != qyViewWatch) {
            qyViewWatch.Relase();
        }
    }

    public void qyViewWatchVideo() {
        if(auth_code != null && port != null && serverIp != null && channelId != null && appId != null){
            if(session != null){
                ret = session.SetServer(serverIp, Integer.valueOf(port));
                if(ret >= 0){
                    session.ViewerLogin(appId, auth_code, new QYSession.OnViewerLogin() {
                        @Override
                        public void on(int i, QYLoginReturnInfo qyLoginReturnInfo) {
                            if(i >= 0){
                                session.CreateView(Long.valueOf(channelId), new QYSession.OnCreateView() {
                                    @Override
                                    public void on(int i, QYView qyView) {
                                        qyViewWatch = qyView;
                                        if(i >= 0){
                                            AudioPlay.getInstance().start();
                                            if(qyViewWatch != null){
                                                if(surface_video_view !=  null){
                                                    qyViewWatch.SetCanvas(surface_video_view.getHolder()); // 传入画布显示视频监控画面
                                                } else if(surfaceView != null){
                                                    qyViewWatch.SetCanvas(surfaceView.getHolder());
                                                }
                                                qyViewWatch.StartConnect(new QYView.OnStartConnect() {
                                                    @Override
                                                    public void on(int i) {
                                                        if(i >= 0){
                                                            qyViewWatch.CtrlAudio(true);
                                                        } else {

                                                        }
                                                    }
                                                });
                                            } else {

                                            }
                                        } else {

                                        }
                                    }
                                });
                            } else {
                            }
                        }
                    });
                } else {

                }
            } else {

            }
        } else {

        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
