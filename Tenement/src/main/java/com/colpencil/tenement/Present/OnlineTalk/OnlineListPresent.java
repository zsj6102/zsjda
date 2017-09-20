package com.colpencil.tenement.Present.OnlineTalk;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OnlineListUser;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Model.Imples.OnlineTalk.IOnlineTalkListModel;
import com.colpencil.tenement.Model.OnlineTalk.OnlineTalkListModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.OnlineTalkListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 在线对讲列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/18
 */
public class OnlineListPresent extends ColpencilPresenter<OnlineTalkListView>{

    private IOnlineTalkListModel iOnlineTalkListModel;

    public OnlineListPresent(){
        iOnlineTalkListModel = new OnlineTalkListModel();
    }

    public void getOnlineList(String communityId){
        iOnlineTalkListModel.getOnlineTalkList(communityId);
        Subscriber<ListCommonResult<OnlineListUser>> subscriber = new Subscriber<ListCommonResult<OnlineListUser>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<OnlineListUser> onlineListUsers) {
                int code = onlineListUsers.getCode();
                String message = onlineListUsers.getMessage();
                if (code==0||code==2||code==3){
                    mView.getOnlineList(onlineListUsers);
                }else {
                    mView.showError(message);
                }

            }
        };
        iOnlineTalkListModel.sub(subscriber);
    }

    public void loadVillage(){
        iOnlineTalkListModel.loadVillage();
        Subscriber<ListCommonResult<Village>> subscriber = new Subscriber<ListCommonResult<Village>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<Village> result) {
                mView.loadCommunity(result);
            }
        };
        iOnlineTalkListModel.subVillage(subscriber);
    }
}
