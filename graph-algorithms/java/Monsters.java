import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Monsters {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   int m = sc.nextInt();
        sc.nextLine();

        char[][] labyrinth = new char[n][m];

        for(int i = 0; i < n; i++) {
            String in = sc.nextLine();
            labyrinth[i] = in.toCharArray();
        }
        goalPossibleAndPrint(labyrinth, n, m);

    }


    private static void print2D(int[][] array2D) {
        Arrays.stream(array2D).forEach(a1d -> System.out.println(Arrays.toString(a1d)));
    }

    private static void print2D(char[][] array2D) {
        Arrays.stream(array2D).forEach(a1d -> System.out.println(Arrays.toString(a1d)));
    }

    private static void goalPossibleAndPrint(char[][] labyrinth, int n, int m){

        Queue<int[]> monsters = new LinkedList<>();
        Queue<int[]> me = new LinkedList<>();
        char[][] path = new char[n][m];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(labyrinth[i][j] == 'A'){
                    me.add(new int[]{i, j});
                    path[i][j] = 'S';
                }
                if(labyrinth[i][j] == 'M') monsters.add(new int[]{i, j});
            }
        }

        int[] dx = {1 ,-1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        char[] direction = {'D', 'U', 'R', 'L'};



        while (!me.isEmpty()) {

            int monsterSteps = monsters.size();

            while (monsterSteps --> 0) {
                int[] currMonster = monsters.poll();

                for(int i = 0; i < 4; i++) {

                    int nextMonsterMoveX = currMonster[0] + dx[i];
                    int nextMonsterMoveY = currMonster[1] + dy[i];

                    if(nextMonsterMoveX < 0 || nextMonsterMoveX == n || nextMonsterMoveY < 0 || nextMonsterMoveY == m) continue;
                    if(labyrinth[nextMonsterMoveX][nextMonsterMoveY] == '#') continue;
                    if(labyrinth[nextMonsterMoveX][nextMonsterMoveY] == 'M') continue;
                    monsters.add(new int[]{nextMonsterMoveX, nextMonsterMoveY});
                    labyrinth[nextMonsterMoveX][nextMonsterMoveY] = 'M';

                }
            }

            int mineSteps = me.size();

            while (mineSteps --> 0) {
                int[] currMe = me.poll();

                if(currMe[0] == 0 || currMe[0] == n - 1 || currMe[1] == 0 || currMe[1] == m - 1) {
                    System.out.println("YES");
                    printPath(path, currMe[0], currMe[1]);
                    return;
                }

                for(int i = 0; i < 4; i++) {

                    int nextMineMoveX = currMe[0] + dx[i];
                    int nextMineMoveY = currMe[1] + dy[i];

                    if(nextMineMoveX < 0 || nextMineMoveX == n || nextMineMoveY < 0 || nextMineMoveY == m) continue;
                    if(labyrinth[nextMineMoveX][nextMineMoveY] == '#') continue;
                    if(labyrinth[nextMineMoveX][nextMineMoveY] == 'M') continue;
                    if(labyrinth[nextMineMoveX][nextMineMoveY] == 'A') continue;

                    path[nextMineMoveX][nextMineMoveY] = direction[i];
                    labyrinth[nextMineMoveX][nextMineMoveY] = '#';
                    me.add(new int[]{nextMineMoveX, nextMineMoveY});

                }
            }

        }

        System.out.println("NO");


    }

    private static void printPath(char[][] paths, int x, int y) {

        int currX = x; int currY = y;
        List<Character> path = new ArrayList<>();

        while (paths[currX][currY] != 'S') {
            char curr = paths[currX][currY];
            path.add(curr);
            if(curr == 'R') {
                currY -= 1;
            }
            if(curr == 'L') {
                currY += 1;
            }
            if(curr == 'U') {
                currX += 1;
            }
            if(curr == 'D') {
                currX -= 1;
            }
        }

        Collections.reverse(path);
        System.out.println(path.size());
        System.out.println(path.stream().map(Object::toString).collect(
            Collectors.joining()));

    }

}
