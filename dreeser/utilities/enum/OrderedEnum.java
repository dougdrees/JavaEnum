//
// $Source$
// $Revision$
// $Date$
//
// Copyright 2003 Douglas Drees
// All Rights Reserved
//

package dreeser.utilities.enum;


/**
 * This class extends <code>BasicEnum</code> to provide implementation of the
 * <code>Comparable</code> interface.  <code>OrderedEnum</code> objects have some
 * natural ordering such that it can be said that some are before some or after
 * others.  <code>OrderedEnum</code> objects can therefore be sorted in a
 * meaningful way. <p>
 *
 * Derived classes can be created in the same way as <code>BasicEnum</code>.
 */
public abstract class OrderedEnum
    extends BasicEnum
    implements Comparable
{
    /***************************************************************************
     *  The two argument constructor.
     *  This constructor is used to assign both the label and the ordinal values.
     *  No checking is done to guarantee that the ordinal values assigned are all
     *  unique.
     *
     *  @param label    The intended label for this enum value.
     *  @param ordinal  The intended integer ordinal value for this enum value.
     ***************************************************************************/
    protected OrderedEnum
    (
        String label,
        int    ordinal
    )
    {
        super(label, ordinal);
    }


    /***************************************************************************
     *  The single argument constructor.
     *  This constructor takes only a label value and computes the associated
     *  ordinal value.  This constructor is the one most derived classes would
     *  use.
     *
     *  @param label  The intended label for this enum value.
     ***************************************************************************/
    protected OrderedEnum(String label)
    {
        super(label);
    }


    /***************************************************************************
     * Compares this <code>OrderedEnum</code> instance to another
     * <code>Object</code>.  If the <code>Object</code> argument is a
     * <code>OrderedEnum</code>, this function behaves like
     * <code>compareTo(OrderedEnum)</code>.  Otherwise, it throws a
     * <code>ClassCastException</code> (as <code>OrderedEnum</code>s are
     * comparable only to other instances of their own specific class).
     *
     * @param   obj  the <code>Object</code> to be compared.
     * @return       the value <code>0</code> if the argument is a <code>OrderedEnum</code>
     *		with an ordinal value equal to my ordinal value; a value less than
     *		<code>0</code> if the argument is a <code>OrderedEnum</code>
     *		with an ordinal value greater than my ordinal value; and a value greater than
     *		<code>0</code> if the argument is a <code>OrderedEnum</code>
     *		with an ordinal value less than my ordinal value.
     * @exception <code>ClassCastException</code> if the argument is not a
     *		  <code>OrderedEnum</code>.
     * @see     java.lang.Comparable
     ***************************************************************************/
    public int compareTo(final Object obj)
    {
        return compareTo((OrderedEnum) obj);
    }


    /***************************************************************************
     * Compares two <code>OrderedEnum</code> objects numerically based upon their ordinal values.
     *
     * @param   anotherEnum   the <code>OrderedEnum</code> to be compared.
     * @return  the value <code>0</code> if the argument enum's ordinal value is equal to
     *          this string; a value less than <code>0</code> if my ordinal value
     *          is numerically less than that of the enum argument; and a
     *          value greater than <code>0</code> if my ordinal value is
     *          numerically greater than that of the enum argument.
     * @exception <code>NullPointerException</code> if the argument
     *          is <code>null</code>.
     * @exception <code>ClassCastException</code> if the argument is not of the same specific
     *          class as this object.
     ***************************************************************************/
    public int compareTo(final OrderedEnum anotherEnum)
    {
        if ( anotherEnum.getClass() == getClass() )
        {
            return getOrdinal() - anotherEnum.getOrdinal();
        }
        else
        {
            throw new ClassCastException("Attempted to compare an object of class " +
                                         anotherEnum.getClass() + " to this object of class " +
                                         getClass() + ".");
        }
    }

}

//
// $Log: OrderedEnum.java,v $
// Revision 1.2  2002/11/27 22:31:13  dougd
// Added/corrected file begin and end comments.
//
//
