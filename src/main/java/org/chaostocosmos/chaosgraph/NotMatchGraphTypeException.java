/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph;
/**
* <p>Title: NotMatchGraphTypeException</p>
* <p>Description:</p>
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* @author 9ins
* @version 1.0, 2001/8/13 19:30 First draft
* @version 1.2, 2006/7/5
*/
public class NotMatchGraphTypeException extends RuntimeException
{
    /**
     * Error message
     * @since JDK1.4.1
     */
    public static final String ERR_STR = "ERROR - It's not matched with between GraphElements object and Graph object.";
    /**
     * Constructor
     * @since JDK1.4.1
     */
    public NotMatchGraphTypeException()
    {
        this(ERR_STR);
    }
    /**
     *Constructor
     * @param str String �޽���
     * @since JDK1.4.1
     */
    public NotMatchGraphTypeException(String str)
    {
        super(str);
    }
}
