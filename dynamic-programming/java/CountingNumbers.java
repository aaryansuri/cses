import java.util.Arrays;
import java.util.Scanner;

public class CountingNumbers {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        long a = sc.nextLong();
        long b = sc.nextLong();

        String aa = Long.toString(a - 1);
        String bb = Long.toString(b);

        long[][][][] dp = new long[20][10][2][2];

        for (long[][][] ints : dp) {
            for (long[][] anInt : ints) {
                for (long[] value : anInt) {
                    Arrays.fill(value, -1);
                }
            }
        }

        long bW = ways(bb, bb.length(), -1, 1, 1, dp);

        for (long[][][] ints : dp) {
            for (long[][] anInt : ints) {
                for (long[] value : anInt) {
                    Arrays.fill(value, -1);
                }
            }
        }

        long aW = ways(aa, aa.length(), -1, 1, 1, dp);


        System.out.println(bW - aW);

    }

    private static long ways(String number, int n, int x,int leadingZeroes, int boundConstrained, long[][][][] dp) {

        if (n == 0) return 1;
        if(x != -1 && dp[n][x][leadingZeroes][boundConstrained] !=-1) {
            return dp[n][x][leadingZeroes][boundConstrained];
        }

        int lb = 0;
        int ub = boundConstrained == 1 ? (number.charAt(number.length() - n) - '0') : 9;

        long ans = 0;

        for(int digit = lb; digit <= ub; digit++) {

            if(digit == x && leadingZeroes == 0) continue;
            ans += ways(number, n - 1, digit, leadingZeroes == 1 && (digit == 0) ? 1 : 0, boundConstrained == 1 && (digit == ub) ? 1 : 0, dp);

        }

        if(x !=-1) {
            dp[n][x][leadingZeroes][boundConstrained] = ans;
        }




        return ans;

    }


}
