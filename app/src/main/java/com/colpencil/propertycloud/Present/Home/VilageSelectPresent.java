package com.colpencil.propertycloud.Present.Home;

import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.Home.VilageSelectModel;
import com.colpencil.propertycloud.Model.Imples.Home.IVilageSelectModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.View.Imples.VilageSelectView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 小区选择
 * @Email DramaScript@outlook.com
 * @date 2016/9/9
 */
public class VilageSelectPresent extends ColpencilPresenter<VilageSelectView> {

    private IVilageSelectModel model;

    public VilageSelectPresent() {
        model = new VilageSelectModel();
    }

    public void getList(int provinceId, int cityId, int areaId) {
        model.getList(provinceId, cityId, areaId);
        Subscriber<ResultListInfo<CellInfo>> subscriber = new Subscriber<ResultListInfo<CellInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResultListInfo<CellInfo> listResultInfo) {
                ColpencilLogger.e("选择：" + listResultInfo.data.size());
                if (listResultInfo.code == 0 || listResultInfo.code == 2) { // 成功
                    mView.refresh(listResultInfo);
                } else {  // 失败
                    mView.loadError(listResultInfo.message);
                }
            }
        };
        model.sub(subscriber);
    }

    public void getOrderCells(boolean isProperty) {
        model.getOrderCell(isProperty);
        Subscriber<ResultListInfo<CellInfo>> subscriber = new Subscriber<ResultListInfo<CellInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResultListInfo<CellInfo> listResultInfo) {
                if (listResultInfo.code == 0 || listResultInfo.code == 2) { // 成功
                    mView.refresh(listResultInfo);
                } else {  // 失败
                    mView.loadError(listResultInfo.message);
                }
            }
        };
        model.subOrderCells(subscriber);
    }

    public void select(String memberId, String comuId) {
        model.select(memberId, comuId);
        Subscriber<ResultInfo<String>> subscriber = new Subscriber<ResultInfo<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastTools.showShort(CluodApplaction.getInstance(), "服务器错误：" + e.getMessage());
            }

            @Override
            public void onNext(ResultInfo<String> resultInfo) {
                mView.select(resultInfo);
            }
        };
        model.subSelect(subscriber);
    }

    public void loadCellByName(String cityName) {
        model.loadCellByName(cityName);
        Subscriber<ResultListInfo<CellInfo>> subscriber = new Subscriber<ResultListInfo<CellInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultListInfo<CellInfo> resultListInfo) {
                if (resultListInfo.code == 0 || resultListInfo.code == 2) {
                    mView.refresh(resultListInfo);
                } else {
                    mView.loadError(resultListInfo.message);
                }
            }
        };
        model.subLoadCellByName(subscriber);
    }
}
