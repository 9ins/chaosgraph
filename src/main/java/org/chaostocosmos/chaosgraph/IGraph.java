package org.chaostocosmos.chaosgraph;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.chaostocosmos.chaosgraph.GraphConstants.GRAPH;

/**
 * IGraph
 * 
 * Interface for AWT graph.
 *
 * @author Kooin-Shin
 * 2020. 8. 31.
 */
public interface IGraph {
	
	/**
	 * Get graph type
	 * @return
	 */
	public GRAPH getGraphType();
	
	/**
	 * This method is part of drawing background. Therefore it must be implemented on inherited class.
	 */
	public void initGraph(Graphics2D g2d, int width, int height);
	
    /**
     * This method is part of drawing graph. Therefore it must be implemented on inherited class.
     * @since JDK1.4.1
     */
    public void drawGraph(Graphics2D g2d);
    
    /**
     * This method do repaint graph
     */
    public void repaint(Graphics2D g2d);
    
    /**
     * This method contribute to resize graph
     * @param width
     * @param height
     */
    public void resizeImage(int width, int height);
    
    /**
     * This method contribute to resize graph to specific Graphics2D object.
     * @param g2d
     * @param width
     * @param height
     */
    public void resizeImage(Graphics2D g2d, int width, int height);
    
    /**
     * Get buffered image object
     * @return
     */
    public BufferedImage getBufferedImage();
    
    /**
     * This method is get whether specific position is in graph element shapes.
     * @param x
     * @param y
     * @return GraphElement
     */
    public GraphElement isPointOnShapes(int x, int y);
}
