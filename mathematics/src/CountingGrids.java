import java.util.Scanner;

public class CountingGrids {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        long n = sc.nextLong();

        int mod = 1000000007;



        long ans0 = exponentiation(2, (n * n), mod);
        long ans90 = n % 2 == 0 ? exponentiation(2, (n * n) / 4, mod) : exponentiation(2, (n * n + 3) / 4, mod);
        long ans180 = n % 2 == 0 ? exponentiation(2, (n * n) / 2, mod) : exponentiation(2, (n * n + 1) / 2, mod);
        long ans270 = ans90;

        long ans = (ans0 + ans90 + ans180 + ans270) % mod * exponentiation(4, mod - 2, mod) % mod;

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
