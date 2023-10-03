import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistanceQueries {

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

        int[][] ancestors = new int[n + 1][m + 1];

        List<List<Integer>> adj = new ArrayList<>();

        for(int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        for(int i = 1; i < n; i++) {
            int u = sc.nextInt();   int v = sc.nextInt();
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int[] distance = new int[n + 1];
        dfsForHeight(1, distance, 0, adj, 0, ancestors);
        build(ancestors, m, n);


        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < q; i++) {
            int u = sc.nextInt();   int v = sc.nextInt();
            sb.append(distance[u] + distance[v] - 2 * distance[LCA(u, v , distance, ancestors, m)]).append("\n");
        }

        System.out.println(sb);

    }

    private static int LCA(int u, int v, int[] distance, int[][] ancestors, int m) {

        if(distance[u] > distance[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int k = distance[v] - distance[u];

        for(int j = m; j >= 0; j--) {
            if((k & (1 << j)) != 0) {
                v = ancestors[v][j];
            }
        }

        if(u == v) return u;

        for(int j = m; j >= 0; j--) {
            if(ancestors[u][j] != ancestors[v][j]) {
                u = ancestors[u][j];
                v = ancestors[v][j];
            }
        }

        return ancestors[u][0];
    }

    private static void dfsForHeight(int x, int[] distance, int d, List<List<Integer>> adj, int parent, int[][] ancestors) {

        distance[x] = d;
        ancestors[x][0] = parent;
        for(int child : adj.get(x)) {
            if(child == parent) continue;
            dfsForHeight(child, distance, d + 1, adj, x, ancestors);
        }
    }


    private static void build(int[][] ancestors, int m, int n) {
        for(int j = 1; j <= m; j++) {
            for(int i = 1; i <= n; i++) {
                ancestors[i][j] = ancestors[i][j - 1] == -1 ? -1 : ancestors[ancestors[i][j - 1]][j - 1];
            }
        }
    }


}
