package com.colpencil.propertycloud.Present.Home;

import com.colpencil.propertycloud.Bean.Material;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.Home.MaterialListModel;
import com.colpencil.propertycloud.Model.Imples.Home.IMaterialListModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.View.Imples.MaterialManagerView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.List;

import rx.Subscriber;

/**
 * @Description: 材料管理
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public class MaterialListPresent extends ColpencilPresenter<MaterialManagerView> {

    private IMaterialListModel model;

    public MaterialListPresent(){
        model = new MaterialListModel();
    }

    public void getList(){
        model.getList();
        Subscriber<List<Material>> subscriber = new Subscriber<List<Material>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Material> list) {
                mView.materialList(list);
            }
        };
        model.sub(subscriber);
    }

    private void submit(String houseId, String approveid, String decortCoNm, String decortHead, String peopleNum,
                        String qualifiNo, String decortCoTel, String decortbeginTm, String decortEndTm,
                        String decortDesc, File decortDsnInfo, File decortManReguSign, List<File> decortCommitBookSign,
                        File decortCoQua, String isrentcam, String camnum){
        model.submit(houseId,approveid,decortCoNm,decortHead,peopleNum,qualifiNo,decortCoTel,decortbeginTm,
                decortEndTm,decortDesc,decortDsnInfo,decortManReguSign,decortCommitBookSign,decortCoQua,
                isrentcam,camnum);
        Subscriber<ResultInfo> subscriber = new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastTools.showShort(CluodApplaction.getInstance(), "服务器错误：" + e.getMessage());
            }

            @Override
            public void onNext(ResultInfo resultInfo) {
                mView.submit(resultInfo);
            }
        };
        model.subSubmit(subscriber);
    }

}
