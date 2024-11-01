package org.example.entities.plants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.entities.Entity;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Plant extends Entity{
    private int nutritionValue;

    @Override
    public String toString(){

        return getName() + " " +
                getNutritionValue() + " " +
                getHitPoints() + " " +
                getMaxHumidity() + " " +
                getMinHumidity() + " " +
                getMaxTemperature() + " " +
                getMinTemperature() + " ";
    }
}
