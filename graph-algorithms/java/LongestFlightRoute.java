import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

public class LongestFlightRoute {

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

        int n = sc.nextInt(); int m = sc.nextInt();

        int[] inDegree = new int[n + 1];

        List<List<Integer>> adj = new ArrayList<>();

        for(int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        for(int i = 0; i < m; i++) {
            int a = sc.nextInt();   int b = sc.nextInt();
            inDegree[b]++;
            adj.get(a).add(b);
        }

        Queue<Integer> Q = new LinkedList<>();

        for(int i = 1; i <= n; i++) {
            if(inDegree[i] == 0) Q.add(i);
        }

        int[] prev = new int[n + 1];

        int[] dis = new int[n + 1];
        Arrays.fill(dis, Integer.MIN_VALUE);

        dis[1] = 0;

        while (!Q.isEmpty()) {
            int curr = Q.poll();
            for(int neigh : adj.get(curr)) {
                if(dis[curr] + 1 > dis[neigh]) {
                    dis[neigh] = dis[curr] + 1;
                    prev[neigh] = curr;
                }
                inDegree[neigh]--;
                if(inDegree[neigh] == 0) Q.add(neigh);
            }
        }

        if (dis[n] < 0) {
            System.out.println("IMPOSSIBLE");
            return;
        }

        List<Integer> path = new ArrayList<>();
        int curr = n;
        path.add(curr);
        while (curr != 1) {
            curr = prev[curr];
            path.add(curr);
        }


        System.out.println(path.size());
        Collections.reverse(path);
        System.out.println(path.stream().map(Objects::toString).collect(Collectors.joining(" ")));

    }
}
