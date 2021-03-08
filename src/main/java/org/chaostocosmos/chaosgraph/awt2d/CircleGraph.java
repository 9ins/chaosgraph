/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph.awt2d;

import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.chaostocosmos.chaosgraph.AbstractGraph;
import org.chaostocosmos.chaosgraph.GraphElement;
import org.chaostocosmos.chaosgraph.GraphElements;
import org.chaostocosmos.chaosgraph.NotMatchArrayException;
import org.chaostocosmos.chaosgraph.NotMatchGraphTypeException;
import org.chaostocosmos.chaosgraph.GraphConstants.GRAPH;

import java.awt.geom.*;
/**
* <p>Title: Circle graph class</p>
* <p>Description:</p>
* <pre>
*.
*</pre>
*<br>
* <img src="circle.jpg">
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* @author 9ins
* @version 1.0, 2001/8/13 19:30 first draft<br>
* @since JDK1.4.1
*/
public class CircleGraph extends AbstractGraph
{
    private boolean isShowPercent = false;   
    private boolean isShowValue = true;    
    
    /**
     * Constructor
     * @param ge GraphElements
     * @since JDK1.4.1
     */
    public CircleGraph(GraphElements ge)  {
        this(ge, 600, 300);
    }
    
    /**
     * Constructor
     * @param width int
     * @param height int
     * @param ge GraphElements
     * @since JDK1.4.1
     */
    public CircleGraph(GraphElements ge, int width, int height)  {
        this(ge, "", width, height);
    }
    
    /**
     * Constructor
     * @param title String
     * @param width int
     * @param height int
     * @param ge GraphElements
     * @since JDK1.4.1
     */
    public CircleGraph(GraphElements ge, String title, int width, int height)  {
        super(ge, title, width, height);
        if (ge.getGraphType() != GRAPH.CIRCLE) {
        	throw new NotMatchGraphTypeException("Can't draw graph with given graph elements type: "+ge.getGraphType().name());
        }
        setShowGraphXY(false);
        setShowGridX(false);
        setShowGridY(false);
        setShowIndexX(false);
        setShowIndexY(false);
    }
 
