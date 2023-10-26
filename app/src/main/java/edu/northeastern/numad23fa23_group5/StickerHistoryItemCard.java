package edu.northeastern.numad23fa23_group5;

public class StickerHistoryItemCard {
    String stickerThumbnailPath;
    String stickerPrice;
    String stickerName;
    int stickerSentCount;

    public StickerHistoryItemCard(String stickerThumbnailPath, String stickerPrice, String stickerName, int stickerSentCount) {
        this.stickerThumbnailPath = stickerThumbnailPath;
        this.stickerPrice = stickerPrice;
        this.stickerName = stickerName;
        this.stickerSentCount = stickerSentCount;
    }

    public String getStickerThumbnailPath() {
        return stickerThumbnailPath;
    }

    public void setStickerThumbnailPath(String stickerThumbnailPath) {
        this.stickerThumbnailPath = stickerThumbnailPath;
    }

    public String getStickerPrice() {
        return stickerPrice;
    }

    public void setStickerPrice(String stickerPrice) {
        this.stickerPrice = stickerPrice;
    }

    public String getStickerName() {
        return stickerName;
    }

    public void setStickerName(String stickerName) {
        this.stickerName = stickerName;
    }

    public int getStickerSentCount() {
        return stickerSentCount;
    }

    public void setStickerSentCount(int stickerSentCount) {
        this.stickerSentCount = stickerSentCount;
    }
}
