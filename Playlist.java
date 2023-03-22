import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Playlist {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Map<Long, Integer> location = new HashMap<>();

        int n = sc.nextInt();
        long[] arr = new long[n];

        for(int i = 0; i < n; i++) arr[i] = sc.nextLong();

        long ans = 0;
        long j = 0;


        for(int i = 0; i < n; i++) {
            long num = arr[i];
            j = Math.max(j, location.getOrDefault(num, 0));
            ans = Math.max(ans, i - j + 1);
            location.put(num, i + 1);
        }

        System.out.println(ans);
    }
}
