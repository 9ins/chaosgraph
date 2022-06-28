package org.chaostocosmos.chaosgraph;

import java.util.EventListener;

/**
 * 
 * GraphSelectionListener
 *
 * @author Kooin-Shin
 * 2020. 8. 24.
 */
public interface GraphSelectionListener<V extends Number, X, Y> extends EventListener {
	
	/**
	 * When mouse is over the graph
	 * @param goe
	 * @throws Exception
	 */
	public void onMouseOverGraph(GraphOverEvent<V, X, Y> goe) throws Exception;
		
	/**
	 * When mouse is pressed the graph
	 * @param gpe
	 * @throws Exception
	 */
	public void onMousePressedGraph(GraphPressEvent<V, X, Y> gpe) throws Exception;
	
	/**
	 * When mouse is released the graph
	 * @param gre
	 * @throws Exception
	 */
	public void onMouseReleasedGraph(GraphReleaseEvent<V, X, Y> gre) throws Exception;

}
