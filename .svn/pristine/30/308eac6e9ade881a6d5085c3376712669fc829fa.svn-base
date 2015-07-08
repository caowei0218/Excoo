package com.eoe.excoo.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

import com.eoe.excoo.activity.UserActivity;
import com.eoe.excoo.bean.UserBean;
import com.eoe.excoo.util.JsonBinder;

public class ModiftUserWebservice extends AsyncTask<String, Integer, String> {
	private JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
	public final String METHODNAME = "modifyUser";
	private HttpTransportSE ht = null;
	private SoapSerializationEnvelope envelope;
	private SoapObject send = null;

	private UserActivity mUserActivity;
	private UserBean userBean;

	public ModiftUserWebservice(UserActivity mUserActivity, UserBean userBean) {
		this.mUserActivity = mUserActivity;
		this.userBean = userBean;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		mUserActivity.beginWaitDialog("正在修改用户", true);
		ht = new HttpTransportSE(WebserviceUtils.HTTPTRANSPORTSE);
		ht.debug = false;// 开启ht的调试模式，以打印调试信息。
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		send = new SoapObject(WebserviceUtils.NAMESPACE, METHODNAME);
		envelope.bodyOut = send;
		send.addProperty("user", jsonBinder.toJson(userBean));
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
				resultString = "修改成功";
				saveLoginUserName();
			} else {
				resultString = "修改失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultString = "返回结果失败，请检查网络";
		}
		return resultString;
	}

	private void saveLoginUserName() {
		SharedPreferences prefereces = mUserActivity.getSharedPreferences(
				"user", Context.MODE_PRIVATE);
		Editor editor = prefereces.edit();
		editor.putString("email", userBean.getEmail());
		editor.putString("cellphone", userBean.getCellphone());
		editor.putString("nickName", userBean.getNickname());
		editor.putString("gender", userBean.getGender());
		editor.putString("age_group", userBean.getAge_group());
		editor.commit();
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		mUserActivity.endWaitDialog(true);
		mUserActivity.showToast(result);
		if (result.equals("修改成功")) {
			mUserActivity.finish();
		}
	}

}
