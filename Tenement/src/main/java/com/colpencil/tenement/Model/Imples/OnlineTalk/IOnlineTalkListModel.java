package com.colpencil.tenement.Model.Imples.OnlineTalk;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Online;
import com.colpencil.tenement.Bean.OnlineListUser;
import com.colpencil.tenement.Bean.Village;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 在线对讲列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/18
 */
public interface IOnlineTalkListModel {

    void getOnlineTalkList(String communityId);

    void sub(Subscriber<ListCommonResult<OnlineListUser>> subscriber);

    void loadVillage();

    void subVillage(Subscriber<ListCommonResult<Village>> subscriber);

}
