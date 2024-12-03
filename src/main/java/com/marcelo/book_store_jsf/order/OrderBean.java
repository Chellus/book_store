package com.marcelo.book_store_jsf.order;

import com.marcelo.book_store_jsf.book.Book;
import com.marcelo.book_store_jsf.book.BookService;
import com.marcelo.book_store_jsf.customer.Customer;
import com.marcelo.book_store_jsf.customer.CustomerService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Named
@ViewScoped
public class OrderBean implements Serializable {

    @Inject
    private OrderService orderService;

    @Inject
    private BookService bookService;

    @Inject
    CustomerService customerService;

    private BookOrder bookOrder;
    private Customer customer;
    private Book book;
    private int quantity;
    private double totalPrice;

    @PostConstruct
    public void init() {
        String idParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bookId");
        bookOrder = new BookOrder();
        if (idParam != null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            book = bookService.getBook(Long.parseLong(idParam));
            customer = (Customer) session.getAttribute("customer");
            bookOrder.setCustomer(customer);
            bookOrder.setBook(book);
        }
    }

    public void placeOrder() throws IOException {
        System.out.println("Quantity: " + quantity);
        if (quantity < 0 || quantity > book.getStock()) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error en cantidad", "No puede ordenar más libros de los disponibles");
        }
        bookOrder.setQuantity(quantity);
        bookOrder.setOrderDate(new Date());
        bookOrder.setStatus("En proceso");
        orderService.placeOrder(bookOrder);
        addMessage(FacesMessage.SEVERITY_INFO, "Orden Confirmada", "Has comprado " + quantity + " libros.");
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/home.xhtml");

    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
