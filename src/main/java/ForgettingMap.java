import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// K, V like generics
public final class ForgettingMap<K,V> implements ForgettingMapInterface<K,V>
{

    private final Map<K, V> associations = new ConcurrentHashMap<>(); // map to store associations
    private final Map<K, Integer> popularityOfAssociations = new ConcurrentHashMap<>(); // map to store popularity of given associations
    private final int maximumCapacity;

    /**
     Constructor that takes the max capacity as
     per the requirements.

     @param maximumCapacity is the max capacity of the forgetting map
     **/
    public ForgettingMap(final int maximumCapacity)
    {
        this.maximumCapacity = checkCapacity(maximumCapacity);
    }

    /**
     Add a key -> value pair to the associations map if the map is below the maximum capacity. Otherwise, remove the
     least popular key -> value pair and insert the new key -> value pair.

     Adding 'synchronized' means that when multiple threads try to access the method, it allows only one thread
     to have access at a time.

     @param key the key to be placed in the map
     @param value the value to be placed in the map
     @return if adding association was successful or not
     **/
    @Override
    public String add(final K key, final V value)
    {
        if (containsKey(key))
        {
            return "Forgetting Map already contains key";
        }
        else if(isBelowMaximumCapacity() && !containsKey(key))
        {
            addAssociationAndPopularity(key, value);
        }
        else
        {
            removeLeastUsedAssociation();
            addAssociationAndPopularity(key, value);
        }

        return "Success";
    }

    /**
     Find key, if one exists, and increment its popularity in the popularityOfAssociations map. If the key does not
     exist in the associations map, return null.

     Adding 'synchronized' means that when multiple threads try to access the method, it allows only one thread
     to have access at a time.

     @param key the key to search in the map
     @return the found corresponding value to the key
     **/
    @Override
    public V find(final K key)
    {
        if(containsKey(key))
        {
            updatePopularity(key);
            return associations.get(key);
        }
        return null;
    }

    public boolean contains(final K key){
        return associations.containsKey(key);
    }

    public int getPopularityScore(final K key){
        return popularityOfAssociations.get(key);
    }

    private int checkCapacity(final int maximumCapacity)
    {
        return Math.max(maximumCapacity, 0);
    }

    private void removeLeastUsedAssociation()
    {
        K leastUsed =  null;
        int count = Integer.MAX_VALUE;
        for(final K key : popularityOfAssociations.keySet())
        {
            if(popularityOfAssociations.get(key) < count)
            {
                count = popularityOfAssociations.get(key);
                leastUsed = key;
            }
        }
        // if there are multiple associations that are least used, remove the last one found (simple solution)
        // could also try and sort the hashmap and then remove the last element (would that be quicker or just more complex?)
        associations.remove(leastUsed);
        popularityOfAssociations.remove(leastUsed);
    }

    private void addAssociationAndPopularity(final K key, final V value)
    {
        associations.put(key, value);
        popularityOfAssociations.put(key, 0);
    }

    private void updatePopularity(final K key)
    {
        popularityOfAssociations.put(key, popularityOfAssociations.get(key) + 1);
    }

    private boolean containsKey(final K key)
    {
        return associations.containsKey(key);
    }

    private boolean isBelowMaximumCapacity()
    {
        return associations.size() < maximumCapacity;
    }
}