    /**
     * Draw circle graph
     * @since JDK1.4.1
     */
    @Override
    public void drawGraph(Graphics2D g2d) {
        super.drawGraph(g2d);        
        Map<Object, GraphElement> elementMap = GRAPH_ELEMENTS.getGraphElementMap();
        List<Object> elements = new ArrayList(elementMap.keySet());
        List<Object> xIndex = GRAPH_ELEMENTS.getXIndex();        
        int minXIndex = GRAPH_ELEMENTS.getMinimumXIndexSize();
        if(minXIndex > xIndex.size()) {
            for(int i=0; i<minXIndex-xIndex.size(); i++) {
            	xIndex.add("");
            }
        }

        List<Double> values = elementMap.entrySet().stream().map(ge -> ge.getValue().getValues().get(0)).collect(Collectors.toList());
        double total = values.stream().mapToDouble(Double::doubleValue).sum();
        double temp = 90;        

        float circleWidth = GRAPH_WIDTH-(INDENT_LEFT+INDENT_RIGHT);
        float circleHeight = GRAPH_HEIGHT-(INDENT_TOP+INDENT_BOTTOM);

        float x = GRAPH_X + INDENT_LEFT;
        float y = GRAPH_Y - GRAPH_HEIGHT+INDENT_BOTTOM;
        
        g2d.setClip((int)GRAPH_X, (int)(GRAPH_Y-GRAPH_HEIGHT), (int)GRAPH_WIDTH, (int)GRAPH_HEIGHT);

        if (IS_SHOW_SHADOW)
        {
            float x1 = (float)Math.cos(Math.toRadians(-(double)SHADOW_ANGLE))*SHADOW_DIST + x;
            float y1 = (float)Math.sin(Math.toRadians(-(double)SHADOW_ANGLE))*SHADOW_DIST + y;
            color(SHADOW_COLOR, g2d);
            setComposite(SHADOW_ALPHA, g2d);
            g2d.fill(new Arc2D.Float(x1, y1, circleWidth, circleHeight, 0, 360, Arc2D.PIE));
            color(GRAPH_BG_COLOR, g2d);
            g2d.fill(new Arc2D.Float(x, y, circleWidth, circleHeight, 0, 360, Arc2D.PIE));
        }

        for (Object elementName : GRAPH_ELEMENTS.getElementOrder()) {
            GraphElement ge = GRAPH_ELEMENTS.getGraphElementMap().get(elementName);
        	double value = ge.getValues().get(0);
            if (value < 0) {
            	continue;
            }            
            boolean isSelected = false;
            if(IS_SELECTION_ENABLE && GRAPH_ELEMENTS.getSelectedElement() != null && ge.getElementName().equals(GRAPH_ELEMENTS.getSelectedElement().getElementName())) {
            	if(SEL_BORDER == SELECTION_BORDER.LINE) {
                	g2d.setStroke(new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            	} else if(SEL_BORDER == SELECTION_BORDER.DOT) {
                	g2d.setStroke(new BasicStroke(BORDER_SIZE*1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{BORDER_SIZE*2}, 0));
            	}
            	setComposite(1.0f, g2d);
                color(ge.getElementColor(), SELECTED_COLOR_DENSITY, g2d);
                isSelected = true;
            } else {
            	if(IS_SELECTION_ENABLE && GRAPH_ELEMENTS.getSelectedElement() != null && !ge.getElementName().equals(GRAPH_ELEMENTS.getSelectedElement().getElementName())) {
                	setComposite(0.1f, g2d);
            	} else {
            		setComposite(GRAPH_ALPHA, g2d);
            	}
            	g2d.setStroke(new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                color(ge.getElementColor(), g2d);
            }

            double angle = value * 360 / total * -1;   
            Arc2D.Double af = new Arc2D.Double(x, y, circleWidth, circleHeight, temp, angle, Arc2D.PIE);
            List<Point> shapes = new ArrayList<Point>();
            double cx = x + circleWidth / 2;
            double cy = y + circleHeight / 2;
            shapes.add(new Point((int)cx, (int)cy));
            double unit = angle / 10;
            for(int i=0; i <= 10; i++) {
            	double ang = (temp + unit * i) / 180.0 * Math.PI;
            	//System.out.println(temp+"   "+ang+ "   "+Math.toRadians(ang));
            	int x1 = (int)(cx + Math.cos(-ang) * (circleWidth/2));
            	int y1 = (int)(cy + Math.sin(-ang) * (circleHeight/2));
            	shapes.add(new Point(x1, y1));
            }
            GeneralPath gp = new GeneralPath(GeneralPath.WIND_NON_ZERO, shapes.size());
            gp.moveTo(cx, cy);
            for(Point p : shapes) {
            	gp.lineTo(p.getX(), p.getY());
            }
            ge.setShapes(shapes);
            color(ge.getElementColor(), g2d);
            g2d.fill(af);

        	if(isSelected) {
        		color(ge.getElementColor(), SELECTED_COLOR_DENSITY, g2d);
        	}
            g2d.draw(new Arc2D.Double(x, y, circleWidth, circleHeight, temp, angle, Arc2D.PIE));
            temp += angle;
        } 
        for (Object elementName : GRAPH_ELEMENTS.getElementOrder()) {
            GraphElement ge = GRAPH_ELEMENTS.getGraphElementMap().get(elementName);
        	double value = ge.getValues().get(0);
            if (value < 0) {
            	continue;
            }       
            double angle = value * 360 / total * -1;   
            setComposite(0.6f, g2d);
            color(INDEX_FONT_COLOR, g2d);
            double scale = this.GRAPH_WIDTH * (SCALED_WIDTH) / 400;
            FontMetrics fm = setFont(FONT_NAME, Font.BOLD, (int)scale, g2d);
            String valStr = String.valueOf(value);
            
            double rnd = Math.pow(10, ROUND_PLACE);
        	value = value * 100d / total;
            String perStr = " (" + (Math.round(value * rnd ) / rnd) + "%)";

            int strWidth = fm.stringWidth(valStr+perStr);
            int ascent = fm.getAscent();
            Arc2D.Double af = new Arc2D.Double(x, y, circleWidth, circleHeight, temp, angle, Arc2D.PIE);
            temp += angle;            
            Arc2D.Double af_ = new Arc2D.Double(x+circleWidth/4, y+circleHeight/4, circleWidth/2, circleHeight/2, temp-angle/2, angle/2, Arc2D.PIE);
            float str_x = (float)af_.getStartPoint().getX();
            float str_y = (float)af_.getStartPoint().getY();
            int strHalf = strWidth / 2;
            int strHeight = fm.getHeight();

            if(isInArc(af, new Rectangle.Double((double)str_x, (double)str_y, (double)strWidth/2, (double)strHeight))) {
            	g2d.setClip(new Rectangle.Double((double)str_x-strHalf, (double)str_y-strHeight, (double)strWidth, (double)(strHeight*2)));
            	//g2d.draw(new Rectangle.Double((double)str_x-strHalf, (double)str_y-strHeight, (double)strWidth, (double)(strHeight*2)));

            	if(IS_SHOW_ELEMENT_NAME) {
            		g2d.drawString(ge.getElementName().toString(), str_x-strHalf, str_y);
            	}
	            if (isShowValue) {	                
	                g2d.drawString(valStr, str_x-strWidth/2, str_y+strHeight-fm.getAscent()/2);
	            } 
	            if (isShowPercent) {
	                g2d.drawString(perStr, str_x-strWidth/2+fm.stringWidth(valStr), str_y+strHeight-fm.getAscent()/2);
	            } 	            
	            g2d.setClip((int)GRAPH_X, (int)(GRAPH_Y-GRAPH_HEIGHT), (int)GRAPH_WIDTH, (int)GRAPH_HEIGHT);
            } else {
            	g2d.drawString("...", str_x, str_y);
            }
        }        
        g2d.setClip(0, 0, IMG_WIDTH, IMG_HEIGHT);
        
        if(IS_SHOW_POPUP && GRAPH_ELEMENTS.getSelectedElement() != null && GRAPH_ELEMENTS.getSelectedElement().getSelectedPoint() != null) {
        	drawPopup(GRAPH_ELEMENTS.getSelectedElement().getSelectedPoint(),
            		POPUP_BG_COLOR, 
            		GRAPH_ELEMENTS.getSelectedElement(),
            		g2d);
        }

        if (IS_SHOW_LABEL)
        {
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
    public GraphElement isPointOnShapes(int x, int y) {
		List<GraphElement> list = new ArrayList<GraphElement>(this.getGraphElements().getGraphElementMap().values());
		for(int i=list.size()-1; i >=0; i--) {
		    GraphElement ge = list.get(i);
		    //System.out.println(ge.getElementName());
		    int[] xpoints = new int[ge.getShapes().size()];
		    int[] ypoints = new int[ge.getShapes().size()];
		    int j=0;
		    for(Point p : ge.getShapes()) {
				xpoints[j] = (int)p.getX();
				ypoints[j] = (int)p.getY();
				j++;
		    }
		    Polygon polygon = new Polygon(xpoints, ypoints, ge.getShapes().size());
		    Polygon labelPoly = getPolygon(ge.getLabelShapes(), false);
		    if(labelPoly != null && labelPoly.contains(x, y)) {
		    	return ge;
		    } else if(polygon.contains(x, y)) {
		    	if(ge.getValues().size() > 0) {
			    	ge.setSelectedValue(ge.getValues().get(0));
			    	ge.setSelectedValueIndex(0);
			    	ge.setSelectedPoint(new Point(x, y));
					return ge;
		    	}
		    } else {
		    	ge.setSelectedValue(-1);
		    	ge.setSelectedValueIndex(-1);
		    	ge.setSelectedPoint(null);
		    }
		}
		return null;
    }
    
    /**
     * Whether rectangle is in arc
     * @param arc
     * @param rect
     * @return
     */
    public boolean isInArc(Arc2D.Double arc, Rectangle2D.Double rect) {
    	return arc.contains(rect);
    }
    
    /**
     * Setting whether percentage show on
     * @param is boolean true or false
     * @since JDK1.4.1
     */
    public void setShowPercent(boolean is)
    {
        this.isShowPercent = is;
    }
    
    /**
     * Setting whether value show on
     * @param is boolean true or false
     * @since JDK1.4.1
     */
    public void setShowValue(boolean is)
    {
        this.isShowValue = is;
    }
}
