package com.eoe.excoo.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.eoe.excoo.activity.ShowActivity;
import com.eoe.excoo.bean.ItemBean;
import com.eoe.excoo.bean.UserBean;

public class DeleteItemWebservice extends AsyncTask<String, Integer, String> {
	public final String METHODNAME = "deleteItem";
	private HttpTransportSE ht = null;
	private SoapSerializationEnvelope envelope;
	private SoapObject send = null;

	private ShowActivity mShowActivity;
	private Context context = null;
	private ItemBean userBean = null;

	public DeleteItemWebservice(ShowActivity mShowActivity, Context context,
			ItemBean userBean) {
		this.mShowActivity = mShowActivity;
		this.context = context;
		this.userBean = userBean;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		mShowActivity.beginWaitDialog("正在删除", true);
		ht = new HttpTransportSE(WebserviceUtils.HTTPTRANSPORTSE);
		ht.debug = false;// 开启ht的调试模式，以打印调试信息。
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		send = new SoapObject(WebserviceUtils.NAMESPACE, METHODNAME);
		envelope.bodyOut = send;
		send.addProperty("itemID", userBean.getItem_id());
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
			resultString = "删除失败";
		}
		return resultString;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		mShowActivity.endWaitDialog(true);
		mShowActivity.getListItem(true);
		Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
	}
}
