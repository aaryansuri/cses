import java.util.Scanner;

public class DiceCombinations {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        long combinations = 0;

        long[] lastDiceCombinations = new long[6];

        lastDiceCombinations[0] = 1;

        for(int i = 1; i <= n; i++) {
            combinations = 0;
            for(int j = 1; j <= 6 && j <= i; j++) {
                combinations += lastDiceCombinations[(i-j)%6];
                combinations %= 1000000007;
            }
            lastDiceCombinations[i%6] = combinations;
        }

        System.out.println(lastDiceCombinations[n%6]);

    }

}
