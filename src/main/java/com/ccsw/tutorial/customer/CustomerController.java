package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ana Piqueras
 *
 */
@Tag(name = "Customer", description = "API of Customer")
@RequestMapping(value = "/customer")
@RestController
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar todos los clientes
     *
     * @return {@link List} de {@link CustomerDto}
     */
    @Operation(summary = "Find", description = "Method that return a list of Customers")
    @GetMapping
    public List<CustomerDto> findAll() {

        List<Customer> customers = this.customerService.findAll();

        return customers.stream().map(e -> mapper.map(e, CustomerDto.class)).collect(Collectors.toList());
    }

    /**
     * Método para crear o actualizar un cliente
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Customer")
    @PutMapping(path = { "", "/{id}" })
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody @Valid CustomerDto dto) {
        this.customerService.save(id, dto);
    }

    /**
     * Método para borrar un cliente
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Customer")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.customerService.delete(id);
    }
}
