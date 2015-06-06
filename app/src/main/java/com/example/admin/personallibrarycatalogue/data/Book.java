package com.example.admin.personallibrarycatalogue.data;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Mikhail Valuyskiy on 25.05.2015.
 */
public class Book {

    //region Private fields
    @Nullable private Integer id_ = null;
    private String title_;
    private String author_;
    private String description_;
    private byte[] cover_;
    private int year_;
    private String isbn_;
    //endregion

    //region Constructors
    public Book(@Nullable Integer id, String title, String author, String description, byte[] cover, int year, String isbn) {
        this.id_ = id;
        this.title_ = title;
        this.author_ = author;
        this.description_ = description;
        this.cover_ = cover;
        this.year_ = year;
        this.isbn_ = isbn;
    }

    public Book(){}
    //endregion

    //region Accessor Methods
    @Nullable
    public Integer getId() {
        return id_;
    }

    public void setId(int id){this.id_ = id;}

    public String getTitle(){
        return title_;
    }

    public void setTitle(String title){
        this.title_ = title;
    }

    public String getAuthor(){
        return author_;
    }

    public void setAuthor(String author){
        this.author_ = author;
    }

    public String getDescription(){
        return description_;
    }

    public void setDescription(String description){
        this.description_ = description;
    }

    public byte[] getCover(){
        return this.cover_;
    }

    public void setCover(byte[] cover){
        this.cover_ = cover;
    }

    public int getYear(){
        return this.year_;
    }

    public void setYear(int year){
        this.year_ = year;
    }

    public String getIsbn(){
        return this.isbn_;
    }

    public void setIsbn(String isbn){
        this.isbn_ = isbn;
    }

    //endregion

}
