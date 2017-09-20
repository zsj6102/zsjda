package com.colpencil.propertycloud.Model.Imples.Home;

import com.colpencil.propertycloud.Bean.AdviceList;
import com.colpencil.propertycloud.Bean.ResultListInfo;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 物业建议列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public interface IAdviceListModel {

    void getList(int page,int pageSize);

    void sub(Subscriber<ResultListInfo<AdviceList>> subscriber);

}
