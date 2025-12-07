package kz.edu.electronics_store.controller;

import jakarta.validation.Valid;
import kz.edu.electronics_store.dto.CategoryCreateRequest;
import kz.edu.electronics_store.dto.CategoryDto;
import kz.edu.electronics_store.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления категориями товаров.
 * <p>
 * Позволяет получать список категорий, создавать новые и удалять существующие.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Конструктор с внедрением сервиса категорий.
     *
     * @param categoryService сервис работы с категориями
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Получить все категории.
     *
     * @return список DTO категорий
     */
    @GetMapping
    public List<CategoryDto> list() {
        return categoryService.list();
    }

    /**
     * Создать новую категорию.
     *
     * @param req данные новой категории
     * @return созданная категория
     */
    @PostMapping
    public CategoryDto create(@Valid @RequestBody CategoryCreateRequest req) {
        return categoryService.create(req);
    }

    /**
     * Удалить категорию по идентификатору.
     *
     * @param id идентификатор категории
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        categoryService.delete(id);
    }
}
