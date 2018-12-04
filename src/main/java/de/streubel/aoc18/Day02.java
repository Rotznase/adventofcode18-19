package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day02 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> input) {

        int nrOfTwoTimes = 0;
        int nrOfThreeTimes = 0;
        for (String s: input) {
            Map<Character, Integer> charCounter = countChars(s);
            if (charCounter.values().contains(2)) {
                nrOfTwoTimes++;
            }
            if (charCounter.values().contains(3)) {
                nrOfThreeTimes++;
            }
        }

        int checksum = nrOfTwoTimes * nrOfThreeTimes;

        System.out.println("Result Part 1: checksum="+checksum);


        final String[] strings = input.toArray(new String[0]);
        final List<String> commonLetters = new ArrayList<>();
        for (int i=0; i<strings.length; i++) {
            for (int j=i+1; j<strings.length; j++) {
                int pos = findDifferentCharPosition(strings[i], strings[j]);
                if (pos >= 0) {
                    commonLetters.add(strings[i].substring(0, pos).concat(strings[i].substring(pos+1)));
                }
            }
        }

        System.out.println("Result Part 2: commonLetters="+commonLetters);

    }

    private Map<Character, Integer> countChars(String s) {
        final Map<Character, Integer> charCounter = new HashMap<>();
        for (char c : s.toCharArray()) {
            if (charCounter.containsKey(c)) {
                charCounter.put(c, charCounter.get(c) + 1);
            } else {
                charCounter.put(c, 1);
            }
        }
        return charCounter;
    }

    private int findDifferentCharPosition(final String s1, final String s2) {
        int pos = -1;
        int differences = 0;
        for (int i=0; i<Math.min(s1.length(), s2.length()); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                differences++;
                pos = i;
            }
        }
        return differences == 1 ? pos : -1;
    }
}
