package de.streubel.aoc19;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import de.streubel.AdventOfCodeRunner;

import java.util.*;
import java.util.stream.IntStream;

public class Day03 extends AdventOfCodeRunner {

    private static final int ADD = 1;
    private static final int MUL = 2;
    private static final int EXT = 99;

    @Override
    public void run(List<String> stringInput) {

        String[] movements1 = stringInput.get(0).split(",");
        List<Coord> path1 = recordPath(movements1);

        String[] movements2 = stringInput.get(1).split(",");
        List<Coord> path2 = recordPath(movements2);

        final Sets.SetView<Coord> crossings = Sets.intersection(new HashSet<Coord>(path1), new HashSet<Coord>(path2));

        IntStream distances;
        OptionalInt nearestDistance;

        // Part 1
        distances = crossings
                .stream()
                .flatMapToInt(coord -> IntStream.of(coord.distanceTo(new Coord(0, 0))));
        nearestDistance = distances.min();

        System.out.println("Result Part 1 (273): " + (nearestDistance.isPresent() ? nearestDistance.getAsInt() : null));



        // Part 1
        distances = crossings
                .stream()
                .flatMapToInt(coord -> IntStream.of(distance(path1, coord) + distance(path2, coord)));
        nearestDistance = distances.min();

        System.out.println("Result Part 2 (15622): " + (nearestDistance.isPresent() ? nearestDistance.getAsInt() : null));

    }

    private int distance(List<Coord> path, Coord crossing) {
        int length = 0;
        for (Coord c : path) {
            length++;
            if (c.equals(crossing)) {
                break;
            }
        }

        return length;
    }

    private List<Coord> recordPath(String[] movements) {
        Cursor c = new Cursor(0, 0);
        List<Coord> path = new ArrayList<>();
        for (String move : movements) {
            path.addAll(c.move(move));
        }
        return path;
    }

    private static class Cursor {
        int x;
        int y;

        public Cursor(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Set<Coord> move(String moveInst) {
            final Set<Coord> path = new LinkedHashSet<>();

            int distance = Integer.parseInt(moveInst.substring(1));
            for (int i = 0; i < distance; i++) {
                switch (moveInst.charAt(0)) {
                    case 'R':
                        x++;
                        break;
                    case 'L':
                        x--;
                        break;
                    case 'U':
                        y++;
                        break;
                    case 'D':
                        y--;
                        break;
                }
                path.add(new Coord(x, y));
            }

            return path;
        }
    }

    private static class Coord {
        int x, y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int distanceTo(Coord coord) {
            return Math.abs(x - coord.x) + Math.abs(y - coord.y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coord coord = (Coord) o;
            return x == coord.x &&
                   y == coord.y;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(x, y);
        }

        @Override
        public String toString() {
            return "[" + x + ", " + y + "]";
        }
    }
}
