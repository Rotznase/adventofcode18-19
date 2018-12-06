package de.streubel.aoc18;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import de.streubel.AdventOfCodeRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Day06 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> input) {

        Set<Coord> places = parseInput(input);

        Boundary boundary = getBoundary(places);

        SetMultimap<Coord, Coord> coordToAreaMapping = getAreaMapping(places, boundary);

        int maxArea = 0;
        for (Coord c: coordToAreaMapping.keySet()) {
            if (coordToAreaMapping.get(c).size() > maxArea) {
                maxArea = coordToAreaMapping.get(c).size();
            }
        }

        System.out.println("Result Part 1: "+maxArea);

    }

    private SetMultimap<Coord, Coord> getAreaMapping(Set<Coord> places, Boundary boundary) {
        SetMultimap<Coord, Coord> coordToAreaMapping = HashMultimap.create();
        for (int x=boundary.minX; x<=boundary.maxX; x++) {
            for (int y=boundary.minY; y<=boundary.maxY; y++) {
                Coord c = new Coord(x, y);
                Coord c2 = c.getNearby(places);
                if (c2 != null) {
                    coordToAreaMapping.put(c2, c);
                }
            }
        }
        return coordToAreaMapping;
    }

    private Boundary getBoundary(Set<Coord> places) {
        Boundary r = new Boundary();
        for (Coord c: places) {
            if (c.x < r.minX) r.minX = c.x;
            if (c.x > r.maxX) r.maxX = c.x;
            if (c.y < r.minY) r.minY = c.y;
            if (c.y > r.maxY) r.maxY = c.y;
        }
        return r;
    }

    private Set<Coord> parseInput(List<String> input) {
        Set<Coord> places = new HashSet<>();
        for (String s: input) {
            String[] split = s.split(", *");
            Coord c = new Coord(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            places.add(c);
        }
        return places;
    }

    private static class Boundary {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
    }

    private static class Coord {
        int x;
        int y;

        Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int distance(Coord c) {
            return Math.abs(this.x - c.x) + Math.abs(this.y - c.y);
        }

        private Coord getNearby(Set<Coord> places) {
            int minDistance = Integer.MAX_VALUE;
            Coord nearby = null;
            for (Coord p: places) {
                int distance = distance(p);
                if (distance <= minDistance) {
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearby = p;
                    } else {
                        nearby = null;
                    }
                }
            }

            return nearby;
        }

        @Override
        public String toString() {
            return "" + x + ", " + y;
        }
    }
}
