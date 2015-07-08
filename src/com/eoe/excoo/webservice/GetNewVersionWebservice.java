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
		myActivity.beginWaitDialog("���ڻ�ȡ��Ʒ", true);
		ht = new HttpTransportSE(WebserviceUtils.HTTPTRANSPORTSE);
		ht.debug = false;// ����ht�ĵ���ģʽ���Դ�ӡ������Ϣ��
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
			System.out.println(e.getMessage() + "����ʧ��");
			e.printStackTrace();
			resultString = "����ʧ�ܣ�������������";
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
					resultString = "���ؽ��ʧ��";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultString = "���ؽ��ʧ�ܣ���������";
		}
		return resultString;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		myActivity.endWaitDialog(true);
		if (result.equals("no")) {
			myActivity.showToast("�Ѿ������°汾");
		}
		if (result.equals("���汾ʧ��")) {
			myActivity.showToast(result);
		}
		if (result.equals("���ؽ��ʧ��")) {
			myActivity.showToast(result);
		}
		if (result.equals("yes")) {
			myActivity.showToast("���°汾���汾�ţ�" + newVersion + ",�������");
			Common.URL = URL;
			myActivity.refresh(newVersion);
		}
		if (result.equals("����ʧ�ܣ�������������")) {
			myActivity.showToast("����ʧ�ܣ�������������");
		}
	}
}
