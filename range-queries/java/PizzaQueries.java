import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class PizzaQueries {

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

        int n = sc.nextInt();   int q = sc.nextInt();

        int[] segLeft = new int[4 * n];
        int[] segRight = new int[4 * n];

        int[] nums = new int[n + 1];

        for(int i = 1; i <= n; i++) {
            int num = sc.nextInt();
            nums[i] = num;
            updateRight(segRight, 0, i, num + i, 1 , n);
            updateLeft(segLeft, 0 , i,  num + (n - i + 1) , 1, n);
        }

        StringBuilder sb = new StringBuilder();


        for(int i = 0; i < q; i++) {
            int type = sc.nextInt();
            int k = sc.nextInt();
            if(type == 1) {
                int x = sc.nextInt();
                updateRight(segRight, 0, k, x + k, 1, n);
                updateLeft(segLeft, 0, k, x + (n - k + 1), 1, n);
                nums[k] = x;
            } else  {
                int min = Math.min(minRight(0, segRight, k, n, 1, n) - k, minLeft(0, segLeft, 1, k, 1, n) - (n - k + 1));
                sb.append(min).append("\n");
            }
        }

        System.out.println(sb);

    }

    private static void updateRight(int[] segRight, int v, int pos, int val, int l, int r) {
        if(l == r) {
            segRight[v] = val;
            return;
        }

        int mid = (l + r) / 2;

        if(pos >= l && pos <= mid) updateRight(segRight, 2 * v + 1, pos, val, l , mid);
        else updateRight(segRight, 2 * v + 2, pos, val, mid + 1 , r);

        segRight[v] = Math.min(segRight[2 * v + 1], segRight[2 * v + 2]);
    }

    private static int minRight(int v, int[] tree, int qL, int qR, int l, int r) {

        if(l >= qL && r <= qR) return tree[v];
        if(l > qR || r < qL) return Integer.MAX_VALUE;

        int mid = (l + r) / 2;

        return Math.min(
                minRight(2 * v + 1, tree, qL, qR, l, mid),
                minRight(2 * v + 2, tree, qL, qR, mid + 1, r)
        );
    }

    private static int minLeft(int v, int[] tree, int qL, int qR, int l, int r) {


        if(l >= qL && r <= qR) return tree[v];
        if(l > qR || r < qL) return Integer.MAX_VALUE;

        int mid = (l + r) / 2;

        return Math.min(
                minLeft(2 * v + 1, tree, qL, qR, l, mid),
                minLeft(2 * v + 2, tree, qL, qR, mid + 1, r)
        );
    }

    private static void updateLeft(int[] segLeft, int v, int pos, int val, int l, int r) {
        if(l == r) {
            segLeft[v] =  val;
            return;
        }

        int mid = (l + r) / 2;

        if(pos >= l && pos <= mid) updateLeft(segLeft, 2 * v + 1, pos, val, l , mid);
        else updateLeft(segLeft, 2 * v + 2, pos, val, mid + 1 , r);

        segLeft[v] = Math.min(segLeft[2 * v + 1], segLeft[2 * v + 2]);
    }

}
