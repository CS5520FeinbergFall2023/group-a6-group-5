package edu.northeastern.numad23fa23_group5;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserChatActivity extends AppCompatActivity {

    private RecyclerView recyclerViewChat;
    private String selectedUser; // the user with whom you are chatting

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        selectedUser = getIntent().getStringExtra("selectedUser"); // get the username from intent

        recyclerViewChat = findViewById(R.id.recycler_view_chat);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));

        // Initialize your chat RecyclerView adapter and set it to the RecyclerView

        ImageButton sticker1 = findViewById(R.id.sticker1);
        sticker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sticker selection and sending logic here
            }
        });

        // Similarly, set onClickListeners for other stickers...
    }

    // Implement methods to fetch chat history, send stickers, and update the chat in real-time.
}
