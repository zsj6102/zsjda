package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Record;
import com.colpencil.tenement.Bean.Village;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 选择小区的
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/14
 */
public interface VillageSelectView extends ColpencilBaseView {

    void loadCommunity(ListCommonResult<Village> result);

    void loadCommunityDefu(ListCommonResult<Village> result);

    void loadError(String msg);

    void loadMarker(ListCommonResult<Record> result);
}
