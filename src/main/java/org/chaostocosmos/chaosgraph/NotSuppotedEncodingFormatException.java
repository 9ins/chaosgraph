/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph;
/**
* <p>Title: NotSuppotedEncodingFormatException</p>
* <p>Description:</p>
* <pre>
* </pre>
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* @author 9ins
* @version 1.2, 2006/7/5 First draft
*/
public class NotSuppotedEncodingFormatException extends Exception
{
    /**
     * Error message
     * @since JDK1.4.1
     */
    public static final String ERR_STR = "ERROR - Do not support Codec!!!";
    /**
     * Constructor
     * @since JDK1.4.1
     */
    public NotSuppotedEncodingFormatException()
    {
        this(ERR_STR);
    }
    /**
     * Constructor
     * @param str String
     * @since JDK1.4.1
     */
    public NotSuppotedEncodingFormatException(String str)
    {
        super(str);
    }
}
