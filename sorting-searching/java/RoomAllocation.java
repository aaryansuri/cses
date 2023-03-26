import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class RoomAllocation {


    static class Customer {
        long arrival;
        long departure;
        int pos;

        public Customer(long arrival, long departure, int pos) {
            this.arrival = arrival;
            this.departure = departure;
            this.pos = pos;
        }


    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        List<Customer> customers = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            long arrival = input.nextLong();
            long departure = input.nextLong();
            customers.add(new Customer(arrival, departure, i));
        }
        input.close();

        customers.sort((c1, c2) -> {
            if (c1.arrival == c2.arrival) {
                return Long.compare(c1.departure, c2.departure);
            }
            return Long.compare(c1.arrival, c2.arrival);
        });

        //customers.forEach(System.out::println);

       TreeMap<Long, TreeSet<Long>> pool = new TreeMap<>();
       long[] roomAnswer = new long[n];

       long rooms = 0;


        for(Customer customer : customers) {

            Long lowest = pool.lowerKey(customer.arrival);


            if(lowest == null) {
                TreeSet<Long> existing = pool.getOrDefault(customer.departure, new TreeSet<>());
                existing.add(++rooms);
                pool.put(customer.departure, existing);
                roomAnswer[customer.pos] = rooms;

            } else {
                TreeSet<Long> existing = pool.get(lowest);
                long usePrevious = existing.first();
                existing.remove(usePrevious);
                if(existing.size() == 0) {
                    pool.remove(lowest);
                }

                TreeSet<Long> existingDep = pool.getOrDefault(customer.departure, new TreeSet<>());
                existingDep.add(usePrevious);
                pool.put(customer.departure, existingDep);
                roomAnswer[customer.pos] = usePrevious;
            }

        }


        StringBuilder sb = new StringBuilder();
        sb.append(rooms).append("\n");
        for (long num : roomAnswer) {
            sb.append(num).append(" ");
        }
        System.out.println(sb);
    }


}