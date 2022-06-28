package org.chaostocosmos.chaosgraph;

import java.util.EventObject;

/**
 * 
 * GraphElementEvent
 *
 * @author Kooin-Shin
 * 2020. 8. 12.
 */
public class GraphElementEvent<V extends Number, X, Y> extends EventObject {

	/**
	 * Graph object
	 */
	private Graph<V, X, Y> graph;
	
	/**
	 * Graph element object
	 */
	private GraphElement<V, X, Y> graphElement;
	
	/**
	 * Constructor
	 * @param source
	 * @param graph
	 * @param graphElement
	 */
	public GraphElementEvent(Object source, Graph<V, X, Y> graph, GraphElement<V, X, Y> graphElement) {
		super(source);
		this.graphElement = graphElement;
	}

	/**
	 * Get graph
	 * @return
	 */
	public Graph<V, X, Y> getGraph() {
		return graph;
	}

	/**
	 * Get element
	 * @return
	 */
	public GraphElement<V, X, Y> getGraphElement() {
		return graphElement;
	}

	@Override
	public String toString() {
		return "GraphElementEvent [graph=" + graph + ", graphElement=" + graphElement + "]";
	}
}
