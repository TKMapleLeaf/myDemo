package com.example.paydemo.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.paydemo.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
//    	api = AppHelper.getInstance().getWxApi();
		api = WXAPIFactory.createWXAPI(this, "");
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {

		// 0 成功 展示成功页面
		// -1 错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
		// -2 用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
			builder.show();
		}


		/*
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX){//微信支付回调
            if(resp.errCode==BaseResp.ErrCode.ERR_OK){//微信支付成功
                Intent intent = new Intent();
                intent.setAction(APIDefineConst.BROADCAST_ACTION_WEIXIN_PAY);
                LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
                lbm.sendBroadcast(intent);
                //成功
//              Toast.makeText(this,R.string.wxpay_success, 0).show();
            }else{
//              Toast.makeText(this,R.string.wxpay_success, 0).show();
            }
        }*/
	}
}