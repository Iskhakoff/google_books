package com.example.machine_time.googlebooks;


import android.content.Context;
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
import butterknife.ButterKnife;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(Book item);
    }

    Context context;
    LayoutInflater layoutInflater;

    private List<Book> books;

    private final OnItemClickListener listener;

    public AdapterList(List<Book> books, OnItemClickListener listener) {
        this.books = books;
        this.listener = listener;
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
        holder.bind(books.get(position), listener);
    }



    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.poster)
        ImageView poster;
        @BindView(R.id.titleTv)
        TextView titleTv;
        @BindView(R.id.authorTv)
        TextView authorTv;
        @BindView(R.id.publishDateTv)
        TextView publishDateTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Book book, final OnItemClickListener listener){

            if(book.getThumbSmall() != null){
                Picasso.get().load(book.getThumbSmall()).into(poster);
            }

            if(book.getTitle().length() > 60){
                int count = 0;
                int index = 0;
                for (int i = 0; i < book.getTitle().length(); i++) {
                    if(book.getTitle().charAt(i) == ' '){
                        count++;
                        if(count == 3){
                            index = i;
                            break;
                        }
                    }
                }

                String title = book.getTitle().substring(0, index);
                titleTv.setText(title);
            }else{
                titleTv.setText(book.getTitle());
            }

            if(book.getSubtitle() != null){
                titleTv.setText(titleTv.getText() + ": " + book.getSubtitle());
            }

            if(book.getAuthors() != null){
                if(book.getAuthors().size() > 1){
                    for (int i = 0; i < book.getAuthors().size(); i++) {
                        if(i == 0){
                            authorTv.setText(book.getAuthors().get(i));
                        }else if(i > 0){
                            authorTv.setText(authorTv.getText().toString() + ", " + book.getAuthors().get(i));
                        }
                    }
                }else{
                    authorTv.setText(book.getAuthors().get(0));
                }
            }else{
                authorTv.setText("Автор неизвестен");
            }
            if(book.getDate() != null){
                publishDateTv.setText(book.getDate());
            }else{
                publishDateTv.setText("Дата неизвестна");
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(book);
                }
            });
        }


    }


}
