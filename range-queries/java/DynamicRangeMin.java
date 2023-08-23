import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class DynamicRangeMin {

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
        int q = sc.nextInt();

        int[] nums = new int[n + 1];
        int[] tree = new int[4 * n];

        for(int i = 1; i <= n; i++) nums[i] = sc.nextInt();

        build(0, nums, tree, 0,  n - 1);

        StringBuilder sb = new StringBuilder();


        for(int i = 0; i < q; i++) {
            int type = sc.nextInt();
            if(type == 2) {
                int a = sc.nextInt();   int b = sc.nextInt();
                sb.append(min(0, tree, 0 , n - 1, a - 1, b - 1)).append("\n");
            } else {
                int pos = sc.nextInt(); int val = sc.nextInt();
                update(0, nums, tree, 0, n - 1, pos - 1, val);
            }

        }

        System.out.println(sb);


    }

    private static void build(int v, int[] nums, int[] tree, int l, int r) {

        if(l == r) {
            tree[v] = nums[l]; return;
        }

        int mid = (l + r) / 2;

        build(2 * v + 1, nums, tree, l, mid);
        build(2 * v + 2, nums, tree, mid + 1, r);
        tree[v] = Math.min(tree[2 * v + 1], tree[2 * v + 2]);
    }

    private static int min(int v, int[] tree, int start, int end, int l, int r) {

        if (r < start || l > end) {
            return Integer.MAX_VALUE;
        }

        if (l <= start && end <= r) {
            return tree[v];
        }

        int mid = (start + end) / 2;

        return Math.min(
                min(2 * v + 1, tree, start, mid, l, r),
                min(2 * v + 2, tree, mid + 1, end, l, r)
        );
    }

    private static void update(int v, int[] nums, int[] tree, int l, int r, int pos, int val) {

        if(l == r) {
            tree[v] = val;
            nums[pos] = val;
            return;
        }

        int mid = (l + r) / 2;

        if(pos >= l && pos <= mid) update(2 * v + 1, nums, tree, l, mid, pos, val);
        else update(2 * v + 2, nums, tree, mid + 1, r, pos, val);

        tree[v] = Math.min(tree[2 * v + 1], tree[2 * v + 2]);
    }

}
