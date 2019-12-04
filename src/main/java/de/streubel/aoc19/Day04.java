package de.streubel.aoc19;

import de.streubel.AdventOfCodeRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day04 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> stringInput) {

        int[] input = Arrays
                .stream(stringInput.get(0).split("-"))
                .map(String::trim)
                .flatMapToInt(s -> IntStream.of(Integer.parseInt(s)))
                .toArray();

        int candidateCounterPart1 = 0;
        int candidateCounterPart2 = 0;
        for (int candidate = input[0]; candidate <= input[1]; candidate++) {
            if (checkCriteriaPart1(candidate)) {
                candidateCounterPart1++;

                if (checkCriteriaPart2(candidate)) {
                    candidateCounterPart2++;
                }
            }
        }

        System.out.println("Result Part 1 (1246): " + candidateCounterPart1);
        System.out.println("Result Part 2 (814): " + candidateCounterPart2);
    }

    private boolean checkCriteriaPart1(final int password) {
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

    private boolean checkCriteriaPart2(final int password) {
        final char[] chars = String.valueOf(password).toCharArray();

        int doubleCounter = 1;
        char c = chars[0];
        for (int i = 1; i < chars.length; i++) {

            if (c == chars[i]) {
                doubleCounter++;
            } else {
                if (doubleCounter == 2) {
                    return true;
                }
                doubleCounter = 1;
                c = chars[i];
            }
        }

        return doubleCounter == 2;
    }
}
