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
import com.eoe.excoo.util.JsonBinder;

public class AddPicWebservice extends AsyncTask<String, Integer, String> {
	private JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
	public final String METHODNAME = "addPic";
	private HttpTransportSE ht = null;
	private SoapSerializationEnvelope envelope;
	private SoapObject send = null;

	private DetailActivity mDetailActivity;
	private Context context = null;
	private PicBean mPicBean = null;

	public AddPicWebservice(DetailActivity mDetailActivity, Context context,
			PicBean mPicBean) {
		this.mDetailActivity = mDetailActivity;
		this.context = context;
		this.mPicBean = mPicBean;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		mDetailActivity.beginWaitDialog("正在添加", true);
		ht = new HttpTransportSE(WebserviceUtils.HTTPTRANSPORTSE);
		ht.debug = false;// 开启ht的调试模式，以打印调试信息。
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		send = new SoapObject(WebserviceUtils.NAMESPACE, METHODNAME);
		envelope.bodyOut = send;
		send.addProperty("pic", jsonBinder.toJson(mPicBean));
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
			if (soapObject.toString().length() == 32) {
				resultString = "添加图片成功";
			} else {
				resultString = "添加图片失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultString = "添加图片失败";
		}
		return resultString;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		mDetailActivity.endWaitDialog(true);
		Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
	}
}
