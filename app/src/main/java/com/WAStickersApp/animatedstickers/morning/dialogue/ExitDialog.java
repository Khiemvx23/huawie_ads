package com.WAStickersApp.animatedstickers.morning.dialogue;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.WAStickersApp.animatedstickers.morning.R;
import com.WAStickersApp.animatedstickers.morning.utils.AdUtils;
import com.WAStickersApp.animatedstickers.morning.utils.Methods;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeView;

public class ExitDialog extends Dialog {

    private final Activity context;
    Activity activity;
    private LinearLayout yes;
    private NativeAd nativeAd;
    private TextView text1;
    private TextView text2;

    public ExitDialog(Activity context) {
        super(context);
        this.activity = context;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.exit_dialog);
        refreshAd();
        text1 = (TextView) findViewById(R.id.text_Wait);
        text2 = (TextView) findViewById(R.id.text_exit);
        LinearLayout yes = findViewById(R.id.yes);
        LinearLayout no = findViewById(R.id.no);
        LinearLayout more = findViewById(R.id.more);


        more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Methods.gotoHwGallery(context);
                dismiss();
            }
        });

        yes.setOnClickListener((View v) -> {
            ExitDialog.super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.finish();
        });

        no.setOnClickListener((View v) -> {
            ExitDialog.super.onBackPressed();
            dismiss();
        });

        loadNativeAds();
    }

    private void refreshAd() {
    }

    private void loadNativeAds() {
        FrameLayout frameLayout = findViewById(R.id.fl_adplaceholder);

        NativeView adView = (NativeView) getLayoutInflater().inflate(R.layout.ad_unified_hw, null);
        frameLayout.removeAllViews();
        frameLayout.addView(adView);

        AdUtils.loadNativeAd(context, adView, ad -> {
            AdUtils.destroyNativeAd(nativeAd);
            nativeAd = ad;
            text1.setVisibility(View.GONE);
            text2.setVisibility(View.VISIBLE);
        }, new AdListener() {
            @Override
            public void onAdFailed(int i) {
                text1.setVisibility(View.GONE);
                text2.setVisibility(View.VISIBLE);
            }
        });
    }

}