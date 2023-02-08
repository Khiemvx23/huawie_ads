package com.WAStickersApp.animatedstickers.morning.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.WAStickersApp.animatedstickers.morning.R;

public class Methods {
    public static void sendEmail(Activity activity) {
        if (activity != null) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.setPackage("com.google.android.gm");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{activity.getString(R.string.about_us_email_text)});
            intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name));
            activity.startActivity(intent);
        }
    }

    public static void gotoFB(Context context) {
        if (context != null) {
            Uri uri = Uri.parse("fb://page/" + context.getResources().getString(R.string.facebook_page_id));
            Intent data = new Intent(Intent.ACTION_VIEW, uri);
            data.setPackage("com.facebook.katana");

            try {
                context.startActivity(data);
            } catch (ActivityNotFoundException e) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/" + context.getResources().getString(R.string.facebook_username))));
            }
        }
    }

    public static void gotoinstagram(Context context) {
        if (context != null) {
            Uri uri = Uri.parse("https://www.instagram.com/" + context.getResources().getString(R.string.instagram_username));
            Intent data = new Intent(Intent.ACTION_VIEW, uri);
            data.setPackage("com.instagram.android");

            try {
                context.startActivity(data);
            } catch (ActivityNotFoundException e) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/" + context.getResources().getString(R.string.instagram_username))));
            }
        }
    }

    public static void gotoHwGallery(Context context) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context.getString(R.string.developer_page_more_apps))));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context.getString(R.string.developer_page_more_apps))));
        }
    }
}
