package com.widevision.multipilco.util;

import android.widget.ImageView;

/**
 * Created by rocks on 01/08/16.
 */
public class NumberBean {

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public NumberBean(int image_id, String tag, int number) {
        this.image_id = image_id;
        this.tag = tag;
        this.number = number;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int image_id;
    public String tag;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int number;
}
