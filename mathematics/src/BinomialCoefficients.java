import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class BinomialCoefficients {
    static class Reader {

        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(
                new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n') {
                    if (cnt != 0) {
                        break;
                    } else {
                        continue;
                    }
                }
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
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

        public long nextLong() throws IOException {
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

        public double nextDouble() throws IOException {
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

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0,
                BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

    public static void main(String[] args) throws IOException {

        Reader sc = new Reader();

        int n = sc.nextInt();
        StringBuilder sb = new StringBuilder();

        long[] factorial = new long[1000000 + 1];
        long[] inverseFactorial = new long[1000000 + 1];
        int mod = 1000000007;

        calFactorialAndInverse(factorial, inverseFactorial, mod);


        for(int i = 0; i < n; i++) {
            int a = sc.nextInt();   int b = sc.nextInt();
            sb.append(binomialCoff(factorial, inverseFactorial, mod, a, b)).append("\n");
        }

        System.out.println(sb);

    }

    private static long binomialCoff(long[] factorial, long[] inverseFactorial, int mod, int a, int b) {
        return (((factorial[a] * inverseFactorial[a - b]) % mod) * inverseFactorial[b]) % mod;
    }

    private static void calFactorialAndInverse(long[] factorial, long[] inverseFactorial, int mod) {

        factorial[0] = 1;
        inverseFactorial[0] = 1;

        for(int i = 1; i < factorial.length; i++) {
            factorial[i] = (i * factorial[i - 1]) % mod;
            inverseFactorial[i] = exponentiation(factorial[i], mod - 2, mod);
        }
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
