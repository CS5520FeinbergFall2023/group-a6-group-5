package edu.northeastern.numad23fa23_group5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    private ArrayList<ItemCard> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ReviewAdapter rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //get the http response from the AtYourServiceActivity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("responseKey")) {
            String responseData = intent.getStringExtra("responseKey");
            Log.d("SearchResultActivity",responseData);
            try {
                JSONObject jsonObjectResponse = new JSONObject(responseData);
                Log.d("SearchResultActivity ", jsonObjectResponse.getJSONArray("products").toString());
                JSONArray resultProducts = jsonObjectResponse.getJSONArray("products");
                for (int i = 0; i < resultProducts.length(); i++) {
                    JSONArray thumbnails=resultProducts.getJSONObject(i).getJSONArray("thumbnails");
                    String thumbnailURL=thumbnails.getString(0);
                    String imageURL=thumbnails.getString(thumbnails.length()-1);
                    String title=resultProducts.getJSONObject(i).getString("title");
                    String brand=resultProducts.getJSONObject(i).getString("brand");
                    String price=resultProducts.getJSONObject(i).getString("price");
                    float ratings=Float.parseFloat(resultProducts.getJSONObject(i).getString("rating"));

                    Log.d("SearchResultActivity", thumbnailURL);
                    Log.d("SearchResultActivity", thumbnailURL);
                    Log.d("SearchResultActivity", title);
                    Log.d("SearchResultActivity", brand);
                    Log.d("SearchResultActivity", price);
                    Log.d("SearchResultActivity", ratings+"");

                    int position=0;
                    itemList.add(position, new ItemCard(thumbnailURL,thumbnailURL,title,brand,price,ratings));
                    rviewAdapter.notifyItemInserted(position);
                }
            }
            catch (Exception e)
            {
                //TODO: alert user of exception
                e.printStackTrace();
            }
        }

    }

    private void createRecyclerView() {
        rLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        rviewAdapter = new ReviewAdapter(itemList);
        ItemClickListener itemClickListener = new ItemClickListener()
        {
            @Override
            public void onItemLongClick(int position, Context context) {
                Log.d("SearchResultActivity",position+" clicked");
            }
        };
        rviewAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }
}