package com.example.coffeebakeryadmin.ListCustomer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeebakeryadmin.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CustomerAdapter extends FirebaseRecyclerAdapter<Customer,CustomerAdapter.Holder> {

    public CustomerAdapter(@NonNull FirebaseRecyclerOptions<Customer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull Customer model) {
        holder.hoten.setText(model.getHoten());
        holder.mail.setText(model.getGmail());
        String dc = model.getSonha() + " " + model.getPhuong() + " " + model.getQuan() + " " + model.getThanhpho();
        holder.diachi.setText(dc);
        holder.sdt.setText(model.getSdt());
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_customer, parent, false);
        return new CustomerAdapter.Holder(v);
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView hoten, mail, diachi, sdt;
        public Holder(@NonNull View itemView) {
            super(itemView);
            hoten = (TextView) itemView.findViewById(R.id.txt_TenKH);
            mail = (TextView) itemView.findViewById(R.id.txt_EmailKH);
            diachi = (TextView) itemView.findViewById(R.id.txt_DiachiKH);
            sdt = (TextView) itemView.findViewById(R.id.txt_SDTKH);
        }
    }
}
