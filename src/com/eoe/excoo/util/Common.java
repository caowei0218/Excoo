package com.eoe.excoo.util;

import java.util.ArrayList;
import java.util.List;

import com.eoe.excoo.bean.ItemBean;
import com.eoe.excoo.bean.TypeBean;
import com.eoe.excoo.bean.UserBean;

/**
 * @author 作者:chenxingchun
 * @version 创建时间:2015年6月9日上午11:11:27 类说明:
 */
public class Common {
	public static UserBean userCommon = new UserBean();
	public static List<ItemBean> listItemCommon = new ArrayList<ItemBean>();
	public static List<TypeBean> listTypeBeanCommon = new ArrayList<TypeBean>();
	public static String versionNumber;
	public static String URL;

	public static boolean share = false;
}
