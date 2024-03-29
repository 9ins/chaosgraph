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
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.chaostocosmos.chaosgraph.AbstractGraph;
import org.chaostocosmos.chaosgraph.GraphElement;
import org.chaostocosmos.chaosgraph.GraphElements;
import org.chaostocosmos.chaosgraph.GraphUtility;
import org.chaostocosmos.chaosgraph.NotMatchGraphTypeException;

/**
* <p>Title: AreaGraph</p>
* <p>Description:</p>
* This class provide various option and functionality of drawing area graph.
* <br><br>
* <b>Example</b>
* <br>
* <br>
* <img src="pic/AREA.jpg" alt="">
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
*
* @author 9ins
* @version 1.0, 2001/8/13 19:30 first draft<br>
* @version 1.2, 2006/7/5 
* <br>
* @since JDK1.4.1
*/
public class AreaGraph<V extends Number, X, Y> extends AbstractGraph<V, X, Y>  {	
    /**
     * Constructor
     * @param ge GraphElements
     * @throws NotMatchGraphTypeException
     */
    public AreaGraph(GraphElements<V, X, Y> ge)  {
        this(ge, 600, 300);
    }    
    /**
     * Constructor
     * @param ge
     * @param width
     * @param height
     */
    public AreaGraph(GraphElements<V, X, Y> ge, int width, int height)  {
        this(ge, "", width, height);
    }    
    /**
     * Constructor
     * @param ge
     * @param title
     * @param width
     * @param height
     */
    public AreaGraph(GraphElements<V, X, Y> ge, String title, int width, int height)  {
        super(ge, title, width, height);
        if (ge.getGraphType() != GRAPH.AREA) {
        	throw new NotMatchGraphTypeException("Can't draw graph with given graph elements type: "+ge.getGraphType().name());
        }
    }
    /**
     * Drawing area graph
     * @since JDK1.4.1
     */
    @Override
    public void drawGraph(Graphics2D g2d) {
    	super.drawGraph(g2d);
        //Sort by elements last value
        GRAPH_ELEMENTS.orderElementByLastValue();
    	//System.out.println(g2d);
        double maximum = GRAPH_ELEMENTS.getMaximum();
        List<X> xIndex = GRAPH_ELEMENTS.getXIndex();
        int minXIndex = GRAPH_ELEMENTS.getMinimumXIndexSize();
        if(minXIndex > xIndex.size()) {
            for(int i=0; i<minXIndex-xIndex.size()-1; i++) {
            	xIndex.add(null);
            }
        }
        int xIndexCnt = xIndex.size()-1;
        float intent = (float)GRAPH_WIDTH / (float)xIndexCnt;
        g2d.setStroke(new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setClip((int)GRAPH_X, (int)(GRAPH_Y-GRAPH_HEIGHT), (int)GRAPH_WIDTH, (int)GRAPH_HEIGHT);
        double x1 = Math.cos(Math.toRadians(-(double)SHADOW_ANGLE)) * SHADOW_DIST;
        double y1 = Math.sin(Math.toRadians(-(double)SHADOW_ANGLE)) * SHADOW_DIST;
        double x = 0, y = 0;

        for (Object elementName : GRAPH_ELEMENTS.getElementOrder()) {
            GraphElement<V, X, Y> ge = GRAPH_ELEMENTS.getGraphElementMap().get(elementName);
            List<V> valueList = ge.getValues().stream().map(v -> GraphUtility.roundAvoid(v, GRAPH_ELEMENTS.getDecimalPoint())).collect(Collectors.toList());
            final GeneralPath gp = new GeneralPath(GeneralPath.WIND_NON_ZERO, valueList.size());
            if (IS_SHOW_SHADOW) {
                color(SHADOW_COLOR, g2d);
                setComposite(SHADOW_ALPHA, g2d);
                gp.moveTo(x1 + GRAPH_X, y1 + GRAPH_Y);
                for(int i=0; i<valueList.size(); i++) {
                	double value = (double)valueList.get(i) * VALUE_DIVISION_RATIO;
                	if(value < 0f) {
                	    continue;
                   	}
                    x = x1+(i) * intent+GRAPH_X;
                    y = (LIMIT < maximum) ? y1 + GRAPH_Y - value * GRAPH_HEIGHT / maximum : y1 + GRAPH_Y - value * GRAPH_HEIGHT / LIMIT;
                    gp.lineTo(x, y);
                }
                gp.lineTo(x, GRAPH_Y-GRAPH_BORDER_SIZE);
                gp.lineTo(x1+intent+GRAPH_X, y1+GRAPH_Y-GRAPH_BORDER_SIZE);
                gp.closePath();
                g2d.fill(gp);
                gp.reset();
            }
            boolean isSelected = false;
            if(IS_SELECTION_ENABLE && GRAPH_ELEMENTS.getSelectedElement() != null && ge.getElementName().equals(GRAPH_ELEMENTS.getSelectedElement().getElementName())) {
            	if(SEL_BORDER == SELECTION_BORDER.LINE) {
                	g2d.setStroke(new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            	} else if(SEL_BORDER == SELECTION_BORDER.DOT) {
                	g2d.setStroke(new BasicStroke(BORDER_SIZE * 1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{BORDER_SIZE * 2}, 0));
            	}
            	setComposite(1.0f, g2d);
                color(ge.getElementColor(), g2d);
                isSelected = true;
            } else {
            	if(IS_SELECTION_ENABLE && GRAPH_ELEMENTS.getSelectedElement() != null && !ge.getElementName().equals(GRAPH_ELEMENTS.getSelectedElement().getElementName())) {
                	setComposite(0.15f, g2d);
            	} else {
            		setComposite(GRAPH_ALPHA, g2d);
            	}
            	g2d.setStroke(new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                color(ge.getElementColor(), g2d);
            }
            gp.moveTo(BORDER_SIZE+GRAPH_X, GRAPH_Y);
            List<Point2D.Double> shape = new ArrayList<>();
            x = 0;
            y = 0; 
            if(ge.getInterpolationType() != null) {
            	shape = ge.getInterpolates().stream().map(p -> {
            		gp.lineTo(p.x, p.y);
                    return new Point2D.Double(p.x, p.y);
            	}).collect(Collectors.toList());
            	x = shape.get(shape.size()-1).x;
            } else {
	            for (int i=0; i<ge.getValues().size(); i++) {
	                double value = (double)ge.getValues().get(i);
	                if(value < 0) {
	                	continue;
	                }
	                x = i * intent + GRAPH_X;
	                y = (LIMIT < maximum) ? GRAPH_Y - value * GRAPH_HEIGHT / maximum : GRAPH_Y - value * GRAPH_HEIGHT / LIMIT;
	                gp.lineTo(x, y);
	                shape.add(new Point2D.Double(x, y));
	            }
            }
            shape.add(new Point2D.Double(x, GRAPH_Y + GRAPH_HEIGHT));
            shape.add(new Point2D.Double(GRAPH_X, GRAPH_Y + GRAPH_HEIGHT));
            ge.setShapes(shape);
            gp.lineTo(x, GRAPH_Y);
            gp.closePath();
            g2d.fill(gp);

            if (IS_SHOW_BORDER) {
            	if(isSelected) {
            		color(ge.getElementColor(), SELECTED_COLOR_DENSITY, g2d);
            	}
                g2d.draw(gp);
            }
            gp.reset();
            if(IS_SHOW_PEAK && IS_SELECTION_ENABLE && GRAPH_ELEMENTS.getSelectedElement() != null && ge.getElementName().equals(GRAPH_ELEMENTS.getSelectedElement().getElementName())) {
            	for(int i=0; i<shape.size(); i++) {
            		Point2D.Double p = shape.get(i);
            		if(ge.getInterpolationType() != null && i % ge.getInterpolateScale() != 0) {
            			continue;
            		}
            		drawPeak(PEEK_STYLE.CIRCLE, p, 3, 5, new Color(180, 180, 180), g2d);
            	}
            }
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
        setComposite(LABEL_BG_ALPHA, g2d);
        if (IS_SHOW_LABEL) {
        	List<Object> names = new ArrayList<Object>();
        	for(int i=0; i<GRAPH_ELEMENTS.getElementOrder().size(); i++) {
        		names.add(GRAPH_ELEMENTS.getElementOrder().get(i));
        	}
            drawLabel(FONT_NAME, 
            		LABEL_FONT_SIZE, 
            		Font.BOLD, 
            		LABEL_BG_COLOR, 
            		names.stream().map(n -> GRAPH_ELEMENTS.getGraphElementMap().get(n)).collect(Collectors.toList()),
            		g2d); 
        }
    }
    
    /**
     * Is specific position is in graph element shapes.
     */
    public GraphElement<V, X, Y> isPointOnShapes(int x, int y) {
		List<GraphElement<V, X, Y>> list = new ArrayList<GraphElement<V, X, Y>>(GRAPH_ELEMENTS.getElementOrder().stream().map(n -> GRAPH_ELEMENTS.getGraphElementMap().get(n)).collect(Collectors.toList()));
		//System.out.println(list.toString());
		for(int i=list.size()-1; i>=0; i--) {
			GraphElement<V, X, Y> ge = list.get(i);
		    //System.out.println(ge.getElementName());
		    Polygon elementPoly = getPolygon(ge.getShapes(), false);
		    Polygon labelPoly = getPolygon(ge.getLabelShapes(), false);
		    if(labelPoly == null) {
		    	continue;
		    }
		    if(labelPoly.contains(x, y)) {
		    	return ge;
		    } else if(elementPoly.contains(x, y)) {
		    	double unit = GRAPH_WIDTH / (getGraphElements().getXIndex().size() - 1);
		    	int index = (int)((x - GRAPH_X + unit / 2) / unit);
		    	if(index < ge.getValues().size()) {
			    	ge.setSelectedValue(ge.getValues().get(index));
			    	ge.setSelectedValueIndex(index);
			    	ge.setSelectedPoint(new Point(x, y));
					return ge;
		    	}
		    } else {
		    	ge.setSelectedValue((V)new Double(-1));
		    	ge.setSelectedValueIndex(-1);
		    }
		}
		return null;
    }
}
