import java.util.Arrays;
import java.util.Scanner;

public class MinCoinSum {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] arr = new int[n];

        for(int i = 0; i < n; i++) arr[i] = sc.nextInt();

        Arrays.sort(arr);

        long smallest = 1;

        for(int num : arr) {
            if(num > smallest) break;
            smallest += num;
        }

        System.out.println(smallest);

    }
}
