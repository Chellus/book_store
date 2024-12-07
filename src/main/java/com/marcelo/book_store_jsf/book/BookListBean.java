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
import java.util.Locale;


@Named
@ViewScoped
public class BookListBean implements Serializable {

    @Inject
    private BookService bookService;

    private List<Book> books;
    private List<Book> filteredBooks;
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
            FacesContext.getCurrentInstance().getExternalContext().redirect("admin/edit.xhtml?bookId=" + bookId);

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

    public void redirectToOrders() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("orders.xhtml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            FacesContext.getCurrentInstance().getExternalContext().redirect("admin/create.xhtml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null) {
            return true;
        }

        if (filterText.isEmpty()) {
            return true;
        }

        Book book = (Book) value;
        return book.getTitle().toLowerCase().contains(filterText)
                || book.getAuthor().toLowerCase().contains(filterText)
                || book.getGenre().toLowerCase().contains(filterText);
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public List<Book> getFilteredBooks() {
        return filteredBooks;
    }

    public void setFilteredBooks(List<Book> filteredBooks) {
        this.filteredBooks = filteredBooks;
    }
}
