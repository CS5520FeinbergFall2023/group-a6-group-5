package edu.northeastern.numad23fa23_group5;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatHistoryAdapter extends RecyclerView.Adapter<ChatHistoryAdapter.ChatHistoryViewHolder> {

    private Context context;
    private List<Pair<String, String>> users; // Pair<UserId, Username>
    private OnUserClickListener listener;

    public ChatHistoryAdapter(Context context, List<Pair<String, String>> users, OnUserClickListener listener) {
        this.context = context;
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_history, parent, false);
        return new ChatHistoryViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHistoryViewHolder holder, int position) {
        String username = users.get(position).second; // Get the username from the pair
        holder.textView.setText(username);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ChatHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ChatHistoryViewHolder(@NonNull View itemView, final OnUserClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_chat_user);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onUserClick(position);
                }
            });
        }
    }

    public interface OnUserClickListener {
        void onUserClick(int position);
    }
}
