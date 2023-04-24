import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SubArrayDivisiblty {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        long[] arr = new long[n];
        for(int i = 0; i < n; i++) arr[i] = sc.nextLong();


        long currSum = 0;
        for(int i = 0; i < n; i++) {
            currSum += arr[i];
            arr[i] = (currSum % n + n) % n;
        }

        Map<Long, Long> map = new HashMap<>();
        map.put(0L, 1L);
        long subArrays = 0;

        for(long num : arr) {

            subArrays += map.getOrDefault(num, 0L);
            map.merge(num, 1L, Long::sum);
        }


        System.out.println(subArrays);
    }
}
