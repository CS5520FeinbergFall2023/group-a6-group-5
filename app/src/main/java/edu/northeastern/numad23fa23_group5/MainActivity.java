package edu.northeastern.numad23fa23_group5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button atYourServiceButton = findViewById(R.id.atYourService);
        atYourServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AtYourServiceActivity.class);
                startActivity(intent);
            }
        });

        Button aboutUsButton = findViewById(R.id.aboutUs);
        aboutUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);

                // Pass your name and email as extras to the AboutMeActivity
                intent.putExtra("name1", "Ajay Inavolu");
                intent.putExtra("email1", "inavolu.a@northeastern.edu");
                intent.putExtra("name2", "Jiaming Xu");
                intent.putExtra("email2", "xu.jiami@northeastern.edu");
                intent.putExtra("name3", "Kiran Shatiya T R");
                intent.putExtra("email3", "thirugnanasambanth.k@northeastern.edu");
                intent.putExtra("name4", "Vishrutha Abbaiah Reddy");
                intent.putExtra("email4", "abbaiahreddy.v@northeastern.edu");

                // Start the AboutMeActivity
                startActivity(intent);
                }
        });
    }
}
