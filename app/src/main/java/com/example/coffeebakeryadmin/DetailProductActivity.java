package com.example.coffeebakeryadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailProductActivity extends AppCompatActivity {

    EditText ten, ma, s, m, l, km, mt;
    TextView dm;
    ImageView img;
    Button capnhat;
    ImageButton xoa, backdssp;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri pathchitiet;
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mData = FirebaseDatabase.getInstance().getReference();

        AnhXa();

        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String ngay = dateformat.format(calendar.getTime());

        Intent intent = getIntent();
        String dmuc = intent.getStringExtra("DANHMUC");
        String tenSP = intent.getStringExtra("TENSP");
        String maSP = intent.getStringExtra("MASP");
        String S = intent.getStringExtra("GIAS");
        String M = intent.getStringExtra("GIAM");
        String L = intent.getStringExtra("GIAL");
        String KM = intent.getStringExtra("GIAKM");
        String link = intent.getStringExtra("LINK");
        String nDung = intent.getStringExtra("MOTA");

        dm.setText(dmuc + "");
        ten.setText(tenSP + "");
        ma.setText(maSP + "");
        s.setText(S + "");
        m.setText(M + "");
        l.setText(L + "");
        km.setText(KM + "");
        Glide.with(this).load(link).into(img);
        img.setBackgroundColor(Color.WHITE);
        mt.setText(nDung + "");

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent1,22);
            }
        });

        backdssp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProductActivity.this, ListProductActivity.class);
                startActivity(intent);
            }
        });

        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn đã chắc về các thông tin trên chứ ?");
                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(pathchitiet != null){
                            DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
                            mData.child("SanPham").child(maSP).removeValue();
                            mData.child(dmuc).child(maSP).removeValue();

                            StorageReference ref = storageReference.child("Image").child(dmuc).child(tenSP + ".png");
                            ref.delete();

                            Product sp = new Product();
                            sp.danhmuc = dm.getText().toString().trim();
                            sp.tensp = ten.getText().toString().trim();
                            sp.masp = ma.getText().toString().trim();
                            sp.giaS = s.getText().toString().trim();
                            sp.giaM = m.getText().toString().trim();
                            sp.giaL = l.getText().toString().trim();
                            sp.giaKM = km.getText().toString().trim();
                            sp.mota = mt.getText().toString().trim();
                            sp.ngaydang = ngay;

                            StorageReference ref1 = storageReference.child("Image").child(sp.getDanhmuc()).child(sp.getTensp() + ".png");
                            final ProgressDialog progressDialog= new ProgressDialog(DetailProductActivity.this);
                            progressDialog.setTitle("Uploading...");
                            progressDialog.show();
                            ref1.putFile(pathchitiet).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //Lấy đường link của hình ảnh
                                    ref1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            sp.link = "" + uri.toString();
                                            mData.child("SanPham").child(sp.masp).setValue(sp);
                                            mData.child(sp.danhmuc).child(sp.masp).setValue(sp);
                                        }
                                    });
                                    progressDialog.dismiss();
                                    Toast.makeText(DetailProductActivity.this, "Cập nhật sản phẩm thành công !", Toast.LENGTH_SHORT).show();
                                    Intent intent2 = new Intent(DetailProductActivity.this, ListProductActivity.class);
                                    startActivity(intent2);
                                }
                            })
                                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                            progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                        }
                                    });
                        }
                    }
                });
                builder.setPositiveButton("Quay lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
            }
        });

        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn thật sự muốn xóa sản phẩm này ?");
                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
                        mData.child("SanPham").child(maSP).removeValue();
                        mData.child(dmuc).child(maSP).removeValue();


                        StorageReference ref = storageReference.child("Image").child(dmuc).child(tenSP + ".png");
                        ref.delete();

                        Toast.makeText(DetailProductActivity.this, "Xóa sản phẩm thành công !", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(DetailProductActivity.this, ListProductActivity.class);
                        startActivity(intent2);
                    }
                });
                builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            pathchitiet = uri;
            img.setImageURI(uri);
            img.setBackgroundColor(Color.WHITE);
        }
    }

    private void AnhXa() {
        dm = (TextView) findViewById(R.id.txt_chitietDanhmucSP);
        ten = (EditText) findViewById(R.id.edt_chitietTenSP);
        ma = (EditText) findViewById(R.id.edt_chitietMaSP);
        s = (EditText) findViewById(R.id.edt_chitietGiaS);
        m = (EditText) findViewById(R.id.edt_chitietGiaM);
        l = (EditText) findViewById(R.id.edt_chitietGiaL);
        km = (EditText) findViewById(R.id.edt_chitietGiaKM);
        img = (ImageView) findViewById(R.id.img_chitietHinhanhSP);
        mt = (EditText) findViewById(R.id.edt_chitietMotaSP);
        capnhat = (Button) findViewById(R.id.btn_CapnhatSP);
        xoa = (ImageButton) findViewById(R.id.btn_Xoa);
        backdssp = (ImageButton) findViewById(R.id.img_BackDSSP);
    }
}