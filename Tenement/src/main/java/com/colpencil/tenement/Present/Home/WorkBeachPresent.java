package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.WorkBeach;
import com.colpencil.tenement.Model.Home.WorkBeachModel;
import com.colpencil.tenement.Model.Imples.Home.IWorkBeachModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.WorkbeachView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 工作台
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/17
 */
public class WorkBeachPresent extends ColpencilPresenter<WorkbeachView> {

    public IWorkBeachModel workBeachModel;

    public WorkBeachPresent(){
        workBeachModel = new WorkBeachModel();
    }

    public void loadWorkBeach(){
        workBeachModel.loadWorkBeach();
        Subscriber<List<WorkBeach>> subscriber = new Subscriber<List<WorkBeach>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(List<WorkBeach> workBeachList) {
                mView.loadWorkBeach(workBeachList);
            }
        };
        workBeachModel.sub(subscriber);
    }

}
