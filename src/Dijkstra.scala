/**
 * @author kperikov
 *
 *         Library of different Dijsktra implementation
 */
object Dijkstra {


  /**
   * Slow Dijkstra implementation O(n * m)
   * int n;
	... чтение n ...
	vector < vector < pair<int,int> > > g (n);
	... чтение графа ...
	int s = ...; // стартовая вершина

	vector<int> d (n, INF),  p (n);
	d[s] = 0;
	vector<char> u (n);
	for (int i=0; i<n; ++i) {
		int v = -1;
		for (int j=0; j<n; ++j)
			if (!u[j] && (v == -1 || d[j] < d[v]))
				v = j;
		if (d[v] == INF)
			break;
		u[v] = true;

		for (size_t j=0; j<g[v].size(); ++j) {
			int to = g[v][j].first,
				len = g[v][j].second;
			if (d[v] + len < d[to]) {
				d[to] = d[v] + len;
				p[to] = v;
			}
		}
	}
   */
  def solveDijkstraSlow(): Unit = {

  }

  /**
   * Dijkstra algorithm, with fast data structure (heap). Use Scala standard library
   */
  def solveDijkstraStandardHeap(): Unit = {

  }

  /**
   * Dijkstra algorithm, with fast data structure (heap). Write own heap implementation
   */
  def solveDijkstraSelfWrittenHeap(): Unit = {

  }

  def main(args: Array[String]) = {
       val n =
  }

}
