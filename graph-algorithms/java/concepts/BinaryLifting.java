package concepts;

import java.util.Arrays;

public class BinaryLifting {

    static int[][] ancestors;

    public static void main(String[] args) {

        int[] parent = new int[]{-1, 0, 0, 1, 1, 2, 2};
        int n = parent.length;

        initialize(n, parent);
        Arrays.stream(ancestors).map(Arrays::toString).forEach(System.out::println);
        System.out.println(getKthAncestor(3, 1));

    }

    public static void initialize(int n, int[] parent) {
        int m = (int) (Math.log(n) / Math.log(2));

        ancestors = new int[n][m + 1];

        for(int i = 0; i < n; i++) {
            ancestors[i][0] = parent[i];
        }

        for(int j = 1; j <= m; j++) {
            for(int i = 0; i < n; i++) {
                ancestors[i][j] = ancestors[i][j-1] == -1 ? - 1 : ancestors[ancestors[i][j-1]][j-1];
            }
        }
    }


    public static int getKthAncestor(int node, int k) {

        int col = 0;

        while(k != 0) {
            if((k & 1) == 1) {
                node = ancestors[node][col];
            }
            col = col + 1;
            k = k >> 1;
        }

        return node;
    }

}
