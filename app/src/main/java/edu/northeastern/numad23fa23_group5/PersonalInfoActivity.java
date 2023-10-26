package edu.northeastern.numad23fa23_group5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        stickerHistoryAdapter = new StickerHistoryAdapter(itemList);
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

        //todo:should be getting actual data from database
        //        Intent intent = getIntent();

        String testData="{\n" +
                "  \"result\": [\n" +
                "    {\n" +
                "      \"stickerName\": \"name1\",\n" +
                "      \"stickerPrice\": 10,\n" +
                "      \"stickerImageURL\": \"test\",\n" +
                "      \"useCount\": 5,\n" +
                "      \"userID\": 999\n" +
                "    },\n" +
                "    {\n" +
                "      \"stickerName\": \"name2\",\n" +
                "      \"stickerPrice\": 0.99,\n" +
                "      \"stickerImageURL\": \"test\",\n" +
                "      \"useCount\": 10,\n" +
                "      \"userID\": 999\n" +
                "    },\n" +
                "    {\n" +
                "      \"stickerName\": \"name3\",\n" +
                "      \"stickerPrice\": 2,\n" +
                "      \"stickerImageURL\": \"test\",\n" +
                "      \"useCount\": 1,\n" +
                "      \"userID\": 999\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            JSONObject sampleJsonObject = new JSONObject(testData);
            JSONArray sampleArray = sampleJsonObject.getJSONArray("result");
            for (int i=0;i<sampleArray.length();i++)
            {
                String stickerName=sampleArray.getJSONObject(i).getString("stickerName");
                float stickerPrice=Float.parseFloat(sampleArray.getJSONObject(i).getString("stickerPrice"));
                int useCount=sampleArray.getJSONObject(i).getInt("useCount");
                String stickerImageURL=sampleArray.getJSONObject(i).getString("stickerImageURL");
                String userID=sampleArray.getJSONObject(i).getString("userID");
                itemList.add(new StickerHistoryItemCard(stickerImageURL,stickerPrice,stickerName,useCount));
                stickerHistoryAdapter.notifyItemInserted(itemList.size()-1);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        // Perform item selected listener
        BottomNavigationView bottomNavigationView=findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_personal);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_chat:
                        startActivity(new Intent(getApplicationContext(),ChatActivity.class));
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