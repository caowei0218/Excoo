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
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eoe.excoo.R;
import com.eoe.excoo.bean.ItemBean;
import com.eoe.excoo.bean.PicBean;
import com.eoe.excoo.bean.TypeBean;
import com.eoe.excoo.util.Base64Code;
import com.eoe.excoo.util.Common;
import com.eoe.excoo.util.Constants;
import com.eoe.excoo.util.ImageUtil;
import com.eoe.excoo.util.WXShareUtil;
import com.eoe.excoo.webservice.AddPicWebservice;
import com.eoe.excoo.webservice.DeletePicWebservice;
import com.eoe.excoo.webservice.ModifyItemWebservice;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;

@SuppressLint({ "InflateParams", "SimpleDateFormat" })
public class DetailActivity extends BaseActivity {
	private DisplayMetrics displayMetrics;
	private int window_height;
	private LayoutParams para;
	private RelativeLayout tittle;
	private FrameLayout picture;
	private ImageButton ib_camera;
	private Button btnSave;
	private EditText detail_et_name, detail_et_seasonshow, detail_et_typeshow,
			detail_et_positionshow, detail_et_markshow;
	private ImageButton share, back;
	private ImageView[] imageViews;// ViewPaperС���
	private ImageView imageView;// ViewPaperС���
	public ViewPager viewPager;
	private ItemBean itemBean;
	public List<PicBean> picList;
	private ArrayList<View> imgList = new ArrayList<View>();
	private ArrayList<String> type = new ArrayList<String>();
	ImageUtil imageUtil = new ImageUtil();
	@SuppressWarnings("unused")
	private boolean imgListEmp = false;
	public PagerAdapter viewPaperAdapter;
	private String typeID = null;
	private List<TypeBean> typeList = new ArrayList<TypeBean>();
	private int position = 0;

	final int TAKE_PICTURE = 1; // Ϊ�˱�ʾ���ط����б�ʶ��ĳ���򿪵����
	final int TAKE_PICTURE_ORIGINAL = 2; // Ϊ�˱�ʾ���ط����б�ʶ��ĳ���򿪵����
	private File photoFile;

