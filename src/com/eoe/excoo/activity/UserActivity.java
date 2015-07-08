package com.eoe.excoo.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eoe.excoo.R;
import com.eoe.excoo.bean.UserBean;
import com.eoe.excoo.util.Common;
import com.eoe.excoo.webservice.ModiftUserWebservice;

public class UserActivity extends BaseActivity {

	private LinearLayout user_ll_info;
	private DisplayMetrics displayMetrics;
	private int window_width;
	private int window_height;
	private LayoutParams para;
	private RelativeLayout user_rl_tittle;
	private ImageButton ib_back;
	private ImageView iv_icon;
	private ImageView iv_man;
	private ImageView iv_woman;
	private Button btn_save;
	private RadioButton rb_man;
	private RadioButton rb_woman;
	private RadioButton rb_ageYoung;
	private RadioButton rb_ageMiddle;
	private RadioButton rb_ageOld;
	private UserBean userBean;
	private TextView tv_man;
	private TextView tv_woman;
	private TextView tv_age1;
	private TextView tv_age2;
	private TextView tv_age3;
	private EditText etAccount;
	// private EditText etPassword;
	private EditText etEmail;
	private EditText etCellphone;
	private EditText etNickname;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_activity);
		initView();
		intiData();
		initparas();
		addListener();
	}

	/** 初始化用户数据 */
	private void intiData() {
		SharedPreferences sp = this.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		String email = sp.getString("email", "");
		String cellphone = sp.getString("cellphone", "");
		String nickName = sp.getString("nickName", "");
		String gender = sp.getString("gender", "");
		String age_group = sp.getString("age_group", "");
		etAccount.setText(userBean.getAccount());
		// etPassword.setText(userBean.getPassword());
		etEmail.setText(email);
		etCellphone.setText(cellphone);
		etNickname.setText(nickName);
		if (gender.equals("0")) {
			rb_man.setChecked(true);
		} else if (gender.equals("1")) {
			rb_woman.setChecked(true);
		}
		// Log.i("user", userBean.getAge_group());
		if (age_group.equals("0")) {
			rb_ageYoung.setChecked(true);
		} else if (age_group.equals("1")) {
			rb_ageMiddle.setChecked(true);
		} else if (age_group.equals("2")) {
			rb_ageOld.setChecked(true);
		}

	}

	/** 添加页面监听 */
	private void addListener() {
		ib_back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				UserActivity.this.finish();
			}
		});
		btn_save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				modifyUser();

				ModiftUserWebservice modify = new ModiftUserWebservice(
						UserActivity.this, userBean);
				modify.execute();
			}
		});
		iv_man.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rb_man.setChecked(true);
			}
		});
		tv_man.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rb_man.setChecked(true);
			}
		});
		iv_woman.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rb_woman.setChecked(true);
			}
		});
		tv_woman.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rb_woman.setChecked(true);
			}
		});
		tv_age1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rb_ageYoung.setChecked(true);
			}
		});
		tv_age2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rb_ageMiddle.setChecked(true);
			}
		});
		tv_age3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rb_ageOld.setChecked(true);
			}
		});
	}

	/**
	 * 修改用户
	 */
	private void modifyUser() {
		userBean.setEmail(etEmail.getText().toString());
		userBean.setCellphone(etCellphone.getText().toString());
		userBean.setNickname(etNickname.getText().toString());
		if (rb_man.isChecked()) {
			userBean.setGender("0");
		} else if (rb_woman.isChecked()) {
			userBean.setGender("1");
		}
		if (rb_ageYoung.isChecked()) {
			userBean.setAge_group("0");
		} else if (rb_ageMiddle.isChecked()) {
			userBean.setAge_group("1");
		} else if (rb_ageOld.isChecked()) {
			userBean.setAge_group("2");
		}

	}

	/** 根据手机大小适配 */
	private void initparas() {

		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		window_height = displayMetrics.heightPixels;
		window_width = displayMetrics.widthPixels;

		para = user_ll_info.getLayoutParams();
		para.height = (int) (window_height * (541.0 / 1334));
		para.width = (int) (window_width * (680.0 / 750));
		user_ll_info.setLayoutParams(para);

		para = user_rl_tittle.getLayoutParams();
		para.height = (int) (window_height * (88.0 / 1334));
		para.width = (int) window_width;
		user_rl_tittle.setLayoutParams(para);

		RelativeLayout.LayoutParams lp;
		lp = (RelativeLayout.LayoutParams) iv_icon.getLayoutParams();
		lp.width = (int) (window_width * (164.0 / 750));
		lp.height = (int) (window_width * (200.0 / 750));
		lp.topMargin = (int) (window_width * (80.0 / 750));
		lp.bottomMargin = (int) (window_width * (70.0 / 750));

	}

	private void initView() {
		userBean = Common.userCommon;
		user_ll_info = (LinearLayout) findViewById(R.id.user_ll_info);
		ib_back = (ImageButton) findViewById(R.id.user_ib_return);
		btn_save = (Button) findViewById(R.id.user_btn_save);
		etAccount = (EditText) findViewById(R.id.user_et_account);
		// etPassword = (EditText) findViewById(R.id.user_et_password);
		etEmail = (EditText) findViewById(R.id.user_et_email);
		etCellphone = (EditText) findViewById(R.id.user_et_cellphone);
		etNickname = (EditText) findViewById(R.id.user_et_nickname);
		rb_man = (RadioButton) findViewById(R.id.user_rb_man);
		rb_woman = (RadioButton) findViewById(R.id.user_rb_woman);
		rb_ageYoung = (RadioButton) findViewById(R.id.user_rb_age0);
		rb_ageMiddle = (RadioButton) findViewById(R.id.user_rb_age1);
		rb_ageOld = (RadioButton) findViewById(R.id.user_rb_age2);
		iv_icon = (ImageView) findViewById(R.id.user_iv_icon);
		iv_man = (ImageView) findViewById(R.id.user_iv_man);
		iv_woman = (ImageView) findViewById(R.id.user_iv_woman);
		tv_man = (TextView) findViewById(R.id.user_tv_man);
		tv_woman = (TextView) findViewById(R.id.user_tv_woman);
		tv_age1 = (TextView) findViewById(R.id.user_tv_age1);
		tv_age2 = (TextView) findViewById(R.id.user_tv_age2);
		tv_age3 = (TextView) findViewById(R.id.user_tv_age3);
		user_rl_tittle = (RelativeLayout) findViewById(R.id.user_rl_tittle);
	}
}
