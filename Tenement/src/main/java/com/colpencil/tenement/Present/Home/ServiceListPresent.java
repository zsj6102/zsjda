package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.Online;
import com.colpencil.tenement.Model.Home.OnlineListModel;
import com.colpencil.tenement.Model.Imples.Home.IOnlineListModel;
import com.colpencil.tenement.Model.Imples.OnlineTalk.IOnlineTalkListModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.OnlineListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import org.jivesoftware.smack.Roster;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 客服列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/24
 */
public class ServiceListPresent extends ColpencilPresenter<OnlineListView> {

    private IOnlineListModel model;

    public ServiceListPresent(){
        model = new OnlineListModel();
    }

    public void getList(Roster roster){
        model.getOnlineList(roster);
        Subscriber<List<Online>> subscriber = new Subscriber<List<Online>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(List<Online> list) {
                mView.onlineList(list);
            }
        };
        model.sub(subscriber);
    }
}