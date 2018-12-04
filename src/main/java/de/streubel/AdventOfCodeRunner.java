package de.streubel;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class AdventOfCodeRunner {

    public static void main(String[] args) throws Exception {
        final int dayNr = Integer.parseInt(args[0]);

        final String className = String.format("de.streubel.aoc18.Day%02d", dayNr);
        final Class<?> clazz = Class.forName(className);
        AdventOfCodeRunner runner = (AdventOfCodeRunner) clazz.newInstance();

        List<String> input = Files.readAllLines(Paths.get("./src/main/java/de/streubel/aoc18/resources/input"+clazz.getSimpleName()+".txt"));

        runner.run(input);
    }

    public abstract void run(List<String> input) throws Exception;
}
