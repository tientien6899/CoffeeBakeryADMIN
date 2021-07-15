package com.example.coffeebakeryadmin.ListBook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeebakeryadmin.AdminActivity;
import com.example.coffeebakeryadmin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddBookActivity extends AppCompatActivity {

    EditText tieude, noidung;
    Button save;
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        AnhXa();

        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String ngay = dateformat.format(calendar.getTime());


        Intent intent = getIntent();
        String td = intent.getStringExtra("TIEUDE");
        String nd = intent.getStringExtra("NOIDUNG");

        if(td != null && nd != null){
            tieude.setText(td);
            noidung.setText(nd);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Bạn muốn thêm chính sách này chứ!")
                        .setNeutralButton("Quay lại", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Book b = new Book();
                                b.tieude = tieude.getText().toString();
                                b.noidung = noidung.getText().toString();
                                b.ngaydang = ngay;

                                mData.child("ChinhSach").child(b.getTieude()).setValue(b);
                                Toast.makeText(AddBookActivity.this, "Thêm chính sách thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(view.getContext(), BookActivity.class);
                                startActivity(intent);
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });

    }

    private void AnhXa() {
        tieude = findViewById(R.id.detailbook_tieude);
        noidung = findViewById(R.id.detailbook_noidung);
        save = findViewById(R.id.img_savebook);
    }
}