package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import java.util.*;


public class Day05 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> input) {

        String s;

        s = react(input.get(0));
        System.out.println("Result Part 1: "+s.length());


        int minLength = Integer.MAX_VALUE;
        for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
            char C = Character.toUpperCase(c);
            String regex = "[" + c + C + "]";
            s = input.get(0);
            s = s.replaceAll(regex, "");
            s = react(s);
            if (s.length() < minLength) {
                minLength = s.length();
            }
        }
        System.out.println("Result Part 2: "+minLength);
    }

    private String react(String s) {
        boolean b = true;
        while (b) {
            b = false;
            for (Character c : getAvailableChars(s)) {
                char oppositePolarity = Character.isLowerCase(c) ? Character.toUpperCase(c) : Character.toLowerCase(c);
                String regex = "" + c + oppositePolarity;

                int lengthBefore = s.length();
                s = s.replaceAll(regex, "");
                int lengthAfter = s.length();
                b = b || lengthAfter != lengthBefore;
            }
        }
        return s;
    }

    private Set<Character> getAvailableChars(String s) {
        Set<Character> availChars = new HashSet<>();
        for (char c: s.toCharArray()) {
            availChars.add(c);
        }
        return availChars;
    }

}
