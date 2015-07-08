package com.eoe.excoo.bean;

import java.io.Serializable;

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
public class PicBean implements Serializable {
    private static final long serialVersionUID = -4697791866916407382L;
    private String pic_id;
    private ItemBean itemBean = new ItemBean();
    private String path;
    private String imageStrem;
    private String is_valid = "1";

    public PicBean() {
        super();
    }

    public static PicBean clone(PicBean old) {
        PicBean pic = new PicBean();
        pic.setPic_id(old.getPic_id());
        pic.setPath(old.getPath());
        pic.setIs_valid(old.getIs_valid());
        return pic;
    }

    public String getPic_id() {
        return pic_id;
    }

    public void setPic_id(String pic_id) {
        this.pic_id = pic_id;
    }

    public ItemBean getItemBean() {
        return itemBean;
    }

    public void setItemBean(ItemBean itemBean) {
        this.itemBean = itemBean;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImageStrem() {
        return imageStrem;
    }

    public void setImageStrem(String imageStrem) {
        this.imageStrem = imageStrem;
    }

    public String getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
    }

}
