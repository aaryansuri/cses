import java.util.Scanner;

public class CountingNecklace {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   int m = sc.nextInt();

        long ans = 0;
        int mod = 1000000007;
        long inverse = exponentiation(n, mod - 2, mod) ;

        for(int i = 0; i < n; i++) {
            long temp = exponentiation(m, gcd(i, n), mod) % mod;
            ans += temp;
            ans = ans % mod;
        }

        System.out.println((ans * inverse) % mod);

    }

    private static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
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
