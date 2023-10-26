package edu.northeastern.numad23fa23_group5;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StickerHistoryReviewHolder extends RecyclerView.ViewHolder{
    ImageView stickerImage;
    TextView stickerName;
    TextView stickerPrice;
    TextView stickerSentCount;
    public StickerHistoryReviewHolder(@NonNull View itemView) {
        super(itemView);
        stickerImage=itemView.findViewById(R.id.stickerImageView);
        stickerName=itemView.findViewById(R.id.stickerNameTextView);
        stickerPrice=itemView.findViewById(R.id.stickerPriceTextView);
        stickerSentCount=itemView.findViewById(R.id.sentCountTextView);
    }
}
