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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.chaostocosmos.chaosgraph.AbstractGraph;
import org.chaostocosmos.chaosgraph.GraphElement;
import org.chaostocosmos.chaosgraph.GraphElements;
import org.chaostocosmos.chaosgraph.INTERPOLATE;
import org.chaostocosmos.chaosgraph.InterpolateTransform;
import org.chaostocosmos.chaosgraph.NotMatchGraphTypeException;

/**
* <p>Title: LineGraph</p>
* <p>Description: </p>
* <br><br>
* <br>
* <pre>
*.
*</pre>
*<br>
* <img src="line.jpg">
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* @author 9ins
* @version 1.0, 2001/8/13 19:30 first draft<br>
* @since JDK1.4.1
*/
public class LineGraph extends AbstractGraph
{
    private float LINE_SIZE =2.0f;
    
    /**
     * Constructor
     * @param ge GraphElements
     * @exception GraphElement
     * @since JDK1.4.1
     */
    public LineGraph(GraphElements ge)  {
        this(ge, 600, 300);
    }
    
    /**
     * Constructor
     * @param width int
     * @param height int
     * @param ge GraphElements
     * @exception GraphElement
     * @since JDK1.4.1
     */
    public LineGraph(GraphElements ge, int width, int height) 
    {
        this(ge, "", width, height);
    }
    
    /**
	 * Constructor
     * @param title String
     * @param width int
     * @param height int
     * @param ge GraphElements
     * @exception GraphElement
     * @since JDK1.4.1
     */
    public LineGraph(GraphElements ge, String title, int width, int height) 
    {
        super(ge, title, width, height);
        if (ge.getGraphType() != GRAPH.LINE) {
        	throw new NotMatchGraphTypeException("Can't create "+getGraphStr(ge.getGraphType())+" with LineGraphAWT class.");
        }
    }

