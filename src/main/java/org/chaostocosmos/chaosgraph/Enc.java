/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph;

import java.io.UnsupportedEncodingException;
/**
* <p>Title: Enc class</p>
* <p>Description: </p>
* <pre> Charset encoding utility 
* </pre>
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* @author 9ins
* @version 1.0, 2001/8/13 19:30 First draft
* @version 1.2, 2006/7/5  
* @since JDK1.4.1
*/
public class Enc
{
    /**
     * Get 8859_1 text from euc-kr text
     * @param ko
     * @return
     * @since JDK1.4.1
     */
    public static String get8859_1(String ko) throws UnsupportedEncodingException {
        if (ko == null) {
            return null;
        }
        return new String(ko.getBytes("EUC_KR"),"8859_1");
    }
    
    /**
     * Get euc-kr text from 8859_1 text
     * @param en
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getEUC_KR(String en) throws UnsupportedEncodingException {
        if (en == null) {
            return null;
        }
        return new String (en.getBytes("8859_1"), "EUC_KR");
    }
}
