import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Labyrinth {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   int m = sc.nextInt();
        sc.nextLine();

        char[][] map = new char[n][m];

        int aY = -1;
        int aX = -1;

        for(int i = 0; i < n; i++) {
            String input = sc.nextLine();
            map[i] = input.toCharArray();
            if(input.contains("A")) {
                aY = input.indexOf("A");
                aX = i;
            }
        }

        bfs(map, aX, aY);

    }

    private static void bfs(char[][] map, int x, int y) {

        int[] dx = new int[]{0, 0, -1, 1};
        int[] dy = new int[]{1, -1, 0, 0};
        char[] moves = new char[]{'R', 'L', 'U', 'D'};

        Queue<int[]> q = new LinkedList<>();

        char[][] path = new char[map.length][map[0].length];

        for(char[] p : path) {
            Arrays.fill(p, 'x');
        }

        q.add(new int[]{x, y});
        int d = 0;


        List<Character> shortestPath = new ArrayList<>();

        while (!q.isEmpty()) {

            int size = q.size();
            d++;

            while (size --> 0) {
                int[] cell = q.poll();
                int currX = cell[0];
                int currY = cell[1];


                for(int i = 0; i < 4; i++) {
                    int nextX = currX + dx[i];
                    int nextY = currY + dy[i];

                    if(nextX < 0 || nextX == map.length || nextY < 0 || nextY == map[0].length) continue;

                    if(map[nextX][nextY] == '.') {
                        map[nextX][nextY] = 'v';
                        path[nextX][nextY] = moves[i];
                        q.add(new int[]{nextX, nextY});
                    }

                    if(map[nextX][nextY] == 'B') {

                        System.out.println("YES");
                        System.out.println(d);

                        path[nextX][nextY] = moves[i];

                        int goBackX = nextX; int goBackY = nextY;
                        while (goBackX != x || goBackY != y) {

                            if(path[goBackX][goBackY] == 'R') {
                                shortestPath.add(path[goBackX][goBackY]);
                                goBackY--;
                            }

                            if(path[goBackX][goBackY] == 'L') {
                                shortestPath.add(path[goBackX][goBackY]);
                                goBackY++;
                            }

                            if(path[goBackX][goBackY] == 'U') {
                                shortestPath.add(path[goBackX][goBackY]);
                                goBackX++;
                            }

                            if(path[goBackX][goBackY] == 'D') {
                                shortestPath.add(path[goBackX][goBackY]);
                                goBackX--;
                            }


                        }

                        Collections.reverse(shortestPath);
                        System.out.println(shortestPath.stream().map(Object::toString).collect(
                            Collectors.joining()));
                        return;
                    }
                }


            }


        }

        System.out.println("NO");

    }
}
