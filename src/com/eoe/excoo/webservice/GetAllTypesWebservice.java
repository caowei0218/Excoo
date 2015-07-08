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

import com.eoe.excoo.activity.ShowActivity;
import com.eoe.excoo.bean.ItemBean;
import com.eoe.excoo.bean.TypeBean;
import com.eoe.excoo.util.Common;
import com.eoe.excoo.util.JsonBinder;

/**
 * 获得物品
 * */
public class GetAllTypesWebservice extends AsyncTask<String, Integer, String> {
	private JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
	// 调用的方法名
	public final String MehtodName = "getTypeList";
	List<ItemBean> lists=new ArrayList<ItemBean>();
	// private ArticleBean articleBean;
	private HttpTransportSE ht = null;
	private SoapSerializationEnvelope envelope;
	private SoapObject send = null;
	private ShowActivity showActivity = null;

	public GetAllTypesWebservice(ShowActivity showActivity) {
		this.showActivity = showActivity;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		ht = new HttpTransportSE(WebserviceUtils.HTTPTRANSPORTSE);
		ht.debug = false;
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		send = new SoapObject(WebserviceUtils.NAMESPACE, MehtodName);
		send.addProperty("uid", Common.userCommon.getUser_id());//
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
			if (returnStr.equals("anyType{}")||returnStr.equals("null")) {
				ItemBean Item = new ItemBean();
				showActivity.itemBeans.add(Item);
				TypeBean type = new TypeBean();
				showActivity.typeBeans.add(type);
				return returnStr;
			} else {
				List<TypeBean> types = jsonBinder.stringToList(returnStr, TypeBean.class);
				 for ( int i = 0 ; i < types.size() - 1 ; i ++ ) {
				     for ( int j = types.size() - 1 ; j > i; j -- ) {
				       if (types.get(j).getName().trim().equals(types.get(i).getName().trim())) {
				    	   types.remove(j);
				       }
				      }
				    }
				Common.listTypeBeanCommon=types;
			}
		} catch (SoapFault e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnStr;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}
}
