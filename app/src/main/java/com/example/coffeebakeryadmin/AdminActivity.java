package com.example.coffeebakeryadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeebakeryadmin.ListCustomer.ListCustomerActivity;
import com.example.coffeebakeryadmin.ListReport.ReportActivity;
import com.example.coffeebakeryadmin.List_Product.ListProductActivity;
import com.example.coffeebakeryadmin.List_Receipt.ListReceiptActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {

    LinearLayout cv_dmsp, cv_dssp, cv_qlbv, cv_qlkh, cv_qltk, cv_qldh, cv_qlhd, cv_bqc, cv_ttch, cv_bctk;
    ImageView img_tttk, img_logout;
    TextView txt_tenadmin;
    Intent intent;
    public static DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        AnhXa();

        cv_dssp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), ListProductActivity.class);
                startActivity(intent);
            }
        });

        img_tttk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), ListReceiptActivity.class);
                startActivity(intent);
            }
        });

        cv_qlkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), ListCustomerActivity.class);
                startActivity(intent);
            }
        });

        cv_bctk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), ReportActivity.class);
                startActivity(intent);
            }
        });

        cv_qldh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), ListReceiptActivity.class);
                startActivity(intent);
            }
        });

    }

    private void AnhXa() {
        cv_dssp = (LinearLayout) findViewById(R.id.cv_DSSP);
        cv_dmsp = (LinearLayout) findViewById(R.id.cv_DMSP);
        cv_qlbv = (LinearLayout) findViewById(R.id.cv_QLBV);
        cv_qlkh = (LinearLayout) findViewById(R.id.cv_QLKH);
        cv_qltk = (LinearLayout) findViewById(R.id.cv_QLTK);
        cv_qldh = (LinearLayout) findViewById(R.id.cv_QLDH);
        cv_qlhd = (LinearLayout) findViewById(R.id.cv_QLHD);
        cv_bqc = (LinearLayout) findViewById(R.id.cv_BQC);
        cv_ttch = (LinearLayout) findViewById(R.id.cv_TTCH);
        cv_bctk = (LinearLayout) findViewById(R.id.cv_BCTK);
        img_tttk = findViewById(R.id.img_TTTK);
        img_logout = findViewById(R.id.img_Logout);
        txt_tenadmin = findViewById(R.id.txt_TenAdmin);
    }
}