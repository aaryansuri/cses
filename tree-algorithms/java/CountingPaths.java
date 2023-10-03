import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CountingPaths {

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

        List<List<Integer>> adj = new ArrayList<>();

        for(int i = 0; i <= n; i++) adj.add(new ArrayList<>());



        for(int i = 0; i < n - 1; i++) {
            int a = sc.nextInt();   int b = sc.nextInt();
            adj.get(a).add(b);  adj.get(b).add(a);
        }

        int m = (int) (Math.log(n) / Math.log(2));

        int[][] ancestors = new int[n + 1][m + 1];
        int[] paths = new int[n + 1];
        int[] distance = new int[n + 1];

        dfs(1, -1, adj, ancestors, m, 0, distance);

        for(int i = 0; i < q; i++) {
            int a = sc.nextInt();   int b = sc.nextInt();
            pathSaveLCA(a, b, ancestors, distance, m, paths);
        }


        dfsSum(1, -1, adj, paths);

        String res = Arrays.stream(paths, 1, n + 1).mapToObj(Objects::toString).collect(Collectors.joining(" "));
        System.out.println(res);
    }

    private static void dfsSum(int x, int parent, List<List<Integer>> adj, int[] paths) {
        int sum = 0;
        for(int neigh : adj.get(x)) {
            if(neigh == parent) continue;
            dfsSum(neigh, x, adj, paths);
            sum += paths[neigh];
        }
        paths[x] += sum;
    }

    private static void dfs(int x, int parent, List<List<Integer>> adj, int[][] ancestors, int m, int d, int[] distance){

        distance[x] = d;

        for(int neigh : adj.get(x)) {
            if(neigh == parent) continue;

            ancestors[neigh][0] = x;
            for(int j = 1; j <= m; j++) {
                ancestors[neigh][j] = ancestors[neigh][j - 1] == 0 ? 0 : ancestors[ancestors[neigh][j - 1]][j - 1];
            }
            dfs(neigh, x, adj, ancestors, m, d + 1, distance);
        }

    }

    private static int LCA(int a, int b, int[] distance, int[][] ancestors, int m) {

        int u = a;  int v = b;

        if(distance[u] > distance[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int k = distance[v] - distance[u];
        int col = 0;

        while(k != 0) {
            if((k & 1) == 1) {
                v = ancestors[v][col];
            }
            col = col + 1;
            k = k >> 1;
        }

        if(v == u) return u;

        for(int j = m; j >= 0; j--) {
            if(ancestors[u][j] != ancestors[v][j]) {
                u = ancestors[u][j];
                v = ancestors[v][j];
            }
        }

        return ancestors[u][0];
    }

    private static void pathSaveLCA(int a, int b, int[][] ancestors, int[] distance, int m, int[] paths) {
        int lca = LCA(a, b, distance,ancestors, m);
        paths[a] += 1;
        paths[b] += 1;
        paths[lca] -=1;
        paths[ancestors[lca][0]] -= 1;
    }

}
