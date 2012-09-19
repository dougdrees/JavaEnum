//
// $Source$
// $Revision$
// $Date$
//
// Copyright 2003 Douglas Drees
// All Rights Reserved
//

package dreeser.utilities.enum;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * This class is the base class for all enum classes.
 * It implements the basic functionality associated with enums so that derivative classes
 * have a minimum of work to do to implement a specific class of enumerated final values.
 * <p>
 *
 * This class handles the problems associated with equals() comparison, hash codes,
 * and serialization.  All a derivative class need do is to extend this class, create a
 * private (if no further specialization is allowed) or a protected (if further
 * specialization is planned) constructor that merely passes its arguments on to super()
 * and then define some public static final instances of itself that are the enumeration
 * constants the be used.
 * <p>
 *
 * For example, a simple enum class that represents a 3-speed transmission would look like:
 * <p>
 * <pre>
 * public class Speed extends BasicEnum
 * {
 *     public static final Speed REVERSE = new Speed("Reverse");
 *     public static final Speed NEUTRAL = new Speed("Neutral");
 *     public static final Speed FIRST   = new Speed("First");
 *     public static final Speed SECOND  = new Speed("Second");
 *     public static final Speed THIRD   = new Speed("Third");
 *
 *     private Speed( String label )
 *     {
 *         super(label);
 *     }
 * }
 * </pre>
 * <p>
 */
