package org.chaostocosmos.chaosgraph;

import java.util.EventListener;

/**
 * 
 * GraphSelectionListener
 *
 * @author Kooin-Shin
 * 2020. 8. 24.
 */
public interface GraphSelectionListener extends EventListener {
	
	/**
	 * When mouse is over the graph
	 * @param goe
	 * @throws Exception
	 */
	public void onMouseOverGraph(GraphOverEvent goe) throws Exception;
	
	/**
	 * When mouse is pressed the graph
	 * @param gpe
	 * @throws Exception
	 */
	public void onMousePressedGraph(GraphPressEvent gpe) throws Exception;
	
	/**
	 * When mouse is released the graph
	 * @param gre
	 * @throws Exception
	 */
	public void onMouseReleasedGraph(GraphReleaseEvent gre) throws Exception;

}
