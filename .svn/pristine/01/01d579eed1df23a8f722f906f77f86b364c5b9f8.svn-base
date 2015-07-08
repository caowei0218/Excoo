package com.eoe.excoo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

@SuppressLint("SdCardPath")
public class ImageUtil {

	/**
	 * 保存图片到本地
	 * */
	@SuppressLint("SdCardPath")
	public void savePic(Bitmap bitmap, String picName) {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			return;
		}
		FileOutputStream fileOutputStream = null;
		File file = new File("/sdcard/yiku/");
		file.mkdirs();// 创建文件夹
		String fileName = "/sdcard/yiku/" + picName;
		try {
			fileOutputStream = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);// 把数据写入文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fileOutputStream.flush();
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 判断文件是否存在
	 * */
	public boolean isExists(String picName) {
		boolean boo = false;
		File file = new File("/sdcard/yiku/" + picName);
		boo = file.exists();
		return boo;
	}

	/**
	 * 获得本地图片
	 * */
	public Bitmap getLocalBitmap(String picName) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("/sdcard/yiku/" + picName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(fis);
		return bitmap;
	}

	/**
	 * 删除本地图片
	 * */
	public void delLocalBitmap(String picName) {
		File file = new File("/sdcard/yiku/" + picName); // 输入要删除的文件位置
		if (file.exists())
			file.delete();
	}

	/**
	 * 删除文件夹下的所有文件
	 */
	public void delLocalAllBitmap(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				delLocalAllBitmap(f);
			}
		} else {
			file.delete();
		}
	}
}
