package com.example.coffeebakeryadmin.ListBook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeebakeryadmin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.Holder> {
    private List mBook;
    private Context context;
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    public BookAdapter(List mBook, Context context){
        this.mBook = mBook;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_book, parent, false);
        return new BookAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Book b = (Book) mBook.get(position);
        holder.tieude.setText(b.getTieude());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Bạn muốn xóa chính sách này?")
                        .setNeutralButton("Quay lại", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mData.child("ChinhSach").child(b.getTieude()).removeValue();
                                Toast.makeText(context, "Xóa thànhc công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context,BookActivity.class);
                                context.startActivity(intent);
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, AddBookActivity.class);
                intent.putExtra("TIEUDE",b.getTieude());
                intent.putExtra("NOIDUNG",b.getNoidung());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBook.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tieude;
        ImageView delete;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tieude = itemView.findViewById(R.id.book_tieude);
            delete = itemView.findViewById(R.id.book_deletebook);
        }
    }
}
