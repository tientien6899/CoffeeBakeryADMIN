package com.example.coffeebakeryadmin.ListCustomer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeebakeryadmin.List_Product.ProductAdapter;
import com.example.coffeebakeryadmin.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.Holder>{

    private List mCustomer;
    private Context mContext;

    public CustomerAdapter(List mCustomer, Context context){
        this.mCustomer = mCustomer;
        this.mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_customer, parent, false);
        return new CustomerAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Customer cu = (Customer) mCustomer.get(position);
        holder.hoten.setText(cu.getHoten());
        holder.mail.setText(cu.getGmail());
        holder.diachi.setText(cu.getSonha());
        holder.sdt.setText(cu.getSdt());
    }

    @Override
    public int getItemCount() {
        return mCustomer.size();
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

