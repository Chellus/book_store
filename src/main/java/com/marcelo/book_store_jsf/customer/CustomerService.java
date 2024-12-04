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

    public void addCustomer(Customer customer) {
        em.persist(customer);
    }

    public boolean validCustomer(String email, String password) {
        Customer customer = getCustomer(email);
        System.out.println(customer);
        System.out.println("Contraseña valida: " + Hashing.matches(password, customer.getPassword()));
        if (customer == null) {
           return false;
        }

        return Hashing.matches(password, customer.getPassword());
    }
}
