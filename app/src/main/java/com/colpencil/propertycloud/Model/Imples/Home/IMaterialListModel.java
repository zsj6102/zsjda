package com.colpencil.propertycloud.Model.Imples.Home;

import com.colpencil.propertycloud.Bean.Material;
import com.colpencil.propertycloud.Bean.ResultInfo;

import java.io.File;
import java.util.List;

import rx.Subscriber;

/**
 * @Description:  材料管理界面
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public interface IMaterialListModel {

    void getList();

    void sub(Subscriber<List<Material>> subscriber);

    void submit(String houseId, String approveid, String decortCoNm, String decortHead, String peopleNum,
                String qualifiNo, String decortCoTel, String decortbeginTm, String decortEndTm, String decortDesc,
                File decortDsnInfo,File decortManReguSign,List<File> decortCommitBookSign,File decortCoQua,String isrentcam,String camnum);

    void subSubmit(Subscriber<ResultInfo> subscriber);

}
