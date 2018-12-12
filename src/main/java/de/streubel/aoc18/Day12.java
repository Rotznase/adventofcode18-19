package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day12 extends AdventOfCodeRunner {

    public Day12() {
    }

    @Override
    public void run(List<String> input) {

        PotGeneration potGeneration = PotGeneration.parse(input);

        System.out.println(String.format("% 2d: %s", 0, potGeneration));

        long N = 21;
        for (long i=1; i<N; i++) {
            potGeneration.nextGeneration();
            System.out.println(String.format("% 2d: %s", i, potGeneration));
        }

        System.out.println("Result Part 1: "+potGeneration.value());

        // After iteration 100 the pots corresponds to the pattern .##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##...##.....
        // which is constantly shifting by 1 to the right with each subsequent iteration.
        // With that in mind the sum of the numbers of all pots can be calculated by hand:
        N = 50000000000L;
        long sum = 40*(2+3)+10*39*40/2 + 40*2*(N-100);
        System.out.println("Result Part 2: "+sum);
    }

    public static class PotGeneration {
        String pots;
        int zeroIndex;
        Map<String, Character> patterns;

        public PotGeneration(String pots) {
            this.pots = pots;
            addBorder();
            this.patterns = new LinkedHashMap<>();
        }

        public void addPattern(String pattern, char c) {
            patterns.put(pattern, c);
        }

        public void nextGeneration() {
            char[] nextGeneration = new char[pots.length()];
            Arrays.fill(nextGeneration, '.');
            int index = -1;
            for (String pattern: patterns.keySet()) {
                do {
                    index = pots.indexOf(pattern, index+1);
                    if (index >= 0) {
                        nextGeneration[index+2] = patterns.get(pattern);
                    }
                } while (index >= 0);
            }
            pots = String.valueOf(nextGeneration);
            addBorder();
        }

        public long nrOfPlants() {
            return pots.chars().filter(c -> c == '#').count();
        }

        public int value() {
            int value = 0;
            for (int i=0; i<pots.length(); i++) {
                if (pots.charAt(i) == '#')
                    value += i - zeroIndex;
            }
            return value;
        }

        private void addBorder() {
            String prefix = "";
            if (pots.charAt(0) == '#')
                prefix = ".....";
            else if (pots.charAt(1) == '#')
                prefix = "....";
            else if (pots.charAt(2) == '#')
                prefix = "...";
            else if (pots.charAt(3) == '#')
                prefix = "..";
            else if (pots.charAt(4) == '#')
                prefix = ".";

            String postfix = "";
            if (pots.charAt(pots.length()-1) == '#')
                postfix = ".....";
            else if (pots.charAt(pots.length()-2) == '#')
                postfix = "....";
            if (pots.charAt(pots.length()-3) == '#')
                postfix = "...";
            else if (pots.charAt(pots.length()-4) == '#')
                postfix = "..";
            else if (pots.charAt(pots.length()-5) == '#')
                postfix = ".";

            pots = prefix + pots + postfix;

            zeroIndex += prefix.length();
        }

        public String toString() {
            return pots;
        }

        private static PotGeneration parse(List<String> input) {
            PotGeneration potGeneration;

            Pattern pattern = Pattern.compile("initial state: (.*)");
            Matcher matcher = pattern.matcher(input.get(0));
            if (matcher.matches()) {
                String initialState = matcher.group(1);
                potGeneration = new PotGeneration(initialState);
            } else {
                throw new RuntimeException();
            }

            pattern = Pattern.compile("([.#]+) => ([.#])");
            for (int i=2; i<input.size(); i++) {
                matcher = pattern.matcher(input.get(i));
                if (matcher.matches()) {
                    potGeneration.addPattern(matcher.group(1), matcher.group(2).charAt(0));
                } else {
                    throw new RuntimeException();
                }
            }

            return potGeneration;
        }

    }

}
