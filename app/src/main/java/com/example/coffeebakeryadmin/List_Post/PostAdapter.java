package com.example.coffeebakeryadmin.List_Post;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeebakeryadmin.ListDetailPost.DetailPostActivity;
import com.example.coffeebakeryadmin.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.Holder> {
    private List mPost;
    private Context mContext;

    public PostAdapter(List mPost, Context context){
        this.mPost = mPost;
        this.mContext = context;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_post, parent, false);
        return new PostAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Post p = (Post) mPost.get(position);
        holder.tieude.setText(p.getTieude());
        holder.noidung.setText(p.getNoidung());
        Glide.with(holder.img.getContext()).load(p.getHinhanh()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailPostActivity.class);
                intent.putExtra("TIEUDE",p.getTieude());
                intent.putExtra("NGAYDANG",p.getNgaydang());
                intent.putExtra("HINHANH",p.getHinhanh());
                intent.putExtra("NOIDUNG",p.getNoidung());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tieude, noidung;
        public Holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.post_imgpost);
            tieude = itemView.findViewById(R.id.post_tieudepost);
            noidung = itemView.findViewById(R.id.post_noidungpost);
        }
    }
}
