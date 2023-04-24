import java.util.Arrays;
import java.util.Scanner;

public class MinimizingCoins {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   int x = sc.nextInt();

        int[] coins = new int[n];

        for(int i = 0; i < n; i++) coins[i] = sc.nextInt();

        System.out.println(minCoinsDp(coins, x));
    }

    public static int minCoinsRecursive(int[] coins, int x) {

        if(x < 0) return -1;
        if(x == 0) return 0;

        int minCoins = Integer.MAX_VALUE;

        for(int coin : coins) {
            int min = minCoinsRecursive(coins, x - coin);
            if(min != -1) {
                minCoins = Math.min(minCoins, min + 1);
            }
        }

        return minCoins == Integer.MAX_VALUE ? - 1 : minCoins;
    }

    public static int minCoinsDp(int [] coins, int x) {

        int[] dp = new int[x + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;


        for(int i = 1; i <= x; i++) {
            for(int coin : coins) {
                if(i - coin >= 0 && dp[i - coin] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(1 + dp[i-coin], dp[i]);
                }
            }

        }

        return dp[x] == Integer.MAX_VALUE ? - 1 : dp[x];

    }
}
