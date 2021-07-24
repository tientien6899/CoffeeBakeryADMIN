package com.example.coffeebakeryadmin.ListReport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.coffeebakeryadmin.List_Receipt.Receipt;
import com.example.coffeebakeryadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class QuarReportActivity extends AppCompatActivity {

    Button xem_quy;
    TextView quyreport;
    AnyChartView quarreport;
    private DatabaseReference mdata = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quar_report);
        AnhXa();

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
        Calendar calendar = Calendar.getInstance();
        String quy = dateformat.format(calendar.getTime());
        quyreport.setText(quy);

        quyreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog d = new Dialog(view.getContext());
                d.setContentView(R.layout.dialog_thongketheongay);
                DatePicker datePicker = d.findViewById(R.id.time_thongkengay);
                Button xacnhan = d.findViewById(R.id.btn_xemthongkengay);
                xacnhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int year = datePicker.getYear();
                        quyreport.setText(year + "");
                        d.cancel();
                    }
                });
                d.show();
            }
        });

        xem_quy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cartesian cartesian = AnyChart.column();
                List<DataEntry> data1 = new ArrayList<>();
                mdata.child("Đơn hàng").child("Thông tin").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int tong_q1 = 0, tong_q2 = 0, tong_q3 = 0, tong_q4 = 0;
                        for(DataSnapshot snap : snapshot.getChildren()){
                            Receipt re = snap.getValue(Receipt.class);
                            String str_ngaydat = re.getNgaydat();
                            String temp_year = str_ngaydat.substring(6,10);
                            String str_trangthai = re.getTrangthai();
                            if(temp_year.contains(quyreport.getText().toString()) && str_trangthai.contains("Hoàn thành")){
                                String temp_thang = str_ngaydat.substring(3,5);
                                if(temp_thang.contains("01") || temp_thang.contains("02") || temp_thang.contains("03")){
                                    String str_tong = snap.child("tongtien").getValue().toString();
                                    tong_q1 += Integer.parseInt(str_tong.replace(".",""));
                                } else if(temp_thang.contains("04") || temp_thang.contains("05") || temp_thang.contains("06")){
                                    String str_tong = snap.child("tongtien").getValue().toString();
                                    tong_q2 += Integer.parseInt(str_tong.replace(".",""));
                                } else if(temp_thang.contains("07") || temp_thang.contains("08") || temp_thang.contains("09")){
                                    String str_tong = snap.child("tongtien").getValue().toString();
                                    tong_q3 += Integer.parseInt(str_tong.replace(".",""));
                                } else {
                                    String str_tong = snap.child("tongtien").getValue().toString();
                                    tong_q4 += Integer.parseInt(str_tong.replace(".",""));
                                }
                            }
                        }
                        data1.add(new ValueDataEntry("Quý 1" , tong_q1));
                        data1.add(new ValueDataEntry("Quý 2", tong_q2));
                        data1.add(new ValueDataEntry("Quý 3", tong_q3));
                        data1.add(new ValueDataEntry("Quý 4", tong_q4));

                        Column column = cartesian.column(data1);
                        column.unselect(0);
                        column.tooltip()
                                .titleFormat("{%X}")
                                .position(Position.CENTER_BOTTOM)
                                .anchor(Anchor.CENTER_BOTTOM)
                                .offsetX(0d)
                                .offsetY(5d)
                                .format("{%Value}{groupsSeparator: }");
                        cartesian.animation(true);
                        cartesian.yScale().minimum(0d);
                        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");
                        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                        cartesian.interactivity().hoverMode(HoverMode.BY_X);
                        quarreport.setChart(cartesian);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void AnhXa() {
        xem_quy = (Button) findViewById(R.id.report_xemreportquy);
        quyreport = (TextView) findViewById(R.id.report_yearreport);
        quarreport = (AnyChartView) findViewById(R.id.any_chart_view_quy);
    }
}