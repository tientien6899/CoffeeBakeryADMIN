package com.example.coffeebakeryadmin.Banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeebakeryadmin.ListCustomer.CustomerAdapter;
import com.example.coffeebakeryadmin.R;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.Holder> {
    private List mPoster;
    private Context mContext;

    public BannerAdapter(List mPoster, Context context){
        this.mPoster = mPoster;
        this.mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_poster, parent, false);
        return new BannerAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Banner bn = (Banner) mPoster.get(position);
        holder.name.setText(bn.getTen());
        Glide.with(holder.img.getContext()).load(bn.getLink()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return mPoster.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        public Holder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.poster_img);
            name = (TextView) itemView.findViewById(R.id.poster_name);
        }
    }
}
