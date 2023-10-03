import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class GraphPaths2 {

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

    private static final long MAX = 4_000_000_000_000_000_000L;

    public static void main(String[] args) throws IOException {

        Reader sc = new Reader();

        int n = sc.nextInt();   int m = sc.nextInt();   int k = sc.nextInt();

        long[][] adj = new long[n][n];

        Arrays.stream(adj).forEach(r -> Arrays.fill(r, MAX));

        while (m --> 0) {
            int a = sc.nextInt();   int b = sc.nextInt();   int c = sc.nextInt();
            adj[a - 1][b - 1] = Math.min(adj[a - 1][b - 1], c);
        }

        long[][] res = matrixPower(adj, k);

        System.out.println(res[0][n - 1] == MAX ? -1 : res[0][n - 1]);


    }

    private static long[][] matrixPower(long[][] coeff, long n) {
        if(n == 1) {
            return coeff;
        }
        long[][] res = matrixPower(coeff, n / 2);
        res = multiply(res, res);

        return n % 2 == 1 ? multiply(res, coeff) : res;
    }

    private static long[][] multiply(long[][] matrix1, long[][] matrix2) {

        long[][] res = new long[matrix1.length][matrix1.length];
        Arrays.stream(res).forEach(r -> Arrays.fill(r, MAX));

        for(int i = 0; i < matrix1.length; i++) {
            for(int j = 0; j < matrix1.length; j++) {
                for(int k = 0; k < matrix1.length; k++) {
                    res[i][j] = Math.min(res[i][j], matrix1[i][k] + matrix2[k][j]);
                }
            }
        }
        return res;
    }
}
