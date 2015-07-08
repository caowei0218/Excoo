package com.eoe.excoo.webservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.util.Log;

import com.eoe.excoo.activity.ShowActivity;
import com.eoe.excoo.bean.ItemBean;
import com.eoe.excoo.bean.PicBean;
import com.eoe.excoo.bean.TypeBean;
import com.eoe.excoo.util.Common;
import com.eoe.excoo.util.JsonBinder;

/**
 * 获得物品
 * */
public class VagueArticleWebservice extends AsyncTask<String, Integer, String> {
	private JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
	// 调用的方法名
	public final String MehtodName = "getItemListByCondition";

	// private ArticleBean articleBean;
	private HttpTransportSE ht = null;
	private SoapSerializationEnvelope envelope;
	private SoapObject send = null;
	private ShowActivity fatherMain = null;
	private String condition;

	public VagueArticleWebservice(ShowActivity fatherMain, String condition) {
		this.fatherMain = fatherMain;
		this.condition = condition;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		fatherMain.beginWaitDialog("正在查询", true);
		ht = new HttpTransportSE(WebserviceUtils.HTTPTRANSPORTSE);
		ht.debug = false;
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		send = new SoapObject(WebserviceUtils.NAMESPACE, MehtodName);
		send.addProperty("uid", Common.userCommon.getUser_id());//
		send.addProperty("condition", condition);
		envelope.bodyOut = send;
		envelope.dotNet = false;
	}

	protected String doInBackground(String... params) {
		String returnStr = null;
		try {
			ht.call(WebserviceUtils.NAMESPACE + "/" + MehtodName, envelope);
		} catch (Exception e) {
			e.printStackTrace();
			returnStr = "连接失败，请检查网络连接";
			return returnStr;
		}

		try {
			Object result = (Object) envelope.getResponse();
			returnStr = result.toString();
			String[] items = returnStr.split("#");
			List<ItemBean> lists = new ArrayList<ItemBean>();
			if ("anyType{}".equals(returnStr)) {
				return "";
			}
			for (int i = 0; i < items.length; i++) {
				String[] item = items[i].split("-");
				ItemBean itemBean = jsonBinder
						.fromJson(item[0], ItemBean.class);
				TypeBean typeBean = jsonBinder
						.fromJson(item[1], TypeBean.class);
				List<PicBean> pics = jsonBinder.stringToList(item[2],
						PicBean.class);
				List<PicBean> sets = new ArrayList<PicBean>(pics);
				itemBean.setPicBeans(sets);
				itemBean.setType(typeBean);
				lists.add(itemBean);
			}
			fatherMain.itemBeans = lists;
			Log.i("cxc", lists.size() + "");
		} catch (SoapFault e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnStr;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		fatherMain.endWaitDialog(true);
		fatherMain.picAdapter();

	}
}
