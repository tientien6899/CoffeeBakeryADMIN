package com.example.coffeebakeryadmin.List_Product;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.coffeebakeryadmin.List_Receipt.RecieptAdapter;
import com.example.coffeebakeryadmin.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder>{
    private List mProduct;
    private Context mContext;

    public ProductAdapter(List mProduct, Context context){
        this.mProduct = mProduct;
        this.mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product, parent, false);
        return new ProductAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Product pr = (Product) mProduct.get(position);
        holder.tensp.setText(pr.getTensp());
        holder.giasp.setText(pr.getGiaS());
        holder.luotmua.setText(pr.getLuotMua() + "");
        if(pr.getSPMoi())
            holder.spmoi.setChecked(true);
        holder.spmoi.setEnabled(false);

        holder.giakm.setText(pr.getGiaKM());
        Glide.with(holder.hinhanh.getContext()).load(pr.getLink()).into(holder.hinhanh);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailProductActivity.class);
                //truyền dữ liệu sang detail
                intent.putExtra("DANHMUC",pr.getDanhmuc());
                intent.putExtra("TENSP",pr.getTensp());
                intent.putExtra("MASP",pr.getMasp());
                intent.putExtra("GIAS",pr.getGiaS());
                intent.putExtra("GIAM",pr.getGiaM());
                intent.putExtra("GIAL",pr.getGiaL());
                intent.putExtra("GIAKM",pr.getGiaKM());
                intent.putExtra("LINK",pr.getLink());
                intent.putExtra("MOTA",pr.getMota());
                intent.putExtra("SPMOI",pr.getSPMoi());
                intent.putExtra("LUOTMUA",pr.getLuotMua());
                intent.putExtra("LUOTTHICH",pr.getLuotYeuThich());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView hinhanh;
        TextView tensp, giasp, gial, giakm, luotmua, luotthich;
        CheckBox spmoi;
        public Holder(@NonNull View itemView) {
            super(itemView);
            hinhanh = (ImageView)itemView.findViewById(R.id.hinhanh_sanpham);
            tensp = (TextView)itemView.findViewById(R.id.tensanpham_sanpham);
            giasp = (TextView)itemView.findViewById(R.id.txt_giasp);
            luotmua = (TextView)itemView.findViewById(R.id.txt_luotmua);
            spmoi = (CheckBox) itemView.findViewById(R.id.chb_spmoi);
            giakm = (TextView)itemView.findViewById(R.id.giakm_sanpham);
        }
    }
}
