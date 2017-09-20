package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.WorkBeach;

import java.util.List;

import rx.Observer;

/**
 * @Description: 工作台数据
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/17
 */
public interface IWorkBeachModel {

    void loadWorkBeach();

    void sub(Observer<List<WorkBeach>> subscriber);
}
