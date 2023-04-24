import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class MaximumSubArraySum2Opti {

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

        TreeMap<Long, Long> map = new TreeMap<>();

        map.put(0L, 1L);
        for(int i = a; i <= n; i++) {
            if(i + b <= n) {
                map.merge(prefix[i] , 1L, Long::sum);
            }
            map.put(prefix[i - a], map.get(prefix[i - a]) - 1);
            if(map.get(prefix[i-a]) == 0) map.remove(prefix[i - a]);
            maxSum = Math.max(maxSum, map.lastKey() - prefix[i - a]);
        }


        System.out.println(maxSum);

    }
}
