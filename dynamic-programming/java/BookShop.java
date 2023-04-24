import java.util.Arrays;
import java.util.Scanner;

public class BookShop {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   int x = sc.nextInt();

        int[] prices = new int[n]; int[] pages = new int[n];

        for(int i = 0; i < n; i++) {
            prices[i] = sc.nextInt();
        }

        for(int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        System.out.println(maxPagesIdp(prices, pages, x));
    }


    private static int maxPagesIdp(int[] prices, int[] pages, int x) {

        int n = prices.length;
        int[][] dp = new int[n + 1][x + 1];

        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= x; j++) {
                dp[i][j] = dp[i - 1][j];
                if(j - prices[i - 1] >= 0) {
                    dp[i][j] = Math.max(dp[i][j], pages[i - 1] + dp[i - 1][j - prices[i - 1]]);
                }
            }
        }
        return dp[n][x];
    }


}
