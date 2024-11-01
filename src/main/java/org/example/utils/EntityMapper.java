package org.example.utils;

import lombok.experimental.UtilityClass;
import org.example.entities.animals.Herbivore;
import org.example.entities.animals.Omnivore;
import org.example.entities.animals.Predator;
import org.example.entities.animals.enums.Gender;
import org.example.entities.plants.Plant;

@UtilityClass
public class EntityMapper {

    public static Plant createNewPlant(Plant parentPlant) {
        return Plant.builder()
                .name(parentPlant.getName())
                .hitPoints(parentPlant.getHitPoints())
                .minHumidity(parentPlant.getMinHumidity())
                .maxHumidity(parentPlant.getMaxHumidity())
                .minTemperature(parentPlant.getMinTemperature())
                .maxTemperature(parentPlant.getMaxTemperature())
                .build();
    }

    public static Herbivore createNewHerbivore(Herbivore male, Herbivore female) {
        return Herbivore.builder()
                .name(male.getName() + "-" + female.getName())
                .hitPoints((male.getHitPoints() + female.getHitPoints()) / 2)
                .attackDamage((male.getAttackDamage() + female.getAttackDamage()) / 2)
                .gender(randomGender())
                .nutritionValue(male.getNutritionValue())
                .minHumidity(male.getMinHumidity())
                .maxHumidity(male.getMaxHumidity())
                .minTemperature(male.getMinTemperature())
                .maxTemperature(male.getMaxTemperature())
                .build();
    }

    public static Omnivore createNewOmnivore(Omnivore male, Omnivore female) {
        return  Omnivore.builder()
                .name(male.getName())
                .hitPoints(male.getHitPoints() + female.getHitPoints())
                .attackDamage((male.getAttackDamage() + female.getAttackDamage()) / 2)
                .gender(randomGender())
                .minHumidity(male.getMinHumidity())
                .maxHumidity(male.getMaxHumidity())
                .minTemperature(male.getMinTemperature())
                .maxTemperature(male.getMaxTemperature())
                .build();
    }

    public static Predator createNewPredator(Predator male, Predator female) {
        return Predator.builder()
                .name(male.getName())
                .hitPoints(male.getHitPoints() + female.getHitPoints())
                .attackDamage((male.getAttackDamage() + female.getAttackDamage()) / 2)
                .gender(randomGender())
                .minHumidity(male.getMinHumidity())
                .maxHumidity(male.getMaxHumidity())
                .minTemperature(male.getMinTemperature())
                .maxTemperature(male.getMaxTemperature())
                .build();
    }

    private Gender randomGender() {
        return Math.random() < 0.5 ? Gender.MALE : Gender.FEMALE;
    }

}
