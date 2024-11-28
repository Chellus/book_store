package com.marcelo.book_store_jsf.customer;

import com.marcelo.book_store_jsf.util.Hashing;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CustomerService {
    @PersistenceContext(unitName = "jsf-crud-unit")
    private EntityManager em;

    public Customer getCustomer(String email) {
        TypedQuery<Customer> query = em.createQuery("SELECT u FROM Customer u WHERE u.email = :email", Customer.class);
        query.setParameter("email", email);
        List<Customer> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public boolean validCustomer(String email, String password) {
        Customer customer = getCustomer(email);

        if (customer == null) {
           return false;
        }

        return Hashing.matches(password, customer.getPassword());
    }
}