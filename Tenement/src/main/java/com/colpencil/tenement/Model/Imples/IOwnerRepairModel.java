package com.colpencil.tenement.Model.Imples;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OwnerRepair;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observable;
import rx.Observer;

/**
 * @author 陈宝
 * @Description:业主报修
 * @Email DramaScript@outlook.com
 * @date 2016/8/25
 */
public interface IOwnerRepairModel extends ColpencilModel {

    /**
     * 获取报修记录
     *
     * @param type
     * @param page
     * @param pageSize
     */
    void loadRepairList(String communityId,int type, int page, int pageSize, int self);

    void subRepairList(Observer<ListCommonResult<OwnerRepair>> observer);

}
