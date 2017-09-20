package com.property.colpencil.colpencilandroidlibrary.Function.ColpencilJson;

import com.google.gson.JsonParseException;

import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @Description: json解析工具
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public class ColpencilJsonFinal<T> {

    /**
     * @Title: jsonStringToJsonObject
     * @Description: The json string into objects
     * @param @param response  Json string of request and response
     * @param @return
     * @return JSONObject    Returns the jsonobject
     * @throws   Json conversion abnormal
     */
    public static JSONObject jsonStringToJsonObject(String response) {

        JSONObject myJsonObject = null;
        try {
            if (!"".equals(response)){
                myJsonObject = new JSONObject(response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return myJsonObject;
    }

    /**
     * @Title: analysisJsonObject
     * @Description:   This is a used to parse json object
     * @param @param response   Json string of request and response
     * @param @param optResult  Need to capture the string
     * @param @param clazz     Parsing into objects
     * @param @return
     * @return T    The return type for an object
     * @throws     Json conversion abnormal
     */
    public <T> T analysisJsonObject(String response, String optResult,
                                    Class<?> clazz) {
        T t = null;
        try {
            JSONObject myJsonObject = jsonStringToJsonObject(response);
            String result = myJsonObject.optString(optResult);
            ColpencilGetNetDataMaster<T> getData = new ColpencilGetNetDataMaster<T>();
            t = getData.getJson2Bean(result, clazz);

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return t;
    }

    /**
     * @Title: analysisJsonArray
     * @Description: This is a used to parse the json array
     * @param @param response  Network request return a json string
     * @param @param optResult Need to capture the string
     * @param @param clazz      Parsing into Array
     * @param @return
     * @return ArrayList<T>    The return type for a set list
     * @throws  Json conversion abnormal
     */
    public ArrayList<T> analysisJsonArray(String response, String optResult,
                                          Class<?> clazz) {

        ArrayList<T> list = new ArrayList<T>();
        try {
            JSONObject myJsonObject = jsonStringToJsonObject(response);
            JSONArray jsonArr = myJsonObject.optJSONArray(optResult);
            if (jsonArr != null && jsonArr.length() > 0) {
                ColpencilGetNetDataMaster<T> getData = new ColpencilGetNetDataMaster<T>();
                list = getData.getJson2List(jsonArr.toString(), clazz);

            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
