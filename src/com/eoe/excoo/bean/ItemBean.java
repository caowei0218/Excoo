/**
 * ItemBean.java
 *
 * 鍔熻兘锛�(绫荤殑鍚嶇О鎴栬�呯敤涓�鍙ヨ瘽鎻忚堪绫荤殑鍔熻兘)
 * 绫诲悕锛欼temBean
 * 
 * ver     鍙樻洿鏃�                  鍏徃              浣滆��                   鍙樻洿鍐呭
 * 鈥斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺�斺��
 * V1.0.0  2015骞�5鏈�28鏃�   浼樺紙鏁版嵁      Administrator   鍒濈増
 *
 * Copyright (c) 2014,2016 浼樺紙鏁版嵁鐗堟湰鎵�鏈�.
 * LICENSE INFORMATION
 */
package com.eoe.excoo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 锛堣繖閲屼竴鍙ヨ瘽鎻忚堪杩欐槸涓�涓粈涔堟牱鐨勭被锛屾牸寮忥細鏈被鏄疿XXX銆傦級
 * 
 * @author Administrator
 * @version Ver 1.0 2015骞�5鏈�28鏃� 鍒涘缓
 * @since CodingExample Ver 1.0
 *
 * @see YourClass, HisClass
 * @deprecated锛堣繖閲屽啓deprecated鐨勪俊鎭級
 */
public class ItemBean implements Serializable {
    private static final long serialVersionUID = 2021232546206515870L;
    private String item_id;
    private String user_id;
    private TypeBean type = new TypeBean();
    private String name;
    private String season;
    private String remarks;
    private String loc;
    private List<PicBean> picBeans = new ArrayList<PicBean>();
    private String is_valid = "1";
  

    public ItemBean() {
        super();
    }

    public static ItemBean clone(ItemBean old){
        ItemBean newItem=new ItemBean();
        newItem.setItem_id(old.getItem_id());
        newItem.setName(old.getName());
        newItem.setLoc(old.getLoc());
        newItem.setUser_id(old.getUser_id());
        newItem.setRemarks(old.getRemarks());
        newItem.setSeason(old.getSeason());
        newItem.setIs_valid(old.getIs_valid());
        newItem.setPicBeans(null);
        newItem.setType(null);
        return newItem;
    }
    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public TypeBean getType() {
        return type;
    }

    public void setType(TypeBean type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }


    public String getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
    }

	public List<PicBean> getPicBeans() {
		return picBeans;
	}

	public void setPicBeans(List<PicBean> picBeans) {
		this.picBeans = picBeans;
	}


}
