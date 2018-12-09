package de.streubel.aoc18;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import de.streubel.AdventOfCodeRunner;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day07 extends AdventOfCodeRunner {

    @Override
    public void run(List<String> input) {

        Graph graph = Graph.parse(input);
        Node firstNode = graph.findFirstNode();

        SortedSet<Node> available = new TreeSet<>(nodeOrdering);
        available.add(firstNode);
        String path = recordPath(available);

        System.out.println("Result Part 1: "+ path);


        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker("Worker 1"));
        workers.add(new Worker("Worker 2"));
        workers.add(new Worker("Worker 3"));
        workers.add(new Worker("Worker 4"));
        workers.add(new Worker("Worker 5"));


        graph = Graph.parse(input);
        firstNode = graph.findFirstNode();
        available.clear();

        List<Node> inbox = new ArrayList<>();

        inbox.add(firstNode);

        int seconds = 0;
        int nrOfBusyWorkers = 0;

        while (!inbox.isEmpty() || nrOfBusyWorkers > 0) {

            List<Worker> idleWorkers = workers.stream().filter(Worker::isIdle).collect(Collectors.toList());

            int min = Math.min(inbox.size(), idleWorkers.size());
            for (int i = 0; i < min; i++) {
                Node task = inbox.get(0);
                idleWorkers.get(i).assignTask(task);
                inbox.remove(task);
            }

            workers.stream().filter(Worker::isBusy).forEach(Worker::perform);

            List<Node> finishedTasks = workers.stream().filter(Worker::hasFinished).map((Function<Worker, Node>) Worker::fetchTask).collect(Collectors.toList());

            available.addAll(finishedTasks);

            seconds++;


            if (!available.isEmpty()) {
                Node node = available.first();
                available.remove(node);

                node.visited = true;
                for (Edge edge : node.connections) {
                    edge.end.counter--;
                    if (edge.end.counter <= 0 && !edge.end.visited) {
                        inbox.add(edge.end);
                    }
                }
            }

            nrOfBusyWorkers = workers.stream().filter(Worker::isBusy).collect(Collectors.toList()).size();
        }

        System.out.println("Result Part 2: "+ seconds);
    }

    private static String recordPath(SortedSet<Node> available) {
        StringBuilder path = new StringBuilder();

        while (!available.isEmpty()) {
            Node node = available.first();
            available.remove(node);

            path.append(node.name);
            node.visited = true;
            for (Edge edge : node.connections) {
                edge.end.counter--;
                if (edge.end.counter <= 0 && !edge.end.visited) {
                    available.add(edge.end);
                }
            }
        }

        return path.toString();
    }



    public static class Node {
        String name;
        Set<Edge> connections;
        int counter;
        boolean visited;

        Node(String name) {
            this.name = name;
            this.connections = new HashSet<>();
            this.counter = 0;
            this.visited = false;
        }

        void addEdgeTo(Node end) {
            connections.add(new Edge(this, end));
        }

        int taskDuration() {
            return 60 + name.charAt(0) - 'A' + 1;
        }

        public String toString() {
            return name;
        }
    }

    public static class Edge {
        Node start;
        Node end;

        Edge(Node start, Node end) {
            this.start = start;
            this.end   = end;
            this.end.counter++;
        }

        public String toString() {
            return start.name + "->" + end.name;
        }
    }

    public static class Graph {
        Set<Node> nodes = new HashSet<>();

        Node findFirstNode() {

            //noinspection Guava,ConstantConditions
            Set<Edge> allEdges = FluentIterable.from(nodes)
                    .transformAndConcat(node -> node.connections)
                    .toSet();

            //noinspection Guava,ConstantConditions
            Set<Node> allEndNodes = FluentIterable.from(allEdges)
                    .transform(edge -> edge.end)
                    .toSet();

            Sets.SetView<Node> difference = Sets.difference(nodes, allEndNodes);
            return Iterables.getOnlyElement(difference);
        }

        Node findOrCreateNode(String nodeName) {
            Node node = nodes.stream()
                    .filter(node1 -> nodeName.equals(node1.name))
                    .findFirst()
                    .orElse(null);

            if (node == null) {
                node = new Node(nodeName);
                nodes.add(node);
            }

            return node;
        }

        static Graph parse(List<String> input) {
            Graph graph = new Graph();

            for (String s: input) {
                Pattern pattern = Pattern.compile("Step ([A-Z]) must be finished before step ([A-Z]) can begin.");

                Matcher m = pattern.matcher(s);
                if (m.matches()) {
                    Node start = graph.findOrCreateNode(m.group(1));
                    Node end   = graph.findOrCreateNode(m.group(2));
                    start.addEdgeTo(end);
                } else {
                    throw new RuntimeException();
                }
            }

            return graph;
        }
    }


    public static class Worker {
        String name;
        Node task;
        int remainingSec;

        Worker(String name) {
            this.name = name;
            this.remainingSec = -1;
        }

        void assignTask(Node task) {
            this.task = task;
            remainingSec = task.taskDuration();
        }
        void perform() {
            remainingSec--;
        }
        Node fetchTask() {
            Node task = this.task;
            this.task = null;
            remainingSec = -1;
            return task;
        }
        boolean isIdle() {
            return remainingSec < 0;
        }
        boolean isBusy() {
            return remainingSec > 0;
        }
        boolean hasFinished() {
            return remainingSec == 0;
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static Ordering<Node> nodeOrdering = Ordering.natural()
            .onResultOf((Function<Node, String>) node -> node.name);

}
