package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.author.AuthorService;
import com.ccsw.tutorial.category.CategoryService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.loan.model.Loan;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    AuthorService authorService;

    @Autowired
    CategoryService categoryService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Loan> find(Long idGame, Long idCustomer, String dateString) {

        LocalDate date = (dateString != null && !dateString.isEmpty()) ? LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy")) : null;
        LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", idGame));
        LoanSpecification customerSpec = new LoanSpecification(new SearchCriteria("customer.id", ":", idCustomer));
        LoanSpecification dateSpec = new LoanSpecification(new SearchCriteria("loanDateBetween", "between", date));

        Specification<Loan> spec = Specification.where(gameSpec).and(customerSpec).and(dateSpec);

        return this.loanRepository.findAll(spec);
    }

}
