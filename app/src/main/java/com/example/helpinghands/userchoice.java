package com.example.helpinghands;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class userchoice extends AppCompatActivity {
    Button logout_button,books_button,clothes_button;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userchoice);
        auth = FirebaseAuth.getInstance();
        logout_button=(Button) findViewById(R.id.logout);
        books_button=(Button) findViewById(R.id.Books);
        clothes_button=(Button) findViewById(R.id.Clothes);

        books_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), bookschoice.class);
                startActivity(intent);
            }
        });
        clothes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), clotheschoice.class);
                startActivity(intent);
            }
        });
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Toast.makeText(userchoice.this, "Successfully LoggedOut", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(new Intent(userchoice.this, login.class));

                finish();
            }
        });
    }
}