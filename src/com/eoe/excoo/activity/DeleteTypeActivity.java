package com.eoe.excoo.activity;

import java.util.ArrayList;
import java.util.List;

import com.eoe.excoo.R;
import com.eoe.excoo.bean.TypeBean;
import com.eoe.excoo.util.Common;
import com.eoe.excoo.webservice.DeletetypeWebservice;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DeleteTypeActivity extends BaseActivity {

	private ListView lv;
	private List<String> typeList=new ArrayList<String>();
	List<TypeBean> listTypeBeanCommon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_type);
		lv=(ListView) findViewById(R.id.lv_deleteType);
		initView();
	}
	int i=0;
	public void initData(){
		listTypeBeanCommon =Common.listTypeBeanCommon;
		for (TypeBean typeBean : listTypeBeanCommon) {
			typeList.add(typeBean.getName());
		}
		
	}
	private void initView() {
		
		initData();
			lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,typeList));	    
			lv.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					i=position;
					AlertDialog.Builder builder = new Builder(DeleteTypeActivity.this);
					builder.setTitle("删除类型，同时删除对应物品");
					builder.setNegativeButton("取消", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					});
					builder.setPositiveButton("确定", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (listTypeBeanCommon.size()<=1) {
								showToast("至少保留一个类型");
								return;
							}
							DeletetypeWebservice delete=new DeletetypeWebservice(DeleteTypeActivity.this,listTypeBeanCommon.get(i).getType_id());
							delete.execute();
						}
					});
					builder.create();
					builder.show();
					return true;
				}
			});
		
		
		
		
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		initData();
	}

	
}
