package org.example.entities.animals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class Omnivore extends Animal{

    @Override
    public String toString(){

        return getName() + " " +
                getGender() + " " +
                getAttackDamage() + " " +
                getHitPoints() + " " +
                getMaxHumidity() + " " +
                getMinHumidity() + " " +
                getMaxTemperature() + " " +
                getMinTemperature() + " ";
    }
}
