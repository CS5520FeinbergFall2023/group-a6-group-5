package edu.northeastern.numad23fa23_group5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class StickerHistoryAdapter extends RecyclerView.Adapter<StickerHistoryReviewHolder>{
    private final ArrayList<StickerHistoryItemCard> itemList;
    private ItemClickListener listener;

    private Context context;

    public StickerHistoryAdapter(Context context,ArrayList<StickerHistoryItemCard> itemList) {
        this.context=context;
        this.itemList = itemList;
    }

    @Override
    public StickerHistoryReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_sticker_history, parent, false);
        final StickerHistoryReviewHolder holder = new StickerHistoryReviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StickerHistoryReviewHolder holder, int position) {
        StickerHistoryItemCard currentItem = itemList.get(position);
        holder.stickerSentCount.setText("Sent Count: "+currentItem.getStickerSentCount());
        holder.stickerName.setText(currentItem.getStickerName());
        holder.stickerPrice.setText("Price: $" + currentItem.getStickerPrice());
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(currentItem.getStickerThumbnailPath());
        // Use Glide to load the image
        Glide.with(context)
                .load(storageRef)
                .into(holder.stickerImage);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
