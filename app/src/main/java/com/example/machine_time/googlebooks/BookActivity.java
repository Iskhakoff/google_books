package com.example.machine_time.googlebooks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.machine_time.googlebooks.net.GoogleBookApi;
import com.example.machine_time.googlebooks.net.model.Book;
import com.example.machine_time.googlebooks.net.model.Example;
import com.example.machine_time.googlebooks.net.model.Item;
import com.example.machine_time.googlebooks.net.model.VolumeInfo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class BookActivity extends AppCompatActivity {

    @BindView(R.id.posterThumb)
    ImageView posterThumb;
    @BindView(R.id.titleBookTv)
    TextView titleBookTv;
    @BindView(R.id.subtitleBookTv)
    TextView subtitleBookTv;
    @BindView(R.id.authorBookTv)
    TextView authorBookTv;
    @BindView(R.id.publishDateBookTv)
    TextView publishDateBookTv;
    @BindView(R.id.descriptionOfBook)
    TextView descriptionOfBook;
    @BindView(R.id.languageOfBook)
    TextView languageOfBook;
    @BindView(R.id.categoryOfBook)
    TextView categoryOfBook;
    @BindView(R.id.publisherOfBook)
    TextView publisherOfBook;
    @BindView(R.id.publishDateOfBook)
    TextView publishDateOfBook;
    @BindView(R.id.pageCountOfBook)
    TextView pageCountOfBook;

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent getId = getIntent();
        String bookId = getId.getStringExtra("bookId");



        GoogleBookApi googleBookApi = GoogleBookApi.getInstanse();
        subscription = googleBookApi.getBookById(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<Item>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MainActivity", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity", "onError => " + e.getMessage());
                    }

                    @Override
                    public void onNext(Item response) {
                        Log.d("MainActivity", "onNext => " + response);
                        try{
                            if(response.getVolumeInfo().getImageLinks() != null){
                                Picasso.get().load(response.getVolumeInfo().getImageLinks().getThumbnail()).into(posterThumb);
                            }
                            if(response.getVolumeInfo().getTitle() != null){
                                titleBookTv.setText(response.getVolumeInfo().getTitle());
                            }
                            if(response.getVolumeInfo().getSubtitle() != null){
                                subtitleBookTv.setText(response.getVolumeInfo().getSubtitle());
                            }
                            if(response.getVolumeInfo().getAuthors() != null){
                                for (int i = 0; i < response.getVolumeInfo().getAuthors().size(); i++) {
                                    if(i == 0){
                                        authorBookTv.setText(response.getVolumeInfo().getAuthors().get(i));
                                    }else if(i > 0){
                                        authorBookTv.setText(authorBookTv.getText().toString() + ", " + response.getVolumeInfo().getAuthors().get(i));
                                    }
                                }
                            }else{
                                authorBookTv.setText("Автор неизвестен");
                            }

                            if(response.getVolumeInfo().getPublishedDate() != null){
                                publishDateBookTv.setText(response.getVolumeInfo().getPublishedDate());
                                publishDateOfBook.setText(response.getVolumeInfo().getPublishedDate());
                            }else{
                                publishDateBookTv.setText("Дата неизвестна");
                                publishDateOfBook.setText("Дата неизвестна");
                            }

                            if(response.getVolumeInfo().getDescription() != null){
                                descriptionOfBook.setText(response.getVolumeInfo().getDescription());
                            }else{
                                descriptionOfBook.setText("Описание отсутствует");
                            }

                            if(response.getVolumeInfo().getLanguage() != null){
                                languageOfBook.setText(response.getVolumeInfo().getLanguage());
                            }else{
                                languageOfBook.setText("Неизвестно");
                            }

                            if(response.getVolumeInfo().getCategories() != null){
                                for (int i = 0; i < response.getVolumeInfo().getCategories().size(); i++) {
                                    if(i == 0){
                                        categoryOfBook.setText(response.getVolumeInfo().getCategories().get(i));
                                    }else if(i > 0){
                                        categoryOfBook.setText(categoryOfBook.getText() + ", " + response.getVolumeInfo().getCategories().get(i));
                                    }
                                }
                            }else{
                                categoryOfBook.setText("Без категории");
                            }

                            if(response.getVolumeInfo().getPublisher() != null){
                                publisherOfBook.setText(response.getVolumeInfo().getPublisher());
                            }else{
                                publisherOfBook.setText("Издатель неизвестен");
                            }

                            if(response.getVolumeInfo().getPageCount() != null){
                                pageCountOfBook.setText(response.getVolumeInfo().getPageCount());
                            }else{
                                pageCountOfBook.setText("Неизвестно");
                            }

                        }catch (NullPointerException e){
                            Log.e("MainActivity", "NullPointerException => " + e.getMessage());
                        }
                    }
                });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                result = true;
                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }
}
