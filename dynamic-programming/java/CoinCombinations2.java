import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class CoinCombinations2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   int x = sc.nextInt();
        int[] coins = new int[n];
        for(int i = 0; i < n; i++) coins[i] = sc.nextInt();
        System.out.println(combinations(coins, x, coins.length-1));
    }

    private static final Map<String, Integer> cache = new HashMap<>();
    private static final BiFunction<Integer, Integer, String> keyGen = (x, curr) -> x + "|" + curr;

    private static int combinations(int[] coins, int x, int curr) {

        String key = keyGen.apply(x, curr);
        if(cache.containsKey(key)) return cache.get(key);

        if(curr < 0 || x < 0) return 0;
        if(x == 0) return 1;

        int combinations = combinations(coins, x, curr - 1) +
            combinations(coins, x - coins[curr], curr);

        cache.put(key, combinations% 1000000007);
        return cache.get(key);
    }

    private static int combinationsD(int[] coins, int x) {


        int[] combinations = new int[x + 1];
        combinations[0] = 1;

        for(int coin : coins) {
            for(int i = 1; i <= x; i++) {
                if(i - coin >= 0) {
                    combinations[i] += combinations[i - coin];
                    combinations[i] %= 1000000007;
                }
            }
        }

        return combinations[x];

    }
}
