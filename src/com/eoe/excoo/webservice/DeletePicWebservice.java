package com.eoe.excoo.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.eoe.excoo.activity.DetailActivity;
import com.eoe.excoo.bean.PicBean;
import com.eoe.excoo.bean.UserBean;

public class DeletePicWebservice extends AsyncTask<String, Integer, String> {
	public final String METHODNAME = "deletePic";
	private HttpTransportSE ht = null;
	private SoapSerializationEnvelope envelope;
	private SoapObject send = null;

	private DetailActivity mDetailActivity;
	private Context context = null;
	private PicBean mPicBean = null;
	@SuppressWarnings("unused")
	private int i;

	public DeletePicWebservice(DetailActivity mDetailActivity, Context context,
			PicBean mPicBean, int i) {
		this.mDetailActivity = mDetailActivity;
		this.context = context;
		this.mPicBean = mPicBean;
		this.i = i;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		mDetailActivity.beginWaitDialog("����ɾ��", true);
		ht = new HttpTransportSE(WebserviceUtils.HTTPTRANSPORTSE);
		ht.debug = false;// ����ht�ĵ���ģʽ���Դ�ӡ������Ϣ��
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		send = new SoapObject(WebserviceUtils.NAMESPACE, METHODNAME);
		envelope.bodyOut = send;
		send.addProperty("picID", mPicBean.getPic_id());
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
				resultString = "ɾ��ͼƬ�ɹ�";
			} else {
				resultString = "ɾ��ͼƬʧ��";
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultString = "ɾ��ͼƬʧ��";
		}
		return resultString;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		mDetailActivity.initPoint();
		mDetailActivity.endWaitDialog(true);
		Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
	}
}
