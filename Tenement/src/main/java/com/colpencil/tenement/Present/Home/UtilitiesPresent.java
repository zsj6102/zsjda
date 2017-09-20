package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.Building;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.ReadingNum;
import com.colpencil.tenement.Bean.Room;
import com.colpencil.tenement.Bean.Unit;
import com.colpencil.tenement.Bean.UtilitiesRecord;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Model.Home.UtilitiesModel;
import com.colpencil.tenement.Model.Imples.Home.IUtilitiesModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.UtilitiesView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Observer;
import rx.Subscriber;

/**
 * @author 陈宝
 * @Description:水电费抄表
 * @Email DramaScript@outlook.com
 * @date 2016/9/2
 */
public class UtilitiesPresent extends ColpencilPresenter<UtilitiesView> {

    private IUtilitiesModel model;

    public UtilitiesPresent() {
        model = new UtilitiesModel();
    }

    /**
     * 获取小区
     */
    public void loadVillage(){
        model.loadVillage();
        Subscriber<ListCommonResult<Village>> subscriber = new Subscriber<ListCommonResult<Village>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<Village> result) {
                mView.loadCommunity(result);
            }
        };
        model.subVillage(subscriber);
    }

    /**
     * 获取楼宇
     */
    public void loadBuilds(String communityId) {
        model.loadBuilds(communityId);
        Observer<ListCommonResult<Building>> observer = new Observer<ListCommonResult<Building>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<Building> result) {
                    mView.loadBuilds(result);
            }
        };
        model.subBuilds(observer);
    }

    /**
     * 获取单元
     * @param buildingId
     */
    public void loadUnit(String buildingId){
        model.loadUnit(buildingId);
        Subscriber<ListCommonResult<Unit>> subscriber = new Subscriber<ListCommonResult<Unit>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<Unit> unitListCommonResult) {
                mView.loadUnit(unitListCommonResult);
            }
        };
        model.subUnit(subscriber);
    }

    /**
     * 获取房间
     *
     * @param buildId
     */
    public void loadRooms(String buildId) {
        model.loadRooms(buildId);
        Observer<ListCommonResult<Room>> observer = new Observer<ListCommonResult<Room>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<Room> result) {
                    mView.loadRooms(result);
            }
        };
        model.subRooms(observer);
    }


    /**
     * 获取抄表记录
     *
     * @param buildingId
     * @param houseCode
     * @param page
     * @param pageSize
     */
    public void loadRecord(int self,String type,String dan,String communityId,String buildingId, String houseCode, int page, int pageSize) {
        model.loadRecord(self,type,dan,communityId,buildingId, houseCode, page, pageSize);
        Observer<ListCommonResult<UtilitiesRecord>> observer = new Observer<ListCommonResult<UtilitiesRecord>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<UtilitiesRecord> result) {
                ColpencilLogger.e("-----------result="+result.toString());
                if (result.getCode() == 0||result.getCode() == 2||result.getCode()==3) {
                    if (page == 1) {
                        mView.refresh(result);
                    } else {
                        mView.loadMore(result);
                    }
                } else if (result.getCode() == 1){  // 失败
                    mView.loadError(result.getMessage());
                }
            }
        };
        model.subRecord(observer);
    }

    /**
     * 获取抄表数
     */
    public void loadNum(String communityId,String buildingId,String unitId,String houseId,String type) {
        model.loadNum(communityId,buildingId,unitId,houseId,type);
        Observer<EntityResult<ReadingNum>> observer = new Observer<EntityResult<ReadingNum>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(EntityResult<ReadingNum> result) {
                if (result.getCode() == 0) {
                    mView.loadNum(result.getData());
                }
            }
        };
        model.subNum(observer);
    }
}
