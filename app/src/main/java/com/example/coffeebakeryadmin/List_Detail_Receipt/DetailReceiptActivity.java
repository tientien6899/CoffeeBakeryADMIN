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
        String md = intent.getStringExtra("MADON");
        String trangthai = intent.getStringExtra("TRANGTHAI");
        listchitiet = new ArrayList<DetailReceipt>();
        data.child("Đơn hàng").child("Thông tin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    String str_madon = snap.child("madon").getValue().toString();
                    if(str_madon.contains(md)){
                        String temp_hoten = snap.child("hoten").getValue().toString();
                        String temp_sdt = snap.child("sdt").getValue().toString();
                        String temp_sonha = snap.child("sonha").getValue().toString();
                        String temp_ngaydat = snap.child("ngaydat").getValue().toString();
                        String temp_tamtinh = snap.child("tamtinh").getValue().toString();
                        String temp_ship = snap.child("ship").getValue().toString();
                        String temp_tongcong = snap.child("tongtien").getValue().toString();
                        ten_kh.setText(temp_hoten);
                        sdt_kh.setText(temp_sdt);
                        diachi.setText(temp_sonha);
                        madon.setText(md);
                        ngaydat.setText(temp_ngaydat);
                        thanhtien.setText(temp_tamtinh);
                        phigh.setText(temp_ship);
                        tongcong.setText(temp_tongcong);
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
                    String temp_trangthai = "Hoàn thành";
                    data.child("Đơn hàng").child("Thông tin").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot datasnap : snapshot.getChildren()){
                                Receipt re = datasnap.getValue(Receipt.class);
                                if(re.getMadon().contains(md)){
                                    re.setTrangthai(temp_trangthai);
                                    data.child("Đơn hàng").child("Thông tin").child(md).setValue(re);
                                    Toast.makeText(DetailReceiptActivity.this, "Đơn hàng đã được phê duyệt!", Toast.LENGTH_SHORT).show();

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
                                data.child("Đơn hàng").child("Thông tin").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String temp_trangthai = "Hủy đơn";
                                        for(DataSnapshot snap : snapshot.getChildren()){
                                            Receipt receipt = snap.getValue(Receipt.class);
                                            if(receipt.getMadon().contains(md)){
                                                receipt.setTrangthai(temp_trangthai);
                                                data.child("Đơn hàng").child("Thông tin").child(md).setValue(receipt);
                                                Toast.makeText(DetailReceiptActivity.this, "Đơn hàng đã được hủy bỏ!", Toast.LENGTH_SHORT).show();

                                                ThongBao noti = new ThongBao();
                                                noti.setTieude("Thông báo xác nhận đơn hàng");
                                                noti.setNoidung("Đơn hàng " + receipt.getMadon()+ " của bạn đã bị hủy bỏ!");
                                                noti.setUserid(receipt.getNguoidung());
                                                data.child("Thông báo").child(noti.getTieude()).child(receipt.getMadon()).setValue(noti);

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