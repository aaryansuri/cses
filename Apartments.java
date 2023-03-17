import java.util.Arrays;
import java.util.Scanner;

// one case failed
public class Apartments {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); int m = sc.nextInt(); long k = sc.nextLong();
        sc.nextLine();

        String input = sc.nextLine()
            + "\n"
            + sc.nextLine();


        long[] applicants = Arrays.stream(input.split("\n")[0].split(" ")).mapToLong(Long::parseLong).toArray();
        long[] apartments = Arrays.stream(input.split("\n")[1].split(" ")).mapToLong(Long::parseLong).toArray();
        Arrays.sort(applicants); Arrays.sort(apartments);

        int i = 0;
        int j = 0;
        int cnt = 0;


        while (i < applicants.length && j < apartments.length) {

            if(Math.abs(apartments[j] - applicants[i]) <= k) {
                i++;
                j++;
                cnt++;
            } else if(applicants[i] < apartments[j]) {
                i++;
            } else if(applicants[i] > apartments[j]) {
                j++;
            }

        }

        System.out.println(cnt);



    }
}
