package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

public interface UploadRecordView extends ColpencilBaseView {

    /**
     * 上传结果
     */
    void uploadResult(EntityResult result);
    void loadError(String msg);

}
