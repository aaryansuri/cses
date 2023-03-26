import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class TrafficLights {



    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        long x = sc.nextLong(); long n = sc.nextLong();
        long[] lights = new long[(int)n];

        for(int i = 0; i < n; i++) lights[i] = sc.nextLong();

        TreeSet<Long> lightPool = new TreeSet<>();
        TreeMap<Long, Long> lengthsPool = new TreeMap<>();

        lightPool.add(0L);   lightPool.add(x);
        lengthsPool.put(x, 1L);

        StringBuilder sb = new StringBuilder();

        for(long light : lights) {

            Long lower = lightPool.lower(light);
            Long higher = lightPool.higher(light);
            lightPool.add(light);

            if(lower != null && higher != null) {
                long l1 = light - lower;
                long l2 = higher - light;

                long oldLength = higher - lower;
                Long count = lengthsPool.get(oldLength);
                if (count != null) {
                    if (count == 1) {
                        lengthsPool.remove(oldLength);
                    } else {
                        lengthsPool.put(oldLength, count - 1);
                    }
                }
                lengthsPool.merge(l1, 1L, Long::sum);
                lengthsPool.merge(l2, 1L, Long::sum);
            }
            sb.append(lengthsPool.lastEntry().getKey()).append(" ");

        }

        System.out.println(sb);
    }
}
