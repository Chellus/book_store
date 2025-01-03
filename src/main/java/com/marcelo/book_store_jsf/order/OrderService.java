package com.marcelo.book_store_jsf.order;

import com.marcelo.book_store_jsf.book.Book;
import com.marcelo.book_store_jsf.book.BookService;
import com.marcelo.book_store_jsf.customer.Customer;
import com.marcelo.book_store_jsf.customer.CustomerService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OrderService {
    @PersistenceContext(unitName = "jsf-crud-unit")
    private EntityManager em;

    @Inject
    private BookService bookService;

    @Inject
    private CustomerService customerService;

    public void placeOrder(BookOrder order) {
        Book book = order.getBook();
        int new_stock = book.getStock() - order.getQuantity();
        book.setStock(new_stock);
        bookService.updateBook(book);
        order.setTotalPrice(book.getPrice() * order.getQuantity());
        em.persist(order);
    }

    public List<BookOrder> getOrdersByCustomer(Customer customer) {
        return em.createQuery(
                        "SELECT o FROM BookOrder o WHERE o.customer = :customer", BookOrder.class)
                .setParameter("customer", customer)
                .getResultList();
    }

}
