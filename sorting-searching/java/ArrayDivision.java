import java.util.Random;
import java.util.Scanner;

public class ArrayDivision {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();   int k = sc.nextInt();
        int[] arr = new int[n];
        long sum = 0; long max = Long.MIN_VALUE;

        for(int i = 0; i < n; i++){
            arr[i] = sc.nextInt();
            sum += arr[i];
            max = Math.max(max, arr[i]);
        }

        long low = max; long high = sum;

        while (low <= high) {

            long mid = (low + high) / 2;
            if(can(arr, mid, k)) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }

        }

        System.out.println(low);

    }

    private static boolean can(int[] arr, long maxSum, int k) {

        long sum = 0;
        int subArrays = 1;

        for(int num : arr) {

            sum += num;
            if(sum > maxSum) {
                sum = num;
                subArrays++;
            }

        }

        return subArrays <= k;
    }
}
