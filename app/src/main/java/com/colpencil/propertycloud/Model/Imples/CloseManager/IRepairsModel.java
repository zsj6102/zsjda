package com.colpencil.propertycloud.Model.Imples.CloseManager;

import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.HouseInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;
import java.util.List;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 提交物业报修
 * @Email DramaScript@outlook.com
 * @date 2016/10/11
 */
public interface IRepairsModel {

    void submitRepair(int type,
                      String describe,
                      String url_audio,
                      String book_time,
                      String book_site,
                      String book_mobile,
                      String license,
                      String comuId,
                      List<File> files,
                      int build_id,
                      int unit_id,
                      int house_id,
                      String audio_duration);

    void sub(Subscriber<ResultInfo> subscriber);

    /**
     * 业主房产信息
     */
    void loadEstate();

    void subEstate(Subscriber<ResultListInfo<HouseInfo>> subscriber);

    /**
     * 获取小区列表
     */
    void loadCell();

    void subCell(Subscriber<ResultListInfo<CellInfo>> subscriber);

}
