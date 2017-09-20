package com.colpencil.tenement.View.Fragments.OnlineTalk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OnlineListUser;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Present.OnlineTalk.OnlineListPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Ui.BottomDialog;
import com.colpencil.tenement.View.Activitys.OnlineTalk.VoiceCallActivity;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Adpaters.OnlineTalkListAapter;
import com.colpencil.tenement.View.Imples.OnlineTalkListView;
import com.easemob.EMCallBack;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.CmdMessageBody;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.MessageBody;
import com.easemob.exceptions.EaseMobException;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;


/**
 * @author 汪亮
 * @Description:
 * @Email DramaScript@outlook.com
 * @date 2016/6/30
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_online_talk
)
public class OnlineTalkListFragment extends ColpencilFragment implements OnlineTalkListView {

    @Bind(R.id.ll_left)
    RelativeLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_left)
    TextView tv_left;

    @Bind(R.id.tvEmpty)
    TextView tvEmpty;

    @Bind(R.id.expend_online_talk)
    ExpandableListView expend_online_talk;

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

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    private OnlineListPresent present;
    private OnlineTalkListAapter onlineTalkListAapter;

    private List<OnlineListUser> lists = new ArrayList<>();
    private String communityId = "0";

    public List<Village> VillageList = new ArrayList<>();
    private String shortName;

    @Override
    protected void initViews(View view) {
        communityId = SharedPreferencesUtil.getInstance(getActivity()).getString("plotId");
        shortName = SharedPreferencesUtil.getInstance(getActivity()).getString("shortName");
        if (TextUtils.isEmpty(shortName)) {
            tv_left.setText("未选择小区");
        } else {
            tv_left.setText(shortName);
        }
        expend_online_talk.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        tv_title.setText(R.string.online_talk);
        ll_rigth.setVisibility(View.INVISIBLE);
        ll_rigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VoiceCallActivity.class);
                intent.putExtra("username", "test");
                intent.putExtra("isComingCall", false);
                startActivity(intent);
            }
        });
        expend_online_talk.setGroupIndicator(null);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVilageSelectBottom();
            }
        });
        loadData();
        expend_online_talk.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {
                String passWord = SharedPreferencesUtil.getInstance(getActivity()).getString("passWord");
                String talkname = SharedPreferencesUtil.getInstance(getActivity()).getString("talkname");
                if (lists.get(groupPosition).members.get(childPosition).username.equals(talkname)) {

                } else {
                    if (lists.get(groupPosition).members.get(childPosition).onlineStatus==0){
                        ToastTools.showShort(getActivity(),"对方不在线。");
                    }else {
                        MaterialDialog show = new MaterialDialog.Builder(getActivity())
                                .title(R.string.online_title)
                                .content(R.string.online_content)
                                .positiveText(R.string.online_yes)
                                .negativeText(R.string.online_no)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        ColpencilLogger.e("talkname=" + talkname + ",username=" + lists.get(groupPosition).members.get(childPosition).username);
                                        if (!EMChatManager.getInstance().isConnected()) {

                                            loginHuanXin(talkname, passWord);
                                            Toast.makeText(getActivity(), "连接服务器中。", Toast.LENGTH_SHORT).show();
                                        }
                                        if (TextUtils.isEmpty(lists.get(groupPosition).members.get(childPosition).username)) {
                                            Toast.makeText(getActivity(), "对方用户名为空", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        Intent intent = new Intent(getActivity(), VoiceCallActivity.class);
                                        intent.putExtra("username", lists.get(groupPosition).members.get(childPosition).username);
                                        intent.putExtra("isComingCall", false);
                                        intent.putExtra("name", lists.get(groupPosition).members.get(childPosition).membername);
                                        startActivity(intent);

                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    }
                                })
                                .show();
                    }

                }

                return false;
            }
        });



        // 注册一个cmd消息的BroadcastReceiver
        EMChat.getInstance().setAppInited();
        EMChatManager.getInstance().getChatOptions().setShowNotificationInBackgroud(false);
        IntentFilter cmdIntentFilter = new IntentFilter(EMChatManager.getInstance().getCmdMessageBroadcastAction());
        getActivity().registerReceiver(cmdMessageReceiver, cmdIntentFilter);

    }

    /**
     * cmd消息BroadcastReceiver
     */
    private BroadcastReceiver cmdMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //获取cmd message对象
            String msgId = intent.getStringExtra("msgid");
            EMMessage message = intent.getParcelableExtra("message");
            //获取消息body
            CmdMessageBody cmdMsgBody = (CmdMessageBody) message.getBody();
            String aciton = cmdMsgBody.action;//获取自定义action
            ColpencilLogger.e("--------------------aciton="+aciton);
            lists.clear();
            loadData();
            //获取扩展属性
            try {
                String attr=message.getStringAttribute("a");
            } catch (EaseMobException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 登陆环信
     *
     * @param talkname
     * @param pwd
     */
    private void loginHuanXin(String talkname, String pwd) {
        ColpencilLogger.e("talkname=" + talkname + "pwd=" + pwd);
        if (TextUtils.isEmpty(talkname) && TextUtils.isEmpty(talkname)) {
            Toast.makeText(getActivity(), "账号或密码不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        //这里先进行自己服务器的登录操作
        //自己服务器登录成功后再执行环信服务器的登录操作
        EMChatManager.getInstance().login(talkname, pwd, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        EMGroupManager.getInstance().loadAllGroups();
                        EMChatManager.getInstance().loadAllConversations();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.e("TAG", "登陆聊天服务器中 " + "progress:" + progress + " status:" + status);
            }

            @Override
            public void onError(int code, String message) {
                ColpencilLogger.e("登录在操作中返回失败 " + code + "即时通讯登陆失败" + message);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "即时通讯登陆失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 直接显示底部选择小区的框
     */
    public void showVilageSelectBottom() {
        DialogTools.showLoding(getActivity(), "温馨提示", "正在获取全部小区");
        present.loadVillage();
    }

    private void loadData() {
        present.getOnlineList(communityId);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new OnlineListPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void getOnlineList(ListCommonResult<OnlineListUser> onlineListUsers) {
        int code = onlineListUsers.getCode();
        if (code == 2) {
            // 数据为空
            expend_online_talk.setVisibility(View.GONE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText("请先选择小区！");
            btnReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadData();
                }
            });
        } else if (code == 3) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } else {
            expend_online_talk.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
        }
        lists.addAll(onlineListUsers.getData());
        onlineTalkListAapter = new OnlineTalkListAapter(onlineListUsers.getData(), getActivity());
        expend_online_talk.setAdapter(onlineTalkListAapter);
        onlineTalkListAapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg) {
        // 加载出错
        expend_online_talk.setVisibility(View.GONE);
        rlProgress.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
        rlEmpty.setVisibility(View.GONE);
        btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    @Override
    public void showVillageError(String msg) {
        DialogTools.dissmiss();
        ToastTools.showShort(getActivity(), false, "请求失败！");
    }

    @Override
    public void loadCommunity(ListCommonResult<Village> result) {
        DialogTools.dissmiss();
        int code = result.getCode();
        String message = result.getMessage();
        VillageList.addAll(result.getData());
        switch (code) {
            case 0:
                List<String> villageList = new ArrayList<>();
                for (Village village : result.getData()) {
                    villageList.add(village.plot);
                }
                new BottomDialog.Builder(getActivity())
                        .setTitle("选择小区")
                        .setDataList(villageList)
                        .setPositiveText("确认")
                        .setNegativeText("取消")
                        .onPositive(dialog -> {
                            communityId = result.getData().get(dialog.position).plotId;
                            tv_left.setText(result.getData().get(dialog.position).shortName);
                            loadData();
                        })
                        .onNegative(dialog -> {

                        }).show();
                break;
            case 1:
                ToastTools.showShort(getActivity(), message);
                break;
            case 2:
                break;
            case 3:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }
}
