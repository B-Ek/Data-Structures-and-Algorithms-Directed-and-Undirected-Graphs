/*
 * Giray Berk Kuþhan 
 * Section 4
 * 10889878942
 * Homework 2 - Q2
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Q2 {
	

	static int xorCost(int a, int b) {
		return a ^ b;
	}
	static int[] prnt;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int t = 0;
		int cases = Integer.parseInt(br.readLine().trim());
		while (t < cases) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());

			st = new StringTokenizer(br.readLine());
			int r1 = Integer.parseInt(st.nextToken()), c1 = Integer.parseInt(st.nextToken());
			int r2 = Integer.parseInt(st.nextToken()), c2 = Integer.parseInt(st.nextToken());

			int[][] grid = new int[N][M];

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				int j = 0;
				do {
					grid[i][j] = Integer.parseInt(st.nextToken());
					j++;
				} while (j < M);

			}

			int result = minTripCost(grid, N, M, r1, c1, r2, c2);
			System.out.println(result);

			t++;
		}

	}
	/*
	 * Multiple test scenarios are input into this code. It reads the grid's size,
	 * beginning and finishing positions, and integer values to fill the grid with
	 * for each test case. It uses a specific function to determine the minimum
	 * travel cost and prints the result. The technique is then repeated for each
	 * test case.
	 */

	static int minTripCost(int[][] grd, int N, int M, int r1, int c1, int r2, int c2) {
		int vrtc = N * M;
		prnt = new int[vrtc];
		Arrays.fill(prnt, -1);

		List<Edge> e = new ArrayList<>();

		int i = 0;
		while (i < N) {
			int j = 0;
			while (j < M - 1) {
				int u = i * M + j;
				int v = u + 1;
				int weight = xorCost(grd[i][j], grd[i][j + 1]);
				e.add(new Edge(u, v, weight));
				j++;
			}
			i++;
		}

		i = 0;
		while (i < N - 1) {
			int j = 0;
			while (j < M) {
				int u = i * M + j;
				int v = (i + 1) * M + j;
				int weight = xorCost(grd[i][j], grd[i + 1][j]);
				e.add(new Edge(u, v, weight));
				j++;
			}
			i++;
		}

		int src = (r1 - 1) * M + (c1 - 1);
		int dest = (r2 - 1) * M + (c2 - 1);

		return kruskal(e, vrtc, src, dest);
	}
	/*
	 * On the basis of the specified dimensions and starting/ending points, the
	 * minTripCost method determines the minimal trip cost in a grid. By repeatedly
	 * traversing the grid, it creates a list of edges and adds horizontal and
	 * vertical edges with matching weights. The next step is to discover the grid's
	 * minimum-cost route between the source and the destination using the Kruskal's
	 * algorithm, which is implemented in the kruskal function.
	 */

	static int find(int u) {
		switch (prnt[u]) {
		case -1:
			return u;
		default:
			return prnt[u] = find(prnt[u]);
		}
	}

	static class Edge {
		int u, v, weight;

		Edge(int u, int v, int weight) {
			this.u = u;
			this.v = v;
			this.weight = weight;
		}
	}

	static void union(int u, int v) {
		int pu = find(u);
		int pv = find(v);
		prnt[pu] = pv;
	}
	/*
	 * The find function uses path compression to iteratively locate the
	 * representative of a given node within a disjoint-set structure. With source,
	 * destination, and weight characteristics, the Edge class represents an edge in
	 * a graph. By designating one representative as the parent of the other, the
	 * union function combines two distinct sets.
	 */

	static int kruskal(List<Edge> edges, int vertices, int src, int dest) {
		int cost = 0;
		int count = 0;

		Collections.sort(edges, (a, b) -> a.weight - b.weight); // sorting with ascending order for edges.

		int i = 0;
		do {
			Edge edge = edges.get(i);
			if (find(edge.u) != find(edge.v)) {
				union(edge.u, edge.v);
				cost += edge.weight;
				count++;
			}

			if (count == vertices - 1 && find(src) == find(dest)) {
				break; // exit the loop
			}

			i++;
		} while (i < edges.size());

		return cost;
	}
	/*
	 * The Kruskal's approach to locate the smallest spanning tree in a graph is
	 * implemented in this code. If no cycle forms as it goes through the sorted
	 * edges, it adds them to the tree. When the source and destination vertices are
	 * connected and the minimum spanning tree is finished, the algorithm ends,
	 * returning the tree's total cost.
	 */

}
