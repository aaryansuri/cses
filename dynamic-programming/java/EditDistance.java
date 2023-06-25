import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EditDistance {

    public static void main(String[] args) {

//        Scanner sc = new Scanner(System.in);
//
//        String one = sc.nextLine();
//        String two = sc.nextLine();
//
//        System.out.println(distanceIDP(one, two, one.length(), two.length()));

        List<String> result = new ArrayList<>();
        generateParenthesis(result, "(", 3, 1, 0);
        System.out.println(result);

    }

    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        generateParenthesis(result, "(", n, 1, 0);
        return result;
    }

    public static void generateParenthesis(List<String> result, String curr, int n, int opening, int closing) {

        if(closing == n) {
            result.add(curr);
            return;
        }

        if(opening > closing) {
            generateParenthesis(result, curr + ")", n, opening, closing + 1);
            generateParenthesis(result, curr + "(", n, opening + 1, closing);
        }

        if(opening == closing) {
            generateParenthesis(result, curr + "(", n, opening + 1, closing);
        }

    }

    private static int distance(String one, String two, int c1, int c2) {

        if(c1 == -1 && c2 == -1) return 0;
        if(c1 == -1) return c2 + 1;
        if(c2 == -1) return c1 + 1;

        if(one.charAt(c1) == two.charAt(c2)) return distance(one, two, c1 - 1, c2 - 1);

        int delete = distance(one, two, c1 - 1, c2);
        int update = distance(one, two, c1 - 1, c2 - 1);
        int add = distance(one , two, c1 , c2 - 1);

        return 1 + Math.min(delete, Math.min(update, add));
    }

    private static int distanceIDP(String one, String two, int c1, int c2) {

        int[][] dp = new int[c1 + 1][c2 + 1];

        for (int i = 0; i <= one.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= two.length(); j++) {
            dp[0][j] = j;
        }

        for(int i = 1; i <= c1; i++) {
            for(int j = 1; j <= c2; j++) {
                if(one.charAt(i-1) == two.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i-1][j],dp[i-1][j-1]), dp[i][j-1]);
                }
            }
        }

       return dp[c1][c2];
    }

}
