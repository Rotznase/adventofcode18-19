package de.streubel.aoc18;

abstract class Instr {
    int[] register;
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
        if (this.register == null)
            this.register = new int[register.length];
        System.arraycopy(register, 0, this.register, 0, register.length);
    }

    public String toString() {
        return getClass().getSimpleName();
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
