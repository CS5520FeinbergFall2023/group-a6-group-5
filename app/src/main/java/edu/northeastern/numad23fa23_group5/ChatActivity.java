package edu.northeastern.numad23fa23_group5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatActivity extends AppCompatActivity {

    private List<Pair<String, String>> chatUsersList = new ArrayList<>(); // Pair<UserId, Username>
    private ChatHistoryAdapter chatHistoryAdapter;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_chat);

        Intent intent = getIntent();
        currentUserId = intent != null && intent.hasExtra("userID") ? intent.getStringExtra("userID") : "defaultUserID";

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_chat:
                    return true;
                case R.id.navigation_personal:
                    Intent personalIntent = new Intent(getApplicationContext(), PersonalInfoActivity.class);
                    personalIntent.putExtra("userID", currentUserId);
                    startActivity(personalIntent);
                    return true;
            }
            return false;
        });

        FloatingActionButton fabShowUsers = findViewById(R.id.fabUserList);
        fabShowUsers.setOnClickListener(v -> {
            Intent usersIntent = new Intent(ChatActivity.this, UsersListActivity.class);
            usersIntent.putExtra("currentUserId", currentUserId);  // Pass the current logged-in user's ID
            startActivity(usersIntent);
        });


        // Set up chat history RecyclerView
        RecyclerView chatHistoryRecyclerView = findViewById(R.id.chatHistory);
        chatHistoryAdapter = new ChatHistoryAdapter(this, chatUsersList, position -> {
            String selectedUserId = chatUsersList.get(position).first;
            Intent chatIntent = new Intent(ChatActivity.this, UserChatActivity.class);
            chatIntent.putExtra("selectedUserID", selectedUserId);
            startActivity(chatIntent);
        });

        chatHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatHistoryRecyclerView.setAdapter(chatHistoryAdapter);

        fetchChatHistory();
    }

    private void fetchChatHistory() {
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("sticker-messaging").child("messages");

        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> chatUsersSet = new HashSet<>();

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);

                    if (message != null) {
                        if (currentUserId.equals(message.getSenderID())) {
                            chatUsersSet.add(message.getReceiverID());
                        } else if (currentUserId.equals(message.getReceiverID())) {
                            chatUsersSet.add(message.getSenderID());
                        }
                    }
                }

                fetchUsernamesFromUserIds(new ArrayList<>(chatUsersSet));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }

    private void fetchUsernamesFromUserIds(List<String> userIds) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("sticker-messaging").child("users");
        chatUsersList.clear();

        for (String userId : userIds) {
            usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String username = dataSnapshot.child("username").getValue(String.class);
                    if (username != null) {
                        chatUsersList.add(new Pair<>(userId, username));
                        chatHistoryAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }

}
