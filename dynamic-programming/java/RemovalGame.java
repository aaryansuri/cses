import java.util.Scanner;

public class RemovalGame {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] arr = new int[n];

        for(int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();;
        }

        System.out.println(maxScore(arr));
    }

    static class Pair<K, V> {
        K first; V second;

        public Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return first + "-" + second;
        }
    }

    private static long maxScore(int[] arr) {

        int n = arr.length;

        Pair<Long, Long>[][] dp = new Pair[n][n];

        for(int i = 0; i < n; i++) {
            dp[i][i] = new Pair<>((long) arr[i], 0L);
        }

        for(int d = 1; d < n; d++) {
            for(int i = 0; i < n - d; i++) {
                int l = i;
                int r = i + d;

                if(arr[l] + dp[l + 1][r].second >= arr[r] + dp[l][r - 1].second) {
                    dp[l][r] = new Pair<>(arr[l] + dp[l + 1][r].second, dp[l + 1][r].first);
                } else {
                    dp[l][r] = new Pair<>(arr[r] + dp[l][r - 1].second, dp[l][r - 1].first);
                }

            }

        }
        return dp[0][n - 1].first;
    }
}
