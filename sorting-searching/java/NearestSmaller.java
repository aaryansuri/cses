import java.util.Scanner;
import java.util.Stack;

public class NearestSmaller {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] arr = new int[n + 1]; int[] pos = new int[n + 1];

        for(int i = 1;i <= n; i++){
            arr[i] = sc.nextInt();
            pos[i] = i;
        }

        Stack<Integer> s = new Stack<>();
        StringBuilder sb = new StringBuilder();
        s.push(pos[0]);

        for(int i = 1; i <= n; i++) {
            while (!s.empty() && arr[i] <= arr[pos[s.peek()]]) s.pop();
            sb.append(s.peek()).append(" ");
            s.push(i);
        }

        System.out.println(sb);

    }
}
