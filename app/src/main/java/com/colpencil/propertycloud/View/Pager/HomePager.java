package com.colpencil.propertycloud.View.Pager;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.CellState;
import com.colpencil.propertycloud.Bean.ModuleInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.CloseManager.TenementRepairsActivity;
import com.colpencil.propertycloud.View.Activitys.Home.ComplaintActivity;
import com.colpencil.propertycloud.View.Activitys.Home.FitmentHomeActivity;
import com.colpencil.propertycloud.View.Activitys.Home.PayFeesActivity;
import com.colpencil.propertycloud.View.Activitys.Home.VilageSelectActivity;
import com.colpencil.propertycloud.View.Adapter.ModuleAdapter;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;

import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xiaohuihui on 2017/1/12.
 */
public class HomePager {

    private Context mContext;
    private ColpencilGridView gridview;
    public ModuleAdapter mAdapter;
    private List<ModuleInfo> modules;

    public HomePager(Context context, List<ModuleInfo> modules) {
        mContext = context;
        this.modules = modules;
    }

    public View inflateView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pager_module, null);
        gridview = (ColpencilGridView) view.findViewById(R.id.module_gridview);
        mAdapter = new ModuleAdapter(mContext, modules, R.layout.item_module);
        gridview.setAdapter(mAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModuleInfo info = modules.get(position);
                RxBusMsg msg = new RxBusMsg();
                msg.setType(3);
                msg.setContent(info.url);
                msg.setId(info.serviceId);
                msg.setMsg(info.code);
                msg.setIsOwner(info.isOwner);
                RxBus.get().post(VilageSelectActivity.class.getSimpleName(), msg);
                if (info.code.equals("_choujiang")) {
                    mAdapter.clearAnim();
                }
            }
        });
        return view;
    }

    public void startAnim() {
        mAdapter.shake();
    }

}
