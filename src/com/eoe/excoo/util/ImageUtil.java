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
	 * ����ͼƬ������
	 * */
	@SuppressLint("SdCardPath")
	public void savePic(Bitmap bitmap, String picName) {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
			return;
		}
		FileOutputStream fileOutputStream = null;
		File file = new File("/sdcard/yiku/");
		file.mkdirs();// �����ļ���
		String fileName = "/sdcard/yiku/" + picName;
		try {
			fileOutputStream = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);// ������д���ļ�
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
	 * �ж��ļ��Ƿ����
	 * */
	public boolean isExists(String picName) {
		boolean boo = false;
		File file = new File("/sdcard/yiku/" + picName);
		boo = file.exists();
		return boo;
	}

	/**
	 * ��ñ���ͼƬ
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
	 * ɾ������ͼƬ
	 * */
	public void delLocalBitmap(String picName) {
		File file = new File("/sdcard/yiku/" + picName); // ����Ҫɾ�����ļ�λ��
		if (file.exists())
			file.delete();
	}

	/**
	 * ɾ���ļ����µ������ļ�
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
