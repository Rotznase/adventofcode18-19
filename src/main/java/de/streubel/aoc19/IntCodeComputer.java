package de.streubel.aoc19;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;

public class IntCodeComputer {

    private static final int ADD = 1;
    private static final int MUL = 2;
    private static final int INP = 3;
    private static final int OUP = 4;
    private static final int JMP_TRUE = 5;
    private static final int JMP_FALSE = 6;
    private static final int LT = 7;
    private static final int EQ = 8;
    private static final int EXT = 99;

    private static final int P_MODE = 0;
    private static final int I_MODE = 1;

    public enum State {IDLE, RUNNING, PAUSED, HALTED,}

    private Queue<Integer> input;
    private Deque<Integer> output;
    private int pc;
    private State state;

    public IntCodeComputer() {
        input = new ArrayDeque<>();
        output = new ArrayDeque<>();
        pc = 0;
        state = State.IDLE;
    }

    public void setInput(final Integer ... input) {
        this.input.addAll(Arrays.asList(input));
    }

    public Integer getLastOutput() {
        return output.getLast();
    }

    public Integer getOutput() {
        return output.poll();
    }

    public void reset() {
        pc = 0;
        state = State.IDLE;
    }

    public void run(final int[] program) {
        state = State.RUNNING;

        for (;;) {
            final int[] instrArr = split(program[pc + 0]);
            final int instr = instrArr[0];

            if (instr == EXT) {
                state = State.HALTED;
            }

            if (state == State.PAUSED || state == State.HALTED) {
                break;
            }

            final int p1Mode = instrArr[1];
            final int p2Mode = instrArr[2];
            final int p3Mode = instrArr[3];

            int param1;
            int param2;
            int param3;
            int a, b;

            int c;
            switch (instr) {
                case ADD:
                    param1 = program[pc + 1];
                    param2 = program[pc + 2];
                    param3 = program[pc + 3];
                    a = p1Mode == P_MODE ? program[param1] : param1;
                    b = p2Mode == P_MODE ? program[param2] : param2;
                    c = a + b;
                    program[param3] = c;
                    pc += 4;
                    break;

                case MUL:
                    param1 = program[pc + 1];
                    param2 = program[pc + 2];
                    param3 = program[pc + 3];
                    a = p1Mode == P_MODE ? program[param1] : param1;
                    b = p2Mode == P_MODE ? program[param2] : param2;
                    c = a * b;
                    program[param3] = c;
                    pc += 4;
                    break;

                case INP:
                    param1 = program[pc + 1];
                    if (input.isEmpty()) {
                        state = State.PAUSED;
                    } else {
                        program[param1] = input.poll();
                        pc += 2;
                    }
                    break;

                case OUP:
                    param1 = program[pc + 1];
                    output.add(p1Mode == P_MODE ? program[param1] : param1);
                    pc += 2;
                    break;

                case JMP_TRUE:
                    param1 = program[pc + 1];
                    param2 = program[pc + 2];
                    a = p1Mode == P_MODE ? program[param1] : param1;
                    b = p2Mode == P_MODE ? program[param2] : param2;
                    pc = a != 0 ? b : pc + 3;
                    break;

                case JMP_FALSE:
                    param1 = program[pc +1];
                    param2 = program[pc +2];
                    a = p1Mode == P_MODE ? program[param1] : param1;
                    b = p2Mode == P_MODE ? program[param2] : param2;
                    pc = a == 0 ? b : pc + 3;
                    break;

                case LT:
                    param1 = program[pc + 1];
                    param2 = program[pc + 2];
                    param3 = program[pc + 3];
                    a = p1Mode == P_MODE ? program[param1] : param1;
                    b = p2Mode == P_MODE ? program[param2] : param2;
                    program[param3] = a < b ? 1 : 0;
                    pc += 4;
                    break;

                case EQ:
                    param1 = program[pc + 1];
                    param2 = program[pc + 2];
                    param3 = program[pc + 3];
                    a = p1Mode == P_MODE ? program[param1] : param1;
                    b = p2Mode == P_MODE ? program[param2] : param2;
                    program[param3] = a == b ? 1 : 0;
                    pc += 4;
                    break;

                default:
                    throw new RuntimeException("unknown instruction");
            }
        }

    }

    public boolean hasStopped() {
        return state == State.HALTED;
    }

    private int[] split(int number) {
        int[] intArr = new int[4];

        intArr[0] = number % 100;

        number /= 100;
        intArr[1] = number % 10;

        number /= 10;
        intArr[2] = number % 10;

        number /= 10;
        intArr[3] = number % 10;

        return intArr;
    }
}
