import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FixedLengthPath1 {

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

    private static int[] cnt;
    private static int maxDepth;
    private static boolean[] processed;
    private static int[] subTreeSize;
    private static long ans;
    private static int k;

    private static List<List<Integer>> adj;

    public static void main(String[] args) throws IOException {

        Reader sc = new Reader();

        int n = sc.nextInt();   k = sc.nextInt();

        cnt = new int[n + 1];
        processed = new boolean[n + 1];
        subTreeSize = new int[n + 1];

        adj = new ArrayList<>();

        for(int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        for(int i = 0; i < n - 1; i++) {
            int a = sc.nextInt();   int b = sc.nextInt();
            adj.get(a).add(b);  adj.get(b).add(a);
        }

        cnt[0] = 1;

        centroidDecompose(1);

        System.out.println(ans);
        System.out.println(time);

    }

    private static void centroidDecompose(int node) {


        int subTreeSize = getSubTreeSize(node, 0);
        int centroid = getCentroid(node, subTreeSize / 2, 0);

        processed[centroid] = true;
        maxDepth = 0;



        System.out.println("centroid is " + centroid);
        for(int child : adj.get(centroid)) {
            if(!processed[child]) {
                long temp = ans;
                getCount(child, centroid, false, 1);
                getCount(child, centroid, true, 1);
                System.out.println("answer -> " + child + ", " + (ans - temp));
            }
        }
        System.out.println();


        Arrays.fill(cnt, 1, maxDepth + 1, 0);
        for(int neigh : adj.get(centroid)) {
            if(!processed[neigh]) centroidDecompose(neigh);
        }
    }

    private static long time = 0;

    private static void getCount(int node, int parent, boolean filling, int depth) {
        if(depth > k) return;
        maxDepth = Math.max(depth, maxDepth);
        if(filling) cnt[depth]++;
        else {
            ans += cnt[k - depth];
            time++;
        }
        for(int neigh : adj.get(node)) {
            if(!processed[neigh] && neigh != parent) {
                getCount(neigh, node, filling, depth + 1);
            }
        }
    }

    private static int getCentroid(int node, int size, int parent) {
        for(int child : adj.get(node)) {
            if(!processed[child] && child != parent && subTreeSize[child] >= size) {
                return getCentroid(child, size, node);
            }
        }
        return node;
    }

    private static int getSubTreeSize(int node, int parent) {
        subTreeSize[node] = 1;
        for(int neigh : adj.get(node)) {
            if(!processed[neigh] && neigh != parent) {
                subTreeSize[node] += getSubTreeSize(neigh, node);
            }
        }
        return subTreeSize[node];
    }



}
