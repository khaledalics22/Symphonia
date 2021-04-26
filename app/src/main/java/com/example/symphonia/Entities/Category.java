package com.example.symphonia.Entities;

import android.graphics.Bitmap;

public class Category extends Container {
    private String id;
    private String href;
    public Category(String s, Bitmap b) {
        super(s, b);
    }

    public Category(String link) {
        super(link);
    }

    public Category(String s, Bitmap b,String i,String h) {
        super(s, b);
        id=i;
        href=h;
    }

    public String getId() {
        return id;
    }

    public String getHref(){
        return href;
    }
}
