package edu.northeastern.numad23fa23_group5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;
import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    private ArrayList<ItemCard> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ReviewAdapter rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;

    private static final String KEY_ITEM_LIST = "KEY_ITEM_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        init(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("responseKey")) {
            String responseData = intent.getStringExtra("responseKey");
            Log.d("SearchResultActivity", responseData);
            try {
                JSONObject jsonObjectResponse = new JSONObject(responseData);
                JSONArray resultProducts = jsonObjectResponse.getJSONArray("products");
                for (int i = 0; i < resultProducts.length(); i++) {
                    JSONArray thumbnails = resultProducts.getJSONObject(i).getJSONArray("thumbnails").getJSONArray(0);
                    String thumbnailURL = thumbnails.getString(0);
                    String imageURL = thumbnails.getString(thumbnails.length() - 1);
                    String title = resultProducts.getJSONObject(i).getString("title");
                    String brand = resultProducts.getJSONObject(i).getString("brand");
                    String price = resultProducts.getJSONObject(i).getString("price");
                    float ratings = Float.parseFloat(resultProducts.getJSONObject(i).getString("rating"));
                    long reviews = Long.parseLong(resultProducts.getJSONObject(i).getString("reviews"));

                    int position = 0;
                    itemList.add(position, new ItemCard(thumbnailURL, thumbnailURL, title, brand, price, ratings,reviews));
                    rviewAdapter.notifyItemInserted(position);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_ITEM_LIST, itemList);
    }

    private void init(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_ITEM_LIST)) {
            itemList = savedInstanceState.getParcelableArrayList(KEY_ITEM_LIST);
        }
        createRecyclerView();
    }

    private void createRecyclerView() {
        rLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        rviewAdapter = new ReviewAdapter(itemList);
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onItemLongClick(int position, Context context) {
                Log.d("SearchResultActivity", position + " clicked");
            }
        };
        rviewAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }
}
