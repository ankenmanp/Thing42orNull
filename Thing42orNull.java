import java.util.Collection;
import java.util.List;
interface Thing42orNull<K,D> {
	void addPeer(Thing42orNull newPeer) throws NullPointerException;
	void appendToPool(Thing42orNull newMember) throws NullPointerException;
	D getData();
	K getKey();
	long getLevel();
	Thing42orNull getOnePeer(K key);
	Collection<Thing42orNull> getPeersAsCollection();
	Collection<Thing42orNull> getPeersAsCollection(K key);
	List<Thing42orNull> getPoolAsList();
	boolean removeFromPool(Thing42orNull member) throws NullPointerException;
	boolean removePeer(Thing42orNull peer) throws NullPointerException;
	void setData(D newData);
}
