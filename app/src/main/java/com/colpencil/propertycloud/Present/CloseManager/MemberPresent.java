package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Bean.Member;
import com.colpencil.propertycloud.Model.CloseManager.MemberModel;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IMemberModel;
import com.colpencil.propertycloud.View.Imples.MemberManagerView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 成员管理
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public class MemberPresent extends ColpencilPresenter<MemberManagerView> {

    private IMemberModel model;

    public MemberPresent(){
        model = new MemberModel();
    }

    public void getMemberList(){
        model.getMemberList();
        Subscriber<List<Member>> subscriber = new Subscriber<List<Member>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Member> list) {
                mView.memberList(list);
            }
        };
        model.sub(subscriber);
    }
}
