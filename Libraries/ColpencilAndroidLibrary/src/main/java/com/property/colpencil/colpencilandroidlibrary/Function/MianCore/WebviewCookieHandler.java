package com.property.colpencil.colpencilandroidlibrary.Function.MianCore;

/**
 * Created by Administrator on 2016/12/3.
 */

import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

//import com.tencent.smtt.sdk.CookieManager;
//import com.tencent.smtt.sdk.CookieSyncManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public final class WebviewCookieHandler implements CookieJar {
   static private CookieManager webviewCookieManager ;
    static private  CookieSyncManager cookieSyncManager;
    public WebviewCookieHandler(Context context){
        cookieSyncManager= CookieSyncManager.createInstance(context);//TODO app.oncreate
        webviewCookieManager = CookieManager.getInstance();
        webviewCookieManager.setAcceptCookie(true);
//  //     webviewCookieManager.setAcceptThirdPartyCookies(true);
//       if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
//       {
//           //// webviewCookieManager.removeSessionCookie();//移除无 expired cookie
//           webviewCookieManager.removeExpiredCookie();
//       }
//       //if sdk<=
//       cookieSyncManager.startSync();
    }
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        String urlString = url.toString();
        boolean hasUpdate=false;
        for (Cookie cookie : cookies) {
            webviewCookieManager.setCookie(urlString, cookie.toString());
            hasUpdate=true;
        }
        if(hasUpdate){ //if sdk<=
           // android.webkit.CookieSyncManager.getInstance().sync();
            cookieSyncManager.sync();
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        String urlString = url.toString();
        String cookiesString = webviewCookieManager.getCookie(urlString);

        if (cookiesString != null && !cookiesString.isEmpty()) {
            //We can split on the ';' char as the cookie manager only returns cookies
            //that match the url and haven't expired, so the cookie attributes aren't included
            String[] cookieHeaders = cookiesString.split(";");
            List<Cookie> cookies = new ArrayList<>(cookieHeaders.length);

            for (String header : cookieHeaders) {
                cookies.add(Cookie.parse(url, header));
            }
            return cookies;
        }
        return Collections.emptyList();
    }
}