import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class SumOfThreeValues {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();  long x = sc.nextLong();
        int[] arr = new int[n];
        Integer[] pos = new Integer[n];

        for(int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
            pos[i] = i;
        }

        Arrays.sort(pos, Comparator.comparingInt(i -> arr[i]));


        for(int i = 0; i < n - 2; i++) {

            int j = i + 1;  int k = n - 1;

            while (j < k) {
                long currSum = arr[pos[i]] + arr[pos[j]] + arr[pos[k]];
                if(currSum == x){
                    System.out.println((pos[i] + 1) + " " + (pos[j] + 1) + " " + (pos[k] + 1));
                    return;
                } else if(currSum > x) k--;
                 else j++;
            }

        }

        System.out.println("IMPOSSIBLE");

    }

}
