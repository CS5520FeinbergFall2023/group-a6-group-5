package edu.northeastern.numad23fa23_group5;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * A class for cards that represent product search result
 */
public class ItemCard implements Parcelable {
    String thumbnailURL;
    String imageURL;
    String title;
    String brand;
    String price;
    float ratings;
    long reviews;

    //TODO: delivery and pick up, not sure how many possible variations are there for these two
    // and they need zipcode which is actually optional. Add them later if wanted.
    /* As far as I can see there're:
        --------------DILIVERY---------------
        "delivery":
        {
        "out_of_stock":true (if it cannot be delivered)
        }
        OR
        "delivery":
        {
        "free":true,
        "free_delivery_threshold":false (if it's available for free delivery, haven't seen one that can be delivered but not for free)
        }
        ---------------PICKUP--------------------------------
        "pickup":
        {
        "free_ship_to_store":true (if it can be pick up for free)
        }
        OR
        this field of "pick up" won't exists if pickup is unavailable
     */

    public ItemCard(String thumbnailURL, String imageURL, String title, String brand, String price,
                    float ratings, long reviews) {
        this.thumbnailURL = thumbnailURL;
        this.imageURL = imageURL;
        this.title = title;
        this.brand = brand;
        this.price = price;
        this.ratings = ratings;
        this.reviews = reviews;
    }

    protected ItemCard(Parcel in) {
        thumbnailURL = in.readString();
        imageURL = in.readString();
        title = in.readString();
        brand = in.readString();
        price = in.readString();
        ratings = in.readFloat();
        reviews = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumbnailURL);
        dest.writeString(imageURL);
        dest.writeString(title);
        dest.writeString(brand);
        dest.writeString(price);
        dest.writeFloat(ratings);
        dest.writeLong(reviews);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemCard> CREATOR = new Creator<ItemCard>() {
        @Override
        public ItemCard createFromParcel(Parcel in) {
            return new ItemCard(in);
        }

        @Override
        public ItemCard[] newArray(int size) {
            return new ItemCard[size];
        }
    };

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getBrand() {
        return brand;
    }

    public String getPrice() {
        return price;
    }

    public float getRatings() {
        return ratings;
    }

    public long getReviews() { return reviews; }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public void setReviews(long reviews) { this.reviews = reviews; }
}
