import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CountingTilings {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        dp = new int[m + 1][1 << 11];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        System.out.println(solve(1, 0, m, n));
    }

    static int[][] dp;

    static int solve(int col, int mask, int m, int n) {
        // BASE CASE
        if (col == m + 1) {
            return mask == 0 ? 1 : 0;
        }

        if (dp[col][mask] != -1)
            return dp[col][mask];

        int answer = 0;
        List<Integer> nextMasks = new ArrayList<>();
        generateNextMasks(mask, 1, 0, n, nextMasks);
        System.out.println();

        for (int nextMask : nextMasks) {
            answer = (answer + solve(col + 1, nextMask, m, n)) % 1000000007;
        }

        return dp[col][mask] = answer;
    }

    static void generateNextMasks(int currentMask, int i, int nextMask, int n, List<Integer> nextMasks) {


        if (i == n + 1) {
            nextMasks.add(nextMask);
            System.out.println(currentMask);
            System.out.println(nextMasks);
            return;
        }

        if ((currentMask & (1 << i)) != 0)
            generateNextMasks(currentMask, i + 1, nextMask, n, nextMasks);

        if (i != n && (currentMask & (1 << i)) == 0 && (currentMask & (1 << (i + 1))) == 0)
            generateNextMasks(currentMask, i + 2, nextMask, n, nextMasks);

        if ((currentMask & (1 << i)) == 0)
            generateNextMasks(currentMask, i + 1, nextMask | (1 << i), n, nextMasks);

    }

}
