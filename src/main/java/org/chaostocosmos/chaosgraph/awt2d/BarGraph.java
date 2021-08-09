/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph.awt2d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.chaostocosmos.chaosgraph.AbstractGraph;
import org.chaostocosmos.chaosgraph.GraphElement;
import org.chaostocosmos.chaosgraph.GraphElements;
import org.chaostocosmos.chaosgraph.NotMatchArrayException;
import org.chaostocosmos.chaosgraph.NotMatchGraphTypeException;

/**
* <p>Title: BarGraph class</p>
* <p>Description:</p>
*<br>
* <img src="pic/BAR.jpg"  alt="">
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* @author 9ins
* @version 1.0, 2001/8/13 19:30 first draft<br>
* @since JDK1.4.1
*/
public class BarGraph extends AbstractGraph {
    /**
     * Constructor
     * @param ge GraphElements 
     * @throws NotMatchGraphTypeException 
     * @since JDK1.4.1
     */
    public BarGraph(GraphElements ge)  {
        this(ge, 600, 300);
    }
    
    /**
     * Constructor
     * @param width int
     * @param height int
     * @param ge GraphElements
     * @since JDK1.4.1
     */
    public BarGraph(GraphElements ge, int width, int height)  {
        this(ge, "", width, height);
    }
    
    /**
     * Constructor
     * @param ge GraphElements 
     * @param title 
     * @param width int 
     * @param height int 
     * @since JDK1.4.1
     */
    public BarGraph(GraphElements ge, String title, int width, int height)  {
        super(ge, title, width, height);
        if (ge.getGraphType() != GRAPH.BAR) {
        	throw new NotMatchGraphTypeException("Can't draw graph with given graph elements type: "+ge.getGraphType().name());
        }
    }
    
