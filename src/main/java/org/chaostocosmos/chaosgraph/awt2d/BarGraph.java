/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph.awt2d;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.chaostocosmos.chaosgraph.AbstractGraph;
import org.chaostocosmos.chaosgraph.GraphElement;
import org.chaostocosmos.chaosgraph.GraphElements;
import org.chaostocosmos.chaosgraph.GraphUtility;
import org.chaostocosmos.chaosgraph.NotMatchGraphTypeException;


/**
* <p>Title: BarGraph class</p>
* <p>Description:</p>
* <br>
* <img src="pic/BAR.jpg"  alt="">
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* @author 9ins
* @version 1.0, 2001/8/13 19:30 first draft<br>
* @since JDK1.4.1
*/
public class BarGraph<V extends Number, X, Y> extends AbstractGraph<V, X, Y> {
    /**
     * Constructor
     * @param ge GraphElements 
     * @throws NotMatchGraphTypeException 
     * @since JDK1.4.1
     */
    public BarGraph(GraphElements<V, X, Y> ge)  {
        this(ge, 600, 300);
    }    
    /**
     * Constructor
     * @param width int
     * @param height int
     * @param ge GraphElements
     * @since JDK1.4.1
     */
    public BarGraph(GraphElements<V, X, Y> ge, int width, int height)  {
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
    public BarGraph(GraphElements<V, X, Y> ge, String title, int width, int height)  {
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
    	List<X> xIndex = GRAPH_ELEMENTS.getXIndex();
        int minXIndex = GRAPH_ELEMENTS.getMinimumXIndexSize();
        if(minXIndex > xIndex.size()) {
            for(int i=0; i<minXIndex-xIndex.size(); i++) {
            	xIndex.add(null);
            }
        }
        Map<Object, GraphElement<V, X, Y>> elementMap = GRAPH_ELEMENTS.getGraphElementMap();
        List<Object> elementNames = new ArrayList<>(elementMap.keySet());
        double maximum = (double) GRAPH_ELEMENTS.getMaximum();

        g2d.setStroke(new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setClip((int)GRAPH_X, (int)(GRAPH_Y-GRAPH_HEIGHT), (int)GRAPH_WIDTH, (int)GRAPH_HEIGHT);
        
        double indent = 10d;
        double unit = (double)GRAPH_WIDTH / (double)xIndex.size();
        double tab = unit - (indent * 2);
        double width = tab / (double)elementNames.size();
        
		int xIndexCount = xIndex.size()-1;
        int i = 0;
        for(Object elementName : GRAPH_ELEMENTS.getElementOrder()) {
            GraphElement<V, X, Y> ge = GRAPH_ELEMENTS.getGraphElementMap().get(elementName);
			List<V> values = ge.getValues().stream().map(v -> GraphUtility.roundAvoid(v, GRAPH_ELEMENTS.getDecimalPoint())).collect(Collectors.toList());
            List<Point2D.Double> shapes = new ArrayList<>();
            for(int j=0; j<xIndexCount; j++) {
	        	double value = (double)values.get(j);
	            if (value < 0) {
	            	continue;
	            }
				value = value * VALUE_DIVISION_RATIO;
	            //double x = ((i + 1) * tab - tab / 2) + width * i + GRAPH_X + 1;
	            double x = i * width + (j * (unit) + indent)  + GRAPH_X;
	            x = (x < 1) ? 1 : x;
	            double height = (LIMIT < maximum) ? value * GRAPH_HEIGHT / maximum : (value * GRAPH_HEIGHT) / LIMIT;
	            double y = GRAPH_Y - height;
	            
	            //System.out.println("value: "+value+" max : "+maxValue+" limit: "+LIMIT+" tab : "+tab+"   width: "+width+"  elements size: "+elements.size()+"  x: "+x+"  y: "+y+"  height: "+height);
	            if (y < GRAPH_Y && x > GRAPH_X) {
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
                shapes.add(new Point2D.Double(x, y)); 
                shapes.add(new Point2D.Double(x, y+height));
                shapes.add(new Point2D.Double(x+width, y+height)); 
                shapes.add(new Point2D.Double(x+width, y));
            }
            ge.setShapes(shapes);
            i++;
        }
        g2d.setClip(0, 0, IMG_WIDTH, IMG_HEIGHT);

        //Draw grid Y axis
        if (IS_SHOW_GRID_Y) {
            g2d.setStroke(new BasicStroke(GRID_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            drawGridY(super.getGraphElements().getYIndex(), GRID_Y_COLOR, GRID_STYLE, LIMIT, maximum, g2d);
        }        
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
            		g2d); 	
        }
    }
    
    /**
     * Is specific position is in graph element shapes.
     */
    public GraphElement<V, X, Y> isPointOnShapes(int x, int y) {
		List<GraphElement<V, X, Y>> list = new ArrayList<GraphElement<V, X, Y>>(this.getGraphElements().getGraphElementMap().values());
		for(int i=0; i<list.size(); i++) {
		    GraphElement<V, X, Y> ge = list.get(i);
		    int valueIndex = 0;
		    int loop = ge.getShapes().size() / 4;
		    for(int j=0; j<loop; j++) {
			    int[] xpoints = new int[4];
			    int[] ypoints = new int[4];
			    for(int k=0; k<4; k++) {
			    	Point2D.Double p = ge.getShapes().get(j*4+k);
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
			    	ge.setSelectedValue((V)new Double(-1d));
			    	ge.setSelectedValueIndex(-1);
			    }
		    	valueIndex++;
		    }
		}
		return null;
    }
}
