package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.Room;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description: 添加成员
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public interface AddMemberView extends ColpencilBaseView{

    void isAdd(boolean isAdd);

    void roomList(List<Room> list);
}
