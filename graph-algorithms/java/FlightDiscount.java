
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class FlightDiscount {

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

        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int c1 = sc.nextInt();
            int c2 = sc.nextInt();
            long d = sc.nextLong();
            adj.get(c1).add(new Connection(c2, d));
        }

        long[] discountedPrice = new long[n + 1];
        long[] fullPrice = new long[n + 1];
        Arrays.fill(discountedPrice, Long.MAX_VALUE);
        Arrays.fill(fullPrice, Long.MAX_VALUE);
        discountedPrice[1] = 0;
        fullPrice[1] = 0;
        discountedPrice[0] = 0;
        fullPrice[0] = 0;

        PriorityQueue<Decision> queue = new PriorityQueue<>(Comparator.comparingLong(d -> d.price));

        queue.add(new Decision(1, 0, false));

        while (!queue.isEmpty()) {

            Decision curr = queue.poll();
            int city = curr.city;
            long price = curr.price;
            boolean couponUsed = curr.couponUsed;

            if(couponUsed){
                if(discountedPrice[city] < price) continue;
            }

            if(!couponUsed) {
                if(fullPrice[city] < price) continue;
            }

            for(Connection j : adj.get(city)) {

                if(!couponUsed) {
                    if(j.price/2 + price < discountedPrice[j.city]) {
                        discountedPrice[j.city] = j.price/2 + price;
                        queue.add(new Decision(j.city, discountedPrice[j.city], true));
                    }
                    if(j.price + price < fullPrice[j.city]) {
                        fullPrice[j.city] = j.price + price;
                        queue.add(new Decision(j.city, fullPrice[j.city], false));
                    }
                }

                if(couponUsed) {
                    if(j.price + price < discountedPrice[j.city]) {
                        discountedPrice[j.city] = j.price + price;
                        queue.add(new Decision(j.city, discountedPrice[j.city], true));
                    }
                }


            }

        }

        System.out.println(discountedPrice[n]);

    }

    static class Decision {
        int city; long price; boolean couponUsed;

        public Decision(int city, long price, boolean couponUsed) {
            this.city = city;
            this.price = price;
            this.couponUsed = couponUsed;
        }
    }

    static class Connection {
        int city; long price;
        public Connection(int city, long price) {
            this.city = city;
            this.price = price;
        }

    }
}
