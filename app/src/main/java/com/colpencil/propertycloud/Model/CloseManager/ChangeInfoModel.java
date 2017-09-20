package com.colpencil.propertycloud.Model.CloseManager;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IChangeInfoModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 更改信息
 * @Email DramaScript@outlook.com
 * @date 2016/10/11
 */
public class ChangeInfoModel implements IChangeInfoModel {

    private Observable<ResultInfo> changeObser;

    @Override
    public void changeInfo(int flag, String changeInfo) {
        HashMap<String, String> map = new HashMap<>();
        switch (flag){
            case 0:  // 名称
                map.put("name",changeInfo);
                break;
            case 1: //  昵称
                map.put("nickname",changeInfo);
                break;
            case 2: //  年龄
                map.put("birthday",changeInfo);
                break;
            case 3://  性别
                map.put("sex",changeInfo);
                break;
            case 4://  爱好
                map.put("hobby",changeInfo);
                break;
            case 5://  工作单位
                map.put("workunit",changeInfo);
                break;
            case 6://  民族
                map.put("nation",changeInfo);
                break;
            case 7://  紧急联系电话
                map.put("urgent_tel",changeInfo);
                break;
            case 8://  通讯地址
                map.put("address",changeInfo);
                break;
            case 9://  邮箱
                map.put("email",changeInfo);
                break;
            case 10://  常住地址
                map.put("live_address",changeInfo);
                break;
        }
        changeObser = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .changeInfo(map)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo> subscriber) {
        changeObser.subscribe(subscriber);
    }
}
