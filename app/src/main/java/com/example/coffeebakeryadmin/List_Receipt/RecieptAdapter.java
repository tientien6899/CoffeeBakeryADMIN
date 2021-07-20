package com.example.coffeebakeryadmin.List_Receipt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.coffeebakeryadmin.List_Detail_Receipt.DetailReceiptActivity;
import com.example.coffeebakeryadmin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecieptAdapter extends RecyclerView.Adapter<RecieptAdapter.Holder>{
    private List mDonhang;
    private Context mContext;
    private DatabaseReference myData = FirebaseDatabase.getInstance().getReference();

    public RecieptAdapter(List donhang, Context context){
        this.mDonhang = donhang;
        this.mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_receipt, parent, false);
        return new RecieptAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Receipt re = (Receipt) mDonhang.get(position);
        holder.hoadon.setText(re.getMadon());
        holder.ngaydat.setText(re.getNgaydat());
        holder.thanhtien.setText(re.getTongtien());
        holder.nguoidung.setText(re.getHoten());
        holder.trangthai.setText(re.getTrangthai());
        String tt = re.getTrangthai();
        if(tt.contains("Đang xử lý")){
            holder.trangthai.setTextColor(Color.BLUE);
        } else if(tt.contains("Đang chuẩn bị đơn")){
            holder.trangthai.setTextColor(Color.GREEN);
        }else if(tt.contains("Đơn hàng đang được giao")){
            holder.trangthai.setTextColor(Color.YELLOW);
        } else {
            holder.trangthai.setTextColor(Color.RED);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailReceiptActivity.class);
                intent.putExtra("MADON",re.getMadon());
                intent.putExtra("TRANGTHAI",re.getTrangthai());
                intent.putExtra("DRIVERID",re.getDriverid());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDonhang.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView hoadon, ngaydat, thanhtien, trangthai, nguoidung;
        public Holder(@NonNull View itemView) {
            super(itemView);
            hoadon = (TextView) itemView.findViewById(R.id.txt_MaHoaDon);
            ngaydat = (TextView) itemView.findViewById(R.id.txt_ThoiGianDat);
            thanhtien = (TextView) itemView.findViewById(R.id.txt_TongTien);
            trangthai = (TextView) itemView.findViewById(R.id.txt_TrangThai);
            nguoidung = (TextView) itemView.findViewById(R.id.txt_TenKhachHang);
        }
    }
}
