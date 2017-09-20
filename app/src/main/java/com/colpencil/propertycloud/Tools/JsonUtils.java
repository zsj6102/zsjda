package com.colpencil.propertycloud.Tools;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

import cn.finalteam.toolsfinal.StringUtils;

public class JsonUtils {

    public static boolean isBadJson(String json) {
        return !isGoodJson(json);
    }

    public static boolean isGoodJson(String json) {
        if (StringUtils.isBlank(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            ColpencilLogger.e("bad json: " + json);
            return false;
        }
    }

}
