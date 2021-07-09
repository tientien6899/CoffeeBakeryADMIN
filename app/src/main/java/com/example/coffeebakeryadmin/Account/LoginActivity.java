package com.example.coffeebakeryadmin.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeebakeryadmin.Admin;
import com.example.coffeebakeryadmin.AdminActivity;
import com.example.coffeebakeryadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    public static String user = "admin";
    public static String pass = "123456";
    public static String arr_pass = "";
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    EditText username, password;
    Button dangnhap;
    TextView quenmatkhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AnhXa();

        quenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        mData.child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Admin ad = snapshot.getValue(Admin.class);
                    arr_pass = ad.getMkhientai();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_user = username.getText().toString();
                String str_pass = password.getText().toString();

                if(str_user.contains(user) && str_pass.contains(arr_pass)){
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), AdminActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Mời nhập lại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void AnhXa() {
        username = findViewById(R.id.edt_usernameDN);
        password = findViewById(R.id.edt_passwordDN);
        dangnhap = findViewById(R.id.btn_Dangnhap);
        quenmatkhau = findViewById(R.id.txt_QuenMK);
    }
}