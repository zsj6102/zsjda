package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Home.IUploadRecordModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.lzy.imagepicker.bean.ImageItem;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:绿化保洁
 * @Email DramaScript@outlook.com
 * @date 2016/8/24
 */
public class UploadRecordModel implements IUploadRecordModel {


    private Observable<EntityResult<String>> upload;
    private File file;

    @Override
    public void upload(int type, String startTime, String endTime, String nowLocation,
                       String workDetail, List<File> imglist, String communityId,String longitude,String latitude) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("type", OkhttpUtils.toRequestBody(type+""));
        map.put("startTime", OkhttpUtils.toRequestBody(startTime));
        map.put("nowLocation", OkhttpUtils.toRequestBody(nowLocation));
        map.put("workDetail", OkhttpUtils.toRequestBody(workDetail));
        map.put("communityId", OkhttpUtils.toRequestBody(communityId));
//        map.put("endTime", OkhttpUtils.toRequestBody(endTime));
        map.put("longitude", OkhttpUtils.toRequestBody(longitude));
        map.put("latitude", OkhttpUtils.toRequestBody(latitude));
        for (int i=0;i<imglist.size();i++){
            ColpencilLogger.e("fileSize="+imglist.get(i));
            map.put("imageList\";filename=\""+"imageList"+i+".jpg",RequestBody.create(MediaType.parse("image/png"), imglist.get(i)));
        }
        upload = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .submitRecord(map)
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> stringEntityResult) {
                        return stringEntityResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subUpload(Observer<EntityResult> observer) {
        upload.subscribe(observer);
    }
}
