package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.entities.animals.Herbivore;
import org.example.entities.animals.Omnivore;
import org.example.entities.animals.Predator;
import org.example.entities.plants.Plant;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Simulation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<Herbivore> herbivores;
    private List<Omnivore> omnivores;
    private List<Predator> predators;
    private List<Plant> plants;
    private Climate climate;
    private String name;

    public Simulation() {
        this.climate = new Climate();
        this.herbivores = new ArrayList<>();
        this.omnivores = new ArrayList<>();
        this.predators = new ArrayList<>();
        this.plants = new ArrayList<>();
    }
}
