package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day05 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> input) {

        Set<Character> availChars = new HashSet<>();
        for (char c: input.get(0).toCharArray()) {
            availChars.add(c);
        }

        String s = input.get(0);
        boolean b = true;
        while (b) {
            b = false;
            for (Character c : availChars) {
                char oppositePolarity = Character.isLowerCase(c) ? Character.toUpperCase(c) : Character.toLowerCase(c);
                String regex = "" + c + oppositePolarity;

                int lengthBefore = s.length();
                s = s.replaceAll(regex, "");
                int lengthAfter = s.length();
                b = b || lengthAfter != lengthBefore;
            }
        }

        System.out.println("Result Part 1: "+s.length());
    }

}
