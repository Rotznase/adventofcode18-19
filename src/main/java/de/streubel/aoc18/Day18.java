package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import java.util.List;


public class Day18 extends AdventOfCodeRunner {

    public Day18() {
    }

    @Override
    public void run(List<String> input) {

        char[][] area = new char[input.size()][];
        for (int i=0; i<input.size(); i++) {
            area[i] = input.get(i).toCharArray();
        }

        int[] adjacent = new int[128];

        for (int m=0; m<10; m++) {
            char[][] next = copy(area);
            for (int y = 0; y < next.length; y++) {
                for (int x = 0; x < next[y].length; x++) {
                    getAdjacent(x, y, area, adjacent);
                    switch (area[y][x]) {
                        case '.':
                            if (adjacent['|'] >= 3)
                                next[y][x] = '|';
                            break;
                        case '|':
                            if (adjacent['#'] >= 3)
                                next[y][x] = '#';
                            break;
                        case '#':
                            if (adjacent['#'] < 1 || adjacent['|'] < 1)
                                next[y][x] = '.';
                            break;
                        default:
                            throw new RuntimeException();
                    }
                }
            }

            area = next;
        }

        int count1 = 0;
        int count2 = 0;
        for (int y=0; y<area.length; y++) {
            for (int x=0; x<area.length; x++) {
                count1 += area[y][x] == '|' ? 1 : 0;
                count2 += area[y][x] == '#' ? 1 : 0;
            }
        }
        System.out.println("Result Part 1: "+count1*count2);
    }

    private void getAdjacent(int x, int y, char[][] area, int[] adjacent) {
        int Y = area.length;
        int X = area[0].length;

        adjacent['.'] = 0;
        adjacent['|'] = 0;
        adjacent['#'] = 0;

        if (x-1>=0 && y-1 >= 0) adjacent[area[y-1][x-1]]++;
        if (          y-1 >= 0) adjacent[area[y-1][  x]]++;
        if (x+1< X && y-1 >= 0) adjacent[area[y-1][x+1]]++;

        if (x-1>=0            ) adjacent[area[  y][x-1]]++;
//                                adjacent[area[  y][  x]]++;
        if (x+1<X             ) adjacent[area[  y][x+1]]++;

        if (x-1>=0 && y+1 < Y) adjacent[area[y+1][x-1]]++;
        if (          y+1 < Y) adjacent[area[y+1][  x]]++;
        if (x+1<X  && y+1 < Y) adjacent[area[y+1][x+1]]++;
    }

    private char[][] copy(char[][] toCopy) {
        char[][] result = new char[toCopy.length][];
        for (int i=0; i<result.length; i++)
            result[i] = toCopy[i].clone();
        return result;
    }
}
