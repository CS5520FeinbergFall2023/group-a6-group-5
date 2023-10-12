package edu.northeastern.numad23fa23_group5;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import network.ApiClient;
import network.ApiResponse;
import network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtYourServiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView tvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_you_service);

        tvResults = findViewById(R.id.tvResults);
        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(v -> performSearch());

        //the dropdown for the sorting methods
        Spinner spinner = (Spinner) findViewById(R.id.spinnerSorting);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.sortings_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item is selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos).
        Snackbar snackBar = Snackbar.make(view, "Item selected from dropdown: "+parent.getItemAtPosition(pos), Snackbar.LENGTH_SHORT);
        snackBar.show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback.
        // Callback method to be invoked when the selection disappears from this view.
        // The selection can disappear for instance when touch is activated or when the adapter becomes empty.
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

