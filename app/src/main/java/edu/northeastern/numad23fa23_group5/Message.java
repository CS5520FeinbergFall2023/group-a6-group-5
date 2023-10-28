package edu.northeastern.numad23fa23_group5;

public class Message {
    private String from;
    private String to;
    private String sticker;
    private String timestamp;  // Timestamp when the message was sent

    // Default constructor required for Firebase
    public Message() {}

    public Message(String from, String to, String sticker, String timestamp) {
        this.from = from;
        this.to = to;
        this.sticker = sticker;
        this.timestamp = timestamp;
    }

    // Getters and Setters

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSticker() {
        return sticker;
    }

    public void setSticker(String sticker) {
        this.sticker = sticker;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
