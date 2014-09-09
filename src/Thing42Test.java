import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class Thing42Test.
 *
 * This file contains tests for (hopefully) every nontrivial 
 * action that can be taken with the Thing42 class. 
 * Trivial methods, like one-line getters and setters, are ignored,
 * because testing them would equate to testing the Java API rather than
 * the Thing42 class. 
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
public class Thing42Test
{
	public static void main(String[] args) {
		
	}
	
    //The Thing42 object upon which tests should call methods. 
    Thing42<Integer,String> testThing;
    //A valid Thing42 object that can be passed as a parameter to methods.  
    Thing42<Integer,String> validThing;
    /**
     * Default constructor for test class Thing42Test
     */
    public Thing42Test()
    {
    }

    /**
     * Sets up the test fixture
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        testThing = new Thing42<Integer,String>(1, 1, "test");
        validThing = new Thing42<Integer,String>(2,2,"test");
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()	
    {
        testThing = null;
        validThing = null; 
    }
    
        /**
     * Tests Constructor for Thing42(K key, long level, D data).
     * Verifies multiple data types are allowed/enforced when specifying upon construction.
     */
    @Test
    public void constructorTest() {
        // parameters set as integers
        Thing42<Integer, Integer> integerTest = new Thing42<Integer, Integer>(1, 1, 1);
        assertNotNull(integerTest);
        assertTrue(integerTest.getKey() == 1);
        assertTrue(integerTest.getData() == 1);
        assertTrue(integerTest.getLevel() == 1);
        // parameters set as strings
        Thing42<String, String> stringTest = new Thing42<String, String>("Hello", 5, "World");
        assertTrue(stringTest.getKey().equals("Hello"));
        assertTrue(stringTest.getLevel() == 5);
        assertTrue(stringTest.getData().equals("World"));
        // parameters are set differently, includes a double
        Thing42<Double, String> variableTest = new Thing42<Double, String>(3.14159, 5, "Don't Panic");
        assertTrue(variableTest.getKey().equals(3.14159));
        assertTrue(variableTest.getLevel() == 5);
        assertTrue(variableTest.getData().equals("Don't Panic"));

    }
    /**
     * Attempts to add a Thing42 object
     * to the peer collection of a Thing42 object. Also 
     * attempts to add a duplicate peer. 
     */
    @Test
    public void testAddPeer() 
    {        
        //Should add successfully
        testThing.addPeer(validThing);
        assertTrue(testThing.getPeersAsCollection().size() == 1);
        //SHOULD add duplicate
        testThing.addPeer(validThing);
        assertTrue(testThing.getPeersAsCollection().size() == 2);
    }
    
    /**
     * Attempts to add null
     * to the peer collection of a Thing42 object.
     */
    @Test(expected = NullPointerException.class)
    public void testAddNullPeer() 
    {
        //Should throw exception
        testThing.addPeer(null);        
    }

    /**
     * Attempts to add a Thing42 object
     * to the pool collection of a Thing42 object. 
     */
    @Test
    public void testAppendMember()
    {
        // check testThing's pool doesn't have validThing yet
        assertTrue(testThing.getPoolAsList().size() == 0);
        assertFalse(testThing.getPoolAsList().contains(validThing));
        //Should add successfully
        testThing.appendToPool(validThing);
        assertTrue(testThing.getPoolAsList().size() == 1);
        assertTrue(testThing.getPoolAsList().contains(validThing));
    }
    
    /**
     * Attempts to add null into pool collection 
     * of a Thing42 object
     */
    @Test(expected = NullPointerException.class)
    public void testAppendNullMember()
    {
        //Should throw exception
        testThing.appendToPool(null);        
    }
    
    /**
     * Attempts twice to get a single peer
     * from a Thing42 object. The first time,
     * the object has no peers. The second time, it does.
     */
    @Test
    public void testGetOnePeer()
    {
        //Should return null when peer isn't found
        assertNull(testThing.getOnePeer(0));
        //Should return the object when it IS found
        testThing.addPeer(validThing);
        assertEquals(validThing, testThing.getOnePeer(2));
    }
    
    /**
     * Attempts twice to get the collection of peers from
     * a Thing42 object. The first time, the object has no peers.
     * The second time, it does. 
     */
    @Test
    public void testGetPeersAsCollection() 
    {
        assertTrue(testThing.getPeersAsCollection().size() == 0);
        
        testThing.addPeer(validThing);
        assertTrue(testThing.getPeersAsCollection().size() == 1);
        assertTrue(testThing.getPeersAsCollection().contains(validThing));
    }
    
