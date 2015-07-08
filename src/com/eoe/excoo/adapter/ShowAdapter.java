package com.eoe.excoo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eoe.excoo.R;
import com.eoe.excoo.bean.ItemBean;
import com.eoe.excoo.bean.PicBean;
import com.eoe.excoo.util.ImageUtil;
import com.eoe.excoo.webservice.GetPicWebservice;

public class ShowAdapter extends BaseAdapter {
	private Context context;
	private List<ItemBean> itemList;
	public static Bitmap[] images = new Bitmap[10];
	ImageUtil imageUtil = new ImageUtil();

	public ShowAdapter(List<ItemBean> itemList, Context context) {
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

	@SuppressLint({ "InflateParams", "NewApi" })
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

		if (imageUtil.isExists(getPicName(position))) {
			viewHolder.show_image.setImageBitmap(imageUtil
					.getLocalBitmap(getPicName(position)));
		} else {// 本地没有就设置为默认图片，并且从网络异步下载
			getPic(position, viewHolder.show_image);
		}
		
		viewHolder.show_name.setText(itemList.get(position).getName());
		
		if (itemList.get(position).getSeason() == null
				|| "".equals(itemList.get(position).getSeason())) {
			viewHolder.show_collection.setText("");
		} else {
			viewHolder.show_collection.setText("季节："
					+ itemList.get(position).getSeason());
		}
		if (itemList.get(position).getLoc() == null
				|| "".equals(itemList.get(position).getLoc())) {
			viewHolder.show_loc.setText("");
		} else {
			viewHolder.show_loc
					.setText("位置：" + itemList.get(position).getLoc());
		}
		if (itemList.get(position).getRemarks() == null
				|| "".equals(itemList.get(position).getRemarks())) {
			viewHolder.show_price.setText("");
		} else {
			viewHolder.show_price.setText("备注："
					+ itemList.get(position).getRemarks());
		}
		// viewHolder.show_collection
		// .setText(itemList.get(position).getSeason() == null ? ""
		// : "季节：" + itemList.get(position).getSeason());
		// viewHolder.show_loc
		// .setText(itemList.get(position).getLoc() == null ? "" : "位置："
		// + itemList.get(position).getLoc());
		// viewHolder.show_price
		// .setText(itemList.get(position).getRemarks() == null ? ""
		// : "备注：" + itemList.get(position).getRemarks());
		return convertView;
	}

	/**
	 * 获取单张图片的接口
	 */
	public void getPic(int i, ImageView show_image) {
		List<PicBean> picBeans = itemList.get(i).getPicBeans();
		List<PicBean> pics = new ArrayList<PicBean>(picBeans);
		GetPicWebservice addPics = new GetPicWebservice(i, show_image, pics
				.get(0).getPic_id());
		addPics.execute();
	}

	/**
	 * 获得图片名称
	 * */
	private String getPicName(int position) {
		String picName = "";
		List<PicBean> picBeans = itemList.get(position).getPicBeans();
		List<PicBean> pics = new ArrayList<PicBean>(picBeans);
		if (pics.size() == 0) {

		} else {
			picName = pics.get(0).getPic_id();
		}

		return picName;
	}

	class ViewHolder {
		ImageView show_image;
		TextView show_name, show_collection, show_loc, show_price;
	}
}
