package org.chaostocosmos.chaosgraph;

import java.util.EventObject;

/**
 * 
 * GraphOverEvent
 * 
 * This is event when mouse is moved in graph element.
 *
 * @author Kooin-Shin
 * 2020. 8. 26.
 */
public class GraphOverEvent<V extends Number, X, Y> extends EventObject {
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
	public GraphOverEvent(Object source, GraphElement<V, X, Y> ge) {
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
	public void setGe(GraphElement<V, X, Y> ge) {
		this.ge = ge;
	}

	@Override
	public String toString() {
		return "GraphOverEvent [ge=" + ge + "]";
	}
}
