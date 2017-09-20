package com.colpencil.propertycloud.Model.Imples.CloseManager;

import com.colpencil.propertycloud.Bean.RepairHistory;
import com.colpencil.propertycloud.Bean.ResultListInfo;

import rx.Subscriber;

/**
 * @Description: 历史报修
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/11
 */
public interface IRepairHistoryModel {

    void getHistory(int pageNo,int pageSize);

    void sub(Subscriber<ResultListInfo<RepairHistory>> subscriber);

}
