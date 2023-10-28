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
import java.util.List;

public class UserChatActivity extends AppCompatActivity {

    private RecyclerView recyclerViewStickers;
    private List<Sticker> stickers = new ArrayList<>();
    private StickerAdapter stickerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        // Set up stickers RecyclerView
        recyclerViewStickers = findViewById(R.id.recycler_view_stickers);
        recyclerViewStickers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        stickerAdapter = new StickerAdapter(this, stickers);
        recyclerViewStickers.setAdapter(stickerAdapter);

        // Fetch stickers from Firebase
        fetchStickersFromFirebase();
    }

    private void fetchStickersFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference stickersRef = database.getReference().child("stickers");

        stickersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stickers.clear();
                for (DataSnapshot stickerSnapshot : dataSnapshot.getChildren()) {
                    Sticker sticker = stickerSnapshot.getValue(Sticker.class);
                    stickers.add(sticker);
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
