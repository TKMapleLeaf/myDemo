package com.example.retrofitrxcaceh.rxcache;

import android.content.Context;

import com.google.gson.JsonObject;

/**
 * 创建者     Maggie
 * 创建时间   2017/8/1 11:57
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class GetAppMsgService extends BaseService {

    public GetAppMsgService(Context context) {
        super(context);
    }

    public void getAppMsg(String version, final CallBack<JsonObject> callBack) {
        doHttp(mRetrofitService.getAppMsg(version), callBack);
    }
}
