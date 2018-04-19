/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.realtek.portal;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

public class SonyApp implements Serializable {

    public static final int CATEGORY_TOP_FEATURED = 0x01;
    public static final int CATEGORY_MORE_APPS = 0x02;
    public static final int CATEGORY_PROMOTION = 0x03;

    public static final int[] CATEGORY_CHINA_TOP_FEATURED = {
            R.drawable.danbei,
            R.drawable.wasu,
            R.drawable.youku
    };

    public static final int[] CATEGORY_CHINA_TOP_FEATURED_TITLE = {
            R.string.danbei,
            R.string.wasu,
            R.string.youku
    };

    public static final int[] CATEGORY_OTHERS_TOP_FEATURED = {
            R.drawable.google,
            R.drawable.google_movies,
            R.drawable.google_music,
            R.drawable.google_photo,
            R.drawable.google_play,
    };

    public static final int[] CATEGORY_OTHERS_TOP_FEATURED_TITLE = {
            R.string.google,
            R.string.google_movies,
            R.string.google_music,
            R.string.google_photo,
            R.string.google_play,
    };

    public static final int[] CATEGORY_OTHERS_MORE = {
            R.drawable.wasu,
            R.drawable.netflix,
            R.drawable.youtube
    };

    public static final int[] CATEGORY_OTHERS_MORE_TITLE = {
            R.string.wasu,
            R.string.netflix,
            R.string.youtube
    };

    public static final int[] CATEGORY_OTHERS_PROMOTION = {
            R.drawable.facebook
    };

    public static final int[] CATEGORY_OTHERS_PROMOTION_TITLE = {
            R.string.facebook
    };

    // app title
    private String mTitle;
    // app icon
    private Drawable mIcon;
    // app package name
    private String mPackageName;
    // app package name
    private String mClassName;
    // app version
    private String mVersion;
    // app category
    private int mCategory;
    // app url only for BIVL
    private String mBivlUrl;

    public SonyApp() {
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setIcon(Drawable icon) {
        this.mIcon = icon;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setPackageName(String packageName){
        this.mPackageName = packageName;
    }

    public String getPackageName(){
        return mPackageName;
    }

    public void setClassName(String className){
        this.mClassName = className;
    }

    public String getClassName(){
        return mClassName;
    }

    public void setVersion(String version){
        this.mVersion = version;
    }

    public String getVersion(){
        return mVersion;
    }

    public void setCategory(int category){
        this.mCategory = category;
    }

    public int getCategory(){
        return mCategory;
    }

    public void setBivlUrl(String bivlUrl) {
        this.mBivlUrl = bivlUrl;
    }

    public URI getBivlUri() {
        try {
            return new URI(mBivlUrl);
        } catch (URISyntaxException e) {
            Log.d("URI exception: ", mBivlUrl);
            return null;
        }
    }

    @Override
    public String toString() {
        return "SonyApp{" +
                "title=" + mTitle +
                ", packageName=" + mPackageName +
                ", className=" + mClassName +
                ", category=" + mCategory +
                ", version=" + mVersion +
                ", mBivlUrl=" + mBivlUrl +
                "}";
    }
}
