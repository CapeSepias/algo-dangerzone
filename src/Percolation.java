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
        weightedQuickUnionUF = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 1; i <= N; ++i) {
            weightedQuickUnionUF.union(0, i);
        }
        for (int i = N * (N - 1); i <= N * N; ++i) {
            weightedQuickUnionUF.union(N * N + 1, i);
        }
        grid = new boolean[N][N];

    }          // create N-by-N grid, with all sites blocked

    public void open(int i, int j) {
        int[] dx = new int[]{-1, 1, 0, 0};
        int[] dy = new int[]{0, 0, -1, 1};
        if (i <= 0 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N) throw new IndexOutOfBoundsException("column index j out of bounds");
        if (isBlocked(i, j)) {
            grid[i - 1][j - 1] = true;
            for (int k = 0; k < 4; ++k) {
                if (i + dx[k] > 0 && i + dx[k] <= N && j + dy[k] > 0 && j + dy[k] <= N && isOpen(i + dx[k], j + dy[k])) {
                    weightedQuickUnionUF.union(transform(i, j), transform(i + dx[k], j + dy[k]));
                }
            }
        }
    }      // open site (row i, column j) if it is not already

    private int transform(int x, int y) {
        return (x - 1) * N + y;
    }

    public boolean isOpen(int i, int j) {
        if (i <= 0 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N) throw new IndexOutOfBoundsException("column index j out of bounds");
        return grid[i - 1][j - 1];
    }   // is site (row i, column j) open?

    public boolean isFull(int i, int j) {
        if (i <= 0 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N) throw new IndexOutOfBoundsException("column index j out of bounds");
        if (isOpen(i, j)) {
            return weightedQuickUnionUF.connected(0, transform(i, j));
        } else {
            return false;
        }
    }   // is site (row i, column j) full?

    public boolean percolates() {
        return weightedQuickUnionUF.connected(0, N * N + 1);
    }           // does the system percolate?

    public boolean isBlocked(int i, int j) {
        if (i <= 0 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N) throw new IndexOutOfBoundsException("column index j out of bounds");
        return !grid[i - 1][j - 1];
    }
}
