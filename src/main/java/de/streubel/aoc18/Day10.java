package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Day10 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> input) {

        List<Point> points = Point.parse(input);

        long iMax = 0;
        double densityMax = 0;
        for (int i=0; i<100000; i++) {
            double density = density(points);
            if (density >= densityMax) {
                densityMax = density;
                iMax = i;
            }
            points.forEach(Point::move);
        }

        points = Point.parse(input);
        for (int i=0; i<iMax; i++) {
            points.forEach(Point::move);
        }

        System.out.println(iMax);
        System.out.println(densityMax);

        display(points);
    }

    private static Rectangle boundingBox(List<Point> points) {
        List<Integer> allX = points.stream().map(point -> point.x).collect(Collectors.toList());
        List<Integer> allY = points.stream().map(point -> point.y).collect(Collectors.toList());
        int minX = allX.stream().min(Comparator.comparing(Integer::intValue)).orElse(0);
        int maxX = allX.stream().max(Comparator.comparing(Integer::intValue)).orElse(0);
        int minY = allY.stream().min(Comparator.comparing(Integer::intValue)).orElse(0);
        int maxY = allY.stream().max(Comparator.comparing(Integer::intValue)).orElse(0);

        //noinspection UnnecessaryLocalVariable
        Rectangle box = new Rectangle(minX, minY, maxX-minX+1, maxY-minY+1);

        return box;
    }

    private static double density(List<Point> points) {
        Rectangle rectangle = boundingBox(points);

        double area = rectangle.width * rectangle.height;

        //noinspection UnnecessaryLocalVariable
        double density = points.size() / area;

        return density;
    }

    private static void display(List<Point> points) {

        Rectangle rectangle = boundingBox(points);

        char[][] screen = new char[rectangle.height][rectangle.width];

        for (Point p: points) {
            int x = p.x - rectangle.x;
            int y = p.y - rectangle.y;
            screen[y][x] = 'x';
        }

        for (char[] line: screen) {
            System.out.println(String.valueOf(line));
        }
    }

    static class Point {
        int x;
        int y;
        int vx;
        int vy;

        Point(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        void move() {
            x += vx;
            y += vy;
        }

        public String toString() {
            return "position=<"+x+", "+y+"> velocity=<"+vx+", "+vy+">";
        }

        static List<Point> parse(List<String> input) {
            List<Point> points = new ArrayList<>();
            Pattern pattern = Pattern.compile("position=< *(-?\\d+), *(-?\\d+)> velocity=< *(-?\\d+), *(-?\\d+)>");

            for (String s: input) {
                Matcher matcher = pattern.matcher(s);
                if (matcher.matches()) {
                    Point p = new Point(
                            Integer.parseInt(matcher.group(1)),
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3)),
                            Integer.parseInt(matcher.group(4))
                    );
                    points.add(p);
                } else {
                    throw new RuntimeException(s);
                }
            }

            return points;
        }
    }

}
