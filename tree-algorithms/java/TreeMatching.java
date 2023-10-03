import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class TreeMatching {

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

        List<Set<Integer>> adj = new ArrayList<>();

        for(int i = 0; i <= n; i++) adj.add(new HashSet<>());

        for(int i = 0; i < n - 1; i++) {
            int a = sc.nextInt();   int b = sc.nextInt();
            adj.get(a).add(b);  adj.get(b).add(a);
        }

        int[][] dp = new int[n + 1][2];

        Arrays.stream(dp).forEach(r -> Arrays.fill(r, -1));

        System.out.println(Math.max(dfs(1, 0, false, adj, dp), dfs(1, 0, true, adj, dp)));

    }

    private static int dfs(int x, int parent, boolean nextPickup, List<Set<Integer>> adj, int[][] dp) {

        if(dp[x][nextPickup? 1 : 0] != - 1) return dp[x][nextPickup? 1 : 0];

        if(nextPickup) {

            int max = 0;
            int pickUpAll = 0;

            for(int neigh : adj.get(x)) {
                if(neigh == parent) continue;
                pickUpAll += dfs(neigh, x, true, adj, dp);
            }

            for(int neigh : adj.get(x)) {
                if(neigh == parent) continue;
                int pickup = 1 + dfs(neigh, x, false, adj, dp);
                max = Math.max(max, pickUpAll - dp[neigh][1] + pickup);
            }

            dp[x][1] = max;

            return max;
        } else {
            int ans = 0;

            for(int neigh : adj.get(x)) {
                if(neigh == parent) continue;
                ans += Math.max(dfs(neigh, x, true, adj, dp), dfs(neigh, x, false, adj, dp));
            }

            dp[x][0] = ans;

            return ans;
        }


    }

}
