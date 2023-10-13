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


import com.google.android.material.snackbar.Snackbar;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class AtYourServiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView tvResults;
    private ProgressBar progressBar;
    private EditText editTextKeyword;
    private EditText editTextMinPrice;
    private EditText editTextMaxPrice;
    private CheckBox checkBoxIfCache;

    private ToggleButton toggleButtonCountry;

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
        toggleButtonCountry=findViewById(R.id.toggleCountry);
        progressBar=findViewById(R.id.progressLoader);
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

        //country toggle also changes the currency
        toggleButtonCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current=toggleButtonCountry.isChecked()?"CAD":"USD";
                String minUnit=current+" to";
                ((TextView) findViewById(R.id.minPriceTextView)).setText(minUnit);
                ((TextView) findViewById(R.id.maxPriceTextView)).setText(current);
            }
        });

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
        country=toggleButtonCountry.isChecked()?"Canada":"USA";
        //get user input
        //keyword
        keyword=editTextKeyword.getText().toString();
        //check if search keyword is empty
        if(keyword.isEmpty())
        {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
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

        //USA
        //key          value
        //----------------------
        //q             $keyword
        //engine        home_depot
        //api_key       $your_api_key
        //country       us
        //hd_sort       $sortType
        //lowerbound    $minPrice
        //upperbound    $max_price
        //no_cache      $ifDisableCache (false/true)

        //Canada
        //key          value
        //----------------------
        //q             $keyword
        //engine        home_depot
        //api_key       $your_api_key
        //country       ca
        //sort          $sortType
        //minmax        Example: price:[100 TO 500](Between $100 to $500)
        //              Example: price:[100 TO *](Minimum $100)
        //              Example: price:[0 TO 500](Maximum $500)
        //no_cache      $ifDisableCache (false/true)

        try{

            String api_key="46b4efc39069e71e94b9df0cc639c4fb01951988f2a312425fdaf43cdf1b807d";
            String baseURL="https://serpapi.com/search.json?engine=home_depot";
            String getParams;
            if(country.equals("USA"))
            {
                getParams=String.format("&q=%s&api_key=%s&country=us&hd_sort=%s&no_cache=%b",keyword,api_key,sortType,ifDisableCache);
                if(!minPrice.isEmpty())
                {
                    getParams=getParams+"&lowerbound="+minPrice;
                }
                if(!maxPrice.isEmpty())
                {
                    getParams=getParams+"&upperbound="+maxPrice;
                }
            }
            else{
                getParams=String.format("&q=%s&api_key=%s&country=ca&sort=%s&no_cache=%b",keyword,api_key,sortType,ifDisableCache);
                if(!minPrice.isEmpty()||!maxPrice.isEmpty())
                {
                    minPrice=(minPrice.isEmpty())?"*":minPrice;
                    maxPrice=(maxPrice.isEmpty())?"*":maxPrice;
                    getParams=getParams+String.format("&minmax=[%s TO %s]",minPrice,maxPrice);
                }
            }
            URL url=new URL(baseURL+getParams);
            /// a url that gives feedback in given amount delay time, to test the loading progress bar.
//            url = new URL("https://jsonplaceholder.typicode.com/posts?_delay=5000");
            Log.d("performSearchHomeDepotURL",url.toString());
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
                Log.d("performSearchResponse",response);
//                JSONObject jsonObjectResponse=new JSONObject(response);
//                Log.d("performSearchProductsPosition ", jsonObjectResponse.getJSONArray("products").toString());
//                JSONArray resultProducts=jsonObjectResponse.getJSONArray("products");
//                for (int i = 0; i < resultProducts.length(); i++) {
//                    Log.d("performSearchProductTitle",resultProducts.getJSONObject(i).getString("title"));
//                }
                // Process the response on the main UI thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        View rootView = findViewById(android.R.id.content);
                        // Process the response
                        // TODO: API error message format
                        if (response != null) {
                            Snackbar.make(rootView, "Success", Snackbar.LENGTH_SHORT).show();
                            Log.d("performSearchSuccess",response);

                        } else {
                            // Handle null or error response
                            Snackbar.make(rootView, "Fail", Snackbar.LENGTH_SHORT).show();
                            Log.d("performSearchFail",response);
                            return;
                        }
                        // Hide loading indicator
                        progressBar.setVisibility(View.GONE);
                    }
                });
                // start a new activity to show the search result
                Intent intent = new Intent(AtYourServiceActivity.this, SearchResultActivity.class);
                // pass the response to the new activity
                intent.putExtra("responseKey", response);
                startActivity(intent);
            } finally {
                urlConnection.disconnect();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    View rootView = findViewById(android.R.id.content);
                    // Handle exception
                    Snackbar.make(rootView, "Fail", Snackbar.LENGTH_SHORT).show();
                    Log.d("performSearchException",e.toString());
                    // Hide loading indicator
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }
}

