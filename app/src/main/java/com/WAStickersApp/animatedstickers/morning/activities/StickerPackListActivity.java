/*
 * Copyright (c) WhatsApp Inc. and its affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.WAStickersApp.animatedstickers.morning.activities;

import static com.WAStickersApp.animatedstickers.morning.utils.Methods.gotoFB;
import static com.WAStickersApp.animatedstickers.morning.utils.Methods.gotoHwGallery;
import static com.WAStickersApp.animatedstickers.morning.utils.Methods.gotoinstagram;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.WAStickersApp.animatedstickers.morning.BuildConfig;
import com.WAStickersApp.animatedstickers.morning.R;
import com.WAStickersApp.animatedstickers.morning.StickerPack;
import com.WAStickersApp.animatedstickers.morning.adapters.StickerPackListAdapter;
import com.WAStickersApp.animatedstickers.morning.dialogue.ExitDialog;
import com.WAStickersApp.animatedstickers.morning.stickerloader.WhitelistCheck;
import com.WAStickersApp.animatedstickers.morning.utils.AdUtils;
import com.WAStickersApp.animatedstickers.morning.viewholders.StickerPackListItemViewHolder;
import com.google.android.material.navigation.NavigationView;
import com.huawei.hms.ads.banner.BannerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


///////// iN THIS Activity i want to ad facebbook native ads
/////

public class StickerPackListActivity extends AddStickerPackActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String appUrl;

    private DrawerLayout drawer;
    //////////


    public static final String EXTRA_STICKER_PACK_LIST_DATA = "sticker_pack_list";
    private static final int STICKER_PREVIEW_DISPLAY_LIMIT = 5;
    private LinearLayoutManager packLayoutManager;
    private RecyclerView packRecyclerView;
    private StickerPackListAdapter allStickerPacksListAdapter;
    private WhiteListCheckAsyncTask whiteListCheckAsyncTask;
    private ArrayList<StickerPack> stickerPackList;
    private BannerView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sticker_pack_list);

        appUrl = getString(R.string.developer_page_more_apps);

        mAdView = findViewById(R.id.adView);
        AdUtils.loadBannerAd(mAdView);

        //////////ads

/////////////oncreate///////////////
        drawer = findViewById(R.id.draw);
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_format_align_left_24);

        Toolbar toolbar1 = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar1);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);


        packRecyclerView = findViewById(R.id.sticker_pack_list);
        stickerPackList = getIntent().getParcelableArrayListExtra(EXTRA_STICKER_PACK_LIST_DATA);
        showStickerPackList(stickerPackList);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getQuantityString(R.plurals.title_activity_sticker_packs_list, stickerPackList.size()));
        }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        whiteListCheckAsyncTask = new WhiteListCheckAsyncTask(this);
        whiteListCheckAsyncTask.execute(stickerPackList.toArray(new StickerPack[0]));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (whiteListCheckAsyncTask != null && !whiteListCheckAsyncTask.isCancelled()) {
            whiteListCheckAsyncTask.cancel(true);
        }
    }

    private void showStickerPackList(List<StickerPack> stickerPackList) {
        allStickerPacksListAdapter = new StickerPackListAdapter(stickerPackList, onAddButtonClickedListener);
        packRecyclerView.setAdapter(allStickerPacksListAdapter);
        packLayoutManager = new LinearLayoutManager(this);
        packLayoutManager.setOrientation(RecyclerView.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                packRecyclerView.getContext(),
                packLayoutManager.getOrientation()
        );
        packRecyclerView.addItemDecoration(dividerItemDecoration);
        packRecyclerView.setLayoutManager(packLayoutManager);
        packRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(this::recalculateColumnCount);
    }


    private final StickerPackListAdapter.OnAddButtonClickedListener onAddButtonClickedListener = pack -> addStickerPackToWhatsApp(pack.identifier, pack.name);


    private void recalculateColumnCount() {
        final int previewSize = getResources().getDimensionPixelSize(R.dimen.sticker_pack_list_item_preview_image_size);
        int firstVisibleItemPosition = packLayoutManager.findFirstVisibleItemPosition();
        StickerPackListItemViewHolder viewHolder = (StickerPackListItemViewHolder) packRecyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition);
        if (viewHolder != null) {
            final int widthOfImageRow = viewHolder.imageRowView.getMeasuredWidth();
            final int max = Math.max(widthOfImageRow / previewSize, 1);
            int maxNumberOfImagesInARow = Math.min(STICKER_PREVIEW_DISPLAY_LIMIT, max);
            int minMarginBetweenImages = (widthOfImageRow - maxNumberOfImagesInARow * previewSize) / (maxNumberOfImagesInARow - 1);
            allStickerPacksListAdapter.setImageRowSpec(maxNumberOfImagesInARow, minMarginBetweenImages);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmain, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


            case R.id.nav_facebook:
                gotoFB(this);

                break;
            case R.id.nav_instagram:
                gotoinstagram(this);
                break;
            case R.id.action_share: {
                Intent _myIntent = new Intent(Intent.ACTION_SEND);
                _myIntent.setType("text/plain");
                String ShareBody = getString(R.string.shareapplication) + getResources().getString(R.string.app_name) + "'\nYou'll love it. \n\n" + appUrl;
                String ShareSub = "Subject";
                _myIntent.putExtra(Intent.EXTRA_SUBJECT, ShareSub);
                _myIntent.putExtra(Intent.EXTRA_TEXT, ShareBody);
                startActivity(Intent.createChooser(_myIntent, "Share using"));
                return true;
            }
            case R.id.action_pp: {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(R.string.privacy_policy);
                WebView wv = new WebView(this);
                wv.loadUrl("file:///android_asset/PrivacyPolicy.html");
                wv.setWebViewClient(new WebViewClient());
                alert.setView(wv);
                alert.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                return true;
            }

            case R.id.privacy_side:

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(R.string.privacy_policy);
                WebView wv = new WebView(this);
                wv.loadUrl("file:///android_asset/PrivacyPolicy.html");
                wv.setWebViewClient(new WebViewClient());
                alert.setView(wv);
                alert.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();

                break;

            case R.id.moreapps_side:
            case R.id.rateapp_side:
            case R.id.about_side:
            case R.id.action_rateapp_side:
                gotoHwGallery(this);

                break;

            case R.id.exit_side:
                onBackPressed();

                break;
            case R.id.feedback:

                email();


                break;
            case R.id.Request:
                email();

                break;

            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "i am using this amazing Animated whatsapp stickers app contain 1000+ Funny stickers\n " + appUrl;
                String shareSub = "Your subject here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                break;
            default:
                break;

        }


        drawer.closeDrawer(GravityCompat.START);

        return true;

    }

    static class WhiteListCheckAsyncTask extends AsyncTask<StickerPack, Void, List<StickerPack>> {
        private final WeakReference<StickerPackListActivity> stickerPackListActivityWeakReference;

        WhiteListCheckAsyncTask(StickerPackListActivity stickerPackListActivity) {
            this.stickerPackListActivityWeakReference = new WeakReference<>(stickerPackListActivity);
        }

        @Override
        protected final List<StickerPack> doInBackground(StickerPack... stickerPackArray) {
            final StickerPackListActivity stickerPackListActivity = stickerPackListActivityWeakReference.get();
            if (stickerPackListActivity == null) {
                return Arrays.asList(stickerPackArray);
            }
            for (StickerPack stickerPack : stickerPackArray) {
                stickerPack.setIsWhitelisted(WhitelistCheck.isWhitelisted(stickerPackListActivity, stickerPack.identifier));
            }
            return Arrays.asList(stickerPackArray);
        }

        @Override
        protected void onPostExecute(List<StickerPack> stickerPackList) {
            final StickerPackListActivity stickerPackListActivity = stickerPackListActivityWeakReference.get();
            if (stickerPackListActivity != null) {
                stickerPackListActivity.allStickerPacksListAdapter.setStickerPackList(stickerPackList);
                stickerPackListActivity.allStickerPacksListAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onBackPressed() {
        ExitDialog exitDialog = new ExitDialog(StickerPackListActivity.this);
        exitDialog.show();
        exitDialog.setCancelable(false);
        Window window = exitDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void inter() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void fbbanner2() {
        //NATIVE
    }

    public void email() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }
}
