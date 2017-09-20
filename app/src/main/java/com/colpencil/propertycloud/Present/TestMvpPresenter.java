package com.colpencil.propertycloud.Present;

import com.colpencil.propertycloud.Bean.ContentlistEntity;
import com.colpencil.propertycloud.Model.Imples.ITestMvpModel;
import com.colpencil.propertycloud.Model.Test.TestMvpModel;
import com.colpencil.propertycloud.View.Imples.TestMvpView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

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
 * <p>
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * 作者：LigthWang
 * <p>
 * 描述：present的测试类
 */
public class TestMvpPresenter extends ColpencilPresenter<TestMvpView> {

    private ITestMvpModel mvpModel;

    public TestMvpPresenter() {
        mvpModel = new TestMvpModel();
    }

    public void getContent(final int page){
        mvpModel.loadData(page);

        Observer<List<ContentlistEntity>> observer = new Observer<List<ContentlistEntity>>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();//完成的时候就隐藏加载中
            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e(e.getMessage());
                mView.showError(null, null);
            }

            @Override
            public void onNext(List<ContentlistEntity> contentlistEntities) {
                mView.showLoading("加载中。。。");
                if (contentlistEntities.size()==0){//这个时候是空的界面
                    mView.showEmpty(null,null);
                }else {//这个时候就是有数据的界面
                    if (page == 1) {
                        mView.refresh(contentlistEntities);
                    } else {
                        mView.loadMore(contentlistEntities);
                    }
                }
            }
        };
        mvpModel.sub(observer);
    }

}
