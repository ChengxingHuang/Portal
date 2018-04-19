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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;

public class MainFragment extends BrowseFragment {
    private static final String TAG = "MainFragment";

    private ArrayObjectAdapter mRowsAdapter;
    private Context mContext;

    String mTopFeaturedApps[][];
    String mMoreApps[][];
    String mPromotionApps[][];

    List<SonyApp> mTopFeaturedList = new ArrayList<>();
    List<SonyApp> mMoreAppsList = new ArrayList<>();
    List<SonyApp> mPromotionList = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();

        setTitle(getString(R.string.app_name));
        initData();
        loadRows();
        setupEventListeners();
    }

    private void initData(){
        String region = Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry();
        Log.d(TAG, "region = " + region);
        switch (region){
            case "zh_CN":
                mTopFeaturedApps = SonyUtils.AreaChina.TOP_FEATURED_APPS;
                mMoreApps = SonyUtils.AreaChina.MORE_APPS;
                mPromotionApps = SonyUtils.AreaChina.PROMOTION_APPS;
                break;

            case "zh_TW":
                mTopFeaturedApps = SonyUtils.AreaTW.TOP_FEATURED_APPS;
                mMoreApps = SonyUtils.AreaTW.MORE_APPS;
                mPromotionApps = SonyUtils.AreaTW.PROMOTION_APPS;
                break;

            case "en_US":
                mTopFeaturedApps = SonyUtils.AreaUSA.TOP_FEATURED_APPS;
                mMoreApps = SonyUtils.AreaUSA.MORE_APPS;
                mPromotionApps = SonyUtils.AreaUSA.PROMOTION_APPS;
                break;

            case "en_GB":
                mTopFeaturedApps = SonyUtils.AreaUK.TOP_FEATURED_APPS;
                mMoreApps = SonyUtils.AreaUK.MORE_APPS;
                mPromotionApps = SonyUtils.AreaUK.PROMOTION_APPS;
                break;

            default:
                mTopFeaturedApps = SonyUtils.AreaOthers.TOP_FEATURED_APPS;
                mMoreApps = SonyUtils.AreaOthers.MORE_APPS;
                mPromotionApps = SonyUtils.AreaOthers.PROMOTION_APPS;
                break;
        }
    }

    private void loadRows() {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER);
                List<ResolveInfo> resolveInfo = mContext.getPackageManager().queryIntentActivities(intent, 0);

                for (int i = 0; i < resolveInfo.size(); i++) {
                    ResolveInfo info = resolveInfo.get(i);
                    String packageName = info.activityInfo.packageName;
                    String className = info.activityInfo.name;
                    CharSequence title = info.activityInfo.loadLabel(mContext.getPackageManager());
                    Drawable icon = null;
                    Log.d(TAG, "packageName = " + packageName + ", className = " + className + ", title = " + title);

                    try {
                        icon = mContext.getPackageManager().getApplicationBanner(packageName);
                        if(null == icon) {
                            icon = mContext.getPackageManager().getApplicationIcon(packageName);
                        }
                    }catch (PackageManager.NameNotFoundException e){
                        Log.d(TAG, "package not found:" + packageName);
                        continue;
                    }

                    //TOP Featured
                    if(initAppList(packageName, className,
                            title + "", icon, SonyApp.CATEGORY_TOP_FEATURED,
                            mTopFeaturedApps, mTopFeaturedList))
                        continue;

                    //More apps
                    if(initAppList(packageName, className,
                            title + "", icon, SonyApp.CATEGORY_MORE_APPS,
                            mMoreApps, mMoreAppsList))
                        continue;

                    //Promotion
                    initAppList(packageName, className,
                            title + "", icon, SonyApp.CATEGORY_PROMOTION,
                            mPromotionApps, mPromotionList);
                }

                String headerName = mContext.getString(R.string.no_apps);
                CardPresenter cardPresenter = new CardPresenter();
                int numRows = initRowsNumber();

                if(numRows > 0) {
                    for (int i = 0; i < numRows; i++) {
                        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);

                        switch (i) {
                            case 0:
                                headerName = mContext.getString(R.string.top_featured);
                                for (int j = 0; j < mTopFeaturedList.size(); j++) {
                                    listRowAdapter.add(mTopFeaturedList.get(j));
                                }
                                break;

                            case 1:
                                headerName = mContext.getString(R.string.more_apps);
                                for (int j = 0; j < mMoreAppsList.size(); j++) {
                                    listRowAdapter.add(mMoreAppsList.get(j));
                                }
                                break;

                            case 2:
                                headerName = mContext.getString(R.string.promotion);
                                for (int j = 0; j < mPromotionList.size(); j++) {
                                    listRowAdapter.add(mPromotionList.get(j));
                                }
                                break;
                        }
                        HeaderItem header = new HeaderItem(i, headerName);
                        mRowsAdapter.add(new ListRow(header, listRowAdapter));
                    }
                }else{
                    HeaderItem header = new HeaderItem(0, headerName);
                    mRowsAdapter.add(new ListRow(header, new ArrayObjectAdapter(cardPresenter)));
                }

                setAdapter(mRowsAdapter);
            }
        });
    }

    // TODO: 2018/4/18 If you have better way to handler this,fix me please
    private boolean initAppList(String packageName, String className,
                                String title, Drawable icon, int category,
                                String[][] apps, List<SonyApp> list){
        for(int p = 0; p < apps.length; p++) {
            if (packageName.equals(apps[p][0])
                    && className.equals(apps[p][1])) {
                SonyApp sonyApp = new SonyApp();
                sonyApp.setTitle(title);
                sonyApp.setCategory(category);
                sonyApp.setIcon(icon);
                sonyApp.setPackageName(packageName);
                sonyApp.setClassName(className);
                //sonyApp.setVersion(version);
                //sonyApp.setBivlUrl(bivlUrl);
                list.add(sonyApp);
                return true;
            }
        }

        return false;
    }

    private int initRowsNumber(){
        int numRows = 0;
        if(mTopFeaturedList.size() > 0){
            numRows++;
        }
        if(mMoreAppsList.size() > 0){
            numRows++;
        }
        if(mPromotionList.size() > 0){
            numRows++;
        }
        Log.d(TAG, "topFeatured size = " + mTopFeaturedList.size() +
                ", moreApps size = " + mMoreAppsList.size() +
                ", promotion size = " + mPromotionList.size());

        return numRows;
    }

    private void setupEventListeners() {
        // Don't show the search icon,if you want show this,open this mark
        /*setOnSearchClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                        .show();
            }
        });*/

        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            Log.d(TAG, "Click Item: " + item.toString());
            Intent intent = new Intent();
            intent.setClassName(((SonyApp)item).getPackageName(), ((SonyApp)item).getClassName());
            startActivity(intent);
        }
    }
}
