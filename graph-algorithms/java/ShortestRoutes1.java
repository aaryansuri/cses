import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ShortestRoutes1 {

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

        List<List<Pair>> adj = new ArrayList<>();

        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int c1 = sc.nextInt();
            int c2 = sc.nextInt();
            long d = sc.nextLong();
            adj.get(c1).add(new Pair(c2, d));
        }

        shortestRoutes(1, adj, n);
    }

    private static void shortestRoutes(int source, List<List<Pair>> adj, int n) {

        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparing(s -> s.distance));

        pq.offer(new Pair(source, 0));
        long[] shortestCities = new long[n + 1];
        Arrays.fill(shortestCities, Long.MAX_VALUE);
        shortestCities[source] = 0;


        while (!pq.isEmpty()) {

            Pair curr = pq.poll();

            if(shortestCities[curr.city] < curr.distance) continue;

            for(Pair neighbours : adj.get(curr.city)) {

                long localMinima = shortestCities[curr.city] + neighbours.distance;

                if (shortestCities[neighbours.city] >= localMinima) {
                    shortestCities[neighbours.city] = localMinima;
                    pq.offer(new Pair(neighbours.city, shortestCities[neighbours.city]));
                }

            }

        }

        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= n; i++) sb.append(shortestCities[i]).append(" ");

        System.out.println(sb);


    }

    static class Pair {
        int city; long distance;

        public Pair(int city, long distance) {
            this.city = city;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "Pair{" +
                "city=" + city +
                ", distance=" + distance +
                '}';
        }
    }
}
