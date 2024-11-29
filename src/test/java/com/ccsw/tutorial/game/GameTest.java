package com.ccsw.tutorial.game;

import com.ccsw.tutorial.author.AuthorRepository;
import com.ccsw.tutorial.author.AuthorServiceImpl;
import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.category.CategoryServiceImpl;
import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameTest {
    public static final Long EXISTS_GAME_ID = 1L;
    public static final String GAME_TITLE = "GAME1";
    public static final Long EXISTS_CATEGORY_ID = 1L;

    public static final String AUTHOR_NAME = "NAME";
    public static final String CATEGORY_NAME = "NAME";

    @Mock
    private AuthorRepository authorRepository;  // Mock del repositorio de Author

    @Mock
    private AuthorServiceImpl authorService;  // Mock del servicio de Author

    @Mock
    private GameRepository gameRepository;

    @Mock
    private CategoryServiceImpl categoryService;
    @InjectMocks
    GameServiceImpl gameService;

    @Test
    public void findShouldReturnAllGamesByTitleAndCategory() {

        Game game = mock(Game.class);
        Category category = mock(Category.class);
        List<Game> list = new ArrayList<>();
        list.add(game);

        when(game.getTitle()).thenReturn(GAME_TITLE);
        when(game.getCategory()).thenReturn(category);
        when(category.getId()).thenReturn(EXISTS_CATEGORY_ID);

        when(gameRepository.findAll(any(Specification.class))).thenReturn(list);
        List<Game> games = gameService.find(GAME_TITLE, EXISTS_CATEGORY_ID);

        assertNotNull(games);
        assertEquals(1, games.size());
        assertEquals(GAME_TITLE, games.get(0).getTitle());
        assertEquals(EXISTS_CATEGORY_ID, games.get(0).getCategory().getId());
    }

    @Test
    public void saveNotExistsGameIdShouldInsert() {

        GameDto gameDto = new GameDto();
        gameDto.setTitle(GAME_TITLE);

        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);

        gameDto.setAge("18");
        gameDto.setAuthor(authorDto);
        gameDto.setCategory(categoryDto);

        Author author = new Author();
        author.setName(AUTHOR_NAME);
        Category category = new Category();
        category.setName(CATEGORY_NAME);

        when(authorService.get(1L)).thenReturn(author);
        when(categoryService.get(1L)).thenReturn(category);

        ArgumentCaptor<Game> game = ArgumentCaptor.forClass(Game.class);
        gameService.save(null, gameDto);

        verify(gameRepository).save(game.capture());
        assertEquals(GAME_TITLE, game.getValue().getTitle());
    }

    @Test
    public void saveExistsGameIdShouldUpdate() {

        GameDto gameDto = new GameDto();
        gameDto.setTitle(GAME_TITLE);

        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);

        gameDto.setAge("18");
        gameDto.setAuthor(authorDto);
        gameDto.setCategory(categoryDto);

        Author author = new Author();
        author.setName(AUTHOR_NAME);
        Category category = new Category();
        category.setName(CATEGORY_NAME);

        when(authorService.get(1L)).thenReturn(author);
        when(categoryService.get(1L)).thenReturn(category);

        Game game = mock(Game.class);
        when(gameRepository.findById(EXISTS_GAME_ID)).thenReturn(Optional.of(game));
        gameService.save(EXISTS_GAME_ID, gameDto);

        verify(gameRepository).save(game);
    }

}
