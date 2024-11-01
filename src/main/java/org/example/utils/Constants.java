package org.example.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final double BIRTH_RATE_COEFFICIENT = 1.0;

    //MENU CONSTANTS
    public static final String MAIN_MENU_UI = """
            === Экосистема ===
            1. Создать новую симуляцию
            2. Загрузить существующую симуляцию
            3. Выход
            """;

    public static final String SIMULATION_MENU_UI = """
            === Меню симуляции===
            1. Добавить животных
            2. Добавить растения
            3. Изменить климат
            4. Показать текущее состояние
            5. Запустить симуляцию
            6. Получить прогноз
            7. Сохранить и выйти
            """;

    public static final String ANIMAL_MENU_UI = """
            === Добавление животных ===
            1. Добавить травоядное
            2. Добавить хищника
            3. Добавить всеядное
            4. Назад
            """;
}
