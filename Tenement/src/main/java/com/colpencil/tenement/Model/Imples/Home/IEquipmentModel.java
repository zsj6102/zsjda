package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.Equipment;
import com.colpencil.tenement.Bean.ListCommonResult;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 设备管理
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/23
 */
public interface IEquipmentModel {

    void getEquipmentList(String communityId,int type, int page, int pageSize,String devCode,String devName,int self,String startDate,String endDate);

    void sub(Subscriber<ListCommonResult<Equipment>> subscriber);

}
