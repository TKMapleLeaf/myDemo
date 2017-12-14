package com.example.notify;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

public class JavaScriptMetod {

    private WebView mWebView;

    public JavaScriptMetod(Context context, WebView webView) {
        mWebView = webView;
    }

    /**
     * js调用本地方法
     *
     * @param json
     */
    @JavascriptInterface
    public void callMethod(String json) {
        String type = "";
        String info = "";
        try {
            JSONObject object = new JSONObject(json);
            type = object.optString("type");
            info = object.optString("state");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试用的方法
     *
     * @param json
     */
    @JavascriptInterface
    public void callJavascriptMethod(String json) {
        JSONObject object = new JSONObject();
        try {
            object.put("type", "updata");
            object.put("state", "success");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        invokeJavascriptMethod("", object.toString());
    }

    /**
     * 本地方法调用js
     *
     * @param callback
     * @param json
     */
    private void invokeJavascriptMethod(final String callback, final String json) {
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:JavascriptMethod(" + json.toString() + ")");
//                mWebView.loadUrl("javascript:" + callback + "(" + json + ")");
            }
        });
    }

}
