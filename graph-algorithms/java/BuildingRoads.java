import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class BuildingRoads {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   int m = sc.nextInt();

        List<List<Integer>> adj = new ArrayList<>();

        for(int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        List<Integer> cityLeaders = new ArrayList<>();

        for(int i = 0; i < m; i++) {
            int c1 = sc.nextInt();  int c2 = sc.nextInt();
            adj.get(c1).add(c2);
            adj.get(c2).add(c1);
        }

        boolean[] visited = new boolean[n + 1];

        for(int i = 1; i <= n; i++) {

            if(!visited[i]) {
                cityLeaders.add(i);
                travelThroughCityBFS(i, adj, visited);
            }

        }

        System.out.println(cityLeaders.size() - 1);

        for(int i = 0; i < cityLeaders.size() - 1; i++) {
            System.out.println(cityLeaders.get(i) + " " + cityLeaders.get(i + 1));
        }

    }

    private static void travelThroughCityDFS(int curr, List<List<Integer>> adj, boolean[] visited) {

        if(visited[curr]) return;
        visited[curr] = true;
        for(int neighbours : adj.get(curr)) {
            travelThroughCityDFS(neighbours, adj, visited);
        }
    }

    private static void travelThroughCityBFS(int curr, List<List<Integer>> adj, boolean[] visited) {

        Queue<Integer> queue = new LinkedList<>();
        queue.add(curr);

        while (!queue.isEmpty()) {

            int city = queue.poll();

            for (int neighbour : adj.get(city)) {
                if (!visited[neighbour]) {
                    visited[neighbour] = true;
                    queue.add(neighbour);
                }
            }

        }

        if(visited[curr]) return;
        visited[curr] = true;
        for(int neighbours : adj.get(curr)) {
            travelThroughCityDFS(neighbours, adj, visited);
        }
    }


}
