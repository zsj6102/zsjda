package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.Building;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.ReadingNum;
import com.colpencil.tenement.Bean.Room;
import com.colpencil.tenement.Bean.Unit;
import com.colpencil.tenement.Bean.UtilitiesRecord;
import com.colpencil.tenement.Bean.Village;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:水电费抄表
 * @Email DramaScript@outlook.com
 * @date 2016/9/2
 */
public interface UtilitiesView extends ColpencilBaseView {

    /**
     * 获取小区
     * @param result
     */
    void loadCommunity(ListCommonResult<Village> result);

    /**
     * 获取全部楼宇结果
     *
     * @param result
     */
    void loadBuilds(ListCommonResult<Building> result);

    /**
     * 获取单元
     * @param result
     */
    void loadUnit(ListCommonResult<Unit> result);

    /**
     * 获取房间
     *
     * @param result
     */
    void loadRooms(ListCommonResult<Room> result);

    /**
     * 加载更多
     *
     * @param result
     */
    void loadMore(ListCommonResult<UtilitiesRecord> result);

    /**
     * 刷新
     *
     * @param result
     */
    void refresh(ListCommonResult<UtilitiesRecord> result);

    /**
     * 获取抄表数
     *
     * @param num
     */
    void loadNum(ReadingNum num);

    /**
     * 加载失败的界面
     * @param msg
     */
    void loadError(String msg);

    void loadVilError();
}
