package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Day13 extends AdventOfCodeRunner {

    public Day13() {
    }

    @Override
    public void run(List<String> input) {

        Track track = parseTrack(input);
        List<Cart> carts = track.findCarts();

        while (true) {
            //System.out.println(track);
            try {
                carts.stream()
                     .sorted(Comparator.comparing(Cart::getX)
                                       .thenComparing(Cart::getY))
                     .forEach(cart -> cart.move(track));
            } catch (CollisionDetected e) {
                System.out.println("Result Part 1: x,y = "+e.x+","+e.y);
                break;
            }
        }

    }

    private Track parseTrack(List<String> input) {
        char[][] course = new char[input.size()][];
        for (int line=0; line<input.size(); line++) {
            course[line] = input.get(line).toCharArray();
        }

        return new Track(course);
    }

    public static class Cart {
        int x;
        int y;
        char dir;
        int nextTurn;
        char trackSymbol;

        private static final int L = 0;
        private static final int S = 1;
        private static final int R = 2;

        Cart(int x, int y, char dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.nextTurn = L;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

        void move(Track track) throws CollisionDetected {

            track.set(x, y, trackSymbol);
            switch (dir) {
                case '>': x++; break;
                case 'v': y++; break;
                case '<': x--; break;
                case '^': y--; break;
            }

            char c = track.get(x, y);

            switch (c) {
                case '-':
                case '|':
                    break;
                case '\\':
                case '/':
                case '+': dir = turnTable(dir, c);
                    break;
                default:
                    throw new CollisionDetected(x, y);
            }
            trackSymbol = track.get(x, y);
            track.set(x, y, dir);

        }

        /**
         *    \  /  +
         * >  v  ^
         * <  ^  v
         * ^  <  >
         * v  >  <
         */
        private char turnTable(char movingDir, char trackMarker) {
            switch (movingDir) {
                case '>':
                    switch (trackMarker) {
                        case '\\': return 'v';
                        case '/':  return '^';
                        case '+':
                            int nextTurn = this.nextTurn;
                            this.nextTurn= (this.nextTurn + 1) % 3;
                            switch (nextTurn) {
                                case L: return '^';
                                case S: return '>';
                                case R: return 'v';
                            }
                    }
                case '<':
                    switch (trackMarker) {
                        case '\\': return '^';
                        case '/':  return 'v';
                        case '+':
                            int nextTurn = this.nextTurn;
                            this.nextTurn= (this.nextTurn + 1) % 3;
                            switch (nextTurn) {
                                case L: return 'v';
                                case S: return '<';
                                case R: return '^';
                            }
                    }
                case '^':
                    switch (trackMarker) {
                        case '\\': return '<';
                        case '/':  return '>';
                        case '+':
                            int nextTurn = this.nextTurn;
                            this.nextTurn= (this.nextTurn + 1) % 3;
                            switch (nextTurn) {
                                case L: return '<';
                                case S: return '^';
                                case R: return '>';
                            }
                    }
                case 'v':
                    switch (trackMarker) {
                        case '\\': return '>';
                        case '/':  return '<';
                        case '+':
                            int nextTurn = this.nextTurn;
                            this.nextTurn= (this.nextTurn + 1) % 3;
                            switch (nextTurn) {
                                case L: return '>';
                                case S: return 'v';
                                case R: return '<';
                            }
                    }
            }

            throw new RuntimeException();
        }

    }

    public static class Track {

        char[][] course;

        Track(char[][] course) {
            this.course = course;
        }

        public char get(int x, int y) {
            return course[y][x];
        }

        void set(int x, int y, char dir) {
            course[y][x] = dir;
        }

        List<Cart> findCarts() {
            List<Cart> carts = new ArrayList<>();
            for (int y=0; y<course.length; y++) {
                for (int x=0; x<course[y].length; x++) {
                    if ("<>^v".indexOf(course[y][x]) >= 0) {
                        Cart cart = new Cart(x, y, course[y][x]);
                        cart.trackSymbol = guessTrack(x, y);
                        //course[y][x] = guessTrack(x, y);
                        carts.add(cart);
                    }
                }
            }
            return carts;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (char[] aCourse : course)
                sb.append(String.valueOf(aCourse)).append("\n");

            return sb.toString();
        }

        char guessTrack(int x, int y) {
            final char north = y>0 ? course[y-1][x] : ' ';
            final char south = y<course.length-1 ? course[y+1][x] : ' ';
            final char west  = x>0 ? course[y][x-1] : ' ';
            final char east  = x<course[y].length-1 ? course[y][x+1] : ' ';

            char c;
            if ("-/\\".contains(""+west) && "-/\\".contains(""+east) && "|/\\".contains(""+north) && "|/\\".contains(""+south ))
                c = '+';
            else
            if ("-+/\\".contains(""+west) && "-+/\\".contains(""+east) && "- /\\".contains(""+north) && "- /\\".contains(""+south))
                c = '-';
            else
            if ("| /\\".contains(""+west) && "| /\\".contains(""+east) && "|+/\\".contains(""+north) && "|+/\\".contains(""+south))
                c ='|';
            else
            if (("-+".contains(""+west) && "|+".contains(""+north) && " |".contains(""+east) && " -".contains(""+south))
             || (" |".contains(""+west) && " -".contains(""+north) && "-+".contains(""+east) && "|+".contains(""+south)))
                c ='/';
            else
            if (("-+".contains(""+west) && "|+".contains(""+south) && " |".contains(""+east) && " -".contains(""+north))
             || (" |".contains(""+west) && " -".contains(""+south) && "-+".contains(""+east) && "|+".contains(""+north)))
                c ='\\';
            else
                throw new RuntimeException();

            return c;
        }

    }

    public static class CollisionDetected extends RuntimeException {
        private final int x;
        private final int y;

        CollisionDetected(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
