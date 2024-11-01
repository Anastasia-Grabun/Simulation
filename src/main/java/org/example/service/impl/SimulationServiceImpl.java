package org.example.service.impl;

import org.example.entities.Simulation;
import org.example.entities.animals.Animal;
import org.example.entities.animals.Herbivore;
import org.example.entities.animals.Omnivore;
import org.example.entities.animals.Predator;
import org.example.entities.animals.enums.Gender;
import org.example.entities.plants.Plant;
import org.example.service.FileService;
import org.example.service.SimulationService;
import org.example.utils.EntityValidator;
import org.example.utils.SerializeUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationServiceImpl implements SimulationService {
    private final HerbivoreServiceImpl herbivoreService;
    private final OmnivoreServiceImpl omnivoreService;
    private final PredatorServiceImpl predatorService;
    private final PlantServiceImpl plantService;
    private final FileService fileService;

    public SimulationServiceImpl() {
        this.herbivoreService = new HerbivoreServiceImpl();
        this.omnivoreService = new OmnivoreServiceImpl();
        this.predatorService = new PredatorServiceImpl();
        this.plantService = new PlantServiceImpl();
        this.fileService = new FileServiceImpl();
    }

    public void simulateStep(Simulation simulation) {
            validateAndRemoveUnsuitableEntities(simulation);

            consumeAllAnimals(simulation);

            reproduceAllEntities(simulation);

            removeDeadEntities(simulation);
    }

    public void saveSimulation(Simulation simulation) {
        if (simulation != null) {
            SerializeUtils.serializeSimulation(simulation);

            List<String> existingSimulations = fileService.readFromFile("src/main/resources/simulation_list.txt");
            if (!existingSimulations.contains(simulation.getName())) {
                fileService.addData("src/main/resources/simulation_list.txt", simulation.getName());
            }
        }
    }

    @Override
    public Map<String, String> predictPopulation(Simulation simulation) {
        Map<String, String> predictions = new HashMap<>();

        List<Herbivore> herbivores = simulation.getHerbivores();
        List<Omnivore> omnivores = simulation.getOmnivores();
        List<Predator> predators = simulation.getPredators();
        List<Plant> plants = simulation.getPlants();

        processPlantPopulations(plants, predictions);

        processAnimalPopulations(herbivores, predictions);
        processAnimalPopulations(omnivores, predictions);
        processAnimalPopulations(predators, predictions);

        return predictions;
    }

    private void processPlantPopulations(List<Plant> plants, Map<String, String> predictions) {
        Map<String, List<Integer>> plantGroups = new HashMap<>();

        for (Plant plant : plants) {
            String name = plant.getName();
            int hitPoints = plant.getHitPoints();
            plantGroups.computeIfAbsent(name, k -> new ArrayList<>()).add(hitPoints);
        }

        for (Map.Entry<String, List<Integer>> entry : plantGroups.entrySet()) {
            String name = entry.getKey();
            List<Integer> hitPointsList = entry.getValue();
            double averageHitPoints = hitPointsList.stream().mapToInt(Integer::intValue).average().orElse(0);

            if (averageHitPoints > 15) {
                predictions.put(name, "Популяция будет расти.");
            } else if (averageHitPoints == 15) {
                predictions.put(name, "Популяция останется стабильной.");
            } else {
                predictions.put(name, "Популяция будет уменьшаться.");
            }
        }
    }

    private <T extends Animal> void processAnimalPopulations(List<T> animals, Map<String, String> predictions) {
        Map<String, List<Integer>> animalGroups = new HashMap<>();

        for (T animal : animals) {
            String key = animal.getName() + "_" + animal.getClass().getSimpleName(); // Группируем по имени и типу
            int hitPoints = animal.getHitPoints();
            animalGroups.computeIfAbsent(key, k -> new ArrayList<>()).add(hitPoints);
        }

        for (Map.Entry<String, List<Integer>> entry : animalGroups.entrySet()) {
            String name = entry.getKey();
            List<Integer> hitPointsList = entry.getValue();
            double averageHitPoints = hitPointsList.stream().mapToInt(Integer::intValue).average().orElse(0);

            // Проверяем наличие пар
            boolean hasPairs = hasPairs(animals, name);

            if (averageHitPoints > 15 && hasPairs) {
                predictions.put(name, "Популяция будет расти.");
            } else if (averageHitPoints <= 15 && hasPairs) {
                predictions.put(name, "Популяция останется стабильной из-за недостаточного уровня hitPoints.");
            } else {
                predictions.put(name, "Популяция будет уменьшаться из-за отсутствия подходящих пар.");
            }
        }
    }

    private boolean hasPairs(List<? extends Animal> animals, String key) {
        String name = key.split("_")[0]; // Извлекаем имя из ключа
        List<Animal> males = new ArrayList<>();
        List<Animal> females = new ArrayList<>();

        for (Animal animal : animals) {
            if (animal.getName().equals(name) && animal.getClass().getSimpleName().equals(key.split("_")[1])) { // Проверяем только по имени и типу
                if (animal.getGender().equals(Gender.MALE)) males.add(animal);
                else if (animal.getGender().equals(Gender.FEMALE)) females.add(animal);
            }
        }

        return !males.isEmpty() && !females.isEmpty();
    }

    private void validateAndRemoveUnsuitableEntities(Simulation simulation) {
        simulation.setHerbivores(EntityValidator.validateHerbivores(simulation.getHerbivores(), simulation.getClimate()));
        simulation.setOmnivores(EntityValidator.validateOmnivores(simulation.getOmnivores(), simulation.getClimate()));
        simulation.setPredators(EntityValidator.validatePredators(simulation.getPredators(), simulation.getClimate()));
        simulation.setPlants(EntityValidator.validatePlants(simulation.getPlants(), simulation.getClimate()));
    }

    private void consumeAllAnimals(Simulation simulation) {
        herbivoreService.consume(simulation);

        omnivoreService.consume(simulation);

        predatorService.consume(simulation);
    }

    private void reproduceAllEntities(Simulation simulation) {
        herbivoreService.reproduce(simulation);

        omnivoreService.reproduce(simulation);

        predatorService.reproduce(simulation);

        plantService.reproduce(simulation);
    }

    private void removeDeadEntities(Simulation simulation) {
        simulation.getHerbivores().removeIf(this::isAnimalExtinct);

        simulation.getOmnivores().removeIf(this::isAnimalExtinct);

        simulation.getPredators().removeIf(this::isAnimalExtinct);

        simulation.getPlants().removeIf(this::isPlantExtinct);
    }

    private boolean isAnimalExtinct(Animal animal) {
        return animal.getHitPoints() <= 0;
    }

    private boolean isPlantExtinct(Plant plant) {
        return plant.getHitPoints() <= 0;
    }
}


