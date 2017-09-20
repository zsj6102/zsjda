package com.colpencil.propertycloud.View.Fragments.CloseManager;


import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.EstateInfo;
import com.colpencil.propertycloud.Bean.HouseInfo;
import com.colpencil.propertycloud.Bean.ImagePreview;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Present.CloseManager.ReparlPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.PlayerUtil;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.Ui.AVLoadingIndicatorView;
import com.colpencil.propertycloud.View.Activitys.CloseManager.ChangeAdressActivity;
import com.colpencil.propertycloud.View.Activitys.Common.ImagePreviewActivity;
import com.colpencil.propertycloud.View.Activitys.Home.RepairsHistoryActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.ImageSelectAdapter;
import com.colpencil.propertycloud.View.Fragments.BaseFragment;
import com.colpencil.propertycloud.View.Imples.TenementRepairsView;
import com.google.gson.Gson;
import com.jph.takephoto.model.TResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;
import com.umeng.analytics.MobclickAgent;

import org.feezu.liuli.timeselector.TimeSelector;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * @author 陈 宝
 * @Description:家庭报修
 * @Email 1041121352@qq.com
 * @date 2016/11/29
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_family_repair
)
public class FamilyRepairFragment extends BaseFragment implements TenementRepairsView, OnClickListener, PlayerUtil.OnCompleteListener {

