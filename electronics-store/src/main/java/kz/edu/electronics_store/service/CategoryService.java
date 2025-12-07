package kz.edu.electronics_store.service;

import kz.edu.electronics_store.dto.CategoryCreateRequest;
import kz.edu.electronics_store.dto.CategoryDto;

import java.util.List;

/**
 * Сервисный интерфейс для работы с категориями товаров.
 */
public interface CategoryService {

    /**
     * Получить все категории.
     *
     * @return список DTO категорий
     */
    List<CategoryDto> list();

    /**
     * Создать новую категорию.
     *
     * @param req DTO с названием категории
     * @return созданная категория
     */
    CategoryDto create(CategoryCreateRequest req);

    /**
     * Удалить категорию по идентификатору.
     *
     * @param id идентификатор категории
     */
    void delete(long id);
}
