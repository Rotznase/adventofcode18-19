package de.streubel.aoc18;

import com.google.common.base.Stopwatch;
import de.streubel.AdventOfCodeRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day18 extends AdventOfCodeRunner {

    public Day18() {
    }

    @Override
    public void run(List<String> input) {

        char[][] area = new char[input.size()][];
        for (int i=0; i<input.size(); i++) {
            area[i] = input.get(i).toCharArray();
        }


        for (int m=0; m<10; m++) {
            char[][] next = copy(area);
            for (int y = 0; y < next.length; y++) {
                for (int x = 0; x < next[y].length; x++) {
                    char[] xxxx = getAdjacent(x, y, area);
                    switch (area[y][x]) {
                        case '.':
                            if (count(xxxx, '|') >= 3)
                                next[y][x] = '|';
                            break;
                        case '|':
                            if (count(xxxx, '#') >= 3)
                                next[y][x] = '#';
                            break;
                        case '#':
                            if (count(xxxx, '#') < 1 || count(xxxx, '|') < 1)
                                next[y][x] = '.';
                            break;
                        default:
                            throw new RuntimeException();
                    }
                }
            }

//            for (int y = 0; y < next.length; y++)
//                System.out.println(String.valueOf(next[y]));

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

    private char[] getAdjacent(int x, int y, char[][] area) {
        int Y = area.length;
        int X = area[0].length;

        String s = "";
        if (x-1>=0 && y-1 >= 0) s += area[y-1][x-1];
        if (          y-1 >= 0) s += area[y-1][  x];
        if (x+1< X && y-1 >= 0) s += area[y-1][x+1];

        if (x-1>=0            ) s += area[  y][x-1];
//                                s += area[  y][  x];
        if (x+1<X             ) s += area[  y][x+1];

        if (x-1>=0 && y+1 < Y) s += area[y+1][x-1];
        if (          y+1 < Y) s += area[y+1][  x];
        if (x+1<X  && y+1 < Y) s += area[y+1][x+1];

        return s.toCharArray();
    }

    private int count(char[] adjacent, char c) {
        int count = 0;
        for (char cc: adjacent) {
            if (cc == c)
                count++;
        }
        return count;
    }

    private char[][] copy(char[][] toCopy) {
        char[][] result = new char[toCopy.length][];
        for (int i=0; i<result.length; i++)
            result[i] = toCopy[i].clone();
        return result;
    }
}
