
/**
 * @author kperikov
 *         <p/>
 *         Percolation problem
 */
public class Percolation {

    private boolean[][] grid;
    private int N;
    private WeightedQuickUnionUF weightedQuickUnionUF;

    public Percolation(int N) {
        this.N = N;
        weightedQuickUnionUF = new WeightedQuickUnionUF(N * N + 1);
        for (int i = 1; i <= N; ++i) {
            weightedQuickUnionUF.union(0, i);
        }
        for (int i = N * (N - 1); i <= N * N; ++i) {
            weightedQuickUnionUF.union(N * N + 1, i);
        }
        grid = new boolean[N][N];

    }          // create N-by-N grid, with all sites blocked

    public void open(int i, int j) {
        if (i <= 0 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N) throw new IndexOutOfBoundsException("column index j out of bounds");
        grid[i][j] = true;
        weightedQuickUnionUF.union(i, j);
    }      // open site (row i, column j) if it is not already

    public boolean isOpen(int i, int j) {
        if (i <= 0 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N) throw new IndexOutOfBoundsException("column index j out of bounds");
        return !grid[i][j];
    }   // is site (row i, column j) open?

    public boolean isFull(int i, int j) {
        if (i <= 0 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N) throw new IndexOutOfBoundsException("column index j out of bounds");
        return grid[i][j];
    }   // is site (row i, column j) full?

    public boolean percolates() {
        return weightedQuickUnionUF.connected(0, N * N + 1);
    }           // does the system percolate?
}
