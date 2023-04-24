import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SubArrayDistinctValues {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); int k = sc.nextInt();

        int[] arr = new int[n];

        for(int i = 0; i < n; i++) arr[i] = sc.nextInt();

        Map<Integer, Integer> distincts = new HashMap<>();

        int i = 0; int j = 0;
        long cnt = 0;

        while (j < n) {
            distincts.merge(arr[j],1, Integer::sum);

            while (distincts.size() > k) {
                distincts.put(arr[i], distincts.get(arr[i]) - 1);
                if(distincts.get(arr[i]) == 0) distincts.remove(arr[i]);
                i++;
            }
            cnt += j - i + 1;
            j++;
        }

        System.out.println(cnt);

    }
}
