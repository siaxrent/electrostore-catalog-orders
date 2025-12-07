package kz.edu.electronics_store.service;

import kz.edu.electronics_store.dto.ProductCreateRequest;
import kz.edu.electronics_store.entity.Product;

import java.util.List;

/**
 * Сервисный интерфейс для работы с товарами каталога.
 */
public interface ProductService {

    /**
     * Получить все товары.
     *
     * @return список сущностей товаров
     */
    List<Product> list();

    /**
     * Найти товар по идентификатору.
     *
     * @param id идентификатор товара
     * @return найденный товар
     */
    Product getById(long id);

    /**
     * Создать новый товар.
     *
     * @param req DTO с данными для создания
     * @return сохранённый товар
     */
    Product create(ProductCreateRequest req);
}
