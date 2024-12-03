package com.marcelo.book_store_jsf.customer;

import com.marcelo.book_store_jsf.order.BookOrder;
import com.marcelo.book_store_jsf.order.OrderService;
import com.marcelo.book_store_jsf.util.Hashing;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class CustomerBean implements Serializable {
    @Inject
    private CustomerService customerService;

    @Inject
    private OrderService orderService;

    private Customer customer;

    private String name;
    private String address;
    private String phone;
    private String email;
    private String password;
    private String role;

    private String confirmPassword;

    private List<BookOrder> orders;

    public CustomerBean() {}

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null && session.getAttribute("customer") != null) {
            customer = (Customer) session.getAttribute("customer");
            orders = orderService.getOrdersByCustomer(customer);
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void register() throws IOException {
        if (customerService.getCustomer(email) != null) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Correo ya registrado!", "Correo " + email + " ya registrado en el sistema");
        }

        Customer customer = new Customer();

        customer.setName(name);
        customer.setAddress(address);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setPassword(Hashing.hash(password));
        customer.setRole("user");
        customerService.addCustomer(customer);
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.xhtml");

    }

    public void login() throws IOException {
        if (!customerService.validCustomer(email, password)) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Correo o contraseña inválidos", "Correo o contraseña inválidos");
        }
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        customer = customerService.getCustomer(email);
        orders = customer.getOrders();
        session.setAttribute("customer", customer);
        session.setAttribute("name", customer.getName());
        session.setAttribute("role", customer.getRole());
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/home.xhtml");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<BookOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<BookOrder> orders) {
        this.orders = orders;
    }
}
