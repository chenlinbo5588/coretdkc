package com.clb.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

public class JsonHelper {
    private static final SerializeConfig _config;
    private static final SerializerFeature[] _features;

    public JsonHelper() {
    }

    public static String object2Json(Object obj) {
        String json = JSON.toJSONString(obj, _config, _features);
        return json;
    }

    public static <T> T json2Object(String json, Class<T> clazz) {
        T t = JSON.parseObject(json, clazz);
        return t;
    }

    public static <T> List<T> json2List(String json, Class<T> clazz) {
        List<T> list = JSON.parseArray(json, clazz);
        return list;
    }

    public static Object getObject(String json) {
        Object obj = JSON.parse(json);
        return obj;
    }

    public static Object getObject(String json, Feature... features) {
        Object obj = JSON.parse(json, features);
        return obj;
    }

    public static JSONObject getJSONObject(String json) {
        JSONObject obj = JSON.parseObject(json);
        return obj;
    }

    public static JSONObject getJSONObject(String json, Feature... features) {
        JSONObject obj = JSON.parseObject(json, features);
        return obj;
    }

    public static JSONArray getJSONArray(String json) {
        JSONArray array = JSON.parseArray(json);
        return array;
    }

    public static Map getMap(String json) {
        Map map = (Map)JSON.parseObject(json, Map.class);
        return map;
    }

    static {
        _features = new SerializerFeature[]{SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteDateUseDateFormat};
        _config = new SerializeConfig();
    }
}
