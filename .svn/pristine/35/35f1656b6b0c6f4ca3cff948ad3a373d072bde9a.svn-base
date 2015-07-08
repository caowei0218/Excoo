package com.eoe.excoo.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.os.AsyncTask;

import com.eoe.excoo.activity.MyActivity;
import com.eoe.excoo.bean.UserBean;
import com.eoe.excoo.util.Common;

public class GetNewVersionWebservice extends AsyncTask<String, Integer, String> {
	public final String METHODNAME = "getNewVersion";
	private HttpTransportSE ht = null;
	private SoapSerializationEnvelope envelope;
	private SoapObject send = null;

	private MyActivity myActivity;
	@SuppressWarnings("unused")
	private Context context = null;
	private String versionNumber;
	private String URL;
	private String newVersion;

	public GetNewVersionWebservice(MyActivity myActivity, Context context,
			String versionNumber) {
		this.myActivity = myActivity;
		this.context = context;
		this.versionNumber = versionNumber;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		myActivity.beginWaitDialog("正在获取物品", true);
		ht = new HttpTransportSE(WebserviceUtils.HTTPTRANSPORTSE);
		ht.debug = false;// 开启ht的调试模式，以打印调试信息。
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		send = new SoapObject(WebserviceUtils.NAMESPACE, METHODNAME);
		envelope.bodyOut = send;
		send.addProperty("versionID", versionNumber);
		envelope.addMapping(WebserviceUtils.NAMESPACE, "UserBean",
				UserBean.class);
		envelope.dotNet = false;
	}

	protected String doInBackground(String... params) {
		String resultString = null;
		try {
			ht.call(WebserviceUtils.NAMESPACE + "/" + METHODNAME, envelope);

		} catch (Exception e) {
			System.out.println(e.getMessage() + "连接失败");
			e.printStackTrace();
			resultString = "连接失败，请检查网络连接";
			return resultString;
		}
		try {
			Object soapObject = (Object) envelope.getResponse();
			if (soapObject.toString().equals("0")) {
				resultString = "no";
			} else {
				try {
					String[] split = soapObject.toString().split("#");
					URL = split[0];
					newVersion = split[1];
					resultString = "yes";
				} catch (Exception e) {
					resultString = "返回结果失败";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultString = "返回结果失败，请检查网络";
		}
		return resultString;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		myActivity.endWaitDialog(true);
		if (result.equals("no")) {
			myActivity.showToast("已经是最新版本");
		}
		if (result.equals("检查版本失败")) {
			myActivity.showToast(result);
		}
		if (result.equals("返回结果失败")) {
			myActivity.showToast(result);
		}
		if (result.equals("yes")) {
			myActivity.showToast("有新版本，版本号：" + newVersion + ",请检查更新");
			Common.URL = URL;
			myActivity.refresh(newVersion);
		}
		if (result.equals("连接失败，请检查网络连接")) {
			myActivity.showToast("连接失败，请检查网络连接");
		}
	}
}
