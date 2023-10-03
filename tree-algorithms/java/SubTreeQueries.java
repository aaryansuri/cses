import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubTreeQueries {

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

        long[] values = new long[n + 1];
        int[] subTreeSizes = new int[n + 1];

        List<List<Integer>> adj = new ArrayList<>();

        for(int i = 0; i <= n; i++) adj.add(new ArrayList<>());
        long[] BIT = new long[n + 1];

        for(int i = 1; i <= n; i++) {
            values[i] = sc.nextInt();
        }

        for(int i = 0; i < n - 1; i++) {
            int a = sc.nextInt();   int b = sc.nextInt();
            adj.get(a).add(b);  adj.get(b).add(a);
        }

        int[] lookup = new int[n + 1];
        int[] reverseLookUp = new int[n + 1];

        dfsForSubTree(1, -1, adj, subTreeSizes, lookup, reverseLookUp);

        for(int i = 1; i <= n; i++) update(BIT, i, n, values[lookup[i]]);

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < q; i++) {
            int type = sc.nextInt();
            if(type == 1) {
                int s = sc.nextInt();   int x = sc.nextInt();
                update(BIT, reverseLookUp[s], n, x - values[s]);
                values[s] = x;
            } else {
                int s = sc.nextInt();
                int subTreeSize = subTreeSizes[s];
                sb.append(sum(BIT, reverseLookUp[s] + subTreeSize) - sum(BIT, reverseLookUp[s] - 1)).append("\n");
            }
        }

        System.out.println(sb);


    }

    private static long sum(long[] BIT, int k) {
        long sum = 0;
        while (k >= 1) {
            sum += BIT[k];
            k -= k & -k;
        }
        return sum;
    }

    private static void update(long[] BIT, int k, int n, long v) {
        while (k <= n) {
            BIT[k] += v;
            k += k & -k;
        }
    }

    private static int id = 0;

    private static void dfsForSubTree(int x, int parent, List<List<Integer>> adj, int[] subTreeSizes, int[] lookup, int[] reverseLookUp) {

        id++;
        int size = 0;
        lookup[id] = x;
        reverseLookUp[x] = id;
        for(int neigh : adj.get(x)) {
            if(neigh == parent) continue;
            dfsForSubTree(neigh, x, adj, subTreeSizes, lookup, reverseLookUp);
            size += 1 + subTreeSizes[neigh];
        }
        subTreeSizes[x] = size;

    }

}
