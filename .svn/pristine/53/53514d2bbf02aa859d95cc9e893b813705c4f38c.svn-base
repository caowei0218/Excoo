package com.eoe.excoo.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.eoe.excoo.adapter.ShowAdapter;
import com.eoe.excoo.bean.UserBean;
import com.eoe.excoo.util.Base64Code;
import com.eoe.excoo.util.ImageUtil;

public class GetPicWebservice extends AsyncTask<String, Integer, String> {
	public final String METHODNAME = "getPicStrem";
	private HttpTransportSE ht = null;
	private SoapSerializationEnvelope envelope;
	private SoapObject send = null;
	private String pid = "";
	@SuppressWarnings("unused")
	private Context context = null;
	Bitmap bitmap = null;
	private ImageView imageView = null;
	private int i;
	ImageUtil imageUtil = new ImageUtil();

	public GetPicWebservice(int i, ImageView imageView, String pid) {
		this.pid = pid;
		this.imageView = imageView;
		this.i = i;
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
			ShowAdapter.images[i] = bitmap;
			resultString = "上传成功";
		} catch (Exception e) {
			e.printStackTrace();
			resultString = "上传失败";
		}
		return resultString;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		imageView.setImageBitmap(bitmap);
		imageUtil.savePic(bitmap, pid);
	}
}
