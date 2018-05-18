package com.example.machine_time.googlebooks.net.model;

import java.util.List;

public class Book {
    private String id;
    private String title;
    private String subtitle;
    private List<String> authors;
    private String date;
    private ImageLinks imageLinks;

    public Book(String id, String title, String subtitle, List<String> authors, String date, ImageLinks imageLinks) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.date = date;
        this.imageLinks = imageLinks;
    }

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getThumbSmall(){
        String urlThumbSmall = null;
        if(imageLinks != null){
            urlThumbSmall = imageLinks.getSmallThumbnail();
        }
        return urlThumbSmall;
    }

    public String getThumb(){
        String urlThumb = null;
        if(imageLinks != null){
            urlThumb = imageLinks.getThumbnail();
        }
        return urlThumb;
    }
}
