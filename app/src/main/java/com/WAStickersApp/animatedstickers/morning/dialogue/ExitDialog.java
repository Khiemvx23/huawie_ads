package com.WAStickersApp.animatedstickers.morning.dialogue;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.WAStickersApp.animatedstickers.morning.R;

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
        text2 = (TextView)  findViewById(R.id.text_exit);
        LinearLayout yes = findViewById(R.id.yes);
        LinearLayout no = findViewById(R.id.no);
        LinearLayout more = findViewById(R.id.more);


        more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(context.getString(R.string.developer_page_more_apps))));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(context.getString(R.string.developer_page_more_apps))));
                }
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
        AdLoader.Builder builder = new AdLoader.Builder(getContext(), getContext().getString(R.string.ADMOB_Native_ads_UNIT_ID));
        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd NativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                text1.setVisibility(View.GONE);
                text2.setVisibility(View.VISIBLE);
                nativeAd = NativeAd;
                FrameLayout frameLayout = findViewById(R.id.fl_adplaceholder);

                NativeAdView adView = (NativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);

                populateNativeAdView(NativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);

            }
        });

        NativeAdOptions adOptions = new NativeAdOptions.Builder().build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
              text1.setVisibility(View.GONE);
              text2.setVisibility(View.VISIBLE);
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }


    private void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {

        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));

        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }
        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());

        }
        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);

        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
    }

}