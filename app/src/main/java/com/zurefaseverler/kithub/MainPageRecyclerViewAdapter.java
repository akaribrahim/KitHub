package com.zurefaseverler.kithub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainPageRecyclerViewAdapter extends RecyclerView.Adapter<MainPageRecyclerViewAdapter.ViewHolder>{

    @NonNull
    private Context context;
    private List<MainPageBook> list;

    public MainPageRecyclerViewAdapter(@NonNull Context context, List<MainPageBook> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_page_book_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bookimage.setImageResource(R.drawable.seytani_uyandirma);        //resim işi nasıl olacak ibo???
        holder.bookname.setText(list.get(position).getBookname());
        holder.authorname.setText(list.get(position).getAuthorname());
        holder.discountamount.setText(list.get(position).getDiscountamount());
        holder.price.setText(list.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView bookimage;
        public TextView bookname,authorname,discountamount,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookimage = itemView.findViewById(R.id.BookImageMainPage);
            bookname = itemView.findViewById(R.id.BookNameMainPage);
            authorname = itemView.findViewById(R.id.AuthorNameMainPage);
            discountamount = itemView.findViewById(R.id.discountAmountMainPage);
            price = itemView.findViewById(R.id.mainPagePrice);

        }
    }
}