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
import java.util.concurrent.atomic.AtomicInteger;


public class UserChatActivity extends AppCompatActivity {

    private RecyclerView recyclerViewChat;
    private RecyclerView recyclerViewStickers;
    private List<Message> chatHistory = new ArrayList<>();
    private List<Sticker> stickers = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private StickerAdapter stickerAdapter;
    private String selectedUserID;  // Renamed from selectedUser
    private String loggedInUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        loggedInUserID = getIntent().getStringExtra("loggedInUserID");
        selectedUserID = getIntent().getStringExtra("selectedUserID");
        System.out.println(selectedUserID);

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
        DatabaseReference messagesRef = database.getReference("sticker-messaging").child("messages");

        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatHistory.clear();
                AtomicInteger remainingCalls = new AtomicInteger((int) dataSnapshot.getChildrenCount());  // Count of remaining username fetch operations

                for (DataSnapshot messageIDSnapshot : dataSnapshot.getChildren()) {
                    Message message = messageIDSnapshot.getValue(Message.class);
                    if (message != null &&
                            ((message.getSenderID().equals(loggedInUserID) && message.getReceiverID().equals(selectedUserID)) ||
                                    (message.getSenderID().equals(selectedUserID) && message.getReceiverID().equals(loggedInUserID)))) {
                        chatHistory.add(message);
                        fetchAndSetUserName(message, () -> {
                            if (remainingCalls.decrementAndGet() == 0) {
                                // Sorting by timestamp
                                Collections.sort(chatHistory, (m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));
                                chatAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }

    // Method to fetch the username and set it in the Message object
    private void fetchAndSetUserName(Message message, Runnable onComplete) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("sticker-messaging").child("users").child(message.getSenderID());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("username").getValue(String.class);
                message.setSenderUsername(username);  // Set the username in the Message object
                onComplete.run();  // Notify that this operation is complete
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
                onComplete.run();  // Still notify that this operation is complete to not block other operations
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
