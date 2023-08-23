import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class PlanetQueries2 {

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

    private static List<List<Integer>> G;
    private static List<List<Integer>> rG;
    private static List<List<Integer>> uG;

    private static final int LG = 30;

    private static int[] computed;
    public static void main(String[] args) throws IOException {

        Reader sc = new Reader();

        int n = sc.nextInt();   int q = sc.nextInt();
        G = new ArrayList<>();
        rG = new ArrayList<>();
        uG = new ArrayList<>();
        computed = new int[n + 1];
        cycleLen = new int[n + 1];
        pointsCycle = new int[n + 1];
        visited = new int[n + 1];
        ancestor = new int[n + 1][LG];
        distanceFromSomeRoot = new int[n + 1];
        rootOfNodes = new int[n + 1]; // root



        for(int i = 0; i <= n; i++) {
            G.add(new ArrayList<>());
            rG.add(new ArrayList<>());
            uG.add(new ArrayList<>());
        }

        for(int i = 1; i <= n; i++) {
            int v = sc.nextInt();
            G.get(i).add(v);
            rG.get(v).add(i);
            uG.get(i).add(v);
            uG.get(v).add(i);
        }



        for(int i = 1; i <= n; i++) {
            if(computed[i] == 0) {
                markComputed(i, i);
                findCycle(i);
            }
        }

        for(int i = 1; i <=n; i++) {
            if(cycleLen[i] != 0) dfs(i, i);
        }

        StringJoiner sb = new StringJoiner("\n");

        for(int i = 0; i < q; i++) {
            int u = sc.nextInt();   int v = sc.nextInt();

            if(computed[u] != computed[v]) {
                sb.add("-1");
                continue;
            }

            if(cycleLen[u] != 0) {
                if(cycleLen[v] != 0) {
                    sb.add(String.valueOf(distanceInCycle(u, v)));
                } else {
                    sb.add("-1");
                }
            } else {
                if(cycleLen[v] != 0) {
                    sb.add(String.valueOf(
                        distanceFromSomeRoot[u] + distanceInCycle(rootOfNodes[u], v)));
                } else {
                    if(jump(u, distanceFromSomeRoot[u] - distanceFromSomeRoot[v]) == v) {
                        sb.add(String.valueOf(distanceFromSomeRoot[u] - distanceFromSomeRoot[v]));
                    } else {
                        sb.add("-1");
                    }
                }
            }
        }

        System.out.println(sb);

    }

    private static int distanceInCycle(int u, int v) {
        return (pointsCycle[v] - pointsCycle[u] + cycleLen[u]) % cycleLen[u];
    }

    private static int jump(int u, int len) {
        for(int i = 0; len > 0; len >>= 1, i++) {
            if((len & 1) == 1) u = ancestor[u][i];
        }
        return u;
    }

    private static int[] cycleLen;
    private static int[] pointsCycle;
    private static int[] visited;

    private static void findCycle(int u) {

        while (true) {
            visited[u] = 1;
            u = G.get(u).get(0);
            if(visited[u] == 1) break;
        }

        int len = 0; int cycleEnd = u;

        while (true) {
            pointsCycle[u] = len++;
            u = G.get(u).get(0);
            if(u == cycleEnd) break;
        }

        while (true) {
            cycleLen[u] = len;
            u = G.get(u).get(0);
            if(u == cycleEnd) break;
        }

    }

    private static int[] rootOfNodes;
    private static int[] distanceFromSomeRoot;
    private static int[][] ancestor;

    /**
     * sets root to cycle junction point for the nodes in tree
     * which are connected at that junction
     * @param u
     * @param _rt
     */

    public static void dfs(int u, int _rt) {
        rootOfNodes[u] = _rt;
        for(int i = 1; i <= LG - 1; i++) {
            ancestor[u][i] = ancestor[ancestor[u][i-1]][i-1];
        }
        for(Integer v : rG.get(u)) {
            if(cycleLen[v] == 0) {
                distanceFromSomeRoot[v] = distanceFromSomeRoot[u] + 1;
                ancestor[v][0] = u;
                dfs(v, _rt);
            }
        }
    }

    /**
     * marking nodes that can't be reaches as disjoint
     * @param u
     * @param c
     */

    public static void markComputed(int u, int c) {
        computed[u] = c;
        for(Integer v : uG.get(u)) {
            if(computed[v] == 0) markComputed(v, c);
        }    
    }
}
