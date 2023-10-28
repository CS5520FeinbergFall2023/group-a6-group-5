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
        holder.senderTextView.setText(currentMessage.getFrom());
        holder.timestampTextView.setText(currentMessage.getTimestamp());

        Sticker sticker = findStickerByName(currentMessage.getSticker());
        if (sticker != null) {
            Glide.with(context).load(sticker.getImage()).into(holder.stickerImageView);
        }
    }

    @Override
    public int getItemCount() {
        return chatHistory.size();
    }

    // Helper function to fetch the Sticker object based on its name
    private Sticker findStickerByName(String name) {
        for (Sticker sticker : stickers) {
            if (sticker.getName().equals(name)) {
                return sticker;
            }
        }
        return null;
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView senderTextView;
        ImageView stickerImageView;
        TextView timestampTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.tv_sender);
            stickerImageView = itemView.findViewById(R.id.iv_sticker);
            timestampTextView = itemView.findViewById(R.id.tv_timestamp);
        }
    }
}
