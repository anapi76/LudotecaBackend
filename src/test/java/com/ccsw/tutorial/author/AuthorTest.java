package com.ccsw.tutorial.author;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.author.model.AuthorSearchDto;
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.exceptions.AuthorNotFoundException;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorTest {

    public static final Long EXISTS_AUTHOR_ID = 1L;
    public static final String AUTHOR_NAME = "ANA";
    public static final String AUTHOR_NATIONALITY = "ESPAÑOLA";
    public static final Long NOT_EXISTS_AUTHOR_ID = 0L;

    @Mock
    AuthorRepository authorRepository;
    @InjectMocks
    AuthorServiceImpl authorService;

    @Mock
    private AuthorSearchDto authorSearchDto; // Mock de AuthorSearchDto

    @Mock
    private PageableRequest pageableRequest; // Mock de PageableRequest

    @Mock
    private Pageable pageable; // Mock de Pageable

    @Test
    public void findAllShouldReturnAllAuthorsByPage() {

        Author author1 = new Author();
        author1.setName(AUTHOR_NAME);
        author1.setNationality(AUTHOR_NATIONALITY);
        List<Author> authors = Arrays.asList(author1);
        Page<Author> page = new PageImpl<>(authors, PageRequest.of(0, 2), authors.size());

        when(authorSearchDto.getPageable()).thenReturn(pageableRequest);
        when(pageableRequest.getPageable()).thenReturn(pageable);
        when(authorRepository.findAll(pageable)).thenReturn(page);

        Page<Author> result = authorService.findPage(authorSearchDto);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(AUTHOR_NAME, result.getContent().get(0).getName());
        assertEquals(AUTHOR_NATIONALITY, result.getContent().get(0).getNationality());

        verify(authorRepository, times(1)).findAll(pageable);
    }

    @Test
    public void findAllShouldReturnAllAuthors() {

        List<Author> list = new ArrayList<>();
        list.add(mock(Author.class));

        when(authorRepository.findAll()).thenReturn(list);
        List<Author> authors = authorService.findAll();

        assertNotNull(authors);
        assertEquals(1, authors.size());
    }

    @Test
    public void getExistsAuthorIdShouldReturnAuthor() {

        Author author = mock(Author.class);
        when(author.getId()).thenReturn(EXISTS_AUTHOR_ID);
        when(authorRepository.findById(EXISTS_AUTHOR_ID)).thenReturn(Optional.of(author));

        Author authorResponse = authorService.get(EXISTS_AUTHOR_ID);

        assertNotNull(authorResponse);
        assertEquals(EXISTS_AUTHOR_ID, authorResponse.getId());
    }

    @Test
    public void getNotExistAuthorIdShouldReturnNull() {

        when(authorRepository.findById(NOT_EXISTS_AUTHOR_ID)).thenThrow(AuthorNotFoundException.class);

        assertThrows(AuthorNotFoundException.class, () -> authorService.get(NOT_EXISTS_AUTHOR_ID));

    }

    @Test
    public void saveNotExistsAuthorIdShouldInsert() {

        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(AUTHOR_NAME);
        authorDto.setNationality(AUTHOR_NATIONALITY);

        ArgumentCaptor<Author> author = ArgumentCaptor.forClass(Author.class);
        authorService.save(null, authorDto);

        verify(authorRepository).save(author.capture());
        assertEquals(AUTHOR_NAME, author.getValue().getName());
        assertEquals(AUTHOR_NATIONALITY, author.getValue().getNationality());
    }

    @Test
    public void saveExistsAuthorIdShouldUpdate() {

        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(AUTHOR_NAME);
        authorDto.setNationality(AUTHOR_NATIONALITY);

        Author author = mock(Author.class);
        when(authorRepository.findById(EXISTS_AUTHOR_ID)).thenReturn(Optional.of(author));
        authorService.save(EXISTS_AUTHOR_ID, authorDto);

        verify(authorRepository).save(author);
    }

    @Test
    public void deleteExistsAuthorIdShouldDelete() {

        Author author = mock(Author.class);
        when(authorRepository.findById(EXISTS_AUTHOR_ID)).thenReturn(Optional.of(author));

        authorService.delete(EXISTS_AUTHOR_ID);

        verify(authorRepository).deleteById(EXISTS_AUTHOR_ID);
    }

    @Test
    public void deleteNotExistsAuthorIdShouldAuthorNotFoundException() {

        when(authorRepository.findById(NOT_EXISTS_AUTHOR_ID)).thenThrow(AuthorNotFoundException.class);

        assertThrows(AuthorNotFoundException.class, () -> authorService.delete(NOT_EXISTS_AUTHOR_ID));

    }

}
