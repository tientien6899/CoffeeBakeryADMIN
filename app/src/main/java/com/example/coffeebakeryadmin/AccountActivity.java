package com.example.coffeebakeryadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {

    ImageView img_backtttk, img_savetttk, img_avataradmin;
    EditText txt_hotenadmin, txt_emailadmin, txt_matkhauhientai, txt_matkhaumoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        AnhXa();

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