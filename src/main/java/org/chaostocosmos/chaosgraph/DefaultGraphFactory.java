package org.chaostocosmos.chaosgraph;

import org.chaostocosmos.chaosgraph.GraphConstants.GRAPH;
import org.chaostocosmos.chaosgraph.awt2d.AreaGraph;
import org.chaostocosmos.chaosgraph.awt2d.BarGraph;
import org.chaostocosmos.chaosgraph.awt2d.BarRatioGraph;
import org.chaostocosmos.chaosgraph.awt2d.CircleGraph;
import org.chaostocosmos.chaosgraph.awt2d.LineGraph;

/**
 * 
 * DefaultGraphFactory
 *
 * @author Kooin-Shin
 * 2020. 9. 16.
 */
public class DefaultGraphFactory {

	public static <V, X, Y> AbstractGraph<V, X, Y> createGraph(GRAPH graphType, GraphElements<V, X, Y> elements, int width, int height) {
		AbstractGraph<V, X, Y> graph;
        if(graphType.compareTo(GRAPH.AREA) == 0) {
        	graph = new AreaGraph(elements, width, height);
        } else if(graphType.compareTo(GRAPH.BAR) == 0) {
        	graph = new BarGraph(elements, width, height);
        } else if(graphType.compareTo(GRAPH.BAR_RATIO) == 0) {
        	graph = new BarRatioGraph(elements, width, height);
        } else if(graphType.compareTo(GRAPH.CIRCLE) == 0) {
        	graph = new CircleGraph(elements, width, height);
        } else if(graphType.compareTo(GRAPH.LINE) == 0) {
        	graph = new LineGraph(elements, width, height);
        } else {
        	throw new IllegalArgumentException("Wrong graph type is defined. Parameter type: "+graphType.name());
        }
        return graph;
	}
}
