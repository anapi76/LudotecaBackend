package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    public Customer findByName(String name);

}