package edu.northeastern.numad23fa23_group5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //get the http response from the AtYourServiceActivity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("responseKey")) {
            String responseData = intent.getStringExtra("responseKey");
            Log.d("SearchResultActivity",responseData);
        }

    }
}