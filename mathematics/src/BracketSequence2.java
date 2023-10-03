import java.util.Scanner;

public class BracketSequence2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();

        String input = sc.nextLine();

        if(n % 2 == 1) {
            System.out.println(0);
            return;
        }

        int opening = 0;    int closing = 0;

        for(char c : input.toCharArray()) {
            if(c == ')') {
                closing++;
            }
            if(c == '(') {
                opening++;
            }
            if(closing > opening) {
                System.out.println(0);
                return;
            }
        }

        if(opening * 2 > n) {
            System.out.println(0);
            return;
        }

        n -= input.length();

        int remClosed = (n + opening - closing) / 2;
        int remOpening = n - remClosed;

        factorial = new long[n + 10];
        factorialCal();

        long ans = (binomialCoff(n, remClosed) - binomialCoff(n, remClosed + 1) + mod) % mod;
        System.out.println(ans);
    }

    private static long[] factorial;
    private static final int mod = 1000000007;

    private static void factorialCal() {
        factorial[0] = 1;

        for(int i = 1; i < factorial.length; i++) {
            factorial[i] = (i * factorial[i - 1]) % mod;
        }
    }

    private static long factorial(int x) {
        return factorial[x];
    }

    private static long inverseFactorial(int x) {
        return exponentiation(factorial(x), mod - 2, mod);
    }

    private static long binomialCoff(int a, int b) {
        if(a < 0 || a < b) return 0;
        return (((factorial(a) * inverseFactorial(a - b)) % mod) * inverseFactorial(b)) % mod;
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
