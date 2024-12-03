package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.loan.model.Loan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanTest {

    public static final Long EXISTS_LOAN_ID = 1L;
    public static final Long EXISTS_GAME_ID = 1L;
    public static final Long EXISTS_CUSTOMER_ID = 1L;
    public static final String DATE = "01-12-2024";

    @Mock
    LoanRepository loanRepository;

    @InjectMocks
    LoanServiceImpl loanService;

    @Test
    public void findShouldReturnAllLoansByLoanAndCustomerAndDate() {

        Loan loan = mock(Loan.class);
        Game game = mock(Game.class);
        Customer customer = mock(Customer.class);
        List<Loan> list = new ArrayList<>();
        list.add(loan);

        when(loan.getGame()).thenReturn(game);
        when(loan.getCustomer()).thenReturn(customer);
        when(game.getId()).thenReturn(EXISTS_GAME_ID);
        when(customer.getId()).thenReturn(EXISTS_CUSTOMER_ID);

        when(loanRepository.findAll(any(Specification.class))).thenReturn(list);
        List<Loan> loans = loanService.find(EXISTS_GAME_ID, EXISTS_CUSTOMER_ID, DATE);

        assertNotNull(loans);
        assertEquals(1, loans.size());
        assertEquals(EXISTS_GAME_ID, loans.get(0).getGame().getId());
        assertEquals(EXISTS_CUSTOMER_ID, loans.get(0).getCustomer().getId());
    }

    /*@Test
    public void findAllShouldReturnAllLoansByPage() {

        Loan loan1 = new Loan(EXISTS_LOAN_ID, mock(Loan.class), mock(Customer.class), LocalDate.now(), LocalDate.now().plusDays(14));
        List<Loan> loans = Arrays.asList(loan1);
        Page<Loan> page = new PageImpl<>(loans, PageRequest.of(0, 2), loans.size());

        when(loanSearchDto.getPageable()).thenReturn(pageableRequest);
        when(pageableRequest.getPageable()).thenReturn(pageable);
        when(loanRepository.findAll(pageable)).thenReturn(page);

        Page<Loan> result = loanService.findPage(loanSearchDto);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());

        verify(loanRepository, times(1)).findAll(pageable);
    }*/

}
