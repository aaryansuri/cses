import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ListRemovals {


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

        int[] arr = new int[n + 1];

        int[] tree = new int[4 * n];

        for(int i = 1; i <= n; i++) {
            arr[i] = sc.nextInt();
            update(0, tree, 1 , n, i, 1);
        }

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < n; i++) {
            int pos = sc.nextInt();
            int idx = reportRemoved(0, tree, 1, n, pos);
            update(0, tree, 1, n, idx, -1);
            sb.append(arr[idx]).append(" ");
        }

        System.out.println(sb);
        // 1 2 2 6 4


    }

    private static int reportRemoved(int v, int[] tree, int l, int r, int pos) {

        if(l == r) {
            return l;
        }

        int mid = (l + r) / 2;

        int left = tree[2 * v + 1];

        if(left >= pos) {
            return reportRemoved(2 * v + 1, tree, l, mid, pos);
        } else {
            return reportRemoved(2 * v + 2, tree, mid + 1, r, pos - left);
        }

    }

    private static void update(int v, int[] tree, int l, int r, int pos, int val) {

        if(l == r) {
            tree[v] += val;
            return;
        }

        int mid = (l + r) / 2;

        if(pos >= l && pos <= mid) update(2 * v + 1, tree, l, mid, pos,  val);
        else update(2 * v + 2, tree, mid + 1, r, pos, val);
        tree[v] = tree[2 * v + 1] + tree[2 * v + 2];

    }

}
