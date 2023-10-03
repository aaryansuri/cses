import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class GridPaths {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();

        char[] path = input.toCharArray();

        boolean[][] visited = new boolean[8][8];

        System.out.println(dfs(1, 1, visited, path, -1, 's'));
    }

    private static int dfs(int x, int y, boolean[][] visited, char[] path, int curr, char step) {

        if(x == 7 && y == 1 && curr == 47) {
            return 1;
        }
        if(x == 7 && y == 1) return 0;
        if(x < 1 || x > 7 || y < 1 || y > 7) return 0;
        if(visited[x][y]) return 0;
        if(curr == 48) return 0;
        if(step != 's' && path[curr] != '?' && step != path[curr]) return 0;

        visited[x][y] = true;

        int ans = 0;

        ans += dfs(x + 1, y, visited, path, curr + 1, 'D');
        ans += dfs(x - 1, y, visited, path, curr + 1, 'U');
        ans += dfs(x, y - 1, visited, path, curr + 1, 'L');
        ans += dfs(x, y + 1, visited, path, curr + 1, 'R');

        visited[x][y] = false;

        return ans;
    }


}
