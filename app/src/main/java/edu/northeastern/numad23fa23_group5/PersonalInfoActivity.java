package edu.northeastern.numad23fa23_group5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.northeastern.numad23fa23_group5.databinding.ActivityPersonalInfoBinding;

public class PersonalInfoActivity extends AppCompatActivity {
    private ArrayList<StickerHistoryItemCard> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StickerHistoryAdapter stickerHistoryAdapter;
    private RecyclerView.LayoutManager rLayoutManger;

    private static final String KEY_ITEM_LIST = "KEY_ITEM_LIST";

    private void init(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_ITEM_LIST)) {
            itemList = savedInstanceState.getParcelableArrayList(KEY_ITEM_LIST);
        }
        createRecyclerView();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_ITEM_LIST, itemList);
    }

    private void createRecyclerView() {
        rLayoutManger = new GridLayoutManager(this, 2);
        recyclerView = findViewById(R.id.recyclerViewStickersSent);
        recyclerView.setHasFixedSize(true);
        stickerHistoryAdapter = new StickerHistoryAdapter(this,itemList);
//        ItemClickListener itemClickListener = new ItemClickListener() {
//            @Override
//            public void onItemLongClick(int position, Context context) {
//
//            }
//        };
//        stickerHistoryAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(stickerHistoryAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        init(savedInstanceState);

        Intent intent = getIntent();
        final String userID= intent!=null&&intent.hasExtra("userID")?intent.getStringExtra("userID"):"defaultUserID";
        final String username= intent!=null&&intent.hasExtra("username")?intent.getStringExtra("username"):"defaultUsername";
        TextView welcome=findViewById(R.id.text_view_welcome);
        welcome.setText("Welcome! "+username);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference().child("sticker-messaging");
        //sticker ID -> send count of this user
        Map<String, Integer> stickerUsageCounts = new HashMap<>();
        // find messages sent by this user
        databaseRef.child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String senderID = messageSnapshot.child("senderID").getValue(String.class);
                    // Check if the message was sent by the target user
                    if (senderID != null && senderID.equals(userID)) {
                        String stickerID = messageSnapshot.child("stickerID").getValue(String.class);
                        if (stickerID != null) {
                            stickerUsageCounts.put(stickerID, stickerUsageCounts.getOrDefault(stickerID, 0) + 1);
                        }
                    }
                }

                // sticker information
                for (String stickerID : stickerUsageCounts.keySet()) {
                    databaseRef.child("stickers").child(stickerID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String stickerImageURL = dataSnapshot.child("image").getValue(String.class);
                            String stickerName = dataSnapshot.child("name").getValue(String.class);
                            float stickerPrice = dataSnapshot.child("price").getValue(Float.class);
                            int useCount=stickerUsageCounts.get(stickerID);
                            itemList.add(new StickerHistoryItemCard(stickerImageURL,stickerPrice,stickerName,useCount));
                            stickerHistoryAdapter.notifyItemInserted(itemList.size()-1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle error
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });



//
//        String testData="{\n" +
//                "  \"result\": [\n" +
//                "    {\n" +
//                "      \"stickerName\": \"name1\",\n" +
//                "      \"stickerPrice\": 10,\n" +
//                "      \"stickerImageURL\": \"test\",\n" +
//                "      \"useCount\": 5,\n" +
//                "      \"userID\": 999\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"stickerName\": \"name2\",\n" +
//                "      \"stickerPrice\": 0.99,\n" +
//                "      \"stickerImageURL\": \"test\",\n" +
//                "      \"useCount\": 10,\n" +
//                "      \"userID\": 999\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"stickerName\": \"name3\",\n" +
//                "      \"stickerPrice\": 2,\n" +
//                "      \"stickerImageURL\": \"test\",\n" +
//                "      \"useCount\": 1,\n" +
//                "      \"userID\": 999\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//        try {
//            JSONObject sampleJsonObject = new JSONObject(testData);
//            JSONArray sampleArray = sampleJsonObject.getJSONArray("result");
//            for (int i=0;i<sampleArray.length();i++)
//            {
//                String stickerName=sampleArray.getJSONObject(i).getString("stickerName");
//                float stickerPrice=Float.parseFloat(sampleArray.getJSONObject(i).getString("stickerPrice"));
//                int useCount=sampleArray.getJSONObject(i).getInt("useCount");
//                String stickerImageURL=sampleArray.getJSONObject(i).getString("stickerImageURL");
//                itemList.add(new StickerHistoryItemCard(stickerImageURL,stickerPrice,stickerName,useCount));
//                stickerHistoryAdapter.notifyItemInserted(itemList.size()-1);
//            }
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        // Perform item selected listener
        BottomNavigationView bottomNavigationView=findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_personal);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.navigation_home:
                        //take as logging out when navigate back to home
                        //don't pass down username and ID
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_chat:
                        //pass down username and userID
                        Intent intent=new Intent(getApplicationContext(),ChatActivity.class);
                        intent.putExtra("userID", userID);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_personal:
                        return true;
                }
                return false;
            }
        });


    }

}