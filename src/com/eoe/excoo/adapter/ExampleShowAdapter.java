package com.eoe.excoo.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eoe.excoo.R;
import com.eoe.excoo.bean.ItemBean;
import com.eoe.excoo.util.ImageUtil;

public class ExampleShowAdapter extends BaseAdapter {
	private Context context;
	private List<ItemBean> itemList;
	public static Bitmap[] images = new Bitmap[10];
	ImageUtil imageUtil = new ImageUtil();

	public ExampleShowAdapter(List<ItemBean> itemList, Context context) {
		this.context = context;
		this.itemList = itemList;
	}

	public int getCount() {
		return itemList.size();
	}

	public Object getItem(int position) {
		return itemList.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.show_activity_listitem, null);
			viewHolder = new ViewHolder();
			viewHolder.show_image = (ImageView) convertView
					.findViewById(R.id.show_image);
			viewHolder.show_name = (TextView) convertView
					.findViewById(R.id.show_name);
			viewHolder.show_collection = (TextView) convertView
					.findViewById(R.id.show_collection);
			viewHolder.show_loc = (TextView) convertView
					.findViewById(R.id.show_loc);
			viewHolder.show_price = (TextView) convertView
					.findViewById(R.id.show_price);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ApplicationInfo appInfo = context.getApplicationInfo();
		viewHolder.show_image.setImageResource(context.getResources()
				.getIdentifier("guide_01", "drawable", appInfo.packageName));
		viewHolder.show_name.setText("��Ʒ����");
		viewHolder.show_collection.setText("���ڣ�" + "��");
		viewHolder.show_loc.setText("λ�ã�" + "�����");
		viewHolder.show_price.setText("��ע��" + "����");

		return convertView;
	}

	class ViewHolder {
		ImageView show_image;
		TextView show_name, show_collection, show_loc, show_price;
	}
}
