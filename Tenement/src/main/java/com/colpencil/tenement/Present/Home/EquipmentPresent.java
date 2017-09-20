package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.Equipment;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Model.Home.EquipmentModel;
import com.colpencil.tenement.Model.Imples.Home.IEquipmentModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.EquipmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 设备管理
 * @Email DramaScript@outlook.com
 * @date 2016/8/23
 */
public class EquipmentPresent extends ColpencilPresenter<EquipmentView> {

    private IEquipmentModel model;

    public EquipmentPresent() {
        model = new EquipmentModel();
    }

    public void getEquipmentList(String communityId,int type, int page, int pageSize,String devCode,String devName,int self,String startDate,String endDate) {
        model.getEquipmentList(communityId,type, page, pageSize,devCode,devName,self,startDate,endDate);
        Subscriber<ListCommonResult<Equipment>> subscriber = new Subscriber<ListCommonResult<Equipment>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<Equipment> equipments) {
                ColpencilLogger.e("--------------------------------Equipment");
                mView.code(equipments.getCode());
                if (equipments.getCode() == 0||equipments.getCode() == 2||equipments.getCode()==3) {
                    mView.count(equipments.getCount());
                    if (page == 1) {
                        mView.refresh(equipments);
                    } else {
                        mView.loadMore(equipments);
                    }
                } else {
                    mView.loadError(equipments.getMessage());
                }
            }
        };
        model.sub(subscriber);
    }

}
