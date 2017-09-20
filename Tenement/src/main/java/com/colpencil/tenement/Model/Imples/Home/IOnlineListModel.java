package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.Online;

import org.jivesoftware.smack.Roster;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 客服列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/24
 */
public interface IOnlineListModel {

    void getOnlineList(Roster roster);

    void sub(Subscriber<List<Online>> onlineSubscriber);

}
