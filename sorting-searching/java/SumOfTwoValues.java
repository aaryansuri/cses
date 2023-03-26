import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SumOfTwoValues {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); int x = sc.nextInt(); sc.nextLine();

        int[] arr = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        Map<Integer, int[]> position = new HashMap<>();

        for(int i = 0; i < arr.length; i++) {

            if(position.containsKey(arr[i])) {
                int[] s = position.get(arr[i]);
                s[1] = i + 1;
                position.put(arr[i], s);
            } else {
                int[] s = new int[2];
                s[0] = i + 1;
                position.put(arr[i], s);
            }


        }

        Arrays.sort(arr);

        int i = 0; int j = n - 1;

        while (i < j) {

            int currSum = arr[i] + arr[j];

            if(currSum == x) {

                if(arr[i] == arr[j]) {
                    System.out.println(position.get(arr[i])[0] + " " + position.get(arr[j])[1]);
                    return;
                }

                System.out.println(position.get(arr[i])[0] + " " + position.get(arr[j])[0]);
                return;
            } else if (currSum > x) {
                j--;
            } else  {
                i++;
            }
        }

        System.out.println("IMPOSSIBLE");

    }

}
