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
 * @author  Jamie Wohletz
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
     * Attempts to add both null and a Thing42 object
     * to the peer collection of a Thing42 object. Also 
     * attempts to add a duplicate peer. 
     */
    @Test(expected = NullPointerException.class)
    public void testAddPeer() 
    {
        //Should throw exception
        testThing.addPeer(null);
        //Should add successfully
        testThing.addPeer(validThing);
        assertTrue(testThing.getPeersAsCollection().size() == 1);
        //SHOULD add duplicate
        testThing.addPeer(validThing);
        assertTrue(testThing.getPeersAsCollection().size() == 2); 
    }

    /**
     * Attempts to add both null and a Thing42 object
     * to the pool collection of a Thing42 object. 
     */
    @Test(expected = NullPointerException.class)
    public void testAppendMember()
    {
        //Should throw exception
        testThing.appendToPool(null);
        //Should add successfully
        testThing.appendToPool(validThing);
        assertTrue(testThing.getPoolAsList().size() == 1); 
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
     * Attempts to remove a Thing42 object from the pool
     * of another Thing42 object. 
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveFromPool() 
    {
        testThing.appendToPool(validThing);
        
        //Should throw NullPointerException
        testThing.removeFromPool(null);
        
        assertTrue(testThing.removeFromPool(validThing));
    }
    
    /**
     * Attempts to remove a Thing42 object from the peer collection
     * of another Thing42 object. 
     */
    @Test(expected = NullPointerException.class)
    public void testRemovePeer()
    {
        testThing.addPeer(validThing);
        
        //Should throw NullPointerException
        testThing.removePeer(null);
        
        assertTrue(testThing.removePeer(validThing)); 
    }
}


