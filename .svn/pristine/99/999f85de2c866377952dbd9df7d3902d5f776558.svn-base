/**
 * TypeBean.java
 *
 * 鍔熻兘锛�(绫荤殑鍚嶇О鎴栬�呯敤涓�鍙ヨ瘽鎻忚堪绫荤殑鍔熻兘)
 * 绫诲悕锛歍ypeBean
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
public class TypeBean implements Serializable {
    private static final long serialVersionUID = 6834832911248950798L;
    private String type_id;
    private String user_id;
    private String name;
    private String is_valid = "1";
    private List<ItemBean> itemBeans = new ArrayList<ItemBean>();

    public TypeBean(){
        super();
    }
    
    public static TypeBean clone(TypeBean old) {
        TypeBean newTypeBean = new TypeBean();
        newTypeBean.setType_id(old.getType_id());
        newTypeBean.setUser_id(old.getUser_id());
        newTypeBean.setName(old.getName());
        newTypeBean.setIs_valid(old.getIs_valid());
        newTypeBean.setItemBeans(null);
        return newTypeBean;
    }
    
    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
    }

	public List<ItemBean> getItemBeans() {
		return itemBeans;
	}

	public void setItemBeans(List<ItemBean> itemBeans) {
		this.itemBeans = itemBeans;
	}


}
