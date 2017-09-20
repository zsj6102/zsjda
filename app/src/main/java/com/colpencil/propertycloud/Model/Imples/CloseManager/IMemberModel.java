package com.colpencil.propertycloud.Model.Imples.CloseManager;

import com.colpencil.propertycloud.Bean.Member;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 成员管理
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public interface IMemberModel {

    void getMemberList();

    void sub(Subscriber<List<Member>> subscriber);

}