    /**
     * Draw bar graph
     * This method paint bar graph elements
     * @since JDK1.4.1
     */
    @Override
    public void drawGraph(Graphics2D g2d) {
    	super.drawGraph(g2d);
    	
    	List<Object> xIndex = GRAPH_ELEMENTS.getXIndex();
        int minXIndex = GRAPH_ELEMENTS.getMinimumXIndexSize();
        if(minXIndex > xIndex.size()) {
            for(int i=0; i<minXIndex-xIndex.size(); i++) {
            	xIndex.add("");
            }
        }
        
        Map<Object, GraphElement> elementMap = GRAPH_ELEMENTS.getGraphElementMap();
        List<Object> elements = new ArrayList(elementMap.keySet());
        double maxValue = GRAPH_ELEMENTS.getMaximum();

        g2d.setStroke(new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setClip((int)GRAPH_X, (int)(GRAPH_Y-GRAPH_HEIGHT), (int)GRAPH_WIDTH, (int)GRAPH_HEIGHT);
        
        double indent = 10;
        double unit = GRAPH_WIDTH / xIndex.size();
        double tab = unit - (indent * 2);        
        double width = tab / elements.size();
        
        int i = 0;
        for (Object elementName : GRAPH_ELEMENTS.getElementOrder()) {
            GraphElement ge = GRAPH_ELEMENTS.getGraphElementMap().get(elementName);
            List<Double> values = ge.getValues();
            List<Point> shapes = new ArrayList<Point>();
            for(int j=0; j<values.size(); j++) {
	        	double value = values.get(j);
	            if (value < 0) {
	            	continue;
	            }
	            //double x = ((i + 1) * tab - tab / 2) + width * i + GRAPH_X + 1;
	            double x = i * width + (j * (unit) + indent)  + GRAPH_X;
	            x = (x < 1) ? 1 : x;
	            double height = (LIMIT < maxValue) ? value * GRAPH_HEIGHT / maxValue : (value * GRAPH_HEIGHT) / LIMIT;
	            double y = GRAPH_Y - height;
	            
	            //System.out.println("value: "+value+" max : "+maxValue+" limit: "+LIMIT+" tab : "+tab+"   width: "+width+"  elements size: "+elements.size()+"  x: "+x+"  y: "+y+"  height: "+height);
	            if (y<GRAPH_Y && x>GRAPH_X) {
	                if (IS_SHOW_SHADOW) {
	                    color(SHADOW_COLOR, g2d);
	                    setComposite(SHADOW_ALPHA, g2d);
	                    double x1 = Math.cos(Math.toRadians(-(double)SHADOW_ANGLE))*SHADOW_DIST + x;
	                    double y1 = Math.sin(Math.toRadians(-(double)SHADOW_ANGLE))*SHADOW_DIST + y;
	                    g2d.fill(new Rectangle2D.Double(x1, y1, width, height-SHADOW_DIST));
	                }
	                boolean isSelected = false;
	                if(IS_SELECTION_ENABLE && ge.getSelectedValueIndex() == j && GRAPH_ELEMENTS.getSelectedElement() != null && ge.getElementName().equals(GRAPH_ELEMENTS.getSelectedElement().getElementName())) {
	                	if(SEL_BORDER == SELECTION_BORDER.LINE) {
	                    	g2d.setStroke(new BasicStroke(BORDER_SIZE*1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	                	} else if(SEL_BORDER == SELECTION_BORDER.DOT) {
	                    	g2d.setStroke(new BasicStroke(BORDER_SIZE*1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{BORDER_SIZE*2}, 0));
	                	}
	                	setComposite(GRAPH_ALPHA, g2d);
		                color(ge.getElementColor(), SELECTED_COLOR_DENSITY, g2d);
		                isSelected = true;
	                } else {
	                	if(IS_SELECTION_ENABLE && GRAPH_ELEMENTS.getSelectedElement() != null && !ge.getElementName().equals(GRAPH_ELEMENTS.getSelectedElement().getElementName())) {
	                    	setComposite(0.05f, g2d);
	                	} else {
	                		setComposite(GRAPH_ALPHA, g2d);
	                	}
	                	g2d.setStroke(new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		                color(ge.getElementColor(), g2d);
	                }
	                color(ge.getElementColor(), g2d);
	                g2d.fill(new Rectangle2D.Double(x, y, width, height));
	                if (IS_SHOW_BORDER) {
	                	if(isSelected) {
	                		color(ge.getElementColor(), SELECTED_COLOR_DENSITY, g2d);
	                	} else {
	                		color(BORDER_COLOR, g2d);
	                	}
	                    g2d.draw(new Rectangle2D.Double(x, y, width, height));
	                }
	            }
                shapes.add(new Point((int)x, (int)y));
                shapes.add(new Point((int)x, (int)(y+height)));
                shapes.add(new Point((int)(x+width), (int)(y+height)));
                shapes.add(new Point((int)(x+width), (int)y));
            }
            ge.setShapes(shapes);
            i++;
        }
        g2d.setClip(0, 0, IMG_WIDTH, IMG_HEIGHT);
        
        if(IS_SHOW_POPUP && GRAPH_ELEMENTS.getSelectedElement() != null && GRAPH_ELEMENTS.getSelectedElement().getSelectedPoint() != null) {
        	drawPopup(GRAPH_ELEMENTS.getSelectedElement().getSelectedPoint(),
            		POPUP_BG_COLOR, 
            		GRAPH_ELEMENTS.getSelectedElement(),
            		g2d);
        }

        if (IS_SHOW_LABEL) {
            drawLabel(FONT_NAME, 
            		LABEL_FONT_SIZE, 
            		Font.BOLD, 
            		LABEL_BG_COLOR, 
            		GRAPH_ELEMENTS.getElementOrder().stream().map(n -> GRAPH_ELEMENTS.getGraphElementMap().get(n)).collect(Collectors.toList()),
            		g2d); 	//???? ?????.--Graph?? ?????
        }
    }
    
    /**
     * Is specific position is in graph element shapes.
     */
    public GraphElement isPointOnShapes(int x, int y) {
		List<GraphElement> list = new ArrayList<GraphElement>(this.getGraphElements().getGraphElementMap().values());
		for(int i=0; i<list.size(); i++) {
		    GraphElement ge = list.get(i);
		    int valueIndex = 0;
		    int loop = ge.getShapes().size() / 4;
		    for(int j=0; j<loop; j++) {
			    int[] xpoints = new int[4];
			    int[] ypoints = new int[4];
			    for(int k=0; k<4; k++) {
			    	Point p = ge.getShapes().get(j*4+k);
			    	xpoints[k] = (int)p.getX();
			    	ypoints[k] = (int)p.getY();
			    }
			    Polygon elementPoly = new Polygon(xpoints, ypoints, xpoints.length);
			    Polygon labelPoly = getPolygon(ge.getLabelShapes(), false);
			    if(labelPoly.contains(x, y)) {
			    	return ge;
			    } else if(elementPoly.contains(x, y)) {
			    	ge.setSelectedValue(ge.getValues().get(valueIndex));
			    	ge.setSelectedValueIndex(valueIndex);
			    	ge.setSelectedPoint(new Point(x, y));
			    	return ge;
			    } else {
			    	ge.setSelectedValue(-1);
			    	ge.setSelectedValueIndex(-1);
			    	ge.setSelectedPoint(null);
			    }
		    	valueIndex++;
		    }
		}
		return null;
    }
}
