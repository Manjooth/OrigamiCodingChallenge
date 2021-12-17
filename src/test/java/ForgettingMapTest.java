import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForgettingMapTest {

    private static ForgettingMap<String, String> forgettingMap;
    private static final int MAP_SIZE = 1;

    private static final String KEY = "StringKey";
    private static final String VALUE = "StringValue";
    private static final String KEY1 = "StringKey1";
    private static final String VALUE1 = "StringValue1";

    @BeforeEach
    void setUp() {
        forgettingMap = new ForgettingMap<>(MAP_SIZE);
    }

    @Test
    void addAssociationToForgettingMapTest(){
        // expected, actual
        // check map is empty
        assertEquals(0, forgettingMap.getSize());
        // add association to map
        forgettingMap.add(KEY, VALUE);
        // check map contains 1 association
        assertEquals(1, forgettingMap.getSize());
    }

    @Test
    void findAssociationFromForgettingMapTest(){
        // add association to map
        forgettingMap.add(KEY, VALUE);
        // check map contains 1 association
        assertEquals(1, forgettingMap.getSize());
        // check find returns VALUE
        assertEquals(VALUE, forgettingMap.find(KEY));
    }

    @Test
    void findUnknownAssociationFromForgettingMap(){
        assertNull(forgettingMap.find("UNKNOWN"));
    }

    @Test
    void removeAssociationWhenMapFullTest(){
        // check map is empty
        assertEquals(0, forgettingMap.getSize());
        // add association to map
        forgettingMap.add(KEY, VALUE);
        // check map contains 1 association
        assertEquals(1, forgettingMap.getSize());
        // check find returns VALUE
        assertEquals(VALUE, forgettingMap.find(KEY));

        // add another association --> max size is 1 so should remove KEY, VALUE
        forgettingMap.add(KEY1, VALUE1);
        // check map contains only 1 association
        assertEquals(1, forgettingMap.getSize());
        // check find returns VALUE1
        assertEquals(VALUE1, forgettingMap.find(KEY1));
    }
}
