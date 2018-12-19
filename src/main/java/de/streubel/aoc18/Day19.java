package de.streubel.aoc18;

import de.streubel.AdventOfCodeRunner;
import de.streubel.aoc18.Day16.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day19 extends AdventOfCodeRunner {

    public Day19() {
    }

    @Override
    public void run(List<String> input) {

        Pattern instrPattern  = Pattern.compile("([a-z]+) ([0-9]+) ([0-9]+) ([0-9]+)");
        Pattern ipPattern  = Pattern.compile("#ip ([0-9]+)");

        Matcher matcher;

        int ip = 0;
        int ipReg = 0;
        List<Instr> program = new ArrayList<>();

        for (String line: input) {
            matcher = ipPattern.matcher(line);
            if (matcher.matches()) {
                ipReg = Integer.parseInt(matcher.group(1));
            }

            matcher = instrPattern.matcher(line);
            if (matcher.matches()) {
                String instrName = matcher.group(1);
                Instr instr = null;
                switch (instrName) {
                    case "addr": instr = new addr(); break;
                    case "addi": instr = new addi(); break;
                    case "mulr": instr = new mulr(); break;
                    case "muli": instr = new muli(); break;
                    case "banr": instr = new banr(); break;
                    case "bani": instr = new bani(); break;
                    case "borr": instr = new borr(); break;
                    case "bori": instr = new bori(); break;
                    case "setr": instr = new setr(); break;
                    case "seti": instr = new seti(); break;
                    case "gtir": instr = new gtir(); break;
                    case "gtri": instr = new gtri(); break;
                    case "gtrr": instr = new gtrr(); break;
                    case "eqir": instr = new eqir(); break;
                    case "eqri": instr = new eqri(); break;
                    case "eqrr": instr = new eqrr(); break;
                    default: throw new RuntimeException();
                }

                int argA = Integer.parseInt(matcher.group(2));
                int argB = Integer.parseInt(matcher.group(3));
                int argC = Integer.parseInt(matcher.group(4));
                instr.setArgs(argA, argB, argC);

                program.add(instr);
            }
        }

        int[] register = {0, 0, 0, 0, 0, 0};

        while (ip < program.size()) {
            register[ipReg] = ip;
            Instr instr = program.get(ip);
            instr.setRegister(register);
            instr.exec();
            register = instr.getRegister();
            ip = register[ipReg];
            ip++;
        }

        System.out.println("Result Part 1: "+register[0]);
    }

}
