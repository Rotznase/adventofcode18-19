package de.streubel.aoc19;

import de.streubel.AdventOfCodeRunner;

import java.util.List;

public class Day01 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> stringInput) {
        int[] input = new int[stringInput.size()];
        for (int i=0; i<input.length; i++) {
            input[i] = Integer.parseInt(stringInput.get(i));
        }

        int fuelTotal = 0;
        for (int moduleMass: input) {
            fuelTotal += moduleMass / 3 - 2;
        }
        System.out.println("Result Part 1: "+fuelTotal);

        fuelTotal = 0;
        for (int moduleMass: input) {
            int mass = moduleMass;
            int fuel;

            do {
                fuel = mass / 3 - 2;
                if (fuel > 0) {
                    fuelTotal += fuel;
                }
                mass = fuel;
            } while (fuel > 0);

        }
        System.out.println("Result Part 2: "+fuelTotal);


    }

}
