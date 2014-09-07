import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Simple implementation of the Thing42orNull interface.
 * Duplicate peers are not allowed. <br />
 * An object of type Thing42 has five attributes. There are two immutable attributes: a generic attribute known as 
 * its key and an integer-valued attribute known as its level. There is also a generic mutable attribute known as its data. 
 * In addition, each Thing42 object has an unordered collection, in the form of a HashMap, of Thing42 objects known as its peers as well as an ordered 
 * collection, in the form of an ArrayList, of Thing42 objects known as its pool.<br/><br/>
 *
 * <pre>  +-----------+
 *  |  Thing42  |
 *  +-----------+
 *  | Key       |
 *  | Level     |
 *  | Data      |
 *  | Peers...  |
 *  | Pool...   |
 *  +-----------+</pre>
 * <br/>
 * Objects of type Thing42 are created using a constructor with the following signature:<br/>
 * <span style="font-family: 'courier new', courier, monospace;">public Thing42(K key, long level, D data)</span>
 *
 * 
 * @author Jamie Wohletz
 * @author Stephen Yugel
 * @author Kim Bui
 * @author Eric Van Gelder
 * @author Sterling Zerr
 * @author Chris Moquin
 * @author Paul Ankenman
 * @version 8/21/14
 */
public class Thing42<K, D> extends Object implements Thing42orNull<K, D> {
    //Fields
    //Immutable
    private final K key;
    private final long level;
    //Mutable
    private D data;

    //We use a HashMap to store peers. The mapping for this map is the following:
    //K key -> Arraylist of peers with this key
    //Storing peers in this way has the following advantages:
    //1. O(1) peer lookup by key (note: finding a specific peer in the list of peers
    //   matching a given key is still O(n)). 
    //2. Accepts duplicate peers. 
    private HashMap<K, ArrayList<Thing42orNull<K, D>>> peers;
    //We use an ArrayList to represent the pool because it's simple and maintains order of insertion. 
    private ArrayList<Thing42orNull<K, D>> pool;
  
