package com.alipay.sdk.pay.demo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.demo.util.OrderInfoUtil2_0;

import java.util.Map;

/**
 *  重要说明:
 *  
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
 */
public class PayDemoActivity extends FragmentActivity {
	
	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2017041806782991";
	
	/** 支付宝账户登录授权业务：入参pid值 */
	public static final String PID = "2088221626451032";
	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "";

	/** 商户私钥，pkcs8格式 */
	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
	/** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
	public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0uTfKpt1AMpmMzVE7peB0sqP0uyb3uLV5PmogKsCMnDNdJbhfhktjDB9E3Snf0LTcwMcmI+xxQmvFyiJk94WMaYvsZkJepUD+iXPbE4faXDkccBAEVJxJIhZvLxD/dKOl5R6p7Y/ZPTJnzp5xmEpdPKEov6ZnSkCxuCM1AFK38vB2rlP74btPaCB8xfPskhBEJvy+k/UUZOx2OppM8+/ewnmb9Ka9aTqyle1H4XriV4FTG//pOokQtzZBWZVQUBPfnlh1sKF9akxuAsXFou9j5h2PtunefPqSNpsm3uV1/AyZkjJwIiXvbeGIS6c64vBR5fPrxXciKdjkmMFFiCkZAgMBAAECggEAOGohGmegZWvmNufY1c/gE024lKrfbTEBpUlwg34VAU+5UPh3Z4paeh7MnAiWC8Hw04ByKlvIcUqUrfd9aRtDWyOPhEofqb/YXBy4R8j+/ZEQWuo4dcGbz9CORELjAXt9okm1msIHx36s/26mUzuA6z4D6N56qX+qVk/u3ZquN7FeQEC58LC+df6LnZrMxL9PiTTKavAeSyxFkrwWKxOuNpFJfEuTsJxTJ9sN2YuBpLOM+llAuk467g59M1YM1qp2YYBpuS5XQBFyqprrTQemVLevRL7Hisrio87guicEZpZQAFOut3qZ72KSR/SI7+uln7y4GRfudsZwqjXN3UqHAQKBgQDcBLVVukjJbB6N0eVLHeBucDt0uSUxCFVv9RJK/5RzHBxjurZyo8xkj+7yyip2rU2iUB8jBxP44JXybMQJEyeyEt76or1wReo0h0zKZr8EMINLDoB/67QPP/pCUOTDLfKO0tMg6S0V+fj2aX46PeWtabKIw4GDzyjypNxAcf238QKBgQDSR2PBZe2L3ng6WIJHQh3rgCA+Hsc4blWAkkFM4pXN+DACCexkDJk14s5ZdeohqUYDxwU7vaZCjQ/hbUhWcnlH5b1MiRhG1byS+5amQT8f7YLBuzlp2odH4xKFOHxxJWQUzju9Rauw21jq43QX91nXbJ0iWz6s48XRZLhfc6hrqQKBgQCgay5sGy8G7bY5YVj1tAr3ew1exb/ASHtC7kUwOblbVJu+e1ybqaJVkDyA+baMYT/BoPmS6I4/YED0SSmGFFlVFT1PMWk+ezuQoTX0PYX44AZaq3A11P2sQNGPmLuzM8UvLbiSvnEJHQHRaImInajhD7gEz8eW+2y3vkx5EKQVgQKBgQCj0Nn2IOIexQIE8k8MFIYaoYZ83p11aP9TWp1kuGDcRS2haPmwN+BkCn2XVDVCZA0XhESfde/AiQSGKgzgy+2QBXqj1WoERR7TowyKGOqEhRQIuULRJF2fs1cKUicgPnDphIElwtEuxrNOykP3J8vl98eyrEL3jbrelgjM092xgQKBgB1uvPK22wWkfNXS3ymkSPwTinclcPur3/2y3ZHl9LdCQTDEdAGovn0EcrjO/IBoEMWvUhdNXZTtMqpGrDJ/cJr+ciWUAwDEvyXrWTlxhqDfaESZQ2cgPOMtuoVIFPyIi/rr4i19KIKVAg417TROrdoBWB6gd8uvBvQrOyhXtfBe";
	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMStleK6i5AOGkd5NPUYFlkZJuk56aLhWR8U1ataEGWALOA3d8n3XaIO9tQEzJJUVJIKbCDIR9D1zPS9LPchloqb0OHk9y9I4SJss1ZQZayDWW179ojpOzTqhmto7d566+mebddjRcNiUwD2bDGmcWXigCyAvncHKaW4nuO9iuVfAgMBAAECgYEAu/48PasXycthHR5jGz855VJgWh/sDa+e01HD5vTApXR98Je0XY2fp07sab5omBoZeDqUHkWyN68riGfmuhYV4JyWppub5iayzxHS2N5761PmB+2RxxD6bF7k+cy1qG84sZw0t3qUaChCJiizK5LAd9uNSv50n9Dr7MwHz3OgHcECQQDgofT1SdwQ3WJdXUIkeYhe0LaXbO3hiT5LmfO685TQMov2efYgBjfa22VvFxJd1tei2ml6mYWIx+1zAfe+xwu/AkEA4CRS7xH1tbEJknLVbpWwWjHCaA0bd155y/9/Bkvm0taOtm4pdddm5Mrz+w+dh1mYivWy01c0NLy9I6mmWUUOYQJAWOzEZDYRADwjrII2pOnXqnFFVzywDxCdsKAJdIDo8GKSNciiPps3kVQ5G3kutCdQxg9gokAUNMmwnk6xHLz/UQJBALEXgf8prXz0d5+h40gQNNnOXs9fK8hQeOLY5z/OUH1c0D0LJO7aVY2HXOWMHOaHv6JrJfMc/z57sOSwcaIukYECQQCUhwTfT5hjn1F1yzvCb/l9HugT3qPlgtHCWAtWiPzFS3nbTFmB0szkoW0/ao9uddsMI5MMGN88xy34aN1FPhi5";
	
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				@SuppressWarnings("unchecked")
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
				/**
				 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为9000则代表支付成功
				if (TextUtils.equals(resultStatus, "9000")) {
					// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
				}
				break;
			}
			case SDK_AUTH_FLAG: {
				@SuppressWarnings("unchecked")
				AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
				String resultStatus = authResult.getResultStatus();

				// 判断resultStatus 为“9000”且result_code
				// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
				if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
					// 获取alipay_open_id，调支付时作为参数extern_token 的value
					// 传入，则支付账户为该授权账户
					Toast.makeText(PayDemoActivity.this,
							"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
							.show();
				} else {
					// 其他状态值则为授权失败
					Toast.makeText(PayDemoActivity.this,
							"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

				}
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
	}
	
	/**
	 * 支付宝支付业务
	 * 
	 * @param v
	 */
	public void payV2(View v) {
		if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							//
							finish();
						}
					}).show();
			return;
		}
	
		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * orderInfo的获取必须来自服务端；
		 */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
		final String orderInfo = orderParam + "&" + sign;
		
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(PayDemoActivity.this);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());
				
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * 支付宝账户授权业务
	 * 
	 * @param v
	 */
	public void authV2(View v) {
		if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
				|| (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
				|| TextUtils.isEmpty(TARGET_ID)) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
						}
					}).show();
			return;
		}

		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * authInfo的获取必须来自服务端；
		 */
		boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
		String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
		
		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
		final String authInfo = info + "&" + sign;
		Runnable authRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造AuthTask 对象
				AuthTask authTask = new AuthTask(PayDemoActivity.this);
				// 调用授权接口，获取授权结果
				Map<String, String> result = authTask.authV2(authInfo, true);

				Message msg = new Message();
				msg.what = SDK_AUTH_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread authThread = new Thread(authRunnable);
		authThread.start();
	}
	
	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
	 * 
	 * @param v
	 */
	public void h5Pay(View v) {
		Intent intent = new Intent(this, H5PayDemoActivity.class);
		Bundle extras = new Bundle();
		/**
		 * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
		 * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
		 * 商户可以根据自己的需求来实现
		 */
		String url = "http://m.taobao.com";
		// url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
		extras.putString("url", url);
		intent.putExtras(extras);
		startActivity(intent);
	}

}
