package edu.northeastern.numad23fa23_group5;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserChatActivity extends AppCompatActivity {

    private RecyclerView recyclerViewChat;
    private RecyclerView recyclerViewStickers;
    private List<Message> chatHistory = new ArrayList<>();
    private List<Sticker> stickers = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private StickerAdapter stickerAdapter;
    private String selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        selectedUser = getIntent().getStringExtra("selectedUser");
//        System.out.println(selectedUser);


        recyclerViewChat = findViewById(R.id.recycler_view_chat);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(this, chatHistory, stickers);
        recyclerViewChat.setAdapter(chatAdapter);

        recyclerViewStickers = findViewById(R.id.recycler_view_stickers);
        recyclerViewStickers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        stickerAdapter = new StickerAdapter(this, stickers);
        recyclerViewStickers.setAdapter(stickerAdapter);

        fetchChatHistoryFromFirebase();
        fetchStickersFromFirebase();
    }

    private void fetchChatHistoryFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Adjust the reference path to match the new JSON structure
        DatabaseReference messagesRef = database.getReference("sticker-messaging").child("messages");

        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatHistory.clear();
                for (DataSnapshot messageIDSnapshot : dataSnapshot.getChildren()) {
                    Message message = messageIDSnapshot.getValue(Message.class);
                    if (message != null && (message.getSenderID().equals(selectedUser) || message.getReceiverID().equals(selectedUser))) {
                        chatHistory.add(message);
//                        System.out.println("Entered");
                    }
                }

                // Sorting by timestamp
                Collections.sort(chatHistory, (m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));

                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }


    private void fetchStickersFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference stickersRef = database.getReference("sticker-messaging").child("stickers");

        stickersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stickers.clear();
                for (DataSnapshot stickerSnapshot : dataSnapshot.getChildren()) {
                    Sticker sticker = stickerSnapshot.getValue(Sticker.class);
                    if (sticker != null) {
                        stickers.add(sticker);
                    }
                }
                stickerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }

}
