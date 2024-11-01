package org.example.service.impl;

import lombok.SneakyThrows;
import org.example.entities.Simulation;
import org.example.entities.animals.Herbivore;
import org.example.entities.animals.Omnivore;
import org.example.entities.animals.enums.Gender;
import org.example.entities.plants.Plant;
import org.example.service.AnimalService;
import org.example.utils.EntityMapper;
import java.util.ArrayList;
import java.util.List;
import static org.example.utils.Constants.BIRTH_RATE_COEFFICIENT;

public class OmnivoreServiceImpl implements AnimalService {

    @Override
    @SneakyThrows
    public void consume(Simulation simulation) {
        List<Omnivore> omnivores = simulation.getOmnivores();
        List<Herbivore> herbivores = simulation.getHerbivores();
        List<Plant> plants = simulation.getPlants();

        if (omnivores.isEmpty()) {
            return;
        }

        Omnivore omnivore = getRandomOmnivore(omnivores);

        if (!herbivores.isEmpty()) {
            consumeHerbivore(omnivore, herbivores);
        } else {
            consumePlant(omnivore, plants);
        }
    }

    @Override
    public void reproduce(Simulation simulation) {
        List<Omnivore> omnivores = simulation.getOmnivores();

        List<Omnivore> males = new ArrayList<>();
        List<Omnivore> females = new ArrayList<>();

        for(Omnivore omnivore: omnivores)
            if(omnivore.getGender().equals(Gender.MALE))
                males.add(omnivore);
            else if(canReproduce(omnivore))
                females.add(omnivore);


        int pairsCount = Math.min(males.size(), females.size());
        int newOmnivoresCount = (int) (pairsCount * BIRTH_RATE_COEFFICIENT);

        for (int i = 0; i < newOmnivoresCount; i++) {
            if (i >= pairsCount) break; // Если пар меньше, чем количество новых особей

            Omnivore male = males.get(i % males.size());
            Omnivore female = females.get(i % females.size());

            System.out.printf("%s и %s размножились%n", male.getName(), female.getName());

            Omnivore newOmnivore = EntityMapper.createNewOmnivore(male,female);

            simulation.getOmnivores().add(newOmnivore);

            female.setHitPoints(female.getHitPoints() - 3);

        }
    }

    private Omnivore getRandomOmnivore(List<Omnivore> omnivores) {
        return omnivores.get((int) (Math.random() * omnivores.size()));
    }

    private void consumeHerbivore(Omnivore omnivore, List<Herbivore> herbivores) {
        Herbivore targetHerbivore = getRandomHerbivore(herbivores);

        omnivore.setHitPoints(omnivore.getHitPoints() + targetHerbivore.getNutritionValue());
        System.out.println(omnivore.getName() + " съел " + targetHerbivore.getName() + ".");

        targetHerbivore.setHitPoints(targetHerbivore.getHitPoints() - omnivore.getAttackDamage());

        if (targetHerbivore.getHitPoints() <= 0) {
            System.out.println(targetHerbivore.getName() + " было съедено и погибло из-за: " + omnivore.getName() + ".");
            herbivores.remove(targetHerbivore);
        }
    }

    private Herbivore getRandomHerbivore(List<Herbivore> herbivores) {
        return herbivores.get((int) (Math.random() * herbivores.size()));
    }

    private void consumePlant(Omnivore omnivore, List<Plant> plants) {
        if (!plants.isEmpty()) {
            Plant plant = getRandomPlant(plants);

            omnivore.setHitPoints(omnivore.getHitPoints() + plant.getNutritionValue());
            System.out.println(omnivore.getName() + " съел " + plant.getName() + ".");

            plant.setHitPoints(plant.getHitPoints() - omnivore.getAttackDamage());

            if (plant.getHitPoints() <= 0) {
                System.out.println(plant.getName() + " было съедено и погибло из-за: " + omnivore.getName() + ".");
                plants.remove(plant);
            }
        }
    }

    private Plant getRandomPlant(List<Plant> plants) {
        return plants.get((int) (Math.random() * plants.size()));
    }

    private boolean canReproduce(Omnivore omnivore) {return omnivore.getHitPoints() > 4;}
}
