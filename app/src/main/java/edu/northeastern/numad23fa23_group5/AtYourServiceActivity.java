package edu.northeastern.numad23fa23_group5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private ProgressBar progressBar;
    private EditText editTextKeyword;
    private EditText editTextMinPrice;
    private EditText editTextMaxPrice;
    private CheckBox checkBoxIfCache;

    private Handler handler = new Handler();
    private String keyword;
    private String minPrice;
    private String maxPrice;
    private String sortType;
    private String country;
    private boolean ifDisableCache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_you_service);

        tvResults = findViewById(R.id.tvResults);
        editTextKeyword=findViewById(R.id.textInputKeyword);
        editTextMinPrice=findViewById(R.id.minPriceEditText);
        editTextMaxPrice=findViewById(R.id.maxPriceEditText);
        checkBoxIfCache=findViewById(R.id.checkBoxCaching);
        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show loading indicator
                progressBar.setVisibility(View.VISIBLE);
                // Start a new thread for the API call
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Perform API call
                        performSearch();
                    }
                }).start();
            }
        });

        progressBar=findViewById(R.id.progressLoader);

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
        sortType=parent.getItemAtPosition(pos).toString();
        snackBar.show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback.
        // Callback method to be invoked when the selection disappears from this view.
        // The selection can disappear for instance when touch is activated or when the adapter becomes empty.
    }

    private void performSearch()
    {
        country=((ToggleButton)findViewById(R.id.toggleCountry)).isChecked()?"Canada":"USA";
        //get user input
        //keyword
        keyword=editTextKeyword.getText().toString();
        //check if search keyword is empty
        if(keyword.isEmpty()||keyword.isBlank())
        {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    View rootView = findViewById(android.R.id.content);
                    Snackbar.make(rootView, "Search keyword cannot be empty.", Snackbar.LENGTH_SHORT).show();
                }
            });
        }
        //min price
        minPrice=editTextMinPrice.getText().toString();
        //max price
        maxPrice=editTextMaxPrice.getText().toString();
        switch (sortType)
        {
            case "Top Sellers":
                sortType=(country.equals("USA"))?"top_sellers":"priceSaving";
                break;
            case "Price Low to High":
                sortType=(country.equals("USA"))?"price_low_to_high":"price-asc";
                break;
            case "Price High to Low":
                sortType=(country.equals("USA"))?"price_high_to_low":"price-desc";
                break;
            case "Top Rated":
                sortType=(country.equals("USA"))?"top_rated":"reviewAvgRating";
                break;
            case "Relevance":
                sortType=(country.equals("USA"))?"best_match":"relevance";
                break;
        }
        String sortTypeSearchKey=(country.equals("USA"))?"hd_sort":"sort";
        ifDisableCache=checkBoxIfCache.isChecked();
        Log.d("performSearchCountry",country);
        Log.d("performSearchKeyword",keyword);
        Log.d("performSearchMinPrice",minPrice);
        Log.d("performSearchMaxPrice",maxPrice);
        Log.d("performSearchSortType",sortType);
        Log.d("performSearchSortKey",sortTypeSearchKey);
        Log.d("performSearchNoCache", String.valueOf(ifDisableCache));

        try{
            URL url = new URL("https://jsonplaceholder.typicode.com/posts?_delay=5000");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                final String response = stringBuilder.toString();
                // Process the response on the main thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        View rootView = findViewById(android.R.id.content);
                        // Process the response
                        if (response != null) {
                            Snackbar.make(rootView, "Success", Snackbar.LENGTH_SHORT).show();
                            Log.d("performSearchSuccess",response);
                        } else {
                            // Handle null or error response
                            Snackbar.make(rootView, "Fail", Snackbar.LENGTH_SHORT).show();
                            Log.d("performSearchFail",response);
                        }
                        // Hide loading indicator
                        progressBar.setVisibility(View.GONE);
                    }
                });

            } finally {
                urlConnection.disconnect();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

