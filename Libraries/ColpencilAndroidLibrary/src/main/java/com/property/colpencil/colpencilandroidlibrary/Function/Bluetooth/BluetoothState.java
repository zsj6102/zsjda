package com.property.colpencil.colpencilandroidlibrary.Function.Bluetooth;
/**
 * @Description:蓝牙状态码
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/7/7
 */
public class BluetoothState {
    // 连接的常量状态
    public static final int STATE_NONE = 0;       	// 正在执行中
    public static final int STATE_LISTEN = 1;     	// 正在监听新加入的连接
    public static final int STATE_CONNECTING = 2; 	// 监听初始化中的断开的连接
    public static final int STATE_CONNECTED = 3;  	// 连接一个远程的设备
    public static final int STATE_NULL = -1;  	 	// 没有服务

    // 从BluetoothChatService发出的消息处理
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    
    // 意图请求码
    public static final int REQUEST_CONNECT_DEVICE = 384;
    public static final int REQUEST_ENABLE_BT = 385;

    // 从BluetoothChatService接收处理程序的key名称
    public static final String DEVICE_NAME = "device_name";
    public static final String DEVICE_ADDRESS = "device_address";
    public static final String TOAST = "toast";
    
    public static final boolean DEVICE_ANDROID = true;
    public static final boolean DEVICE_OTHER = false;
    
    // 返回另外的意图
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    
}
