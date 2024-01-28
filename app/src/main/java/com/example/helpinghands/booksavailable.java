package com.example.helpinghands;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Queue;

public class booksavailable extends AppCompatActivity {

    RecyclerView recyclerView;
   // TextView phoneNo;
    ArrayList<booksinfo> userArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
   ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booksavailable);
     //   ProgressDialog progressDialog = new ProgressDialog(this);
      //  progressDialog.setCancelable(false);
      //  progressDialog.setMessage("Fetching Data");
       // progressDialog.show();
      //  phoneNo = findViewById(R.id.booksmobile1);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<booksinfo>();
        myAdapter = new MyAdapter(booksavailable.this,userArrayList);
        recyclerView.setAdapter(myAdapter);
        EventChangeListener();
        //phoneNo.setText("089");

    }
    private void EventChangeListener(){
        db.collection("booksinfo").orderBy("mobile", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error!=null){
                    //if(progressDialog.isShowing())
                       // progressDialog.dismiss();
                    Log.e("Firestore Error",error.getMessage());
                    return;
                }
                for(DocumentChange dc:value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        booksinfo data1=new booksinfo();
                        String address=dc.getDocument().getString("Address");
                        String mobile=dc.getDocument().getString("mobile");
                        String photo=dc.getDocument().getString("Photo");

                        data1.setAddress(address);
                        data1.setMobile(mobile);
                        data1.setBooksimage(photo);
                       // phoneNo.setText(mobile);
                       // userArrayList.add(dc.getDocument().toObject(booksinfo.class));
                        Toast.makeText(booksavailable.this,address, Toast.LENGTH_SHORT).show();
                        userArrayList.add(data1);
                        //String address=dc.getDocument().getString("address");
                        //Toast.makeText(booksavailable.this,address, Toast.LENGTH_SHORT).show();
                    }

                    myAdapter.notifyDataSetChanged();
                  /*  if(phoneNo != null) {
                        Toast.makeText(booksavailable.this,"not null",Toast.LENGTH_SHORT).show();

                        phoneNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String phoneno = phoneNo.getText().toString();
                                Intent i = new Intent(Intent.ACTION_CALL);
                                i.setData(Uri.parse("tel:" + phoneno));
                                startActivity(i);
                            }
                        });
                    }
                    else Toast.makeText(booksavailable.this,"unable to call",Toast.LENGTH_SHORT).show();*/
                   // if(progressDialog.isShowing())
                      //  progressDialog.dismiss();
                }
            }
        });
    }



}