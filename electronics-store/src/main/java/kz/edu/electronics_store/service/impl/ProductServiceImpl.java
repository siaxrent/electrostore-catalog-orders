package kz.edu.electronics_store.service.impl;

import kz.edu.electronics_store.dto.ProductCreateRequest;
import kz.edu.electronics_store.entity.Category;
import kz.edu.electronics_store.entity.Product;
import kz.edu.electronics_store.exception.NotFoundException;
import kz.edu.electronics_store.repository.CategoryRepository;
import kz.edu.electronics_store.repository.ProductRepository;
import kz.edu.electronics_store.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация сервиса работы с товарами.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /**
     * Конструктор с внедрением репозиториев товаров и категорий.
     *
     * @param productRepository  репозиторий товаров
     * @param categoryRepository репозиторий категорий
     */
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> list() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Товар не найден: id=" + id));
    }

    @Override
    public Product create(ProductCreateRequest req) {
        Category category = categoryRepository.findById(req.categoryId())
                .orElseThrow(() -> new NotFoundException("Категория не найдена: id=" + req.categoryId()));

        Product p = new Product(
                req.sku(),
                req.name(),
                req.price(),
                req.stock(),
                req.type(),
                category
        );

        return productRepository.save(p);
    }
}
