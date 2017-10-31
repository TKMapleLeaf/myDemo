package com.example.paydemo.wechatpay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.paydemo.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class WechatPayActivity extends AppCompatActivity {
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_pay);
        api = WXAPIFactory.createWXAPI(this, "");
    }

    /**
     * 微信支付
     *
     *  {"appid":"wxb4ba3c02aa476ea1","partnerid":"1900006771","package":"Sign=WXPay","noncestr":"4bb5b85e2f9d68864a8946056e658853","timestamp":1506570075,"prepayid":"wx2017092811411574bddfdb290542896166","sign":"14087F0114454376469681952047FC8C"}
     */
    private void wechatPay(String content) {

        JSONObject json = null;
        try {
            json = new JSONObject(content);
            if(null != json && !json.has("retcode") ){
                PayReq req = new PayReq();
                //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                req.appId			= json.getString("appid");
                req.partnerId		= json.getString("partnerid");
                req.prepayId		= json.getString("prepayid");
                req.nonceStr		= json.getString("noncestr");
                req.timeStamp		= json.getString("timestamp");
                req.packageValue	= json.getString("package");
                req.sign			= json.getString("sign");
                req.extData			= "app data"; // optional
                Toast.makeText(WechatPayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                api.sendReq(req);
            }else{
                Toast.makeText(WechatPayActivity.this, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(WechatPayActivity.this, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

       /* String url = "http://wxpay.wxutil.com/pub_v2/app/app_pay.php";
        Toast.makeText(RechargeActivity.this, "获取订单中...", Toast.LENGTH_SHORT).show();
        Call<String> call = HttpRequests.getinstance().getHttpService().getWechatPay(url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null && response.isSuccessful()){
                    String content = response.body();


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });*/
    }

}