    /**
     * Attempts twice to get the pool as list.
     *  The first time, the pool is empty,
     *  the second time, the pool has 1 object
     */
    @Test
    public void testGetPoolAsList()    {
    	assertTrue(testThing.getPoolAsList().size() == 0);        
        testThing.appendToPool(validThing);
        assertTrue(testThing.getPoolAsList().size() == 1);
        assertTrue(testThing.getPoolAsList().contains(validThing));
    }
    
    /**
     * Attempts to retrieve from a Thing42 object 
     * a list of peers containing a given key.  
     */
    @Test
    public void testGetPeersAsCollectionUsingKey()
    {
        assertTrue(testThing.getPeersAsCollection(5).size() == 0);
        
        testThing.addPeer(validThing);
        testThing.addPeer(new Thing42<Integer,String>(2,3,"test"));
        
        assertTrue(testThing.getPeersAsCollection(2).size() == 2);
        
        testThing.addPeer(new Thing42<Integer,String>(3,3,"test"));
        assertTrue(testThing.getPeersAsCollection(2).size() == 2); 
    }
    
    /**
     * Attempt to remove a Thing42 object from the pool
     * of another Thing42 object. 
     */
    @Test
    public void testRemoveFromPool() 
    {
        testThing.appendToPool(validThing);        
        assertTrue(testThing.removeFromPool(validThing));
    }
    
    
    /**
     * Attempt to remove null object from the pool
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveNullFromPool()
    {
        //Should throw NullPointerException
        testThing.removeFromPool(null);
    }
    
    
    /**
     * Attempt to remove a Thing42 object from the peer collection
     * of another Thing42 object. 
     */
    @Test
    public void testRemovePeer()
    {
        testThing.addPeer(validThing);
        assertTrue(testThing.removePeer(validThing));
        assertTrue(testThing.getPeersAsCollection().size() == 0);
    }
    
    /**
     * Attempt to remove null from peer Collection
     */    
    @Test(expected = NullPointerException.class)
    public void testRemoveNullPeer()
    {
        //Should throw NullPointerException
        testThing.removePeer(null);      
    }
    
   /**
     * Tests equivalence relations for equals method on non-null objects
     * reflexive: x.equals(x) should return true
     * symmetric: x.equals(y) == y.equals(x)
     * transitive: x.equals(y) == y.equals(z) == x.equals(z)
     * consistent: x.equals(y) should always return the same result if no
     * 			   changes to either x or y
     * x.equals(null) should return false 
     */
    @Test
    public void testEquals(){
        //reflexive:
    	assertTrue(testThing.equals(testThing)); 
    	assertFalse(testThing.equals(validThing));
    	
    	//symmetric:
    	Thing42<Integer, String> thing1 = new Thing42<Integer,String>(1, 1, "test");
    	assertTrue(testThing.equals(thing1));
    	assertTrue(thing1.equals(testThing));
    	
    	//transitive:
    	Thing42<Integer, String> thing2 = new Thing42<Integer,String>(1, 1, "test");
    	assertTrue(testThing.equals(thing1));
    	assertTrue(thing1.equals(testThing));
    	assertTrue(testThing.equals(thing2));
    	assertTrue(thing2.equals(testThing));
    	assertTrue(thing1.equals(thing2));
    	assertTrue(thing2.equals(thing1));
    	
    	//null
    	Thing42<Integer, String> nullThing = null;
    	assertFalse(testThing.equals(nullThing));
    	
    	//consistent:
    	for (int i = 0; i < 100; i ++){
    	    assertTrue(testThing.equals(thing1));
    	    assertFalse(testThing.equals(validThing));
    	    assertFalse(testThing.equals(nullThing));
    	}
    }
    
    /**
     * Tests equality using peers and pool with same memory referenced Thing42s
     * in them, as well as equal Thing42s with different memory references.
     */
    @Test
    public void testEqualsPeersAndPool(){
        Thing42<Integer, String> thing1 = new Thing42<Integer, String>(0, 0, "test");
    	Thing42<Integer, String> thing2 = new Thing42<Integer, String>(0, 0, "test");
    		
    	// peers and pool with same memory references
    	Thing42<Integer, String> newThing;
    	for (int i = 1; i < 15; i++){
    	    newThing = new Thing42<Integer, String>(i, i, Character.toString((char) i));
    	    thing1.addPeer(newThing);
    	    thing1.appendToPool(newThing);
    	    thing2.addPeer(newThing);
    	    thing2.appendToPool(newThing);
    	}
    		
    	assertFalse(thing1 == thing2);
    	assertTrue(thing1.equals(thing2));
    	assertTrue(thing2.equals(thing1));
    		
    	// peers and pool with different memory references, still equal
    	thing1 = new Thing42<Integer, String>(0, 0, "test");
    	thing2 = new Thing42<Integer, String>(0, 0, "test");
    		
    	Thing42<Integer, String> newThing1;
    	Thing42<Integer, String> newThing2;
    	for (int i = 1; i < 15; i++){
    	    newThing1 = new Thing42<Integer, String>(i, i, Character.toString((char) i));
    	    thing1.addPeer(newThing1);
    	    thing1.appendToPool(newThing1);
    			
    	    newThing2 = new Thing42<Integer, String>(i, i, Character.toString((char) i));
    	    thing2.addPeer(newThing2);
    	    thing2.appendToPool(newThing2);
    	}
    		
    	assertFalse(thing1 == thing2);
    	assertTrue(thing1.equals(thing2));
    	assertTrue(thing2.equals(thing1));
    }

