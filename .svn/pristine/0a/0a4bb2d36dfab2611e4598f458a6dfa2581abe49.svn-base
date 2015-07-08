package com.eoe.excoo.webservice;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.eoe.excoo.activity.DetailActivity;
import com.eoe.excoo.bean.UserBean;
import com.eoe.excoo.util.Base64Code;
import com.eoe.excoo.util.ImageUtil;

public class GetItemPicWebservice extends AsyncTask<String, Integer, String> {
	public final String METHODNAME = "getPicStrem";
	private HttpTransportSE ht = null;
	private SoapSerializationEnvelope envelope;
	private SoapObject send = null;
	private String pid = "";
	private View view = null;
	Bitmap bitmap = null;
	ImageView mImageView = null;
	ViewPager viewPager;
	ArrayList<View> imgList;
	ImageUtil imageUtil = new ImageUtil();

	public GetItemPicWebservice(View view, String pid,
			DetailActivity mDetailActivity, ViewPager viewPager) {
		this.pid = pid;
		this.view = view;
		this.viewPager = viewPager;
		mImageView = new ImageView(mDetailActivity);
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
			mImageView.setImageBitmap(bitmap);
			view = mImageView;
			viewPager.addView(view, 0);
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
