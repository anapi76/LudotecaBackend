package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDto;
import com.ccsw.tutorial.exceptions.CustomerNotFoundException;
import com.ccsw.tutorial.exceptions.NameAlreadyExistsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ana Piqueras
 *
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer get(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not exists"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer getByName(String name) {
        return customerRepository.findByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> findAll() {

        return (List<Customer>) this.customerRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, CustomerDto dto) {

        if (getByName(dto.getName()) != null) {
            throw new NameAlreadyExistsException("Invalid name");
        }

        Customer customer;
        customer = (id == null) ? new Customer() : this.get(id);
        customer.setName(dto.getName());
        this.customerRepository.save(customer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        this.get(id);
        this.customerRepository.deleteById(id);
    }
}