    @Bind(R.id.text_family_address)
    TextView text_family_address;
    @Bind(R.id.tv_choose_address)
    Button tv_choose_address;
    @Bind(R.id.tv_long_press)
    TextView tv_press;
    @Bind(R.id.ll_record_time)
    LinearLayout ll_record_time;
    @Bind(R.id.iv_lu)
    ImageView iv_lu;
    @Bind(R.id.iv_del)
    ImageView iv_del;
    @Bind(R.id.ll_select_time)
    LinearLayout ll_select_time;
    @Bind(R.id.gl_pic)
    ColpencilGridView recycler;
    @Bind(R.id.et_content)
    EditText et_content;
    @Bind(R.id.btn_submit)
    Button btn_submit;
    @Bind(R.id.ll_choose_img)
    LinearLayout ll_choose_img;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.indicator_left)
    AVLoadingIndicatorView record_left;
    @Bind(R.id.indicator_right)
    AVLoadingIndicatorView record_right;
    @Bind(R.id.iv_left)
    ImageView iv_left;
    @Bind(R.id.iv_right)
    ImageView iv_right;
    @Bind(R.id.iv_play)
    ImageView iv_play;
    @Bind(R.id.rl_record)
    RelativeLayout rl_record;
    @Bind(R.id.tv_record_time)
    TextView tv_record_time;
    @Bind(R.id.ll_phone)
    LinearLayout ll_phone;

    private ImageSelectAdapter adapter;
    private ArrayList<String> selImageList = new ArrayList<>(); //当前选择的所有图片
    private int maxImgCount = 3;               //允许选择图片最大数
    private List<File> fileList = new ArrayList<>();    //压缩后的图片

    private File mRecAudioFile; // 录制的音频文件
    private File mRecAudioPath; // 录制的音频文件路徑
    private MediaRecorder mMediaRecorder;// MediaRecorder对象
    private String strTempFile = "recaudio_";// 零时文件的前缀
    private String adiPath;
    private int recordTime;
    private PlayerUtil playerUtil;
    private boolean isPlaying = false;

    private ReparlPresent present;
    private EstateInfo info = new EstateInfo();
    public final int IMAGE_ITEM_DELL = 100;

    @Override
    protected void initViews(View view) {
        tv_choose_address.setOnClickListener(this);
        ll_choose_img.setOnClickListener(this);
        ll_select_time.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        iv_del.setOnClickListener(this);
        ll_phone.setOnClickListener(this);
        iv_lu.setOnTouchListener(new VoiceTouch());
        playerUtil = new PlayerUtil();
        playerUtil.setOnCompleteListener(this);
        initWidget();
        tv_time.setText(TimeUtil.getTimeStrFromMillis(System.currentTimeMillis()));
        DialogTools.showLoding(getActivity(), "温馨提示", "正在获取数据中...");
        present.loadEstate();
    }

    private void initWidget() {
        adapter = new ImageSelectAdapter(getActivity(), selImageList, R.layout.upload_img_item3);
        recycler.setAdapter(adapter);
        recycler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImagePreview imagePreview = new ImagePreview();
                imagePreview.imageList.addAll(selImageList);
                Gson gson = new Gson();
                String json = gson.toJson(imagePreview);
                Intent intent = new Intent(getActivity(), ImagePreviewActivity.class);
                intent.putExtra("imageList", json);
                intent.putExtra("position", position);
                startActivityForResult(intent, IMAGE_ITEM_DELL);
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new ReparlPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        for (int i = 0; i < result.getImages().size(); i++) {
            selImageList.add(result.getImages().get(i).getCompressPath());
            fileList.add(new File(result.getImages().get(i).getCompressPath()));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_choose_address) {
            Intent intent = new Intent(getActivity(), ChangeAdressActivity.class);
            intent.putExtra("data", info);
            startActivityForResult(intent, 1);
        } else if (id == R.id.ll_choose_img) {
            openSelect(false, maxImgCount - selImageList.size());
        } else if (id == R.id.ll_select_time) {
            TimeSelector timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
                @Override
                public void handle(String time) {
                    tv_time.setText(time.substring(0, 16).replace("-", "-"));
                }
            }, TimeUtil.getTimeStrFromMillis(System.currentTimeMillis()), "2019-11-29 21:54");
            timeSelector.show();
        } else if (id == R.id.iv_play) {
            if (isPlaying) {
                playerUtil.stop();
                isPlaying = false;
                iv_play.setImageResource(R.mipmap.record_pause);
                iv_left.setVisibility(View.VISIBLE);
                iv_right.setVisibility(View.VISIBLE);
                record_left.setVisibility(View.GONE);
                record_right.setVisibility(View.GONE);
                handler.removeMessages(1);
                updataView(recordTime);
            } else {
                if (!TextUtils.isEmpty(adiPath)) {
                    playerUtil.start(adiPath);
                    isPlaying = true;
                    iv_play.setImageResource(R.mipmap.record_stop);
                    iv_left.setVisibility(View.GONE);
                    iv_right.setVisibility(View.GONE);
                    record_left.setVisibility(View.VISIBLE);
                    record_right.setVisibility(View.VISIBLE);
                    mTime = 0;
                    handler.sendEmptyMessage(1);
                } else {
                    NotificationTools.show(getActivity(), "您还未录音。");
                }
            }
        } else if (id == R.id.btn_submit) {
            MobclickAgent.onEvent(getActivity(), "comitRepailWithAudio");
            String description = et_content.getText().toString();
            String booktime = tv_time.getText().toString();
            String booksite = text_family_address.getText().toString();
            String mobile;
            if (TextUtils.isEmpty(info.getMobile())) {
                mobile = SharedPreferencesUtil.getInstance(getActivity()).getString("mobile");
            } else {
                mobile = info.getMobile();
            }
            String comuid = SharedPreferencesUtil.getInstance(getActivity()).getString("comuid");
            if (TextUtils.isEmpty(adiPath) && TextUtils.isEmpty(description)) {
                ToastTools.showShort(getActivity(), "请录音或者输入描述");
            } else {
                if (booktime.equals("尽快维修")) {
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
                    booktime = sdf.format(date);
                }
                DialogTools.showLoding(getActivity(), "温馨提示", "正在提交报修");
                present.submit(0, description, adiPath, booktime, booksite, mobile, "agree", comuid, fileList, info.getBuildId(), info.getUnitId(), info.getHouseId(), recordTime + "");
            }
        } else if (id == R.id.iv_del) {
            if (!TextUtils.isEmpty(adiPath)) {
                new MaterialDialog.Builder(getActivity())
                        .title("温馨提示")
                        .content("是否删除录音文件？")
                        .positiveText("是")
                        .negativeText("否")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (isPlaying) {
                                    playerUtil.stop();
                                }
                                new File(adiPath).delete();
                                adiPath = null;
                                iv_del.setVisibility(View.GONE);
                                iv_play.setVisibility(View.GONE);
                                rl_record.setVisibility(View.GONE);
                                iv_lu.setVisibility(View.VISIBLE);
                                tv_press.setVisibility(View.VISIBLE);
                                recordTime = 0;
                                mTime = 0;
                                handler.removeMessages(1);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            }
                        })
                        .show();
            } else {
                NotificationTools.show(getActivity(), "您还未录音。");
            }
        } else if (id == R.id.ll_phone) {
            MobclickAgent.onEvent(getActivity(), "repairTel");
            String phone;
            if (TextUtils.isEmpty(info.getPropertytel())) {
                phone = SharedPreferencesUtil.getInstance(getActivity()).getString("propertytel");
            } else {
                phone = info.getPropertytel();
            }
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + phone));
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_ITEM_DELL) {
            selImageList.clear();
            fileList.clear();
            if (data != null) {
                String dataStringExtra = data.getStringExtra("change");
                Gson gson = new Gson();
                ImagePreview imagePreview = gson.fromJson(dataStringExtra, ImagePreview.class);
                selImageList.addAll(imagePreview.imageList);
                for (int i = 0; i < selImageList.size(); i++) {
                    fileList.add(new File(selImageList.get(i)));
                }
            }
            adapter.notifyDataSetChanged();
        } else if (requestCode == 1) {
            if (data != null) {
                info = (EstateInfo) data.getSerializableExtra("data");
                text_family_address.setText(info.getCellName() + info.getBuildName() + "栋" + info.getUnitName() + "单元" + info.getHourseName());
            }
        }
    }

    private void startRecordAudio() {
        try {
            if (!initRecAudioPath()) {
                return;
            }

			/* ①Initial：实例化MediaRecorder对象 */
            mMediaRecorder = new MediaRecorder();
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default
			 * THREE_GPP(3gp格式，H263视频
			 * /ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
			 */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            /* ②设置输出文件的路径 */
            try {
                mRecAudioFile = File.createTempFile(strTempFile, ".amr",
                        mRecAudioPath);

            } catch (Exception e) {
                e.printStackTrace();
            }
            mMediaRecorder.setOutputFile(mRecAudioFile.getAbsolutePath());
            /* ③准备 */
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
            //开始计时
            handler.sendEmptyMessage(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecordAudio() {
        if (mRecAudioFile != null) {
            if (mMediaRecorder != null) {
                /* ⑤停止录音 */
                mMediaRecorder.stop();
                /* 将录音文件添加到List中 */
                addItem(mRecAudioFile);
                /* ⑥释放MediaRecorder */
                mMediaRecorder.release();
                mMediaRecorder = null;
                if (!hasRecord) {
                    hasRecord = true;
                    recordTime = mTime;
                }
                handler.sendEmptyMessage(1);
                //停止计时
                handler.removeMessages(1);
            }
        }
    }

    private boolean initRecAudioPath() {
        if (sdcardIsValid()) {
            String path = Environment.getExternalStorageDirectory().toString()
                    + File.separator + "record";// 得到SD卡得路径
            mRecAudioPath = new File(path);
            if (!mRecAudioPath.exists()) {
                mRecAudioPath.mkdirs();
            }
        } else {
            mRecAudioPath = null;
        }
        return mRecAudioPath != null;
    }

    private boolean sdcardIsValid() {
        if (Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            Toast.makeText(getActivity(), "没有SD卡", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void addItem(File item) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("###,###,###,###,###");
        adiPath = item.getPath();
    }

    private Map<String, Object> getOneItem(File file) {
        Map<String, Object> map = new HashMap<>();
        DecimalFormat df = new DecimalFormat();

        df.applyPattern("###,###,###,###,###");

        map.put("text", file.getName());
        map.put("text_length", df.format(file.length()));
        map.put("text_time", GetFilePlayTime(file));
        return map;
    }

    private String GetFilePlayTime(File file) {
        java.util.Date date;
        SimpleDateFormat sy1;
        String dateFormat = "error";

        try {
            sy1 = new SimpleDateFormat("HH:mm:ss");//设置为时分秒的格式

            //使用媒体库获取播放时间
            MediaPlayer mediaPlayer;
            mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(file.toString()));

            //使用Date格式化播放时间mediaPlayer.getDuration()
            date = sy1.parse("00:00:00");
            date.setTime(mediaPlayer.getDuration() + date.getTime());//用消除date.getTime()时区差
            dateFormat = sy1.format(date);

            mediaPlayer.release();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("dateFormat", dateFormat);
        return dateFormat;
    }

    @Override
    public void complete() {
        isPlaying = false;
        iv_play.setImageResource(R.mipmap.record_pause);
        iv_left.setVisibility(View.VISIBLE);
        iv_right.setVisibility(View.VISIBLE);
        record_left.setVisibility(View.GONE);
        record_right.setVisibility(View.GONE);
        handler.removeMessages(1);
        updataView(recordTime);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        if (playerUtil != null) {
            playerUtil.stop();
        }
    }

    @Override
    public void result(ResultInfo resultInfo) {
        DialogTools.dissmiss();
        if (resultInfo.code == 0) {
            WarnDialog.showInfoAndIntent(getActivity(), "已收到您的报修申请，我们将在2小时内进行处理", RepairsHistoryActivity.class);
        } else if (resultInfo.code == 3) {
            ToastTools.showShort(getActivity(), resultInfo.message);
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } else if (resultInfo.code == 1) {
            ToastTools.showShort(getActivity(), resultInfo.message);
        }
    }

    @Override
    public void estateResult(ResultListInfo<HouseInfo> result) {
        DialogTools.dissmiss();
        if (result.code == 0) {
            List<HouseInfo> list = result.data;
            if (list != null && list.size() > 0) {
                HouseInfo info = list.get(0);
                text_family_address.setText(info.getShort_name()
                        + info.getBuilding_name() + "栋"
                        + info.getUnit_name() + "单元"
                        + info.getHouse_name());
                FamilyRepairFragment.this.info.setCityName(info.getCity_name());
                FamilyRepairFragment.this.info.setCellName(info.getShort_name());
                FamilyRepairFragment.this.info.setCellId(info.getCommunity_id());
                FamilyRepairFragment.this.info.setUnitName(info.getUnit_name());
                FamilyRepairFragment.this.info.setUnitId(info.getUnit_id());
                FamilyRepairFragment.this.info.setHourseName(info.getHouse_name());
                FamilyRepairFragment.this.info.setHouseId(info.getHouse_id());
                FamilyRepairFragment.this.info.setBuildName(info.getBuilding_name());
                FamilyRepairFragment.this.info.setBuildId(info.getBuilding_id());
                FamilyRepairFragment.this.info.setPropertytel(info.getPropertytel());
            }
        }
    }

    @Override
    public void cellResult(ResultListInfo<CellInfo> result) {
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mTime += 1;
                    updataView(mTime);
                    handler.sendEmptyMessageDelayed(1, 1000);
                    break;
            }
        }
    };
    private boolean hasRecord = false;
    private int mTime = 0;

    private void updataView(int time) {
        int minute = (time / 60) % 60;
        int second = (time % 60);
        if (second < 10) {
            if (minute < 10) {
                tv_record_time.setText("0" + minute + ":" + "0" + second);
            } else {
                tv_record_time.setText(minute + ":" + "0" + second);
            }
        } else {
            if (minute < 10) {
                tv_record_time.setText("0" + minute + ":" + second);
            } else {
                tv_record_time.setText(minute + ":" + second);
            }
        }
    }

    class VoiceTouch implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    tv_press.setVisibility(View.GONE);
                    rl_record.setVisibility(View.VISIBLE);
                    iv_left.setVisibility(View.GONE);
                    iv_right.setVisibility(View.GONE);
                    record_left.setVisibility(View.VISIBLE);
                    record_right.setVisibility(View.VISIBLE);
                    startRecordAudio();
                    break;
                case MotionEvent.ACTION_UP:
                    iv_play.setVisibility(View.VISIBLE);
                    iv_del.setVisibility(View.VISIBLE);
                    iv_lu.setVisibility(View.GONE);
                    iv_left.setVisibility(View.VISIBLE);
                    iv_right.setVisibility(View.VISIBLE);
                    record_left.setVisibility(View.GONE);
                    record_right.setVisibility(View.GONE);
                    SystemClock.sleep(500);
                    stopRecordAudio();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    iv_del.setVisibility(View.GONE);
                    iv_play.setVisibility(View.GONE);
                    rl_record.setVisibility(View.GONE);
                    iv_lu.setVisibility(View.VISIBLE);
                    tv_press.setVisibility(View.VISIBLE);
                    recordTime = 0;
                    mTime = 0;
                    handler.sendEmptyMessage(1);
                    handler.removeMessages(1);
                    SystemClock.sleep(500);
                    if (mMediaRecorder != null) {
                        mMediaRecorder.stop();
                    }
                    break;
            }
            return true;
        }
    }
}
