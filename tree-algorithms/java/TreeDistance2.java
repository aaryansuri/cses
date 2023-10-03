import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


// revisit
public class TreeDistance2 {

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

        int n = sc.nextInt();

        List<List<Integer>> adj = new ArrayList<>();

        for(int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        for(int i = 0; i < n - 1; i++) {
            int a = sc.nextInt();   int b = sc.nextInt();
            adj.get(a).add(b);
            adj.get(b).add(a);
        }

        long[] sumFromRoot1 = new long[n + 1];
        int[] childFromRoot1 = new int[n + 1];

        sumOfDistancesForRoot(1, 0, adj, sumFromRoot1, childFromRoot1);

        long[] distances = new long[n + 1];

        distances[1] = sumFromRoot1[1];
        sumOfDistancesForAll(1, 0, adj, childFromRoot1, distances, n);


        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= n; i++) {
            sb.append(distances[i]).append(" ");
        }

        System.out.println(sb);

    }

    private static void sumOfDistancesForRoot(int x, int parent, List<List<Integer>> adj, long[] sumFromRoot1, int[] childFromRoot1) {

        for(int neigh : adj.get(x)) {
            if(neigh == parent) continue;
            sumOfDistancesForRoot(neigh, x, adj, sumFromRoot1, childFromRoot1);
            sumFromRoot1[x] += 1 + sumFromRoot1[neigh] + childFromRoot1[neigh];
            childFromRoot1[x] += childFromRoot1[neigh];
            childFromRoot1[x] += 1;
        }
    }

    private static void sumOfDistancesForAll(int x, int parent, List<List<Integer>> adj, int[] childFromRoot1, long[] distances, int n) {

        for(int neigh : adj.get(x)) {
            if(neigh == parent) continue;
            distances[neigh] = distances[x] - childFromRoot1[neigh] + (n - childFromRoot1[neigh] - 2);
            sumOfDistancesForAll(neigh, x, adj, childFromRoot1, distances, n);
        }

    }


}
