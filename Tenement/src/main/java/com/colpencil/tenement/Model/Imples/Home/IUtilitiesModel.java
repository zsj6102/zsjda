package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.Building;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.ReadingNum;
import com.colpencil.tenement.Bean.Room;
import com.colpencil.tenement.Bean.Unit;
import com.colpencil.tenement.Bean.UtilitiesRecord;
import com.colpencil.tenement.Bean.Village;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;
import rx.Subscriber;

/**
 * @author 陈宝
 * @Description:水电抄表
 * @Email DramaScript@outlook.com
 * @date 2016/9/2
 */
public interface IUtilitiesModel extends ColpencilModel {

    /**
     * 获取楼宇
     */
    void loadBuilds(String communityId);

    void subBuilds(Observer<ListCommonResult<Building>> observer);

    /**
     * 获取单元
     * @param buildingId
     */
    void loadUnit(String buildingId);

    void subUnit(Subscriber<ListCommonResult<Unit>> subscriber);

    /**
     * 获取房间
     *
     * @param buildId
     */
    void loadRooms(String buildId);

    void subRooms(Observer<ListCommonResult<Room>> observer);

    /**
     * 获取抄表记录
     * @param flag  1 全部  2 楼宇查找  3 房间号查找
     * @param communityId
     * @param buildingId
     * @param houseCode
     * @param page
     * @param pageSize
     */
    void loadRecord(int self,String type,String dan,String communityId,String buildingId, String houseCode, int page, int pageSize);

    void subRecord(Observer<ListCommonResult<UtilitiesRecord>> observer);

    /**
     * 抄表数
     */
    void loadNum(String communityId,String buildingId,String unitId,String houseId,String type);

    void subNum(Observer<EntityResult<ReadingNum>> observer);

    /**
     * 获取小区
     */
    void loadVillage();

    void subVillage(Subscriber<ListCommonResult<Village>> subscriber);
}
