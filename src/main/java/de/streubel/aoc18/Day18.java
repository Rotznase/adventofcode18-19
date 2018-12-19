package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;


public class Day18 extends AdventOfCodeRunner {

    public Day18() {
    }

    @Override
    public void run(List<String> input) {

        char[][] area = new char[input.size()][];
        for (int i=0; i<input.size(); i++) {
            area[i] = input.get(i).toCharArray();
        }

        int[] proximity = new int[128];

        Map<String, int[]> map = new LinkedHashMap<>();
        final int M = 1000000000;

        for (int m=0; m<M; m++) {

            String hash = hash(area);
            int[] value = {m, count(area)};
            if (!map.containsKey(hash)) {
                map.put(hash, value);
            } else {
                break;
            }

            char[][] next = copy(area);
            for (int y = 0; y < next.length; y++) {
                for (int x = 0; x < next[y].length; x++) {
                    getAdjacent(x, y, area, proximity);
                    switch (area[y][x]) {
                        case '.':
                            if (proximity['|'] >= 3)
                                next[y][x] = '|';
                            break;
                        case '|':
                            if (proximity['#'] >= 3)
                                next[y][x] = '#';
                            break;
                        case '#':
                            if (proximity['#'] < 1 || proximity['|'] < 1)
                                next[y][x] = '.';
                            break;
                        default:
                            throw new RuntimeException();
                    }
                }
            }

            area = next;
        }

        String firstRepeatingHash = hash(area);
        int offset = map.get(firstRepeatingHash)[0];

        int indexPart1 = 10;
        int indexPart2 = offset + (M-offset) % (map.size()-offset);

        System.out.println("Result Part 1: "+map.values().stream().filter(ints -> ints[0] == indexPart1).map(ints -> ints[1]).findFirst().orElse(null));
        System.out.println("Result Part 2: "+map.values().stream().filter(ints -> ints[0] == indexPart2).map(ints -> ints[1]).findFirst().orElse(null));

    }

    private int count(char[][] area) {
        int[] count = new int[2];
        for (int y=0; y<area.length; y++) {
            for (int x=0; x<area.length; x++) {
                count[0] += area[y][x] == '|' ? 1 : 0;
                count[1] += area[y][x] == '#' ? 1 : 0;
            }
        }
        return count[0]*count[1];
    }

    private void getAdjacent(int x, int y, char[][] area, int[] proximity) {
        int Y = area.length;
        int X = area[0].length;

        proximity['.'] = 0;
        proximity['|'] = 0;
        proximity['#'] = 0;

        if (x-1>=0 && y-1 >= 0) proximity[area[y-1][x-1]]++;
        if (          y-1 >= 0) proximity[area[y-1][  x]]++;
        if (x+1< X && y-1 >= 0) proximity[area[y-1][x+1]]++;

        if (x-1>=0            ) proximity[area[  y][x-1]]++;
//                                proximity[area[  y][  x]]++;
        if (x+1< X            ) proximity[area[  y][x+1]]++;

        if (x-1>=0 && y+1 <  Y) proximity[area[y+1][x-1]]++;
        if (          y+1 <  Y) proximity[area[y+1][  x]]++;
        if (x+1< X && y+1 <  Y) proximity[area[y+1][x+1]]++;
    }

    private char[][] copy(char[][] toCopy) {
        char[][] result = new char[toCopy.length][];
        for (int i=0; i<result.length; i++)
            result[i] = toCopy[i].clone();
        return result;
    }

    private String hash(char[][] area) {
        int hash = 7;
        for (int y=0; y<area.length; y++) {
            for (int x=0; x<area.length; x++) {
                hash = hash * 31 + area[y][x];
            }
        }
        return ""+hash;
    }
}
