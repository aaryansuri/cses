import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class DivisorAnalysis {

    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(
                new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n') {
                    if (cnt != 0) {
                        break;
                    }
                    else {
                        continue;
                    }
                }
                buf[cnt++] = (byte)c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0,
                BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }

    public static void main(String[] args) throws IOException {

        Reader sc = new Reader();

        int mod = 1000000007;

        int n = sc.nextInt();


        int[] prime = new int[n];
        int[] expo = new int[n];

        for(int i = 0; i < n; i++) {
            prime[i] = sc.nextInt();
            expo[i] = sc.nextInt();

        }

        long numberOfDivisors = 1;
        for(int i = 0; i < n; i++) {
            numberOfDivisors = (numberOfDivisors * (expo[i] + 1)) % mod;
        }

        long sumOfFactors = 1;

        for(int i = 0; i < n; i++) {
            sumOfFactors = (sumOfFactors * geometricSum(prime[i], expo[i], mod))  % mod;
        }

        long productOfDivisors = 1;
        boolean oddExponent = false;
        int posOddEle = -1;

        for(int i = 0; i < n; i++) {
            if(expo[i] % 2 == 1) {
                oddExponent = true;
                posOddEle = i;
            }
        }

        // a ^ b ^ c
        // use mod - 1 b ^ c

        if(oddExponent) {
            long outerExpo = 1;
            for(int i = 0; i < n; i++) {
                if(i == posOddEle) {
                    outerExpo = (outerExpo * (expo[i] + 1)/2) % (mod - 1);
                } else {
                    outerExpo = (outerExpo * (expo[i] + 1)) % (mod - 1);
                }
            }
            for(int i = 0; i < n; i++) {
                productOfDivisors = (productOfDivisors * exponentiation(prime[i], (expo[i] * outerExpo) % (mod - 1), mod)) % mod;
            }
        } else {
            long outerExpo = 1;
            for(int i = 0; i < n; i++) {
                outerExpo = (outerExpo * (expo[i] + 1)) % (mod - 1);
            }
            for(int i = 0; i < n; i++) {
                productOfDivisors = (productOfDivisors * exponentiation(prime[i], ((expo[i]/2) * outerExpo) % (mod - 1), mod)) % mod;
            }
        }

        System.out.println(numberOfDivisors + " " + sumOfFactors + " " + productOfDivisors);
        int[][] dp = new int[301][6];
        Arrays.stream(dp).forEach(row -> Arrays.fill(row, -1));

    }

    private static long geometricSum(int base, int power, int mod) {
        long numerator = (exponentiation(base, power + 1, mod) - 1 + mod) % mod;
        long denominator = exponentiation(base - 1, mod - 2, mod);
        return (numerator * denominator) % mod;
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
