package com.eoe.excoo.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eoe.excoo.R;
import com.eoe.excoo.bean.ItemBean;
import com.eoe.excoo.bean.PicBean;
import com.eoe.excoo.bean.TypeBean;
import com.eoe.excoo.util.Base64Code;
import com.eoe.excoo.util.Common;
import com.eoe.excoo.webservice.AddItemWebservice;

@SuppressLint("InflateParams")
public class AddArticleActivity extends BaseActivity {
	private DisplayMetrics displayMetrics;
	@SuppressWarnings("unused")
	private int window_width, window_height;
	private LayoutParams para;
	private RelativeLayout rl_tittle, rl_picture, season, rl_type;
	private TextView tv_season, tv_type;
	private ImageButton ib_camera, add_btn_return;
	private Button btn_save;
	private ImageView iv;
	private EditText add_et_name, add_et_position, add_et_mark;
	private ArrayList<String> type = new ArrayList<String>();
	private List<TypeBean> typeList;
	private ItemBean item = null;
	private String typeID;
	final int TAKE_PICTURE = 1; // Ϊ�˱�ʾ���ط����б�ʶ��ĳ���򿪵����
	final int TAKE_PICTURE_ORIGINAL = 2; // Ϊ�˱�ʾ���ط����б�ʶ��ĳ���򿪵����

