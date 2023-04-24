import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SubArraySum2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); long x = sc.nextLong();

        int[] arr = new int[n];

        for(int i = 0; i < n; i++) arr[i] = sc.nextInt();

        long currSum = 0;
        long subArrays = 0;

        Map<Long, Long> prefixes = new HashMap<>();
        prefixes.put(0L, 1L);

        for(int num : arr) {

            currSum += num;
            long prefix = currSum - x;

            if(prefixes.containsKey(prefix)) {
                subArrays += prefixes.get(prefix);
            }
            prefixes.merge(currSum, 1L, Long::sum);
        }

        System.out.println(subArrays);
    }
}
