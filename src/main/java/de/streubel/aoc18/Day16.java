package de.streubel.aoc18;

import com.google.common.collect.Iterables;
import de.streubel.AdventOfCodeRunner;
import de.streubel.aoc18.Instr.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day16 extends AdventOfCodeRunner {

    public Day16() {
    }

    @Override
    public void run(List<String> input) {
        Instr[] instructions = {
            new addr(), new addi(),
            new mulr(), new muli(),
            new banr(), new bani(),
            new borr(), new bori(),
            new setr(), new seti(),
            new gtir(), new gtri(), new gtrr(),
            new eqir(), new eqri(), new eqrr(),
        };

        Pattern beforePattern = Pattern.compile("Before: +\\[([0-9]), ([0-9]), ([0-9]), ([0-9])]");
        Pattern afterPattern  = Pattern.compile("After: +\\[([0-9]), ([0-9]), ([0-9]), ([0-9])]");
        Pattern instrPattern  = Pattern.compile("([0-9]+) ([0-9]) ([0-9]) ([0-9])");
        Matcher matcher;

        int result = 0;

        int[] regBefore = null;
        int[] regAfter = null;
        int[] args = null;

        Map<Integer, Instr> opcodeInstr = new HashMap<>();

        for (String line: input) {

            matcher = beforePattern.matcher(line);
            if (matcher.matches()) {
                regBefore = new int[matcher.groupCount()];
                for (int i=0; i<regBefore.length ; i++) {
                    regBefore[i] = Integer.parseInt(matcher.group(i+1));
                }
            }

            matcher = instrPattern.matcher(line);
            if (matcher.matches()) {
                args = new int[matcher.groupCount()];
                for (int i=0; i<args.length ; i++) {
                    args[i] = Integer.parseInt(matcher.group(i+1));
                }
            }

            matcher = afterPattern.matcher(line);
            if (matcher.matches()) {
                regAfter = new int[matcher.groupCount()];
                for (int i=0; i<regAfter.length ; i++) {
                    regAfter[i] = Integer.parseInt(matcher.group(i+1));
                }
            }

            if (regAfter != null && args != null && regBefore != null) {

                int opcode = args[0];
                int argA = args[1];
                int argB = args[2];
                int argC = args[3];

                List<Instr> candidates = new ArrayList<>();
                for (Instr instr : instructions) {
                    instr.setArgs(argA, argB, argC);
                    instr.setRegister(regBefore);
                    instr.exec();
                    int[] reg = instr.getRegister();
                    if (Arrays.equals(reg, regAfter)) {
                        candidates.add(instr);
                    }
                }

                if (candidates.size() >= 3)
                    result++;

                Iterables.removeIf(candidates, instr -> opcodeInstr.values().contains(instr));
                if (candidates.size() == 1)
                    opcodeInstr.put(opcode, candidates.get(0));

                regAfter = null;
                regBefore = null;
                args = null;
            }
        }

        System.out.println("Result Part 1: "+result);

        int emptyLine = 0;
        int l;
        for (l = 0; l<input.size(); l++) {
            if (input.get(l).equals(""))
                emptyLine++;
            else
                emptyLine = 0;

            if (emptyLine >= 3)
                break;
        }
        l++;

        int[] register = {0, 0, 0, 0};
        args = null;

        for (; l<input.size(); l++) {
            matcher = instrPattern.matcher(input.get(l));
            if (matcher.matches()) {
                args = new int[matcher.groupCount()];
                for (int i=0; i<args.length ; i++) {
                    args[i] = Integer.parseInt(matcher.group(i+1));
                }
            }

            if (args != null) {
                Instr instr = opcodeInstr.get(args[0]);
                if (instr != null) {
                    instr.setRegister(register);
                    instr.setArgs(args[1], args[2], args[3]);
                    instr.exec();
                    System.arraycopy(instr.getRegister(), 0, register, 0, register.length);
                } else {
                    throw new RuntimeException();
                }
            } else {
                throw new RuntimeException();
            }
        }

        //5927797001713954464
        System.out.println("Result Part 2: "+register[0]);
    }
}
