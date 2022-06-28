package org.chaostocosmos.chaosgraph;

import java.util.EventObject;

/**
 * 
 * GraphReleaseEvent
 * 
 * This event is occurred when mouse is released on graph element.
 *
 * @author Kooin-Shin
 * 2020. 8. 26.
 */
public class GraphReleaseEvent<V extends Number, X, Y> extends EventObject {
	/**
	 * Graph element
	 */
	GraphElement<V, X, Y> ge;
	
	/**
	 * constructor
	 * 
	 * @param source
	 * @param ge
	 */
	public GraphReleaseEvent(Object source, GraphElement<V, X, Y> ge) {
		super(source);
		this.ge = ge;
	}

	/**
	 * Get graph element
	 * @return
	 */
	public GraphElement<V, X, Y> getGraphElement() {
		return ge;
	}

	/**
	 * Set graph element
	 * @param ge
	 */
	public void setGraphElement(GraphElement<V, X, Y> ge) {
		this.ge = ge;
	}

	@Override
	public String toString() {
		return "GraphReleaseEvent [ge=" + ge + "]";
	}
}
