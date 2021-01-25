package com.example.coffeebakeryadmin.List_Detail_Receipt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeebakeryadmin.List_Receipt.ListReceiptActivity;
import com.example.coffeebakeryadmin.List_Receipt.Receipt;
import com.example.coffeebakeryadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class DetailReceiptActivity extends AppCompatActivity {
    TextView ten_kh, sdt_kh, diachi, madon, ngaydat, thanhtien, tongmon, tongcong, phigh;
    RecyclerView recyclerView;
    ImageView back;
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
        String gmail = intent.getStringExtra("NGUOIDUNG");
        String md = intent.getStringExtra("MADON");
        String nd = intent.getStringExtra("NGAYDAT");
        String tt = intent.getStringExtra("TONGTIEN");
        String trangthai = intent.getStringExtra("TRANGTHAI");
        String mgh = intent.getStringExtra("MAGIOHANG");

        madon.setText(md);
        ngaydat.setText(nd);
        thanhtien.setText(tt);

        listchitiet = new ArrayList<DetailReceipt>();
        data.child("KHACHHANG").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    String mail = snap.child("gmail").getValue().toString();
                    if(mail.contains(gmail)){
                        String hoten = snap.child("hoten").getValue().toString();
                        String sdt = snap.child("sdt").getValue().toString();
                        String sonha = snap.child("sonha").getValue().toString();
                        String phuong = snap.child("phuong").getValue().toString();
                        String quan = snap.child("quan").getValue().toString();
                        String tp = snap.child("thanhpho").getValue().toString();
                        ten_kh.setText(hoten);
                        sdt_kh.setText(sdt);
                        diachi.setText(sonha + ", " + phuong + ", " + quan + ", " + tp);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        data.child("GioHang").child(gmail).child(md).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    String giohang = snap.child("giohang").getValue().toString();
                    if(giohang.contains(mgh)){
                        String link = snap.child("hinhanh").getValue().toString();
                        String sl = snap.child("soluong").getValue().toString();
                        String ten = snap.child("ten").getValue().toString();
                        String gia = snap.child("tongtien").getValue().toString();
                        String temp_gia = gia.substring(0,gia.length()-2);
                        tongslmon += Integer.parseInt(sl);
                        listchitiet.add(new DetailReceipt(link,sl,ten,temp_gia));
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
        tongcong.setText(String.valueOf(Integer.parseInt(thanhtien.getText().toString()) + Integer.parseInt(phigh.getText().toString())));

        chapnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trangthai.contains("Đang xử lý")){
                    String temp_trangthai = "Hoàn thành";
                    Receipt re = new Receipt(md,nd,tt,temp_trangthai,gmail);
                    data.child("DonHang").child(md).setValue(re);
                    Toast.makeText(DetailReceiptActivity.this, "Đơn hàng đã được phê duyệt!", Toast.LENGTH_SHORT).show();
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
                                data.child("DonHang").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot snap : snapshot.getChildren()){
                                            String temp_madon = snap.child("madon").getValue().toString();
                                            if(temp_madon.contains(md)){
                                                String temp_nguoidung = snap.child("nguoidung").getValue().toString();
                                                if(temp_nguoidung.contains(gmail)){
                                                    String temp_trangthai = snap.child("trangthai").getValue().toString();
                                                    if(temp_trangthai.contains("Hoàn thành")){
                                                        Toast.makeText(DetailReceiptActivity.this, "Đơn hàng đã hoàn thành!", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        String temp_huybo = "Từ chối";
                                                        Receipt re = new Receipt(md,nd,tt,temp_huybo,gmail);
                                                        data.child("DonHang").child(md).setValue(re);
                                                        Toast.makeText(DetailReceiptActivity.this, "Hủy đơn thành công!", Toast.LENGTH_SHORT).show();
                                                        Intent intent1 = new Intent(DetailReceiptActivity.this, ListReceiptActivity.class);
                                                        startActivity(intent1);
                                                    }
                                                }
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailReceiptActivity.this, ListReceiptActivity.class);
                startActivity(intent);
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
        back = findViewById(R.id.img_BackDSDH);
    }
}