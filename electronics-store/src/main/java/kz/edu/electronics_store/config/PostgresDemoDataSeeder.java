package kz.edu.electronics_store.config;

import kz.edu.electronics_store.dto.OrderCreateRequest;
import kz.edu.electronics_store.entity.Category;
import kz.edu.electronics_store.entity.Product;
import kz.edu.electronics_store.entity.ProductType;
import kz.edu.electronics_store.repository.CategoryRepository;
import kz.edu.electronics_store.repository.ProductRepository;
import kz.edu.electronics_store.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.List;

/**
 * Демонстрационное наполнение PostgreSQL- БД начальными данными.
 * <p>
 * Выполняется только в профиле {@code postgres} и только если БД пуста
 * (нет ни одной категории).
 */
@Configuration
@Profile("postgres")
public class PostgresDemoDataSeeder {

    @Bean
    CommandLineRunner postgresSeed(CategoryRepository categoryRepo,
                                   ProductRepository productRepo,
                                   OrderService orderService) {
        return args -> {
            // Если в БД уже есть категории – считаем, что данные созданы вручную
            if (categoryRepo.count() > 0) {
                return;
            }

            // --- Категории (подтипы внутри вида) ---
            Category flagshipPhones = categoryRepo.save(new Category("Флагманские смартфоны"));
            Category budgetPhones = categoryRepo.save(new Category("Бюджетные смартфоны"));
            Category gamingLaptops = categoryRepo.save(new Category("Игровые ноутбуки"));
            Category workLaptops = categoryRepo.save(new Category("Рабочие ноутбуки"));
            Category appleLaptops = categoryRepo.save(new Category("Ноутбуки Apple"));
            Category laptopDocks = categoryRepo.save(new Category("Док-станции для ноутбуков"));
            Category tablets = categoryRepo.save(new Category("Планшеты"));

            // --- Товары ---
            Product iphone15 = productRepo.save(new Product(
                    "SKU-IPH-15",
                    "iPhone 15",
                    new BigDecimal("499999.00"),
                    15,
                    ProductType.SMARTPHONE,
                    flagshipPhones
            ));

            Product galaxyS24 = productRepo.save(new Product(
                    "SKU-SAM-S24",
                    "Samsung Galaxy S24",
                    new BigDecimal("399999.00"),
                    20,
                    ProductType.SMARTPHONE,
                    budgetPhones
            ));

            Product macbook14 = productRepo.save(new Product(
                    "SKU-MBP-14",
                    "MacBook Pro 14",
                    new BigDecimal("999999.00"),
                    8,
                    ProductType.LAPTOP,
                    appleLaptops
            ));

            Product lenovoIdea = productRepo.save(new Product(
                    "SKU-LEN-IDEA",
                    "Lenovo IdeaPad 5",
                    new BigDecimal("299999.00"),
                    12,
                    ProductType.LAPTOP,
                    workLaptops
            ));

            Product iphoneCase = productRepo.save(new Product(
                    "SKU-CASE-IPH15",
                    "Чехол силиконовый для iPhone 15",
                    new BigDecimal("9999.00"),
                    50,
                    ProductType.ACCESSORY,
                    budgetPhones
            ));

            Product laptopDock = productRepo.save(new Product(
                    "SKU-DOCK-USB4",
                    "Док-станция USB4 для ноутбука",
                    new BigDecimal("79999.00"),
                    18,
                    ProductType.ACCESSORY,
                    laptopDocks
            ));

            Product ipad = new Product(
                    "SKU-IPAD-11",
                    "iPad 11\"",
                    new BigDecimal("349999.00"),
                    10,
                    ProductType.TABLET,
                    tablets
            );
            productRepo.save(ipad);

            Product dellMonitor = productRepo.save(new Product(
                    "SKU-DELL-27",
                    "Dell 27\" монитор",
                    new BigDecimal("199999.00"),
                    7,
                    ProductType.MONITOR,
                    laptopDocks
            ));

            // --- Пара демонстрационных заказов ---
            orderService.create(new OrderCreateRequest(
                    "Иван Иванов",
                    "+77001112233",
                    List.of(
                            new OrderCreateRequest.Item(iphone15.getId(), 1),
                            new OrderCreateRequest.Item(iphoneCase.getId(), 2)
                    )
            ));

            orderService.create(new OrderCreateRequest(
                    "Алия Серикова",
                    "+77005556677",
                    List.of(
                            new OrderCreateRequest.Item(galaxyS24.getId(), 1),
                            new OrderCreateRequest.Item(laptopDock.getId(), 1),
                            new OrderCreateRequest.Item(ipad.getId(), 1)
                    )
            ));
        };
    }
}


