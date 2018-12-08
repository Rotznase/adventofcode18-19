package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import java.text.NumberFormat;
import java.util.*;


public class Day08 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> input) {

        Node rootNode = parseInput(new Scanner(input.get(0)));

        System.out.println("Result Part 1: "+rootNode.calculateMetadata());

    }

    private static Node parseInput(Scanner scanner) {
        int nrOfChildren = scanner.nextInt();
        int nrOfMetadata = scanner.nextInt();
        Node n = new Node("A", nrOfChildren, nrOfMetadata);

        for (int i=0; i<nrOfChildren; i++) {
            Node child = parseInput(scanner);
            n.children[i] = child;
        }

        for (int i=0; i<nrOfMetadata; i++) {
            int metaData = scanner.nextInt();
            n.metadata[i] = metaData;
        }

        return n;
    }

    static class Node {
        String name;
        Node[] children;
        int[] metadata;

        Node(String name, int nrOfChildren, int nrOfMetadata) {
            this.name = name;
            this.children = new Node[nrOfChildren];
            this.metadata = new int[nrOfMetadata];
        }

        int calculateMetadata() {
            int sum = sumMetadata();
            for (Node n: children)
                sum += n.calculateMetadata();
            return sum;
        }

        int sumMetadata() {
            int sum = 0;
            for (int i: metadata)
                sum += i;
            return sum;
        }
    }
}
