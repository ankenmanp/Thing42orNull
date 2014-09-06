import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Simple implementation of the Thing42orNull interface.
 * Uses a HashMap to hold peers and an ArrayList to represent the pool.
 * Duplicate peers are not allowed. 
 * 
 * @author Jamie Wohletz
 * @version 8/21/14
 */
public class Thing42<K, D> implements Thing42orNull<K, D> {
    //Fields
    //Immutable
    private final K KEY;
    private final long LEVEL;
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
     * Thing42 constructor.
     */
    public Thing42(K key, long level, D data) {
        this.KEY = key;
        this.LEVEL = level;
        this.data = data;
        this.peers = new HashMap<K, ArrayList<Thing42orNull<K, D>>>(0);
        this.pool = new ArrayList<Thing42orNull<K, D>>(0); 
    }
    /** 
     * Add a peer to this object's peers. Accepts duplicates.
     * 
     * @param newPeer the peer to add
     * @throws NullPointerException if newPeer is null
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
     * Append a Thing42 object to this object's pool. 
     * Duplicate Thing42 objects are accepted. 
     * 
     * @param newMember the object to add to this object's pool collection. 
     */
    public void appendToPool(Thing42orNull<K, D> newMember) throws NullPointerException {
        if(newMember == null) {
            throw new NullPointerException();
        }
        pool.add(newMember); 
    }
    /**
     * Get this object's data.
     *
     * @return this object's data.
     */
    public D getData() {
        return data;
    }
    /**
     * Get this object's key.
     * 
     * @return this object's key.
     */
    public K getKey() {
        return KEY;
    }
    /**
     * Get this object's level.
     * 
     * @return this object's level.
     */
    public long getLevel() {
        return LEVEL;
    }
    /**
     * Access a peer matching the given key.
     * 
     * @param key the key to find a peer with
     * @return a peer with the given key, or null if no matching peers found
     */
    public Thing42orNull<K, D> getOnePeer(K key) {
        if(!peers.containsKey(key) || peers.get(key).size() == 0) {
            return null;
        }
        
        //We always just return the first one in the list.
        return peers.get(key).get(0); 
    }
    /**
     * Access all peers known by this object.
     * 
     * @return every peer stored in this object
     */
    public Collection<Thing42orNull<K, D>> getPeersAsCollection() {
        Iterator iterator = peers.keySet().iterator();
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
     * Access all peers corresponding to the given key. 
     * 
     * @param key the key to match peers against
     * @return the peers matching the given key or a collection with size() == 0 if none found.
     */
    public Collection<Thing42orNull<K, D>> getPeersAsCollection(K key) {
        if(!peers.containsKey(key)) {
            return new ArrayList<Thing42orNull<K, D>>(0);
        }
        return peers.get(key); 
    }
    /**
     * Access the pool as a list.
     * 
     * @return this object's pool, or a List with size() == 0 if pool is empty
     */
    public List<Thing42orNull<K, D>> getPoolAsList() {
        return pool; 
    }
    /**
     * Remove the first occurence of an object from the pool.
     * 
     * @param member the object to remove
     * @throws NullPointerException if the given member is null
     * @return true if the member was removed, false if not
     */
    public boolean removeFromPool(Thing42orNull<K, D> member) throws NullPointerException {
        if(member == null) {
            throw new NullPointerException(); 
        }
        
        return pool.remove(member); 
    }
    /**
     * Remove a peer from this object's peers collection. 
     * 
     * @param peer the peer to remove
     * @throws NullPointerException if the given peer is null
     * @return whether the peer was successfully removed
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
     * Update this Thing42's data. 
     * 
     * @param newData the object to use as data
     */
    public void setData(D newData) {
        this.data = newData;
    }
    
	/**
	 * Check if this Thing42 equals Object o.
	 *
	 * @param o - the object to check for equality
	 * @return true if this is equal to o, false otherwise
	 */
	@Override
	public boolean equals(Object o){
        Thing42<K, D> thing = (Thing42<K, D>) o;

		if (this == o)
        {
            return true;
        }
        if (! (o instanceof Thing42))
        {
            return false;
        }
        return LEVEL == thing.getLevel()
            && KEY.equals(thing.getKey())
            && data.equals(thing.getData())
            && this.getPeersAsCollection().equals(thing.getPeersAsCollection())
            && this.getPoolAsList().equals(thing.getPoolAsList());
	}
	
	/**
	 * Compute this Thing42's hashcode.
	 *
	 * @return hash code for this Thing42
	 */
	@Override
	public int hashCode(){
		int prime = 17;
		int result = 1;
		Long l = Long.valueOf(LEVEL);
		// to avoid NPE, if key or data is null, use 0, if not, use their hash
		result = prime * result + ((KEY == null) ? 0 : KEY.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((l == null) ? 0 : l.hashCode());
		return result;
	}
}
