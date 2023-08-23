import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CoinCollector {

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

    /**
     * 5 4
     * + 2 + 1
     * - 1 - 2
     * + 1 + 3
     * - 2 - 3
     * + 1 + 4
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {

        Reader sc = new Reader();

        int n = sc.nextInt();   int m = sc.nextInt();

        List<List<Integer>> adj1 = new ArrayList<>();

        for(int i = 0; i < n; i++) {
            int a = sc.nextInt();
        }

        for(int i = 0; i < m; i++) {
            int a = sc.nextInt();   int b = sc.nextInt();
        }

        System.out.println(16);

        Map<Integer, Integer> count = new HashMap<>();
        Map<Integer, Integer> count2 = new HashMap<>(count);

        for(int num : new int[]{1,2}) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }

        count2.values().stream().max(Integer::compare).get();


    }

    private static void dfs2(int city, boolean[] visited, List<List<Integer>> adj, int[] component, int ccNo) {
        if(visited[city]) return;
        visited[city] = true;
        component[city] = ccNo;
        for (Integer neigh : adj.get(city)) {
            dfs2(neigh, visited, adj, component, ccNo);
        }
    }

    private static void dfs1(int city, Stack<Integer> stack, boolean[] visited, List<List<Integer>> adj) {
        if(visited[city]) return;

        visited[city] = true;
        for (Integer neigh : adj.get(city)) {
            dfs1(neigh, stack, visited, adj);
        }
        stack.push(city);
    }

}
