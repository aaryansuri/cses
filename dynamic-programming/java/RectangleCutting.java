import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RectangleCutting {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Arrays.stream(cache).forEach(row -> Arrays.fill(row, -1));
        int a = sc.nextInt();
        int b = sc.nextInt();

        System.out.println(idpMinCuts(a, b));
    }

    private static final int[][] cache = new int[501][501];

    private static int minCuts(int a, int b) {

        if(a == b) return 0;
        if(cache[a][b] != -1) return cache[a][b];

        int minVertical = Integer.MAX_VALUE;

        for(int i = 1; i <= a - 1; i++) {
            minVertical = Math.min(minVertical, 1 + minCuts(i, b) + minCuts( a - i, b));
        }

        int minHorizontal = Integer.MAX_VALUE;
        for(int i = 1; i <= b - 1; i++) {
            minHorizontal = Math.min(minHorizontal, 1 + minCuts(a, i) + minCuts( a, b - i));
        }

        cache[a][b] = Math.min(minVertical, minHorizontal);

        return Math.min(minVertical, minHorizontal);
    }


    private static int idpMinCuts(int a, int b) {

        int[][] dp = new int[a+1][b+1];

        for(int i = 1; i <= a; i++) {
            for(int j = 1; j <= b; j++) {
                if(i != j) {
                    int minCuts = Integer.MAX_VALUE;
                    // vertical cuts
                    for(int k = 1; k <= i-1; k++) {
                        minCuts = Math.min(minCuts, 1 + dp[k][j] + dp[i-k][j]);
                    }
                    // horizontal cuts
                    for(int k = 1; k <= j-1; k++) {
                        minCuts = Math.min(minCuts, 1 + dp[i][k] + dp[i][j-k]);
                    }
                    dp[i][j] = minCuts;
                }
            }
        }

        return dp[a][b];
    }

}
