import java.util.Collection;
import java.util.List;

/**
 * This interface specifies the required methods for <code>Thing42</code> objects.  Its name
 * reflects that the value of a variable of this type may be a reference to a
 * <code>Thing42</code> object or the value may be null.
 *<p>
 * An object of type <code>Thing42</code> has five attributes.
 * There are two immutable attributes: a generic attribute known as its <code>key</code> and an integer-valued
 * attribute known as its <code>level</code>.  There is also a generic mutable attribute known as its
 * <code>data</code>. Values of <code>key</code>, <code>level</code> and <code>data</code> are assigned at object creation.
 * In addition, each <code>Thing42</code> object has an unordered collection of <code>Thing42</code> objects known as
 * its <code>peers</code>as well as an  ordered collection of <code>Thing42</code> objects known as its <code>pool</code>.
 *</p>
 * @param <K> the type of key
 * @param <D> the type of data
 * @author
 * @version 0.1.0
 */
public interface Thing42orNull<K, D> {
    /**
     * Add a peer to this object.
     *
     * @param newPeer the peer to be added
     * @throws NullPointerException if the specified peer is null
     */
	void addPeer(Thing42orNull<K, D> newPeer) throws NullPointerException;

    /**
     * Appends a member to the pool of this object.
     *
     * @param newMember the object to be appended to the pool
     * @throws NullPointerException if the specified item is null
     */
	void appendToPool(Thing42orNull<K, D> newMember) throws NullPointerException;

    /**
     * Accesses the data of this Thing42.
     *
     * @return the data of this object
     */
	D getData();

    /**
     * Accesses the key of this Thing42.
     *
     * @return the key of this object
     */
	K getKey();

    /**
     * Accesses the level of this THing42.
     *
     * @return the level of this object
     */
	long getLevel();

    /**
     * Accesses a peer matching the specified key.
     *
     * @param key - the search key
     * @return any peer known by this object that matches the given key;
     *             null if no match
     */
	Thing42orNull<K, D> getOnePeer(K key);

    /**
     * Accesses all peers.
     *
     * @return all peers known by this object; if no peers then returns a collection with size()==0.
     */
	Collection<Thing42orNull<K, D>> getPeersAsCollection();

    /**
     * Accesses all peers matching the specified key.
     *
     * @param key the search key
     * @return all peers known by this object that match the given key; if no peer matches then returns a collection with size() == 0.
     */
	Collection<Thing42orNull<K, D>> getPeersAsCollection(K key);

    /**
     * Accesses all members of the pool.
     *
     * @return all members of the pool known by this object; if no members then returns a list with size()==0.
     */
	List<Thing42orNull<K, D>> getPoolAsList();

    /**
     * Removes a single instance of the specified object from this object's pool.
     *
     * @param member the member to be removed from the pool.
     * @return true if a pool member was removed as a result of this call
     * @throws NullPointerException if the specified parameter is null
     */
	boolean removeFromPool(Thing42orNull<K, D> member);

    /**
     * Removes a single instance of the specified peer from this object.
     *
     * @param peer the peer to be removed
     * @return true if a peer was removed as a result of this call
     * @throws NullPointerException if the specified peer is null
     */
	boolean removePeer(Thing42orNull<K, D> peer) throws NullPointerException;

    /**
     * Modifies the data of this Thing42.
     *
     * @param newData the updated data for this object
     */
	void setData(D newData);

	/**
	 * Indicates whether some other object is equal to this one.
	 *
	 * @param o the reference object with which to compare
	 * @return true if this object is the same as o; false otherwise.
	 */
	boolean equals(Object o);

	/**
	 * Returns an integer hash code value for the object. This method is
	 * supported for hash tables such as those provided by HashMap.
	 *
	 * @return this object's hash code.
	 */
	int hashCode();
}
