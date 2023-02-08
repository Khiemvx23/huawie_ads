package com.WAStickersApp.animatedstickers.morning.utils;

import android.app.Activity;
import android.content.Context;

import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.InterstitialAd;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeAdLoader;

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

    public static void loadNativeAd(Context context, NativeAd.NativeAdLoadedListener nativeLoadedListener, AdListener adListener) {
        NativeAdLoader.Builder builder = new NativeAdLoader.Builder(context, NATIVE_BANNER_ID);
        builder.setNativeAdLoadedListener(nativeLoadedListener).setAdListener(adListener);
        NativeAdLoader nativeAdLoader = builder.build();
        nativeAdLoader.loadAd(new AdParam.Builder().build());
    }
}
