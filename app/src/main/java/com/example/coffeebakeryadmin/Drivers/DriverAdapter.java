package com.example.coffeebakeryadmin.Drivers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeebakeryadmin.List_Post.PostAdapter;
import com.example.coffeebakeryadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.Holder> {
    private List mDriver;
    private Context mContext;
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public DriverAdapter(List Driver, Context context){
        this.mDriver = Driver;
        this.mContext = context;
    }
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_driver, parent, false);
        return new DriverAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverAdapter.Holder holder, int position) {
        Driver dr = (Driver) mDriver.get(position);
        holder.taikhoan.setText(dr.getGmail());
        holder.hoten.setText(dr.getDriverName());
        holder.sdt.setText(dr.getDriverPhone());
        holder.tongdon.setText("0");
        
//        mData.child("Tài xế").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    for(DataSnapshot data : snapshot.getChildren()){
//                        int i=0;
//                        String driveruid = data.child("driverid").getValue().toString();
//                        if(dr.getDriverid().contains((driveruid))){
//                            i++;
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return mDriver.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView hoten, sdt, tongdon, taikhoan;
        public Holder(@NonNull View itemView) {
            super(itemView);
            hoten = itemView.findViewById(R.id.txt_hotentaixe);
            sdt = itemView.findViewById(R.id.txt_sdttaixe);
            tongdon = itemView.findViewById(R.id.txt_tongsodonhang);
            taikhoan = itemView.findViewById(R.id.txt_taikhoandangnhap);
        }
    }
}
