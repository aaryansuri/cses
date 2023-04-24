import java.util.Scanner;

public class ArrayDescription {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   int m = sc.nextInt();

        int[] arr = new int[n];

        for(int i = 0; i < n; i++) arr[i] = sc.nextInt();

        System.out.println(numberOfArrays(arr, m));

    }

    private static long numberOfArrays(int[] arr, int m) {

        int n = arr.length;

        long[][] dp = new long[n + 1][m + 2];

        if(arr[0] == 0) {
            for(int j = 1; j <= m; j++) {
                dp[1][j] = 1;
            }
        } else {
            dp[1][arr[0]] = 1;
        }


        for(int i = 2; i <= n; i++) {
            int currEle = arr[i-1];
            if(currEle == 0) {
                for(int j = 1; j <= m; j++) {
                    dp[i][j] += dp[i-1][j-1] + dp[i-1][j] + dp[i-1][j+1];
                    dp[i][j] %= 1000000007;
                }
            } else {
                dp[i][currEle] = (dp[i-1][currEle-1] + dp[i-1][currEle] + dp[i-1][currEle+1]) % 1000000007;
            }
        }

        long arrays = 0;

        for(int j = 1; j <= m; j++) {
            arrays += dp[n][j];
            arrays %= 1000000007;
        }

        return arrays;
    }


}
