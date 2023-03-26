import java.util.Arrays;
import java.util.Scanner;

public class CollectingNumbers {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] arr = new int[n];

        for(int i = 0; i < n; i++) arr[i] = sc.nextInt();

        boolean[] minusOne = new boolean[n + 1];
        int cnt = 0;

        for(int num : arr) {
            if(!minusOne[num - 1]) {
                cnt++;
            }
            minusOne[num] = true;
        }

        System.out.println(cnt);



    }
}
