package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import org.springframework.data.domain.Page;

/**
 * @author Ana Piqueras
 *
 */
public interface LoanService {

    /**
     * Recupera un {@link Loan} a través de su ID
     *
     * @param id PK de la entidad
     * @return {@link Loan}
     */
    public Loan get(Long id);

    /**
     * Método para recuperar un listado paginado y filtrado de {@link Loan}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> findPage(LoanSearchDto dto);

    /**
     * Método para crear o actualizar un {@link Loan}
     *
     * @param dto datos de la entidad
     */
    void save(LoanDto dto);

    /**
     * Método para crear o actualizar un {@link Author}
     *
     * @param id PK de la entidad
     */
    void delete(Long id);
}
