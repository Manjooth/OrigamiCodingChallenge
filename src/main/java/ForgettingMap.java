import java.util.HashMap;
import java.util.Map;

// K, V like generics
public class ForgettingMap<K,V> implements ForgettingMapInterface<K,V>{

    private final Map<K, V> associations = new HashMap<>();
    private final Map<K, Integer> popularityOfAssociations = new HashMap<>();
    private final int maximumCapacity;
    private int size = 0;

    public ForgettingMap(final int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    @Override
    public synchronized void add(K key, V value) {
        // do I even update once trying to add or once find is called
        if (isBelowMaximumCapacity() && containsKey(key)) {
            updatePopularity(key); // does this need to be done? (need to make an assumption)
        } else if(isBelowMaximumCapacity() && !containsKey(key)) {
            addAssociationAndPopularity(key, value);
        } else {
            removeLeastUsedAssociation();
            addAssociationAndPopularity(key, value);
        }
    }

    @Override
    public synchronized V find(K key) {
        if(containsKey(key)){
            updatePopularity(key);
            return associations.get(key);
        }
        return null;
    }

    private void removeLeastUsedAssociation() {
        K leastUsed =  null;
        int count = Integer.MAX_VALUE;
        for(K key : popularityOfAssociations.keySet()){
            if(popularityOfAssociations.get(key) < count){
                count = popularityOfAssociations.get(key);
                leastUsed = key;
            }
        }
        // if there are multiple associations that are least used, remove the last one found (simple solution)
        // could also try and sort the hashmap and then remove the last element (would that be quicker or just more complex?)
        associations.remove(leastUsed);
        popularityOfAssociations.remove(leastUsed);

        size--;
    }

    private void addAssociationAndPopularity(K key, V value) {
        associations.put(key, value);
        popularityOfAssociations.put(key, 0);
        size++;
    }

    private void updatePopularity(K key) {
        popularityOfAssociations.put(key, popularityOfAssociations.get(key) + 1);
    }

    private boolean containsKey(K key) {
        return associations.containsKey(key);
    }

    private boolean isBelowMaximumCapacity() {
        return associations.size() < maximumCapacity;
    }

    public int getSize(){
        return size;
    }
}
