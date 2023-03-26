import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class NestedRangeChecks {

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

        Range[] ranges = new Range[n];

        for (int i = 0; i < n; i++) {
            long a = sc.nextLong();
            long b = sc.nextLong();
            ranges[i] = new Range(a, b, i);
        }

        Arrays.sort(ranges, (r1, r2) -> {
            if(r1.a == r2.a) {
                return Long.compare(r2.b, r1.b);
            }
            return Long.compare(r1.a, r2.a);
        });

        int[] contains = new int[n];
        int[] contained = new int[n];

        long max_end = 0;

        for(int i = 0; i < n; i++) {
            if(ranges[i].b <= max_end) {
                contained[ranges[i].i] = 1;
            }
            max_end = Math.max(max_end, ranges[i].b);
        }

        long min_end = Long.MAX_VALUE;

        for(int i = n - 1; i >= 0; i--) {
            if(ranges[i].b >= min_end) {
                contains[ranges[i].i] = 1;
            }
            min_end = Math.min(min_end, ranges[i].b);
        }

        StringBuilder sb1 = new StringBuilder();
        for(int i = 0; i<n;i++) {
            sb1.append(contains[i]).append(" ");
        }
        sb1.append("\n");
        for(int i = 0; i<n;i++) {
            sb1.append(contained[i]).append(" ");
        }
        System.out.println(sb1);

    }

    static class Range {
        long a; long b; int i;

        public Range(long a, long b, int i) {
            this.a = a;
            this.b = b;
            this.i = i;
        }
    }
}
