package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import java.util.*;


public class Day14 extends AdventOfCodeRunner {

    public Day14() {
    }

    @Override
    public void run(List<String> input) {

        {
            Receip first = new Receip('3');
            Receip last = first.append(new Receip('7'));

            Receip elf1 = first;
            Receip elf2 = last;

            int N = Integer.parseInt(input.get(0));
            for (int n = 0; n < N + 10; n++) {
                int score1 = elf1.score - '0';
                int score2 = elf2.score - '0';
                int newScore = score1 + score2;

                char[] chars = String.valueOf(newScore).toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    last = last.append(new Receip(chars[i]));
                }

                for (int i = 0; i < score1 + 1; i++) {
                    elf1 = elf1.next;
                }
                for (int i = 0; i < score2 + 1; i++) {
                    elf2 = elf2.next;
                }

            }

            System.out.println("Result Part 1: "+printLast10(first, N));
        }


        {
            Receip first = new Receip('3');
            Receip last = first.append(new Receip('7'));

            Receip elf1 = first;
            Receip elf2 = last;

            StringBuilder sb = new StringBuilder(toString(first));
            String str = input.get(0);

            long nrOfLeftReceips = 0;
            while (true) {
                int score1 = elf1.score - '0';
                int score2 = elf2.score - '0';
                int newScore = score1 + score2;

                char[] chars = String.valueOf(newScore).toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    last = last.append(new Receip(chars[i]));
                    sb.append(toString(last));
                }

                for (int i = 0; i < score1 + 1; i++) {
                    elf1 = elf1.next;
                }
                for (int i = 0; i < score2 + 1; i++) {
                    elf2 = elf2.next;
                }

                if (sb.indexOf(str) >= 0) {
                    nrOfLeftReceips += sb.indexOf(str);
                    break;
                }

                int x = sb.length() - str.length();
                if (x > 0) {
                    sb.delete(0, x);
                    nrOfLeftReceips += x;
                }
            }

            System.out.println("Result Part 2: "+nrOfLeftReceips);
        }


    }

    private String printLast10(Receip first, int skipReceips) {
        StringBuilder sb = new StringBuilder();
        Receip r = first;

        while (r.pos < skipReceips) r = r.next;

        for (int i=0; i<10; i++) {
            sb.append(r.score);
            r = r.next;
        }

        return sb.toString();
    }


    private String toString(Receip first) {
        StringBuilder sb = new StringBuilder(""+first.score);
        Receip r = first.next;
        while (r.pos > 0) {
            sb.append(r.score);
            r = r.next;
        }
        return sb.toString();
    }

    static class Receip {
        Receip next;

        char score;
        int pos;

        public Receip(char score) {
            this.next = this;
            this.score = score;
            this.pos = 0;
        }

        Receip append(Receip receip) {
            receip.next = this.next;
            this.next = receip;
            receip.pos = this.pos + 1;
            return receip;
        }

        public String toString() {
            return ""+score;
        }
    }
}
