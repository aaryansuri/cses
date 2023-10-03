import java.util.ArrayList;
import java.util.List;

class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.countSmaller(new int[]{26,78,27,100,33,67,90,23,66,5,38,7,35,23,52,22,83,51,98,69,81,32,78,28,94,13,2,97,3,76,99,51,9,21,84,66,65,36,100,41}));
    }

    public List<Integer> countSmaller(int[] nums) {

        int n = nums.length;
        int[] segTree = new int[(n + 1) * 4];

        for(int i = 0; i < n; i++) {
            update(0, segTree, i + 1, nums[i], 1, n);
        }

        List<Integer> res = new ArrayList<>();

        for(int i = 1; i <= n; i++) {
            System.out.println(i);
            res.add(queryMaxAndLess(0, segTree, i + 1, n, 1, n, nums[i - 1]));
        }

        return res;
    }

    private void update(int v, int[] segTree, int pos, int val, int l, int r) {
        if(l == r) {
            segTree[v] = val;
            return;
        }

        int mid = (l + r) / 2;

        if(pos <= mid) update(2 * v + 1, segTree, pos, val, l , mid);
        else update(2 * v + 2, segTree, pos, val, mid + 1 , r);

        segTree[v] = Math.max(segTree[2 * v + 1], segTree[2 * v + 2]);
    }

    private int queryMaxAndLess(int v, int[] segTree, int qL, int qR, int l, int r, int val) {

        if(r < qL || l > qR) return 0;
        if(l >= qL && r <= qR && segTree[v] < val) return r - l + 1;
        if(l == r && segTree[v] >= val) return 0;

        int mid = (l + r) / 2;

        return queryMaxAndLess(2 * v + 1, segTree, qL, qR, l, mid, val) + queryMaxAndLess(2 * v + 2, segTree, qL, qR, mid + 1, r, val);

    }
}