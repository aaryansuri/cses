import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class SubArraySumQueries {

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

        int[] nums = new int[n + 1];
        Node[] tree = new Node[4 * n];

        IntStream.range(0, 4 * n).forEach(i -> tree[i] = new Node());

        for(int i = 1; i <= n; i++) {
            nums[i] = sc.nextInt();
            update(tree, 0, i , nums[i], 1, n);
        }

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < q; i++) {
            int k = sc.nextInt();   int x = sc.nextInt();
            update(tree, 0, k, x, 1, n);
            sb.append(tree[0].maxSubSum).append("\n");
        }

        System.out.println(sb);

    }

    private static void update(Node[] tree, int v, int pos, int val, int l, int r) {

        if(l == r) {
            tree[v] = new Node();
            tree[v].maxSubSum = val;
            tree[v].totalSum = val;
            tree[v].prefixSum = val;
            tree[v].suffixSum = val;
            return;
        }

        int mid = (l + r) / 2;

        if(pos >= l && pos <= mid) update(tree, 2 * v + 1, pos, val, l , mid);
        else update(tree, 2 * v + 2, pos, val, mid + 1 , r);


        tree[v] = new Node();
        tree[v].totalSum = tree[2 * v + 1].totalSum + tree[2 * v + 2].totalSum;
        tree[v].suffixSum = Math.max(tree[2 * v + 2].suffixSum, tree[2 * v + 1].suffixSum + tree[2 * v + 2].totalSum);
        tree[v].prefixSum = Math.max(tree[2 * v + 1].prefixSum, tree[2 * v + 1].totalSum + tree[2 * v + 2].prefixSum);
        tree[v].maxSubSum = Math.max(Math.max(tree[2 * v + 1].maxSubSum, tree[2 * v + 2].maxSubSum), tree[2 * v + 1].suffixSum + tree[2 * v + 2].prefixSum);
        tree[v].maxSubSum = Math.max(0, tree[v].maxSubSum);
    }

    static class Node {

        public Node() {
        }

        long totalSum;   long maxSubSum;
        long prefixSum;  long suffixSum;
    }

}
