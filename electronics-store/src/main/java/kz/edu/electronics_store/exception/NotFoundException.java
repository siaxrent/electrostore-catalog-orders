package kz.edu.electronics_store.exception;

/**
 * Исключение: сущность не найдена.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
