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
public class GraphPressEvent extends EventObject {
	/**
	 * Graph element
	 */
	GraphElement ge;

	/**
	 * Constructor
	 * 
	 * @param source
	 * @param me
	 * @param ge
	 */
	public GraphPressEvent(Object source, GraphElement ge) {
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
		return "GraphPressEvent [ge=" + ge + "]";
	}
}
