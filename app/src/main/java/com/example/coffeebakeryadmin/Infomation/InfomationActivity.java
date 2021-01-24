package com.example.coffeebakeryadmin.Infomation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeebakeryadmin.AdminActivity;
import com.example.coffeebakeryadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class InfomationActivity extends AppCompatActivity {

    ImageView addinfo, back;
    TextView ten, mail, diachi, sdt, ghichu;
    private DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        mData = FirebaseDatabase.getInstance().getReference();
        AnhXa();

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("ThongTinCH")){
                    mData.child("ThongTinCH").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Info i = snapshot.getValue(Info.class);
                            ten.setText(i.getTencuahang());
                            mail.setText(i.getEmail());
                            diachi.setText(i.getDiachi());
                            sdt.setText(i.getSdt());
                            ghichu.setText(i.getGhichu());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Intent intent = new Intent(InfomationActivity.this,AddInfoActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddInfoActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdminActivity.class);
                startActivity(intent);
            }
        });

    }

    private void AnhXa() {
        back = findViewById(R.id.img_BackTTCH_Admin);
        addinfo = findViewById(R.id.img_AddInfo);
        ten = findViewById(R.id.txt_TenCuaHangInfo);
        mail = findViewById(R.id.txt_EmailCuaHangInfo);
        diachi = findViewById(R.id.txt_DiaChiCuaHangInfo);
        sdt = findViewById(R.id.txt_SDTCuaHangInfo);
        ghichu = findViewById(R.id.txt_GhiChuCuaHangInfo);
    }
}