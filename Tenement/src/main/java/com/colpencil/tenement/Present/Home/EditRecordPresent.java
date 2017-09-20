package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Model.Home.EditRecordModel;
import com.colpencil.tenement.Model.Imples.Home.IEditRecordModel;
import com.colpencil.tenement.View.Imples.EditRecordView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

import rx.Observable;
import rx.Observer;

/**
 * Created by Administrator on 2017/2/14.
 */

public class EditRecordPresent extends ColpencilPresenter<EditRecordView> {

    private IEditRecordModel editRecordModel;

    public EditRecordPresent(){
        editRecordModel = new EditRecordModel();
    }

    public void editRecord(String workDetail, int workId){
        editRecordModel.editRecord(workDetail, workId);
        Observer<Result> observer = new Observer<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误" + e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(Result result) {
                mView.editResult(result);
            }
        };
        editRecordModel.subEditRecord(observer);
    }
}
