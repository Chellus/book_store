package com.marcelo.book_store_jsf.book;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.DataInput;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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

    public void add() throws IOException {
        book = new Book();
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
}
