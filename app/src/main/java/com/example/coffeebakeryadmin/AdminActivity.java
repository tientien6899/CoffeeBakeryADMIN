package com.example.coffeebakeryadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeebakeryadmin.Account.LoginActivity;
import com.example.coffeebakeryadmin.Banner.ListBannerActivity;
import com.example.coffeebakeryadmin.Infomation.InfomationActivity;
import com.example.coffeebakeryadmin.ListBook.BookActivity;
import com.example.coffeebakeryadmin.ListCustomer.ListCustomerActivity;
import com.example.coffeebakeryadmin.ListReport.ReportActivity;
import com.example.coffeebakeryadmin.List_Post.ListPostActivity;
import com.example.coffeebakeryadmin.List_Product.ListProductActivity;
import com.example.coffeebakeryadmin.List_Receipt.ListReceiptActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.coffeebakeryadmin.Account.LoginActivity.gmail;

public class AdminActivity extends AppCompatActivity {

    LinearLayout cv_dssp, cv_qlbv, cv_qlkh, cv_qldh, cv_bqc, cv_ttch, cv_bctk, cv_csdk;
    ImageView img_tttk, img_logout;
    TextView txt_tenadmin;
    Intent intent;
    public static DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        AnhXa();

        cv_ttch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(view.getContext(), InfomationActivity.class);
                startActivity(intent);
            }
        });

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
                intent = new Intent(AdminActivity.this, AccountActivity.class);
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

        cv_qlbv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), ListPostActivity.class);
                startActivity(intent);
            }
        });

        cv_csdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(view.getContext(), BookActivity.class);
                startActivity(intent);
            }
        });

        cv_bqc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(view.getContext(), ListBannerActivity.class);
                startActivity(intent);
            }
        });

        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Bạn muốn đăng xuất?");
                builder.setNeutralButton("Quay lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                        .setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                gmail = "";
                                Intent dangxuat = new Intent(AdminActivity.this, LoginActivity.class);
                                startActivity(dangxuat);
                            }
                        }).show();
            }
        });

    }

    private void AnhXa() {
        cv_dssp = (LinearLayout) findViewById(R.id.cv_DSSP);
        cv_qlbv = (LinearLayout) findViewById(R.id.cv_QLBV);
        cv_qlkh = (LinearLayout) findViewById(R.id.cv_QLKH);
        cv_qldh = (LinearLayout) findViewById(R.id.cv_QLDH);
        cv_bqc = (LinearLayout) findViewById(R.id.cv_BQC);
        cv_ttch = (LinearLayout) findViewById(R.id.cv_TTCH);
        cv_bctk = (LinearLayout) findViewById(R.id.cv_BCTK);
        cv_csdk = (LinearLayout) findViewById(R.id.cv_CSDK);
        img_tttk = (ImageView) findViewById(R.id.img_TTTK);
        img_logout = findViewById(R.id.img_Logout);
        txt_tenadmin = findViewById(R.id.txt_TenAdmin);
    }
}