package org.example.service.impl;

import lombok.SneakyThrows;
import org.example.entities.Simulation;
import org.example.entities.animals.Herbivore;
import org.example.entities.animals.enums.Gender;
import org.example.entities.plants.Plant;
import org.example.service.AnimalService;
import org.example.utils.EntityMapper;
import java.util.ArrayList;
import java.util.List;
import static org.example.utils.Constants.BIRTH_RATE_COEFFICIENT;

public class HerbivoreServiceImpl implements AnimalService {

    @Override
    @SneakyThrows
    public void consume(Simulation simulation) {
        List<Herbivore> herbivores = simulation.getHerbivores();
        List<Plant> plants = simulation.getPlants();

        if (simulation.getPlants().isEmpty() || simulation.getHerbivores().isEmpty())
            return;

        Herbivore herbivore = herbivores.get((int) (Math.random() * herbivores.size()));
        Plant plant = plants.get((int) (Math.random() * plants.size()));

        System.out.println(herbivore.getName() + " съел " + plant.getName() + ".");

        herbivore.setHitPoints(herbivore.getHitPoints() + plant.getNutritionValue());
        plant.setHitPoints(plant.getHitPoints() - herbivore.getAttackDamage());

        if (plant.getHitPoints() <= 0) {
            System.out.println(plant.getName() + " было съедено и погибло из-за " + herbivore.getName() + ".");

            plants.remove(plant);
        }
    }

    @Override
    public void reproduce(Simulation simulation) {
        List<Herbivore> herbivores = simulation.getHerbivores();

        List<Herbivore> males = new ArrayList<>();
        List<Herbivore> females = new ArrayList<>();

        for(Herbivore herbivore: herbivores)
            if(herbivore.getGender().equals(Gender.MALE))
                males.add(herbivore);
            else if(canReproduce(herbivore))
                females.add(herbivore);

        int pairsCount = Math.min(males.size(), females.size());
        int newHerbivoresCount = (int) (pairsCount * BIRTH_RATE_COEFFICIENT);

        for (int i = 0; i < newHerbivoresCount; i++) {
            if (i >= pairsCount) break; // Если пар меньше, чем количество новых особей

            Herbivore male = males.get(i % males.size());
            Herbivore female = females.get(i % females.size());

            System.out.printf("%s и %s размножились%n", male.getName(), female.getName());

            Herbivore newHerbivore = EntityMapper.createNewHerbivore(male, female);

            female.setHitPoints(female.getHitPoints() - 3);

            simulation.getHerbivores().add(newHerbivore);
        }
    }

    private boolean canReproduce(Herbivore herbivore) {return herbivore.getHitPoints() > 4;}
}

