package org.example.utils;

import lombok.experimental.UtilityClass;
import org.example.entities.Climate;
import org.example.entities.Entity;
import org.example.entities.animals.Herbivore;
import org.example.entities.animals.Omnivore;
import org.example.entities.animals.Predator;
import org.example.entities.plants.Plant;

import java.util.List;

@UtilityClass
public class EntityValidator {
    public List<Herbivore> validateHerbivores(List<Herbivore> herbivores, Climate climate) {
        return validateEntities(herbivores, climate);
    }

    public List<Omnivore> validateOmnivores(List<Omnivore> omnivores, Climate climate) {
        return validateEntities(omnivores, climate);
    }

    public List<Predator> validatePredators(List<Predator> predators, Climate climate) {
        return validateEntities(predators, climate);
    }

    public List<Plant> validatePlants(List<Plant> plants, Climate climate) {
        return validateEntities(plants, climate);
    }

    private <T extends Entity> List<T> validateEntities(List<T> entities, Climate climate) {
        entities.removeIf(entity -> {
            if (isClimateUnsuitable(entity, climate)) {
                String message = String.format("%s - %s погиб(ло) из-за неподходящего климата.",
                        entity.getClass().getSimpleName(), entity.getName());
                System.out.println(message);

                return true;
            }

            return false;
        });

        return entities;
    }

    private <T extends Entity> boolean isClimateUnsuitable(T entity, Climate climate) {
        return !(climate.getHumidity() <= entity.getMaxHumidity() &&
                climate.getHumidity() >= entity.getMinHumidity() &&
                climate.getTemperature() <= entity.getMaxTemperature() &&
                climate.getTemperature() >= entity.getMinTemperature());
    }

}
