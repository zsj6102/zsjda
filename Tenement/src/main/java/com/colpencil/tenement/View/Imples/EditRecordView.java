package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/2/14.
 */

public interface EditRecordView extends ColpencilBaseView {

    void editResult(Result result);
    void loadError(String msg);
}
