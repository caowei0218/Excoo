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
		mDeleteTypeActivity.beginWaitDialog("����ɾ��", true);
		ht = new HttpTransportSE(WebserviceUtils.HTTPTRANSPORTSE);
		ht.debug = false;// ����ht�ĵ���ģʽ���Դ�ӡ������Ϣ��
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
			System.out.println(e.getMessage() + "����ʧ��");
			e.printStackTrace();
			resultString = "����ʧ�ܣ�������������";
			return resultString;
		}
		try {
			Object soapObject = (Object) envelope.getResponse();
			if (soapObject.toString().equals("1")) {
				resultString = "ɾ���ɹ�";
			} else {
				resultString = "ɾ��ʧ��";
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultString = "���ؽ��ʧ�ܣ���������";
		}
		return resultString;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		mDeleteTypeActivity.endWaitDialog(true);
		;
		mDeleteTypeActivity.showToast(result);
		if (result.equals("ɾ���ɹ�")) {
			mDeleteTypeActivity.finish();
		}
	}
}
