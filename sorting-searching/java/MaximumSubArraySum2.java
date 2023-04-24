import java.util.Scanner;

public class MaximumSubArraySum2 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   int a = sc.nextInt();   int b = sc.nextInt();
        long[] arr = new long[n];
        for(int i = 0; i < n; i++) arr[i] = sc.nextLong();

        long[] prefix = new long[n + 1];

        for(int i = 1; i <= n; i++) {
            prefix[i] = prefix[i-1] + arr[i-1];
        }

        long maxSum = Long.MIN_VALUE;

        for (int i = 0; i <= n - a; i++) {
            for (int j = i + a - 1; j <= Math.min(i + b - 1, n - 1); j++) {
                long currSum = prefix[j + 1] - prefix[i];
                maxSum = Math.max(currSum, maxSum);
            }
        }

        System.out.println(maxSum);

    }
}
