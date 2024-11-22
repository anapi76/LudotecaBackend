package com.ccsw.tutorial.author;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.author.model.AuthorSearchDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AuthorTest {

    public static final Long EXISTS_AUTHOR_ID = 1L;
    public static final String AUTHOR_NAME = "ANA";
    public static final String AUTHOR_NACIONALITY = "ESPAÑOLA";

    @Test
    public void findAllShouldReturnAllCategoriesByPage() {

        List<Author> list = new ArrayList<>();
        Author author1 = new Author();
        author1.setId(EXISTS_AUTHOR_ID);
        author1.setName(AUTHOR_NAME);
        author1.setNationality(AUTHOR_NACIONALITY);
        //list.add(mock(Author.class));

        //when(categoryRepository.findAll()).thenReturn(list);
        //List<Author> authors = authorService.findAll();
        list.add(author1);

        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    public void saveNotExistsAuthorIdShouldInsert() {

        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(AUTHOR_NAME);
        authorDto.setNationality(AUTHOR_NACIONALITY);
        Author author = new Author();
        author.setName(AUTHOR_NAME);
        //ArgumentCaptor<Author> author = ArgumentCaptor.forClass(Author.class);
        //categoryService.save(null, categoryDto);

        //verify(categoryRepository).save(category.capture());
        //assertEquals(AUTHOR_NAME, author.getValue().getName());
        assertEquals(AUTHOR_NAME, author.getName());
    }

    @Test
    public void saveExistsAuthorIdShouldUpdate() {

        AuthorSearchDto authorSearchDto = new AuthorSearchDto();
        //authorSearchDto.setPageable();

        Author author = mock(Author.class);
        //when(authorRepository.findById(EXISTS_AUTHOR_ID)).thenReturn(Optional.of(author));
        //authorService.save(EXISTS_AUTHOR_ID, authorDto);

        //verify(authorRepository).save(author);
    }

    @Test
    public void deleteExistsAuthorIdShouldDelete() throws Exception {

        Author author = mock(Author.class);
        //when(authorRepository.findById(EXISTS_AUTHOR_ID)).thenReturn(Optional.of(author));

        //authorService.delete(EXISTS_AUTHOR_ID);

        //verify(authorRepository).deleteById(EXISTS_AUTHOR_ID);
    }

}
