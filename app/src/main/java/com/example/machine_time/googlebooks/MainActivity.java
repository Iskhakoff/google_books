package com.example.machine_time.googlebooks;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.machine_time.googlebooks.net.GoogleBookApi;
import com.example.machine_time.googlebooks.net.model.Book;
import com.example.machine_time.googlebooks.net.model.Example;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    @BindView(R.id.searchingBookEt)
    EditText searchingBookEt;
    @BindView(R.id.findBookBtn)
    Button findBookBtn;
    @BindView(R.id.showMore)
    Button showMore;
    @BindView(R.id.booksLv)
    RecyclerView booksLv;
    @BindView(R.id.searchingBook)
    TextInputLayout searchingBook;

    RecyclerView.LayoutManager layoutManager;

    ArrayList<Book> books = new ArrayList<>();
    AdapterList adapterList;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        showMore.setBackgroundColor(Color.TRANSPARENT);
        findBookBtn.setOnClickListener(this);
        showMore.setOnClickListener(this);

        searchingBookEt.setOnFocusChangeListener(this);

    }


    @Override
    public void onClick(View v) {
        GoogleBookApi googleBookApi = GoogleBookApi.getInstanse();
        switch (v.getId()){
            case R.id.findBookBtn:
                showMore.setVisibility(View.VISIBLE);
                hideKeyBoard();

                subscription = googleBookApi.getBook(searchingBookEt.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(mainThread())
                        .subscribe(new Subscriber<Example>() {
                            @Override
                            public void onCompleted() {
                                Log.d("MainActivity", "onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("MainActivity", "onError => " + e.getMessage());
                            }

                            @Override
                            public void onNext(Example response) {
                                Log.d("MainActivity", "onNext => " + response);
                                try{
                                    if(response.getItems().size() > 0){
                                        if(booksLv.getAdapter() == null){
                                            fillModel(response, response.getItems().size());
                                            adapterList = new AdapterList(books, new AdapterList.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(Book item) {
                                                    Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            layoutManager = new LinearLayoutManager(getApplicationContext());
                                            booksLv.setLayoutManager(layoutManager);
                                        }else{
                                            books.clear();
                                            fillModel(response, response.getItems().size());
                                            adapterList = new AdapterList(books, new AdapterList.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(Book item) {
                                                    Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            layoutManager = new LinearLayoutManager(getApplicationContext());
                                            booksLv.setLayoutManager(layoutManager);
                                        }
                                        booksLv.setAdapter(adapterList);
                                    }
                                }catch (NullPointerException e){
                                    Log.e("MainActivity", "NullPointerException => " + e.getMessage());
                                }
                            }
                        });
                break;
            case R.id.showMore:
                subscription = googleBookApi.showMore(searchingBookEt.getText().toString(), books.size(), 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(mainThread())
                        .subscribe(new Subscriber<Example>() {
                            @Override
                            public void onCompleted() {
                                Log.d("MainActivity", "onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("MainActivity", "onError => " + e.getMessage());
                            }

                            @Override
                            public void onNext(Example response) {
                                Log.d("MainActivity", "onNext => " + response);
                                try{
                                    if(response.getItems().size() > 0){
                                        fillModel(response, response.getItems().size());
                                        adapterList = new AdapterList(books, new AdapterList.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(Book item) {
                                                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        layoutManager = new LinearLayoutManager(getApplicationContext());
                                        booksLv.setLayoutManager(layoutManager);
                                        booksLv.setAdapter(adapterList);

                                    }else{
                                        showMore.setVisibility(View.INVISIBLE);
                                    }
                                }catch (NullPointerException e){
                                    Log.e("MainActivity", "NullPointerException => " + e.getMessage());
                                }
                            }
                        });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    public void fillModel(Example example, int countItems){
        for (int i = 0; i < countItems; i++) {
            books.add(new Book(example.getItems().get(i).getId(),
                    example.getItems().get(i).getVolumeInfo().getTitle(),
                    example.getItems().get(i).getVolumeInfo().getSubtitle(),
                    example.getItems().get(i).getVolumeInfo().getAuthors(),
                    example.getItems().get(i).getVolumeInfo().getPublishedDate(),
                    example.getItems().get(i).getVolumeInfo().getImageLinks()));
        }
    }
    private void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if( v != searchingBookEt && !searchingBookEt.getText().toString().equalsIgnoreCase("")){
            searchingBook.setErrorEnabled(true);
            searchingBook.setError(getResources().getString(R.string.login_error));
        }else{
            searchingBook.setErrorEnabled(false);
        }
    }
}
