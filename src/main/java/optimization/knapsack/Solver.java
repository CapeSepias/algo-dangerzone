package optimization.knapsack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * The class <code>Solver</code> is an implementation of a branch&bound algorithm to solve the knapsack problem.
 */
public class Solver {

    /**
     * The main class
     */
    public static void main(String[] args) {
        try {
            Solver s = new Solver();
            s.solve(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the instance, solve it, and print the solution in the standard output
     */
    public void solve(String[] args) throws IOException {
        String fileName = null;

        // get the temp file name
        for (String arg : args) {
            if (arg.startsWith("-file=")) {
                fileName = arg.substring(6);
            }
        }
        if (fileName == null)
            return;

        // read the lines out of the file
        List<String> lines = new ArrayList<>();

        BufferedReader input = new BufferedReader(new FileReader(fileName));
        try {
            String line;
            while ((line = input.readLine()) != null) {
                lines.add(line);
            }
        } finally {
            input.close();
        }

        // parse the data in the file
        String[] firstLine = lines.get(0).split("\\s+");
        final int items = parseInt(firstLine[0]);
        final int capacity = parseInt(firstLine[1]);

        int[] values;
        int[] weights;
        values = new int[items];
        weights = new int[items];
        for (int i = 1; i < items + 1; i++) {
            String line = lines.get(i);
            String[] parts = line.split("\\s+");

            values[i - 1] = parseInt(parts[0]);
            weights[i - 1] = parseInt(parts[1]);
        }
        final double bestExpectation = getBestExpectation(values, weights, capacity);
        int bestSolution = Integer.MIN_VALUE;
        BitSet taken = new BitSet();
        Node root = new Node(0, capacity, bestExpectation);
        Deque<Node> deque = new LinkedList<>();
        deque.addFirst(root);
        while (!deque.isEmpty()) {
            final Node node = deque.getFirst();

        }
        System.out.println(bestExpectation);
    }

    private double getBestExpectation(int[] values, int[] weights, int capacity) {
        final int n = values.length;
        Value[] valuePerKilo = new Value[n];
        for (int i = 0; i < n; ++i) {
            valuePerKilo[i] = new Value((double) values[i] / (double) weights[i], weights[i]);
        }
        Arrays.sort(valuePerKilo);
        double result = 0.0d;
        int left = capacity;
        for (int i = 0; i < n && left > 0; ++i) {
            final int min = Math.min(valuePerKilo[i].weight, left);
            result += min * valuePerKilo[i].valuePerKilo;
            left -= min;
        }
        return result;
    }

    class Value implements Comparable<Value> {

        Value(double valuePerKilo, int weight) {
            this.valuePerKilo = valuePerKilo;
            this.weight = weight;
        }

        double valuePerKilo;
        int weight;

        @Override
        public int compareTo(Value o) {
            return Double.compare(o.valuePerKilo, this.valuePerKilo);
        }

        @Override
        public String toString() {
            return "Value{" +
                    "valuePerKilo=" + valuePerKilo +
                    ", weight=" + weight +
                    '}';
        }
    }

    class Node {

        Node(int capacityLeft, int currentValue, double bestExpectation) {
            this.level = 0;
            this.capacityLeft = capacityLeft;
            this.currentValue = currentValue;
            this.bestExpectation = bestExpectation;
            this.taken = new BitSet();
        }

        int capacityLeft;
        int currentValue;
        double bestExpectation;
        int level;
        BitSet taken;

        @Override
        public String toString() {
            return "Node{" +
                    "capacityLeft=" + capacityLeft +
                    ", currentValue=" + currentValue +
                    ", bestExpectation=" + bestExpectation +
                    '}';
        }
    }
}
