import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GiantPizza {

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
        List<List<Integer>> adj2 = new ArrayList<>();

        for(int i = 0; i <= 2 * m + 1; i++) {
            adj1.add(new ArrayList<>());
            adj2.add(new ArrayList<>());
        }

        for(int i = 0; i < n; i++) {
            String line = sc.readLine();


            String[] splits = line.split(" ");

            char c1 = splits[0].charAt(0);
            char c2 = splits[2].charAt(0);


            int u = Integer.parseInt(splits[1]);
            int v = Integer.parseInt(splits[3]);

            int u1 = u; int v1 = v;

            if(c1 == '-') {
                u = m + u;
            } else {
                u1 = m + u;
            }

            if(c2 == '-') {
                v = m + v;
            } else {
                v1 = m + v;
            }

            adj1.get(v1).add(u); adj1.get(u1).add(v);
            adj2.get(u).add(v1); adj2.get(v).add(u1);
        }

        boolean[] visited = new boolean[2 * m + 1];

        Stack<Integer> stack = new Stack<>();

        for(int i = 1; i <= 2 * m; i++) {
            if(!visited[i]) {
                dfs1(i, stack, visited, adj1);
            }
        }

        Arrays.fill(visited, false);

        int cc = 0;
        int[] component = new int[2 * m + 1];

        while (!stack.isEmpty()) {
            int curr = stack.pop();
            if(!visited[curr]) {
                dfs2(curr, visited, adj2, component, ++cc);
            }
        }

        char[] result = new char[m + 1];

        for(int i = 1; i <= m; i++) {
            if(component[i] == component[m + i]) {
                System.out.println("IMPOSSIBLE");
                return;
            }

            result[i] = component[i] > component[m + i] ? '+' : '-';
        }


        System.out.println(IntStream.range(1, m + 1).mapToObj(i -> result[i]).map(String::valueOf).collect(Collectors.joining(" ")));

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
