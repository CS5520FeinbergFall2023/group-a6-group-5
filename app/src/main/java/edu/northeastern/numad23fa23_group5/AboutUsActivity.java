package edu.northeastern.numad23fa23_group5;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutUsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        // Retrieve the name and email extras from the Intent
        String name1 = getIntent().getStringExtra("name1");
        String email1 = getIntent().getStringExtra("email1");
        String name2 = getIntent().getStringExtra("name2");
        String email2 = getIntent().getStringExtra("email2");
        String name3 = getIntent().getStringExtra("name3");
        String email3 = getIntent().getStringExtra("email3");
        String name4 = getIntent().getStringExtra("name4");
        String email4 = getIntent().getStringExtra("email4");

        // Find the TextViews in your layout
        TextView nameTextView1 = findViewById(R.id.nameTextView1);
        TextView emailTextView1 = findViewById(R.id.emailTextView1);
        TextView nameTextView2 = findViewById(R.id.nameTextView2);
        TextView emailTextView2 = findViewById(R.id.emailTextView2);
        TextView nameTextView3 = findViewById(R.id.nameTextView3);
        TextView emailTextView3 = findViewById(R.id.emailTextView3);
        TextView nameTextView4 = findViewById(R.id.nameTextView4);
        TextView emailTextView4 = findViewById(R.id.emailTextView4);

        // Set the text of the TextViews with your name and email
        nameTextView1.setText(name1);
        emailTextView1.setText(email1);
        nameTextView2.setText(name2);
        emailTextView2.setText(email2);
        nameTextView3.setText(name3);
        emailTextView3.setText(email3);
        nameTextView4.setText(name4);
        emailTextView4.setText(email4);
    }
}
