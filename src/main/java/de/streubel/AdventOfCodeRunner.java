package de.streubel;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class AdventOfCodeRunner {

    public static void main(String[] args) throws Exception {
        final int yearNr = Integer.parseInt(args[0]);
        final int dayNr = Integer.parseInt(args[1]);

        final String className = String.format("de.streubel.aoc%02d.Day%02d", yearNr, dayNr);
        final Class<?> clazz = Class.forName(className);
        AdventOfCodeRunner runner = (AdventOfCodeRunner) clazz.newInstance();

        final String resourceName = String.format("./src/main/java/de/streubel/aoc%02d/resources/inputDay%02d.txt", yearNr, dayNr);
        List<String> input = Files.readAllLines(Paths.get(resourceName));

        runner.run(input);
    }

    public abstract void run(List<String> input) throws Exception;
}
