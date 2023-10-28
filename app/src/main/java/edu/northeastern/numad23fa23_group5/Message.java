package edu.northeastern.numad23fa23_group5;

public class Message {
    private String sender;
    private String receiver;
    private String stickerName;
    private String timestamp;  // Timestamp when the message was sent

    // Default constructor required for Firebase
    public Message() {}

    public Message(String sender, String receiver, String stickerName, String timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.stickerName = stickerName;
        this.timestamp = timestamp;
    }

    // Getters and Setters

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getStickerName() {
        return stickerName;
    }

    public void setStickerName(String stickerName) {
        this.stickerName = stickerName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
