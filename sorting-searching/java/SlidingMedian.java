import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeSet;
 
public class SlidingMedian {
 
    public static void main(String[] args) {
 
        Scanner sc = new Scanner(System.in);
 
        int n = sc.nextInt();   int k = sc.nextInt();
        long[] arr = new long[n];
 
        for(int i = 0; i < n; i++) arr[i] = sc.nextLong();
 
        StringBuilder sb = new StringBuilder();
 
        if(k == 1) {
            for(long num : arr) sb.append(num).append(" ");
            System.out.println(sb);
            return;
        }
 
        if(k == 2) {
            for(int i = 1; i < n; i++) sb.append(Math.min(arr[i-1], arr[i])).append(" ");
            System.out.println(sb);
            return;
        }
 
 
        Comparator<Pair> comp = Comparator.comparingLong(Pair::getEle).thenComparing(Pair::getPos);
        TreeSet<Pair> left = new TreeSet<>(comp);
        TreeSet<Pair> right = new TreeSet<>(comp);
 
        // 2 4 3 5 8 1 2 1
        List<Pair> firstKSorted = new ArrayList<>();
        for(int i = 0; i < k; i++) firstKSorted.add(new Pair(arr[i], i));
 
        firstKSorted.sort(comp);
 
        for(int i = 0; i < k/2 + k%2; i++) {
            left.add(firstKSorted.get(i));
        }
 
        for(int i = k/2 + k%2; i < k; i++) {
            right.add(firstKSorted.get(i));
        }
 
        sb.append(left.last()).append(" ");
 
        for(int i = 1; i < n - k + 1; i++) {
 
            Pair previous = new Pair(arr[i-1], i - 1);
 
            if(left.contains(previous)) {
                left.remove(previous);
            } else {
                right.remove(previous);
            }
 
            Pair curr = new Pair(arr[k-1+i], k-1+i);
 
            if(left.last().ele < curr.ele) {
                right.add(curr);
            } else {
                left.add(curr);
            }
 
            while (left.size() < k/2 + k%2) {
                left.add(right.first());
                right.remove(right.first());
            }
 
            while (left.size() > k/2 + k%2) {
                right.add(left.last());
                left.remove(left.last());
            }
 
            sb.append(left.last()).append(" ");
        }
 
        System.out.println(sb);
 
    }
 
    static class Pair {
        private long ele;    private int pos;
 
        public Pair(long ele, int pos) {
            this.ele = ele;
            this.pos = pos;
        }
 
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pair pair = (Pair) o;
            return ele == pair.ele && pos == pair.pos;
        }
 
        public long getEle() {
            return ele;
        }
 
        public int getPos() {
            return pos;
        }
 
        @Override
        public int hashCode() {
            return Objects.hash(ele, pos);
        }
 
        @Override
        public String toString() {
            return ele + "";
        }
    }
}