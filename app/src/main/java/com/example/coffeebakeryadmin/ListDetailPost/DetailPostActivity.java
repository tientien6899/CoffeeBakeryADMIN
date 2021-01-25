package com.example.coffeebakeryadmin.ListDetailPost;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.coffeebakeryadmin.List_Post.ListPostActivity;
import com.example.coffeebakeryadmin.List_Post.Post;
import com.example.coffeebakeryadmin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailPostActivity extends AppCompatActivity {

    EditText tieude, noidung;
    TextView ngaydang;
    ImageView hinhanh, updatePost, deletePost;
    FirebaseStorage storage;
    StorageReference storageReference;
    public Uri path;
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        AnhXa();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mData = FirebaseDatabase.getInstance().getReference();

        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String ngay = dateformat.format(calendar.getTime());

        Intent intent = getIntent();
        String td = intent.getStringExtra("TIEUDE");
        String nd = intent.getStringExtra("NOIDUNG");
        String ng = intent.getStringExtra("NGAYDANG");
        String ha = intent.getStringExtra("HINHANH");

        tieude.setText(td);
        noidung.setText(nd);
        ngaydang.setText("Ngày đăng: " + ng);
        Glide.with(this).load(ha).into(hinhanh);

        hinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 22);
            }
        });

        updatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp_tieude = tieude.getText().toString();
                String temp_noidung = noidung.getText().toString();

                Post p = new Post();
                p.setTieude(temp_tieude);
                p.setNoidung(temp_noidung);
                p.setNgaydang(ngay);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("Thông báo");
                builder.setMessage("Xác nhận cập nhật bài viết?");
                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mData.child("Post").child(td).removeValue();
                        if (path != null) {
                            StorageReference ref = storageReference.child("Post").child(p.getTieude() + ".png");
                            final ProgressDialog progressDialog = new ProgressDialog(DetailPostActivity.this);
                            progressDialog.setTitle("Uploading...");
                            progressDialog.show();
                            ref.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //Lấy đường link của hình ảnh
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            p.setHinhanh("" + uri.toString());
                                            mData.child("Post").child(p.getTieude()).setValue(p);
                                        }
                                    });
                                    progressDialog.dismiss();
                                    Toast.makeText(DetailPostActivity.this, "Cập nhật bài viết thành công!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(DetailPostActivity.this, ListPostActivity.class);
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
                                Intent intent = new Intent(DetailPostActivity.this, ListPostActivity.class);
                                startActivity(intent);
                            }
                        }
                );
                builder.show();

            }
        });

        deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("Thông báo");
                builder.setMessage("Xác nhận xóa bài viết?");
                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mData.child("Post").child(td).removeValue();
                        Toast.makeText(DetailPostActivity.this, "Xóa bài viết thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailPostActivity.this, ListPostActivity.class);
                        startActivity(intent);
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(DetailPostActivity.this, ListPostActivity.class);
                                startActivity(intent);
                            }
                        }
                );
                builder.show();
            }
        });

    }

    private void AnhXa() {
        tieude = findViewById(R.id.detailpost_tieude);
        noidung = findViewById(R.id.detailpost_noidung);
        hinhanh = findViewById(R.id.detailpost_hinhanh);
        ngaydang = findViewById(R.id.detailpost_ngaydang);
        updatePost = findViewById(R.id.img_UpdatePost);
        deletePost = findViewById(R.id.img_DeletePost);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            path = uri;
            hinhanh.setImageURI(uri);
            hinhanh.setBackgroundColor(Color.WHITE);
        }
    }
}