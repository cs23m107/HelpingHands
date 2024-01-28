package com.example.helpinghands;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;
    ArrayList<booksinfo> userArrayList;

    public MyAdapter(Context context, ArrayList<booksinfo> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        booksinfo user=userArrayList.get(position);
        holder.address.setText(user.address);
        holder.mobile.setText(user.mobile);
        Glide.with(context).load(userArrayList.get(position).getBooksimage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return userArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView address,mobile;
        ImageView imageView;
        //public  MyViewHolder(@NonNull View )
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.booksaddress1);
            mobile = itemView.findViewById(R.id.booksmobile1);
            imageView = itemView.findViewById(R.id.booksimage1);
        }
    }
}
