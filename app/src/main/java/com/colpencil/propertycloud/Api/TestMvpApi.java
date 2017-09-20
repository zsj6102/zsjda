package com.colpencil.propertycloud.Api;

import com.colpencil.propertycloud.Bean.LoginResultEntity;
import com.colpencil.propertycloud.Bean.ResultEntity;
import com.colpencil.propertycloud.Bean.TestMvpEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;


public interface TestMvpApi {

    @Headers("apikey:83ec99fff780989a5376a1bc595ed5ff")
    @GET("showapi_joke/joke_text")
    Observable<TestMvpEntity> getJoke(@Query("page") int page);

    @GET("showapi_joke/joke_text")
    Call<TestMvpEntity> callJoke(@Header("apikey") String apikey, @Query("page") int page);

    @GET("hybm/mobileMember!mobile_login.do")
    Observable<LoginResultEntity> login(@Query("login_name") String username, @Query("password") String checknum);
}
