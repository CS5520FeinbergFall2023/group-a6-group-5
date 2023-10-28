package edu.northeastern.numad23fa23_group5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
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
        final EditText usernameEditText = findViewById(R.id.username);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                if(!username.isEmpty())
                {
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    //add to database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference usersRef = database.getReference().child("sticker-messaging").child("users");
                    // Check if the username already exists
                    Query query = usersRef.orderByChild("username").equalTo(username);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Log.e("UserFirebase",username+" exists");
                                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                    String userID = userSnapshot.getKey();
                                    intent.putExtra("userID", userID);
                                }
                            } else {
                                // Username does not exist, create a new user
                                DatabaseReference newUserRef = usersRef.push();
                                String userId = newUserRef.getKey();
                                //manually set fields
//                                newUserRef.child("username").setValue(username);
                                //or
                                newUserRef.setValue(new User(username));
                                intent.putExtra("userID", userId);
                                Log.e("UserFirebase",userId+" "+username);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("UserFirebase","error occurred when creating new user.");
                        }
                    });
                intent.putExtra("userID", username);
                startActivity(intent);
                }
                else{
                    Snackbar.make(findViewById(R.id.main_view), "Username can't be empty", Snackbar.LENGTH_LONG).show();
                }
            }
        });


    }
}
