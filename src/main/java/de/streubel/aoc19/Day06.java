package de.streubel.aoc19;

import de.streubel.AdventOfCodeRunner;

import java.util.*;

public class Day06 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> stringInput) {

        stringInput.forEach(s ->  {
            final String[] split = s.split("\\)");
            Orbiter.connect(split[0], split[1]);
        });

        Orbiter.initDepth();
        final Orbiter com = Orbiter.getRoot();
        final int result = com.collectAllDepths();

        System.out.println("Result Part 1 (241064): "+result);


    }

    public static class Orbiter {

        private static final Map<String, Orbiter> orbiterRepo = new HashMap<>();

        static void connect(final String leftName, final String rightName) {
            Orbiter parent = orbiterRepo.get(leftName);
            if (parent == null) {
                parent = new Orbiter(leftName);
                orbiterRepo.put(leftName, parent);
            }

            Orbiter child = orbiterRepo.get(rightName);
            if (child == null) {
                child = new Orbiter(rightName);
                orbiterRepo.put(rightName, child);
            }

            parent.add(child);
        }

        static Orbiter getRoot() {
            Orbiter anyOrbiter = orbiterRepo.values().stream().findFirst().orElse(null);
            if (anyOrbiter != null) {
                while (anyOrbiter.parent != null) {
                    anyOrbiter = anyOrbiter.parent;
                }
            }

            return anyOrbiter;
        }

        static void initDepth() {
            getRoot().calculateDepth();
        }



        String name;
        Orbiter parent;
        List<Orbiter> children;
        int depth;

        public Orbiter(String name) {
            this.name = name;
            this.children = new ArrayList<>();

            this.parent = null;
            this.depth = -1;
        }

        public void calculateDepth() {
            this.depth = parent != null ? parent.depth + 1 : 0;
            for (Orbiter child : children) {
                child.calculateDepth();
            }
        }

        public int collectAllDepths() {
            int depth = this.depth;
            for (Orbiter o : children) {
                depth += o.collectAllDepths();
            }
            return depth;
        }

        public void add(Orbiter o) {
            children.add(o);
            o.parent = this;
        }
    }

}
