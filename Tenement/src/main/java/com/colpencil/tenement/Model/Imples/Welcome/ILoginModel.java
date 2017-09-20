package com.colpencil.tenement.Model.Imples.Welcome;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.TenementComp;
import com.colpencil.tenement.Bean.UserInfo;
import com.colpencil.tenement.Bean.Village;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;
import rx.Subscriber;

/**
 * @Description: 登陆
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/7/15
 */
public interface ILoginModel extends ColpencilModel{

    //登陆操作
    void login(String acount, String passWord);

    //注册观察者
    void sub(Observer<Boolean> subscriber);

    /**
     * 登录到本地服务器
     */
    void loginToServer(String acount, String passWord,String propertyId,String deviceId);

    void sublogin(Observer<EntityResult<UserInfo>> observer);

    /**
     * 获取物业公司列表
     */
    void getCompList();

    void sub(Subscriber<ListCommonResult<TenementComp>> subscriber);

    void loadVillage();

    void subVillage(Subscriber<ListCommonResult<Village>> subscriber);
}
