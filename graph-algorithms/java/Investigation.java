import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Investigation {
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

        List<List<Connection>> adj = new ArrayList<>();

        for(int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        for(int i = 0; i < m; i++) {
            int a = sc.nextInt();   int b = sc.nextInt();   int c = sc.nextInt();
            adj.get(a).add(new Connection(b, c));
        }

        long[] distance = new long[n + 1];
        Arrays.fill(distance, Long.MAX_VALUE);
        distance[1] = 0;

        PriorityQueue<Decision> queue = new PriorityQueue<>(Comparator.comparingLong(d -> d.price));

        queue.add(new Decision(1, 0));
        int[] minCities = new int[n + 1];
        int[] maxCities = new int[n + 1];
        int[] minimumPriceRoutes = new int[n + 1];
        minimumPriceRoutes[1] = 1;

        while (!queue.isEmpty()) {

            Decision curr = queue.poll();

            if(distance[curr.city] < curr.price) continue;

            for(Connection neigh : adj.get(curr.city)) {
                long localMinima = distance[curr.city] + neigh.price;

                if(localMinima > distance[neigh.city]) continue;

                if(localMinima == distance[neigh.city]) {
                    minimumPriceRoutes[neigh.city] += minimumPriceRoutes[curr.city];
                    minimumPriceRoutes[neigh.city] %= 1000000007;
                    minCities[neigh.city] = Math.min(minCities[neigh.city], minCities[curr.city] + 1);
                    maxCities[neigh.city] = Math.max(maxCities[neigh.city], maxCities[curr.city] + 1);

                } else if(localMinima < distance[neigh.city]) {
                    distance[neigh.city] = localMinima;
                    minimumPriceRoutes[neigh.city] = minimumPriceRoutes[curr.city];
                    minCities[neigh.city] = minCities[curr.city] + 1;
                    maxCities[neigh.city] = maxCities[curr.city] + 1;
                    queue.offer(new Decision(neigh.city, localMinima));
                }
            }
        }


        System.out.println(distance[n] + " " +minimumPriceRoutes[n] + " " + minCities[n] + " " + maxCities[n]);

    }

    static class Connection {
        int city; long price;
        public Connection(int city, long price) {
            this.city = city;
            this.price = price;
        }

    }

    static class Decision {
        int city; long price;

        public Decision(int city, long price) {
            this.city = city;
            this.price = price;
        }
    }
}
