package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.Member;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description: 成员管理
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public interface MemberManagerView extends ColpencilBaseView{

    void memberList(List<Member> list);

}
