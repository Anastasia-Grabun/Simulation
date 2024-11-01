package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Entity implements Serializable {
    private String name;
    private int hitPoints;
    private int minHumidity;
    private int maxHumidity;
    private double minTemperature;
    private double maxTemperature;
}

