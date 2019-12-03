package de.streubel.aoc19;

import com.google.common.base.Splitter;
import de.streubel.AdventOfCodeRunner;

import java.util.List;
import java.util.stream.IntStream;

public class Day02 extends AdventOfCodeRunner {

    private static final int ADD = 1;
    private static final int MUL = 2;
    private static final int EXT = 99;

    @Override
    public void run(List<String> stringInput) {
        int[] input = Splitter
                .on(",")
                .trimResults()
                .splitToList(stringInput.get(0))
                .stream()
                .flatMapToInt(s -> IntStream.of(Integer.parseInt(s)))
                .toArray();

        int result = getLastResult(input.clone(), 12, 2);

        System.out.println("Result Part 1 (4330636): "+result);


        try {
            for (int noun = 0; noun < 100; noun++) {
                for (int verb = 0; verb < 100; verb++) {
                    result = getLastResult(input.clone(), noun, verb);
                    if (result == 19690720) {
                        throw new Exception(""+(100 * noun + verb));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Result Part 2: "+e.getMessage());
        }

    }

    private int getLastResult(int[] input, int noun, int verb) {
        int lastAddr = -1;

        input[1] = noun;
        input[2] = verb;

        for (int pointer=0; pointer<input.length; pointer += 4) {
            final int instr = input[pointer+0];
            if (instr == EXT) {
                break;
            }

            final int addrA = input[pointer+1];
            final int addrB = input[pointer+2];
            final int addrC = input[pointer+3];
            final int a = input[addrA];
            final int b = input[addrB];

            int c;
            switch (instr) {
                case ADD:
                    c = a + b;
                    break;

                case MUL:
                    c = a * b;
                    break;

                default:
                    throw new RuntimeException("unknown instruction");
            }

            input[addrC] = c;
            lastAddr = addrC;
        }

        return input[lastAddr];
    }

}
