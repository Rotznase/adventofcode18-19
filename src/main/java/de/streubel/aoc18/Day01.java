package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day01 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> stringInput) {
        int[] input = new int[stringInput.size()];
        for (int i=0; i<input.length; i++) {
            input[i] = Integer.parseInt(stringInput.get(i));
        }
        int result = sum(input);
        System.out.println("Result Part 1: "+result);


        result = 0;
        int i = 0;
        final Set<Integer> x = new HashSet<>();
        do {
            result += input[i];
            i = (i + 1) % input.length;
        } while (x.add(result));
        System.out.println("Result Part 2: "+result);
    }

    private int sum(final int[] input) {
        int result = 0;
        for (int i : input) {
            result += i;
        }

        return result;
    }
}
