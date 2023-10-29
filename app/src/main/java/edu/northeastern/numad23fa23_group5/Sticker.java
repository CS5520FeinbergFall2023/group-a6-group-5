package edu.northeastern.numad23fa23_group5;

public class Sticker {
    private Long id;
    private String name;
    private String image;
    private double price;
    private int sentCount;  // Number of times the sticker has been sent

    // Default constructor required for Firebase
    public Sticker() {}

    public Sticker(Long id, String name, String image, double price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.sentCount = 0;  // Initially, the sticker hasn't been sent
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
