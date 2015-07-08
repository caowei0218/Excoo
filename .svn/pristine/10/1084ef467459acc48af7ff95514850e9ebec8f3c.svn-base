package com.eoe.excoo.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.widget.ImageView;

/**
 * @author 作�??:chenxingchun
 * @version 创建时间:2015�?6�?9日上�?8:25:48 类说�?:
 */
public class Base64Code {

	/**
	 * 从Image获取图片转成字符�?
	 * 
	 * @param iv
	 * @return
	 */
	public static String Image2String(ImageView iv) {
		String str = "";

		Drawable drawable = iv.getDrawable();
		Bitmap drawableToBitmap = drawableToBitmap(drawable);
		byte[] bitmap2Bytes = Bitmap2Bytes(drawableToBitmap);
		str = Base64.encodeToString(bitmap2Bytes, Base64.DEFAULT);

		return str;
	}

	public static String File2String(String fileName) {
		FileInputStream fInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader in = null;
		StringBuffer sBuffer = null;
		try {
			fInputStream = new FileInputStream(fileName);
			inputStreamReader = new InputStreamReader(fInputStream, "UTF-8");
			in = new BufferedReader(inputStreamReader);
			sBuffer = new StringBuffer();
			sBuffer.append(in.readLine());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fInputStream.close();
				in.close();
				inputStreamReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sBuffer.toString();
	}

	/**
	 * 把Bitmap转Byte
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		// �? drawable 的长�?
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		// �? drawable 的颜色格�?
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画�?
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// �? drawable 内容画到画布�?
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 字符串转换成Bitmap
	 * 
	 * @param pic
	 * @return
	 */
	public static Bitmap String2Bitmap(String pic) {
		byte[] bytes = Base64.decode(pic, Base64.DEFAULT);
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return bitmap;
	}
}
