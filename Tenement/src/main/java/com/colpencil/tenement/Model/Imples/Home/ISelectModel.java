package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Village;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:小区选择
 * @Email DramaScript@outlook.com
 * @date 2016/8/31
 */
public interface ISelectModel extends ColpencilModel {

    void loadVilliage();

    void sub(Observer<ListCommonResult<Village>> observer);

}
