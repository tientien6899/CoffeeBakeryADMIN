package com.example.coffeebakeryadmin.Drivers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeebakeryadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.coffeebakeryadmin.AdminActivity.mData;

public class AddDriverActivity extends AppCompatActivity {

    EditText dangnhap, matkhau, rematkhau;
    Button dangky;
    ImageButton back;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);
        AnhXa();
        mAuth = FirebaseAuth.getInstance();

        //Không được để trống username
        dangnhap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    dangnhap.setError("Không được để trống tên người dùng.");
                } else {
                    dangnhap.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Không được để trống Password
        matkhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    matkhau.setError("Không được để trống mật khẩu.");
                } else {
                    matkhau.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Không được để trống Nhập lại mật khẩu
        rematkhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    rematkhau.setError("Không được để trống nhập lại mật khẩu.");
                } else {
                    rematkhau.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Nút Đăng ký
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Strpass = matkhau.getText().toString();
                String Strrepeatpass = rematkhau.getText().toString();
                String Stremail = dangnhap.getText().toString() + "@gmail.com";

                if(Stremail.contains("@gmail.com"))
                {
                    if (Strpass.equals(Strrepeatpass)) {
                        Dangky(Stremail, Strpass);
                        Driver dr = new Driver();
                        dr.gmail = dangnhap.getText().toString();
                        dr.driverPhone = "";
                        dr.avatar = "";
                        dr.driverName = "";
                        dr.driverid = "";
                        mData.child("Tài xế").child(dr.getGmail()).setValue(dr);
                        Intent intent = new Intent(AddDriverActivity.this, DriverActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddDriverActivity.this, "Mật khẩu không trùng khớp.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddDriverActivity.this);
                    builder.setTitle("Thông báo")
                            .setMessage("Vui lòng nhập lại thông tin!")
                            .setPositiveButton("Nhập lại", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    matkhau.requestFocus();
                                }
                            }).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddDriverActivity.class);
                startActivity(intent);
            }
        });

    }

    //Hàm đăng ký tài khoản bằng username + password
    private void Dangky(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddDriverActivity.this, "Đăng ký tài khoản thành công.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddDriverActivity.this, "Đăng ký tài khoản thất bại.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void AnhXa() {
        dangnhap = findViewById(R.id.edt_usernameDK);
        matkhau = findViewById(R.id.edt_passwordDK);
        rematkhau = findViewById(R.id.edt_repeatpass);
        dangky = findViewById(R.id.btn_Dangky);
        back = findViewById(R.id.img_Quaylai);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}