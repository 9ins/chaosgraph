package org.chaostocosmos.chaosgraph;

import java.util.EventObject;

/**
 * 
 * GraphPressEvent
 *
 * This event is occurred when mouse is pressed on graph element.
 * @author Kooin-Shin
 * 2020. 8. 26.
 */
public class GraphPressEvent<V extends Number, X, Y> extends EventObject {
	/**
	 * Graph element
	 */
	GraphElement<V, X, Y> ge;

	/**
	 * Constructor
	 * 
	 * @param source
	 * @param ge
	 */
	public GraphPressEvent(Object source, GraphElement<V, X, Y> ge) {
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
		return "GraphPressEvent [ge=" + ge + "]";
	}
}
