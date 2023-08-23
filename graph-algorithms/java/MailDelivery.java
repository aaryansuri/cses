import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MailDelivery {

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

        List<Set<Integer>> adj = new ArrayList<>();

        for(int i = 0; i <= n; i++) adj.add(new HashSet<>());

        for(int i = 0; i < m; i++) {
            int a = sc.nextInt();   int b = sc.nextInt();
            adj.get(a).add(b);
            adj.get(b).add(a);
        }

        boolean isImpossible = IntStream.range(1, n + 1).anyMatch(i -> (adj.get(i).size() % 2 == 1));
        if(isImpossible) {
            System.out.println("IMPOSSIBLE");
            return;
        }

        List<Integer> path = new ArrayList<>();

        dfs(1, path, adj);
        if(path.size() != m + 1) {
            System.out.println("IMPOSSIBLE");
            return;
        }
        StringBuilder sb = new StringBuilder();

        for (Integer obj : path) {
            sb.append(obj).append(" ");
        }

        System.out.println(sb);

    }

    private static void dfs(int node, List<Integer> path, List<Set<Integer>> adj) {

        while (adj.get(node).size() != 0) {
            Iterator<Integer> i = adj.get(node).iterator();
            int nextNode = i.next();
            adj.get(node).remove(nextNode);
            adj.get(nextNode).remove(node);
            dfs(nextNode, path, adj);
        }

        path.add(node);

    }

}
