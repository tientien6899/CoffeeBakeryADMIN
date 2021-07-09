package com.example.coffeebakeryadmin.Infomation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeebakeryadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddInfoActivity extends AppCompatActivity {

    public static int checkInfo = 0;
    EditText ten, email, diachi, sdt, ghichu;
    Button addInfo;
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        AnhXa();
        mData = FirebaseDatabase.getInstance().getReference();

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("ThongTinCH")){
                    mData.child("ThongTinCH").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Info i = snapshot.getValue(Info.class);
                            ten.setText(i.getTencuahang());
                            email.setText(i.getEmail());
                            diachi.setText(i.getDiachi());
                            sdt.setText(i.getSdt());
                            ghichu.setText(i.getGhichu());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tench = ten.getText().toString();
                String mail = email.getText().toString();
                String dc = diachi.getText().toString();
                String dt = sdt.getText().toString();
                String gc = ghichu.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn muốn lưu thông tin này chứ ?");
                builder.setNeutralButton("Quay lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(view.getContext(), InfomationActivity.class);
                        startActivity(intent);
                        dialogInterface.cancel();
                    }
                })
                        .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mData.child("ThongTinCH").setValue(new Info(tench,mail,dc,dt,gc));

                                Toast.makeText(AddInfoActivity.this, "Thông tin đã được lưu!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(view.getContext(), InfomationActivity.class);
                                startActivity(intent);
                                dialogInterface.cancel();
                            }
                        }).show();
                checkInfo = 1;
            }
        });

    }

    private void AnhXa() {
        ten = findViewById(R.id.txt_TenCuaHang);
        email = findViewById(R.id.txt_EmailCuaHang);
        diachi = findViewById(R.id.txt_DiaChiCuaHang);
        sdt = findViewById(R.id.txt_SDTCuaHang);
        ghichu = findViewById(R.id.txt_GhiChuCuaHang);
        addInfo = findViewById(R.id.btn_Dongy);
    }
}