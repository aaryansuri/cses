import java.util.Arrays;
import java.util.Scanner;

public class ThrowingDice {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        long n = sc.nextLong();
        if(n == 0) {
            System.out.println(0);
            return;
        }

        long[][] coeff = new long[][]{{1, 1, 1, 1, 1, 1} , {1, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 1, 0}};

        long[][] result = matrixPower(coeff, n);

        System.out.println(result[0][0]);

    }

    private static final int mod = 1000000007;

    private static long[][] matrixPower(long[][] coeff, long n) {
        if(n == 0) {
            return new long[][]{{1, 0, 0, 0, 0, 0} , {0, 1, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 1, 0}, {0, 0, 0, 0, 0, 1}};
        }
        long[][] res = matrixPower(coeff, n / 2);
        res = multiply(res, res);

        return n % 2 == 1 ? multiply(res, coeff) : res;
    }




    private static long[][] multiply(long[][] matrix1, long[][] matrix2) {

        long[][] res = new long[6][6];

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                for(int k = 0; k < 6; k++) {
                    res[i][j] = (res[i][j] +  matrix1[i][k] * matrix2[k][j]) % mod;
                }
            }
        }
        return res;
    }
}
