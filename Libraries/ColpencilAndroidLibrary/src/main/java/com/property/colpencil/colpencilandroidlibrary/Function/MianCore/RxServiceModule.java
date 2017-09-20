package com.property.colpencil.colpencilandroidlibrary.Function.MianCore;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description:获取Service的一个实例
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public class RxServiceModule {

    //baseUrl
    private static final String BASETESTURL = "http://apis.baidu.com/showapi_open_bus/";
    //配置okhttp
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor
            (new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                            .addHeader("Accept-Encoding", "gzip, deflate")
                            .addHeader("Connection", "keep-alive")
                            .addHeader("Accept", "*/*")
                            .addHeader("Cookie", "add cookies here")
                            .build();
                    return chain.proceed(request);
                }

            }).build();
    //配置初始化Retrofit
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASETESTURL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //保证RxService为单例的
    private RxServiceModule() {
        //construct

    }

    //创建Api
    public static <T> T createApi(Class<T> clazz) {

        return retrofit.create(clazz);
    }

}
