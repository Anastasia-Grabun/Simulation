package org.example.controllers;

import lombok.Getter;
import org.example.entities.Climate;
import org.example.entities.Simulation;
import org.example.entities.animals.Animal;
import org.example.entities.animals.Herbivore;
import org.example.entities.animals.Omnivore;
import org.example.entities.animals.Predator;
import org.example.entities.animals.enums.Gender;
import org.example.entities.plants.Plant;
import org.example.service.FileService;
import org.example.service.impl.FileServiceImpl;
import org.example.service.impl.SimulationServiceImpl;
import org.example.utils.InputUtils;
import org.example.utils.Constants;
import org.example.utils.SerializeUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import static org.example.utils.InputUtils.getDoubleInput;
import static org.example.utils.InputUtils.getIntInput;
import static org.example.utils.InputUtils.getStringInput;

public class SimulationController {
    private final Scanner scanner;
    private final SimulationServiceImpl simulationService;
    private final FileServiceImpl fileServiceImpl;
    @Getter
    private static SimulationController controller;

    public SimulationController() {
        this.scanner = new Scanner(System.in);
        this.simulationService = new SimulationServiceImpl();
        this.fileServiceImpl = new FileServiceImpl();
        controller = this;
    }

    public void run() {
        start();
    }

    public void start() {

        while (true) {
            System.out.println(Constants.MAIN_MENU_UI);
            int choice = getIntInput("Выберите опцию: ");

            switch (choice) {
                case 1:
                    createNewSimulation();
                    break;
                case 2:
                    chooseSimulation();
                    break;
                case 3:
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Неверный выбор! Пожалуйста, попробуйте снова.");
            }
        }
    }

    private void simulationMenu(Simulation simulation) {
        while (true) {
            System.out.println(Constants.SIMULATION_MENU_UI);
            int choice = getIntInput("Выберите опцию: ");

            switch (choice) {
                case 1:
                    addAnimalsMenu(simulation);
                    break;

                case 2:
                    addPlantsMenu(simulation);
                    break;

                case 3:
                    setupClimate(simulation);
                    break;

                case 4:
                    showCurrentState(simulation);
                    break;

                case 5:
                    runSimulation(simulation);
                    break;

                case 6:
                    showPredictions(simulation);
                    break;

                case 7:
                    simulationService.saveSimulation(simulation);
                    return;

                default:
                    System.out.println("Неверный выбор! Пожалуйста, выберите опцию из меню.");
                    break;
            }
        }
    }

    private void createNewSimulation() {
        System.out.println("\n=== Создание новой симуляции ===");

        Simulation simulation = new Simulation();

        simulation.setName(getUniqueSimulationName());

        controller.setupClimate(simulation);

        controller.simulationMenu(simulation);
    }

    private String getUniqueSimulationName() {
        FileService fileService = new FileServiceImpl();
        List<String> existingSimulations = fileService.readFromFile("src/main/resources/simulation_list.txt");

        String simulationName;
        while (true) {
            simulationName = InputUtils.getStringInput("Введите название симуляции (должно быть уникальным): ");

            if (!existingSimulations.contains(simulationName)) break;
            else System.out.println("Симуляция с таким именем уже существует. Пожалуйста, введите другое имя.");
        }
        return simulationName;
    }

    private void setupClimate(Simulation simulation) {
        System.out.println("\n=== Настройка климата ===");

        double temperature = getDoubleInput("Введите температуру (от -89°C до 56°C): ");
        while(temperature < -89.0 || temperature > 56.0){
            temperature = getIntInput("Введите повторно: ");
        }

        int humidity = getIntInput("Введите влажность (от 1% до 100%): ");
        while(humidity < 1 || humidity > 100){
            humidity = getIntInput("Введите повторно: ");
        }

        Climate climate = new Climate(temperature, humidity);

        simulation.setClimate(climate);
    }

