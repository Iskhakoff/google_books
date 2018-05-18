package com.example.machine_time.googlebooks;


import android.content.Context;
import android.media.Image;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.machine_time.googlebooks.net.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder>{

    Context context;
    LayoutInflater layoutInflater;

    private List<Book> books;

    public AdapterList(List<Book> books) {
        this.books = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = books.get(position);
        if(book.getThumbSmall() != null){
            Picasso.get().load(book.getThumbSmall()).into(holder.poster);
        }
        holder.titleTv.setText(book.getTitle());
        if(book.getAuthors() != null){
            if(book.getAuthors().size() > 1){
                for (int i = 0; i < book.getAuthors().size(); i++) {
                    if(i == 0){
                        holder.authorTv.setText(book.getAuthors().get(i));
                    }else{
                        holder.authorTv.setText(holder.authorTv.getText().toString() + ", " + book.getAuthors().get(i));
                    }
                }
            }else{
                holder.authorTv.setText(book.getAuthors().get(0));
            }
        }else{
            holder.authorTv.setText("Автор неизвестен");
        }
        if(book.getDate() != null){
            holder.publishDateTv.setText(book.getDate());
        }else{
            holder.publishDateTv.setText("Дата неизвестна");
        }
    }



    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView poster;
        TextView titleTv;
        TextView authorTv;
        TextView publishDateTv;
//        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView)itemView.findViewById(R.id.poster);
            titleTv = (TextView)itemView.findViewById(R.id.titleTv);
            authorTv = (TextView)itemView.findViewById(R.id.authorTv);
            publishDateTv = (TextView)itemView.findViewById(R.id.publishDateTv);

        }
    }


}

