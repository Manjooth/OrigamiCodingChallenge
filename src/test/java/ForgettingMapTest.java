import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ForgettingMapTest {

    public static final String SUCCESS = "Success";
    private static ForgettingMap<String, String> forgettingMap;
    private static final int MAP_SIZE = 2;

    @BeforeEach
    void setUp()
    {
        forgettingMap = new ForgettingMap<>(MAP_SIZE);
        forgettingMap.add("StringKey", "StringValue");
    }

    @Test
    void shouldBeAbleToAddAssociation()
    {
        String response = forgettingMap.add("StringKey1", "StringValue1");
        assertEquals(SUCCESS, response);
    }

    @Test
    void shouldDoNothingIfAssociationAlreadyContainsKey()
    {
        String response = forgettingMap.add("StringKey", "StringValue");
        assertEquals("Forgetting Map already contains key", response);
    }

    @Test
    void shouldAddAssociationWhenMapBelowMaximumCapacity()
    {
        String response = forgettingMap.add("StringKey2", "StringValue2");
        assertEquals(SUCCESS, response);
    }

    @Test
    void shouldRemoveLeastPopularAssociationWhenMapFullTest()
    {
        forgettingMap.add("StringKey1", "StringValue1");
        forgettingMap.add("StringKey2", "StringValue2");
        String response = forgettingMap.add("StringKey3", "StringValue3");

        assertEquals(SUCCESS, response);
    }

    @Test
    void shouldReturnNullWhenCannotFindAssociation()
    {
        String result = forgettingMap.find("Unknown");
        assertNull(result);
    }

    @Test
    void shouldFindAssociationAndUpdatePopularity()
    {
        String result = forgettingMap.find("StringKey");
        assertEquals("StringValue", result);
    }

    @Test
    void shouldBeThreadSafe() throws InterruptedException {
        final ForgettingMap<String, Integer> forgettingMap = new ForgettingMap<>(30);
        final ThreadSafeHelper threadSafeHelper = new ThreadSafeHelper(forgettingMap);

        ThreadSafeHelper thread1 = new ThreadSafeHelper(forgettingMap);
        ThreadSafeHelper thread2 = new ThreadSafeHelper(forgettingMap);
        ThreadSafeHelper thread3 = new ThreadSafeHelper(forgettingMap);

        thread1.start(); thread2.start(); thread3.start();

        while(thread1.isAlive() || thread2.isAlive() || thread3.isAlive()){
            Thread.sleep(1000);
        }

        IntStream.range(0, 10).forEach(i -> {
            // expected, actual
            assertEquals(i, forgettingMap.find("Thread-1" + i)); // for mac thread names start from 1, on ubuntu start from 1
            assertEquals(i, forgettingMap.find("Thread-2" + i));
            assertEquals(i, forgettingMap.find("Thread-3" + i));
        });
    }
}
