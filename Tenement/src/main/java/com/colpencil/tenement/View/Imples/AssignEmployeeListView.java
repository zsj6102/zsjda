package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OnlineListUser;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface AssignEmployeeListView extends ColpencilBaseView {

    void getAssignEmployeeList(ListCommonResult<OnlineListUser> assignEmployeeList);

    void showError(String message);

}
