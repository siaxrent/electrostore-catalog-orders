package kz.edu.electronics_store;

import kz.edu.electronics_store.entity.Category;
import kz.edu.electronics_store.entity.Product;
import kz.edu.electronics_store.entity.ProductType;
import kz.edu.electronics_store.repository.CategoryRepository;
import kz.edu.electronics_store.repository.ProductRepository;
import kz.edu.electronics_store.repository.OrderItemRepository;
import kz.edu.electronics_store.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderApiTest {

    @Autowired MockMvc mvc;
    @Autowired OrderItemRepository orderItemRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired ProductRepository productRepository;
    @Autowired CategoryRepository categoryRepository;

    long product1Id;
    long product2Id;

    @BeforeEach
    void setup() {
        // ВАЖНО: чистим в порядке, учитывающем внешние ключи
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        Category c = categoryRepository.save(new Category("TestCat"));

        Product p1 = new Product("SKU-T1", "TestPhone", new BigDecimal("1000.00"), 10, ProductType.SMARTPHONE, c);
        Product p2 = new Product("SKU-T2", "TestAcc", new BigDecimal("200.00"), 5, ProductType.ACCESSORY, c);

        product1Id = productRepository.save(p1).getId();
        product2Id = productRepository.save(p2).getId();
    }

    @Test
    void createOrder_shouldReturn200_andCorrectTotal() throws Exception {
        String body = """
                {
                  "customerName":"Test",
                  "customerPhone":"+77000000000",
                  "items":[
                    {"productId":%d,"quantity":1},
                    {"productId":%d,"quantity":2}
                  ]
                }
                """.formatted(product1Id, product2Id);

        mvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(1400.00))
                .andExpect(jsonPath("$.items.length()").value(2));
    }

    @Test
    void createOrder_whenNotEnoughStock_shouldReturn400() throws Exception {
        String body = """
                {
                  "customerName":"Test",
                  "customerPhone":"+77000000000",
                  "items":[{"productId":%d,"quantity":999}]
                }
                """.formatted(product1Id);

        mvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStatus_shouldWork() throws Exception {
        // create order
        String body = """
                {"customerName":"Test","customerPhone":"+77000000000","items":[{"productId":%d,"quantity":1}]}
                """.formatted(product1Id);

        String response = mvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // очень грубо достаём id из ответа (для курсовой хватит)
        long orderId = Long.parseLong(response.replaceAll(".*\"id\":(\\d+).*", "$1"));

        mvc.perform(patch("/api/orders/" + orderId + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"PAID\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAID"));
    }
}
