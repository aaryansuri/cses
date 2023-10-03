import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PathQueries2 {

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

        for(int i = 1; i <= n; i++) values[i] = sc.nextInt();

        List<List<Integer>> adj = new ArrayList<>();
        for(int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        for(int i = 0; i < n - 1; i++) {
            int a = sc.nextInt();   int b = sc.nextInt();
            adj.get(a).add(b);  adj.get(b).add(a);
        }

        int[] heavy = new int[n + 1];
        int[] size = new int[n + 1];
        int[] lookUp = new int[n + 1];
        int[] head = new int[n + 1]; // stores chain number
        int[] depth = new int[n + 1];
        int[] idxValues = new int[n + 1];
        int[] parents = new int[n + 1];

        dfs(1, 0, heavy, size, parents, depth, adj);
        dfsHLD(1, 0, heavy, 1, adj, head, lookUp, idxValues, values);

        SegTree segTree = new SegTree(new int[4 * n]);

        for(int i = 1; i <= n; i++) {
            segTree.updateST(0, i, idxValues[i], 1, n);
        }

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < q; i++) {
            int type = sc.nextInt();

            if(type == 1) {
                int s = sc.nextInt();   int x = sc.nextInt();
                segTree.updateST(0, lookUp[s], x, 1, n);
                idxValues[lookUp[s]] = x;
            } else {
                int a = sc.nextInt();   int b = sc.nextInt();
                sb.append(lcaAndMax(a, b, parents, depth, head, segTree, n, lookUp)).append("\n");
            }
        }

        System.out.println(sb);
    }

    static class SegTree {
        private int[] tree;

        public SegTree(int[] tree) {
            this.tree = tree;
        }

        public void updateST(int v, int pos, int val, int l, int r) {
            if(l == r) {
                tree[v] = val;
                return;
            }
            int mid = (l + r) / 2;
            if(pos >= l && pos <= mid) updateST(2 * v + 1, pos, val, l, mid);
            else updateST(2 * v + 2, pos, val, mid + 1, r);
            tree[v] = Math.max(tree[2 * v + 1], tree[2 * v + 2]);
        }

        public int maxST(int v, int l, int r, int qL, int qR) {
            if(r < qL || l > qR) return Integer.MIN_VALUE;
            if(l >= qL && r <= qR) return tree[v];

            int mid = (l + r) / 2;
            return Math.max(
                maxST(2 * v + 1, l, mid, qL, qR),
                maxST(2 * v + 2, mid + 1, r, qL, qR)
            );
        }
    }

    private static int pos = 1;

    private static void dfsHLD(int x, int parent, int[] heavy, int chainNo, List<List<Integer>> adj, int[] head, int[] lookUp, int[] idxValue, int [] values) {

        head[x] = chainNo;
        lookUp[x] = pos;
        idxValue[pos] = values[x];
        pos++;


        if(heavy[x] != 0) {
            // same chain
            dfsHLD(heavy[x], x, heavy, chainNo, adj, head, lookUp, idxValue, values);
        }

        for(int neigh : adj.get(x)) {
            if(neigh == parent || neigh == heavy[x]) continue;
            // change chain
            dfsHLD(neigh, x, heavy, neigh, adj, head, lookUp, idxValue, values);
        }

    }

    private static int lcaAndMax(int a, int b, int[] parents, int[] depth, int[] head, SegTree segTree, int n, int[] lookUp) {

        int max = Integer.MIN_VALUE;

        while (head[a] != head[b]) {
            if(depth[head[a]] > depth[head[b]]) {
                max = Math.max(segTree.maxST(0, 1, n, lookUp[head[a]], lookUp[a]), max);
                a = parents[head[a]];
            } else {
                max = Math.max(segTree.maxST(0, 1, n, lookUp[head[b]], lookUp[b]), max);
                b = parents[head[b]];
            }
        }

        if(depth[a] < depth[b]) {
            max = Math.max(segTree.maxST(0, 1, n, lookUp[a], lookUp[b]), max);
        } else {
            max = Math.max(segTree.maxST(0, 1, n, lookUp[b], lookUp[a]), max);
        }

        return max;
    }

    private static void dfs(int x, int parent, int[] heavy, int[] size, int[] parents, int[] depth, List<List<Integer>> adj) {

        size[x]++;
        for(int neigh : adj.get(x)) {
            if(neigh == parent) continue;
            depth[neigh] = 1 + depth[x];
            parents[neigh] = x;
            dfs(neigh, x, heavy, size, parents, depth, adj);
            size[x] += size[neigh];
            if(size[neigh] > size[heavy[x]]) {
                heavy[x] = neigh;
            }
        }
    }

}
