package com.colpencil.propertycloud.Model.Imples.Home;

import com.colpencil.propertycloud.Bean.ComplaintHistroy;
import com.colpencil.propertycloud.Bean.ResultListInfo;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 历史投诉
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/5
 */
public interface IHistoryModel {

    void getList(int page,int type,int pageSize);

    void sub(Subscriber<ResultListInfo<ComplaintHistroy>> subscriber);

}
