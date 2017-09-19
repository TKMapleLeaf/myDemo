package com.example.moments.http;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 请求参数封装
 */
public class RequestParams {

    private ConcurrentHashMap<String, String> urlParams;

    public RequestParams() {
        init();
    }

    private void init(){
        urlParams = new ConcurrentHashMap();
    }

    public void put(String key, String values) {
        if (key != null && values != null) {
            urlParams.put(key, values);
        }
    }

    public Map<String, String> getMapParams() {
        Map<String, String> params = new HashMap<>();
        if (urlParams != null) {
            params.putAll(urlParams);
            return params;
        } else {
            return params;
        }
    }

    public Map<String, String> getMapParamsAES() {

        Map<String, String> params = new HashMap<>();
        if (urlParams != null) {
            JSONObject object = new JSONObject();

            for (Map.Entry<String, String> entry : urlParams.entrySet()) {
                try {
                    object.put(entry.getKey(),entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
//            String aesResult = AES.encrypt(object.toString());
//            if (aesResult != null){
//                params.put(Constants.HTTPPRAMS,aesResult);
//            }else {
//                params.put(Constants.HTTPPRAMS,"");
//            }
            return params;
        } else {
            return params;
        }
    }


}
