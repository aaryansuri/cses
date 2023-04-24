import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CountingTowers {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        StringBuilder sb = new StringBuilder();

        int[] input = new int[n];

        for(int i = 0; i < n; i++) {
            input[i] = sc.nextInt();
        }

        OptionalInt max = Arrays.stream(input).max();


        if(max.isPresent()) {
            long[][] dp = towers(max.getAsInt());
            for(int num : input) {
                System.out.println((dp[num][1] + dp[num][0])% 1000000007);
            }
        }

    }

    private static long[][] towers(int n) {

        long[][] dp = new long[n+1][2];

        dp[1][1] = 1;
        dp[1][0] = 1;

        for(int i = 2; i <= n; i++) {
            dp[i][0] = (dp[i-1][1] + 4 * dp[i-1][0]) % 1000000007;
            dp[i][1] = (dp[i-1][0] + 2 * dp[i-1][1]) % 1000000007;
        }

        return dp;
    }

}
