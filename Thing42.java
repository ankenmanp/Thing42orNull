import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Simple implementation of the Thing42orNull interface.
 * Uses a HashSet to hold peers and an ArrayList to represent the pool.
 * Duplicate peers are not allowed. 
 * 
 * @author Jamie Wohletz
 * @version 8/21/14
 */
public class Thing42<K,D> implements Thing42orNull<K,D> {
	//Fields
	//Immutable
	private final K key;
	private final long level;
	//Mutable
	private D data;

	//We use HashSet because it's a fast, unordered Collection implementation. 
	//Note that using this disallows us from storing duplicate peers. 
	private HashSet<Thing42orNull> peers;
	//We use an ArrayList to represent the pool because it's simple and maintains order of insertion. 
	private ArrayList<Thing42orNull> pool;
  
	/**
	 * Thing42 constructor.
	 */
	public Thing42(K key, long level, D data) {
		this.key = key;
		this.level = level;
		this.data = data;
		this.peers = new HashSet<Thing42orNull>(0);
		this.pool = new ArrayList<Thing42orNull>(0); 
	}
	/** 
	 * Add a Thing42 object to this object's "peers" collection. 
	 * The collection does not accept duplicate Thing42 objects. 
	 * 
	 * @param newPeer the object to add to this object's peers collection. 
	 */
	public void addPeer(Thing42orNull newPeer) throws NullPointerException {
		if(newPeer == null) {
			throw new NullPointerException();
		}
		peers.add((Thing42)newPeer);
	}
	/**
	 * Append a Thing42 object to this object's pool. 
	 * Duplicate Thing42 objects are accepted. 
	 * 
	 * @param newMember the object to add to this object's pool collection. 
	 */
	public void appendToPool(Thing42orNull newMember) throws NullPointerException {
		if(newMember == null) {
			throw new NullPointerException();
		}
		pool.add((Thing42)newMember); 
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
		return key;
	}
	/**
	 * Get this object's level.
	 * 
	 * @return this object's level.
	 */
	public long getLevel() {
		return level;
	}
	/**
	 * Get a single instance of a peer stored in this object with the given key. 
	 * Order is not guaranteed. 
	 * 
	 * @param key a key to search for a peer with
	 * @return a peer matching the given key, or null if no match
	 */
	public Thing42orNull getOnePeer(K key) {
		for(Thing42orNull thing: peers) {
			if(thing.getKey().equals(key)) {
				return thing;
			}
		}
		return null; 
	}
	/**
	 * Get the collection of peers associated with this object.
	 * 
	 * @return a collection containing all the peers known by this object (collection size() will equal 0 if no peers found)
	 */
	public Collection<Thing42orNull> getPeersAsCollection() {
		return peers; 
	}
	/**
	 * Get every peer with the given key. 
	 * 
	 * @param key The key to search for peers with
	 * @return a collection of peers matching the given key (collection size() will equal 0 if no peers found)
	 */
	public Collection<Thing42orNull> getPeersAsCollection(K key) {
		HashSet<Thing42orNull> peersWithKey = new HashSet<Thing42orNull>(0);
		for(Thing42orNull thing: peers) {
			if(thing.getKey().equals(key)) {
				peersWithKey.add(thing);
			}
		}
		return peersWithKey; 
	}
	/**
	 * Access the pool as a list.
	 * 
	 * @return this object's pool, or a List with size() == 0 if pool is empty
	 */
	public List<Thing42orNull> getPoolAsList() {
		return pool; 
	}
	/**
	 * Remove the first occurence of an object from the pool.
	 * 
	 * @param member the object to remove
	 * @throws NullPointerException if the given member is null
	 * @return true if the member was removed, false if not
	 */
	public boolean removeFromPool(Thing42orNull member) throws NullPointerException {
		if(member == null) {
			throw new NullPointerException(); 
		}
		
		return pool.remove(member); 
	}
	/**
	 * Remove the only instance of an object from this object's peers. 
	 * 
	 * @param peer The object to remove
	 * @throws NullPointerException if peer is null
	 * @return true if object was removed, false otherwise
	 */
	public boolean removePeer(Thing42orNull peer) {
		if(peer == null) {
			throw new NullPointerException();
		}
		return peers.remove(peer); 
	}
	/**
	 * Update this Thing42's data. 
	 * 
	 * @param newData the object to use as data
	 */
	public void setData(D newData) {
		this.data = newData;
	}
}
