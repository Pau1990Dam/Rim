package com.lafamilia.rim.models;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by laFamilia on 09/03/2017.
 */

public class Partner extends RealmObject {
    private String name;
    private String age;
    private RealmList<Book> books ;

    public Partner() {
    }

    public Partner(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public RealmList<Book> getBooks() {
        return books;
    }

    public void setBooks(RealmList<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
    }
}
