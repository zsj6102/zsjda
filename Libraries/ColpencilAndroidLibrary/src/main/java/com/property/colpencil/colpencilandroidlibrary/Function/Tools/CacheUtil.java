package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.property.colpencil.colpencilandroidlibrary.Function.Cache.CacheParam;
import com.property.colpencil.colpencilandroidlibrary.Function.Cache.ColpencilCache;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.L;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DrawableUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

/**
<<<<<<< .mine
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
 * 描述：缓存工具
||||||| .r1868
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
 *
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * 作者：LigthWang
 *
 * 描述：缓存工具
 * @Description:缓存工具
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public class CacheUtil extends ColpencilCache {
    private static final String TAG = "CacheUtil";

    /**
     * 默认使用默认路径
     *
     * @param context
     * @param useMemory 是否使用内存缓存
     */
    public CacheUtil(Context context, boolean useMemory) {
        super(context, useMemory);
    }

    /**
     * 指定缓存文件夹
     *
     * @param context
     * @param useMemory 是否使用内存缓存
     * @param cacheDir  缓存文件夹
     */
    public CacheUtil(Context context, boolean useMemory, @NonNull String cacheDir) {
        super(context, useMemory, cacheDir);
    }

    /**
     * 设置是否使用内存缓存
     */
    public void setUseMemoryCache(boolean useMemoryCache) {
        setUseMemory(useMemoryCache);
    }

    /**
     * 打开某个目录下的缓存
     *
     * @param cacheDir   缓存目录，只需填写文件夹名，不需要写路径
     * @param valueCount 指定同一个key可以对应多少个缓存文件，基本都是传1
     * @param cacheSize  缓存大小
     * @see CacheParam
     */
    public void openCache(String cacheDir, int valueCount, long cacheSize) {
        openDiskCache(cacheDir, valueCount, cacheSize);
    }

    /**
     * 写入Bitmap类型缓存，注意：需要在特定的时候flush,一般在onPause()里面写,onDestroy()
     *
     * @param key    键值，一般是url
     * @param bitmap 需要写入的数据
     */
    public void putBitmapCache(String key, Bitmap bitmap) {
        byte[] data = DrawableUtil.getBitmapByte(bitmap);
        putByteCache(key, data);
    }

    /**
     * 获取缓存中的bitmap
     *
     * @param key
     * @return
     */
    public Bitmap getBitmapCache(String key) {
        byte[] data = getByteCache(key);
        return DrawableUtil.getBitmapFromByte(data);
    }

    /**
     * 写入String类型缓存，注意：需要在特定的时候flush,一般在onPause()里面写,onDestroy()
     *
     * @param key  键值，一般是url
     * @param data 需要写入的数据
     */
    public void putStringCache(String key, String data) {
        try {
            putByteCache(key, data.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取字符串缓存
     */
    public String getStringCache(String key) {
        byte[] data = getByteCache(key);
        String str = "";
        if (data != null) {
            try {
                str = new String(data, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    /**
     * 写入byte类型缓存，注意：需要在特定的时候flush,一般在onPause()里面写,onDestroy()
     *
     * @param key  键值，一般是url
     * @param data 需要写入的数据
     */
    public void putByteCache(String key, byte[] data) {
        writeDiskCache(key, data);
    }

    /**
     * 读取byte类型缓存
     *
     * @param key 缓存的key
     */
    public byte[] getByteCache(String key) {
        return readDiskCache(key);
    }

    /**
     * 写入对象缓存后，注意：需要在特定的时候flush,一般在onPause()里面写,onDestroy()
     *
     * @param clazz  对象类型
     * @param key    缓存键值
     * @param object 对象
     */
    public void putObjectCache(Class<?> clazz, String key, Object object) {
        String json = new Gson().toJson(object, clazz);
        try {
            writeDiskCache(key, json.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            L.e(TAG, "编码转换错误", e);
        }
    }

    /**
     * 读取对象缓存
     *
     * @param clazz 对象类型
     * @param key   缓存键值
     * @return
     */
    public <T> T getObjectCache(Class<T> clazz, String key) {
        T object = null;
        try {
            object = new Gson().fromJson(new String(readDiskCache(key), "utf-8"), clazz);
        } catch (UnsupportedEncodingException e) {
            L.e(TAG, "编码转换错误", e);
        }
        return object;
    }

    /**
     * 同步记录,不同步则提取不了缓存
     */
    public void flush() {
        flushDiskCache();
    }

    /**
     * 删除一条缓存
     *
     * @param key 缓存键值
     */
    public void remove(String key) {
        readDiskCache(key);
    }

    /**
     * 删除所有缓存
     */
    public void removeAll() {
        clearCache();
    }

    /**
     * 关闭磁盘缓存
     */
    public void close() {
        closeDiskCache();
    }

    /**
     * 获取缓存大小
     */
    public String getCacheSize() {
        return super.getCacheSize();
    }

    /**
     * 清除缓存
     */
    public static void cleanApplication(Context context, String... path) {
        clearInternalCache(context);    //清除本应用的内部缓存
        clearExternalCache(context);    //清除本应用的外部缓存
        clearDataBase(context);     //清除数据库的所有数据
        cleanSharedPreference(context);     //清除SharedPreference的缓存
        cleanFiles(context);    //清除/data/data/com.xxx.xxx/files下的内容
    }

    /**
     * 清除本应用内部的缓存
     *
     * @param context
     */
    private static void clearInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用的外部缓存
     *
     * @param context
     */
    private static void clearExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除数据库的缓存
     *
     * @param context
     */
    private static void clearDataBase(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * 清除数据库的缓存
     *
     * @param context
     */
    private static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * * 清除/data/data/com.xxx.xxx/files下的内容 * *
     *
     * @param context
     */
    private static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param dir
     */
    private static boolean deleteFilesByDirectory(File dir) {
        if (dir == null) {
            return true;
        }
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteFilesByDirectory(new File(dir,
                        children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 获取缓存的大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    // 获取文件
    // Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/
    // 目录，一般放一些长时间保存的数据
    // Context.getExternalCacheDir() -->
    // SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            // return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
