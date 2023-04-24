import java.util.Scanner;

public class GridPaths {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();

        boolean[][] grid = new boolean[n][n];

        for(int i = 0; i < n; i++) {
            String row = sc.nextLine();
            for(int j = 0; j < n; j++) {
                char c = row.charAt(j);
                grid[i][j] = c == '.';
            }
        }



        int[][] dp = new int[n][n];
        dp[0][0] = !grid[0][0] ? 0 : 1;


        for(int i = 1; i < n; i++) {
            dp[i][0] = !grid[i][0] ? 0 : dp[i-1][0];
            dp[i][0] = dp[i][0] % 1000000007;
        }

        for(int i = 1; i < n; i++) {
            dp[0][i] = !grid[0][i] ? 0 : dp[0][i-1];
            dp[0][i] = dp[0][i] % 1000000007;
        }

        for(int i = 1; i < n; i++) {
            for(int j = 1; j < n; j++) {
                dp[i][j] = !grid[i][j] ? 0 : dp[i-1][j] + dp[i][j-1];
                dp[i][j] = dp[i][j] % 1000000007;
            }
        }

        System.out.println(dp[n-1][n-1]);

    }
}