    @Test(expected = StackOverflowError.class)
    public void testEqualsInfiniteLoop() {
        Thing42<Integer, String> thing1 = new Thing42<Integer, String>(0, 0, "test");
        Thing42<Integer, String> thing2 = new Thing42<Integer, String>(0, 0, "test");
        thing1.addPeer(thing1);
        thing2.addPeer(thing2);
        thing1.equals(thing2);      
    }
    
     /**
     * Test equality infinite loop for peers.
     */
    @Test(expected = StackOverflowError.class)
    public void testEqualsPeersInfiniteLoop() {
        Thing42<Integer, String> thing1 = new Thing42<Integer, String>(0, 0, "test");
        Thing42<Integer, String> thing2 = new Thing42<Integer, String>(0, 0, "test");
        thing1.addPeer(thing1);
        thing2.addPeer(thing2);
        thing1.getPeersAsCollection().equals(thing2.getPeersAsCollection());      
    }
    
    /**
     * Test equality infinite loop for pool.
     */
    @Test(expected = StackOverflowError.class)
    public void testEqualsPoolInfiniteLoop() {
        Thing42<Integer, String> thing1 = new Thing42<Integer, String>(0, 0, "test");
        Thing42<Integer, String> thing2 = new Thing42<Integer, String>(0, 0, "test");
        thing1.appendToPool(thing2);
        thing2.appendToPool(thing1);
        thing1.getPoolAsList().equals(thing2.getPoolAsList());      
    }
    
    /**
     * Tests equality of unordered peers and ordered pool
     * by adding new objects in different orders.
     */
    @Test
    public void testOrderPeersAndPool(){
        Thing42<Integer, String> thing1 = new Thing42<Integer, String>(0, 0, "test");
    	Thing42<Integer, String> thing2 = new Thing42<Integer, String>(1, 0, "test");
    	Thing42<Integer, String> thing3 = new Thing42<Integer, String>(3, 3, "test3");
    	Thing42<Integer, String> thing4 = new Thing42<Integer, String>(4, 4, "test4");
    	
    	//add thing3 and thing4 in different order
    	thing1.addPeer(thing3);
    	thing1.addPeer(thing4);
    	thing2.addPeer(thing4);
    	thing2.addPeer(thing3);
    		
    	assertFalse(thing1 == thing2);    	
    	assertTrue(thing1.getPeersAsCollection().equals(thing2.getPeersAsCollection()));
    	
    	thing1.appendToPool(thing3);
    	thing1.appendToPool(thing4);
    	thing2.appendToPool(thing4);
    	thing2.appendToPool(thing3);
    	assertFalse(thing1.getPoolAsList().equals(thing2.getPoolAsList()));
    		
    }

