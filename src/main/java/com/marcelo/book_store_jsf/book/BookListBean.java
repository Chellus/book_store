package com.marcelo.book_store_jsf.book;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
    private Book selectedBook;

    @PostConstruct
    public void init() {
        books = bookService.getBooks();
    }

    public List<Book> getBooks() {
        books = bookService.getBooks();
        return books;
    }

    public void editBook(Long bookId) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("book/admin/edit.xhtml?bookId=" + bookId);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBook(Long bookId) {
        bookService.deleteBook(bookId);
        addMessage(FacesMessage.SEVERITY_INFO, "Book deleted", "Book deleted successfully");
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        System.out.println("Selected book to be set: " + selectedBook);
        this.selectedBook = selectedBook;
    }

    public void redirectToPurchase(Long bookId) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("purchase.xhtml?bookId=" + bookId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void redirectToDetails(Long bookId) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("book/details.xhtml?bookId=" + bookId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void redirectToCreate() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("book/admin/create.xhtml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
