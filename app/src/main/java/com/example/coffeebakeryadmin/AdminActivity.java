package com.example.coffeebakeryadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.coffeebakeryadmin.List_Product.ListProductActivity;
import com.example.coffeebakeryadmin.List_Receipt.ListReceiptActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {

    CardView cv_dshd, cv_cd, cv_bctk;
    LinearLayout cv_dssp;
    public static DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        AnhXa();

        cv_dssp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListProductActivity.class);
                startActivity(intent);
            }
        });

        cv_dshd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListReceiptActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        cv_dssp = (LinearLayout) findViewById(R.id.cv_DSSP);
        cv_dshd = (CardView) findViewById(R.id.cv_DSHD);
        cv_cd = (CardView) findViewById(R.id.cv_CD);
        cv_bctk = (CardView) findViewById(R.id.cv_BCTK);
    }
}