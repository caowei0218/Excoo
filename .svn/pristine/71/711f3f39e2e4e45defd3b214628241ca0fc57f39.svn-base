package com.eoe.excoo.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;

import com.eoe.excoo.activity.DeleteTypeActivity;
import com.eoe.excoo.bean.UserBean;

public class DeletetypeWebservice extends AsyncTask<String, Integer, String> {
	public final String METHODNAME = "deleteType";
	private HttpTransportSE ht = null;
	private SoapSerializationEnvelope envelope;
	private SoapObject send = null;

	private DeleteTypeActivity mDeleteTypeActivity;
	private String typeID;

	public DeletetypeWebservice(DeleteTypeActivity mDeleteTypeActivity,
			String typeID) {
		this.mDeleteTypeActivity = mDeleteTypeActivity;
		this.typeID = typeID;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		mDeleteTypeActivity.beginWaitDialog("正在删除", true);
		ht = new HttpTransportSE(WebserviceUtils.HTTPTRANSPORTSE);
		ht.debug = false;// 开启ht的调试模式，以打印调试信息。
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		send = new SoapObject(WebserviceUtils.NAMESPACE, METHODNAME);
		envelope.bodyOut = send;
		send.addProperty("typeID", typeID);
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
			if (soapObject.toString().equals("1")) {
				resultString = "删除成功";
			} else {
				resultString = "删除失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultString = "返回结果失败，请检查网络";
		}
		return resultString;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		mDeleteTypeActivity.endWaitDialog(true);
		;
		mDeleteTypeActivity.showToast(result);
		if (result.equals("删除成功")) {
			mDeleteTypeActivity.finish();
		}
	}
}
