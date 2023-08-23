import java.util.Arrays;
import java.util.Scanner;

public class Temp {

    static class Solution1 {

        public Solution1() {
        }

        public boolean checkArray(int[] nums, int k) {

            if(allZeroes(nums)) return true;

            boolean possible = false;

            for(int i = 0; i < nums.length - k; i++) {
                if(isSubArrayZero(nums, i, i + k) || nums[i] == 0) continue;
                if(checkArray(getArray(nums, i, i + k), k)) return true;
            }

            return false;

        }

        private int[] getArray(int[] nums, int i, int j) {
            for(int k = i; k < j; k++) nums[k] -= 1;
            return nums;
        }

        private boolean isSubArrayZero(int[] nums, int i, int j) {
            for(int k = i; k < j; k++) if(nums[k] != 0) return false;
            return true;
        }

        private boolean allZeroes(int[] nums) {
            for(int num : nums) {
                if(num != 0) return false;
            }
            return true;
        }
    }


    static class Solution {

        public Solution() {
        }

        public int maxNonDecreasingLength(int[] nums1, int[] nums2) {


            int n = nums1.length;
            int[] pickOne = new int[n];
            int[] pickTwo = new int[n];
            int[] pickOne2 = new int[n];
            int[] pickTwo2 = new int[n];

            pickOne[0] = nums1[0];  int a = 1;
            pickOne2[0] = nums1[0]; int b = 1;
            pickTwo[0] = nums2[0];  int c = 1;
            pickTwo2[0] = nums2[0]; int d = 1;

            int max = 1;


            for(int i = 1; i < nums1.length; i++) {

                int num1 = nums1[i];
                int num2 = nums2[i];

                if (num1 >= pickOne[i - 1]) {
                    pickOne[i] = num1;
                    a++;
                    max = Math.max(max, a);
                } else {
                    a = 1;
                    pickOne[i] = num1;
                }

                if (num2 >= pickOne2[i - 1]) {
                    pickOne2[i] = num2;
                    b++;
                    max = Math.max(max, b);
                } else {
                    b = 1;
                    pickOne2[i] = num2;
                }

                if (num1 >= pickOne2[i - 1]) {
                    pickOne2[i] = num1;
                    b++;
                    max = Math.max(max, b);
                } else {
                    b = 1;
                    pickOne2[i] = num1;
                }

                if (num1 >= pickTwo2[i - 1]) {
                    pickTwo2[i] = num1;
                    d++;
                    max = Math.max(max, d);
                } else {
                    d = 1;
                    pickTwo2[i] = num1;
                }

                if (num2 >= pickTwo[i - 1]) {
                    pickTwo[i] = num2;
                    c++;
                    max = Math.max(max, c);
                } else {
                    c = 1;
                    pickTwo[i] = num2;
                }

                if (num2 >= pickOne[i - 1]) {
                    pickOne[i] = num2;
                    a++;
                    max = Math.max(max, a);
                } else {
                    a = 1;
                    pickOne[i] = num2;
                }

                if (num1 >= pickTwo[i - 1]) {
                    pickTwo[i] = num1;
                    c++;
                    max = Math.max(max, c);
                } else {
                    c = 1;
                    pickTwo[i] = num1;
                }

            }

            return max;
        }
    }

//    static class Solution {
//
//        public Solution() {
//        }
//
//        public int numTrees(int n) {
//            Math.max(1,2);
//            return numTrees(n, (1 << n) - 1, n);
//        }
//
//        public int numTrees(int n, int mask, int N) {
//
//            if(mask == 0 && n > 1) return 0;
//            if(mask == 0 && n == 1) return 1;
//
//            int trees = 0;
//
//            for(int i = 1; i <= N; i++) {
//                if((mask & (1 << (i - 1))) == 0) continue;
//                int lowerMask = ((1 << (i - 1)) - 1) & mask;
//                int higherMask = mask >> i;
//                higherMask = higherMask << i;
//                trees += numTrees(n - 1, lowerMask, N);
//                trees += numTrees(n - 1, higherMask, N);
//            }
//
//            return trees;
//        }
//
//    }
    public static void main(String[] args) {


//        Solution s = new Solution();
//        System.out.println(s.maxNonDecreasingLength(new int[]{3,8}, new int[]{15,2}));

        Solution1 solution1 = new Solution1();
        System.out.println(solution1.checkArray(new int[]{2,2,3,1,1,0}, 3));

    }
}
