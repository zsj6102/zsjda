package com.colpencil.tenement.Model.Imples.Welcome;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Result;

import rx.Subscriber;

/**
 * @Description: 忘记密码
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/29
 */
public interface IForgetPwdModel {

    void change(String phone,String yan,String pwd,String newPwe,String type);

    void subChange(Subscriber<EntityResult<String>> subscriber);

    void getCode(String phone,int flag);

    void subGet(Subscriber<Result> subscriber);

}
