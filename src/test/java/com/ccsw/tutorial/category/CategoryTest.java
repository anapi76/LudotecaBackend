package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import com.ccsw.tutorial.exceptions.CategoryNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryTest {
    public static final Long EXISTS_CATEGORY_ID = 1L;
    public static final String CATEGORY_NAME = "CAT1";
    public static final Long NOT_EXISTS_CATEGORY_ID = 0L;

    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    public void findAllShouldReturnAllCategories() {

        List<Category> list = new ArrayList<>();
        list.add(mock(Category.class));

        when(categoryRepository.findAll()).thenReturn(list);
        List<Category> categories = categoryService.findAll();

        assertNotNull(categories);
        assertEquals(1, categories.size());
    }

    @Test
    public void getExistsCategoryIdShouldReturnCategory() {

        Category category = mock(Category.class);
        when(category.getId()).thenReturn(EXISTS_CATEGORY_ID);
        when(categoryRepository.findById(EXISTS_CATEGORY_ID)).thenReturn(Optional.of(category));

        Category categoryResponse = categoryService.get(EXISTS_CATEGORY_ID);

        assertNotNull(categoryResponse);
        assertEquals(EXISTS_CATEGORY_ID, categoryResponse.getId());
    }

    @Test
    public void getNotExistCategoryIdShouldReturnNull() {

        when(categoryRepository.findById(NOT_EXISTS_CATEGORY_ID)).thenThrow(CategoryNotFoundException.class);

        assertThrows(CategoryNotFoundException.class, () -> categoryService.get(NOT_EXISTS_CATEGORY_ID));

    }

    @Test
    public void saveNotExistsCategoryIdShouldInsert() {

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(CATEGORY_NAME);

        ArgumentCaptor<Category> category = ArgumentCaptor.forClass(Category.class);
        categoryService.save(null, categoryDto);

        verify(categoryRepository).save(category.capture());
        assertEquals(CATEGORY_NAME, category.getValue().getName());
    }

    @Test
    public void saveExistsCategoryIdShouldUpdate() {

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(CATEGORY_NAME);

        Category category = mock(Category.class);
        when(categoryRepository.findById(EXISTS_CATEGORY_ID)).thenReturn(Optional.of(category));
        categoryService.save(EXISTS_CATEGORY_ID, categoryDto);

        verify(categoryRepository).save(category);
    }

    @Test
    public void deleteExistsCategoryIdShouldDelete() throws Exception {

        Category category = mock(Category.class);
        when(categoryRepository.findById(EXISTS_CATEGORY_ID)).thenReturn(Optional.of(category));

        categoryService.delete(EXISTS_CATEGORY_ID);

        verify(categoryRepository).deleteById(EXISTS_CATEGORY_ID);
    }

    @Test
    public void deleteNotExistsCategoryIdShouldCategoryNotFoundException() throws Exception {

        when(categoryRepository.findById(NOT_EXISTS_CATEGORY_ID)).thenThrow(CategoryNotFoundException.class);

        assertThrows(CategoryNotFoundException.class, () -> categoryService.delete(NOT_EXISTS_CATEGORY_ID));
    }
}
