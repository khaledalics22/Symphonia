package com.example.symphonia.Entities;

import android.graphics.Bitmap;

import org.json.JSONArray;

/**
 * @author Mahmoud Amr Nabil
 * @version 1.0
 */
public class Container {
    private String catName;
    private String catName2;
    private Bitmap imgRes;
    private String imgUrl;
    private String id;

    public Container(String s, Bitmap i) {
        catName = s;
        imgRes = i;
        catName2 = null;
    }

    public Container(String s, String i) {
        catName = s;
        imgUrl = i;
        catName2 = null;
    }

    public Container(String s, String s2, Bitmap i) {
        catName = s;
        imgRes = i;
        catName2 = s2;
    }

    public Container(String s, String url, String f) {
        catName = s;
        imgUrl=url;
        catName2=f;
    }

    public Container(String s, String url, String f,String i) {
        catName = s;
        imgUrl=url;
        catName2=f;
        id=i;
    }

    public void setCatName(String s){
        catName=s;
    }

    public Container(String s) {
        catName = s;
    }

    public String getCat_Name2() {
        return catName2;
    }

    public String getCat_Name() {
        return catName;
    }

    public Bitmap getImg_Res() {
        return imgRes;
    }

    public void setImgBitmap(Bitmap bitmap){
        imgRes=bitmap;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getId(){
        return id;
    }
}
