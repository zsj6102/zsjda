package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Record;
import com.colpencil.tenement.Bean.Village;

import rx.Subscriber;

/**
 * @Description: 小区选择
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/14
 */
public interface IVillageSelectModel {

    void loadVillage();

    void subVillage(Subscriber<ListCommonResult<Village>> subscriber);

    void loadRecord(String communityId);

    void subRecord(Subscriber<ListCommonResult<Record>> subscriber);
}
