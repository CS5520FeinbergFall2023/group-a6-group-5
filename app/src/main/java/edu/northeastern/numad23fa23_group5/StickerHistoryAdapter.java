package edu.northeastern.numad23fa23_group5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StickerHistoryAdapter extends RecyclerView.Adapter<StickerHistoryReviewHolder>{
    private final ArrayList<StickerHistoryItemCard> itemList;

    public StickerHistoryAdapter(ArrayList<StickerHistoryItemCard> itemList) {
        this.itemList = itemList;
    }

    @Override
    public StickerHistoryReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_collapse, parent, false);
        final StickerHistoryReviewHolder holder = new StickerHistoryReviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StickerHistoryReviewHolder holder, int position) {
        StickerHistoryItemCard currentItem = itemList.get(position);
        holder.stickerSentCount.setText("Sent Count: "+currentItem.getStickerSentCount());
        holder.stickerName.setText(currentItem.getStickerName());
        holder.stickerPrice.setText("Price: $" + currentItem.getStickerPrice());
        //todo:also a function to load picture from image
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
