import java.util.Scanner;

public class Permutations {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        if (n == 1) {
            System.out.println(1);
            return;
        }

        if(n == 2 || n == 3) {
            System.out.println("NO SOLUTION");
            return;
        }

        if(n == 4) {
            System.out.println("2 4 1 3");
            return;
        }

        int odd = 1;    int even = 2;

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        while (odd <= n) {
            sb1.append(odd).append(" ");
            odd += 2;
        }

        while (even <= n) {
            sb2.append(even).append(" ");
            even += 2;
        }

        System.out.println(sb1 + String.valueOf(sb2));

    }
}
