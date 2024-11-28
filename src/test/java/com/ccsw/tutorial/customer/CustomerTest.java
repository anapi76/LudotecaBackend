package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDto;
import com.ccsw.tutorial.exceptions.CustomerNotFoundException;
import com.ccsw.tutorial.exceptions.NameAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerTest {

    public static final Long EXISTS_CUSTOMER_ID = 1L;
    public static final String CUSTOMER_NAME = "CUSTOMER1";
    public static final Long NOT_EXISTS_CUSTOMER_ID = 0L;
    public static final String NOT_EXISTS_CUSTOMER_NAME = "";

    @Mock
    CustomerRepository customerRepository;
    @InjectMocks
    CustomerServiceImpl customerService;

    @Test
    public void findAllShouldReturnAllCustomers() {

        List<Customer> list = new ArrayList<>();
        list.add(mock(Customer.class));

        when(customerRepository.findAll()).thenReturn(list);
        List<Customer> customers = customerService.findAll();

        assertNotNull(customers);
        assertEquals(1, customers.size());
    }

    @Test
    public void getExistsCustomerIdShouldReturnCustomer() {

        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(EXISTS_CUSTOMER_ID);
        when(customerRepository.findById(EXISTS_CUSTOMER_ID)).thenReturn(Optional.of(customer));

        Customer customerResponse = customerService.get(EXISTS_CUSTOMER_ID);

        assertNotNull(customerResponse);
        assertEquals(EXISTS_CUSTOMER_ID, customerResponse.getId());
    }

    @Test
    public void getNotExistCustomerIdShouldReturnNull() {

        when(customerRepository.findById(NOT_EXISTS_CUSTOMER_ID)).thenReturn(Optional.empty());

        Customer customer = customerService.get(NOT_EXISTS_CUSTOMER_ID);

        assertNull(customer);

    }

    @Test
    public void getByNameExistsCustomerIdNameShouldReturnCustomer() {

        Customer customer = mock(Customer.class);
        when(customer.getName()).thenReturn(CUSTOMER_NAME);
        when(customerRepository.findByName(CUSTOMER_NAME)).thenReturn(Optional.of(customer));

        Customer customerResponse = customerService.getByName(CUSTOMER_NAME);

        assertNotNull(customerResponse);
        assertEquals(CUSTOMER_NAME, customerResponse.getName());
    }

    @Test
    public void getByNameNotExistCustomerIdNameShouldReturnNull() {

        when(customerRepository.findByName(NOT_EXISTS_CUSTOMER_NAME)).thenReturn(Optional.empty());

        Customer customer = customerService.getByName(NOT_EXISTS_CUSTOMER_NAME);

        assertNull(customer);

    }

    @Test
    public void saveNotExistsCustomerIdAndCustomerNameIsValidShouldInsert() {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(CUSTOMER_NAME);

        ArgumentCaptor<Customer> customer = ArgumentCaptor.forClass(Customer.class);
        customerService.save(null, customerDto);

        verify(customerRepository).save(customer.capture());
        assertEquals(CUSTOMER_NAME, customer.getValue().getName());
    }

    @Test
    public void saveExistsCustomerIdAndCustomerNameIsValidShouldUpdate() {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(CUSTOMER_NAME);

        Customer customer = mock(Customer.class);
        when(customerRepository.findById(EXISTS_CUSTOMER_ID)).thenReturn(Optional.of(customer));
        customerService.save(EXISTS_CUSTOMER_ID, customerDto);

        verify(customerRepository).save(customer);
    }

    @Test
    public void saveCustomerIdNameAlreadyExistsShouldReturnNameAlreadyExistsException() {

        when(customerRepository.findByName(CUSTOMER_NAME)).thenThrow(NameAlreadyExistsException.class);

        assertThrows(NameAlreadyExistsException.class, () -> customerService.getByName(CUSTOMER_NAME));
    }

    @Test
    public void deleteExistsCustomerIdShouldDelete() {

        Customer customer = mock(Customer.class);
        when(customerRepository.findById(EXISTS_CUSTOMER_ID)).thenReturn(Optional.of(customer));

        customerService.delete(EXISTS_CUSTOMER_ID);

        verify(customerRepository).deleteById(EXISTS_CUSTOMER_ID);
    }

    @Test
    public void deleteNotExistsCustomerIdShouldCustomerNotFoundException() {

        when(customerRepository.findById(NOT_EXISTS_CUSTOMER_ID)).thenThrow(CustomerNotFoundException.class);

        assertThrows(CustomerNotFoundException.class, () -> customerService.delete(NOT_EXISTS_CUSTOMER_ID));
    }
}