	public static final String IMAGE_UNSPECIFIED = "image/*";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_activity);
		initView();
		initData();
		intiParas();
		addListener();
		initViewPaper();
	}

	/**
	 * ��ʼ��ͼƬ�ߴ�
	 */
	private void intiParas() {
		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		window_height = displayMetrics.heightPixels;

		para = tittle.getLayoutParams();
		para.height = (int) (window_height * (88.0 / 1334));
		tittle.setLayoutParams(para);

		para = picture.getLayoutParams();
		para.height = (int) (window_height * (634.0 / 1334));
		picture.setLayoutParams(para);

	}

	/** ��ʼ����ͼ */
	private void initView() {
		ib_camera = (ImageButton) findViewById(R.id.detail_ib_camera);
		viewPager = (ViewPager) findViewById(R.id.detail_vp);
		btnSave = (Button) findViewById(R.id.detail_btn_save);
		tittle = (RelativeLayout) findViewById(R.id.detail_tittle);
		picture = (FrameLayout) findViewById(R.id.detail_fl_picture);
		detail_et_name = (EditText) findViewById(R.id.detail_et_name);
		detail_et_seasonshow = (EditText) findViewById(R.id.detail_et_seasonshow);
		detail_et_typeshow = (EditText) findViewById(R.id.detail_et_typeshow);
		detail_et_positionshow = (EditText) findViewById(R.id.detail_et_positionshow);
		detail_et_markshow = (EditText) findViewById(R.id.detail_et_markshow);
		share = (ImageButton) findViewById(R.id.share);
		back = (ImageButton) findViewById(R.id.back);
		share.getBackground().setAlpha(0);
		back.getBackground().setAlpha(0);
	}

	/** ��ʼ������ */
	private void initData() {
		itemBean = (ItemBean) getIntent().getExtras().get("itemBean");
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
		detail_et_name.setText(itemBean.getName());
		detail_et_seasonshow.setText(itemBean.getSeason());
		detail_et_typeshow.setText(itemBean.getType().getName());
		detail_et_positionshow.setText(itemBean.getLoc());
		detail_et_markshow.setText(itemBean.getRemarks());
	}

	/** �޸����� */
	private void modifyData() {
		itemBean.setName(detail_et_name.getText().toString());
		itemBean.setSeason(detail_et_seasonshow.getText().toString());
		TypeBean typeBean = new TypeBean();
		typeBean.setName(detail_et_typeshow.getText().toString());
		typeBean.setType_id(typeID);
		itemBean.setType(typeBean);
		itemBean.setLoc(detail_et_positionshow.getText().toString());
		itemBean.setRemarks(detail_et_markshow.getText().toString());
		itemBean.setPicBeans(null);
	}

	/** ��Ӽ��� */
	private void addListener() {

		ib_camera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					final String[] options = new String[] { "����", "������ȡ" };
					new AlertDialog.Builder(DetailActivity.this)
							.setTitle("��ѡ��ͼƬ��ȡ��ʽ")
							.setItems(new String[] { "����", "������ȡ" },
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

		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				modifyItem();
			}
		});

		share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final IWXAPI api = WXAPIFactory.createWXAPI(
						getApplicationContext(), Constants.APP_ID, true);
				api.registerApp(Constants.APP_ID);

				ImageUtil imageUtil = new ImageUtil();
				if (imageUtil.isExists(picList.get(position).getPic_id())) {
					Bitmap bmp = imageUtil.getLocalBitmap(picList.get(position)
							.getPic_id());
					WXImageObject imgObj = new WXImageObject(bmp);

					WXMediaMessage msg = new WXMediaMessage();
					msg.mediaObject = imgObj;

					Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150,
							true);
					bmp.recycle();
					msg.thumbData = WXShareUtil.bmpToByteArray(thumbBmp, true);
					// ��������ͼ

					final SendMessageToWX.Req req = new SendMessageToWX.Req();
					req.transaction = buildTransaction("img");
					req.message = msg;
					final String[] ways = new String[] { "���������", "��������Ȧ" };
					new AlertDialog.Builder(DetailActivity.this)
							.setTitle("��ѡ��ͼƬ��ȡ��ʽ")
							.setItems(ways,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											if (ways[which].equals("���������")) {
												//
												req.scene = SendMessageToWX.Req.WXSceneSession;
												Common.share = api.sendReq(req);
											} else if (ways[which]
													.equals("��������Ȧ")) {
												req.scene = SendMessageToWX.Req.WXSceneTimeline;
												Common.share = api.sendReq(req);
											}
										}

									}).show();
				}
			}
		});

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(1010);
				DetailActivity.this.finish();
			}
		});

		detail_et_typeshow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int size = type.size();
				String[] array = (String[]) type.toArray(new String[size]);
				setType(array);
			}
		});

		detail_et_seasonshow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setSeason();
			}
		});
	}

	public void closeDetail() {
		setResult(0);
		DetailActivity.this.finish();
	}

	public void modifyItem() {
		modifyData();
		ModifyItemWebservice modify = new ModifyItemWebservice(
				DetailActivity.this, DetailActivity.this, itemBean);
		modify.execute();
	}

	/** ���ViewPaper���� */
	public void initViewPaper() {
		initImgList();

		if (imgList.size() > 1) {
			initPoint();
		}
		viewPaperAdapter = new viewPaperAdapter(imgList);
		viewPager.setAdapter(viewPaperAdapter);
		viewPager.setOnPageChangeListener(new GuidePageChangeListener());

	}

	public void reBuildPageViewer() {
		initImgList();

		if (imgList.size() > 1) {
			initPoint();
		}
	}

	/*** ��ʼ��ͼƬѡ��� **/
	public void initPoint() {
		LinearLayout ll_focus_indicator_container = (LinearLayout) findViewById(R.id.ll_focus_indicator_container);
		ll_focus_indicator_container.removeAllViews();
		imageViews = new ImageView[imgList.size()];
		for (int i = 0; i < imgList.size(); i++) {
			imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(5, 5, 5, 5);
			imageViews[i] = imageView;
			if (i == 0) {
				imageViews[i].setImageResource(R.drawable.detail_ic_focus);// ���λС��㣨����
			} else {
				imageViews[i]
						.setImageResource(R.drawable.detail_ic_focus_select);// ���λС��㣨����
			}
			ll_focus_indicator_container.addView(imageViews[i]);
		}
	}

	/** ��ʼ��ViewPaper����ʾ��ͼƬ��Դ */
	private void initImgList() {
		PicBean picBean;
		List<PicBean> picBeans = itemBean.getPicBeans();
		picList = new ArrayList<PicBean>(picBeans);
		for (int i = 0; i < picList.size(); i++) {
			ImageView imageView = new ImageView(this);
			picBean = picList.get(i);
			imageView.setImageBitmap(imageUtil.getLocalBitmap(picBean
					.getPic_id()));
			imgList.add(imageView);
		}
		if (imgList.size() == 0) {
			ImageView img1 = new ImageView(this);
			img1.setImageDrawable(getResources().getDrawable(
					R.drawable.detail_icon));
			img1.setScaleType(ScaleType.CENTER_INSIDE);
			imgList.add(img1);
			imgListEmp = true;
		}
	}

	/**
	 * ��������
	 * */
	private void setType(final String[] option) {
		new AlertDialog.Builder(DetailActivity.this).setTitle("��ѡ������")
				.setItems(option, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String type = option[which];
						if ("���������".equals(type)) {
							final EditText dialog_et = new EditText(
									DetailActivity.this);

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
									detail_et_typeshow.setText(add_type);
									typeID = getIDByName(add_type);
								}
							};

							new AlertDialog.Builder(DetailActivity.this)
									.setTitle("������")
									.setIcon(android.R.drawable.ic_dialog_info)
									.setView(dialog_et)
									.setPositiveButton("ȷ��", listener)
									.setNegativeButton("ȡ��", null).show();
						} else {
							detail_et_typeshow.setText(option[which]);
							typeID = getIDByName(option[which]);
						}
					}
				}).show();
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
	 * ���ü���
	 * */
	private void setSeason() {
		final String[] options = new String[] { "��", "��", "��", "��" };
		new AlertDialog.Builder(DetailActivity.this).setTitle("��ѡ�񼾽�")
				.setItems(options, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						detail_et_seasonshow.setText(options[which]);
					}
				}).show();
	}

	/**
	 * ��������adapter
	 **/
	public class PopupWindowAdapterForString extends BaseAdapter {
		private Context context;
		private LayoutInflater layoutInflater;
		private List<String> items;

		public PopupWindowAdapterForString(Context context, List<String> items) {
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
			return position;
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

	/**
	 * ��������adapter
	 **/
	public class PopupWindowAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater layoutInflater;
		private List<String> items;

		public PopupWindowAdapter(Context context, List<String> items) {
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
			return position;
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

	private final class viewPaperAdapter extends PagerAdapter {
		private ArrayList<View> imgList;

		public viewPaperAdapter(ArrayList<View> imgList) {
			this.imgList = imgList;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(imgList.get(arg1));
		}

		public void finishUpdate(View arg0) {
		}

		public int getCount() {
			return imgList.size();
		}

		public Object instantiateItem(View arg0, int arg1) {
			final View view = imgList.get(arg1);
			final ViewPager viewPager = ((ViewPager) arg0);
			viewPager.addView(view, 0);
			position = arg1;
			view.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					AlertDialog create = new AlertDialog.Builder(
							DetailActivity.this)

							.setTitle("ɾ��")

							.setMessage("ȷ��ɾ��ͼƬ��")

							.setPositiveButton("ȷ��",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											if (imgList.size() <= 1) {
												DetailActivity.this
														.showToast("���ٱ���һ��ͼƬ");
												return;
											}
											viewPager.removeView(view);
											imgList.remove(view);
											viewPaperAdapter
													.notifyDataSetChanged();
											viewPager.refreshDrawableState();
											PicBean picBean = picList
													.get(position);
											DeletePicWebservice deletePic = new DeletePicWebservice(
													DetailActivity.this,
													DetailActivity.this,
													picBean, position);
											deletePic.execute();
										}
									})
							.setNegativeButton("ȡ��",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									}).create();
					create.show();
					return true;
				}
			});
			return imgList.get(arg1);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate(View arg0) {
		}

	}

	private final class GuidePageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			for (int i = 0; i < imageViews.length; i++) {
				if (i == arg0) {
					imageViews[i].setImageResource(R.drawable.detail_ic_focus);
				} else {
					imageViews[i]
							.setImageResource(R.drawable.detail_ic_focus_select);
				}
			}
		}
	}

	/** �������ͼƬ */
	@Override
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

					// ѹ��ͼƬ֮���ϴ�����
					ImageView img = new ImageView(this);
					img.setImageBitmap(photo);
					if (photo.getWidth() < photo.getHeight()) {
						img.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.MATCH_PARENT));
					} else {
						img.setLayoutParams(new LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
					}

					// if (imgListEmp == true) {
					// imgList.set(0, img);
					// imgListEmp = false;
					// } else {
					// imgList.add(img);
					// }
					imgList.add(img);
					String image2String = Base64Code.Image2String(img);
					/**
					 * �������ͼƬ�ӿ�
					 */
					PicBean picBean = new PicBean();
					picBean.setImageStrem(image2String);
					picBean.setItemBean(itemBean);
					picList.add(picBean);
					AddPicWebservice addPic = new AddPicWebservice(
							DetailActivity.this, DetailActivity.this, picBean);
					addPic.execute();
					viewPaperAdapter.notifyDataSetChanged();
					initPoint();
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

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

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
	 * �������ؼ�
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(1010);
			DetailActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
