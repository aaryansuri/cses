/* IMPORTANT: Multiple classes and nested static classes are supported */



//imports for BufferedReader
import java.io.BufferedReader;
import java.io.InputStreamReader;

//import for Scanner and other utility classes
import java.util.*;


// Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail

class TestClass {
    public static void main(String args[] ) throws Exception {
        /* Sample code to perform I/O:
         * Use either of these methods for input

        //BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = br.readLine();                // Reading input from STDIN
        System.out.println("Hi, " + name + ".");    // Writing output to STDOUT

            */
        //Scanner
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();
        sc.nextLine();

        StringBuilder sb = new StringBuilder();

        while(t --> 0) {
            int n = sc.nextInt();   int m = sc.nextInt();
            sc.nextLine();
            String s1 = sc.nextLine();
            String s2 = sc.nextLine();
            sb.append(makeSameAsB(s1, s2)).append("\n");
        }

        System.out.println(sb);

        // Write your code here

    }


    private static int makeSameAsB(String A, String B) {

        int[] mapA = new int[26];
        int[] mapB = new int[26];

        Set<Character> bC = new HashSet<>();

        for(char c : B.toCharArray()) {
            bC.add(c);
            mapB[c - 'a']++;
        }

        int min = Integer.MAX_VALUE;

        for(char c : A.toCharArray()) {
            if(bC.contains(c)) {
                mapA[c - 'a']++;
            }
        }


        for(int i : mapA) {
            if(i == 0) continue;
            min = Math.min(i, min);
        }

        return min;

    }
}
