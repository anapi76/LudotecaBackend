package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.game.model.Game;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LoanDto {

    private Long id;

    @NotNull
    private Game game;

    @NotNull
    private Customer customer;

    @NotNull
    private LocalDate loanDate;

    @NotNull
    private LocalDate returnDate;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id new value of {@link #getId}.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game new value of {@link #getGame}.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer new value of {@link #getCustomer}.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return loanDate
     */
    public LocalDate getLoanDate() {
        return loanDate;
    }

    /**
     * @param loanDate new value of {@link #getLoanDate}.
     */
    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    /**
     * @return returnDate
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate new value of {@link #getReturnDate}.
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
