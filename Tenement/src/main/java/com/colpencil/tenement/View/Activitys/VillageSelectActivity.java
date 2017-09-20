package com.colpencil.tenement.View.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Record;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Bean.VillageBus;
import com.colpencil.tenement.Present.Home.VillageSelectPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Ui.BottomDialog;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Imples.VillageSelectView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @Description: 适配带有选择小区
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/14
 */
public abstract class VillageSelectActivity extends CheckPermissionsActivity implements VillageSelectView{


    public VillageSelectPresent present;
    public DialogPlus dialogPlus;
    private RelativeLayout rl_select_villige;
    public TextView tv_village;
    private ImageView cb_switch;
    private Button btn_ok;
    public int self;
    private boolean isChecked = false;

    private int hasAll = 0;

    private int flag =0; // 0 选择顶部开启   1 选择底部开启

    private View.OnClickListener btnOkListener;

    private BottomDialog.ButtonCallback buttonCallback;

    public List<Village> lists = new ArrayList<>();
    public String communityId = "";
    private String plot;
    private String plotId;
    private String village;

    public int getHasAll() {
        return hasAll;
    }

    public void setHasAll(int hasAll) {
        this.hasAll = hasAll;
    }

    @Override
    protected void initViews(View view) {
        present.loadVillageDefu();
    }

    public String getPlot() {
        return plot;
    }

    public String getPlotId() {
        return plotId;
    }

    public void setButtonCallback(BottomDialog.ButtonCallback buttonCallback) {
        this.buttonCallback = buttonCallback;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setBtnOkListener(View.OnClickListener btnOkListener) {
        this.btnOkListener = btnOkListener;
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new VillageSelectPresent();
        return present;
    }

    public void showTopDialog(){
        View view = View.inflate(this, R.layout.dialog_select_village, null);
        dialogPlus = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(view))
                .setCancelable(true)
                .setGravity(Gravity.TOP)
                .setExpanded(false)
                .create();
        dialogPlus
                .show();
        rl_select_villige = (RelativeLayout) view.findViewById(R.id.rl_select_villige);
        tv_village = (TextView) view.findViewById(R.id.tv_village);
        tv_village.setText(village);
        cb_switch = (ImageView) view.findViewById(R.id.switch_img);
        btn_ok = (Button) view.findViewById(R.id.btn_ok);

        rl_select_villige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVilageSelectBottom();
            }
        });

        //记住当前状态
        if(isChecked){
            cb_switch.setImageDrawable(getResources().getDrawable(R.mipmap.open));
        } else {
            cb_switch.setImageDrawable(getResources().getDrawable(R.mipmap.close));
        }

        cb_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChecked == false){
                    cb_switch.setImageDrawable(getResources().getDrawable(R.mipmap.open));
                    self = 1;
                    isChecked = true;
                } else {
                    cb_switch.setImageDrawable(getResources().getDrawable(R.mipmap.close));
                    self = 0;
                    isChecked = false;
                }
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VillageBus villageBus = new VillageBus();
                villageBus.setPlotId(communityId);
                villageBus.setFlag(0);
                villageBus.setTpye(self);
                RxBus.get().post("repair",villageBus);
//                RxBus.get().post("self",self);
                dialogPlus.dismiss();
            }
        });
    }

    /**
     * 直接显示底部选择小区的框
     */
    public void showVilageSelectBottom(){
        DialogTools.showLoding(VillageSelectActivity.this,"温馨提示","正在获取全部小区");
        present.loadVillage();
    }

    @Override
    public void loadCommunityDefu(ListCommonResult<Village> result) {
        int code = result.getCode();
        String message = result.getMessage();
        switch (code){
            case 0:
                List<Village> data = result.getData();
                if (data.size()!=0){
                    plot = data.get(0).shortName;
                    plotId = data.get(0).plotId;
                }else {
                    plot = "";
                    plotId = "";
                }
                break;
            case 1:
                ToastTools.showShort(this,false,message);
                break;
            case 2:
                ToastTools.showShort(this,false,message);
                break;
            case 3:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }

    @Override
    public void loadCommunity(ListCommonResult<Village> result) {
        DialogTools.dissmiss();
        int code = result.getCode();
        String message = result.getMessage();
        List<Village> data = result.getData();
        if (hasAll==0){
            Village village2 = new Village();
            village2.plot = "全部小区";
            village2.plotId = "";
            village2.shortName = "全部小区";
            data.add(0,village2);
        }
        lists.addAll(data);
        switch (code){
            case 0:
                List<String> villageList = new ArrayList<>();
                for (Village village: data){
                    villageList.add(village.shortName);
                }
                if (flag==0){
                    new BottomDialog.Builder(this)
                            .setTitle("选择小区")
                            .setDataList(villageList)
                            .setPositiveText("确认")
                            .setNegativeText("取消")
                            .onPositive(dialog -> {
                                communityId = data.get(dialog.position).plotId;
                                village = villageList.get(dialog.position);
                                tv_village.setText(villageList.get(dialog.position));
                            })
                            .onNegative(dialog -> {

                            }).show();
                }else {
                    new BottomDialog.Builder(this)
                            .setTitle("选择小区")
                            .setDataList(villageList)
                            .setPositiveText("确认")
                            .setNegativeText("取消")
                            .onPositive(buttonCallback)
                            .onNegative(dialog -> {

                            }).show();
                }

                break;
            case 1:
                ToastTools.showShort(this,false,message);
                break;
            case 2:
                ToastTools.showShort(this,false,message);
                break;
            case 3:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }

    @Override
    public void loadError(String msg) {
        DialogTools.dissmiss();
        ToastTools.showShort(this,false,"请求失败！");
    }

    @Override
    public void loadMarker(ListCommonResult<Record> result) {

    }
}
