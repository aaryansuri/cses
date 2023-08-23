import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class PrefixSumQueries {

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

        long[] segSum = new long[4 * n];
        long[] prefixMax = new long[4 * n];

        for(int i = 1; i <= n; i++) {
            int num = sc.nextInt();
            update(segSum, prefixMax, 0, i, num, 1 , n);
        }

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < q; i++) {
            int type = sc.nextInt();
            if(type == 1) {
                int k = sc.nextInt();   int u = sc.nextInt();
                update(segSum, prefixMax, 0, k, u, 1, n);
            } else {
                int a = sc.nextInt();   int b = sc.nextInt();

                sb.append(Math.max(0, query(segSum, prefixMax, 0, 1, n, a, b)[1])).append("\n");
            }
        }

        System.out.println(sb);



    }

    private static void update(long[] segSum, long[] prefixMax, int v, int pos, int val, int l, int r) {

        if(l == r) {
            segSum[v] = val;
            prefixMax[v] = val;
            return;
        }

        int mid = (l + r) / 2;

        if(pos >= l && pos <= mid) update(segSum, prefixMax, 2 * v + 1, pos, val, l, mid);
        else update(segSum, prefixMax, 2 * v + 2, pos, val, mid + 1, r);

        segSum[v] = segSum[2 * v + 1] + segSum[2 * v + 2];
        prefixMax[v] = Math.max(prefixMax[2 * v + 1], segSum[2 * v + 1] + prefixMax[2 * v + 2]);
    }

    private static long[] query(long[] segSum, long[] prefixMax, int v, int l, int r, int qL, int qR) {

        if(l > qR || r < qL) return new long[]{0, 0};
        if(l >= qL && r <= qR) return new long[]{segSum[v], prefixMax[v]};

        int mid = (l + r) / 2;

        long[] left = query(segSum, prefixMax, 2 * v + 1, l, mid, qL, qR);
        long[] right = query(segSum, prefixMax, 2 * v + 2, mid + 1, r, qL, qR);

        return new long[] {
                left[0] + right[0],
                Math.max(
                        left[1],
                        left[0] + right[1]
                )
        };
    }


}
