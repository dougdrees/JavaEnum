//
// $Source$
// $Revision$
// $Date$
//
// Copyright 2003 Douglas Drees
// All Rights Reserved
//

package dreeser.utilities.enum;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestOrderedEnum extends TestCase
{

    private static class Speed extends OrderedEnum
    {
	public static final Speed REVERSE = new Speed("Reverse");
	public static final Speed NEUTRAL = new Speed("Neutral");
	public static final Speed FIRST   = new Speed("First");
	public static final Speed SECOND  = new Speed("Second");
	public static final Speed THIRD   = new Speed("Third");

	private Speed( String label )
	{
	    super(label);
	}

	int getNumber()
	{
	    return super.getOrdinal();
	}
    }

    private static class Color extends OrderedEnum
    {
	public static final Color BLACK  = new Color("Black",  0);
	public static final Color WHITE  = new Color("White",  9);
	public static final Color BROWN  = new Color("Brown",  1);
	public static final Color RED    = new Color("Red",    2);
	public static final Color ORANGE = new Color("Orange", 3);
	public static final Color YELLOW = new Color("Yellow", 4);
	public static final Color GREEN  = new Color("Green",  5);
	public static final Color BLUE   = new Color("Blue",   6);
	public static final Color PURPLE = new Color("Purple", 7);
	public static final Color GREY   = new Color("Grey",   8);

	private Color(String label, int ordinal)
	{
	    super(label, ordinal);
	}

	int getNumber()
	{
	    return super.getOrdinal();
	}
    }

    public TestOrderedEnum(String name)
    {
        super(name);
    }

    public void testBothConstructors()
    {
	//
	// Note that the constructors were called when the class
	// were loaded - likely before this method is called.
	// All we do here is check to see if the ordinals and 
	// labels match correctly.
	//
        try
        {
            assertTrue(Speed.REVERSE.getNumber() == 0);
	    assertTrue(Speed.NEUTRAL.getNumber() == 1);
	    assertTrue(Speed.FIRST.getNumber()   == 2);
	    assertTrue(Speed.SECOND.getNumber()  == 3);
	    assertTrue(Speed.THIRD.getNumber()   == 4);
            assertTrue(Color.BLACK.getNumber()   == 0);
	    assertTrue(Color.WHITE.getNumber()   == 9);
	    assertTrue(Color.BROWN.getNumber()   == 1);
	    assertTrue(Color.RED.getNumber()     == 2);
	    assertTrue(Color.ORANGE.getNumber()  == 3);
	    assertTrue(Color.YELLOW.getNumber()  == 4);
	    assertTrue(Color.GREEN.getNumber()   == 5);
	    assertTrue(Color.BLUE.getNumber()    == 6);
	    assertTrue(Color.PURPLE.getNumber()  == 7);
	    assertTrue(Color.GREY.getNumber()    == 8);

            assertTrue(Speed.REVERSE.toString().equals("Reverse"));
	    assertTrue(Speed.NEUTRAL.toString().equals("Neutral"));
	    assertTrue(Speed.FIRST.toString().equals("First"));
	    assertTrue(Speed.SECOND.toString().equals("Second"));
	    assertTrue(Speed.THIRD.toString().equals("Third"));
            assertTrue(Color.BLACK.toString().equals("Black"));
	    assertTrue(Color.WHITE.toString().equals("White"));
	    assertTrue(Color.BROWN.toString().equals("Brown"));
	    assertTrue(Color.RED.toString().equals("Red"));
	    assertTrue(Color.ORANGE.toString().equals("Orange"));
	    assertTrue(Color.YELLOW.toString().equals("Yellow"));
	    assertTrue(Color.GREEN.toString().equals("Green"));
	    assertTrue(Color.BLUE.toString().equals("Blue"));
	    assertTrue(Color.PURPLE.toString().equals("Purple"));
	    assertTrue(Color.GREY.toString().equals("Grey"));
        }
        catch (Exception e)
        {
            fail(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void testEqualsAndNotEquals()
    {
        try
        {
        }
        catch (Exception e)
        {
            fail(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void testHashCode()
    {
        try
        {
        }
        catch (Exception e)
        {
            fail(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void testGetBytes()
    {
        try
        {
        }
        catch (Exception e)
        {
            fail(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void testCompareToObject()
    {
        try
        {
	    Object one = new Object();
	    int result = Speed.NEUTRAL.compareTo(one);
	    fail("Should have thrown ClassCastException.");
        }
	catch (ClassCastException e)
	{
	    assertTrue(true);
	}
        catch (Exception e)
        {
            fail(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void testCompareToOrderedEnum()
    {
        try
        {
	    int result = Speed.NEUTRAL.compareTo(Color.BLACK);
	    fail("Should have thrown ClassCastException.");
        }
	catch (ClassCastException e)
	{
	    assertTrue(true);
	}
        catch (Exception e)
        {
            fail(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void testCompareToSameClass()
    {
        try
        {
	    assertTrue(Speed.NEUTRAL.compareTo(Speed.THIRD)   <  0);
	    assertTrue(Speed.NEUTRAL.compareTo(Speed.NEUTRAL) == 0);
	    assertTrue(Speed.NEUTRAL.compareTo(Speed.REVERSE) >  0);
        }
        catch (Exception e)
        {
            fail(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestOrderedEnum.class);

        return suite;
    }

    public static void main(String args[])
    {
        junit.textui.TestRunner.run(suite());
    }
}

//
// $Log: TestOrderedEnum.java,v $
// Revision 1.2  2002/11/27 22:31:13  dougd
// Added/corrected file begin and end comments.
//
//
 