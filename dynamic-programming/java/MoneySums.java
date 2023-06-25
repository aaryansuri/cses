import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MoneySums {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] arr = new int[n];

        for(int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();;
        }

        Set<Integer> set = new HashSet<>();
        set.add(0);

        for(int num : arr) {
            Set<Integer> temp = new HashSet<>();
            for(int ele : set) {
                temp.add(ele + num);
            }
            set.addAll(temp);
        }


        set.remove(0);
        TreeSet<Integer> res = new TreeSet<>(set);
        System.out.println(res.size());
        System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));

    }
}
