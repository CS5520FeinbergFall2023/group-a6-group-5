package edu.northeastern.a6_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SearchActivity extends AppCompatActivity {
    private final Executor backgroundExecutor = Executors.newSingleThreadExecutor();
    private TextInputEditText edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edtSearch = findViewById(R.id.edtSearch);
        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = edtSearch.getText().toString().trim();
                if (!query.isEmpty()) {
                    searchUsingSerpAPI(query);
                } else {
                    Toast.makeText(SearchActivity.this, "Please enter a search term", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void searchUsingSerpAPI(String searchTerm) {
        new Thread(() -> {
            String apiKey = "46b4efc39069e71e94b9df0cc639c4fb01951988f2a312425fdaf43cdf1b807d";
            String apiUrl = "https://api.serpapi.com/search?q=" + searchTerm + "&engine=home_depot&country=us&api_key=" + apiKey;
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                Log.d("AtYourServiceActivity", result.toString());
                handleAPIResponse(result.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void handleAPIResponse(String result) {
        runOnUiThread(() -> {
            Toast.makeText(SearchActivity.this, "Data fetched successfully!",
                    Toast.LENGTH_SHORT).show();
        });
    }


    private void showError(String errorMessage) {
        runOnUiThread(() -> Toast.makeText(SearchActivity.this, errorMessage, Toast.LENGTH_SHORT).show());
    }

}