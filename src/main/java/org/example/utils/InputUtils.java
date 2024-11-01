package org.example.utils;

import lombok.experimental.UtilityClass;
import java.util.Scanner;

@UtilityClass
public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Пожалуйста, введите число");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Пожалуйста, введите число");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();

        return value;
    }
}
