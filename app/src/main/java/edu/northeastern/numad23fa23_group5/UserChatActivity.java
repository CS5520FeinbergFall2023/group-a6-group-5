package edu.northeastern.numad23fa23_group5;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;


public class UserChatActivity extends AppCompatActivity implements StickerAdapter.MessageSender {

    private RecyclerView recyclerViewChat;
    private RecyclerView recyclerViewStickers;
    private List<Message> chatHistory = new ArrayList<>();
    private List<Sticker> stickers = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private StickerAdapter stickerAdapter;
    private String selectedUserID;  // Renamed from selectedUser
    private String loggedInUserID;

    private Sticker selectedSticker;

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

        stickerAdapter = new StickerAdapter(this, stickers, this);
        recyclerViewStickers.setAdapter(stickerAdapter);

        Button sendButton = findViewById(R.id.btn_send_sticker);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the sendMessage method when the send button is clicked
                sendMessage(selectedSticker != null ? selectedSticker.getId().toString() : null);
            }
        });

        selectedSticker = null;

        fetchChatHistoryFromFirebase();
        fetchStickersFromFirebase();

        Log.d("UserChatActivity", "selectedUserID: " + selectedUserID);
        Log.d("UserChatActivity", "loggedInUserID: " + loggedInUserID);

    }

    private void fetchChatHistoryFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference messagesRef = database.getReference("sticker-messaging").child("messages");

        messagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                Message message = dataSnapshot.getValue(Message.class);
                if (message != null &&
                        ((message.getSenderID().equals(loggedInUserID) && message.getReceiverID().equals(selectedUserID)) ||
                                (message.getSenderID().equals(selectedUserID) && message.getReceiverID().equals(loggedInUserID)))) {
                    chatHistory.add(message);
                    fetchAndSetUserName(message, () -> {
                        // Sorting by timestamp
                        Collections.sort(chatHistory, (m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));
                        chatAdapter.notifyDataSetChanged();
                    });
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                // Handle changes to existing messages (if needed)
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // Handle message removal (if needed)
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                // Handle message movement (if needed)
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

    @Override
    public void sendMessage(String selectedStickerID) {
        // Check if a sticker is selected
        if (selectedStickerID != null) {
            // Create a Message object with the selected sticker ID and send it in the chat
            String timestamp = getCurrentTimestamp();
            Message message = new Message(loggedInUserID, selectedUserID, selectedStickerID, timestamp);

            DatabaseReference messagesRef = FirebaseDatabase.getInstance()
                    .getReference("sticker-messaging")
                    .child("messages");

            // Generate a unique message ID
            String messageId = messagesRef.push().getKey();
            if (messageId != null) {
                // Set the message in the database
                messagesRef.child(messageId).setValue(message)
                        .addOnSuccessListener(aVoid -> {
                            // Message sent successfully, you can update the UI or handle success here
                        })
                        .addOnFailureListener(e -> {
                            // Handle the error
                            Log.e("UserChatActivity", "Failed to send message: " + e.getMessage());
                        });
            }
        }
    }


    // Implement the getCurrentTimestamp method as follows
    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date();
        return sdf.format(currentDate);
    }

}
