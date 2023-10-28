package edu.northeastern.numad23fa23_group5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import edu.northeastern.numad23fa23_group5.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button atYourServiceButton = findViewById(R.id.atYourService);
        atYourServiceButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AtYourServiceActivity.class);
            startActivity(intent);
        });

        Button aboutUsButton = findViewById(R.id.aboutUs);
        aboutUsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            intent.putExtra("name1", "Ajay Inavolu");
            intent.putExtra("email1", "inavolu.a@northeastern.edu");
            intent.putExtra("name2", "Jiaming Xu");
            intent.putExtra("email2", "xu.jiami@northeastern.edu");
            intent.putExtra("name3", "Kiran Shatiya T R");
            intent.putExtra("email3", "thirugnanasambanth.k@northeastern.edu");
            intent.putExtra("name4", "Vishrutha Abbaiah Reddy");
            intent.putExtra("email4", "abbaiahreddy.v@northeastern.edu");
            startActivity(intent);
        });

        final EditText usernameEditText = findViewById(R.id.username);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            if (!username.isEmpty()) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("users");  // This references the 'users' node directly at the root
                Query query = usersRef.child(username);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                        if (!snapshot.exists()) {
                            // If user does not exist, create a new user in the Firebase database
                            DatabaseReference newUserRef = usersRef.child(username);
                            newUserRef.setValue(new User(username));
                        }
                        intent.putExtra("username", username); // Pass the username to the ChatActivity
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("UserFirebase", "Error occurred when checking user.");
                    }
                });
            }
        });
    }
}
