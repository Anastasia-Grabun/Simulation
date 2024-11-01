package org.example.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.example.entities.Simulation;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@UtilityClass
public class SerializeUtils {

    @SneakyThrows
    public static void serializeSimulation(Simulation simulation){
        FileOutputStream outputStream = new FileOutputStream(String.format("src/main/resources/%s.txt", simulation.getName()));

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(simulation);

        objectOutputStream.close();

    }

    @SneakyThrows
    public static Simulation deserializeSimulationByName(String name) {
        FileInputStream fileInputStream = new FileInputStream(String.format("src/main/resources/%s.txt", name));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        return (Simulation) objectInputStream.readObject();
    }
}
