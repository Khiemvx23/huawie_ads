/*
 * Copyright (c) WhatsApp Inc. and its affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.WAStickersApp.animatedstickers.morning.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.WAStickersApp.animatedstickers.morning.R;

public class StickerPackListItemViewHolder extends RecyclerView.ViewHolder {

    public final View container;
    public final TextView titleView;
    public final TextView publisherView;
    public final TextView filesizeView;
    public final TextView itempackcreated;
    public final ImageView addButton;
    public final ImageView packtryimage;
    public final LinearLayout imageRowView;

    public StickerPackListItemViewHolder(final View itemView) {
        super(itemView);
        container = itemView;
        titleView = itemView.findViewById(R.id.sticker_pack_title);
        publisherView = itemView.findViewById(R.id.sticker_pack_publisher);
        filesizeView = itemView.findViewById(R.id.sticker_pack_filesize);
        itempackcreated = itemView.findViewById(R.id.item_pack_created);
        addButton = itemView.findViewById(R.id.add_button_on_list);
        packtryimage = itemView.findViewById(R.id.pack_try_image);
        imageRowView = itemView.findViewById(R.id.sticker_packs_list_item_image_list);
    }
}