    private void addAnimalsMenu(Simulation simulation) {
        while (true) {
            System.out.println(Constants.ANIMAL_MENU_UI);

            int choice = getIntInput("Выберите тип животного: ");
            while (choice < 1 || choice > 4) {
                choice = getIntInput("Выберите ещё раз: ");
            }

            if (choice == 4) break;

            String name = getStringInput("Введите имя животного: ");

            int hitPoints = getIntInput("Введите очки здоровья (от 1 до 30): ");
            while (hitPoints < 1 || hitPoints > 30) {
                hitPoints = getIntInput("Введите повторно: ");
            }

            int attackDamage = getIntInput("Введите силу атаки (от 1 до 10): ");
            while (attackDamage < 1 || attackDamage > 10) {
                attackDamage = getIntInput("Введите повторно: ");
            }

            int minHumidity = getIntInput("Введите минимальную влажность (от 1% до 100%): ");
            while(minHumidity < 1 || minHumidity > 100){
                minHumidity = getIntInput("Введите повторно: ");
            }

            int maxHumidity = getIntInput("Введите максимальную влажность (от 1% до 100%): ");
            while(maxHumidity < 1 || maxHumidity > 100){
                maxHumidity = getIntInput("Введите повторно: ");
            }

            double minTemperature = getDoubleInput("Введите минимальную температуру (от -89°C до 56°C): ");
            while(minTemperature < -89.0 || minTemperature > 56.0){
                minTemperature = getIntInput("Введите повторно: ");
            }

            double maxTemperature = getDoubleInput("Введите максимальную температуру (от -89°C до 56°C): ");
            while(maxTemperature< -89.0 || maxTemperature > 56.0){
                maxTemperature= getIntInput("Введите повторно: ");
            }

            System.out.println("Выберите пол животного:");
            System.out.println("1. Мужской");
            System.out.println("2. Женский");
            int genderChoice = scanner.nextInt();
            while (genderChoice != 1 && genderChoice != 2) {
                genderChoice = getIntInput("Введите повторно: ");
            }
            Gender gender = genderChoice == 1 ? Gender.MALE : Gender.FEMALE;

            switch (choice) {
                case 1:
                    int nutritionValue = getIntInput("Введите питательную ценность: ");
                    Herbivore herbivore = Herbivore.builder()
                            .name(name)
                            .hitPoints(hitPoints)
                            .attackDamage(attackDamage)
                            .gender(gender)
                            .nutritionValue(nutritionValue)
                            .minHumidity(minHumidity)
                            .maxHumidity(maxHumidity)
                            .minTemperature(minTemperature)
                            .maxTemperature(maxTemperature)
                            .build();

                    simulation.getHerbivores().add(herbivore);
                    break;

                case 2:
                    Predator predator = Predator.builder()
                            .name(name)
                            .hitPoints(hitPoints)
                            .attackDamage(attackDamage)
                            .gender(gender)
                            .minHumidity(minHumidity)
                            .maxHumidity(maxHumidity)
                            .minTemperature(minTemperature)
                            .maxTemperature(maxTemperature)
                            .build();

                    simulation.getPredators().add(predator);
                    break;

                case 3:
                    Omnivore omnivore = Omnivore.builder()
                            .name(name)
                            .hitPoints(hitPoints)
                            .attackDamage(attackDamage)
                            .gender(gender)
                            .minHumidity(minHumidity)
                            .maxHumidity(maxHumidity)
                            .minTemperature(minTemperature)
                            .maxTemperature(maxTemperature)
                            .build();

                    simulation.getOmnivores().add(omnivore);
                    break;
            }
        }
    }


    private void addPlantsMenu(Simulation simulation) {
        System.out.println("\n=== Добавление растений ===");

        String name = getStringInput("Введите название растения: ");
        int hitPoints = getIntInput("Введите очки здоровья: ");
        int nutritionValue = getIntInput("Введите питательную ценность: ");

        int minHumidity = getIntInput("Введите минимальную влажность (от 1 до 100): ");
        while(minHumidity < 1 || minHumidity > 100){
            minHumidity = getIntInput("Введите повторно: ");
        }

        int maxHumidity = getIntInput("Введите максимальную влажность (от 1 до 100): ");
        while(maxHumidity < 1 || maxHumidity > 100){
            maxHumidity = getIntInput("Введите повторно: ");
        }

        double minTemperature = getDoubleInput("Введите минимальную температуру (от -89 до 56): ");
        while(minTemperature < -89.0 || minTemperature > 56.0){
            minTemperature = getIntInput("Введите повторно: ");
        }

        double maxTemperature = getDoubleInput("Введите максимальную температуру (от -89 до 56): ");
        while(maxTemperature< -89.0 || maxTemperature > 56.0){
            maxTemperature= getIntInput("Введите повторно: ");
        }

        Plant plant = Plant.builder()
                .name(name)
                .hitPoints(hitPoints)
                .nutritionValue(nutritionValue)
                .minHumidity(minHumidity)
                .maxHumidity(maxHumidity)
                .minTemperature(minTemperature)
                .maxTemperature(maxTemperature)
                .build();

        simulation.getPlants().add(plant);
    }

