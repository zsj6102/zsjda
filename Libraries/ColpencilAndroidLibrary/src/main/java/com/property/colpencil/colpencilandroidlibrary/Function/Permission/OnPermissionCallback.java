package com.property.colpencil.colpencilandroidlibrary.Function.Permission;

/**
 * @Description:权限回调
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public interface OnPermissionCallback {
    public static final int PERMISSION_ALERT_WINDOW = 0xad1;
    public static final int PERMISSION_WRITE_SETTING = 0xad2;

    public void onSuccess(String... permissions);

    public void onFail(String... permissions);
}
