import java.util.Arrays;
import java.util.Scanner;

public class TasksAndDeadlines {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        long totalDeadLine = 0;
        int[] durations = new int[n];

        for(int i = 0; i < n; i++) {
            durations[i] = sc.nextInt();
            totalDeadLine += sc.nextInt();
        }

        Arrays.sort(durations);

        long optimalDurations = 0;

        for(int i = 0; i < n; i++) {
            optimalDurations += (long) (n - i) * durations[i];
        }

        System.out.println(totalDeadLine - optimalDurations);

    }

}
