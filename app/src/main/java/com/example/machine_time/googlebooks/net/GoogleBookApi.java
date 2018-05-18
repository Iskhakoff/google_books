package com.example.machine_time.googlebooks.net;

import com.example.machine_time.googlebooks.net.model.Example;
import com.example.machine_time.googlebooks.net.model.VolumeInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.Version;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public class GoogleBookApi {

    private static final String URL = "https://www.googleapis.com/books/v1/";
    private static final String VOLUME = "volumes";

    private Retrofit retrofit;

    private GoogleBookApi(){
        init();
    }

    private void init(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request requestWithUserAgent = originalRequest.newBuilder()
                            .removeHeader("User-Agent")
                            .header("User-Agent", Version.userAgent() + "/" + "android")
                            .build();
                    return chain.proceed(requestWithUserAgent);
                })
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static GoogleBookApi getInstanse(){
        return GoogleBookApi.GoogleBookApiHolder.getInstanse();
    }

    public Observable<Example> getBook(String book){
        GoogleBookApiEndPoint request = retrofit.create(GoogleBookApiEndPoint.class);
        return request.getBook(book);
    }

    public Observable<VolumeInfo> getBookById(String bookId){
        GoogleBookApiEndPoint request = retrofit.create(GoogleBookApiEndPoint.class);
        return request.getBookById(bookId);
    }
    public Observable<Example> showMore(String book, int startIndex, int maxResult){
        GoogleBookApiEndPoint request = retrofit.create(GoogleBookApiEndPoint.class);
        return request.showMore(book, startIndex, maxResult);
    }

    private static class GoogleBookApiHolder{
        private static GoogleBookApi instanse;

        static GoogleBookApi getInstanse(){
            if(instanse == null){
                instanse = new GoogleBookApi();
            }else{
                instanse.init();
            }
            return instanse;
        }
    }

    public interface GoogleBookApiEndPoint{
        @GET(VOLUME + "?")
        Observable<Example> getBook(@Query("q") String book);
        @GET(VOLUME + "/{id}")
        Observable<VolumeInfo> getBookById(@Path("id") String bookId);
        @GET(VOLUME + "?")
        Observable<Example> showMore(@Query("q") String book, @Query("startIndex") int startIndex, @Query("maxResult") int maxResult);
    }
}
