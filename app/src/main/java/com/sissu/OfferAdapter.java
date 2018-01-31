package com.sissu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by bhavi on 20-01-2018.
 */

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {
    private Context offerContext;
    private List<Offer> OfferList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageButton offer_image;
        public String offer_url;
        public MyViewHolder(View view) {
            super(view);
            offer_image = view.findViewById(R.id.offer_image);
        }
    }
    public OfferAdapter(Context offerContext, List<Offer> OfferList) {
        this.offerContext = offerContext;
        this.OfferList = OfferList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View offerView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_card, parent, false);
        return new MyViewHolder(offerView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Offer Offer = OfferList.get(position);
        holder.offer_url=Offer.getOfferImage();
        try {
            URL offer_url = new URL(Offer.getOfferImage());
            Bitmap bmp = BitmapFactory.decodeStream(offer_url.openConnection().getInputStream());
            holder.offer_image.setImageBitmap(bmp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // loading Offer offer_image using Glide library
        Glide.with(offerContext).load(Offer.getOfferImage()).override(750,600).into(holder.offer_image);
    }

    @Override
    public int getItemCount() {
        return OfferList.size();
    }
}
