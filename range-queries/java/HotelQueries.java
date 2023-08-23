import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class HotelQueries {

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
     * Input:
     * 8 5
     * 3 2 4 1 5 5 2 6
     * 4 4 7 1 1
     *
     * Output:
     * 3 5 0 1 1
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {

        Reader sc = new Reader();
        int n = sc.nextInt();   int q = sc.nextInt();

        Pair[] tree = new Pair[4 * n];
        int[] arr = new int[n + 1];

        for(int i = 1; i <= n; i++) {
            arr[i] = sc.nextInt();
        }

        build(0, 1, n, arr, tree);


        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < q; i++) {
            int num = sc.nextInt();
            int ans = firstGreaterThan(0, 1, n, arr, tree, num);
            sb.append(ans == -1 ? 0 : ans).append(" ");
        }

        System.out.println(sb);

    }

    static void build(int v, int l, int r, int[] arr, Pair[] tree) {

        if(l == r) {
            tree[v] = new Pair(arr[l], l);
            return;
        }

        int mid = (l + r) / 2;

        build(2 * v + 1, l, mid, arr, tree);
        build(2 * v + 2, mid + 1, r, arr, tree);

        if(tree[2 * v + 1].ele >= tree[2 * v + 2].ele) {
            tree[v] = new Pair(tree[2 * v + 1].ele, tree[2 * v + 1].pos);
        } else {
            tree[v] = new Pair(tree[2 * v + 2].ele, tree[2 * v + 2].pos);
        }

    }

    static int firstGreaterThan(int v, int l, int r, int[] arr, Pair[] tree, int val) {

        if(tree[v].ele < val) return -1;

        if(l == r) {
            update(0, arr, tree, 1, arr.length - 1, l, tree[v].ele - val);
            return tree[v].pos;
        }

        int mid = (l + r) / 2;

        if(tree[2 * v  + 1].ele >= val) {
            return firstGreaterThan(2 * v + 1, l, mid, arr, tree, val);
        } else {
            return firstGreaterThan(2 * v + 2, mid + 1, r, arr, tree, val);
        }

    }

    static void update(int v, int[] nums, Pair[] tree, int l, int r, int pos, int val) {

        if(l == r) {
            tree[v] = new Pair(val, l);
            return;
        }

        int mid = (l + r) / 2;

        if(pos >= l && pos <= mid) update(2 * v + 1, nums, tree, l, mid, pos, val);
        else update(2 * v + 2, nums, tree, mid + 1, r, pos, val);

        if(tree[2 * v + 1].ele >= tree[2 * v + 2].ele) {
            tree[v] = new Pair(tree[2 * v + 1].ele, tree[2 * v + 1].pos);
        } else {
            tree[v] = new Pair(tree[2 * v + 2].ele, tree[2 * v + 2].pos);
        }
    }

    static class Pair {

        int ele; int pos;

        public Pair(int ele, int pos) {
            this.ele = ele;
            this.pos = pos;
        }

        @Override
        public String toString() {
            return "{" +
                    "ele=" + ele +
                    ", pos=" + pos +
                    '}';
        }
    }

}
