package kz.edu.electronics_store.controller;

import jakarta.validation.Valid;
import kz.edu.electronics_store.dto.ProductCreateRequest;
import kz.edu.electronics_store.dto.ProductDto;
import kz.edu.electronics_store.entity.Product;
import kz.edu.electronics_store.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для работы с товарами.
 * <p>
 * Позволяет получать список товаров, информацию о конкретном товаре
 * и создавать новые позиции каталога.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Конструктор с внедрением сервиса товаров.
     *
     * @param productService сервис работы с товарами
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Получить список всех товаров каталога.
     *
     * @return список DTO товаров
     */
    @GetMapping
    public List<ProductDto> list() {
        return productService.list().stream().map(this::toDto).toList();
    }

    /**
     * Получить товар по идентификатору.
     *
     * @param id идентификатор товара
     * @return DTO найденного товара
     */
    @GetMapping("/{id}")
    public ProductDto get(@PathVariable long id) {
        return toDto(productService.getById(id));
    }

    /**
     * Создать новый товар.
     *
     * @param req данные для создания товара
     * @return DTO созданного товара
     */
    @PostMapping
    public ProductDto create(@Valid @RequestBody ProductCreateRequest req) {
        return toDto(productService.create(req));
    }

    private ProductDto toDto(Product p) {
        return new ProductDto(
                p.getId(),
                p.getSku(),
                p.getName(),
                p.getPrice(),
                p.getStock(),
                p.getType(),
                p.getCategory().getId(),
                p.getCategory().getName()
        );
    }
}
