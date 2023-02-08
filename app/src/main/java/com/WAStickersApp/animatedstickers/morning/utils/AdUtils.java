package com.WAStickersApp.animatedstickers.morning.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.WAStickersApp.animatedstickers.morning.R;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.InterstitialAd;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.ads.nativead.MediaView;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeAdLoader;
import com.huawei.hms.ads.nativead.NativeView;

public class AdUtils {
    private static final String BANNER_ID = "testw6vs28auh3";
    private static final String INTERSTITIAL_ID = "testb4znbuh3n2";
    private static final String NATIVE_BANNER_ID = "testy63txaom86";

    public static void loadBannerAd(BannerView bannerView) {
        bannerView.setAdId(BANNER_ID);
        AdParam adParam = new AdParam.Builder().build();
        bannerView.loadAd(adParam);
    }

    public static void destroyBannerAd(BannerView bannerView) {
        if (bannerView != null) {
            bannerView.destroy();
        }
    }

    public static InterstitialAd loadInterstitialAd(Context context, AdListener adListener) {
        InterstitialAd interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdId(INTERSTITIAL_ID);

        AdParam adParam = new AdParam.Builder().build();
        interstitialAd.loadAd(adParam);
        interstitialAd.setAdListener(adListener);
        return interstitialAd;
    }

    public static void showInterstitialAd(Activity activity, InterstitialAd ad, Runnable onFailedRunnable) {
        if (ad != null && ad.isLoaded()) {
            ad.show(activity);
        } else {
            if (onFailedRunnable != null) onFailedRunnable.run();
        }
    }

    public static void loadNativeAd(Context context, NativeView nativeView, NativeAd.NativeAdLoadedListener nativeAdLoadedListener, AdListener adListener) {
        NativeAdLoader.Builder builder = new NativeAdLoader.Builder(context, NATIVE_BANNER_ID);
        builder.setNativeAdLoadedListener(nativeAd -> {
            initNativeAdView(nativeAd, nativeView);
            if (nativeAdLoadedListener != null)
                nativeAdLoadedListener.onNativeAdLoaded(nativeAd);
        }).setAdListener(adListener);
        NativeAdLoader nativeAdLoader = builder.build();
        nativeAdLoader.loadAd(new AdParam.Builder().build());
    }

    public static void destroyNativeAd(NativeAd nativeAd) {
        if (nativeAd != null) {
            nativeAd.destroy();
        }
    }

    private static void initNativeAdView(NativeAd nativeAd, NativeView nativeView) {
        // Register and populate the title view.
        nativeView.setTitleView(nativeView.findViewById(R.id.ad_title));
        ((TextView) nativeView.getTitleView()).setText(nativeAd.getTitle());
        // Register and populate the multimedia view.
        nativeView.setMediaView((MediaView) nativeView.findViewById(R.id.ad_media));
        nativeView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        // Register and populate other asset views.
        nativeView.setAdSourceView(nativeView.findViewById(R.id.ad_source));
        nativeView.setCallToActionView(nativeView.findViewById(R.id.ad_call_to_action));
        if (null != nativeAd.getAdSource()) {
            ((TextView) nativeView.getAdSourceView()).setText(nativeAd.getAdSource());
        }
        nativeView.getAdSourceView()
                .setVisibility(null != nativeAd.getAdSource() ? View.VISIBLE : View.INVISIBLE);
        if (null != nativeAd.getCallToAction()) {
            ((Button) nativeView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        nativeView.getCallToActionView()
                .setVisibility(null != nativeAd.getCallToAction() ? View.VISIBLE : View.INVISIBLE);

        // Register the native ad object.
        nativeView.setNativeAd(nativeAd);
    }
}
