package com.ccsw.tutorial.game;

import com.ccsw.tutorial.category.model.Category;
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

    @Mock
    GameRepository gameRepository;
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

        ArgumentCaptor<Game> game = ArgumentCaptor.forClass(Game.class);
        gameService.save(null, gameDto);

        verify(gameRepository).save(game.capture());
        assertEquals(GAME_TITLE, game.getValue().getTitle());
    }

    @Test
    public void saveExistsGameIdShouldUpdate() {

        GameDto gameDto = new GameDto();
        gameDto.setTitle(GAME_TITLE);

        Game game = mock(Game.class);
        when(gameRepository.findById(EXISTS_GAME_ID)).thenReturn(Optional.of(game));
        gameService.save(EXISTS_GAME_ID, gameDto);

        verify(gameRepository).save(game);
    }

     /*   @Test
    public void findAllShouldReturnAllGames() {
        List<Game> list = new ArrayList<>();
        list.add(mock(Game.class));

        when(gameRepository.findAll()).thenReturn(list);
        List<Game> games = gameService.findAll();

        assertNotNull(games);
        assertEquals(1, games.size());
    }*/

   /* @Test
    public void deleteExistsGameIdShouldDelete() throws Exception {

        Game game = mock(Game.class);
        when(gameRepository.findById(EXISTS_GAME_ID)).thenReturn(Optional.of(game));

        gameService.delete(EXISTS_GAME_ID);

        verify(gameRepository).deleteById(EXISTS_GAME_ID);
    }*/
}
