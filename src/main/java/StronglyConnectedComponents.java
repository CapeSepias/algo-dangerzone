import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Java version of Scala code
 */
public class StronglyConnectedComponents implements Runnable {

    PrintWriter out;
    BufferedReader br;
    StringTokenizer st;
    ArrayList<ArrayList<Integer>> g;
    ArrayList<ArrayList<Integer>> gr;
    ArrayList<Integer> order;
    ArrayList<Integer> components;
    boolean[] used;

    public static void main(String[] args) throws IOException {
        new Thread(new StronglyConnectedComponents()).start();
    }

    public String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return st.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("E:/file.txt")));
            out = new PrintWriter(new BufferedOutputStream(System.out));
            solve();
            out.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    void findOrder(int n) {
        Arrays.fill(used, false);
        order = new ArrayList<Integer>();
        for (int i = 0; i < n; ++i) {
            if (!used[i]) {
                dfs1(i);
            }
        }
    }

    void dfs1(int vertex) {
        used[vertex] = true;
        for (int i = 0; i < g.get(vertex).size(); ++i) {
            int v = g.get(vertex).get(i);
            if (!used[v]) {
                dfs1(v);
            }
        }
        order.add(vertex);
    }

    void dfs2(int vertex) {
        used[vertex] = true;
        components.add(vertex);
        for (int i = 0; i < gr.get(vertex).size(); ++i) {
            int v = gr.get(vertex).get(i);
            if (!used[v]) {
                dfs2(v);
            }
        }
    }

    ArrayList<Integer> findComponents(int n) {
        Arrays.fill(used, false);
        components = new ArrayList<Integer>();
        ArrayList<Integer> ans = new ArrayList<Integer>();
        for (int i = 0; i < n; ++i) {
            int vertex = order.get(n - i - 1);
            if (!used[vertex]) {
                dfs2(vertex);
                ans.add(components.size());
                components.clear();
            }
        }
        return ans;
    }

    public void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        g = new ArrayList<ArrayList<Integer>>(n);
        gr = new ArrayList<ArrayList<Integer>>(n);
        used = new boolean[n];
        for (int i = 0; i < n; ++i) {
            g.add(new ArrayList<Integer>());
            gr.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < m; ++i) {
            int a = nextInt() - 1;
            int b = nextInt() - 1;
            g.get(a).add(b);
            gr.get(b).add(a);
        }
        long begin = System.currentTimeMillis();
        findOrder(n);
        System.out.println("Find order in " + (System.currentTimeMillis() - begin) + " msec");
        begin = System.currentTimeMillis();
        ArrayList<Integer> comp = findComponents(n);
        Collections.sort(comp, Collections.reverseOrder());
        System.out.println("Find components in " + (System.currentTimeMillis() - begin) + " msec");
        System.out.print("Ans = ");
        for (int i = 0; i < Math.min(5, comp.size()); ++i) {
            System.out.print(comp.get(i) + ",");
        }
        System.out.println();

    }
}
