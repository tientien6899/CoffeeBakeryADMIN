package com.example.coffeebakeryadmin;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecieptAdapter extends FirebaseRecyclerAdapter<Receipt,RecieptAdapter.Holder> {
    public RecieptAdapter(@NonNull FirebaseRecyclerOptions<Receipt> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecieptAdapter.Holder holder, int position, @NonNull Receipt model) {

    }

    @NonNull
    @Override
    public RecieptAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