public abstract class BasicEnum
    implements java.io.Serializable
{
    /**
     * This inner class contains information for each specific derived class
     * that has created instances.  It is used to keep track of the next ordinal
     * value to assign for automatic assignment and the map of ordinals to values
     * for those classes that support it.
     */
    private static class EnumClassInfo
    {
        /**
        * The next ordinal value to assign if ordinal assignment is automatic.
        */
        int myNextOrdinal = 0;

        /**
        * A list used to map the ordinal numbers to the constant values
        * so that <code>EnumSet</code>s can lookup instances from bits in their
        * <code>BitSet</code>-based containers.
        */
        ArrayList myOrdinalMap;
    }

    /**
     * Contains <code>EnumClassInfo</code> objects containing the next ordinal value
     * and the ordinal map (if any) for the class begin constructed.
     */
    private static HashMap   ourClassInfo = new HashMap();

    /**
     * Contains the <code>String</code> label for this <code>BasicEnum</code> value.
     */
    private final  String    myLabel;

    /**
     * Contains the integer ordinal value for this <code>BasicEnum</code> value.
     */
    private final  int       myOrdinal;


    /***************************************************************************
     * This static method is to be called before any constructors are called for
     * any specific derived class - likely in the static initializer.  It is used
     * to create an ordinal map for this class.  If this method is called after
     * a constructor has already been called, the result is a
     * <code>RuntimeException</code>.
     *
     * @exception RuntimeException Thrown if a constructor has already been called.
     ***************************************************************************/
    protected static void createOrdinalMap(Class thisClass)
    {
        synchronized (ourClassInfo)
        {
            EnumClassInfo info = (EnumClassInfo) ourClassInfo.get(thisClass);

            if (null == info)
            {
                info = new EnumClassInfo();
                info.myOrdinalMap = new ArrayList();
                ourClassInfo.put(thisClass, info);
            }
            else
            {
                throw new RuntimeException("createOrdinalMap() called after a constructor.");
            }
        }
    }


    /***************************************************************************
     * The two argument constructor.
     * This constructor is used to assign both the label and the ordinal values.
     * No checking is done to guarantee that the ordinal values assigned are all
     * unique.  <p>
     *
     * A negative ordinal value causes the ordinal value to be automatically
     * generated.
     *
     * @param label   The intended label for this enum value.
     * @param ordinal The intended integer ordinal value for this enum value.
     ***************************************************************************/
    protected BasicEnum
    (
        String label,
        int    ordinal
    )
    {
        super();

        EnumClassInfo info;
        Class         thisClass = this.getClass();

        synchronized (ourClassInfo)
        {
            info = (EnumClassInfo) ourClassInfo.get(thisClass);

            if (null == info)
            {
                info = new EnumClassInfo();
                ourClassInfo.put(thisClass, info);
            }
        }

        synchronized (info)
        {
            //
            // If the specified ordinal is negative, compute the ordinal to use.
            //
            if (ordinal < 0)
            {
                ordinal = info.myNextOrdinal++;
            }

            //
            // Set the attributes for this object.
            //
            myLabel   = label;
            myOrdinal = ordinal;

            //
            // Only if we have an ordinal map do we do the next step.
            //
            if (info.myOrdinalMap != null)
            {
                //
                // Add this value to the ordinal map for this class.  NOTE that if more than
                // one enum value is assigned the same value, only the last one instantiated
                // will be listed in the map.
                //
                ArrayList map = info.myOrdinalMap;

                if (ordinal >= map.size())
                {
                    map.ensureCapacity(ordinal + 1);
                    for (int i = map.size(); i <= ordinal; i++)
                    {
                        map.add(null);
                    }
                }

                map.set(ordinal, this);

                //
                // Since we have an ordinal map, we can assure the next ordinal value is
                // not used.
                //
                try
                {
                    while (map.get(ordinal) != null)
                    {
                        ordinal++;
                    }
                }
                catch (IndexOutOfBoundsException e)
                { /* Do nothing */ }

                info.myNextOrdinal = ordinal;
            }
        }
    }


    /***************************************************************************
     * The single argument constructor.
     * This constructor takes only a label value and computes the associated
     * ordinal value.  This constructor is the one most derived classes would
     * use.
     *
     * @param label The intended label for this enum value.
     ***************************************************************************/
    protected BasicEnum(String label)
    {
        this(label, -1);
    }


    /***************************************************************************
     * Compares this enum to the specified object.
     * The result is <code>true</code> if and only if the argument is not
     * <code>null</code> and is a <code>BasicEnum</code> object with the same
     * specific class and the same ordinal value.  This method cannot be
     * overridden.
     *
     * @param obj the object to compare this <code>BasicEnum</code> against.
     * @return <code>true</code> if the <code>BasicEnum</code> objects are
     *     equal; <code>false</code> otherwise.
     **************************************************************************/
    public final boolean equals(final Object obj)
    {
        boolean eq = ( obj == this );

        if ( !eq && obj != null && obj.getClass() == getClass() )
        {
            eq = (((BasicEnum)obj).myOrdinal == myOrdinal);
        }

        return eq;
    }


    /***************************************************************************
     * Returns a hashcode for this enum. The hashcode for a <code>BasicEnum</code>
     * object is the ordinal value.  This method cannot be overridden. <p>
     *
     * Caution should be taken in assuming that this method will always return the
     * ordinal value as it may be changed in the future to return some more
     * random integer value.  Derivative classes can use getOrdinal() and make it
     * available via a different method if access to the ordinal value is necessary
     * for classes using that specific enum class.
     *
     * @return an integer hash code value for this enum object.
     ***************************************************************************/
    public final int hashCode()
    {
        //
        // Hashtable performance might be improved if we returned something more random.
        //
        return myOrdinal;
    }


    /***************************************************************************
     * Returns the label of the enum object.
     *
     * @return the enum label.
     ***************************************************************************/
    public String toString()
    {
        return myLabel;
    }


    /***************************************************************************
     * Returns an array of bytes that represents the ordinal value of this
     * enum value.
     *
     * @return a <code>byte[]</code> that represents the ordinal value.
     ***************************************************************************/
    public final byte[] getBytes()
    {
        byte[] result = new byte[4];
        int    temp   = myOrdinal;

        for (int i = 0; i < 4; i++)
        {
            result[i] = (byte)(temp % 256);
            temp >>= 8;
        }

        return result;
    }


    /***************************************************************************
     * Returns the ordinal value of this enum value.
     *
     * @return the integer ordinal value assigned to this enum.
     ***************************************************************************/
    public final int getOrdinal()
    {
        return myOrdinal;
    }


    /***************************************************************************
     * Returns the constant enum value for a specific enum class that is represented
     * by the specified ordinal value.
     * <p>
     * This method is used by <code>EnumSet</code> to convert from ordinals to
     * values.
     *
     * @param theClass The <code>Class</code> object for the requested enum
     *     value.
     * @param ordinal  The integer ordinal value used to look up the
     *     value.
     *
     * @return         A <code>BasicEnum</class> object specified by
     *     <code>theClass</code> and <code>ordinal</code>.
     *
     * @exception UnsupportedOperationException Thrown if this class has no
     *     ordinal map.
     * @exception RuntimeException if there are no instances of the specified
     *     class or if the ordinal value does not map to an instance.
     ***************************************************************************/
    protected final static BasicEnum getByClassAndOrdinal
    (
        Class theClass,
        int   ordinal
    )
    {
        EnumClassInfo info = (EnumClassInfo) ourClassInfo.get(theClass);
        if (null == info)
        {
            throw new RuntimeException("No instances have been created of class " + theClass);
        }

        List map = info.myOrdinalMap;
        if (null == map)
        {
            throw new UnsupportedOperationException();
        }

        BasicEnum temp = (BasicEnum) map.get(ordinal);
        if (null == temp)
        {
            throw new RuntimeException("No instance exists with the ordinal value of " + ordinal);
        }

        return temp;
    }
}

//
// $Log: BasicEnum.java,v $
// Revision 1.2  2002/11/27 22:31:13  dougd
// Added/corrected file begin and end comments.
//
//
