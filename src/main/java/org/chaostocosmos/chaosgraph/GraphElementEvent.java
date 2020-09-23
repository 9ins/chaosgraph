package org.chaostocosmos.chaosgraph;

import java.util.EventObject;

/**
 * 
 * GraphElementEvent
 *
 * @author Kooin-Shin
 * 2020. 8. 12.
 */
public class GraphElementEvent extends EventObject {

	/**
	 * Graph object
	 */
	private Graph graph;
	
	/**
	 * Graph element object
	 */
	private GraphElement graphElement;
	
	/**
	 * Constructor
	 * @param source
	 * @param graph
	 * @param graphElement
	 */
	public GraphElementEvent(Object source, Graph graph, GraphElement graphElement) {
		super(source);
		this.graphElement = graphElement;
	}

	/**
	 * Get graph
	 * @return
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * Get element
	 * @return
	 */
	public GraphElement getGraphElement() {
		return graphElement;
	}

	@Override
	public String toString() {
		return "GraphElementEvent [graph=" + graph + ", graphElement=" + graphElement + "]";
	}
}
