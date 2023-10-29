package edu.northeastern.numad23fa23_group5;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Message {
    private boolean ifRead;
    private String senderID;
    private String receiverID;
    private String stickerID;
    private String timestamp;  // Timestamp when the message was sent
    private String senderUsername;

    // Default constructor required for Firebase
    public Message() {}

    public Message(String senderID, String receiverID, String stickerID, String timestamp) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.stickerID = stickerID;
        this.timestamp = timestamp;
    }

    // Getters and Setters

    public String getSenderUsername() {
        return senderUsername;
    }
    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getStickerID() {
        return stickerID;
    }

    public void setStickerID(String stickerID) {
        this.stickerID = stickerID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isIfRead() {
        return ifRead;
    }

    public void setIfRead(boolean ifRead) {
        this.ifRead = ifRead;
    }
}
