package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OnlineListUser;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface IAssignEmployeeListModel {

    void getAssignEmployeeList(String communityId);

    void sub(Subscriber<ListCommonResult<OnlineListUser>> subscriber);


}
