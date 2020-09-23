/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph;
/**
* <p>Title: MotMatchArrayException</p>
* <p>Description:</p>
* <pre>
* </pre>
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* @author 9ins
* @version 1.0, 2001/8/13 19:30 First draft
* @version 1.2, 2006/7/5
*/
public class NotMatchArrayException extends Exception
{
    /**
     * Error text
     * @since JDK1.4.1
     */
    public static final String ERR_STR = "ERROR - Graph elements are not to get same size of array. Please check graph elements values size.";
    /**
     * Constructor
     * @since JDK1.4.1
     */
    public NotMatchArrayException()
    {
        this(ERR_STR);
    }
    /**
     * Constructor
     * @param str String �޽���
     * @since JDK1.4.1
     */
    public NotMatchArrayException(String str)
    {
        super(str);
    }
}
