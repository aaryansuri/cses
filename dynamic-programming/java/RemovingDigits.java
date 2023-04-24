import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RemovingDigits {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        System.out.println(minStepsIdp(n));
    }


    private static final Function<Integer, List<Integer>> getDigits = (n) ->  {
      List<Integer> digits = new ArrayList<>();
      while (n != 0) {
          digits.add(n%10);
          n = n/10;
      }
      return digits.stream().filter(num -> num !=0).collect(Collectors.toList());
    };

    private static final Map<Integer, Integer> cache = new HashMap<>();

    private static int minStepsRecursive(int n) {

        if(cache.containsKey(n)) return cache.get(n);
        if(n == 0) return 0;

        int min = Integer.MAX_VALUE;

        for(int digit : getDigits.apply(n)) {
            min = Math.min(min, 1 + minStepsRecursive(n - digit));
        }

        cache.put(n, min);
        return min;
    }


    private static int minStepsIdp(int n) {

        int[] steps = new int[n + 1];
        Arrays.fill(steps, Integer.MAX_VALUE);

        steps[0] = 0;

        for(int i = 1; i <= n; i++) {
            for(int digit : getDigits.apply(i)) {
                steps[i] = Math.min(steps[i], 1 + steps[i - digit]);
            }
        }

        return steps[n];
    }

}
