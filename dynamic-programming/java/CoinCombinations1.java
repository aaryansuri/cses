import java.util.Scanner;

public class CoinCombinations1 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   int x = sc.nextInt();
        int[] coins = new int[n];
        for(int i = 0; i < n; i++) coins[i] = sc.nextInt();

        System.out.println(combinationD(coins, x));

    }

    private static int combinationsR(int[] coins, int x) {
        if(x < 0) return 0;
        if(x == 0) return 1;
        int combinations = 0;
        for(int coin : coins) {
            combinations += combinationsR(coins, x - coin);
        }
        return combinations;
    }

    private static int combinationD(int[] coins, int x) {

        int[] combinations = new int[x + 1];
        combinations[0] = 1;
        for(int i = 1; i <= x; i++) {
            for (int coin : coins) {
                if (i - coin >= 0) {
                    combinations[i] += combinations[i - coin];
                    combinations[i] %= 1000000007;
                }
            }
        }
        return combinations[x];
    }

}
