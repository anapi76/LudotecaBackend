package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.customer.CustomerService;
import com.ccsw.tutorial.exceptions.InvalidReturnDateException;
import com.ccsw.tutorial.exceptions.LoanConflictException;
import com.ccsw.tutorial.exceptions.LoanNotFoundException;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Ana Piqueras
 *
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameService gameService;

    @Autowired
    CustomerService customerService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Loan get(Long id) {
        return loanRepository.findById(id).orElseThrow(() -> new LoanNotFoundException("Loan not exists"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> findPage(LoanSearchDto dto) {

        LocalDate date = (dto.getDate() != null && !dto.getDate().isEmpty()) ? LocalDate.parse(dto.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")) : null;
        LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", dto.getIdGame()));
        LoanSpecification customerSpec = new LoanSpecification(new SearchCriteria("customer.id", ":", dto.getIdCustomer()));
        LoanSpecification dateSpec = new LoanSpecification(new SearchCriteria("loanDateBetween", "between", date));

        Specification<Loan> spec = Specification.where(gameSpec).and(customerSpec).and(dateSpec);

        return this.loanRepository.findAll(spec, dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(LoanDto dto) {

        if (dto.getReturnDate().isBefore(dto.getLoanDate())) {
            throw new InvalidReturnDateException("The return date cannot be before the loan date");
        }

        if (dto.getReturnDate().isAfter(dto.getLoanDate().plusDays(14))) {
            throw new InvalidReturnDateException("The return date cannot exceed 14 days from the loan date");
        }

        LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", dto.getGame().getId()));
        LoanSpecification dateSpec = new LoanSpecification(new SearchCriteria("loanDateBetween", "between", dto.getLoanDate()));
        Specification<Loan> gameSpecQuery = Specification.where(gameSpec).and(dateSpec);
        List<Loan> gameLoans = loanRepository.findAll(gameSpecQuery);
        if (!gameLoans.isEmpty()) {
            throw new LoanConflictException("The game " + gameLoans.get(0).getGame().getTitle() + " is already loaned on this date.");
        }

        LoanSpecification customerSpec = new LoanSpecification(new SearchCriteria("customer.id", ":", dto.getCustomer().getId()));
        Specification<Loan> customerSpecQuery = Specification.where(customerSpec).and(dateSpec);
        List<Loan> customerLoans = loanRepository.findAll(customerSpecQuery);
        if (customerLoans.size() >= 2) {
            throw new LoanConflictException("Customer " + customerLoans.get(0).getCustomer().getName() + " has 2 active loan on this date.");
        }

        Loan loan = new Loan();

        BeanUtils.copyProperties(dto, loan, "id", "game", "customer");

        loan.setGame(gameService.get(dto.getGame().getId()));
        loan.setCustomer(customerService.get(dto.getCustomer().getId()));

        this.loanRepository.save(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        this.get(id);
        this.loanRepository.deleteById(id);

    }
}
