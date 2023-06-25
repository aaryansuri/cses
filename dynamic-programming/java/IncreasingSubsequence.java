import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IncreasingSubsequence {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] arr = new int[n];

        for(int i = 0; i < n; i++) arr[i] = sc.nextInt();

        System.out.println(longestSubsequenceIDPB(arr));

    }

    private static int longestSubsequenceIDP(int[] arr) {

        int[] dp = new int[arr.length];
        Arrays.fill(dp, 1);

        for(int i = 1; i < arr.length; i++) {
            for(int j = 0; j < i; j++) {
                if(arr[j] < arr[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }


        return Arrays.stream(dp).max().getAsInt();

    }

    private static int longestSubsequenceIDPB(int[] arr) {

        List<Integer> sub = new ArrayList<>();

        for(int num : arr) {
            if(sub.isEmpty() || sub.get(sub.size() - 1) < num) {
                sub.add(num);
            } else {
                int greaterThanNum = Collections.binarySearch(sub, num);
                if (greaterThanNum < 0) {
                    greaterThanNum = -(greaterThanNum + 1);
                }
                sub.set(greaterThanNum, num);
            }

        }

        return sub.size();
    }

    private static int longestSubsequence(int[] arr) {
        int max = 0;

        for(int i = 0; i < arr.length; i++) {
            max = Math.max(longestSubsequence(arr, i), max);
        }

        return max;
    }


    private static int longestSubsequence(int[] arr, int n) {

        if(n < 0) return 0;

        int longest = 1;

        for(int j = 0; j < n; j++) {
            if(arr[j] < arr[n]) {
                longest = Math.max(longest, 1 + longestSubsequence(arr, j));
            }
        }

        return longest;
    }



}
