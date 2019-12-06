package de.streubel.aoc19;

import com.google.common.collect.Lists;
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


        final Orbiter you = Orbiter.find("YOU");
        final Orbiter san = Orbiter.find("SAN");

        final List<Orbiter> pathToYOU = new ArrayList<>();
        final List<Orbiter> pathToSAN = new ArrayList<>();

        com.goTo(you, pathToYOU);
        com.goTo(san, pathToSAN);

        final List<Orbiter> pathInCommon = new ArrayList<>(pathToYOU);
        pathInCommon.retainAll(pathToSAN);

        final List<Orbiter> pathFromForkToYOU = new ArrayList<>(pathToYOU);
        pathFromForkToYOU.removeAll(pathInCommon);
        pathFromForkToYOU.remove(you);

        final List<Orbiter> pathFromForkToSAN = new ArrayList<>(pathToSAN);
        pathFromForkToSAN.removeAll(pathInCommon);
        pathFromForkToSAN.remove(san);

        final List<Orbiter> pathFromYOUToSAN = new ArrayList<>();
        pathFromYOUToSAN.addAll(Lists.reverse(pathFromForkToYOU));
        // apparently the forkNode doesn't count to the path from YOU to SAN.
        // pathFromYOUToSAN.add(Iterables.getLast(pathInCommon));
        pathFromYOUToSAN.addAll(pathFromForkToSAN);

        System.out.println("Result Part 2 (418): "+pathFromYOUToSAN.size());
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

        static Orbiter find(final String name) {
            return orbiterRepo.get(name);
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

        public boolean goTo(final Orbiter destination, final List<Orbiter> path) {
            boolean found = false;
            path.add(this);

            if (this.equals(destination)) {
                found = true;
            } else {
                for (Orbiter o : children) {
                    found = o.goTo(destination, path);
                    if (found) {
                        break;
                    }
                }

                if (!found) {
                    path.remove(this);
                }
            }

            return found;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Orbiter orbiter = (Orbiter) o;
            return name.equals(orbiter.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

}
