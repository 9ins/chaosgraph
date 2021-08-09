/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph.awt2d;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.chaostocosmos.chaosgraph.AbstractGraph;
import org.chaostocosmos.chaosgraph.DefaultGraphFactory;
import org.chaostocosmos.chaosgraph.Graph;
import org.chaostocosmos.chaosgraph.GraphConstants.GRAPH;
import org.chaostocosmos.chaosgraph.GraphElement;
import org.chaostocosmos.chaosgraph.GraphElements;
import org.chaostocosmos.chaosgraph.GraphOverEvent;
import org.chaostocosmos.chaosgraph.GraphPressEvent;
import org.chaostocosmos.chaosgraph.GraphReleaseEvent;

import java.awt.image.BufferedImage;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.Toolkit;
import java.awt.Graphics2D;

/**
* <p>Title: Graph panel class</p>
* <p>Description:</p>
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* @author 9ins
* @version 1.2, 2006/7/5 first draft
* @since JDK1.4.1
*/
public class GraphPanel extends JPanel {      
	Graph graph;
	GraphElements elements;
    BufferedImage buffImg = null;     
    Graphics2D g2d;
    private ComponentAdapterExt component = null;
    private ContainerAdapterExt container = null;
    private MouseAdapterExt mouse = null;
    
    /**
     * Constructor
     * @param graphType
     * @param elements
     * @param width
     * @param height
     */
    public GraphPanel(GRAPH graphType, GraphElements elements, int width, int height) {  
    	this(DefaultGraphFactory.createGraph(graphType, elements, width, height), width, height);
    }
    
    /**
     * constructor
     * @param graph
     * @param width
     * @param height
     */
    public GraphPanel(Graph graph, int width, int height) {
    	this.graph = graph;
        this.component = new ComponentAdapterExt();
        this.container = new ContainerAdapterExt();
        this.mouse = new MouseAdapterExt(this);
        this.addComponentListener(this.component);
        this.addContainerListener(this.container);
        this.addMouseListener(this.mouse);
        this.addMouseMotionListener(this.mouse);
        this.addMouseWheelListener(this.mouse);
        this.setPreferredSize(new Dimension(width, height));   	
    }
    
    /**
     * 
     * @param img BufferedImage
     * @since JDK1.4.1
     */
    public void setGraphImage(BufferedImage img) {
        this.buffImg = img;
        repaint();
        updateUI();
    }
    
    /**
     * Get graph
     * @return
     */
    public Graph getGraph() {
    	return this.graph;
    }
    
    /**
     * Override from JPanel method to catch Graphics object
     * @param g Graphics
     * @since JDK1.4.1
     */
    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
        this.g2d = (Graphics2D)g;        
        if(this.g2d != null) {
        	this.graph.setGraphics2D(this.g2d);
        	this.graph.repaint();
        }
    }
    /**
     * Rotate image
     * @param angle
     * @param x
     * @param y
     */
    public void rotate(double angle, double x, double y) 
    {
    	repaint();
    }
    /**
     * 
     * MouseAdapterExt
     *
     * @author Kooin-Shin
     * 2020. 8. 26.
     */
    public class MouseAdapterExt extends MouseAdapter {
    	
    	Component component;
    	
    	public MouseAdapterExt(Component canvas) {
    		this.component = canvas;
    	}
		
		public void mousePressed(MouseEvent me) {
		    GraphElement ge = graph.isPointOnShapes(me.getX(), me.getY());
		    if(ge != null) {
				//System.out.println(ge.getElementName()+"   selected index: "+ge.getSelectedValueIndex()+"  value : "+ge.getSelectedValue());
			    graph.getGraphSelectionListenerList().stream().forEach(gl -> {
					try {
						gl.onMousePressedGraph(new GraphPressEvent(graph, ge));
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				graph.getGraphElements().setSelectedElement(ge);
				component.repaint();
				return;
		    } else if(SwingUtilities.isLeftMouseButton(me)) {
		    	graph.getGraphElements().circulateElement(true);
		    } else if(SwingUtilities.isRightMouseButton(me)) {
		    	graph.getGraphElements().circulateElement(false);
		    }
		    graph.getGraphElements().setSelectedElement(null);
		    component.repaint();
		}
		
		public void mouseReleased(MouseEvent me) {
		    GraphElement ge = graph.isPointOnShapes(me.getX(), me.getY());
		    if(ge != null) {
				//System.out.println(ge.getElementName()+"   selected index: "+ge.getSelectedValueIndex()+"  value : "+ge.getSelectedValue());
			    graph.getGraphSelectionListenerList().stream().forEach(gl -> {
					try {
						gl.onMouseReleasedGraph(new GraphReleaseEvent(graph, ge));
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				graph.getGraphElements().setSelectedElement(ge);
				component.repaint();
				return;
		    }
		    graph.getGraphElements().setSelectedElement(null);
		    component.repaint();
		}
		
		public void mouseMoved(MouseEvent me) {	    
		    GraphElement ge = graph.isPointOnShapes(me.getX(), me.getY());
		    if(ge != null) {
				//System.out.println(ge.getElementName()+"   selected index: "+ge.getSelectedValueIndex()+"  value : "+ge.getSelectedValue());
			    graph.getGraphSelectionListenerList().stream().forEach(gl -> {
					try {
						gl.onMouseOverGraph(new GraphOverEvent(graph, ge));
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				graph.getGraphElements().setSelectedElement(ge);
				component.repaint();
				return;
		    }
		    graph.getGraphElements().setSelectedElement(null);
		    component.repaint();
		}
		
		public void mouseWheelMoved(MouseWheelEvent e) {
			//System.out.println(e.toString());
			double scale = 1.0d + (e.getWheelRotation() > 0 ? graph.getWheelUnitScale() : -graph.getWheelUnitScale());
			int width = (int)(graph.getImageWidth() * scale);
			int height = (int)(graph.getImageHeight() * scale);
			if(width <= graph.getIndentLeft()+graph.getIndentRight()+100 || width > Toolkit.getDefaultToolkit().getScreenSize().getWidth()) 
				return;
            if(graph != null) {
            	graph.resizeImage((int)width, (int)height);            
            }
		}
    }
    
    /**
     * <p>Title: Component adapter</p>
     * <p>Description: </p>
     * <p>Copyright: Copyleft (c) 2006</p>
     * <p>Company: ChaosToCosmos</p>
     * @author Kooin-Shin
     * @version 1.0
     * @since JDK1.4.1
     */
    public class ComponentAdapterExt extends ComponentAdapter
    {
        /**
         * Doing when this component be shown
         * @param e ComponentEvent
         * @since JDK1.4.1
         */
        public void componentShown(ComponentEvent e)
        {
            componentResized(e);
        }        
        
        /**
         * Doing when this component be resized
         * @param e ComponentEvent
         * @since JDK1.4.1
         */
        public void componentResized(ComponentEvent e)
        {
            int width = e.getComponent().getWidth();
            int height = e.getComponent().getHeight();
            if(graph != null) {
            	graph.resizeImage(width, height);            
            }
        }
    }
    
    /**
     * 
     * ContainerAdapterExt
     *
     * @author Kooin-Shin
     * 2020. 9. 23.
     */
    public class ContainerAdapterExt extends ContainerAdapter
    {
    	public void componentAdded(ContainerEvent e)
    	{
    	}
    	public void componentRemoved(ContainerEvent e) {
    	}
    }
}
