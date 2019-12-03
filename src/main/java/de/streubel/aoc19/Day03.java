package de.streubel.aoc19;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import de.streubel.AdventOfCodeRunner;

import java.util.*;

public class Day03 extends AdventOfCodeRunner {

    private static final int ADD = 1;
    private static final int MUL = 2;
    private static final int EXT = 99;

    @Override
    public void run(List<String> stringInput) {

        String[] movements1 = stringInput.get(0).split(",");
        Set<Coord> path1 = recordPath(movements1);

        String[] movements2 = stringInput.get(1).split(",");
        Set<Coord> path2 = recordPath(movements2);

        final Sets.SetView<Coord> crossings = Sets.intersection(path1, path2);

        Map<Integer, Coord> distances = Maps.uniqueIndex(crossings.immutableCopy(),
                coord -> coord.distanceTo(new Coord(0, 0)));

        final Integer nearestDistance = Ordering.natural().min(distances.keySet());
        System.out.println("Result Part 1 (273): "+nearestDistance);
    }

    private Set<Coord> recordPath(String[] movements) {
        Cursor c = new Cursor(0, 0);
        Set<Coord> path = new LinkedHashSet<>();
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
    }
}
