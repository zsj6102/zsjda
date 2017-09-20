package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Bean.WaterInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;
import rx.Subscriber;

/**
 * @Description: 提交抄水表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/19
 */
public interface IWriteWatermeterModel extends ColpencilModel {

    /**
     * 提交抄表记录
     *
     * @param roomId
     * @param ownerName
     * @param waterId
     * @param monthCoast
     * @param type
     */
    void submit(String roomId, String ownerName, String waterId, String monthCoast, int type,String lastRecord,String communityId,String recordId);

    void sub(Observer<EntityResult<String>> observer);


    /**
     * 获取上月见抄
     * @param devId
     */
    void getLast(String devId,String type);

    void subLast(Subscriber<EntityResult<WaterInfo>> subscriber);

}
