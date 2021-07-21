package com.example.coffeebakeryadmin.List_Detail_Receipt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeebakeryadmin.List_Receipt.ListReceiptActivity;
import com.example.coffeebakeryadmin.List_Receipt.Receipt;
import com.example.coffeebakeryadmin.R;
import com.example.coffeebakeryadmin.ThongBao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailReceiptActivity extends AppCompatActivity {
    TextView ten_kh, sdt_kh, diachi, madon, ngaydat, thanhtien, tongmon, tongcong, phigh;
    RecyclerView recyclerView;
    Button chapnhan, huybo;
    DetailReceiptAdapter adapter;
    ArrayList<DetailReceipt> listchitiet;
    public static int tongslmon = 0;
    private DatabaseReference data = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_receipt);
        AnhXa();
        Intent intent = getIntent();
        String md = intent.getStringExtra("MADON");
        String trangthai = intent.getStringExtra("TRANGTHAI");
        listchitiet = new ArrayList<DetailReceipt>();
        data.child("Đơn hàng").child("Thông tin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snap : snapshot.getChildren()){
                        Receipt re = snap.getValue(Receipt.class);
                        if(re.getMadon().contains(md)){
                            ten_kh.setText(re.getHoten());
                            sdt_kh.setText(re.getSdt());
                            diachi.setText(re.getSonha());
                            madon.setText(md);
                            ngaydat.setText(re.getNgaydat());
                            thanhtien.setText(re.getTamtinh());
                            phigh.setText(re.getShip());
                            tongcong.setText(re.getTongtien());
                            String tt = re.getTrangthai();
                            switch (tt){
                                case "Hoàn thành":
                                case "Đang vận đơn":
                                case "Hủy đơn":
                                   chapnhan.setVisibility(View.GONE);
                                   huybo.setVisibility(View.GONE);
                                    break;
                                case "Đang xử lý":
                                    chapnhan.setVisibility(View.VISIBLE);
                                    huybo.setVisibility(View.VISIBLE);
                                    break;
                                case "Đang chuẩn bị đơn":
                                    chapnhan.setVisibility(View.GONE);
                                    huybo.setVisibility(View.VISIBLE);
                                    break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        data.child("Đơn hàng").child("Chi tiết").child(md).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    String giohang = snap.child("giohang").getValue().toString();
                    if(giohang.contains(md)){
                        String link = snap.child("hinhanh").getValue().toString();
                        String sl = snap.child("soluong").getValue().toString();
                        String ten = snap.child("ten").getValue().toString();
                        String gia = snap.child("tongtien").getValue().toString();

                        tongslmon += Integer.parseInt(sl);
                        listchitiet.add(new DetailReceipt(link,sl,ten,gia));
                    }
                    tongmon.setText(String.valueOf(tongslmon));
                    adapter = new DetailReceiptAdapter(listchitiet, DetailReceiptActivity.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailReceiptActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(linearLayoutManager);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        chapnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trangthai.contains("Đang xử lý")){
                    String temp_trangthai = "Đang chuẩn bị đơn";
                    data.child("Đơn hàng").child("Thông tin").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot datasnap : snapshot.getChildren()){
                                Receipt re = datasnap.getValue(Receipt.class);
                                if(re.getMadon().contains(md)){
                                    re.setTrangthai(temp_trangthai);
                                    data.child("Đơn hàng").child("Thông tin").child(md).setValue(re);
                                    Toast.makeText(DetailReceiptActivity.this, "Đơn hàng đã được phê duyệt!", Toast.LENGTH_SHORT).show();

                                    data.child("Đơn hàng").child("Tổng tiền").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            int temp_total = 0;
                                            String temp_to = snapshot.child("Total").getValue().toString();
                                            String temp_tongcong = tongcong.getText().toString();
                                            temp_total = Integer.parseInt(temp_to) + Integer.parseInt(temp_tongcong.replace(".",""));
                                            data.child("Đơn hàng").child("Tổng tiền").child("Total").setValue(temp_total);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    ThongBao noti = new ThongBao();
                                    noti.setTieude("Thông báo xác nhận đơn hàng");
                                    noti.setNoidung("Đơn hàng " + re.getMadon()+ " của bạn đã được xác nhận!");
                                    noti.setUserid(re.getNguoidung());
                                    data.child("Thông báo").child(noti.getTieude()).child(re.getMadon()).setValue(noti);

                                    startActivity(new Intent(DetailReceiptActivity.this, ListReceiptActivity.class));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    huybo.setEnabled(false);
                }
                else Toast.makeText(DetailReceiptActivity.this, "Đơn hàng đã hoàn thành rồi!", Toast.LENGTH_SHORT).show();
            }
        });

        huybo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailReceiptActivity.this);
                builder.setMessage("Xác nhận hủy đơn")
                        .setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                data.child("Đơn hàng").child("Thông tin").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String temp_trangthai = "Hủy đơn";
                                        for(DataSnapshot snap : snapshot.getChildren()){
                                            Receipt receipt = snap.getValue(Receipt.class);
                                            if(receipt.getMadon().contains(md)){
                                                receipt.setTrangthai(temp_trangthai);
                                                data.child("Đơn hàng").child("Thông tin").child(md).setValue(receipt);
                                                Toast.makeText(DetailReceiptActivity.this, "Đơn hàng đã được hủy bỏ!", Toast.LENGTH_SHORT).show();

                                                data.child("Đơn hàng").child("Tổng tiền").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        int temp_total = 0;
                                                        String temp_to = snapshot.child("Total").getValue().toString();
                                                        String temp_tongcong = tongcong.getText().toString();
                                                        temp_total = Integer.parseInt(temp_to) - Integer.parseInt(temp_tongcong.replace(".",""));
                                                        data.child("Đơn hàng").child("Tổng tiền").child("Total").setValue(temp_total);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                                ThongBao noti = new ThongBao();
                                                noti.setTieude("Thông báo xác nhận đơn hàng");
                                                noti.setNoidung("Đơn hàng " + receipt.getMadon()+ " của bạn đã bị hủy bỏ!");
                                                noti.setUserid(receipt.getNguoidung());
                                                data.child("Thông báo").child(noti.getTieude()).child(receipt.getMadon()).setValue(noti);
                                                chapnhan.setEnabled(false);
                                                startActivity(new Intent(DetailReceiptActivity.this, ListReceiptActivity.class));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }).show();
            }
        });

    }


    private void AnhXa() {
        ten_kh = (TextView) findViewById(R.id.txt_Tenkhachhang);
        sdt_kh = (TextView) findViewById(R.id.txt_SDTkhachhang);
        diachi = (TextView) findViewById(R.id.txt_Diachigiaohang);
        madon = (TextView) findViewById(R.id.txt_Madonhang);
        ngaydat = (TextView) findViewById(R.id.txt_Thoigiandathang);
        thanhtien = (TextView) findViewById(R.id.txt_Thanhtien);
        recyclerView = (RecyclerView) findViewById(R.id.rcv_Chitietdonhang);
        tongmon = (TextView) findViewById(R.id.txt_Soluongmon);
        tongcong = (TextView) findViewById(R.id.txt_Tongcong);
        phigh = (TextView) findViewById(R.id.txt_Phigiaohang);
        chapnhan = (Button) findViewById(R.id.btn_chapnhan);
        huybo = (Button) findViewById(R.id.btn_huybo);
    }
}