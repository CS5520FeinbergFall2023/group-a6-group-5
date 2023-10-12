package edu.northeastern.numad23fa23_group5;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import network.ApiClient;
import network.ApiResponse;
import network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtYourServiceActivity extends AppCompatActivity {

    private TextView tvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_you_service);

        tvResults = findViewById(R.id.tvResults);
        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(v -> performSearch());
    }

    private void performSearch() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Map<String, String> parameter = new HashMap<>();
        parameter.put("engine", "home_depot");
        parameter.put("q", "chair");
        parameter.put("api_key", "46b4efc39069e71e94b9df0cc639c4fb01951988f2a312425fdaf43cdf1b807d");

        Log.d("AtYourServiceActivity", "About to hit the API...");
        Call<ApiResponse> call = apiService.searchGoogle(parameter);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d("AtYourServiceActivity", "API responded with: " + response.code() + " " + response.message());
                Log.d("API_RESPONSE", "Response: " + response.body());
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    // process the apiResponse and update UI
                    tvResults.setText(apiResponse.getResult());
                } else {
                    tvResults.setText("Error occurred: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("AtYourServiceActivity", "API call failed with: " + t.getMessage(), t);
                tvResults.setText("Failure: " + t.getMessage());
            }
        });
    }
}

