package com.marcelo.book_store_jsf.book;

import com.marcelo.book_store_jsf.util.BookAPI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Named
@ViewScoped
public class BookBean implements Serializable {
    @Inject
    private BookService bookService;

    private Book book;

    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int pages;
    private String genre;
    private Date releaseDate;
    private int stock;
    private String edition;
    private String language;
    private double price;
    private String imageUrl;

    public BookBean() {

    }

    @PostConstruct
    public void init() {
        String idParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bookId");
        if (idParam != null) {
            System.out.println("Id = " + idParam);
            id = Long.parseLong(idParam);
            book = bookService.getBook(id);
            isbn = book.getIsbn();
            title = book.getTitle();
            author = book.getAuthor();
            publisher = book.getPublisher();
            pages = book.getPages();
            genre = book.getGenre();
            releaseDate = book.getReleaseDate();
            stock = book.getStock();
            edition = book.getEdition();
            language = book.getLanguage();
            price = book.getPrice();
        }
    }

    public void add() throws IOException, ParseException, java.text.ParseException {
        book = new Book();
        fetchBookDetailsByIsbn();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPages(pages);
        book.setGenre(genre);
        book.setPrice(price);
        book.setEdition(edition);
        book.setLanguage(language);
        book.setStock(stock);
        book.setReleaseDate(releaseDate);
        book.setImageUrl(imageUrl);
        bookService.addBook(book);
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/home.xhtml");
    }

    public void update() throws IOException {
        book.setPrice(price);
        book.setStock(stock);
        bookService.updateBook(book);
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/home.xhtml");

    }

    public void fetchBookDetailsByIsbn() throws IOException, ParseException, java.text.ParseException {
        Book book = BookAPI.getBook(isbn);
        title = book.getTitle();
        author = book.getAuthor();
        publisher = book.getPublisher();
        pages = book.getPages();
        //releaseDate = book.getReleaseDate();
        imageUrl = book.getImageUrl();
        System.out.printf("\t\tReceived data from API:\nTitle:%s\nAuthor:%s", title, author);
    }

    public Book getBook() {
        return book;
    }

    private void setBook(Book book) {
        this.book = book;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
