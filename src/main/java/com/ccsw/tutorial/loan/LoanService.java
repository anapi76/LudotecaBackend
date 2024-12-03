package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;

import java.util.List;

/**
 * @author Ana Piqueras
 *
 */
public interface LoanService {
    /**
     * Recupera los préstamos filtrando opcionalmente por juego y/o cliente y/o fecha del préstamo
     *
     * @param idGame PK del juego
     * @param idCustomer PK del cliente
     * @param dateString fecha del préstamo
     * @return {@link List} de {@link Loan}
     */
    List<Loan> find(Long idGame, Long idCustomer, String dateString);
}
