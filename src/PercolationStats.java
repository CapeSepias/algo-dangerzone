/**
 * @author kperikov
 *         <p/>
 *         PercolationStats
 */
public class PercolationStats {

    private double mean;
    private double stddev;
    private double confLo;
    private double confHi;

    public PercolationStats(int N, int T) {
        if (N <= 0) throw new IllegalArgumentException("N must be bigger than 0");
        if (T <= 0) throw new IllegalArgumentException("T must be bigger than 0");
        double sum = 0.0d;
        double[] percent = new double[T];
        for (int t = 0; t < T; ++t) {
            if (t % 10 == 0) {
                System.out.println(t);
            }
            Percolation p = new Percolation(N);
            int count = 0;
            while (!p.percolates()) {
                int row = StdRandom.uniform(N) + 1;
                int column = StdRandom.uniform(N) + 1;
                if (p.isOpen(row, column)) {
                    p.open(row, column);
                    count++;
                }
            }
            percent[t] = (double) count / (double) (N * N);
            sum += percent[t];
        }
        mean = sum / (double) T;
        sum = 0.0d;
        for (int i = 0; i < T; ++i) {
            sum += (percent[i] - mean) * (percent[i] - mean);
        }
        stddev = sum / (double) (T - 1);
        confLo = mean - (1.96d * stddev / Math.sqrt(T));
        confHi = mean + (1.96d * stddev / Math.sqrt(T));
    }   // perform T independent computational experiments on an N-by-N grid

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }  // test client, described below

    public double mean() {
        return this.mean;
    } // sample mean of percolation threshold

    public double stddev() {
        return this.stddev;
    }// sample standard deviation of percolation threshold

    public double confidenceLo() {
        return this.confLo;
    } // returns lower bound of the 95% confidence interval

    public double confidenceHi() {
        return this.confHi;
    } // returns upper bound of the 95% confidence interval
}
