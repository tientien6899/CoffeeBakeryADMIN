package com.example.coffeebakeryadmin.ListReport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ReportActivity extends AppCompatActivity {

    ImageView back;
    TextView tongdon, tongdoanhthu;
    LinearLayout dailyreport, monthreport, quarreport;
    private DatabaseReference mdata = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        AnhXa();

        mdata.child("STT").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String str_stt = snapshot.child("stt").getValue().toString();
                int temp_stt = Integer.parseInt(str_stt) -1;
                tongdon.setText(String.valueOf(temp_stt));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mdata.child("DonHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int tong = 0;
                for(DataSnapshot snap : snapshot.getChildren()){
                    String str_tong = snap.child("tongtien").getValue().toString();
                    tong += Integer.parseInt(str_tong);
                }
                tongdoanhthu.setText(String.valueOf(tong));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        dailyreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DailyReportActivity.class);
                startActivity(intent);
            }
        });

        monthreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MonthReportActivity.class);
                startActivity(intent);
            }
        });

        quarreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), QuarReportActivity.class);
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
        back = findViewById(R.id.btn_BackReport);
        tongdon = (TextView) findViewById(R.id.txt_TongDonHang);
        tongdoanhthu = (TextView) findViewById(R.id.txt_TongDoanhThu);
        dailyreport = (LinearLayout) findViewById(R.id.report_dailyreport);
        monthreport = (LinearLayout) findViewById(R.id.report_monthreport);
        quarreport = (LinearLayout) findViewById(R.id.report_quarreport);
    }

}
