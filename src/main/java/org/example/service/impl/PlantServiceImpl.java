package org.example.service.impl;

import org.example.entities.Simulation;
import org.example.entities.plants.Plant;
import org.example.service.PlantService;
import org.example.utils.EntityMapper;
import java.util.List;
import static org.example.utils.Constants.BIRTH_RATE_COEFFICIENT;

public class PlantServiceImpl implements PlantService {

    @Override
    public void reproduce(Simulation simulation) {
        List<Plant> plants = simulation.getPlants();

        List<Plant> suitablePlants = plants.stream()
                .filter(this::canReproduce)
                .toList();

        int newPlantsCount = (int) (suitablePlants.size() * BIRTH_RATE_COEFFICIENT);

        for (int i = 0; i < newPlantsCount; i++) {
            if (i >= suitablePlants.size()) break; // Если подходящих растений меньше, чем количество новых

            Plant parentPlant = suitablePlants.get(i % suitablePlants.size());

            Plant newPlant = EntityMapper.createNewPlant(parentPlant);

            simulation.getPlants().add(newPlant);

            parentPlant.setHitPoints(parentPlant.getHitPoints() - 1);

            System.out.printf("Растение %s размножилось.%n", parentPlant.getName());
        }
    }

    private boolean canReproduce(Plant plant) {
        return plant.getHitPoints() > 2;
    }
}