package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day09 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> input) {


        int players = 0;
        Pattern pattern = Pattern.compile("(\\d+) players; last marble is worth (\\d+) points");
        Matcher matcher = pattern.matcher(input.get(0));
        if (matcher.matches()) {
            players = Integer.valueOf(matcher.group(1));
            int lastMarble = Integer.valueOf(matcher.group(2));
            Marble.setLastMarble(lastMarble);
        }

        Map<Integer, Long> playerScore = new HashMap<>();
        for (int p=0; p<players; p++) {
            playerScore.put(p, 0L);
        }

        Marble currentMarble;
        int currentPlayer;
        Marble marble;


        currentMarble = Marble.next();
        currentMarble.next = currentMarble;
        currentMarble.prev = currentMarble;


        currentPlayer = 0;
        currentMarble = currentMarble.insert(2, Marble.next());

        currentPlayer++;
        while (Marble.hasNext()) {
            marble = Marble.next();
            if (marble.nr % 23 == 0) {
                Marble removedMarble = currentMarble.remove(7);
                long score = playerScore.get(currentPlayer) + marble.nr + removedMarble.nr;
                playerScore.put(currentPlayer, score);
                currentMarble = removedMarble.next;
            } else {
                currentMarble = currentMarble.insert(2, marble);
            }
            currentPlayer = (currentPlayer + 1) % players;
        }

        System.out.println("Result Part 1/2: "+playerScore.values().stream().max(Long::compareTo).orElse(null));

    }


    static class Marble {
        int nr;
        Marble next;
        Marble prev;

        public Marble(int marble) {
            this.nr = marble;
        }

        public Marble insert(int steps, Marble insert) {
            Marble marble = this;
            for (int i=0; i<steps-1; i++) {
                marble = marble.next;
            }

            Marble left  = marble;
            Marble right = marble.next;


            insert.next = right;
            insert.prev = marble;

            left.next = insert;
            right.prev = insert;

            return insert;
        }

        public Marble remove(int steps) {
            Marble marbleToRemove = this;
            for (int i=0; i<steps; i++) {
                marbleToRemove = marbleToRemove.prev;
            }

            Marble left  = marbleToRemove.prev;
            Marble right = marbleToRemove.next;
            left.next = right;
            right.prev = left;

            return marbleToRemove;
        }

        public String toString()  {
            return ""+nr;
        }


        static int lastMarble = 0;
        static void setLastMarble(int lastMarble) {
            Marble.lastMarble = lastMarble;
        }
        static int m = 0;
        static Marble next() {
            return new Marble(m++);
        }

        static boolean hasNext() {
            return m < lastMarble;
        }
    }

}
