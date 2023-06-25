import java.util.Objects;
import java.util.Scanner;

public class ElevatorRides {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); int x = sc.nextInt();
        int[] arr = new int[n];

        for(int i = 0; i < n; i++) arr[i] = sc.nextInt();

        System.out.println(minRides(arr, arr.length,  x));
    }


    private static int minRides(int[] people, int n, int x) {

        int limit = 1 << n;
        Pair[] dp = new Pair[limit];
        for (int i = 0; i < limit; i++) {
            dp[i] = new Pair();
        }
        dp[0] = new Pair(1, 0);

        for (int mask = 1; mask < limit; mask++) {
            Pair bestResult = new Pair(Integer.MAX_VALUE, Integer.MAX_VALUE);
            for (int i = 0; i < n; i++) {
                if (((1 << i) & mask) == 0) {
                    continue;
                }
                Pair res = dp[(1 << i) ^ mask];
                if (res.lastWeight + people[i] <= x) {
                    res = new Pair(res.rides, res.lastWeight + people[i]);
                } else {
                    res = new Pair(res.rides + 1, people[i]);
                }
                bestResult = min(bestResult, res);
            }
            dp[mask] = new Pair(bestResult.rides, bestResult.lastWeight);
        }
        return dp[limit - 1].rides;
    }

    private static Pair min(Pair one, Pair two) {
        if (one.rides < two.rides) {
            return new Pair(one.rides, one.lastWeight);
        } else if (one.rides > two.rides) {
            return new Pair(two.rides, two.lastWeight);
        } else {
            // if the number of rides is equal, compare weight remaining
            if (one.lastWeight < two.lastWeight) {
                return new Pair(one.rides, one.lastWeight);
            } else {
                return new Pair(two.rides, two.lastWeight);
            }
        }
    }



    static class Pair {
        int rides;
        int lastWeight;

        Pair(){
            rides = Integer.MAX_VALUE;
            lastWeight = Integer.MAX_VALUE;
        }
        Pair(int r, int w) {
            rides = r;
            lastWeight = w;
        }
    }


}
