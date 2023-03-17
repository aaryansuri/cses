import java.util.Arrays;
import java.util.Scanner;

public class StickLengths {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); sc.nextLine();

        long[] arr = Arrays.stream(sc.nextLine().split(" ")).mapToLong(Long::parseLong).toArray();

        Arrays.sort(arr);
        long median;
        int mid = n/2;

        if((n & 1) == 0) {
            median = (arr[mid] + arr[mid-1])/2;
        } else {
            median = arr[mid];
        }

        long cost = 0;

        for(long num : arr) {
            cost += Math.abs(num - median);
        }

        System.out.println(cost);

    }
}
