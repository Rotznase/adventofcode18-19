package de.streubel.aoc19;

import de.streubel.AdventOfCodeRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day04 extends AdventOfCodeRunner {

    private static final int ADD = 1;
    private static final int MUL = 2;
    private static final int EXT = 99;

    @Override
    public void run(List<String> stringInput) {

        int[] input = Arrays
                .stream(stringInput.get(0).split("-"))
                .map(String::trim)
                .flatMapToInt(s -> IntStream.of(Integer.parseInt(s)))
                .toArray();

        int candidateCounter = 0;
        for (int candidate = input[0]; candidate <= input[1]; candidate++) {
            if (checkCriteria(candidate)) {
                candidateCounter++;
            }
        }

        System.out.println("Result Part 1 (1246): " + candidateCounter);
    }

    private boolean checkCriteria(final int password) {
        final char[] chars = String.valueOf(password).toCharArray();

        int doubleCounter = 0;
        for (int i = 0; i < chars.length-1; i++) {

            if (chars[i] == chars[i+1]) {
                doubleCounter++;
            }
            if (chars[i] > chars[i+1]) {
                return false;
            }
        }

        return doubleCounter >= 1;
    }
}
