package com.eoe.excoo.webservice;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;

import com.eoe.excoo.bean.UserBean;
import com.eoe.excoo.util.Base64Code;
import com.eoe.excoo.util.ImageUtil;

public class GetPicDetailWebservice extends AsyncTask<String, Integer, String> {
	public final String METHODNAME = "getPicStrem";
	private HttpTransportSE ht = null;
	private SoapSerializationEnvelope envelope;
	private SoapObject send = null;
	private String pid = "";
	Bitmap bitmap = null;
	ArrayList<View> imgList;
	ImageUtil imageUtil = new ImageUtil();

	public GetPicDetailWebservice(String pid) {
		this.pid=pid;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		ht = new HttpTransportSE(WebserviceUtils.HTTPTRANSPORTSE);
		ht.debug = false;// 开启ht的调试模式，以打印调试信息。
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		send = new SoapObject(WebserviceUtils.NAMESPACE, METHODNAME);
		envelope.bodyOut = send;
		send.addProperty("picID", pid);
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
			String str = soapObject.toString();
			bitmap = Base64Code.String2Bitmap(str);
			resultString = "上传成功";
		} catch (Exception e) {
			e.printStackTrace();
			resultString = "上传失败";
		}
		return resultString;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		imageUtil.savePic(bitmap, pid);		
	}
}
