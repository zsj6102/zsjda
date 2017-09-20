package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Visitor;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 访客管理
 * @Email DramaScript@outlook.com
 * @date 2016/8/25
 */
public interface IVisitorModel {

    void getVisitor(String communityId, int page, int pageSize);

    void sub(Subscriber<ListCommonResult<Visitor>> subscriber);

}
