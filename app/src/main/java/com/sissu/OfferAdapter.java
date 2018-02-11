package com.sissu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {
    private Context offerContext;
    private List<Offer> OfferList;

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
        Picasso.with(offerContext).load(OfferList.get(position).getOfferImage()).resize(1080,380).into(holder.offer_image);
    }

    @Override
    public int getItemCount() {
        return OfferList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView offer_image;
        public MyViewHolder(View view) {
            super(view);
            offer_image = (ImageView)view.findViewById(R.id.offer_image);
        }
    }

}
