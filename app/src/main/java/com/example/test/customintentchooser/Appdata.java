package com.example.test.customintentchooser;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by test on 08-07-2016.
 */
public class Appdata implements Serializable {
    public String appName;
    public Drawable appIcon;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "Appdata{" +
                "appName='" + appName + '\'' +
                ", appIcon=" + appIcon +

                '}';
    }

    public Appdata(String name, Drawable drawable) {
        this.appName = name;
        this.appIcon = drawable;
    }
}
