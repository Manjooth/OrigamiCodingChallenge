import java.util.Map;

/*
Type parameters:
<K> – the type of keys maintained by this map
<V> – the type of mapped values
*/
public interface ForgettingMapInterface<K, V> {

    void add(K key, V value); // add an association
    V find(K key); // find content using the specified key

}