    /**
     * Draw line graph
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
        
        List<GraphElement> elements = GRAPH_ELEMENTS.getGraphElementMap().values().stream().collect(Collectors.toList());
        double maxValue = GRAPH_ELEMENTS.getMaximum();
        double tab = GRAPH_WIDTH / GRAPH_ELEMENTS.getXIndexCount();	        
        
        g2d.setClip((int)GRAPH_X, (int)(GRAPH_Y-GRAPH_HEIGHT), (int)GRAPH_WIDTH, (int)GRAPH_HEIGHT);
                
        for (Object elementName : GRAPH_ELEMENTS.getElementOrder()) {
            GraphElement ge = GRAPH_ELEMENTS.getGraphElementMap().get(elementName);
        	List<Double> values = ge.getValues();
            
            if(IS_SELECTION_ENABLE && GRAPH_ELEMENTS.getSelectedElement() != null && ge.getElementName().equals(GRAPH_ELEMENTS.getSelectedElement().getElementName())) {
            	if(SEL_BORDER == SELECTION_BORDER.LINE) {
                	g2d.setStroke(new BasicStroke(BORDER_SIZE*1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            	} else if(SEL_BORDER == SELECTION_BORDER.DOT) {
                	g2d.setStroke(new BasicStroke(BORDER_SIZE*1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{BORDER_SIZE*2}, 0));
            	}
            	setComposite(1.0f, g2d);
                color(ge.getElementColor(), g2d);
            } else {
            	if(IS_SELECTION_ENABLE && GRAPH_ELEMENTS.getSelectedElement() != null && !ge.getElementName().equals(GRAPH_ELEMENTS.getSelectedElement().getElementName())) {
                	setComposite(0.2f, g2d);
            	} else {
            		setComposite(GRAPH_ALPHA, g2d);
            	}
            	g2d.setStroke(new BasicStroke(LINE_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                color(ge.getElementColor(), g2d);
            }
            
            GeneralPath gp = new GeneralPath(GeneralPath.WIND_NON_ZERO, values.size());
            gp.moveTo(tab+GRAPH_X, GRAPH_Y);
            
            List<Point> shapes = new ArrayList<Point>();
            List<Point> shapes1 = new ArrayList<Point>();
            double x = 0, y = 0; 
            
            //List<Point2D.Double> points = IntStream.range(0, values.size())
            //							.mapToObj(i -> {
            //								double x0 = i * tab + GRAPH_X + 1;
            //								double y0 = (LIMIT < maxValue) ? GRAPH_Y - values.get(i) * GRAPH_HEIGHT / maxValue : GRAPH_Y - values.get(i) * GRAPH_HEIGHT / LIMIT;
            //								return new Point2D.Double(x0, y0);
            //							}).collect(Collectors.toList());
            //double[] xValues = IntStream.range(0,  values.size()).mapToDouble(idx -> idx * tab + GRAPH_X + 1).toArray();
            //double[] yValues = values.stream().mapToDouble(Double::doubleValue).toArray();
            //double[] xValues = {100d, 150, 200d};
            //double[] yValues = {100d, 180, 200d};
            //
            //double[] xv = IntStream.range(0, values.size()).mapToDouble(i -> i * tab + GRAPH_X).toArray();
            //double[] yv = IntStream.range(0, values.size()).mapToDouble(i -> (LIMIT < maxValue) ? GRAPH_Y - values.get(i) * GRAPH_HEIGHT / maxValue : GRAPH_Y - values.get(i) * GRAPH_HEIGHT / LIMIT).toArray();
            //double[] xi = IntStream.range(0, 80).mapToDouble(i -> GRAPH_X + (((values.size()-1) * tab) / 80d) * i).toArray();
        	//System.out.println(Arrays.toString(xv));
        	//System.out.println(Arrays.toString(yv));
        	//System.out.println(Arrays.toString(xi));
        	//double[] yi = InterpolateTransform.transform(INTERPOLATE.SPLINE, xv, yv, xi);
            gp.moveTo(x, y);            
    
            if(ge.getInterpolationType() != null) {
            	ge.getInterpolates().stream().forEach(p -> {
            		gp.lineTo(p.x, p.y);
                    shapes.add(new Point((int)(p.x-LINE_SIZE*2), (int)(p.y-LINE_SIZE*2)));
                    shapes1.add(new Point((int)(p.x+LINE_SIZE*2), (int)(p.y+LINE_SIZE*2)));
            	});
            }  else {
	            for(int i=0; i<values.size(); i++) {
	                x = i * tab + GRAPH_X;               
	                y = (LIMIT < maxValue) ? GRAPH_Y - values.get(i) * GRAPH_HEIGHT / maxValue : GRAPH_Y - values.get(i) * GRAPH_HEIGHT / LIMIT;
		            gp.lineTo(x, y);
	                shapes.add(new Point((int)(x-LINE_SIZE*2), (int)(y-LINE_SIZE*2)));
	                shapes1.add(new Point((int)(x+LINE_SIZE*2), (int)(y+LINE_SIZE*2)));
	            }        
            }
            for(int i=shapes1.size()-1; i >= 0; i--) {
            	Point p = shapes1.get(i);
            	shapes.add(p);
            }
            ge.setShapes(shapes);
            color(ge.getElementColor(), g2d);
            g2d.draw(gp);
            gp.closePath();
            gp.reset();
            if(IS_SHOW_PEEK && IS_SELECTION_ENABLE && GRAPH_ELEMENTS.getSelectedElement() != null && ge.getElementName().equals(GRAPH_ELEMENTS.getSelectedElement().getElementName())) {
            	for(int i=0; i<shapes.size()/2; i++) {
            		Point p = shapes.get(i);
            		p.setLocation(p.getX()+LINE_SIZE*2, p.getY()+LINE_SIZE*2);
            		if(ge.getInterpolationType() != null && i % ge.getInterpolateScale() != 0) {
            			continue;
            		}
            		drawPeek(PEEK_STYLE.CIRCLE, p, 3, 5, new Color(180, 180, 180), g2d);
            	}
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
		Collections.reverse(list);
		for(GraphElement ge : list) {
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
		    if(labelPoly.contains(x, y)) {
		    	return ge;
		    } else if(polygon.contains(x, y)) {
		    	double unit = GRAPH_WIDTH / (getGraphElements().getXIndex().size());
		    	int index = (int)((x-GRAPH_X+unit/2) / unit);
		    	if(index < ge.getValues().size()) {
			    	ge.setSelectedValue(ge.getValues().get(index));
			    	ge.setSelectedValueIndex(index);
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
     * Set line thickness
     * @param size float
     * @since JDK1.4.1
     */
    public void setLineSize(float size)
    {
        this.LINE_SIZE = size;
    }
}
