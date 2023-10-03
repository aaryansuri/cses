import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PathQueries {

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

        int[] values = new int[n + 1];
        int[] subTreeSize = new int[n + 1];
        long[] pathSum = new long[n + 1];
        int[] lookUp = new int[n + 1];
        long[] BIT = new long[n + 1];

        for(int i = 1; i <= n; i++) values[i] = sc.nextInt();

        List<Integer>[] adj = new List[n +1];

        for(int i = 0; i <= n; i++) adj[i] = new ArrayList<>();

        for(int i = 0; i < n - 1; i++) {
            int a = sc.nextInt();   int b = sc.nextInt();
            adj[a].add(b);  adj[b].add(a);
        }

        dfs(1, 0, adj, subTreeSize, lookUp, values, pathSum, n);

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < q; i++) {
            int type = sc.nextInt();
            if(type == 1) {
                int s = sc.nextInt();   int x = sc.nextInt();
                update(BIT, lookUp[s], n, x - values[s]);
                update(BIT, lookUp[s] + subTreeSize[s] + 1, n,  - (x - values[s]));
                values[s] = x;
            } else {
                int s = sc.nextInt();
                sb.append(sum(BIT, lookUp[s]) + pathSum[lookUp[s]]).append("\n");
            }
        }

        System.out.println(sb);
    }

    /**
     * 5 3
     * 4 2 5 2 1
     * 1 2
     * 1 3
     * 3 4
     * 3 5
     * 2 4
     * 1 3 2
     * 2 4
     * change the value of node s
     *  to x
     *
     * calculate the sum of values on the path from the root to node s
     */

    private static int id = 0;

    private static void dfs(int x, int parent, List<Integer>[] adj, int[] subTreeSizes, int[] lookup, int[] values, long[] pathSum, int n) {
        id++;
        int size = 0;
        lookup[x] = id;
        pathSum[id] = values[x] + pathSum[lookup[parent]];
        for(int neigh : adj[x]) {
            if(neigh == parent) continue;
            dfs(neigh, x, adj, subTreeSizes, lookup, values, pathSum, n);
            size += 1 + subTreeSizes[neigh];
        }
        subTreeSizes[x] = size;
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

}
