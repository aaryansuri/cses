import java.util.Scanner;

public class ChristmasParty {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        long[] dp = new long[n + 2];

        dp[1] = 0;  dp[2] = 1;
        int mod = 1000000007;

        for(int i = 3; i <= n; i++) {
            dp[i] = ((i - 1) * (dp[i - 1] + dp[i - 2]) % mod) % mod;
        }

        System.out.println(dp[n] % mod);
    }


}
