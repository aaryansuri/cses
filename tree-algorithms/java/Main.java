import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) throws URISyntaxException, InterruptedException {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);


        System.out.println(
                atomicBoolean
        );

        Runnable lock = () -> {
            while (!atomicBoolean.compareAndSet(false, true)) {
                System.out.println("in loop " + Thread.currentThread().getName());
            }
        };

        Thread t1 = new Thread(lock, "t1");
        Thread t2 = new Thread(lock, "t2");
        t1.start();
        t2.start();
        Thread.sleep(5000);
        atomicBoolean.set(false);
        t1.join();
        t2.join();



        System.out.println(atomicBoolean);
    }
}