package org.example.service.impl;

import org.example.entities.Simulation;
import org.example.entities.animals.Herbivore;
import org.example.entities.animals.Predator;
import org.example.entities.animals.enums.Gender;
import org.example.service.AnimalService;
import org.example.utils.EntityMapper;
import java.util.ArrayList;
import java.util.List;
import static org.example.utils.Constants.BIRTH_RATE_COEFFICIENT;

public class PredatorServiceImpl implements AnimalService {

    @Override
    public void consume(Simulation simulation) {
        List<Predator> predators = simulation.getPredators();
        List<Herbivore> herbivores = simulation.getHerbivores();

        if (herbivores.isEmpty() || predators.isEmpty())
            return;

        Predator predator = predators.get((int) (Math.random() * predators.size()));
        Herbivore herbivore = herbivores.get((int) (Math.random() * herbivores.size()));

        System.out.println(predator.getName() + " съел " + herbivore.getName() + ".");

        predator.setHitPoints(predator.getHitPoints() + herbivore.getNutritionValue());
        herbivore.setHitPoints(herbivore.getHitPoints() - predator.getAttackDamage());

        if (herbivore.getHitPoints() <= 0) {
            System.out.println(herbivore.getName() + " было съедено и погибло из-за " + predator.getName() + ".");
            simulation.getHerbivores().remove(herbivore);
        }
    }

    @Override
    public void reproduce(Simulation simulation) {
        List<Predator> predators = simulation.getPredators();

        List<Predator> males = new ArrayList<>();
        List<Predator> females = new ArrayList<>();

        for(Predator predator: predators)
            if(predator.getGender().equals(Gender.MALE))
                males.add(predator);
            else if(canReproduce(predator))
                females.add(predator);

        int pairsCount = Math.min(males.size(), females.size());
        int newPredatorsCount = (int) (pairsCount * BIRTH_RATE_COEFFICIENT);

        for (int i = 0; i < newPredatorsCount; i++) {
            if (i >= pairsCount) break; // Если пар меньше, чем количество новых особей

            Predator male = males.get(i % males.size());
            Predator female = females.get(i % females.size());

            System.out.printf("%s и %s размножились%n", male.getName(), female.getName());

            Predator newPredator = EntityMapper.createNewPredator(male, female);

            simulation.getPredators().add(newPredator);

            female.setHitPoints(female.getHitPoints() - 3);
        }
    }

    private boolean canReproduce(Predator predator) {
        return predator.getHitPoints() > 4;
    }
}

