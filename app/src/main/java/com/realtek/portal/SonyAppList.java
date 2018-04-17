package com.realtek.portal;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public final class SonyAppList {
    private static final int APP_CATEGORY[] = {
            R.string.top_featured,
            R.string.more_apps,
            R.string.promotion,
    };

    private static List<SonyApp> mSonyAppList;

    public static String getAppCategoryString(Context context, int index){
        return context.getString(APP_CATEGORY[index]);
    }

    public static List<SonyApp> getSonyAppList(Context context) {
        mSonyAppList = new ArrayList<>();

        mSonyAppList.add(buildSonyAppInfo(SonyApp.CATEGORY_TOP_FEATURED, "app1",
                context.getDrawable(R.drawable.app_icon_your_company), null, null,null, null, null));

        mSonyAppList.add(buildSonyAppInfo(SonyApp.CATEGORY_TOP_FEATURED, "app2",
                context.getDrawable(R.drawable.app_icon_your_company), null, null,null, null, null));

        mSonyAppList.add(buildSonyAppInfo(SonyApp.CATEGORY_TOP_FEATURED, "app3",
                context.getDrawable(R.drawable.app_icon_your_company), null, null,null, null, null));

        mSonyAppList.add(buildSonyAppInfo(SonyApp.CATEGORY_TOP_FEATURED, "app4",
                context.getDrawable(R.drawable.app_icon_your_company), null, null,null, null, null));

        mSonyAppList.add(buildSonyAppInfo(SonyApp.CATEGORY_TOP_FEATURED, "app5",
                context.getDrawable(R.drawable.app_icon_your_company), null, null,null, null, null));
        return mSonyAppList;
    }

    private static SonyApp buildSonyAppInfo(int category, String title, Drawable bgImage,
                                          Drawable icon, String packageName, String className,
                                          String version, String bivlUrl) {
        SonyApp sonyApp = new SonyApp();
        sonyApp.setId(SonyApp.getCount());
        SonyApp.incCount();
        sonyApp.setTitle(title);
        sonyApp.setCategory(category);
        sonyApp.setBackgroundImage(bgImage);
        sonyApp.setIcon(icon);
        sonyApp.setPackageName(packageName);
        sonyApp.setClassName(className);
        sonyApp.setVersion(version);
        sonyApp.setBivlUrl(bivlUrl);
        return sonyApp;
    }
}