    public void showCurrentState(Simulation simulation) {
        System.out.println("\n=== Текущее состояние экосистемы ===");

        if (simulation.getClimate() == null) {
            System.out.println("Климатические условия не инициализированы.");
            return;
        }

        System.out.println("\nКлиматические условия:");
        System.out.printf("Температура: %.1f°C%n", simulation.getClimate().getTemperature());
        System.out.printf("Влажность: %d%%%n", simulation.getClimate().getHumidity());

        List<Herbivore> herbivores = simulation.getHerbivores();
        List<Omnivore> omnivores = simulation.getOmnivores();
        List<Predator> predators = simulation.getPredators();
        List<Plant> plants = simulation.getPlants();

        Map<String, Integer> animalStats = new HashMap<>();
        List<Animal> allAnimals = new ArrayList<>();
        allAnimals.addAll(herbivores);
        allAnimals.addAll(omnivores);
        allAnimals.addAll(predators);

        System.out.println("\nПопуляция животных:");
        if (allAnimals.isEmpty()) {
            System.out.println("Нет животных.");
        } else {
            allAnimals.forEach(animal -> {
                String type = animal.getClass().getSimpleName();
                animalStats.merge(type, 1, Integer::sum);
            });

            animalStats.forEach((type, count) ->
                    System.out.printf("%s: %d особей%n", type, count));
        }

        System.out.println("\nПодробная информация о животных:");
        allAnimals.forEach(animal -> {
            System.out.printf("- %s - %s:%n",
                    animal.getClass().getSimpleName(),
                    animal.getName());
            System.out.printf("  Здоровье: %d%n", animal.getHitPoints());
            System.out.printf("  Сила атаки: %d%n", animal.getAttackDamage());
            if (animal instanceof Herbivore) {
                System.out.printf("  Питательная ценность: %d%n",
                        ((Herbivore) animal).getNutritionValue());
            }
        });

        long plantCount = plants.size();
        System.out.println("\nРастения:");
        System.out.printf("Всего растений: %d%n", plantCount);

        if (plantCount == 0) {
            System.out.println("Нет растений.");
        } else {
            plants.forEach(plant -> {
                System.out.printf("- Plant - %s:%n", plant.getName());
                System.out.printf("  Здоровье: %d%n", plant.getHitPoints());
                System.out.printf("  Питательная ценность: %d%n",
                        plant.getNutritionValue());
            });
        }
    }

    private void runSimulation(Simulation simulation) {
        System.out.println("\n=== Запуск симуляции ===");

        int steps = getIntInput("Введите количество шагов симуляции (до 10): ");
        while (steps < 1 || steps > 10) {
            steps = getIntInput("Введите повторно: ");
        }

        System.out.println("\nНачало симуляции...");

        for (int i = 0; i < steps; i++) {
            System.out.printf("\nШаг симуляции %d из %d%n", (i + 1), steps);
            simulationService.simulateStep(simulation);
        }

        simulationService.saveSimulation(simulation);
        System.out.println("\nСимуляция завершена!");
    }


    private void showPredictions(Simulation simulation) {
        System.out.println("\n=== Прогноз развития экосистемы ===");
        Map<String, String> predictions = simulationService.predictPopulation(simulation);

        predictions.forEach((entity, prediction) ->
                System.out.printf("%s: %s%n", entity, prediction));
    }

    private void chooseSimulation() {
        System.out.println("Выберите симуляцию:");

        List<String> simulationNames = fileServiceImpl.readFromFile("src/main/resources/simulation_list.txt");

        for (int i = 1; i <= simulationNames.size(); ++i) {
            System.out.printf("%s. %s%n", i, simulationNames.get(i - 1));
        }

        while (true) {
            int simulationChoice = getIntInput("Введите номер симуляции (или 0 для выхода): ");

            if (simulationChoice == 0) {
                System.out.println("Выход из выбора симуляции.");
                return;
            }

            if (simulationChoice < 1 || simulationChoice > simulationNames.size()) {
                System.out.println("Неверный выбор! Пожалуйста, попробуйте снова.");
                continue;
            }

            String selectedSimulationName = simulationNames.get(simulationChoice - 1);
            Simulation loadedSimulation = SerializeUtils.deserializeSimulationByName(selectedSimulationName);

            if (loadedSimulation != null) {

                createNewSimulationFromExisting(loadedSimulation);
                return;
            } else {
                System.out.println("Ошибка при загрузке симуляции. Попробуйте снова.");
            }
        }
    }

    private void createNewSimulationFromExisting(Simulation existingSimulation) {
        System.out.println("\n=== Создание новой симуляции на основе существующей ===");

        Simulation newSimulation = new Simulation();
        newSimulation.setName(getUniqueSimulationName());

        newSimulation.setClimate(existingSimulation.getClimate());
        newSimulation.setHerbivores(new ArrayList<>(existingSimulation.getHerbivores()));
        newSimulation.setOmnivores(new ArrayList<>(existingSimulation.getOmnivores()));
        newSimulation.setPredators(new ArrayList<>(existingSimulation.getPredators()));
        newSimulation.setPlants(new ArrayList<>(existingSimulation.getPlants()));

        controller.simulationMenu(newSimulation);
    }
}
