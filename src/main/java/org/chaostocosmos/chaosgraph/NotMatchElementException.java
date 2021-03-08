/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph;
/**
* <p>Title: NotMatchElementException</p>
* <p>Description:</p>
* <pre>
* </pre>
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* @author 9ins
* @version 1.0, 2001/8/13 19:30 First draft
* @version 1.2, 2006/7/5
*/
public class NotMatchElementException extends RuntimeException
{
    /**
     * Error message
     * @since JDK1.4.1
     */
    public static final String ERR_STR = "ERROR - The element is not matched with graph. Please check graph type.";

    /**
     * Constructor
     * @since JDK1.4.1
     */
    public NotMatchElementException()
    {
        this(ERR_STR);
    }
    /**
     * Constructor
     * @param str String
     * @since JDK1.4.1
     */
    public NotMatchElementException(String str)
    {
    	super(str);
    }
}
