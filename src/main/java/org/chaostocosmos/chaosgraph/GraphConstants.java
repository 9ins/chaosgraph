/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph;
/**
* <p>Title: Constant attributes of graph</p>
* <p>Description:</p>
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* @author 9ins
* @version 1.2, 2006/7/5 first draft
* @since JDK1.4.1
*/
public interface GraphConstants
{
	/**
	 * 
	 * GRAPH
	 * 
	 * This is representing type of graph
	 *
	 * @author Kooin-Shin
	 * 2020. 9. 23.
	 */
    public static enum GRAPH {AREA, BAR, BAR_RATIO, CIRCLE, LINE};
	
    /**
     * 
     * GRID
     * This is representing type of grid of graph
     *
     * @author Kooin-Shin
     * 2020. 9. 23.
     */
    public static enum GRID {LINE, DOT};
	
    /**
     * 
     * GRID_VISIBLE
     * This is representing visibility of grid of graph
     *
     * @author Kooin-Shin
     * 2020. 9. 23.
     */
    public static enum GRID_VISIBLE {X, Y, XY};
    
    /**
     * 
     * POPUP_STYLE
     * This is representing element popup border style
     *
     * @author Kooin-Shin
     * 2020. 9. 23.
     */
    public static enum POPUP_STYLE { RECTANGLE, ROUND };
    
    /**
     * 
     * PEEK_STYLE
     * This is representing element value peek point style
     *
     * @author Kooin-Shin
     * 2020. 9. 23.
     */
    public static enum PEEK_STYLE { CIRCLE, RECTANGLE };
    
    /**
     * 
     * SELECTION_BORDER
     * This is representing selected element border style
     *
     * @author Kooin-Shin
     * 2020. 9. 23.
     */
    public static enum SELECTION_BORDER {LINE, DOT};
    
    /**
     * The place of round point
     */
    public static int ROUND_PLACE = 3;
	
    /**
     * Line graph
     * @since JDK1.4.1
     */
    public static int LINE_GRAPH = 1;
    /**
     * Bar graph
     * @since JDK1.4.1
     */
    public static int BAR_GRAPH = 2;
    
    /**
     * Bar ratio graph
     */
    public static int BAR_RATIO_GRAPH = 3;
    /**
     * Circle graph
     * @since JDK1.4.1
     */
    public static int CIRCLE_GRAPH = 4;
    /**
     * Area graph
     * @since JDK1.4.1
     */
    public static int AREA_GRAPH = 5;
}

