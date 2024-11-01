package org.example.service;

import org.example.entities.Simulation;

public interface AnimalService {
    void consume(Simulation simulation);

    void reproduce(Simulation simulation);
}
