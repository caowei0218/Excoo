package com.eoe.excoo.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.eoe.excoo.activity.AddArticleActivity;
import com.eoe.excoo.activity.ShowActivity;
import com.eoe.excoo.bean.ItemBean;
import com.eoe.excoo.bean.UserBean;
import com.eoe.excoo.util.Common;
import com.eoe.excoo.util.JsonBinder;

public class AddItemWebservice extends AsyncTask<String, Integer, String> {
	private JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
	public final String METHODNAME = "saveItem";
	private HttpTransportSE ht = null;
	private SoapSerializationEnvelope envelope;
	private SoapObject send = null;

	private AddArticleActivity addArticleActivity;
	private Context context = null;
	private ItemBean userBean = null;
	private boolean temp;

	public AddItemWebservice(AddArticleActivity addArticleActivity,
			Context context, ItemBean userBean, boolean temp) {
		this.addArticleActivity = addArticleActivity;
		this.context = context;
		this.userBean = userBean;
		this.temp = temp;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		addArticleActivity.beginWaitDialog("�����ϴ�", true);
		ht = new HttpTransportSE(WebserviceUtils.HTTPTRANSPORTSE);
		ht.debug = false;// ����ht�ĵ���ģʽ���Դ�ӡ������Ϣ��
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		send = new SoapObject(WebserviceUtils.NAMESPACE, METHODNAME);
		envelope.bodyOut = send;
		userBean.setUser_id(Common.userCommon.getUser_id());
		String mtype = jsonBinder.toJson(userBean.getType());
		String mPics = jsonBinder.toJson(userBean.getPicBeans());
		userBean.setPicBeans(null);
		userBean.setType(null);
		String mItem = jsonBinder.toJson(userBean);
		String str = mItem + "-" + mtype + "-" + mPics;
		send.addProperty("item", str);
		envelope.addMapping(WebserviceUtils.NAMESPACE, "UserBean",
				UserBean.class);
		envelope.dotNet = false;
	}

	protected String doInBackground(String... params) {
		String resultString = null;
		try {
			ht.call(WebserviceUtils.NAMESPACE + "/" + METHODNAME, envelope);

		} catch (Exception e) {
			System.out.println(e.getMessage() + "����ʧ��");
			e.printStackTrace();
			resultString = "����ʧ�ܣ�������������";
			return resultString;
		}
		try {
			Object soapObject = (Object) envelope.getResponse();
			userBean.setItem_id(soapObject.toString());
			resultString = "�ϴ��ɹ�";
		} catch (Exception e) {
			e.printStackTrace();
			resultString = "�ϴ�ʧ��";
		}
		return resultString;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		addArticleActivity.endWaitDialog(true);
		Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
		if (temp) {
//			addArticleActivity.closeActivity();
		} else {
			Intent intent = new Intent(addArticleActivity, ShowActivity.class);
			addArticleActivity.startActivity(intent);
		}
		if (result.equals("�ϴ��ɹ�")) {
			addArticleActivity.closeActivity();
		}
	}
}
