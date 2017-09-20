package com.colpencil.tenement.Present.TodayTask;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.SignInfo;
import com.colpencil.tenement.Bean.SignList;
import com.colpencil.tenement.Model.Imples.TodayTask.ISignListModel;
import com.colpencil.tenement.Model.TodayTask.SignListMolder;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.SignListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @Description: 签到/签退记录
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/26
 */
public class SignListPresent extends ColpencilPresenter<SignListView> {

    private ISignListModel model;

    public SignListPresent(){
        model = new SignListMolder();
    }

    public void getSignList(int currentPage,int pageSize,int type){
        model.getSignList(currentPage,pageSize,type);
        Subscriber<ListCommonResult<SignList>> subscriber = new Subscriber<ListCommonResult<SignList>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<SignList> result) {
                int code = result.getCode();
                String message = result.getMessage();
                if (code == 0||code==2||code==3) {
                    if (currentPage == 1) {
                        ColpencilLogger.e("----------------");
                        mView.refresh(result);
                    } else {
                        mView.loadMore(result);
                    }
                } else {
                    mView.loadError(message);
                }
            }
        };
        model.sub(subscriber);
    }

    public void getSignState(){
        model.getSignState();
        Subscriber<EntityResult<SignInfo>> subscriber = new Subscriber<EntityResult<SignInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(EntityResult<SignInfo> signInfoEntityResult) {
                mView.getSignState(signInfoEntityResult);
            }
        };
        model.subSign(subscriber);
    }

}
