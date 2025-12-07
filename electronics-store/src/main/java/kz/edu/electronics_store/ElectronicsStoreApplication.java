package kz.edu.electronics_store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Точка входа в приложение интернет-магазина электроники.
 * <p>
 * Запускает Spring Boot и поднимает все REST-контроллеры, слои сервиса и доступ к БД.
 */
@SpringBootApplication
public class ElectronicsStoreApplication {

	/**
	 * Старт приложения.
	 *
	 * @param args аргументы командной строки, не используются.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ElectronicsStoreApplication.class, args);
	}

}
