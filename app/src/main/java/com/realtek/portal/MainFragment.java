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
import android.widget.Toast;

public class MainFragment extends BrowseFragment {
    private static final String TAG = "MainFragment";

    private ArrayObjectAdapter mRowsAdapter;
    private Context mContext;

    List<SonyApp> mTopFeaturedList = new ArrayList<>();
    List<SonyApp> mMoreAppsList = new ArrayList<>();
    List<SonyApp> mPromotionList = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();

        setTitle(getString(R.string.browse_title));
        loadRows();
        setupEventListeners();
    }

    private void loadRows() {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CardPresenter cardPresenter = new CardPresenter();
                String region = Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry();
                String topFeaturedApps[][];
                String moreApps[][];
                String promotionApps[][];

                switch (region){
                    case "zh_CN":
                        topFeaturedApps = SonyUtils.AreaChina.TOP_FEATURED_APPS;
                        moreApps = SonyUtils.AreaChina.MORE_APPS;
                        promotionApps = SonyUtils.AreaChina.PROMOTION_APPS;
                        break;

                    case "zh_TW":
                        topFeaturedApps = SonyUtils.AreaTW.TOP_FEATURED_APPS;
                        moreApps = SonyUtils.AreaTW.MORE_APPS;
                        promotionApps = SonyUtils.AreaTW.PROMOTION_APPS;
                        break;

                    case "en_US":
                        topFeaturedApps = SonyUtils.AreaUSA.TOP_FEATURED_APPS;
                        moreApps = SonyUtils.AreaUSA.MORE_APPS;
                        promotionApps = SonyUtils.AreaUSA.PROMOTION_APPS;
                        break;

                    case "en_GB":
                        topFeaturedApps = SonyUtils.AreaUSA.TOP_FEATURED_APPS;
                        moreApps = SonyUtils.AreaUSA.MORE_APPS;
                        promotionApps = SonyUtils.AreaUSA.PROMOTION_APPS;
                        break;

                    default:
                        topFeaturedApps = SonyUtils.AreaUSA.TOP_FEATURED_APPS;
                        moreApps = SonyUtils.AreaUSA.MORE_APPS;
                        promotionApps = SonyUtils.AreaUSA.PROMOTION_APPS;
                        break;
                }

                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER);
                List<ResolveInfo> resolveInfo = mContext.getPackageManager().queryIntentActivities(intent, 0);

                for (int i = 0; i < resolveInfo.size(); i++) {
                    ResolveInfo info = resolveInfo.get(i);
                    String packageName = info.activityInfo.packageName;
                    String className = info.activityInfo.name;
                    CharSequence title = info.activityInfo.loadLabel(mContext.getPackageManager());
                    Drawable icon = info.loadIcon(mContext.getPackageManager());

                    for(int p = 0; p < topFeaturedApps.length; p++) {
                        for(int c = 0; c < topFeaturedApps[0].length; c++) {
                            if (packageName.equals(topFeaturedApps[p][0])
                                    && className.equals(topFeaturedApps[p][c])) {
                                SonyApp sonyApp = new SonyApp();
                                sonyApp.setId(SonyApp.getCount());
                                SonyApp.incCount();
                                sonyApp.setTitle(title + "");
                                Log.d(TAG, "packageName = " + packageName + ", className = " + className + ", title = " + title);
                                sonyApp.setCategory(SonyApp.CATEGORY_TOP_FEATURED);
                                //sonyApp.setBackgroundImage(bgImage);
                                sonyApp.setIcon(icon);
                                sonyApp.setPackageName(packageName);
                                sonyApp.setClassName(className);
                                //sonyApp.setVersion(version);
                                //sonyApp.setBivlUrl(bivlUrl);
                                mTopFeaturedList.add(sonyApp);
                                break;
                            }
                        }
                    }

                    for(int p = 0; p < moreApps.length; p++) {
                        for(int c = 0; c < moreApps[0].length; c++) {
                            if (packageName.equals(moreApps[p][0])
                                    && className.equals(moreApps[p][c])) {
                                SonyApp sonyApp = new SonyApp();
                                sonyApp.setId(SonyApp.getCount());
                                SonyApp.incCount();
                                sonyApp.setTitle(title + "");
                                sonyApp.setCategory(SonyApp.CATEGORY_MORE_APPS);
                                //sonyApp.setBackgroundImage(bgImage);
                                sonyApp.setIcon(icon);
                                sonyApp.setPackageName(packageName);
                                sonyApp.setClassName(className);
                                //sonyApp.setVersion(version);
                                //sonyApp.setBivlUrl(bivlUrl);
                                mMoreAppsList.add(sonyApp);
                                break;
                            }
                        }
                    }

                    for(int p = 0; p < promotionApps.length; p++) {
                        for(int c = 0; c < promotionApps[0].length; c++) {
                            if (packageName.equals(promotionApps[p][0])
                                    && className.equals(promotionApps[p][c])) {
                                SonyApp sonyApp = new SonyApp();
                                sonyApp.setId(SonyApp.getCount());
                                SonyApp.incCount();
                                sonyApp.setTitle(title + "");
                                sonyApp.setCategory(SonyApp.CATEGORY_PROMOTION);
                                //sonyApp.setBackgroundImage(bgImage);
                                sonyApp.setIcon(icon);
                                sonyApp.setPackageName(packageName);
                                sonyApp.setClassName(className);
                                //sonyApp.setVersion(version);
                                //sonyApp.setBivlUrl(bivlUrl);
                                mPromotionList.add(sonyApp);
                                break;
                            }
                        }
                    }
                }

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

                for (int i = 0; i < numRows; i++) {
                    String headerName = mContext.getString(R.string.no_apps);
                    ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);

                    switch (i){
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

                setAdapter(mRowsAdapter);
            }
        });
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
            Log.d(TAG, "Item: " + item.toString());
            Toast.makeText(getActivity(), "Click Item = " + ((SonyApp)item).getId(), Toast.LENGTH_SHORT).show();
        }
    }
}
