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

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Context context;
    private List<Message> chatHistory;
    private List<Sticker> stickers;

    public ChatAdapter(Context context, List<Message> chatHistory, List<Sticker> stickers) {
        this.context = context;
        this.chatHistory = chatHistory;
        this.stickers = stickers;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message currentMessage = chatHistory.get(position);

        holder.senderTextView.setText(currentMessage.getSenderUsername());
        holder.timestampTextView.setText(currentMessage.getTimestamp());
        Sticker sticker = findStickerByID(currentMessage.getStickerID());
        if (sticker != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(sticker.getImage());
            Glide.with(context)
                    .load(storageRef)
                    .into(holder.stickerImage);  // Adjusted this line
        }
    }




    // Helper function to fetch the Sticker object based on its name
    private Sticker findStickerByID(String stickerID) {
        Long stickerIdLong;
        try {
            stickerIdLong = Long.parseLong(stickerID);
        } catch (NumberFormatException e) {
            return null;  // If stickerID is not a valid Long representation, return null
        }

        for (Sticker sticker : stickers) {
            if (sticker.getId().equals(stickerIdLong)) {
                return sticker;
            }
        }
        return null;
    }



    @Override
    public int getItemCount() {
        return chatHistory.size();
    }


    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView senderTextView;
        ImageView stickerImage;  // Renamed this from stickerImageView to stickerImage
        TextView timestampTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.tv_sender);
            stickerImage = itemView.findViewById(R.id.iv_sticker);  // Adjusted this line
            timestampTextView = itemView.findViewById(R.id.tv_timestamp);
        }
    }

}
