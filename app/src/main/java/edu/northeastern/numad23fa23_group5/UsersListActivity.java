package edu.northeastern.numad23fa23_group5;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
import java.util.List;

public class UsersListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private UsersAdapter usersAdapter;
    private List<String> userIDs = new ArrayList<>();
    private List<String> usernames = new ArrayList<>();
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        recyclerViewUsers = findViewById(R.id.recycler_view_users);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter = new UsersAdapter(usernames);
        currentUserId = getIntent().getStringExtra("currentUserId");


        // Set the item click listener
        usersAdapter.setOnItemClickListener(new UsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String selectedUserID = userIDs.get(position);
//                System.out.println(selectedUserID);
                openChatWithUser(selectedUserID);
            }
        });

        recyclerViewUsers.setAdapter(usersAdapter);
        fetchUsersFromFirebase();
    }

    private void fetchUsersFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("sticker-messaging").child("users");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userIDs.clear();
                usernames.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    userIDs.add(userSnapshot.getKey());
                    usernames.add(userSnapshot.child("username").getValue(String.class));
                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }

    // Method to open the chat interface for the selected user using their userID
    private void openChatWithUser(String selectedUserID) {
        Intent intent = new Intent(UsersListActivity.this, UserChatActivity.class);
        intent.putExtra("selectedUserID", selectedUserID);
        intent.putExtra("loggedInUserID", currentUserId);  // Pass the logged-in user's ID
        startActivity(intent);
    }

}
