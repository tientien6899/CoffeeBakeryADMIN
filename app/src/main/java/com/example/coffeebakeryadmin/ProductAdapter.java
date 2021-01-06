package com.example.coffeebakeryadmin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

public class ProductAdapter extends FirebaseRecyclerAdapter<Product,ProductAdapter.Holder> {

    public ProductAdapter(@NonNull FirebaseRecyclerOptions<Product> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductAdapter.Holder holder, int position, @NonNull Product model) {

        holder.tensp.setText(model.getTensp());
        holder.gias.setText(model.getGiaS());
        holder.giam.setText(model.getGiaM());
        holder.gial.setText(model.getGiaL());
        holder.giakm.setText(model.getGiaKM());
        Glide.with(holder.hinhanh.getContext()).load(model.getLink()).into(holder.hinhanh);

        //Vuốt qua để xóa 1 dòng trong danh sách sản phẩm
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("DANHMUC",model.danhmuc);
                intent.putExtra("TENSP",model.tensp);
                intent.putExtra("MASP",model.masp);
                intent.putExtra("GIAS",model.giaS);
                intent.putExtra("GIAM",model.giaM);
                intent.putExtra("GIAL",model.giaL);
                intent.putExtra("GIAKM",model.giaKM);
                intent.putExtra("LINK",model.link);
                intent.putExtra("MOTA",model.mota);
                context.startActivity(intent);
//
//                Button xoa = (Button) d.findViewById(R.id.huy);
//                xoa.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
//                        mData.child("SanPham").child("Coffee").child(model.getMasp()).removeValue();
//                    }
//                });
//                d.show();

            }
        });

    }


    @NonNull
    @Override
    public ProductAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product, parent, false);

        return new Holder(v);

    }
    class Holder extends RecyclerView.ViewHolder{
        ImageView hinhanh;
        TextView tensp, gias, giam, gial, giakm;
        public Holder(@NonNull final View itemView) {
            super(itemView);
            hinhanh = (ImageView)itemView.findViewById(R.id.hinhanh_sanpham);
            tensp = (TextView)itemView.findViewById(R.id.tensanpham_sanpham);
            gias = (TextView)itemView.findViewById(R.id.gias_sanpham);
            giam = (TextView)itemView.findViewById(R.id.giam_sanpham);
            gial = (TextView)itemView.findViewById(R.id.gial_sanpham);
            giakm = (TextView)itemView.findViewById(R.id.giakm_sanpham);
        }
    }
}