    /**
     * Tests equality of two Thing42 objects.
     * If the pool or peers of the comparing Thing42s is different,
     * the Thing42s are unequal. If the pool/peers are the same, as
     * well as all other fields, the compared Thing42s are the equal.
     * Note that the peers list is unordered and the final test in this
     * method assertsTrue if thing1 is equal to thing 2 with out of order
     * peers lists, but still equal peers lists. 
    */
    @Test
    public void testEqualsAgain() {
        Thing42<Integer, String> thing1 = new Thing42<Integer, String>(0, 0, "test");
        Thing42<Integer, String> thing2 = new Thing42<Integer, String>(0, 0, "test");
        Thing42<Integer, String> thing3 = new Thing42<Integer, String>(1, 1, "test");
        Thing42<Integer, String> thing4 = new Thing42<Integer, String>(2, 2, "test");

        // As expected, the address of thing1 and thing2 are the different
        assertFalse(thing1 == thing2);

        // This is also expected because our equals method determines these objects are equal
        assertTrue(thing1.equals(thing2));
        assertTrue(thing2.equals(thing1));

        // Adding the same things to each thing1 and thing2s pool
        thing1.appendToPool(thing3);
        thing2.appendToPool(thing3);
 
        // Showing that if the same object exists in each pool, the pools are identical
        // and therefore, under current conditions, so are the compared Thing42s.
        assertTrue(thing1.equals(thing2));
        assertTrue(thing2.equals(thing1));
 
        // Adding the same things to each thing1 and thing2s peers
        thing1.addPeer(thing3);
        thing2.addPeer(thing3);
 
        // Showing that if the same object exists in each peers list, the peers lists are identical
        // and therefore, under current conditions, so are the compared Thing42s.
        assertTrue(thing1.equals(thing2));
        assertTrue(thing2.equals(thing1));
 
        // Now we append different objects to each pool to make compared Thing42s unequal
        thing1.appendToPool(thing3);
        thing2.appendToPool(thing4);

        // Showing that if different objects exist in each pool, the compared pools are unequal
        // and therefore, the compared Thing42s are unequal
        assertFalse(thing1.equals(thing2));
        assertFalse(thing2.equals(thing1));
 
        // Remove unequal objects from pool to again make compared Things equal again
        thing1.removeFromPool(thing3);
        thing2.removeFromPool(thing4);
 
        // Ensure Things are equal once again
        assertTrue(thing1.equals(thing2));
        assertTrue(thing2.equals(thing1));

        // Now we add different objects to each peers list to make compared 
        // Thing42s unequal
        thing1.addPeer(thing3);
        thing2.addPeer(thing4);
 
        // Showing that if different objects exist in each peers list, the 
        // compared peers lists are unequal and therefore, the compared
        // Thing42s are unequal
        assertFalse(thing1.equals(thing2));
        assertFalse(thing2.equals(thing1));

        // Now we add the opposite things (4 & 3) from which we addded to the
        // previous things (1 & 2) to make peers equal, sans equal order
        thing1.addPeer(thing4);
        thing2.addPeer(thing3);

        // Showing that if the same objects exist in comparing Thing42s,
        // regardless of the peers list order, compared Thing42s are equal
        assertTrue(thing1.equals(thing2));
        assertTrue(thing2.equals(thing1));
    }
    
    /**
     * Tests hashCode to the general contract of hashCode:
     * 1) The same object shall consistently return the same hashCode, provided no
     * information is modified.
     * 2) If two objects are equal according to equals(Object), then they shall
     * have the same hashCode()
     * 
     * Does not test 3) if two objects have the same hashCode, it is not
     * necessary that they be equal according to equals(Object).
     */
    @Test
    public void testHashCode(){
    	// 1)
		final int result = testThing.hashCode();
		for (int i = 0; i < 100; i++){
			assertTrue(result == testThing.hashCode());
		}
		testThing.setData("modified");
		assertFalse(result == testThing.hashCode());
		
		// 2)
		Thing42<Integer, String> thing1 = new Thing42<Integer, String>(0, 0, "test");
		Thing42<Integer, String> thing2 = new Thing42<Integer, String>(0, 0, "test");
		assertTrue(thing1.equals(thing2));
		assertTrue(thing1.hashCode() == thing2.hashCode());
		
		thing1.setData("modified");
		thing2.setData("modified");
		assertTrue(thing1.equals(thing2));
		assertTrue(thing1.hashCode() == thing2.hashCode());
    }
	
	/**
	* Tests that the hashCode() method generates the same 
	* hash codes when two Thing42 objects have the same 
	* pool and peers collections. 
	*/
	@Test
	public void testHashCodePeersAndPool(){
		Thing42<Integer, String> thing1 = new Thing42<Integer, String>(0, 0, "test");
		Thing42<Integer, String> thing2 = new Thing42<Integer, String>(0, 0, "test");
		assertTrue(thing1.equals(thing2));
		assertTrue(thing1.hashCode() == thing2.hashCode());
		
		Thing42<Integer, String> thing3 = new Thing42<Integer, String>(1, 1, "Test");
		Thing42<Integer, String> thing4 = new Thing42<Integer, String>(1, 1, "Test");
		assertTrue(thing3.equals(thing4));
		assertTrue(thing3.hashCode() == thing4.hashCode());
		
		thing1.addPeer(thing3);
		thing2.addPeer(thing4);
		thing1.appendToPool(thing4);
		thing2.appendToPool(thing3);
		assertTrue(thing1.equals(thing2));
		assertTrue(thing1.hashCode() == thing2.hashCode());
	}
}


