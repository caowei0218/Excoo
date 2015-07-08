package com.eoe.excoo.activity;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eoe.excoo.R;
import com.eoe.excoo.util.AppInfo;
import com.eoe.excoo.util.Common;
import com.eoe.excoo.util.ImageUtil;
import com.eoe.excoo.util.UpdateManager;
import com.eoe.excoo.webservice.GetNewVersionWebservice;

@SuppressLint("SdCardPath")
public class MyActivity extends BaseActivity {

	private DisplayMetrics displayMetrics;
	private int window_width;
	private int window_height;
	private LayoutParams para;
	private RelativeLayout my_rl_tittle;
	private Button btn_exit;
	private ImageButton ib_return;
	private RelativeLayout rl_user;
	private TextView my_tv_update, empty_cache_tv_setting, my_tv_setting,
			tell_a_friend_tv_setting;

	ImageUtil imageUtil = new ImageUtil();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.my_activity);
		initView();
		adaptation();
		addListener();
	}

	private void addListener() {
		ib_return.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MyActivity.this.finish();
			}
		});

		my_tv_setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyActivity.this,
						DeleteTypeActivity.class);
				startActivity(intent);
			}
		});

		btn_exit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SharedPreferences prefereces = MyActivity.this
						.getSharedPreferences("user", Context.MODE_PRIVATE);
				prefereces.edit().clear().commit();
				startActivity(new Intent(MyActivity.this, LoginActivity.class));
				MyActivity.this.finish();
			}
		});

		rl_user.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MyActivity.this, UserActivity.class));
			}
		});

		my_tv_update.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 检查版本更新
				GetNewVersionWebservice getVersion = new GetNewVersionWebservice(
						MyActivity.this, MyActivity.this, AppInfo
								.getAppVersionName(MyActivity.this));
				getVersion.execute();
			}
		});

		tell_a_friend_tv_setting.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("audio/*");
				intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
				intent.putExtra(Intent.EXTRA_TEXT,
						"Excoo衣酷http://117.78.20.206:8080/Excoo.apk");
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(Intent.createChooser(intent, "推荐给朋友"));
			}
		});

		empty_cache_tv_setting.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				imageUtil.delLocalAllBitmap(new File("/sdcard/yiku"));
				Toast.makeText(MyActivity.this, "清空缓存成功", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	// 检查更新版本
	public void refresh(String newVersion) {
		if (!AppInfo.getAppVersionName(MyActivity.this).equals(newVersion)) {
			UpdateManager updateManager = new UpdateManager(MyActivity.this,
					Common.URL);
			updateManager.checkUpdateInfo();

		} else {
			showToast("已经是最新版本！");
		}
	}

	private void initView() {
		btn_exit = (Button) findViewById(R.id.my_btn_exit);
		ib_return = (ImageButton) findViewById(R.id.my_ib_return);
		rl_user = (RelativeLayout) findViewById(R.id.my_rl_user);
		my_tv_update = (TextView) findViewById(R.id.my_tv_update);
		empty_cache_tv_setting = (TextView) findViewById(R.id.empty_cache_tv_setting);
		my_tv_setting = (TextView) findViewById(R.id.my_tv_setting);
		tell_a_friend_tv_setting = (TextView) findViewById(R.id.tell_a_friend_tv_setting);
		my_rl_tittle = (RelativeLayout) findViewById(R.id.my_rl_tittle);
	}

	/**
	 * 屏幕比例适配
	 * */
	private void adaptation() {
		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		window_height = displayMetrics.heightPixels;
		window_width = displayMetrics.widthPixels;

		para = btn_exit.getLayoutParams();
		para.width = (int) (window_width * (442.0 / 750));
		btn_exit.setLayoutParams(para);

		para = my_rl_tittle.getLayoutParams();
		para.height = (int) (window_height * (88.0 / 1334));
		para.width = (int) window_width;
		my_rl_tittle.setLayoutParams(para);

	}
}
