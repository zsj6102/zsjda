package com.property.colpencil.colpencilandroidlibrary.Ui.ColorfulView;

/**
 * @Description:不同界面的接口
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public interface IColView {
    public static final int ERROR = 0xaff1;
    public static final int DATA_NULL = 0xaff2;
    public static final int LOADING = 0xaff3;

    /**
     * 设置填充界面类型
     *
     * @param type {@link IColView#ERROR}
     *             {@link IColView#DATA_NULL}
     *             {@link IColView#LOADING}
     */
    public void setType(int type);

    /**
     * 在这处理type = ITempView#ERROR 时的逻辑
     */
    public void onError();

    /**
     * 在这处理type = ITempView#DATA_NULL 时的逻辑
     */
    public void onNull();

    /**
     * 在这处理type = ITempView#LOADING 时的逻辑
     */
    public void onLoading();

}
