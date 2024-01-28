package com.example.helpinghands;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.media.session.MediaSessionManager;
import android.os.Bundle;
import android.util.Log;
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

public class clothesavailable extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<booksinfo> userArrayList;
    MyAdapter1 myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothesavailable);
        //   ProgressDialog progressDialog = new ProgressDialog(this);
        //  progressDialog.setCancelable(false);
        //  progressDialog.setMessage("Fetching Data");
        // progressDialog.show();
        recyclerView = findViewById(R.id.recycleView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<booksinfo>();
        myAdapter = new MyAdapter1(clothesavailable.this,userArrayList);
        recyclerView.setAdapter(myAdapter);
        EventChangeListener();

    }
    private void EventChangeListener(){
        db.collection("clothesinfo").orderBy("mobile", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                        // userArrayList.add(dc.getDocument().toObject(booksinfo.class));
                        Toast.makeText(clothesavailable.this,address, Toast.LENGTH_SHORT).show();
                        userArrayList.add(data1);
                        //String address=dc.getDocument().getString("address");
                        //Toast.makeText(booksavailable.this,address, Toast.LENGTH_SHORT).show();
                    }

                    myAdapter.notifyDataSetChanged();
                    // if(progressDialog.isShowing())
                    //  progressDialog.dismiss();
                }
            }
        });
    }
}