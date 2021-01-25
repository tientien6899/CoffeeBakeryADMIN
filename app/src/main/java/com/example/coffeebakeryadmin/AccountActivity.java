package com.example.coffeebakeryadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.coffeebakeryadmin.Account.LoginActivity.arr_pass;
import static com.example.coffeebakeryadmin.Account.LoginActivity.pass;
import static com.example.coffeebakeryadmin.Account.LoginActivity.user;
import static com.example.coffeebakeryadmin.AdminActivity.mData;

public class AccountActivity extends AppCompatActivity {

    ImageView img_backtttk, img_savetttk, img_avataradmin;
    EditText txt_hotenadmin, txt_emailadmin, txt_matkhauhientai, txt_matkhaumoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        AnhXa();

        mData.child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Admin a = snapshot.getValue(Admin.class);
                    txt_hotenadmin.setText(a.getHoten());
                    txt_emailadmin.setText(a.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        img_savetttk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = txt_hotenadmin.getText().toString();
                String mail = txt_emailadmin.getText().toString();
                String mkht = txt_matkhauhientai.getText().toString();
                String mkmoi = txt_matkhaumoi.getText().toString();
                if(mkht.contains(arr_pass)){
                    if(mkmoi != ""){
                        mkht = mkmoi;
                    }
                    Admin ad = new Admin(hoten,mail,mkht);
                    mData.child("Admin").setValue(ad);
                    user = hoten;
                    Toast.makeText(AccountActivity.this, "Thêm thông tin Admin thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), AdminActivity.class);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(AccountActivity.this, "Mời nhập lại mật khẩu!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        img_backtttk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });


    }

    private void AnhXa() {
        img_backtttk = findViewById(R.id.img_BackTTTK);
        img_savetttk = findViewById(R.id.img_SaveTTTK);
        img_avataradmin = findViewById(R.id.img_AvatarAdmin);
        txt_hotenadmin = findViewById(R.id.txt_HotenAdmin);
        txt_emailadmin = findViewById(R.id.txt_EmailAdmin);
        txt_matkhauhientai = findViewById(R.id.txt_Matkhauhientai);
        txt_matkhaumoi = findViewById(R.id.txt_Matkhaumoi);
    }
}