package de.streubel;

public abstract class AdventOfCodeRunner {

    public static void main(String[] args) throws Exception {
        final int dayNr = Integer.parseInt(args[0]);

        final String className = String.format("de.streubel.aoc18.Day%02d", dayNr);
        final Class<?> clazz = Class.forName(className);
        AdventOfCodeRunner runner = (AdventOfCodeRunner) clazz.newInstance();
        runner.run();
    }

    public abstract void run();
}
