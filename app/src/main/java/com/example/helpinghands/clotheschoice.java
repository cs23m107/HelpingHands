package com.example.helpinghands;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class clotheschoice extends AppCompatActivity {
    Button logout_button,home_button,donate_button,available_button;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clotheschoice);
        available_button=(Button) findViewById(R.id.Available);
        donate_button=(Button) findViewById(R.id.Donate); // Here created books_button for camera:
        donate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), donateclothes.class);
                startActivity(intent);
            }
        });
        home_button=(Button) findViewById(R.id.Home);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), userchoice.class);
                startActivity(intent);
            }
        });
        available_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),clothesavailable.class);
                startActivity(intent);
            }
        });
        auth = FirebaseAuth.getInstance();
        logout_button = (Button) findViewById(R.id.logout);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Toast.makeText(clotheschoice.this, "Successfully LoggedOut", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(new Intent(clotheschoice.this, login.class));
                finish();
            }
        });
    }
}