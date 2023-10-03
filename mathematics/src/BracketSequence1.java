import java.util.Scanner;

public class BracketSequence1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();


        int mod = 1000000007;

        long[] factorial = new long[2 * n + 1];

        factorial[0] = 1;

        for(int i = 1; i < 2 * n + 1; i++) {
            factorial[i] = (i * factorial[i - 1]) % mod;
        }

        long ans = (((factorial[2 * n] * exponentiation(factorial[n], mod - 2, mod)) % mod) * (exponentiation(factorial[n + 1], mod - 2, mod) % mod)) % mod;

        System.out.println(ans);

    }

    private static long exponentiation(long a, long b, int mod) {
        if(b == 0) return 1;
        long sq = exponentiation(a, b / 2, mod) ;
        if((b & 1) == 0) {
            return (sq * sq) % mod;
        }
        return (a * ((sq * sq) % mod)) % mod;
    }
}
