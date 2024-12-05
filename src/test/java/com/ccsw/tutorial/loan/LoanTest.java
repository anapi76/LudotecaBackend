package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.customer.CustomerServiceImpl;
import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDto;
import com.ccsw.tutorial.exceptions.LoanNotFoundException;
import com.ccsw.tutorial.game.GameServiceImpl;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanTest {

    public static final Long EXISTS_LOAN_ID = 1L;
    public static final Long EXISTS_GAME_ID = 1L;
    public static final Long EXISTS_CUSTOMER_ID = 1L;
    public static final Long EXISTS_AUTHOR_ID = 1L;
    public static final Long EXISTS_CATEGORY_ID = 1L;
    public static final Long NOT_EXISTS_LOAN_ID = 0L;

    public static final String AUTHOR_NAME = "NAME";
    public static final String CATEGORY_NAME = "NAME";
    public static final String CUSTOMER_NAME = "NAME";
    public static final String GAME_TITLE = "NAME";

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private GameServiceImpl gameService;

    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    LoanServiceImpl loanService;

    @Mock
    private LoanSearchDto loanSearchDto;

    @Mock
    private PageableRequest pageableRequest;

    @Mock
    private Pageable pageable;

    @Test
    public void findAllShouldReturnAllLoansByGameAndCustomerAndDateByPage() {

        Loan loan = new Loan(EXISTS_LOAN_ID, mock(Game.class), mock(Customer.class), LocalDate.now(), LocalDate.now().plusDays(14));
        List<Loan> loans = Arrays.asList(loan);
        Page<Loan> page = new PageImpl<>(loans, PageRequest.of(0, 2), loans.size());

        when(loan.getGame().getId()).thenReturn(EXISTS_GAME_ID);
        when(loan.getCustomer().getId()).thenReturn(EXISTS_CUSTOMER_ID);
        when(loanSearchDto.getPageable()).thenReturn(pageableRequest);
        when(pageableRequest.getPageable()).thenReturn(pageable);
        when(loanRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<Loan> result = loanService.findPage(loanSearchDto);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(EXISTS_GAME_ID, result.getContent().get(0).getGame().getId());
        assertEquals(EXISTS_CUSTOMER_ID, result.getContent().get(0).getCustomer().getId());
        verify(loanRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    public void getExistsLoanIdShouldReturnLoan() {

        Loan loan = mock(Loan.class);
        when(loan.getId()).thenReturn(EXISTS_LOAN_ID);
        when(loanRepository.findById(EXISTS_LOAN_ID)).thenReturn(Optional.of(loan));

        Loan loanResponse = loanService.get(EXISTS_LOAN_ID);

        assertNotNull(loanResponse);
        assertEquals(EXISTS_LOAN_ID, loanResponse.getId());
    }

    @Test
    public void getNotExistLoanIdShouldReturnLoanNotFoundException() {

        when(loanRepository.findById(NOT_EXISTS_LOAN_ID)).thenThrow(LoanNotFoundException.class);

        assertThrows(LoanNotFoundException.class, () -> loanService.get(NOT_EXISTS_LOAN_ID));

    }

    @Test
    public void saveLoanIdShouldInsert() {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(EXISTS_CUSTOMER_ID);

        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(EXISTS_AUTHOR_ID);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(EXISTS_CATEGORY_ID);

        GameDto gameDto = new GameDto();
        gameDto.setId(EXISTS_GAME_ID);
        gameDto.setAuthor(authorDto);
        gameDto.setCategory(categoryDto);

        LoanDto loanDto = new LoanDto();
        loanDto.setGame(gameDto);
        loanDto.setCustomer(customerDto);
        loanDto.setLoanDate(LocalDate.now());
        loanDto.setReturnDate(LocalDate.now().plusDays(14));

        Customer customer = new Customer();
        customer.setName(CUSTOMER_NAME);

        Author author = new Author();
        author.setName(AUTHOR_NAME);

        Category category = new Category();
        category.setName(CATEGORY_NAME);

        Game game = new Game();
        game.setTitle(GAME_TITLE);
        game.setCategory(category);
        game.setAuthor(author);

        when(gameService.get(EXISTS_GAME_ID)).thenReturn(game);
        when(customerService.get(EXISTS_CUSTOMER_ID)).thenReturn(customer);

        ArgumentCaptor<Loan> loan = ArgumentCaptor.forClass(Loan.class);
        loanService.save(loanDto);

        verify(loanRepository).save(loan.capture());
        assertEquals(GAME_TITLE, loan.getValue().getGame().getTitle());
        assertEquals(CUSTOMER_NAME, loan.getValue().getCustomer().getName());
    }

    @Test
    public void deleteExistsLoanIdShouldDelete() {

        Loan loan = mock(Loan.class);
        when(loanRepository.findById(EXISTS_LOAN_ID)).thenReturn(Optional.of(loan));

        loanService.delete(EXISTS_LOAN_ID);

        verify(loanRepository).deleteById(EXISTS_LOAN_ID);
    }

    @Test
    public void deleteNotExistsLoanIdShouldLoanNotFoundException() {

        when(loanRepository.findById(NOT_EXISTS_LOAN_ID)).thenThrow(LoanNotFoundException.class);

        assertThrows(LoanNotFoundException.class, () -> loanService.delete(NOT_EXISTS_LOAN_ID));
    }

}
