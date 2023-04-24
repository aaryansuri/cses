import java.util.Scanner;

public class SubArraySum1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); long x = sc.nextLong();

        int[] arr = new int[n];

        for(int i = 0; i < n; i++) arr[i] = sc.nextInt();

        int i = 0; int j = 0;


        long currSum = 0;
        int subArrays = 0;

        while (j < n) {

            currSum += arr[j];

            while (currSum >= x) {
                if(currSum == x) subArrays++;
                currSum -= arr[i];
                i++;
            }
            j++;

        }

        System.out.println(subArrays);
    }
}
