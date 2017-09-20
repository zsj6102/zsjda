package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ComplaintHistroy;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.PhotoPreview.PhotoActivity;
import com.colpencil.propertycloud.Tools.PlayerUtil;
import com.colpencil.propertycloud.View.Adapter.ImageSelectAdapter2;
import com.colpencil.propertycloud.View.Adapter.NullAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;
import com.yinghe.whiteboardlib.bean.EventBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.colpencil.propertycloud.R.id.item;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_complain_detail
)
public class ComplainDetailActivity extends ColpencilActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;
    @Bind(R.id.listview)
    ListView listview;

    private TextView repair_detail_orderid;
    private TextView repair_detail_state;
    private TextView repair_detail_time;
    private TextView repair_detail_description;
    private TextView repair_detail_employee;
    private TextView repair_detail_finishtime;
    private TextView property_opinion;
    private View ll_divide;
    private LinearLayout ll_detail_bottom;
    private TextView tv_evaluation;
    private ImageView iv_state;
    private TextView owner_opinion;
    private LinearLayout ll_empnm;
    private LinearLayout ll_finish_time;
    private LinearLayout ll_property;
    private ColpencilGridView gridview;
    private LinearLayout ll_employee_number;
    private TextView employee_number;
    private LinearLayout ll_no_fill;
    private TextView tv_fill;
    private LinearLayout ll_rudio;
    private TextView tv_music;
    private ImageView iv_phone;

    private ImageSelectAdapter2 adapter;
    private List<String> strings = new ArrayList<>();
    private Observable<EventBean> observable;
    private String id;
    private PlayerUtil util;

    private ImageView imageView;
    private ScaleAnimation animation;

    @Override
    protected void initViews(View view) {
        if (getIntent().getIntExtra("type", 0) == 0) {
            tv_title.setText("投诉详情");
        } else {
            tv_title.setText("表扬详情");
        }
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipe.setOnRefreshListener(this);
        util = new PlayerUtil();
        initheader();
        ComplaintHistroy histroy = (ComplaintHistroy) getIntent().getSerializableExtra("data");
        if (histroy != null) {
            id = histroy.getOrderid() + "";
            initData(histroy);
        } else {
            id = getIntent().getStringExtra("id");
            onRefresh();
        }
        initBus();
        animation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f);
        animation.setDuration(2000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        util.setOnCompleteListener(new PlayerUtil.OnCompleteListener() {
            @Override
            public void complete() {
                imageView.clearAnimation();
            }
        });
    }

    private void initheader() {
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new ImageSelectAdapter2(this, strings, R.layout.upload_img_item3);
        View header = LayoutInflater.from(this).inflate(R.layout.header_complaint_detail, null);
        repair_detail_orderid = (TextView) header.findViewById(R.id.repair_detail_orderid);
        repair_detail_state = (TextView) header.findViewById(R.id.repair_detail_state);
        repair_detail_time = (TextView) header.findViewById(R.id.repair_detail_time);
        repair_detail_description = (TextView) header.findViewById(R.id.repair_detail_description);
        repair_detail_employee = (TextView) header.findViewById(R.id.repair_detail_employee);
        repair_detail_finishtime = (TextView) header.findViewById(R.id.repair_detail_finishtime);
        property_opinion = (TextView) header.findViewById(R.id.property_opinion);
        ll_divide = header.findViewById(R.id.ll_divide);
        ll_detail_bottom = (LinearLayout) header.findViewById(R.id.ll_detail_bottom);
        tv_evaluation = (TextView) header.findViewById(R.id.tv_evaluation);
        iv_state = (ImageView) header.findViewById(R.id.iv_state);
        owner_opinion = (TextView) header.findViewById(R.id.owner_opinion);
        ll_empnm = (LinearLayout) header.findViewById(R.id.ll_empnm);
        ll_finish_time = (LinearLayout) header.findViewById(R.id.ll_finish_time);
        ll_property = (LinearLayout) header.findViewById(R.id.ll_property);
        gridview = (ColpencilGridView) header.findViewById(R.id.gridview);
        ll_employee_number = (LinearLayout) header.findViewById(R.id.ll_employee_number);
        employee_number = (TextView) header.findViewById(R.id.employee_number);
        ll_no_fill = (LinearLayout) header.findViewById(R.id.ll_no_fill);
        tv_fill = (TextView) header.findViewById(R.id.tv_fill);
        ll_rudio = (LinearLayout) header.findViewById(R.id.ll_rudio);
        tv_music = (TextView) header.findViewById(R.id.tv_music);
        imageView = (ImageView) header.findViewById(R.id.iv_music);
        iv_phone = (ImageView) header.findViewById(R.id.iv_phone);
        listview.addHeaderView(header);
        listview.setAdapter(new NullAdapter(this, new ArrayList<String>(), R.layout.item_null));
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int childCount = parent.getChildCount();
                ArrayList<Rect> rects = new ArrayList<>();
                for (int i = 0; i < childCount; i++) {
                    Rect rect = new Rect();
                    View child = parent.getChildAt(i);
                    child.getGlobalVisibleRect(rect);
                    rects.add(rect);
                }
                Intent intent = new Intent(ComplainDetailActivity.this, PhotoActivity.class);
                String imageArray[] = new String[strings.size()];
                for (int i = 0; i < imageArray.length; i++) {
                    imageArray[i] = strings.get(i);
                }
                intent.putExtra("imgUrls", imageArray);
                intent.putExtra("index", position);
                intent.putExtra("bounds", rects);
                startActivity(intent);
            }
        });
    }

    private void initBus() {
        observable = RxBus.get().register("feed", EventBean.class);
        Subscriber<EventBean> subscriber = new Subscriber<EventBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EventBean bean) {
                loadData();
            }
        };
        observable.subscribe(subscriber);
    }

    private void initData(final ComplaintHistroy histroy) {
        repair_detail_orderid.setText(histroy.getSugcode());
        repair_detail_time.setText(histroy.getSugtm());
        repair_detail_description.setText("描述：" + histroy.getDetaildesc());
        repair_detail_state.setText(histroy.getStatu());
        strings.clear();
        strings.addAll(histroy.getPics());
        adapter.notifyDataSetChanged();
        int state = histroy.getState();
        if (state == 0) {       //待指派
            repair_detail_state.setTextColor(getResources().getColor(R.color.main_red));
            ll_empnm.setVisibility(View.GONE);
            ll_finish_time.setVisibility(View.GONE);
            ll_property.setVisibility(View.GONE);
            ll_divide.setVisibility(View.GONE);
            ll_detail_bottom.setVisibility(View.GONE);
            ll_employee_number.setVisibility(View.GONE);
            ll_no_fill.setVisibility(View.GONE);
        } else if (state == 1) {        //已指派
            repair_detail_state.setTextColor(getResources().getColor(R.color.text_red));
            repair_detail_employee.setText(histroy.getEmpnm());
            ll_empnm.setVisibility(View.VISIBLE);
            ll_finish_time.setVisibility(View.GONE);
            ll_property.setVisibility(View.GONE);
            ll_divide.setVisibility(View.GONE);
            ll_detail_bottom.setVisibility(View.GONE);
            ll_employee_number.setVisibility(View.VISIBLE);
            employee_number.setText(histroy.getTel());
            ll_no_fill.setVisibility(View.GONE);
        } else if (state == 2) {        //处理中
            repair_detail_state.setTextColor(getResources().getColor(R.color.color_ffb651));
            repair_detail_employee.setText(histroy.getEmpnm());
            ll_empnm.setVisibility(View.VISIBLE);
            ll_finish_time.setVisibility(View.GONE);
            ll_property.setVisibility(View.GONE);
            ll_divide.setVisibility(View.GONE);
            ll_detail_bottom.setVisibility(View.GONE);
            ll_employee_number.setVisibility(View.VISIBLE);
            employee_number.setText(histroy.getTel());
            ll_no_fill.setVisibility(View.GONE);
        } else {        //已完成
            repair_detail_state.setTextColor(getResources().getColor(R.color.text_green));
            repair_detail_employee.setText(histroy.getEmpnm());
            property_opinion.setText(histroy.getProperty_opinion());
            repair_detail_finishtime.setText(histroy.getCompletm());
            ll_empnm.setVisibility(View.VISIBLE);
            ll_finish_time.setVisibility(View.VISIBLE);
            ll_property.setVisibility(View.VISIBLE);
            ll_employee_number.setVisibility(View.VISIBLE);
            ll_divide.setVisibility(View.VISIBLE);
            employee_number.setText(histroy.getTel());
            if (histroy.getIsfedbck() == 0) {       //未反馈
                ll_detail_bottom.setVisibility(View.GONE);
                ll_divide.setVisibility(View.GONE);
                ll_no_fill.setVisibility(View.VISIBLE);
            } else {        //已反馈
                ll_no_fill.setVisibility(View.GONE);
                ll_detail_bottom.setVisibility(View.VISIBLE);
                owner_opinion.setText(histroy.getDetail_description());
                if (histroy.getScore() == 0) {    //很差
                    tv_evaluation.setText("很差");
                    tv_evaluation.setTextColor(getResources().getColor(R.color.main_red));
                    iv_state.setImageResource(R.mipmap.agre_press);
                } else if (histroy.getScore() == 1) {       //一般
                    tv_evaluation.setText("一般");
                    tv_evaluation.setTextColor(getResources().getColor(R.color.text_orage));
                    iv_state.setImageResource(R.mipmap.general);
                } else {        //不错
                    tv_evaluation.setText("不错");
                    tv_evaluation.setTextColor(getResources().getColor(R.color.main_blue));
                    iv_state.setImageResource(R.mipmap.satisfaction_press);
                }
            }
            tv_fill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ComplainDetailActivity.this, NewFeedBackActivity.class);
                    intent.putExtra("orderid", histroy.getOrderid() + "");
                    intent.putExtra("type", "1");
                    startActivity(intent);
                }
            });
        }
        if (TextUtils.isEmpty(histroy.getUrl_audio())) {
            ll_rudio.setVisibility(View.GONE);
        } else {
            ll_rudio.setVisibility(View.VISIBLE);
        }
        if (((histroy.getAudio_duration() / 60) % 60) > 0) {
            tv_music.setText((histroy.getAudio_duration() / 60) % 60 + "." + histroy.getAudio_duration() % 60 + "”");
        } else {
            tv_music.setText((histroy.getAudio_duration() % 60 + "”"));
        }
        ll_rudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download(histroy.getUrl_audio());
            }
        });
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + histroy.getTel()));
                startActivity(intent);
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("feed", observable);
        if (util.player != null && util.player.isPlaying()) {
            util.stop();
        }
    }

    public void loadData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id + "");
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadComplaintDetail(params)
                .map(new Func1<ResultInfo<ComplaintHistroy>, ResultInfo<ComplaintHistroy>>() {
                    @Override
                    public ResultInfo<ComplaintHistroy> call(ResultInfo<ComplaintHistroy> complaintHistroyResultInfo) {
                        return complaintHistroyResultInfo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<ComplaintHistroy>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        swipe.setRefreshing(false);
                    }

                    @Override
                    public void onNext(ResultInfo<ComplaintHistroy> complaintHistroyResultInfo) {
                        swipe.setRefreshing(false);
                        if (complaintHistroyResultInfo.code == 0) {
                            initData(complaintHistroyResultInfo.data);
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    private void download(String downloadUrl) {
        final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/calls.amr");
        if (file.exists()) {
            file.delete();
        }
        OkHttpUtils
                .get()
                .url(downloadUrl)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "calls.amr") {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        File file1 = new File(response.getAbsolutePath());
                        if (file1.exists()) {
                            if (util.player != null && util.player.isPlaying()) {
                                util.stop();
                                imageView.clearAnimation();
                            }
                            util.start(file1.getAbsolutePath());
                            imageView.startAnimation(animation);
                        }
                    }
                });
    }
}
