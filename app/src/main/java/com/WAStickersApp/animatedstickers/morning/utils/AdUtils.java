package com.WAStickersApp.animatedstickers.morning.utils;

import android.app.Activity;
import android.content.Context;

import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.InterstitialAd;
import com.huawei.hms.ads.banner.BannerView;

public class AdUtils {
    private static final String BANNER_ID = "testw6vs28auh3";
    private static final String INTERSTITIAL_ID = "testb4znbuh3n2";

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

    public static InterstitialAd loadInterstitialAd(Context context) {
        InterstitialAd interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdId(INTERSTITIAL_ID);

        AdParam adParam = new AdParam.Builder().build();
        interstitialAd.loadAd(adParam);
        return interstitialAd;
    }

    public static void showInterstitialAd(Activity activity, InterstitialAd ad, AdListener adListener, Runnable onFailedRunnable) {
        if (ad != null && ad.isLoaded()) {
            ad.setAdListener(adListener);
            ad.show(activity);
        } else {
            if (onFailedRunnable != null) onFailedRunnable.run();
        }
    }
}
