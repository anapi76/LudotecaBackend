package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ccsw
 *
 */
@Tag(name = "Category", description = "API of Category")
@RequestMapping(value = "/category")
@RestController
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar todas las categorias
     *
     * @return {@link List} de {@link CategoryDto}
     */
    @Operation(summary = "Find", description = "Method that return a list of Categories")
    @GetMapping
    public List<CategoryDto> findAll() {
        List<Category> categories = this.categoryService.findAll();
        return categories.stream().map(e -> mapper.map(e, CategoryDto.class)).collect(Collectors.toList());
    }

    /**
     * Método para crear o actualizar una categoria
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Category")
    @PutMapping(path = { "", "/{id}" })
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody @Valid CategoryDto dto) {
        this.categoryService.save(id, dto);
    }

    /**
     * Método para borrar una categoria
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Category")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.categoryService.delete(id);
    }
}
