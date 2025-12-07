package kz.edu.electronics_store.entity;

/**
 * Тип товара (для классификации электроники).
 * <p>
 * Значения зашиты в коде и используются как высокоуровневые виды,
 * а категории уточняют принадлежность внутри вида.
 */
public enum ProductType {
    // Базовые виды, которые уже используются в сидерах и данных
    SMARTPHONE,
    LAPTOP,
    ACCESSORY,
    TABLET,
    MONITOR,

    // Расширенный список видов из ТЗ (для новых товаров)
    SMARTPHONES_AND_GADGETS,
    COMPUTERS_AND_LAPTOPS,
    PC_COMPONENTS,
    PC_PERIPHERALS_AND_ACCESSORIES,
    TV_AND_VIDEO,
    AUDIO,
    PHOTO_AND_VIDEO,
    GAMES_AND_CONSOLES,
    SMART_HOME,
    NETWORK_EQUIPMENT,
    OFFICE_EQUIPMENT,
    STORAGE_AND_MEMORY,
    CABLES_AND_ADAPTERS,
    CHARGERS_AND_POWER,
    SMALL_APPLIANCES,
    CLIMATE_APPLIANCES,
    CAR_ELECTRONICS,
    TOOLS_AND_METERS,
    HOME_ELECTRONICS,
    DISCOUNT_AND_SALE
}
