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
import com.colpencil.propertycloud.Bean.RepairHistory;
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
import com.yinghe.whiteboardlib.bean.Image;
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
import static com.colpencil.propertycloud.R.id.ll_phone;

/**
 * @author 陈 宝
 * @Description:保修详情
 * @Email 1041121352@qq.com
 * @date 2016/11/19
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_complain_detail
)
public class RepairDetailActivity extends ColpencilActivity implements SwipeRefreshLayout.OnRefreshListener {

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
    private TextView repair_detail_type;
    private TextView repair_detail_time;
    private TextView repair_detail_person;
    private TextView repair_detail_address;
    private TextView repair_detail_description;
    private TextView repair_detail_employee;
    private TextView repair_detail_finishtime;
    private TextView property_opinion;
    private TextView owner_opinion;

    private TextView tv_evaluation;
    private ImageView iv_state;
    private LinearLayout ll_empnm;
    private LinearLayout ll_finish_time;
    private LinearLayout ll_property;
    private View ll_divide;
    private LinearLayout ll_detail_bottom;
    private ColpencilGridView gridview;
    private LinearLayout ll_employee_number;
    private TextView employee_number;
    private LinearLayout ll_rudio;
    private TextView tv_music;
    private LinearLayout ll_no_fill;
    private TextView tv_fill;
    private ImageView iv_phone;

    private ImageSelectAdapter2 adapter;
    private List<String> strings = new ArrayList<>();
    private String id;
    private Observable<EventBean> observable;
    private PlayerUtil util;

    private ImageView imageView;
    private ScaleAnimation animation;

    @Override
    protected void initViews(View view) {
        tv_title.setText("报修详情");
        swipe.setOnRefreshListener(this);
        util = new PlayerUtil();
        initheader();
        RepairHistory vo = (RepairHistory) getIntent().getSerializableExtra("data");
        if (vo == null) {
            id = getIntent().getStringExtra("id");
            onRefresh();
        } else {
            id = vo.getOrderid() + "";
            initData(vo);
        }
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        View view = LayoutInflater.from(this).inflate(R.layout.header_repair_detail, null);
        repair_detail_orderid = (TextView) view.findViewById(R.id.repair_detail_orderid);
        repair_detail_state = (TextView) view.findViewById(R.id.repair_detail_state);
        repair_detail_type = (TextView) view.findViewById(R.id.repair_detail_type);
        repair_detail_time = (TextView) view.findViewById(R.id.repair_detail_time);
        repair_detail_person = (TextView) view.findViewById(R.id.repair_detail_person);
        repair_detail_address = (TextView) view.findViewById(R.id.repair_detail_address);
        repair_detail_description = (TextView) view.findViewById(R.id.repair_detail_description);
        repair_detail_employee = (TextView) view.findViewById(R.id.repair_detail_employee);
        repair_detail_finishtime = (TextView) view.findViewById(R.id.repair_detail_finishtime);
        property_opinion = (TextView) view.findViewById(R.id.property_opinion);
        owner_opinion = (TextView) view.findViewById(R.id.owner_opinion);
        tv_evaluation = (TextView) view.findViewById(R.id.tv_evaluation);
        iv_state = (ImageView) view.findViewById(R.id.iv_state);
        ll_empnm = (LinearLayout) view.findViewById(R.id.ll_empnm);
        ll_finish_time = (LinearLayout) view.findViewById(R.id.ll_finish_time);
        ll_property = (LinearLayout) view.findViewById(R.id.ll_property);
        ll_divide = view.findViewById(R.id.ll_divide);
        ll_detail_bottom = (LinearLayout) view.findViewById(R.id.ll_detail_bottom);
        gridview = (ColpencilGridView) view.findViewById(R.id.gridview);
        ll_employee_number = (LinearLayout) view.findViewById(R.id.ll_employee_number);
        employee_number = (TextView) view.findViewById(R.id.employee_number);
        ll_rudio = (LinearLayout) view.findViewById(R.id.ll_rudio);
        tv_music = (TextView) view.findViewById(R.id.tv_music);
        ll_no_fill = (LinearLayout) view.findViewById(R.id.ll_no_fill);
        tv_fill = (TextView) view.findViewById(R.id.tv_fill);
        imageView = (ImageView) view.findViewById(R.id.iv_music);
        iv_phone = (ImageView) view.findViewById(R.id.iv_phone);
        listview.addHeaderView(view);
        listview.setAdapter(new NullAdapter(this, new ArrayList<String>(), R.layout.item_null));
        adapter = new ImageSelectAdapter2(this, strings, R.layout.upload_img_item3);
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
                Intent intent = new Intent(RepairDetailActivity.this, PhotoActivity.class);
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

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    private void initData(final RepairHistory vo) {
        repair_detail_orderid.setText(vo.getRepair_code());
        repair_detail_time.setText(vo.getAddtime());
        repair_detail_description.setText("描述：" + vo.getDescription());
        repair_detail_employee.setText(vo.getEmpnm());
        repair_detail_finishtime.setText(vo.getCompletm());
        property_opinion.setText(vo.getProperty_opinion());
        repair_detail_state.setText(vo.getStatu());
        repair_detail_person.setText(vo.getUsername());
        repair_detail_address.setText(vo.getBook_site());
        strings.clear();
        strings.addAll(vo.getPics());
        adapter.notifyDataSetChanged();
        if (vo.getState() == 0) {   //待指派
            repair_detail_state.setTextColor(getResources().getColor(R.color.main_red));
            ll_empnm.setVisibility(View.GONE);
            ll_finish_time.setVisibility(View.GONE);
            ll_property.setVisibility(View.GONE);
            ll_divide.setVisibility(View.GONE);
            ll_detail_bottom.setVisibility(View.GONE);
            ll_employee_number.setVisibility(View.GONE);
        } else if (vo.getState() == 1) {        //已指派
            repair_detail_state.setTextColor(getResources().getColor(R.color.text_red));
            ll_empnm.setVisibility(View.VISIBLE);
            ll_finish_time.setVisibility(View.GONE);
            ll_property.setVisibility(View.GONE);
            ll_divide.setVisibility(View.GONE);
            ll_detail_bottom.setVisibility(View.GONE);
            ll_employee_number.setVisibility(View.VISIBLE);
            employee_number.setText(vo.getMobile());
        } else if (vo.getState() == 2) {        //处理中
            repair_detail_state.setTextColor(getResources().getColor(R.color.color_ffb651));
            ll_empnm.setVisibility(View.VISIBLE);
            ll_finish_time.setVisibility(View.GONE);
            ll_property.setVisibility(View.GONE);
            ll_divide.setVisibility(View.GONE);
            ll_detail_bottom.setVisibility(View.GONE);
            ll_employee_number.setVisibility(View.VISIBLE);
            employee_number.setText(vo.getMobile());
        } else {        //已完成
            repair_detail_state.setTextColor(getResources().getColor(R.color.text_green));
            ll_empnm.setVisibility(View.VISIBLE);
            ll_finish_time.setVisibility(View.VISIBLE);
            ll_property.setVisibility(View.VISIBLE);
            ll_employee_number.setVisibility(View.VISIBLE);
            employee_number.setText(vo.getMobile());
            if (vo.getIsfedbck() == 0) {        //未反馈
                ll_detail_bottom.setVisibility(View.GONE);
                ll_divide.setVisibility(View.GONE);
                ll_no_fill.setVisibility(View.VISIBLE);
            } else {
                ll_detail_bottom.setVisibility(View.VISIBLE);
                ll_divide.setVisibility(View.VISIBLE);
                ll_no_fill.setVisibility(View.GONE);
                owner_opinion.setText(vo.getDetail_description());
                if (vo.getScore() == 0) {       //很差
                    tv_evaluation.setText("很差");
                    tv_evaluation.setTextColor(getResources().getColor(R.color.main_red));
                    iv_state.setImageResource(R.mipmap.agre_press);
                } else if (vo.getScore() == 1) {        //一般
                    tv_evaluation.setText("一般");
                    tv_evaluation.setTextColor(getResources().getColor(R.color.text_orage));
                    iv_state.setImageResource(R.mipmap.general);
                } else {    //不错
                    tv_evaluation.setText("不错");
                    tv_evaluation.setTextColor(getResources().getColor(R.color.main_blue));
                    iv_state.setImageResource(R.mipmap.satisfaction_press);
                }
            }
        }
        tv_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RepairDetailActivity.this, NewFeedBackActivity.class);
                intent.putExtra("orderid", vo.getOrderid() + "");
                intent.putExtra("type", "0");
                startActivity(intent);
            }
        });
        if (vo.getRepair_type() == 0) {
            repair_detail_type.setText("家庭报修");
        } else {
            repair_detail_type.setText("公共报修");
        }
        if (TextUtils.isEmpty(vo.getUrl_audio())) {
            ll_rudio.setVisibility(View.GONE);
        } else {
            ll_rudio.setVisibility(View.VISIBLE);
        }
        if (((vo.getAudio_duration() / 60) % 60) > 0) {
            tv_music.setText((vo.getAudio_duration() / 60) % 60 + "." + vo.getAudio_duration() % 60 + "”");
        } else {
            tv_music.setText((vo.getAudio_duration() % 60 + "”"));
        }
        ll_rudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download(vo.getUrl_audio());
            }
        });
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + vo.getMobile()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    public void loadData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id + "");
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadRepairDetail(params)
                .map(new Func1<ResultInfo<RepairHistory>, ResultInfo<RepairHistory>>() {
                    @Override
                    public ResultInfo<RepairHistory> call(ResultInfo<RepairHistory> repairHistoryResultInfo) {
                        return repairHistoryResultInfo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<RepairHistory>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        swipe.setRefreshing(false);
                    }

                    @Override
                    public void onNext(ResultInfo<RepairHistory> repairHistoryResultInfo) {
                        swipe.setRefreshing(false);
                        if (repairHistoryResultInfo.code == 0) {
                            initData(repairHistoryResultInfo.data);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("feed", observable);
        if (util.player != null && util.player.isPlaying()) {
            util.stop();
        }
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
