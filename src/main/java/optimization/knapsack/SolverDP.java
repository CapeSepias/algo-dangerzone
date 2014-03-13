package optimization.knapsack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * The class <code>Solver</code> is an implementation of a greedy algorithm to solve the knapsack problem.
 */
public class SolverDP {

    /**
     * The main class
     */
    public static void main(String[] args) {
        try {
            SolverDP s = new SolverDP();
            s.solve(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int[] values;
    int[] weights;
    int[][] solution;

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

        values = new int[items];
        weights = new int[items];
        solution = new int[capacity + 1][items + 1];
        for (int i = 0; i < capacity + 1; ++i) {
            Arrays.fill(solution[i], -1);
        }

        for (int i = 1; i < items + 1; i++) {
            String line = lines.get(i);
            String[] parts = line.split("\\s+");

            values[i - 1] = parseInt(parts[0]);
            weights[i - 1] = parseInt(parts[1]);
        }

        for (int itemNumber = 0; itemNumber < items + 1; ++itemNumber) {
            for (int j = 0; j < capacity + 1; ++j) {
                solution[j][itemNumber] = optimalSolution(j, itemNumber);
//                System.out.print(solution[j][itemNumber] + " ");
            }
//            System.out.println();
        }
        // prepare the solution in the specified output format
        System.out.println(solution[capacity][items] + " 0");
        final int[] taken = new int[items];
        int temp = capacity;
        for (int i = items; i >= 1; --i) {
            if (solution[temp][i] != solution[temp][i - 1]) {
                temp -= weights[i - 1];
                taken[i - 1] = 1;
            }
        }
        for (int i = 0; i < items; i++) {
            System.out.print(taken[i] + " ");
        }
        System.out.println("");
    }

    private int optimalSolution(int k, int j) {
        if (solution[k][j] != -1) {
            return solution[k][j];
        }
        if (j == 0) {
            return 0;
        } else if (weights[j - 1] <= k) {
            solution[k][j] = Math.max(optimalSolution(k, j - 1), values[j - 1] + optimalSolution(k - weights[j - 1], j - 1));
            return solution[k][j];
        } else {
            solution[k][j] = optimalSolution(k, j - 1);
            return solution[k][j];
        }

    }
}
