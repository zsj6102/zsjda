package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.CleanRecord;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Village;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;
import rx.Subscriber;

public interface ICleanRecordModel extends ColpencilModel {

    /**
     * 获取保洁记录
     *
     * @param type
     * @param page
     * @param pageSize
     */
    void loadRecords(String communityId,int type, int page, int pageSize,String beginTime,String endTime,int self);

    void subRecords(Observer<ListCommonResult<CleanRecord>> observer);

    void loadVillage();

    void subVillage(Subscriber<ListCommonResult<Village>> subscriber);
}
