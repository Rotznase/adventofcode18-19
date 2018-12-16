package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;

import java.util.Arrays;
import java.util.List;
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

                int instructionCounter = 0;
                for (Instr instr : instructions) {
                    instr.setArgs(argA, argB, argC);
                    instr.setRegister(regBefore);
                    instr.exec();
                    int[] reg = instr.getRegister();
                    if (Arrays.equals(reg, regAfter))
                        instructionCounter++;
                }

                if (instructionCounter >= 3) {
                    result++;
                }

                regAfter = null;
                regBefore = null;
                args = null;
            }


        }

        System.out.println("Result Part 1: "+result);
    }

    static abstract class Instr {
        int[] register = new int[4];
        int A;
        int B;
        int C;

        void setArgs(int A, int B, int C) {
            this.A = A;
            this.B = B;
            this.C = C;
        }

        abstract void exec();

        int[] getRegister() {
            return register;
        }

        void setRegister(int[] register) {
            System.arraycopy(register, 0, this.register, 0, register.length);
        }
    }

    static class addr extends Instr {
        @Override
        void exec() { register[C] = register[A] + register[B]; }
    }

    static class addi extends Instr {
        @Override
        void exec() { register[C] = register[A] + B; }
    }


    static class mulr extends Instr {
        @Override
        void exec() { register[C] = register[A] * register[B]; }
    }

    static class muli extends Instr {
        @Override
        void exec() { register[C] = register[A] * B; }
    }

    static class banr extends Instr {
        @Override
        void exec() { register[C] = register[A] & register[B]; }
    }

    static class bani extends Instr {
        @Override
        void exec() { register[C] = register[A] & B; }
    }

    static class borr extends Instr {
        @Override
        void exec() { register[C] = register[A] | register[B]; }
    }

    static class bori extends Instr {
        @Override
        void exec() { register[C] = register[A] | B; }
    }

    static class setr extends Instr {
        @Override
        void exec() { register[C] = register[A]; }
    }

    static class seti extends Instr {
        @Override
        void exec() { register[C] = A; }
    }

    static class gtir extends Instr {
        @Override
        void exec() { register[C] = A > register[B] ? 1 : 0; }
    }

    static class gtri extends Instr {
        @Override
        void exec() { register[C] = register[A] > B ? 1 : 0; }
    }

    static class gtrr extends Instr {
        @Override
        void exec() { register[C] = register[A] > register[B] ? 1 : 0; }
    }

    static class eqir extends Instr {
        @Override
        void exec() { register[C] = A == register[B] ? 1 : 0; }
    }

    static class eqri extends Instr {
        @Override
        void exec() { register[C] = register[A] == B ? 1 : 0; }
    }

    static class eqrr extends Instr {
        @Override
        void exec() { register[C] = register[A] == register[B] ? 1 : 0; }
    }
}
