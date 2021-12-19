import java.util.stream.IntStream;

public class ThreadSafeHelper extends Thread{

    private final ForgettingMap<String, Integer> forgettingMap;

    public ThreadSafeHelper(final ForgettingMap<String, Integer> forgettingMap) {
        this.forgettingMap = forgettingMap;
    }

    @Override
    public void run() {
        String key = currentThread().getName();
        // a sequence of primitive int-valued elements supporting sequential and parallel aggregate operations
        IntStream.range(0, 10).forEach(i -> {
            System.out.println((key + i) + ", " + i);
            forgettingMap.add(key + i, i);
        });
    }
}
