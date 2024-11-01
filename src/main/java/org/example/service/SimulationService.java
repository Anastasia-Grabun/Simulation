package org.example.service;

import org.example.entities.Simulation;

import java.util.Map;

public interface SimulationService {
    void simulateStep(Simulation simulation);

    void saveSimulation(Simulation simulation);

    Map<String, String> predictPopulation(Simulation simulation);
}
