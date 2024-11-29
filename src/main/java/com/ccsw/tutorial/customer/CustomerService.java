package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDto;

import java.util.List;

/**
 * @author Ana Piqueras
 *
 */
public interface CustomerService {

    /**
     * Recupera un {@link Customer} a través de su ID
     *
     * @param id PK de la entidad
     * @return {@link Customer}
     */
    public Customer get(Long id);

    /**
     * Método para recuperar todos los clientes
     *
     * @return {@link List} de {@link Customer}
     */
    public List<Customer> findAll();

    /**
     * Método para crear o actualizar un cliente
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    public void save(Long id, CustomerDto dto);

    /**
     * Método para borrar un cliente
     *
     * @param id PK de la entidad
     */
    public void delete(Long id);
}
