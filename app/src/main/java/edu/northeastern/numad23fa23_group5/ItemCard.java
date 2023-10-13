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
    //ratings
    float ratings;
    //price
    //variants
    //TODO: delivery and pick up, not sure how many possible variations are there for these two
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
}
