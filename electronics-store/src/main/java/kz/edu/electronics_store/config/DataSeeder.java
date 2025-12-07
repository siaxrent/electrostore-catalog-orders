package kz.edu.electronics_store.config;

import kz.edu.electronics_store.entity.*;
import kz.edu.electronics_store.repository.CategoryRepository;
import kz.edu.electronics_store.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

/**
 * Начальное заполнение справочников (категории/товары) для разработки.
 * Ограничено профилем dev, чтобы в прод-среде (postgres) данные не перезаписывались.
 */
@Configuration
@Profile("dev")
public class DataSeeder {

    @Bean
    CommandLineRunner seed(CategoryRepository categoryRepo, ProductRepository productRepo) {
        return args -> {
            if (categoryRepo.count() > 0) return; // чтобы не дублировать при перезапуске

            Category phones = categoryRepo.save(new Category("Smartphones"));
            Category laptops = categoryRepo.save(new Category("Laptops"));

            productRepo.save(new Product("SKU-IPH-15", "iPhone 15", new BigDecimal("499999.00"), 10, ProductType.SMARTPHONE, phones));
            productRepo.save(new Product("SKU-MBP-14", "MacBook Pro 14", new BigDecimal("999999.00"), 5, ProductType.LAPTOP, laptops));
        };
    }
}
