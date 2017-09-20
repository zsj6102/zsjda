package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Bean.Room;
import com.colpencil.propertycloud.Model.CloseManager.AddMemberModel;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IAddMemberModel;
import com.colpencil.propertycloud.View.Imples.AddMemberView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 添加成员
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public class AddMemberPresent extends ColpencilPresenter<AddMemberView> {

    private IAddMemberModel model;
    private Subscriber<Boolean> subscriber;

    public AddMemberPresent(){
        model = new AddMemberModel();
    }

    public void add(){
        model.add();
        subscriber = new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                mView.isAdd(aBoolean);
            }
        };
        model.sub(subscriber);
    }

    public void getRoomList(){
        model.getRoomList();
        Subscriber<List<Room>> listSubscriber = new Subscriber<List<Room>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Room> list) {
                mView.roomList(list);
            }
        };
        model.subRoom(listSubscriber);
    }

}
