package com.marcelo.book_store_jsf.book;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class BookListBean implements Serializable {

    @Inject
    private BookService bookService;

    private List<Book> books;

    @PostConstruct
    public void init() {
        books = bookService.getBooks();
    }

    public List<Book> getBooks() {
        books = bookService.getBooks();
        return books;
    }

    public void redirectToDetails(Long bookId) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("book/details.xhtml?bookId=" + bookId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
