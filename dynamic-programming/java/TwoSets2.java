import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TwoSets2 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int sum = (n * (n + 1)) / 2;
        if (sum % 2 == 1) {
            System.out.println(0);
            return;
        }

        System.out.println(waysToSumIDP(sum / 2, n));

    }

    private static int waysToSum(int sum, int n) {

        if(sum == 0) return 1;
        if(sum < 0 || n < 0) return 0;

        return waysToSum(sum - n, n - 1) + waysToSum(sum, n - 1);
    }

    private static long waysToSumIDP(int sum, int n) {

        long[][] dp = new long[sum + 1][n + 1];
        dp[0][0] = 1;

        for(int i=1; i<=sum; i++) {
            for(int j=1; j<=n; j++) {
                dp[i][j] = dp[i][j-1];
                if(i-j >= 0) {
                    dp[i][j] += dp[i-j][j-1];
                }
                dp[i][j] %= 1000000007;
            }
        }

        return dp[sum][n];

    }
}
