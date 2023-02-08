package com.WAStickersApp.animatedstickers.morning.dialogue;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.WAStickersApp.animatedstickers.morning.R;


public class MystickerDialogue extends Dialog
{

    Activity activity;


    LinearLayout urlgb;
    public MystickerDialogue(Activity activity)
    {
        super(activity);
        this.activity = activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sticker_dialogue);
        urlgb = (LinearLayout) findViewById(R.id.url_gb);
        urlgb.  setOnClickListener((View v) ->

            url()

        );

        LinearLayout  no;
        no = (LinearLayout) findViewById(R.id.no);

        no.  setOnClickListener((View v) -> {

            MystickerDialogue.super.onBackPressed();
                dismiss();

        });


    }
    public void url(){
        Uri w = Uri.parse("https://heymods.com/");
        Intent q = new Intent(Intent.ACTION_VIEW, w);
        activity.startActivity(q);

    }


}
