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
public class GraphOverEvent extends EventObject {
	/**
	 * Graph element
	 */
	GraphElement ge;

	/**
	 * Constructor
	 * 
	 * @param source
	 * @param ge
	 */
	public GraphOverEvent(Object source, GraphElement ge) {
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
	public void setGe(GraphElement ge) {
		this.ge = ge;
	}

	@Override
	public String toString() {
		return "GraphOverEvent [ge=" + ge + "]";
	}
}
