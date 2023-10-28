package edu.northeastern.numad23fa23_group5;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class StickerHistoryItemCard implements Parcelable {
    String stickerThumbnailPath;
    float stickerPrice;
    String stickerName;
    int stickerSentCount;

    public StickerHistoryItemCard(String stickerThumbnailPath, float stickerPrice, String stickerName, int stickerSentCount) {
        this.stickerThumbnailPath = stickerThumbnailPath;
        this.stickerPrice = stickerPrice;
        this.stickerName = stickerName;
        this.stickerSentCount = stickerSentCount;
    }

    protected StickerHistoryItemCard(Parcel in) {
        stickerThumbnailPath = in.readString();
        stickerPrice = in.readFloat();
        stickerName = in.readString();
        stickerSentCount = in.readInt();
    }

    public static final Creator<StickerHistoryItemCard> CREATOR = new Creator<StickerHistoryItemCard>() {
        @Override
        public StickerHistoryItemCard createFromParcel(Parcel in) {
            return new StickerHistoryItemCard(in);
        }

        @Override
        public StickerHistoryItemCard[] newArray(int size) {
            return new StickerHistoryItemCard[size];
        }
    };

    public String getStickerThumbnailPath() {
        return stickerThumbnailPath;
    }

    public void setStickerThumbnailPath(String stickerThumbnailPath) {
        this.stickerThumbnailPath = stickerThumbnailPath;
    }

    public float getStickerPrice() {
        return stickerPrice;
    }

    public void setStickerPrice(float stickerPrice) {
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

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(stickerThumbnailPath);
        dest.writeFloat(stickerPrice);
        dest.writeString(stickerName);
        dest.writeInt(stickerSentCount);
    }
}
