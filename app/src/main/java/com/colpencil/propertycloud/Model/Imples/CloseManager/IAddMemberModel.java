package com.colpencil.propertycloud.Model.Imples.CloseManager;

import com.colpencil.propertycloud.Bean.Room;

import java.util.List;

import rx.Subscriber;

/**
 * @Description:
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public interface IAddMemberModel {

    void add();

    void sub(Subscriber<Boolean> subscriber);

    void getRoomList();

    void subRoom(Subscriber<List<Room>> subscriber);

}
