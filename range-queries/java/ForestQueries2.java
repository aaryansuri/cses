import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ForestQueries2 {

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
            byte[] buf = new byte[64*100]; // line length
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

    public static void main(String[] args) throws IOException{

        Reader sc = new Reader();

        int n = sc.nextInt();      int q = sc.nextInt();


        int[][] forest = new int[n + 1][n + 1];

        for(int i = 1; i <= n; i++) {
            String line = sc.readLine();
            line = "x" + line;
            char[] charArray = line.toCharArray();
            forest[i] = IntStream.range(0 , charArray.length).map(c -> charArray[c] == '*' ? 1 : 0).toArray();
        }


        int[][] tree = new int[4 * n][4 * n];

        buildX(tree, forest, 0, 1, n, n);

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < q; i++) {
            int type = sc.nextInt();
            if(type == 2) {
                int x1 = sc.nextInt();  int y1 = sc.nextInt();  int x2 = sc.nextInt();  int y2 = sc.nextInt();
                sb.append(queryX(tree, 0, x1, y1, 1, n, x2, y2, n)).append("\n");
            } else {
                int posX = sc.nextInt();    int posY = sc.nextInt();
                int val = forest[posX][posY] == 1 ? -1 : 1;
                forest[posX][posY] = val;
                updateX(tree, 0, 1, n, n, posX, posY, val);
            }
        }

        System.out.println(sb);
    }


    // simpler
    private static void buildX(int[][] tree, int[][] forest, int vx, int startX, int endX, int n) {

        if(startX == endX) {
            buildY(tree, forest, vx, startX, endX, 0, 1, n);
        } else {
            int mid = (startX + endX) / 2;
            buildX(tree, forest, 2 * vx + 1, startX, mid, n);
            buildX(tree, forest, 2 * vx + 2, mid + 1, endX, n);
            buildY(tree, forest, vx, startX, endX, 0, 1, n);
        }

    }

    // complex

    private static void buildY(int[][] tree, int[][] forest, int vx, int startX, int endX, int vy, int startY, int endY) {

        if(startY == endY) {
            if(startX == endX) {
                tree[vx][vy] = forest[startX][startY];
            } else {
                tree[vx][vy] = tree[2 * vx + 1][vy] + tree[2 * vx + 2][vy];
            }
        } else {
            int mid = (startY + endY) / 2;
            buildY(tree, forest, vx, startX, endX, 2 * vy + 1, startY, mid);
            buildY(tree, forest, vx, startX, endX, 2 * vy + 2, mid + 1, endY);
            tree[vx][vy] = tree[vx][2 * vy + 1] + tree[vx][2 * vy + 2];
        }
    }

    private static int queryX(int[][] tree,
                               int vx,
                               int x1, int y1, int startX, int endX,
                               int x2, int y2, int n) {

        if(endX < x1 || startX > x2) return 0;
        if(startX >= x1 && endX <= x2) {
            return queryY(tree, vx, 0, y1, y2, 1, n);
        }

        int mid = (startX + endX) / 2;
        return queryX(tree, 2 * vx + 1, x1, y1, startX, mid,  x2, y2, n)
                + queryX(tree, 2 * vx + 2, x1, y1, mid + 1, endX, x2, y2, n);
    }

    private static int queryY(int[][] tree,
                              int vx,
                              int vy,
                              int y1, int y2, int startY, int endY) {

        if(endY < y1 || startY > y2) return 0;
        if(startY >= y1 && endY <= y2) {
            return tree[vx][vy];
        }

        int mid = (startY + endY) / 2;
        return queryY(tree, vx, 2 * vy + 1, y1, y2, startY, mid)
                + queryY(tree, vx, 2 * vy + 2, y1, y2, mid + 1, endY);
    }

    private static void updateX(int[][] tree,
                              int vx,
                              int startX, int endX,
                              int n, int posX, int posY, int val) {

        if(startX == endX) {
            updateY(tree, vx, 0, startX, endX, 1, n, posY, val);
        } else {
            int mid = (startX + endX) / 2;
            if(posX <= mid) {
                updateX(tree, 2 * vx + 1, startX, mid, n, posX, posY, val);
            } else {
                updateX(tree, 2 * vx + 2, mid + 1, endX, n, posX, posY, val);
            }
            updateY(tree, vx, 0, startX, endX, 1, n, posY, val);
        }
    }

    private static void updateY(int[][] tree,
                              int vx,
                              int vy, int startX, int endX, int startY, int endY, int posY, int val) {

        if(startY == endY) {
            if(startX == endX) {
                tree[vx][vy] += val;
            } else {
                tree[vx][vy] = tree[2 * vx + 1][vy] + tree[2 * vx + 2][vy];
            }
        } else {
            int mid = (startY + endY) / 2;
            if(posY <= mid) {
                updateY(tree, vx, 2 * vy + 1, startX, endX, startY, mid, posY, val);
            } else {
                updateY(tree, vx, 2 * vy + 2, startX, endX, mid + 1, endY, posY, val);
            }
            tree[vx][vy] = tree[vx][2 * vy + 1] + tree[vx][2 * vy + 2];
        }
    }

}
