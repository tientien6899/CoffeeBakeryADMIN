package com.example.coffeebakeryadmin.List_Product;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeebakeryadmin.R;
import com.example.coffeebakeryadmin.ThongBao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddProductActivity extends AppCompatActivity {

    ImageView hinhanhsp, luusp, backsp ;
    ImageButton backhome;
    TextView danhmucsp, ten_giaM, ten_giaL, txt_giam, txt_gial;
    EditText tensp, masp, giaS, giaM, giaL, giaKM, mota;
    CheckBox sphammoi;
    Intent intent;
    FirebaseStorage storage;
    StorageReference storageReference;
    public Uri path;
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String ngay = dateformat.format(calendar.getTime());

        mData = FirebaseDatabase.getInstance().getReference();
        AnhXa();
        kiemtrathongtin();

        //Chọn danh mục sản phẩm
        danhmucsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(v.getContext());
                d.setContentView(R.layout.custom_danhmucsp);

                RadioButton cafetra = (RadioButton) d.findViewById(R.id.rb_CapheTra);
                RadioButton daxay = (RadioButton) d.findViewById(R.id.rb_Daxay);
                RadioButton nuocepsinhto = (RadioButton) d.findViewById(R.id.rb_NuocepSinhto);
                RadioButton banhngot = (RadioButton) d.findViewById(R.id.rb_Banhngot);
                RadioButton topping = (RadioButton) d.findViewById(R.id.rb_Topping);

                Button xacnhan = (Button) d.findViewById(R.id.btn_XacnhanDM);
                xacnhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cafetra.isChecked()) {
                            danhmucsp.setText(cafetra.getText().toString().trim());
                            giaM.setText("");
                            giaL.setText("");
                            txt_giam.setVisibility(View.VISIBLE);
                            txt_gial.setVisibility(View.VISIBLE);
                            giaM.setVisibility(View.VISIBLE);
                            giaL.setVisibility(View.VISIBLE);
                            d.dismiss();
                        }
                        else if (daxay.isChecked()) {
                            danhmucsp.setText(daxay.getText().toString().trim());
                            giaM.setText("");
                            giaL.setText("");
                            txt_giam.setVisibility(View.VISIBLE);
                            txt_gial.setVisibility(View.VISIBLE);
                            giaM.setVisibility(View.VISIBLE);
                            giaL.setVisibility(View.VISIBLE);
                            d.dismiss();
                        }
                        else if (nuocepsinhto.isChecked()) {
                            danhmucsp.setText(nuocepsinhto.getText().toString().trim());
                            giaM.setText("");
                            giaL.setText("");
                            txt_giam.setVisibility(View.VISIBLE);
                            txt_gial.setVisibility(View.VISIBLE);
                            giaM.setVisibility(View.VISIBLE);
                            giaL.setVisibility(View.VISIBLE);
                            d.dismiss();
                        }
                        else if(banhngot.isChecked()) {
                            danhmucsp.setText(banhngot.getText().toString().trim());
                            giaM.setText("0");
                            giaL.setText("0");
                            txt_giam.setVisibility(View.GONE);
                            txt_gial.setVisibility(View.GONE);
                            giaM.setVisibility(View.GONE);
                            giaL.setVisibility(View.GONE);
                            d.dismiss();
                        }
                        else if(topping.isChecked()) {
                            danhmucsp.setText(topping.getText().toString().trim());
                            giaM.setText("0");
                            giaL.setText("0");
                            txt_giam.setVisibility(View.GONE);
                            txt_gial.setVisibility(View.GONE);
                            giaM.setVisibility(View.GONE);
                            giaL.setVisibility(View.GONE);
                            d.dismiss();
                        }
                    }
                });

                d.show();
            }
        });

        backsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(AddProductActivity.this, ListProductActivity.class);
                startActivity(intent);
            }
        });

        hinhanhsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 22);
            }
        });

        luusp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product sp = new Product();
                sp.danhmuc = danhmucsp.getText().toString().trim();
                sp.tensp = tensp.getText().toString().trim();
                sp.masp = masp.getText().toString().trim();
                sp.giaS = giaS.getText().toString().trim();
                sp.giaM = giaM.getText().toString().trim();
                sp.giaL = giaL.getText().toString().trim();
                sp.giaKM = giaKM.getText().toString().trim();
                sp.mota = mota.getText().toString().trim();
                if(sphammoi.isChecked())
                    sp.spmoi = true;
                else
                    sp.spmoi = false;
                sp.luotmua = 0;
                sp.luotyeuthich = 0;
                sp.ngaydang = ngay;

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc muốn thêm sản phẩm này chứ ?");
                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (path != null) {
                            StorageReference ref = storageReference.child("Image").child(sp.getDanhmuc()).child(sp.getTensp() + ".png");
                            final ProgressDialog progressDialog = new ProgressDialog(AddProductActivity.this);
                            progressDialog.setTitle("Uploading...");
                            progressDialog.show();
                            ref.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //Lấy đường link của hình ảnh
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            sp.link = "" + uri.toString();
                                            mData.child("Sản Phẩm").child(sp.masp).setValue(sp);
                                            mData.child(sp.danhmuc).child(sp.masp).setValue(sp);
                                        }
                                    });
                                    progressDialog.dismiss();

                                    ThongBao noti = new ThongBao();
                                    noti.setTieude("Thông báo sản phẩm mới!");
                                    noti.setNoidung("Thử ngay " + sp.getTensp() + " đi nào!");
                                    mData.child("Thông báo").child(noti.getTieude()).child(sp.getTensp()).setValue(noti);

                                    Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thành công !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddProductActivity.this, ListProductActivity.class);
                                    startActivity(intent);
                                }
                            })
                                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                        }
                                    });
                        }
                    }
                });
                builder.setPositiveButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                intent = new Intent(AddProductActivity.this, ListProductActivity.class);
                                startActivity(intent);
                            }
                        }
                );
                builder.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            path = uri;
            hinhanhsp.setImageURI(uri);
            hinhanhsp.setBackgroundColor(Color.WHITE);
        }
    }

    private void kiemtrathongtin() {
        tensp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    tensp.requestFocus();
                    tensp.setError("Vui lòng nhập tên sản phẩm !");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        masp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    masp.requestFocus();
                    masp.setError("Vui lòng nhập mã sản phẩm !");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void AnhXa() {
        backsp = (ImageView) findViewById(R.id.img_BackDSSP);
        danhmucsp = (TextView) findViewById(R.id.txt_DanhmucSP);
        tensp = (EditText) findViewById(R.id.edt_TenSP);
        masp = (EditText) findViewById(R.id.edt_MaSP);
        giaS = (EditText) findViewById(R.id.edt_GiaS);
        giaM = (EditText) findViewById(R.id.edt_GiaM);
        giaL = (EditText) findViewById(R.id.edt_GiaL);
        giaKM = (EditText) findViewById(R.id.edt_GiaKM);
        mota = (EditText) findViewById(R.id.edt_MotaSP);
        hinhanhsp = (ImageView) findViewById(R.id.img_HinhanhSP);
        luusp = (ImageView) findViewById(R.id.btn_LuuSP);
        ten_giaM = findViewById(R.id.txt_GiaM);
        ten_giaL = findViewById(R.id.txt_GiaL);
        sphammoi = findViewById(R.id.chb_Spmoi);
        txt_giam = findViewById(R.id.txt_GiaM);
        txt_gial = findViewById(R.id.txt_GiaL);
    }
}