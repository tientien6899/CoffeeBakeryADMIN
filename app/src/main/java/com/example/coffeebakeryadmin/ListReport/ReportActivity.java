package com.example.coffeebakeryadmin.ListReport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.coffeebakeryadmin.R;
import com.example.coffeebakeryadmin.AdminActivity.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class ReportActivity extends AppCompatActivity {

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
    }

    private void AnhXa() {
        tongdon = (TextView) findViewById(R.id.txt_TongDonHang);
        tongdoanhthu = (TextView) findViewById(R.id.txt_TongDoanhThu);
        dailyreport = (LinearLayout) findViewById(R.id.report_dailyreport);
        monthreport = (LinearLayout) findViewById(R.id.report_monthreport);
        quarreport = (LinearLayout) findViewById(R.id.report_quarreport);
    }

}
