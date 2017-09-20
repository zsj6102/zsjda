package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.HouseInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.CloseManager.RepairsModel;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IRepairsModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.View.Imples.TenementRepairsView;
import com.lzy.imagepicker.bean.ImageItem;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.List;

import rx.Observer;
import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 物业报修
 * @Email DramaScript@outlook.com
 * @date 2016/10/11
 */
public class ReparlPresent extends ColpencilPresenter<TenementRepairsView> {

    private IRepairsModel model;

    public ReparlPresent() {
        model = new RepairsModel();
    }

    public void submit(int type, String describe, String url_audio, String book_time, String book_site, String book_mobile,
                       String license, String comuId, List<File> files, int build_id, int unit_id, int house_id, String audio_duration) {
        model.submitRepair(type, describe, url_audio, book_time, book_site, book_mobile, license, comuId, files, build_id, unit_id, house_id, audio_duration);
        Subscriber<ResultInfo> subscriber = new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误信息" + e.getMessage());
            }

            @Override
            public void onNext(ResultInfo resultInfo) {
                mView.result(resultInfo);
            }
        };
        model.sub(subscriber);
    }

    /**
     * 获取房产信息
     */
    public void loadEstate() {
        model.loadEstate();
        Subscriber<ResultListInfo<HouseInfo>> observer = new Subscriber<ResultListInfo<HouseInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultListInfo<HouseInfo> result) {
                mView.estateResult(result);
            }
        };
        model.subEstate(observer);
    }

    public void loadCell() {
        model.loadCell();
        Subscriber<ResultListInfo<CellInfo>> subscriber = new Subscriber<ResultListInfo<CellInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultListInfo<CellInfo> result) {
                mView.cellResult(result);
            }
        };
        model.subCell(subscriber);
    }

}
