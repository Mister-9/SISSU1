package com.sissu;

/**
 * Created by bhavi on 20-01-2018.
 */
public class Offer {
    private String offer_image;
    public Offer()
    {}
    public Offer(String offerImage)
    {
        this.offer_image=offerImage;
    }
    public String getOfferImage ()
    {
        return offer_image;
    }
    public void setOfferImage(String offer_image)
    {
        this.offer_image=offer_image;
    }
}
