package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.category.model.CategoryDto;
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.customer.model.CustomerDto;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final Long EXISTS_GAME_ID = 1L;
    public static final Long EXISTS_CUSTOMER_ID = 2L;
    public static final Long EXISTS_AUTHOR_ID = 1L;
    public static final Long EXISTS_CATEGORY_ID = 1L;
    public static final Long DELETE_LOAN_ID = 2L;

    private static final String LOAN_DATE = "loanDate";
    private static final String RETURN_DATE = "returnDate";

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    private static final int TOTAL_LOANS = 6;
    private static final int PAGE_SIZE = 5;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<LoanDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LoanDto>>() {
    };

    ParameterizedTypeReference<List<LoanDto>> responseType = new ParameterizedTypeReference<List<LoanDto>>() {
    };

    private String getUrl() {
        return UriComponentsBuilder.fromHttpUrl(LOCALHOST + port + SERVICE_PATH).queryParam(LOAN_DATE, "{" + LOAN_DATE + "}", RETURN_DATE, "{" + RETURN_DATE + "}").encode().toUriString();
    }

    @Test
    public void findFirstPageWithFilterOneSizeShouldReturnFirstPageOneResults() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, 1));
        searchDto.setIdGame(6L);
        searchDto.setIdCustomer(1L);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(6, response.getBody().getContent().get(0).getGame().getId());
        assertEquals(1, response.getBody().getContent().get(0).getCustomer().getId());
    }

    @Test
    public void findFirstPageWithOutFilterFiveSizeShouldReturnFirstPageFiveResults() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithFiveSizeShouldReturnLastResult() {

        int elementsCount = TOTAL_LOANS - PAGE_SIZE;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(1, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(elementsCount, response.getBody().getContent().size());
    }

    @Test
    public void saveShouldCreateNewLoan() {

        long newLoanSize = TOTAL_LOANS + 1;
        long newLoanId = TOTAL_LOANS + 1;

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

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, (int) newLoanSize));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(loanDto), Void.class);

        response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newLoanSize, response.getBody().getTotalElements());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        LoanDto dto = response.getBody().getContent().stream().filter(item -> item.getId().equals(newLoanId)).findFirst().orElse(null);
        assertNotNull(dto);
    }

    @Test
    public void deleteWithExistsIdShouldDeleteLoan() {
        long newLoansSize = TOTAL_LOANS - 1;

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + DELETE_LOAN_ID, HttpMethod.DELETE, null, Void.class);

        LoanSearchDto loanSearchDto = new LoanSearchDto();
        loanSearchDto.setPageable(new PageableRequest(0, TOTAL_LOANS));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(loanSearchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newLoansSize, response.getBody().getTotalElements());
    }

    @Test
    public void deleteWithNotExistsIdShouldNotFound() {

        long deleteLoanId = TOTAL_LOANS + 1;

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + deleteLoanId, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
