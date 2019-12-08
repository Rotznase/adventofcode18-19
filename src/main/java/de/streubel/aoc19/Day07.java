package de.streubel.aoc19;

import de.streubel.AdventOfCodeRunner;

import java.util.*;
import java.util.stream.IntStream;


public class Day07 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> stringInput) {

        int[] prog = Arrays
                .stream(stringInput.get(0).split(","))
                .flatMapToInt(s -> IntStream.of(Integer.parseInt(s)))
                .toArray();

        final IntCodeComputer ampA = new IntCodeComputer();
        final IntCodeComputer ampB = new IntCodeComputer();
        final IntCodeComputer ampC = new IntCodeComputer();
        final IntCodeComputer ampD = new IntCodeComputer();
        final IntCodeComputer ampE = new IntCodeComputer();

        int maxSignal = 0;

        List<int[]> permutations = generatePermutations(0, 1, 2, 3, 4);

        for (int[] setting : permutations) {
            int intermediateSignal = 0;

            ampA.setInput(setting[0], intermediateSignal);
            ampA.reset();
            ampA.run(prog.clone());
            intermediateSignal = ampA.getOutput();

            ampB.setInput(setting[1], intermediateSignal);
            ampB.reset();
            ampB.run(prog.clone());
            intermediateSignal = ampB.getOutput();

            ampC.setInput(setting[2], intermediateSignal);
            ampC.reset();
            ampC.run(prog.clone());
            intermediateSignal = ampC.getOutput();

            ampD.setInput(setting[3], intermediateSignal);
            ampD.reset();
            ampD.run(prog.clone());
            intermediateSignal = ampD.getOutput();

            ampE.setInput(setting[4], intermediateSignal);
            ampE.reset();
            ampE.run(prog.clone());
            intermediateSignal = ampE.getOutput();
            maxSignal = Math.max(intermediateSignal, maxSignal);

        }

        System.out.println("Result Part 1 (11828): "+maxSignal);
    }

    private List<int[]> generatePermutations(final int... ints) {
        final List<int[]> permutations = new ArrayList<>();
        permute(ints, 0, permutations);
        return permutations;
    }

    private void permute(final int[] arr, int k, final List<int[]> permutations) {
        for(int i = k; i < arr.length; i++){
            swap(arr, i, k);
            permute(arr, k+1, permutations);
            swap(arr, k, i);
        }
        if (k == arr.length - 1){
            permutations.add(arr.clone());
        }
    }

    private void swap(final int[] arr, final int i, final int j) {
        int t  = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

}
