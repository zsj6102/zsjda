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
import android.view.MotionEvent;
import android.view.View;
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
import com.colpencil.propertycloud.Bean.DiviceInfo;
import com.colpencil.propertycloud.Bean.HouseInfo;
import com.colpencil.propertycloud.Bean.ImagePreview;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Present.CloseManager.ReparlPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.PlayerUtil;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.Ui.AVLoadingIndicatorView;
import com.colpencil.propertycloud.Ui.BottomDialog;
import com.colpencil.propertycloud.Ui.RecordView;
import com.colpencil.propertycloud.View.Activitys.CloseManager.ScanCodeActivity;
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
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextUtil;
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
        contentViewId = R.layout.fragment_common_repair
)
public class CommonRepairFragment extends BaseFragment implements View.OnClickListener, TenementRepairsView, PlayerUtil.OnCompleteListener {

    @Bind(R.id.ll_choose_img)
    LinearLayout ll_choose_img;

    @Bind(R.id.ll_select_time)
    LinearLayout ll_select_time;

    @Bind(R.id.tv_long_press)
    TextView tv_press;

    @Bind(R.id.iv_lu)
    ImageView iv_lu;

    @Bind(R.id.iv_del)
    ImageView iv_del;

    @Bind(R.id.gl_pic)
    ColpencilGridView recycler;

    @Bind(R.id.et_content)
    EditText et_content;

    @Bind(R.id.btn_submit)
    Button btn_submit;

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

    @Bind(R.id.ll_cell)
    LinearLayout ll_cell;

    @Bind(R.id.tv_cell_name)
    TextView tv_cell_name;

    @Bind(R.id.scan_code)
    Button scan_code;

    @Bind(R.id.et_address)
    EditText et_address;

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
    private List<CellInfo> cells = new ArrayList<>();
    private DiviceInfo info;
    private int comuid = -1;
    public final int IMAGE_ITEM_DELL = 100;
    private String phone;

    @Override
    protected void initViews(View view) {
        ll_choose_img.setOnClickListener(this);
        ll_select_time.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        ll_cell.setOnClickListener(this);
        scan_code.setOnClickListener(this);
        iv_del.setOnClickListener(this);
        ll_phone.setOnClickListener(this);
        iv_lu.setOnTouchListener(new VoiceTouch());
        playerUtil = new PlayerUtil();
        playerUtil.setOnCompleteListener(this);
        tv_time.setText(TimeUtil.getTimeStrFromMillis(System.currentTimeMillis()));
        initWidget();
        present.loadCell();
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
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_choose_img) {
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
            String mobile = SharedPreferencesUtil.getInstance(getActivity()).getString("mobile");
            if (TextUtils.isEmpty(adiPath) && TextUtils.isEmpty(description)) {
                ToastTools.showShort(getActivity(), "录音或者描述必须选填一个");
            } else if (TextUtils.isEmpty(et_address.getText().toString())) {
                ToastTools.showShort(getActivity(), "请先填写设备名称或者扫描二维码获取设备名称");
            } else if (comuid == -1) {
                ToastTools.showShort(getActivity(), "请先选择小区");
            } else {
                if (booktime.equals("尽快维修")) {
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    booktime = sdf.format(date);
                }
                String booksite = tv_cell_name.getText().toString() + et_address.getText().toString();
                DialogTools.showLoding(getActivity(), "温馨提示", "正在提交...");
                present.submit(1, description, adiPath, booktime, booksite, mobile, "agree", comuid + "", fileList, 0, 0, 0, recordTime + "");
            }
        } else if (id == R.id.ll_cell) {
            showCellsDialog();
        } else if (id == R.id.scan_code) {
            Intent intent = new Intent(getActivity(), ScanCodeActivity.class);
            startActivityForResult(intent, 1);
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
            if (TextUtils.isEmpty(phone)) {
                phone = SharedPreferencesUtil.getInstance(getActivity()).getString("propertytel");
            }
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + phone));
            startActivity(intent);
        }
    }

    private void showCellsDialog() {
        final List<String> strings = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++) {
            strings.add(cells.get(i).getShortName());
        }
        new BottomDialog.Builder(getActivity())
                .setTitle("选择小区")
                .setDataList(strings)
                .setPositiveText("确认")
                .setNegativeText("取消")
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(@NonNull BottomDialog dialog) {
                        tv_cell_name.setText(strings.get(dialog.position));
                        comuid = cells.get(dialog.position).getCommunity_id();
                        phone = cells.get(dialog.position).getPropertytel();
                    }
                }).onNegative(new BottomDialog.ButtonCallback() {
            @Override
            public void onClick(@NonNull BottomDialog dialog) {
            }
        }).show();
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
                info = (DiviceInfo) data.getSerializableExtra("data");
                scan_code.setText("重新扫描");
                et_address.setText(info.getInstallLocation() + info.getEqName());
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
    }

    @Override
    public void cellResult(ResultListInfo<CellInfo> result) {
        DialogTools.dissmiss();
        if (result.code == 0) {
            cells.addAll(result.data);
            if (cells != null && cells.size() > 0) {
                tv_cell_name.setText(cells.get(0).getShortName());
                comuid = cells.get(0).getCommunity_id();
                phone = cells.get(0).getPropertytel();
            }
        }
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
