package com.cloume.common.utils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testMapBuilder()
    {
        Map<String, Object> map = MapBuilder.begin("hello", (Object) "world")
        	.and("world", 1000)
        	.and("object", new AtomicBoolean(true))
        	.and("boolean", false)
        	.and("array", new int[]{0, 1})
        	.and("end", null)
        	.build();
        
        assertEquals("world", map.get("hello"));
        assertEquals(1000, map.get("world"));
        assertEquals(true, ((AtomicBoolean) map.get("object")).get());
        assertEquals(false, map.get("boolean"));
        assertEquals(1, ((int[]) map.get("array"))[1]);
    }
    
    public void testArrays() {
    	String strArray[] = Arrays.from("1", "2", "3");
    	assertEquals(3, strArray.length);
    	assertEquals("3", strArray[2]);
    	
    	Long[] longArray = Arrays.from(0L, 100L, 50L, 300L);
    	assertEquals(4, longArray.length);
    	assertEquals(50L, longArray[2].longValue());
    }
}
