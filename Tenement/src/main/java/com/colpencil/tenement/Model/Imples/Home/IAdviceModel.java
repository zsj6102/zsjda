package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.Advice;
import com.colpencil.tenement.Bean.ListCommonResult;

import java.util.List;

import rx.Subscriber;

/**
 * @Description:  投诉建议
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/26
 */
public interface IAdviceModel {

    void getAdivceList(String communityId,int type,int page,int pageSize, int self);

    void sub(Subscriber<ListCommonResult<Advice>> subscriber);

}
