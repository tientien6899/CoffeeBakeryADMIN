package com.example.coffeebakeryadmin.List_Post;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.coffeebakeryadmin.List_Product.AddProductActivity;
import com.example.coffeebakeryadmin.List_Product.ListProductActivity;
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

public class AddPostActivity extends AppCompatActivity {

    ImageView img_backdsbv, addpost, imgpost;
    EditText tieude, noidung;
    FirebaseStorage storage;
    StorageReference storageReference;
    public Uri path;
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        AnhXa();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String ngay = dateformat.format(calendar.getTime());

        mData = FirebaseDatabase.getInstance().getReference();

        imgpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 22);
            }
        });

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String td = tieude.getText().toString();
                String nd = noidung.getText().toString();

                Post p = new Post();
                p.tieude = td;
                p.noidung = nd;
                p.ngaydang = ngay;

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc muốn thêm bài viết này chứ ?");
                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (path != null) {
                            StorageReference ref = storageReference.child("Post").child(p.getTieude() + ".png");
                            final ProgressDialog progressDialog = new ProgressDialog(AddPostActivity.this);
                            progressDialog.setTitle("Uploading...");
                            progressDialog.show();
                            ref.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //Lấy đường link của hình ảnh
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            p.hinhanh = "" + uri.toString();
                                            mData.child("Post").child(p.tieude).setValue(p);
                                        }
                                    });
                                    progressDialog.dismiss();
                                    Toast.makeText(AddPostActivity.this, "Thêm bài viết thành công !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddPostActivity.this, ListPostActivity.class);
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
                                Intent intent = new Intent(AddPostActivity.this, ListPostActivity.class);
                                startActivity(intent);
                            }
                        }
                );
                builder.show();

            }
        });

        img_backdsbv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPostActivity.this, ListPostActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            path = uri;
            imgpost.setImageURI(uri);
            imgpost.setBackgroundColor(Color.WHITE);
        }
    }

    private void AnhXa() {
        img_backdsbv = findViewById(R.id.img_BackDSBV);
        addpost = (ImageView) findViewById(R.id.img_addPost);
        tieude = findViewById(R.id.edt_TieuDePost);
        noidung = findViewById(R.id.edt_NoiDungPost);
        imgpost = findViewById(R.id.img_HinhanhPost);
    }
}