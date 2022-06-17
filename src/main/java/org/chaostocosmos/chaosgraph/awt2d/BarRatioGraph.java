package org.chaostocosmos.chaosgraph.awt2d;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.chaostocosmos.chaosgraph.AbstractGraph;
import org.chaostocosmos.chaosgraph.GraphElement;
import org.chaostocosmos.chaosgraph.GraphElements;
import org.chaostocosmos.chaosgraph.NotMatchGraphTypeException;

/**
 * 
 * BarRatioGraph
 *
 * @author Kooin-Shin
 * 2020. 9. 23.
 */
public class BarRatioGraph<V, X, Y> extends AbstractGraph<V, X, Y> {
	
    /**
     * Constructor
     * @param ge GraphElements 
     * @throws NotMatchGraphTypeException 
     * @since JDK1.4.1
     */
    public BarRatioGraph(GraphElements<V, X, Y> ge)  {
        this(ge, 600, 300);
    }
    
    /**
     * Constructor
     * @param width int
     * @param height int
     * @param ge GraphElements
     * @since JDK1.4.1
     */
    public BarRatioGraph(GraphElements<V, X, Y> ge, int width, int height)  {
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
    public BarRatioGraph(GraphElements<V, X, Y> ge, String title, int width, int height)  {
        super(ge, title, width, height);
        if (ge.getGraphType() != GRAPH.BAR_RATIO) {
        	throw new NotMatchGraphTypeException("Can't draw graph with given graph elements type: "+ge.getGraphType().name());
        }
    }

    /**
     * Draw bar ratio graph
     * This method paint bar graph elements
     * @since JDK1.4.1
     */
	@Override
	public void drawGraph(Graphics2D g2d) {
        super.drawGraph(g2d);
        double maximum = 0d;
        for(int i=0; i<GRAPH_ELEMENTS.getXIndexCount(); i++) {
        	final int idx = i;
        	double sum = GRAPH_ELEMENTS.getGraphElementMap().values().stream().mapToDouble(ge -> {
        		if(ge.getValues().size() > idx) {
        			return (double)ge.getValues().get(idx);
        		}
        		return 0d;
        	}).sum();        	
        	maximum = maximum > sum ? maximum : sum;
        }
        //maximum += maximum/20;
        if(maximum > (double)GRAPH_ELEMENTS.getMaximum()) {
        	setLimit(maximum);
        }
    	super.setShowShadow(false);    	
    	List<X> xIndex = GRAPH_ELEMENTS.getXIndex();
        int minXIndex = GRAPH_ELEMENTS.getMinimumXIndexSize();
        if(minXIndex > xIndex.size()) {
            for(int i=0; i<minXIndex-xIndex.size(); i++) {
            	xIndex.add(null);
            }
        }
        Map<Object, GraphElement<V, X, Y>> elementMap = GRAPH_ELEMENTS.getGraphElementMap();
        List<Object> elements = new ArrayList(elementMap.keySet());
        Collections.reverse(elements);

        double indent = 10;
        double unit = GRAPH_WIDTH / xIndex.size();
        double width = unit - indent * 2;
        int xIndexCount = GRAPH_ELEMENTS.getXIndexCount();
        
        g2d.setStroke(new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setClip((int)GRAPH_X, (int)(GRAPH_Y-GRAPH_HEIGHT), (int)GRAPH_WIDTH, (int)GRAPH_HEIGHT);

        for(int i=0; i<xIndexCount; i++) {
    		double x = i * unit + indent + GRAPH_X;
    		double y = GRAPH_Y;
    		int j = 0;
            for (Object elementName : GRAPH_ELEMENTS.getElementOrder()) {
                GraphElement<V, X, Y> ge = GRAPH_ELEMENTS.getGraphElementMap().get(elementName);
        		if(i > ge.getValues().size() -1) {
        			continue;
        		}
        		double value = (double)ge.getValues().get(i) * super.VALUE_DIVISION_RATIO;
        		double height = ((double)LIMIT > (double)maximum) ? value : (value * GRAPH_HEIGHT / maximum);
        		y -= height;
        		//System.out.println(ge.getElementName()+"  x: "+x+"   y: "+y+"   height: "+height+"   maximum: "+maximum+"   value: "+value+"   limit: "+LIMIT);        		
                if (IS_SHOW_SHADOW) {
                    color(SHADOW_COLOR, g2d);
                    setComposite(SHADOW_ALPHA, g2d);
                    double x1 = Math.cos(Math.toRadians(-(double)SHADOW_ANGLE))*SHADOW_DIST + x;
                    double y1 = Math.sin(Math.toRadians(-(double)SHADOW_ANGLE))*SHADOW_DIST + y;
                    g2d.fill(new Rectangle2D.Double(x1, y1, width, height-SHADOW_DIST));
                    color(GRAPH_BG_COLOR, g2d);
                    g2d.fill(new Rectangle2D.Double(x, y, width, height-SHADOW_DIST));
                }                
                boolean isSelected = false;
                if(IS_SELECTION_ENABLE && GRAPH_ELEMENTS.getSelectedElement() != null && ge.getElementName().equals(GRAPH_ELEMENTS.getSelectedElement().getElementName())) {
                	if(SEL_BORDER == SELECTION_BORDER.LINE) {
                    	g2d.setStroke(new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
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
                g2d.fill(new Rectangle2D.Double(x, y, width, height));
                if (IS_SHOW_BORDER) {
                	if(isSelected) {
                		color(ge.getElementColor(), SELECTED_COLOR_DENSITY, g2d);
                	} else {
                		color(BORDER_COLOR, g2d);
                	}
                    g2d.draw(new Rectangle2D.Double(x, y, width, height));
                }
                List<Point> shapes = ge.getShapes();
                if(shapes == null) {
                	shapes = new ArrayList<Point>();
                }
                shapes.add(new Point((int)x, (int)y));
                shapes.add(new Point((int)x, (int)(y+height)));
                shapes.add(new Point((int)(x+width), (int)(y+height)));
                shapes.add(new Point((int)(x+width), (int)y));
                while(shapes.size() > ge.getValues().size() * 4) {
                	shapes.remove(0);
                }
                j++;
        	}
        }        
        g2d.setClip(0, 0, IMG_WIDTH, IMG_HEIGHT);
        
        if(IS_SHOW_POPUP && GRAPH_ELEMENTS.getSelectedElement() != null && GRAPH_ELEMENTS.getSelectedElement().getSelectedPoint() != null) {
        	drawPopup(GRAPH_ELEMENTS.getSelectedElement().getSelectedPoint(),
            		POPUP_BG_COLOR, 
            		GRAPH_ELEMENTS.getSelectedElement(),
            		g2d);
        }
        
        if (IS_SHOW_LABEL) {
        	List<Object> names = new ArrayList<Object>();
        	for(int i = GRAPH_ELEMENTS.getElementOrder().size() -1; i >= 0; i--) {
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

	@Override
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
			    	ge.setSelectedValue((V)new Double(-1));
			    	ge.setSelectedValueIndex(-1);
			    	ge.setSelectedPoint(null);
			    }
		    	valueIndex ++;
		    }
		}
		return null;
	}
}
