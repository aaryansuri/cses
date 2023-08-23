import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RoadReparation {

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

        int n = sc.nextInt();   int m = sc.nextInt();

        List<Edge> edges = new ArrayList<>();

        for(int i = 0; i < m; i++) {
            int src = sc.nextInt(); int dest = sc.nextInt();    int w = sc.nextInt();
            edges.add(new Edge(src, dest, w));
        }

        UnionFind unionFind = new UnionFind(n);

        edges.sort(Comparator.comparingInt(e -> e.weight));

        long minimalCost = 0;
        int edgesTaken = 0;

        for(int i = 0; i < edges.size() && edgesTaken <= n - 1; i++) {
            Edge e = edges.get(i);

            int x = unionFind.find(e.src);
            int y = unionFind.find(e.dest);

            if(x == y) continue;

            edgesTaken++;
            unionFind.union(x, y);
            minimalCost += e.weight;
        }

        if(edgesTaken != n - 1) {
            System.out.println("IMPOSSIBLE");
            return;
        }

        System.out.println(minimalCost);
    }

    static class UnionFind {
        private final int[] parent;
        private final int[] rank;

        public UnionFind(int n) {
            this.parent = new int[n + 1];
            for(int i = 1; i <= n; i++) parent[i] = i;
            this.rank = new int[n + 1];
        }

        public void union(int a, int b) {
            int parentA = find(a);
            int parentB = find(b);

            if(parentA == parentB) return;

            if(rank[parentA] < rank[parentB]) {
                parent[parentA] = parentB;
            } else if(rank[parentB] < rank[parentA]) {
                parent[parentB] = parentA;
            } else {
                parent[parentA] = parentB;
                rank[parentB] += 1;
            }
        }

        public int find(int ele) {
            if(parent[ele] == ele) return ele;
            parent[ele] = find(parent[ele]);
            return parent[ele];
        }

    }

    static class Edge {
        int src;    int dest;   int weight;

        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }
}
