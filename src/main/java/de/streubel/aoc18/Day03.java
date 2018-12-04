package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> input) {

        int[][] fabric = new int[1000][1000];
        for (String s: input) {
            Claim claim = Claim.parse(s);
            draw(fabric, claim);
        }

        int overlapCounter = count(fabric, -1);
        System.out.println("Result Part 1: overlapCounter="+overlapCounter);

        int id=0;
        for (String s: input) {
            Claim claim = Claim.parse(s);
            if (!overlaps(fabric, claim)) {
                id = claim.id;
                break;
            }
        }

        System.out.println("Result Part 2: Id="+id);
    }

    private void draw(int[][] fabric, Claim claim) {
        for (int x=claim.x; x<claim.x+claim.w; x++) {
            for (int y=claim.y; y<claim.y+claim.h; y++) {
                if (fabric[y][x] == 0) {
                    fabric[y][x] = claim.id;
                } else {
                    fabric[y][x] = -1;
                }
            }
        }
    }

    private int count(int[][] fabric, int c) {
        int counter = 0;
        //noinspection ForLoopReplaceableByForEach
        for (int y=0; y<fabric.length; y++) {
            for (int x=0; x<fabric[y].length; x++) {
                if (fabric[y][x] == c) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private boolean overlaps(int[][] fabric, Claim claim) {
        for (int x=claim.x; x<claim.x+claim.w; x++) {
            for (int y=claim.y; y<claim.y+claim.h; y++) {
                if (fabric[y][x] != claim.id) {
                    return true;
                }
            }
        }

        return false;
    }

    private static class Claim {
        int id;
        int x, y, w, h;

        Claim(int id, int x, int y, int w, int h) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        public String toString() {
            return "#"+id+" @ "+x+","+y+": "+w+"x"+h;
        }

        static Claim parse(String s) {
            Pattern pattern = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");
            Matcher matcher = pattern.matcher(s);
            if (!matcher.matches())
                throw new RuntimeException();
            int id = Integer.valueOf(matcher.group(1));
            int x = Integer.valueOf(matcher.group(2));
            int y = Integer.valueOf(matcher.group(3));
            int w = Integer.valueOf(matcher.group(4));
            int h = Integer.valueOf(matcher.group(5));
            return new Claim(id, x, y, w, h);
        }

    }
}
