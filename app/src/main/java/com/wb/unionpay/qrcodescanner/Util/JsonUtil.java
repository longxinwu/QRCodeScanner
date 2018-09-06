package com.wb.unionpay.qrcodescanner.Util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    private static Gson GSON= new GsonBuilder().disableHtmlEscaping().
            registerTypeAdapter(Class.class, new JsonSerializer<Class>() {
                @Override
                public JsonElement serialize(Class src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.getName());
                }
            }).create();
    public static <T> T fromJson(String json, Class<T> tClass){
        return GSON.fromJson(json, tClass);
    }
    public static <T> T fromJson(String json, Type type){
        return (T) GSON.fromJson(json, type);
    }
    public static String toJson(Object obj){
        return GSON.toJson(obj);
    }
    public static String filterKeyValue(String json, String... keyFieldNames){
        if(keyFieldNames == null){
            return json;
        }
        try{
            Map<String , String> km = new HashMap<>();
            for(String s : keyFieldNames){
                String[] ss =s.split(":");
                km.put(ss[0], ss.length > 1 ? ss[1] : null );
            }
            Object o = null;
            if(json.trim().startsWith("{")){
                o = GSON.fromJson(json, Map.class);
                filterJsonObject((Map<String, Object>) o, km);
                return toJson(o);
            }else if(json.trim().startsWith("[")){
                o = GSON.fromJson(json, List.class);
                filterJsonArray((List) o, km);
                return toJson(o);
            }else{
                return json;
            }
        }catch(Throwable th){
            Log.i("error", th.getMessage(), th.getCause());
            return json;
        }
    }
    private static void filterJsonObject(Map<String, Object> jo, Map<String, String> km){
        for(Map.Entry<String, Object> entry : jo.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value == null){
                continue;
            }
            if(value instanceof Map){
                filterJsonObject((Map) value, km);
            }else if(value instanceof List){
                filterJsonArray((List) value, km);
            }else if (value instanceof String){
                if(!km.containsKey(key)){
                    continue;
                }
                String rule = km.get(key);
                jo.put(key, filterByRule((String) value, rule));
            }
        }
    }
    private static void filterJsonArray(List<Object> ja, Map<String, String> km){
        for(int i=0;i<ja.size();i++){
            Object value = ja.get(i);
            if(value == null){
                continue;
            }
            if(value instanceof  Map){
                filterJsonObject((Map) value, km);
            }else if(value instanceof List){
                filterJsonArray((List) value, km);
            }
        }
    }
    private static String filterByRule(String value, String rule){
        if(value == null){
            return value;
        }
        if(rule == null){
            return "******";
        }
        if(rule.startsWith("R")){
            Integer i = Integer.valueOf(rule.substring(1));
            if(value.length() >i){
                value = "****" + value.substring(value.length() -i);
            }
        }else if (rule.startsWith("L")){
            Integer i = Integer.valueOf(rule.substring(1));
            if(value.length()>i){
                value = value.substring(0, i)+"****";
            }
        }else{
            throw new RuntimeException("Filter rule invalid");
        }
        return value;
    }
}