	private File photoFile;
	public static final String IMAGE_UNSPECIFIED = "image/*";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_activity);
		initView();
		intiParas();
		initPopData();// ���õ�����������
		addListener();
	}

	public void showMsg(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	public void closeActivity() {
		this.setResult(1);
		this.finish();
	}

	private void addListener() {
		// ����
		btn_save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getItem();
				if (tv_type.getText().toString().equals("")) {
					showToast("���Ͳ���Ϊ��");
					return;
				} else if (iv.getDrawable() == null) {
					return;
				}
				AddItemWebservice add = new AddItemWebservice(
						AddArticleActivity.this, AddArticleActivity.this, item,
						true);
				add.execute();
			}
		});

		// ���������
		ib_camera.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					final String[] options = new String[] { "����", "������ȡ" };
					new AlertDialog.Builder(AddArticleActivity.this)
							.setTitle("��ѡ��ͼƬ��ȡ��ʽ")
							.setItems(options,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											if (options[which].equals("����")) {
												takePhoto();
											} else if (options[which]
													.equals("������ȡ")) {
												Intent intent = new Intent(
														Intent.ACTION_GET_CONTENT,
														null);
												intent.setDataAndType(
														MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
														IMAGE_UNSPECIFIED);
												startActivityForResult(intent,
														1);
											}
										}
									}).show();

				} catch (Exception e) {
					Log.e("Exception", e.getMessage());
				}
			}
		});

		// ����
		add_btn_return.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AddArticleActivity.this.finish();
			}
		});

		// ��������
		rl_type.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int size = type.size();
				String[] array = (String[]) type.toArray(new String[size]);
				setType(array);
			}
		});

		// ���ü���
		season.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setSeason();
			}
		});

		// ��Ƭ����ɾ��
		iv.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				AlertDialog create = new AlertDialog.Builder(
						AddArticleActivity.this)

						.setTitle("ɾ��")

						.setMessage("ȷ��ɾ��ͼƬ��")

						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										iv.setImageDrawable(null);
										iv.refreshDrawableState();
										ib_camera.setVisibility(View.VISIBLE);
									}
								})
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).create();
				create.show();
				return true;
			}
		});
	}

	private void intiParas() {
		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		window_height = displayMetrics.heightPixels;
		window_width = displayMetrics.widthPixels;

		para = rl_tittle.getLayoutParams();
		para.height = (int) (window_height * (88.0 / 1334));
		rl_tittle.setLayoutParams(para);

		para = rl_picture.getLayoutParams();
		para.height = (int) (window_height * (604.0 / 1334));
	}

	private void initView() {
		ib_camera = (ImageButton) findViewById(R.id.add_ib_camera);
		add_btn_return = (ImageButton) findViewById(R.id.add_btn_return);
		btn_save = (Button) findViewById(R.id.add_btn_save);
		iv = (ImageView) findViewById(R.id.add_iv);
		tv_season = (TextView) findViewById(R.id.add_tv_showseason);
		tv_type = (TextView) findViewById(R.id.add_tv_showtype);
		rl_tittle = (RelativeLayout) findViewById(R.id.add_rl_tittle);
		rl_picture = (RelativeLayout) findViewById(R.id.add_rl_picture);
		season = (RelativeLayout) findViewById(R.id.season);
		rl_type = (RelativeLayout) findViewById(R.id.rl_type);
		add_et_name = (EditText) findViewById(R.id.add_et_name);
		add_et_position = (EditText) findViewById(R.id.add_et_position);
		add_et_mark = (EditText) findViewById(R.id.add_et_mark);

	}

	public void getItem() {
		item = new ItemBean();
		item.setUser_id(Common.userCommon.getUser_id());
		item.setName(add_et_name.getText().toString());
		item.setSeason(tv_season.getText().toString());
		item.setLoc(add_et_position.getText().toString());
		item.setRemarks(add_et_mark.getText().toString());
		TypeBean type = new TypeBean();
		type.setType_id(typeID);
		type.setName(tv_type.getText().toString());
		type.setUser_id(Common.userCommon.getUser_id());
		item.setType(type);

		PicBean pic = new PicBean();
		if (iv.getDrawable() == null) {
			showToast("�����ͼƬŶ����");
			return;
		}
		pic.setImageStrem(Base64Code.Image2String(iv));
		List<PicBean> sets = new ArrayList<PicBean>();
		sets.add(pic);
		item.setPicBeans(sets);
	}

	public String getIDByName(String name) {
		String TID = null;
		for (TypeBean ttype : typeList) {
			if (name.equals(ttype.getName())) {
				TID = ttype.getType_id();
			}
		}
		return TID;
	}

	/**
	 * ���õ�����������
	 **/
	private void initPopData() {
		typeList = Common.listTypeBeanCommon;
		for (TypeBean typeL : typeList) {
			type.add(typeL.getName());
		}
		for (int i = 0; i < type.size(); i++) {
			if (type.get(i).equals("��ñ")) {
				type.remove(i);
			}
		}
		for (int i = 0; i < type.size(); i++) {
			if (type.get(i).equals("Ь��")) {
				type.remove(i);
			}
		}
		type.add("��ñ");
		type.add("Ь��");
		type.add("���������");
	}

	/**
	 * ��������adapter
	 **/
	public class PopupWindowAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater layoutInflater;
		private ArrayList<String> items;

		public PopupWindowAdapter(Context context, ArrayList<String> items) {
			this.context = context;
			this.items = items;
		}

		public int getCount() {
			return items.size();
		}

		public Object getItem(int position) {
			return items.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				layoutInflater = LayoutInflater.from(context);
				convertView = layoutInflater.inflate(R.layout.addpopupwindow,
						null);
				viewHolder = new ViewHolder();
				viewHolder.textView = (TextView) convertView
						.findViewById(R.id.add_tv_pop);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.textView.setText(items.get(position));
			return convertView;
		}

		class ViewHolder {
			TextView textView;
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == TAKE_PICTURE_ORIGINAL) { // ��ȡԭͼ
			startPhotoZoom(Uri.fromFile(photoFile));
		}

		if (requestCode == 1) {
			if (data != null) {
				startPhotoZoom(data.getData());
			}
		}

		if (requestCode == 3) {
			if (data != null) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap photo = extras.getParcelable("data");
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);

					// ��ʾͼƬ
					iv.setImageBitmap(photo);
					ib_camera.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

	/** �Ӹ���·������ͼƬ */
	@SuppressWarnings("deprecation")
	public Bitmap loadBitmap(String imgpath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		@SuppressWarnings("unused")
		Bitmap bm = null;

		// �����ڴ�ʹ��������Ч��ֹOOM
		{
			options.inJustDecodeBounds = true;
			bm = BitmapFactory.decodeFile(imgpath, options);

			// ��Ļ��
			int Wight = getWindowManager().getDefaultDisplay().getWidth();

			// ���ű�
			int ratio = options.outWidth / Wight;
			if (ratio <= 0)
				ratio = 1;
			options.inSampleSize = ratio;
			options.inJustDecodeBounds = false;
		}

		// ����ͼƬ,������
		return BitmapFactory.decodeFile(imgpath, options);
	}

	/** �Ӹ�����·������ͼƬ����ָ���Ƿ��Զ���ת���� */
	public Bitmap loadBitmap(String imgpath, boolean adjustOritation) {
		if (!adjustOritation) {
			return loadBitmap(imgpath);
		} else {
			Bitmap bm = loadBitmap(imgpath);
			int digree = 0;
			ExifInterface exif = null;
			try {
				exif = new ExifInterface(imgpath);
			} catch (IOException e) {
				e.printStackTrace();
				exif = null;
			}
			if (exif != null) {
				// ��ȡͼƬ�����������Ϣ
				int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_UNDEFINED);
				// ������ת�Ƕ�
				switch (ori) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					digree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					digree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					digree = 270;
					break;
				default:
					digree = 0;
					break;
				}
			}
			if (digree != 0) {
				// ��תͼƬ
				Matrix m = new Matrix();
				m.postRotate(digree);
				bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
						bm.getHeight(), m, true);
			}
			return bm;
		}
	}

	/** ͼƬ�ü� */
	public void startPhotoZoom(Uri uri) {
		// �ü�ͼƬ��ͼ
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// �ü���ı�����1��1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// �ü������ͼƬ�ĳߴ��С
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);

		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	@SuppressLint("SimpleDateFormat")
	private void takePhoto() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		simpleDateFormat.format(new Date());
		// �ж��Ƿ����ڴ濨
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			photoFile = new File(Environment.getExternalStorageDirectory()// ��ȡSD����Ŀ¼
					+ "/DCIM/Camera/"
					+ simpleDateFormat.format(new Date())
					+ ".jpg");// ͼƬ����·��
		} else {
			photoFile = new File(Environment.getRootDirectory()// ��ȡ�ֻ���Ŀ¼
					+ "/DCIM/Camera/"
					+ simpleDateFormat.format(new Date())
					+ ".jpg");// ͼƬ����·��
		}
		if (!photoFile.getParentFile().exists()) {
			photoFile.getParentFile().mkdirs();
		}

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
		startActivityForResult(intent, TAKE_PICTURE_ORIGINAL);
	}

	/**
	 * ���ü���
	 * */
	private void setSeason() {
		final String[] options = new String[] { "��", "��", "��", "��" };
		new AlertDialog.Builder(AddArticleActivity.this).setTitle("��ѡ�񼾽�")
				.setItems(options, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						tv_season.setText(options[which]);
					}
				}).show();
	}

	/**
	 * ��������
	 * */
	private void setType(final String[] option) {
		new AlertDialog.Builder(AddArticleActivity.this).setTitle("��ѡ������")
				.setItems(option, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String type = option[which];
						if ("���������".equals(type)) {
							final EditText dialog_et = new EditText(
									AddArticleActivity.this);

							DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									String add_type = dialog_et.getText()
											.toString();
									for (TypeBean ttype : typeList) {
										if (add_type.equals(ttype.getName())) {
											showToast("�������Ѵ���");
											return;
										}
									}
									if ("���������".equals(add_type)) {
										showToast("���������Ʋ��ܱ�ʹ��");
										return;
									}
									tv_type.setText(add_type);
									typeID = getIDByName(add_type);
								}
							};

							new AlertDialog.Builder(AddArticleActivity.this)
									.setTitle("������")
									.setIcon(android.R.drawable.ic_dialog_info)
									.setView(dialog_et)
									.setPositiveButton("ȷ��", listener)
									.setNegativeButton("ȡ��", null).show();
						} else {
							tv_type.setText(option[which]);
							typeID = getIDByName(option[which]);
						}
					}
				}).show();
	}
}
