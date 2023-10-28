package edu.northeastern.numad23fa23_group5;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.northeastern.numad23fa23_group5.databinding.ActivityPersonalInfoBinding;


public class ChatActivity extends AppCompatActivity {
    private ActivityPersonalInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        BottomNavigationView bottomNavigationView=findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_chat);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_chat:
                        return true;
                    case R.id.navigation_personal:
                        startActivity(new Intent(getApplicationContext(),PersonalInfoActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        FloatingActionButton fabShowUsers = findViewById(R.id.fabUserList);
        fabShowUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the activity or fragment showing the list of users
                Intent intent = new Intent(ChatActivity.this, UsersListActivity.class);
                startActivity(intent);
            }
        });


    }
}
