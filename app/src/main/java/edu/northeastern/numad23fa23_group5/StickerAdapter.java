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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.StickerViewHolder> {

    private List<Sticker> stickerList;
    private Context context;
    private MessageSender messageSender;

    public StickerAdapter(Context context, List<Sticker> stickerList, UserChatActivity userChatActivity) {
        this.context = context;
        this.stickerList = stickerList;
        this.messageSender = userChatActivity;;
    }

    public interface MessageSender {
        void sendMessage(String selectedStickerID);
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
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(sticker.getImage());

        // Use Glide to load the image
        Glide.with(context)
                .load(storageRef)
                .into(holder.ivStickerItem);

        holder.itemView.setOnClickListener(v -> {
            // Implement the functionality to send the selected sticker in the chat
            if (sticker != null && messageSender != null) {
                Long selectedStickerID = sticker.getId(); // Access the sticker ID (id field)
                // Call the sendMessage method via the callback
                messageSender.sendMessage(selectedStickerID.toString()); // Convert Long to String
            }
        });
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
