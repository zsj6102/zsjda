package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OnlineListUser;
import com.colpencil.tenement.Model.Home.AssignEmployeeListModel;
import com.colpencil.tenement.Model.Imples.Home.IAssignEmployeeListModel;
import com.colpencil.tenement.View.Imples.AssignEmployeeListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/13.
 */

public class AssignEmployeePresent extends ColpencilPresenter<AssignEmployeeListView> {

    private IAssignEmployeeListModel iAssignEmployeeListModel;

    public AssignEmployeePresent(){
        iAssignEmployeeListModel = new AssignEmployeeListModel();
    }

    public void getAssignEmployeeList(String communityId){
        iAssignEmployeeListModel.getAssignEmployeeList(communityId);
        Subscriber<ListCommonResult<OnlineListUser>> subscriber = new Subscriber<ListCommonResult<OnlineListUser>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误" + e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<OnlineListUser> onlineListUserListCommonResult) {
                int code = onlineListUserListCommonResult.getCode();
                String message = onlineListUserListCommonResult.getMessage();
                if(code == 0 || code == 2 || code == 3){
                    mView.getAssignEmployeeList(onlineListUserListCommonResult);
                } else {
                    mView.showError(message);
                }
            }
        };
        iAssignEmployeeListModel.sub(subscriber);
    }
}
