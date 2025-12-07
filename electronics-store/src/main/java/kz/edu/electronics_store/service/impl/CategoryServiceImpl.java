package kz.edu.electronics_store.service.impl;

import kz.edu.electronics_store.dto.CategoryCreateRequest;
import kz.edu.electronics_store.dto.CategoryDto;
import kz.edu.electronics_store.entity.Category;
import kz.edu.electronics_store.exception.NotFoundException;
import kz.edu.electronics_store.repository.CategoryRepository;
import kz.edu.electronics_store.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация сервиса работы с категориями.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Конструктор с внедрением репозитория категорий.
     *
     * @param categoryRepository репозиторий категорий
     */
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> list() {
        return categoryRepository.findAll().stream()
                .map(c -> new CategoryDto(c.getId(), c.getName()))
                .toList();
    }

    @Override
    public CategoryDto create(CategoryCreateRequest req) {
        Category saved = categoryRepository.save(new Category(req.name()));
        return new CategoryDto(saved.getId(), saved.getName());
    }

    @Override
    public void delete(long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Категория не найдена: id=" + id);
        }
        categoryRepository.deleteById(id);
    }
}
