import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeMap;

public class MovieFestival2 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   int k = sc.nextInt();

        Movie[] movies = new Movie[n];

        for(int i = 0; i < n; i++) {
            movies[i] = new Movie(sc.nextInt(), sc.nextInt());
        }

        Arrays.sort(movies, Comparator.comparingInt(Movie::getEnd).thenComparing(Movie::getStart));

        TreeMap<Integer, Integer> map = new TreeMap<>();

        for(int i = 1; i <= k; i++) {
            map.merge(0, 1, Integer::sum);
        }

        int watchables = 0;

        for(int i = 0; i < n; i++) {

            int currStart = movies[i].start;
            Integer lowerEndedMovie = map.floorKey(currStart);

            if(lowerEndedMovie == null) continue;

            map.put(lowerEndedMovie, map.get(lowerEndedMovie) - 1);
            if(map.get(lowerEndedMovie) == 0) map.remove(lowerEndedMovie);

            map.merge(movies[i].end, 1, Integer::sum);
            watchables++;
        }

        System.out.println(watchables);

    }

    static class Movie {
        int start; int end;
        public Movie(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }
    }

}
