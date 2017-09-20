package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.TenementComp;
import com.colpencil.tenement.Bean.UserInfo;
import com.colpencil.tenement.Bean.Village;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 登陆
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/7/15
 */
public interface LoginView extends ColpencilBaseView{

    void login(boolean isLogin);

    /**
     * 登录结果
     * @param result
     */
    void loginResult(EntityResult<UserInfo> result);

    /**
     * 获取物业公司列表
     * @param result
     */
    void compList(ListCommonResult<TenementComp> result);

    void loadCommunity(ListCommonResult<Village> result);

    void loadError(String msg);
}
