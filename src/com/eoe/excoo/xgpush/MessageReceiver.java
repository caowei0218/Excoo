package com.eoe.excoo.xgpush;

import android.content.Context;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

public class MessageReceiver extends XGPushBaseReceiver {
	public static final String LogTag = "TPushReceiver";

	// 閫氱煡灞曠ず
	public void onNotifactionShowedResult(Context context,
			XGPushShowedResult notifiShowedRlt) {
		// Toast.makeText(context, "閫氱煡锛�" + notifiShowedRlt.getTitle(),
		// Toast.LENGTH_SHORT).show();
		// Toast.makeText(context, "閫氱煡锛�" + notifiShowedRlt.getContent(),
		// Toast.LENGTH_SHORT).show();
	}

	public void onDeleteTagResult(Context arg0, int arg1, String arg2) {

	}

	public void onNotifactionClickedResult(Context arg0,
			XGPushClickedResult arg1) {

	}

	public void onRegisterResult(Context arg0, int arg1,
			XGPushRegisterResult arg2) {

	}

	public void onSetTagResult(Context arg0, int arg1, String arg2) {

	}

	public void onTextMessage(Context arg0, XGPushTextMessage arg1) {

	}

	public void onUnregisterResult(Context arg0, int arg1) {

	}

}
