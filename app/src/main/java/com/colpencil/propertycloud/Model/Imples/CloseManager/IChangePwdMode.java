package com.colpencil.propertycloud.Model.Imples.CloseManager;

import com.colpencil.propertycloud.Bean.ResultInfo;

import rx.Subscriber;

/**
 * @Description: 忘记密码/修改密码
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/10
 */
public interface IChangePwdMode {

    void modPwd(String mobile,String wtd,String validCd,String password,String conpassword);

    void subModPwd(Subscriber<ResultInfo> subscriber);

    //获取验证码
    void getCode(int flag,String mobile);

    void subCode(Subscriber<ResultInfo> subscriber);
}
