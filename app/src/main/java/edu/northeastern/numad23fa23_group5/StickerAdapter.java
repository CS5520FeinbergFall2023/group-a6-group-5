package edu.northeastern.numad23fa23_group5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.StickerViewHolder> {

    private List<Sticker> stickerList;
    private Context context;

    public StickerAdapter(Context context, List<Sticker> stickerList) {
        this.context = context;
        this.stickerList = stickerList;
    }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticker, parent, false);
        return new StickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        Sticker sticker = stickerList.get(position);
        holder.tvStickerName.setText(sticker.getName());
        holder.tvStickerPrice.setText("$" + sticker.getPrice());

        // Use Glide to load the image
        Glide.with(context)
                .load(sticker.getImage())
                .into(holder.ivStickerItem);
    }

    @Override
    public int getItemCount() {
        return stickerList.size();
    }

    static class StickerViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStickerItem;
        TextView tvStickerName;
        TextView tvStickerPrice;

        public StickerViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStickerItem = itemView.findViewById(R.id.iv_sticker_item);
            tvStickerName = itemView.findViewById(R.id.tv_sticker_name);
            tvStickerPrice = itemView.findViewById(R.id.tv_sticker_price);
        }
    }
}
