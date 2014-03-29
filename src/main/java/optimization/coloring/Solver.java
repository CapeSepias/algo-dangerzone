package optimization.coloring;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class <code>Solver</code>
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


        BufferedReader input = new BufferedReader(new FileReader(fileName));
        final String[] in = input.readLine().split(" ");
        final int N = Integer.parseInt(in[0]);
        final int E = Integer.parseInt(in[1]);

        final List<List<Integer>> graph = new ArrayList<>(N);
        for (int i = 0; i < N; ++i) {
            graph.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < E; ++i) {
            final String[] vertexes = input.readLine().split(" ");
            final int s = Integer.parseInt(vertexes[0]);
            final int f = Integer.parseInt(vertexes[1]);
            graph.get(s).add(f);
            graph.get(f).add(s);
        }




    }
}
