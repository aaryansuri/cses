import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class CountingRooms {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   int m = sc.nextInt();
        sc.nextLine();

        char[][] map = new char[n][m];

        for(int i = 0; i < n; i++) {
            String input = sc.nextLine();
            map[i] = input.toCharArray();
        }

        System.out.println(countRoomsBFS(map, n, m));
    }

    private static int countRoomsBFS(char[][] map, int n, int m) {

        int rooms = 0;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == '.') {
                    rooms++;
                    map[i][j] = 'v';

                    Queue<int[]> queue = new LinkedList<>();
                    queue.offer(new int[]{i, j});

                    while (!queue.isEmpty()) {
                        int[] cell = queue.poll();
                        int x = cell[0];
                        int y = cell[1];

                        for (int k = 0; k < 4; k++) {
                            int nx = x + dx[k];
                            int ny = y + dy[k];

                            if (nx >= 0 && nx < n && ny >= 0 && ny < m && map[nx][ny] == '.') {
                                map[nx][ny] = 'v';
                                queue.offer(new int[]{nx, ny});
                            }
                        }
                    }
                }
            }
        }

        return rooms;
    }
}
