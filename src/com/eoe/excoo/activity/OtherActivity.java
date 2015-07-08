package com.eoe.excoo.activity;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.eoe.excoo.R;
import com.eoe.excoo.adapter.OtherAdapter;
import com.eoe.excoo.bean.TypeBean;
import com.eoe.excoo.util.Common;

public class OtherActivity extends BaseActivity {

	private OtherAdapter otherAdapter;
	private ListView other_list;
	private List<TypeBean> items = Common.listTypeBeanCommon;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other_activity);

		other_list = (ListView) findViewById(R.id.other_list);

		otherAdapter = new OtherAdapter(items, OtherActivity.this);// 绑定数据
		other_list.setAdapter(otherAdapter);
		other_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("type", items.get(position).getName());
				setResult(1, intent);
				OtherActivity.this.finish();
			}
		});

		other_list.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				DeleteDialog(position);
				return false;
			}
		});
	}

	private void DeleteDialog(final int position) {
		AlertDialog.Builder builder = new Builder(OtherActivity.this);

		builder.setMessage("确定删除“" + items.get(position).getName()
				+ "”类别？该类别下的物品也将一并删除！！！");
		builder.setTitle("警告");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// 通知adapter 更新
				items.remove(position);
				otherAdapter = new OtherAdapter(items, OtherActivity.this);
				other_list.setAdapter(otherAdapter);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.create().show();
	}

}
