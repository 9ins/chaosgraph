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
public class GraphReleaseEvent extends EventObject {
	/**
	 * Graph element
	 */
	GraphElement ge;
	
	/**
	 * constructor
	 * 
	 * @param source
	 * @param ge
	 */
	public GraphReleaseEvent(Object source, GraphElement ge) {
		super(source);
		this.ge = ge;
	}

	/**
	 * Get graph element
	 * @return
	 */
	public GraphElement getGraphElement() {
		return ge;
	}

	/**
	 * Set graph element
	 * @param ge
	 */
	public void setGraphElement(GraphElement ge) {
		this.ge = ge;
	}

	@Override
	public String toString() {
		return "GraphReleaseEvent [ge=" + ge + "]";
	}
}
