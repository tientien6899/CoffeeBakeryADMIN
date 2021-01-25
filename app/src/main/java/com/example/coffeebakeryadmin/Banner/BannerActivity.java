package com.example.coffeebakeryadmin.Banner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.coffeebakeryadmin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class BannerActivity extends AppCompatActivity {

    ImageView imgposter, addposter, back;
    EditText nameposter;
    FirebaseStorage storage;
    StorageReference storageReference;
    public Uri path;
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        AnhXa();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mData = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        String ten = intent.getStringExtra("NAME");
        String link = intent.getStringExtra("LINK");

        if(ten != null && link != null){
            nameposter.setText(ten);
            Glide.with(this).load(link).into(imgposter);
            imgposter.setBackgroundColor(Color.WHITE);
        }

        imgposter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 22);
            }
        });

        addposter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Banner bn = new Banner();
                bn.ten = nameposter.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc muốn thêm Poster này chứ ?");
                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (path != null) {
                            StorageReference ref = storageReference.child("Poster").child(bn.getTen() + ".png");
                            final ProgressDialog progressDialog = new ProgressDialog(BannerActivity.this);
                            progressDialog.setTitle("Uploading...");
                            progressDialog.show();
                            ref.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //Lấy đường link của hình ảnh
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            bn.link = "" + uri.toString();
                                            mData.child("Poster").child(bn.getTen()).setValue(bn);
                                        }
                                    });
                                    progressDialog.dismiss();
                                    Toast.makeText(BannerActivity.this, "Thêm Poster thành công !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(BannerActivity.this, ListBannerActivity.class);
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
                                Intent intent = new Intent(BannerActivity.this, ListBannerActivity.class);
                                startActivity(intent);
                            }
                        }
                );
                builder.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(), ListBannerActivity.class);
                startActivity(intent1);
            }
        });

    }

    private void AnhXa() {
        imgposter = (ImageView) findViewById(R.id.img_HinhAnhPoster);
        addposter = (ImageView) findViewById(R.id.btn_LuuPoster);
        nameposter = (EditText) findViewById(R.id.edt_TenPoster);
        back = findViewById(R.id.img_Back_to_banner);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            path = uri;
            imgposter.setImageURI(uri);
            imgposter.setBackgroundColor(Color.WHITE);
        }
    }
}