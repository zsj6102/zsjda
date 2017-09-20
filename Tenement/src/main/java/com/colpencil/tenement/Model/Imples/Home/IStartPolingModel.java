package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.LastRecord;
import com.colpencil.tenement.Bean.Result;

import rx.Subscriber;

/**
 * @Description:  开始保养/巡检/维修
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/23
 */
public interface IStartPolingModel {


    void post(int type,String eqId,String communityId,String currTime,String content,String lastTime,String lastEmp,
              String maintainId,String eqType);

    void sub(Subscriber<Result> subscriber);

    /**
     *
     * @param eqId
     */
    void getLast(String eqId,int type,String eqType);

    void subLast(Subscriber<EntityResult<LastRecord>> subscriber);

    void linkDev(String devId,String orderId,String content,String eqType);

    void subLinkDev(Subscriber<Result> subscriber);

}