    /**
     * Constructor for objects of class Thing42.
     */
    public Thing42(K key, long level, D data) {
        this.key = key;
        this.level = level;
        this.data = data;
        this.peers = new HashMap<K, ArrayList<Thing42orNull<K, D>>>(0);
        this.pool = new ArrayList<Thing42orNull<K, D>>(0); 
    }
    /** 
     * Add a peer to this object. <br />Accepts duplicates.
     * 
     * @param newPeer the peer to add
     * @throws NullPointerException if the specified peer is null
     */
    public void addPeer(Thing42orNull<K, D> newPeer) throws NullPointerException {
        if(newPeer == null) {
            throw new NullPointerException();
        }
        
        K k = (K)newPeer.getKey();
        if(!peers.containsKey(k))
        {
            peers.put(k, new ArrayList<Thing42orNull<K, D>>());
        }
        peers.get(k).add(newPeer); 
    }
    /**
     * Append a member to the pool of this object. 
     * <br />Duplicate Thing42 objects are accepted. 
     * 
     * @param newMember the object to be appended to the pool
     * @throws NullPointerException if the specified item is null
     */
    public void appendToPool(Thing42orNull<K, D> newMember) throws NullPointerException {
        if(newMember == null) {
            throw new NullPointerException();
        }
        pool.add(newMember); 
    }
    /**
     * Access the data of this Thing42.
     *
     * @return the data of this object
     */
    public D getData() {
        return data;
    }
    /**
     * Access the key of this Thing42.
     * 
     * @return the key of this object
     */
    public K getKey() {
        return key;
    }
    /**
     * Access the level of this Thing42.
     * 
     * @return the level of this object
     */
    public long getLevel() {
        return level;
    }
    /**
     * Access a peer matching the specified key.
     * 
     * @param key the search key
     * @return any peer known by this object that matches the given key; null if no match
     */
    public Thing42orNull<K, D> getOnePeer(K key) {
        if(!peers.containsKey(key) || peers.get(key).size() == 0) {
            return null;
        }
        
        //We always just return the first one in the list.
        return peers.get(key).get(0); 
    }
    /**
     * Access all peers.
     * 
     * @return all peers known by this object; if no peers then returns a collection with size() == 0.
     */
    public Collection<Thing42orNull<K, D>> getPeersAsCollection() {
        Iterator<K> iterator = peers.keySet().iterator();
        ArrayList<Thing42orNull<K, D>> allPeers = new ArrayList<Thing42orNull<K, D>>(0);
        while(iterator.hasNext()) {
            K k = (K)iterator.next();
            for(Thing42orNull<K, D> peer: peers.get(k)){
                allPeers.add(peer); 
            }
        }
        return allPeers; 
    }
    /**
     * Access all peers matching the specified key. 
     * 
     * @param key the search key
     * @return all peers known by this object that match the given key;
     * if no peer matches then returns a collection with size() == 0.
     */
    public Collection<Thing42orNull<K, D>> getPeersAsCollection(K key) {
        if(!peers.containsKey(key)) {
            return new ArrayList<Thing42orNull<K, D>>(0);
        }
        return peers.get(key); 
    }
    /**
     * Access all members of the pool.
     * 
     * @return all members of the pool known by this object;
     * if no members then returns a List with size() == 0.
     */
    public List<Thing42orNull<K, D>> getPoolAsList() {
        return pool; 
    }
    /**
     * Remove a single instance of the specified object from this object's pool.
     * 
     * @param member the member to be removed from the pool
     * @return true if a pool member was removed as a result of this call
     * @throws NullPointerException if the specified parameter is null
     */
    public boolean removeFromPool(Thing42orNull<K, D> member) throws NullPointerException {
        if(member == null) {
            throw new NullPointerException(); 
        }
        
        return pool.remove(member); 
    }
    /**
     * Remove a single instance of the specified peer from this object.
     * 
     * @param peer the peer to be removed
     * @return true if a peer was removed as a result of this call
     * @throws NullPointerException if the specified peer is null
     */
    public boolean removePeer(Thing42orNull<K, D> peer) {
        if(peer == null) {
            throw new NullPointerException();
        }
        
        K k = (K)peer.getKey();
        if(!peers.containsKey(k)){
            return false;
        }
        
        return peers.get(k).remove(peer); 
    }
    /**
     * Modify the data of this Thing42.
     * 
     * @param newData the updated data for this object
     */
    public void setData(D newData) {
        this.data = newData;
    }
    
	/**
	 * Determines whether or not the specified Object
     * is equal to this Thing42. The specified Object
     * is equal to this Thing42 if it is an instance of
     * Thing42; if its level is the same as this Thing42;
     * and if its key, data, peers, and pool are the same
     * as this Thing42 via the equals predicate.
	 *
	 * @param obj an Object to be compared with this Thing42.
	 * @return true if obj is an instance of Thing42 and has
     * the same values; false otherwise.
     * @see #hashCode()
	 */
	@Override
    @SuppressWarnings("unchecked")
	public boolean equals(Object obj){
        Thing42<K, D> thing = (Thing42<K, D>) obj;

		if (this == obj)
        {
            return true;
        }
        if (! (obj instanceof Thing42))
        {
            return false;
        }
        return level == thing.getLevel()
            && key.equals(thing.getKey())
            && data.equals(thing.getData())
            && this.getPeersAsCollection().equals(thing.getPeersAsCollection())
            && this.getPoolAsList().equals(thing.getPoolAsList());
	}
	
	/**
	 * Returns the hashcode for this Thing42.
	 *
	 * @return the hashcode for this Thing42
	 */
	@Override
	public int hashCode(){
		int prime = 17;
		int result = 1;
		Long l = Long.valueOf(level);
		// to avoid NPE, if key or data is null, use 0, if not, use their hash
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + l.hashCode();
		result = prime * result + peers.hashCode();
		result = prime * result + pool.hashCode();
		return result;
	}
}
