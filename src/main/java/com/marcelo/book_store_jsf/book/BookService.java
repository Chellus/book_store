package com.marcelo.book_store_jsf.book;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class BookService {
    @PersistenceContext(unitName = "jsf-crud-unit")
    private EntityManager em;

    public void addBook(Book book) {
        em.persist(book);
    }

    public List<Book> getBooks() {
        CriteriaQuery<Book> criteria = em.getCriteriaBuilder().createQuery(Book.class);
        criteria.select(criteria.from(Book.class));
        return em.createQuery(criteria).getResultList();
    }

    public Book getBook(Long id) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.id = :id", Book.class);
        query.setParameter("id", id);
        List<Book> books = query.getResultList();
        return books.isEmpty() ? null : books.get(0);
    }

    public void updateBook(Book book) {
        em.merge(book);
    }

    public void deleteBook(Long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
