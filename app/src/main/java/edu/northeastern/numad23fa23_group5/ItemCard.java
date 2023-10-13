package edu.northeastern.numad23fa23_group5;

/**
 * A class for cards that represent product search result
 */
public class ItemCard {
    // smallest image thumbnail URL
    String thumbnailURL;
    // biggest image URL
    String imageURL;
    //title
    String title;
    //brand
    String brand;
    String price;
    //ratings
    float ratings;

//    boolean ifDeliver;
//    boolean ifPickUp;
    //price
    //variants
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

    public ItemCard(String thumbnailURL, String imageURL, String title, String brand, String price, float ratings) {
        this.thumbnailURL = thumbnailURL;
        this.imageURL = imageURL;
        this.title = title;
        this.brand = brand;
        this.price = price;
        this.ratings = ratings;
    }

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
}
