package de.streubel.aoc19;

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


    private int[] program;
    private int[] input;
    private int output;

    public void setProgram(final int[] program) {
        this.program = program.clone();
    }

    public void setInput(final int ... input) {
        this.input = input.clone();
    }

    public int getOutput() {
        return output;
    }

    public void run() {
        int output = -1;
        int inputCounter = 0;

        for (int pointer=0; pointer<program.length; ) {
            final int[] instrArr = split(program[pointer+0]);
            final int instr = instrArr[0];

            if (instr == EXT) {
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
                    param1 = program[pointer+1];
                    param2 = program[pointer+2];
                    param3 = program[pointer+3];
                    a = p1Mode == P_MODE ? program[param1] : param1;
                    b = p2Mode == P_MODE ? program[param2] : param2;
                    c = a + b;
                    program[param3] = c;
                    pointer += 4;
                    break;

                case MUL:
                    param1 = program[pointer+1];
                    param2 = program[pointer+2];
                    param3 = program[pointer+3];
                    a = p1Mode == P_MODE ? program[param1] : param1;
                    b = p2Mode == P_MODE ? program[param2] : param2;
                    c = a * b;
                    program[param3] = c;
                    pointer += 4;
                    break;

                case INP:
                    param1 = program[pointer+1];
                    program[param1] = input[inputCounter++];
                    pointer += 2;
                    break;

                case OUP:
                    param1 = program[pointer+1];
                    output = p1Mode == P_MODE ? program[param1] : param1;
                    pointer += 2;
                    break;

                case JMP_TRUE:
                    param1 = program[pointer+1];
                    param2 = program[pointer+2];
                    a = p1Mode == P_MODE ? program[param1] : param1;
                    b = p2Mode == P_MODE ? program[param2] : param2;
                    pointer = a != 0 ? b : pointer + 3;
                    break;

                case JMP_FALSE:
                    param1 = program[pointer+1];
                    param2 = program[pointer+2];
                    a = p1Mode == P_MODE ? program[param1] : param1;
                    b = p2Mode == P_MODE ? program[param2] : param2;
                    pointer = a == 0 ? b : pointer + 3;
                    break;

                case LT:
                    param1 = program[pointer+1];
                    param2 = program[pointer+2];
                    param3 = program[pointer+3];
                    a = p1Mode == P_MODE ? program[param1] : param1;
                    b = p2Mode == P_MODE ? program[param2] : param2;
                    program[param3] = a < b ? 1 : 0;
                    pointer += 4;
                    break;

                case EQ:
                    param1 = program[pointer+1];
                    param2 = program[pointer+2];
                    param3 = program[pointer+3];
                    a = p1Mode == P_MODE ? program[param1] : param1;
                    b = p2Mode == P_MODE ? program[param2] : param2;
                    program[param3] = a == b ? 1 : 0;
                    pointer += 4;
                    break;

                default:
                    throw new RuntimeException("unknown instruction");
            }
        }



        this.output = output;
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
