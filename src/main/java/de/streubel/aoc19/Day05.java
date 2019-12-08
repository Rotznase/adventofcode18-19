package de.streubel.aoc19;

import de.streubel.AdventOfCodeRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day05 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> stringInput) {
        int[] prog = Arrays
                .stream(stringInput.get(0).split(","))
                .map(String::trim)
                .flatMapToInt(s -> IntStream.of(Integer.parseInt(s)))
                .toArray();

        IntCodeComputer computer = new IntCodeComputer();

        computer.setInput(1);
        computer.run(prog.clone());
        Integer result = computer.getLastOutput();
        System.out.println("Result Part 1 (4601506): "+result);

        computer.setInput(5);
        computer.reset();
        computer.run(prog.clone());
        result = computer.getLastOutput();
        System.out.println("Result Part 2 (5525561): "+result);

    }

}
