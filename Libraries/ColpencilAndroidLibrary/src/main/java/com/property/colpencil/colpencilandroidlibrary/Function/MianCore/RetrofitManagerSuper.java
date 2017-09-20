package com.property.colpencil.colpencilandroidlibrary.Function.MianCore;

import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description:
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/7/28
 */
public class RetrofitManagerSuper {

    private static RetrofitManagerSuper mInstance;

    private Retrofit retrofit;

    public static  RetrofitManagerSuper getmInstance(Context context,String baseUrl){
        if (mInstance == null){
            synchronized (RetrofitManagerSuper.class){
                if (mInstance == null)
                    mInstance = new RetrofitManagerSuper(context,baseUrl);
            }
        }
        return mInstance;
    }

    public RetrofitManagerSuper(Context context,String baseUrl){
        PersistentCookieJar persistentCookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);



        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676,TimeUnit.MILLISECONDS)
                .addInterceptor(logging)
                .cookieJar(persistentCookieJar)
                .build();


        retrofit = new Retrofit.Builder().
                baseUrl(baseUrl).
                addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public  <T> T createService(Class<T> clz){
        return retrofit.create(clz);
    }

}
