package com.colpencil.tenement.Model.Imples.TodayTask;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Sign;

import java.io.File;

import rx.Subscriber;

/**
 * @Description: 签到/签退
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/23
 */
public interface ISignModel {

    void signIn(String location,File signImage);

    void subIn(Subscriber<EntityResult<Sign>> subscriber);

    void signOut(String location,File signImage);

    void subOut(Subscriber<EntityResult<Sign>> subscriber);

}
