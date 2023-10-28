package edu.northeastern.numad23fa23_group5;

public class Sticker {
    private String name;
    private String imageUrl;
    private double price;
    private int sentCount;  // Number of times the sticker has been sent

    // Default constructor required for Firebase
    public Sticker() {}

    public Sticker(String name, String imageUrl, double price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.sentCount = 0;  // Initially, the sticker hasn't been sent
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSentCount() {
        return sentCount;
    }

    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

    public void incrementSentCount() {
        this.sentCount += 1;
    }
}
