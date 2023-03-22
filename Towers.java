import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Towers {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        long[] cubes = new long[n];

        for(int i = 0; i < n; i++) cubes[i] = sc.nextLong();

        TreeMap<Long, Integer> towers = new TreeMap<>();
        long ans  = 0;

        for(long cube : cubes) {
            Long localHighest = towers.higherKey(cube);
            if(localHighest != null) {
                towers.put(localHighest, towers.get(localHighest) - 1);
                if(towers.get(localHighest) == 0) {
                    towers.remove(localHighest);
                }
            } else {
                ans++;
            }
            towers.put(cube, towers.getOrDefault(cube, 0) + 1);
        }

        System.out.println(ans);

    }
}
