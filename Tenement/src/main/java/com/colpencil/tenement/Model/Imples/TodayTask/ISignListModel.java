package com.colpencil.tenement.Model.Imples.TodayTask;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.SignInfo;
import com.colpencil.tenement.Bean.SignList;

import rx.Subscriber;

/**
 * @Description: 签到/签退记录
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/26
 */
public interface ISignListModel {

    void getSignList(int currentPage,int pageSize,int type);

    void sub(Subscriber<ListCommonResult<SignList>> subscriber);

    void getSignState();

    void subSign(Subscriber<EntityResult<SignInfo>> subscriber);
}
