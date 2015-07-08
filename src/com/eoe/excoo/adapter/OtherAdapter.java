package com.eoe.excoo.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eoe.excoo.R;
import com.eoe.excoo.bean.TypeBean;

public class OtherAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	@SuppressWarnings("unused")
	private Context context;
	private List<TypeBean> itemList;
	private TextView other_item;

	public OtherAdapter(List<TypeBean> itemList, Context context) {
		this.layoutInflater = LayoutInflater.from(context);
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

	@SuppressLint({ "InflateParams", "ViewHolder" })
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = layoutInflater.inflate(R.layout.other_activity_listitem,
				null);// 这个过程相当耗时间
		other_item = (TextView) convertView.findViewById(R.id.other_item);
		other_item.setText(itemList.get(position).getName());
		return convertView;
	}
}
