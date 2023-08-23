import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RangeQueriesAndCopies {

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

    static class Node {
        long val; Node left; Node right;

        public Node(long val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) throws IOException {

        Reader sc = new Reader();

        int n = sc.nextInt();   int q = sc.nextInt();

        List<Node> arrays = new ArrayList<>();

        int[] arr = new int[n + 1];

        for(int i = 1; i <= n; i++) arr[i] = sc.nextInt();

        Node first = build(1, n, arr);
        arrays.add(null);
        arrays.add(first);

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < q; i++) {
            int type = sc.nextInt();
            if(type == 1) {
                //Set the value a in array k to  x
                int k = sc.nextInt();   int a = sc.nextInt();   int x = sc.nextInt();
                arrays.set(k, update(arrays.get(k), 1, n, a, x));
            } else if(type == 2) {
                // Calculate the sum of values in range [a,b] in array k
                int k = sc.nextInt();   int a = sc.nextInt();   int b = sc.nextInt();
                sb.append(sum(arrays.get(k), 1, n, a, b)).append("\n");
            } else {
                // Create a copy of array k and add it to the end of the list
                int k = sc.nextInt();
                arrays.add(arrays.get(k));
            }
        }

        System.out.println(sb);

    }

    private static Node update(Node node, int l, int r, int pos, int val) {

        if(l == r) {
            return new Node(val, null, null);
        }

        int mid = (l + r) / 2;
        Node left = node.left;
        Node right = node.right;

        if(pos <= mid) {
            left = update(node.left , l, mid, pos, val);
        } else {
            right = update(node.right, mid + 1, r, pos, val);
        }

        return new Node(left.val + right.val, left, right);
    }

    private static long sum(Node node, int l, int r, int qL, int qR) {
        if(l > qR || r < qL) return 0;
        if(l >= qL && r <= qR) {
            return node.val;
        }
        int mid = (l + r) / 2;
        return sum(node.left, l, mid, qL, qR) + sum(node.right, mid + 1, r, qL, qR);
    }

    private static Node build(int l, int r, int[] arr) {

        if(l == r) {
            return new Node(arr[l], null, null);
        }

        int mid = (l + r) / 2;
        Node left = build(l, mid, arr);
        Node right = build(mid + 1, r, arr);

        return new Node(
                left.val + right.val,
                left,
                right
        );
    }

}
