package com.colpencil.propertycloud.Present;

import android.util.Log;
import com.colpencil.propertycloud.Bean.ListEntity;
import com.colpencil.propertycloud.Model.Imples.ITestModel;
import com.colpencil.propertycloud.Model.Test.TestModel;
import com.colpencil.propertycloud.View.Imples.TestView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import java.util.List;
import rx.Observer;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * <p/>
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p/>
 * 作者：LigthWang
 * <p/>
 * 描述：
 */
public class TestPresenter extends ColpencilPresenter<TestView> {

    private ITestModel testModel;

    public TestPresenter() {
        testModel = new TestModel();
    }

    public void getContent(final int pageNo,int pageSize) {
        testModel.loadData(pageNo,pageSize);
        Observer<List<ListEntity>> observer = new Observer<List<ListEntity>>() {
            @Override
            public void onCompleted() {
                Log.e("网络请求", "请求完成");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("网络请求", "请求失败");
                mView.showError(null, null);
            }

            @Override
            public void onNext(List<ListEntity> testEntities) {
                if (pageNo == 1) {
                    mView.refresh(testEntities);
                } else {
                    mView.loadMore(testEntities);
                }
            }
        };
        testModel.sub(observer);
    }

}
