package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * Created by Administrator on 2017/2/14.
 */

public interface IEditRecordModel extends ColpencilModel {

    void editRecord(String workDetail, int workId);

    void subEditRecord(Observer<Result> resultObserver);
}
