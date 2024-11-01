package org.example.entities.animals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Herbivore extends Animal {
    private int nutritionValue;

    @Override
    public String toString(){

        return getName() + " " +
                getGender() + " " +
                getHitPoints() + " " +
                getNutritionValue() + " " +
                getMaxHumidity() + " " +
                getMinHumidity() + " " +
                getMaxTemperature() + " " +
                getMinTemperature() + " ";
    }

}
