package de.streubel.aoc19;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import de.streubel.AdventOfCodeRunner;

import java.util.*;


public class Day08 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> stringInput) {

        final int width = 25;
        final int height = 6;

        final List<String> layers = Splitter
                .fixedLength(width * height)
                .splitToList(stringInput.get(0));

        final CharMatcher matcher_0 = CharMatcher.is('0');
        final CharMatcher matcher_1 = CharMatcher.is('1');
        final CharMatcher matcher_2 = CharMatcher.is('2');

        final Multimap<Integer, String> map = Multimaps.index(layers, matcher_0::countIn);

        final Optional<Integer> min = map.keySet().stream().min(Integer::compareTo);

        if (min.isPresent()) {
            final String layer = map.get(min.get()).stream().findFirst().get();
            final int nrOf1 = matcher_1.countIn(layer);
            final int nrOf2 = matcher_2.countIn(layer);
            System.out.println("Result Part 1 (1572): "+(nrOf1 * nrOf2));
        }

    }

}
