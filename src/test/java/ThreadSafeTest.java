import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ThreadSafeTest {

    private static ForgettingMap<String, Integer> forgettingMap;
    private static final int MAP_SIZE = 30; // 3 threads, 10 calls each so 3 x 10 = 30

    @BeforeEach
    void setUp() throws InterruptedException {
        forgettingMap = new ForgettingMap<>(MAP_SIZE);

        ThreadSafe thread1 = new ThreadSafe(forgettingMap);
        ThreadSafe thread2 = new ThreadSafe(forgettingMap);
        ThreadSafe thread3 = new ThreadSafe(forgettingMap);

        thread1.start();
        thread2.start();
        thread3.start();

        while(thread1.isAlive() || thread2.isAlive() || thread3.isAlive()){
            // wait(); <-- didn't work?
            Thread.sleep(1000);
        }
    }

    @Test
    void checkingForgottenMapIsThreadSafe(){
        IntStream.range(0, 10).forEach(i -> {
            // expected, actual
            assertEquals(i, forgettingMap.find("Thread-0" + i)); // for mac thread names start from 1, on ubuntu start from 1
            assertEquals(i, forgettingMap.find("Thread-1" + i));
            assertEquals(i, forgettingMap.find("Thread-2" + i));
        });
    }
}
