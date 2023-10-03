import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class CompanyQueries1 {

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

        int n = sc.nextInt();   int q = sc.nextInt();

        int m = (int) (Math.log(200000) / Math.log(2));

        int[][] ancestors = new int[n][m + 1];
        ancestors[0][0] = -1;

        for(int i = 0; i < n - 1; i++) {
            ancestors[i + 1][0] = sc.nextInt() - 1;
        }

        build(ancestors, m, n);



        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < q; i++) {
            int x = sc.nextInt();   int k = sc.nextInt();
            sb.append(kthAncestor(ancestors, x - 1, k)).append("\n");
        }

        System.out.println(sb);

    }

    private static void build(int[][] ancestors, int m, int n) {
        for(int j = 1; j <= m; j++) {
            for(int i = 0; i < n; i++) {
                ancestors[i][j] = ancestors[i][j - 1] == -1 ? -1 : ancestors[ancestors[i][j - 1]][j - 1];
            }
        }
    }

    private static int kthAncestor(int[][] ancestors, int node, int k) {

        int col = 0;

        while(k != 0) {
            if((k & 1) == 1) {
                node = ancestors[node][col];
                if(node == - 1) return node;
            }
            col = col + 1;
            k = k >> 1;
        }

        return node + 1;

    }
}
