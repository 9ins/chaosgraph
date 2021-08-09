package org.chaostocosmos.chaosgraph;

import java.awt.AlphaComposite;
import java.awt.BasicStroke; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.chaostocosmos.chaosgraph.Graph;
import org.chaostocosmos.chaosgraph.awt2d.GraphPanel;
/**
* <p>Title: Graph class</p>
* <p>Description:</p>
* <pre>
* This class is abstract of all kind of graph.
* The inheritance of this class must be implemented 'drawGraph' and 'resizeImage' method on itself.
* This class have many various variables of graph sizes, positions, colors... what be need to draw graph and have many initial configurations of graph e.g. xy axis config and indexes.
* </pre>
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* 
* @author Kooin-Shin(9ins)
* @version 1.0, 2001/8/13 19:30 first draft<br>
* @version 1.2, 2006/7/5 <br>
*               2006/7/6 <br>
*               2006/7/11 <br>
*               2006/7/12 <br>
*               2020/08/12<br>
* @since JDK1.4.1
*/
public abstract class AbstractGraph extends Graph {
    protected Color IMG_BG_COLOR = new Color(230,230,255);		//Color of image object background
    protected Color IMG_BORDER_COLOR = new Color(200,200,230);	//Color of image object border
    protected Color GRAPH_BG_COLOR = new Color(255,255,255);	//Color of graph background 
    protected Color GRAPH_BORDER_COLOR = new Color(180,180,230);//Color of graph border
    protected Color BORDER_COLOR = new Color(150,150,150);		//Color of border
    protected Color LABEL_BG_COLOR = new Color(200,200,220);	//Color of label background
    protected Color TITLE_FONT_COLOR = new Color(120,120,170);	//Color of title font
    protected Color INDEX_FONT_COLOR = new Color(100,100,100);	//Color of index font
    protected Color GRAPH_XY_COLOR = new Color(50,50,50);		//Color of graph xy axis
    protected Color GRID_X_COLOR = new Color(100,120,100);		//Color of grid x
    protected Color GRID_Y_COLOR = new Color(100,120,100);		//Color of grid y
    protected Color SHADOW_COLOR = new Color(200,200,200);		//Color of shadow 
    protected Color DEFAULT_COLOR = new Color(220,220,220);		//Default color for temporary use
    protected Color POPUP_BG_COLOR = new Color(220, 220, 220);
    protected Color POPUP_FONT_COLOR = new Color(100, 100, 100);
    protected Color PEEK_COLOR = Color.BLACK;
    
    /**
     * Constructor
     * @param elements
     * @since JDK1.4.1
     */
    public AbstractGraph(GraphElements elements) {
        this(elements, 600, 300);
    }
    
    /**
     * Constructor
     * @param elements
     * @param width 
     * @param height 
     * @since JDK1.4.1
     */
    public AbstractGraph(GraphElements elements, int width, int height) {
        this(elements, "", width, height);
    }
    
    /**
     * Constructor
     * @param elements
     * @param title
     * @param width
     * @param height
     * @since JDK1.4.1
     */
    public AbstractGraph(GraphElements elements, String title, int width, int height) {
    	super(elements, title, width, height);
    }
    
    @Override
    public void drawGraph(Graphics2D g2d) {
        initGraph(g2d, IMG_WIDTH, IMG_HEIGHT);
    	sweepBg(IMG_WIDTH, IMG_HEIGHT);
        GRAPH graphType = GRAPH_ELEMENTS.getGraphType();
        double maxValue = GRAPH_ELEMENTS.getMaximum();
        List<Double> yIndex = GRAPH_ELEMENTS.getYIndex();
        List<Object> xIndex = GRAPH_ELEMENTS.getXIndex();
                
        if (IS_SHOW_BG) {
            setComposite(IMG_BG_ALPHA, g2d);
            drawBg(IMG_BG_COLOR, g2d); 					//draw graph background
            if (IS_SHOW_IMG_BORDER) drawBgBorder(IMG_BORDER_SIZE, IMG_BORDER_COLOR, g2d);
            setComposite(GRAPH_BG_ALPHA, g2d);
            drawGraphBg(GRAPH_BG_COLOR, g2d);
            if (IS_SHOW_GRAPH_BORDER) drawGraphBorder(GRAPH_BORDER_SIZE, GRAPH_BORDER_COLOR, GRAPH_BORDER_SIZE*2, GRAPH_BORDER_SIZE*2, g2d);
        }
        setComposite(GRAPH_XY_ALPHA, g2d);
        if (IS_SHOW_GRAPH_XY) {
            drawXY(GRAPH_XY_SIZE, GRAPH_XY_COLOR, g2d);						//draw x/y axis
        }
        setComposite(INDEX_FONT_ALPHA, g2d);
        if (IS_SHOW_INDEX_X) {
            drawIndexX(graphType, FONT_NAME, INDEX_FONT_SIZE, INDEX_FONT_COLOR, xIndex, g2d); 					//draw x indexex
        }
        if (IS_SHOW_INDEX_Y) {
            drawIndexY(FONT_NAME, INDEX_FONT_SIZE, INDEX_FONT_COLOR, yIndex, LIMIT, maxValue, g2d); 					//draw y indexex
        }
        setComposite(GRID_ALPHA, g2d);
        if (IS_SHOW_GRID_X) {
            g2d.setStroke(new BasicStroke(GRID_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            drawGridX(xIndex, GRID_X_COLOR, GRID_STYLE, g2d);
        }
        if (IS_SHOW_GRID_Y) {
            g2d.setStroke(new BasicStroke(GRID_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            drawGridY(yIndex, GRID_Y_COLOR, GRID_STYLE, LIMIT, maxValue, g2d);
        }
        setComposite(TITLE_FONT_ALPHA, g2d);
        if (IS_SHOW_TITLE) {
            drawTitle(TITLE, FONT_NAME, TITLE_FONT_SIZE, TITLE_FONT_COLOR, g2d);
            drawRight(g2d);
        }
    }
    
    /**
     * To set image background color
     * @param color Color 
     * @since JDK1.4.1
     */
    public void setImgBgColor(Color color) {
        IMG_BG_COLOR = color;
    }
    
    /**
     * To get image background color
     * @return
     */
    public Color getImgBgColor() {
    	return IMG_BG_COLOR;
    }
    
    /**
     * To set image border color
     * @param color Color
     * @since JDK1.4.1
     */
    public void setImgBorderColor(Color color) {
        IMG_BORDER_COLOR = color;
    }
    
    /**
     * To get image border color
     * @return
     */
    public Color getImgBorderColor() {
    	return IMG_BORDER_COLOR;
    }
    
    /**
     * To set graph background color
     * @param color Color Color
     * @since JDK1.4.1
     */
    public void setGraphBgColor(Color color) {
        GRAPH_BG_COLOR = color;
    }
    
    /**
     * To get graph background color
     * @return
     */
    public Color getGraphBgColor() {
    	return GRAPH_BG_COLOR;
    }
    
    /**
     * To set graph border color
     * @param color Color
     * @since JDK1.4.1
     */
    public void setGraphBorderColor(Color color) {
        GRAPH_BORDER_COLOR = color;
    }
    
    /**
     * To get graph border color
     * @return
     */
    public Color getGraphBorderColor() {
    	return GRAPH_BORDER_COLOR;
    }
    
    /**
     * To set border color
     * @param color Color Color
     * @since JDK1.4.1
     */
    public void setBorderColor(Color color) {
        BORDER_COLOR = color;
    }
    
    /**
     * To set default border color
     * @return
     */
    public Color getBorderColor() {
    	return BORDER_COLOR;
    }
    
    /**
     * To set label background color
     * @param color Color Color객체
     * @since JDK1.4.1
     */
    public void setLabelBgColor(Color color) {
        LABEL_BG_COLOR = color;
    }
    
    /**
     * To get graph label backgraound color
     * @return
     */
    public Color getLabelBgColor() {
    	return LABEL_BG_COLOR;
    }
    
    /**
     * To set title font color
     * @param color Color Color
     * @since JDK1.4.1
     */
    public void setTitleFontColor(Color color) {
        TITLE_FONT_COLOR = color;
    }
    
    /**
     * To get title font color
     * @return
     */
    public Color getTitleFontColor() {
    	return TITLE_FONT_COLOR;
    }
    
    /**
     * To set indexes font color
     * @param color Color Color
     * @since JDK1.4.1
     */
    public void setIndexFontColor(Color color) {
        INDEX_FONT_COLOR = color;
    }
    
    /**
     * To get indexes font color
     * @return
     */
    public Color getIndexFontColor() {
    	return INDEX_FONT_COLOR;
    }
    
    /**
     * To set graph x, y axis color
     * @param color Color Color
     * @since JDK1.4.1
     */
    public void setGraphXYColor(Color color) {
        GRAPH_XY_COLOR = color;
    }
    
    /**
     * To get graph XY axis color
     * @return
     */
    public Color getGraphXYColor() {
    	return GRAPH_XY_COLOR;
    }
    
    /**
     * To set grid x axis color
     * @param color Color Color
     * @since JDK1.4.1
     */
    public void setGridXColor(Color color) {        
    	GRID_X_COLOR = color;
    }
    
    /**
     * To get grid X axis color
     * @return
     */
    public Color getGirdXColor() {
    	return GRID_X_COLOR;
    }
    
    /**
     * To set grid y color
     * @param color Color Color
     * @since JDK1.4.1
     */
    public void setGridYColor(Color color) {
        GRID_Y_COLOR = color;
    }
    
    /**
     * To get grid Y axis color
     * @return
     */
    public Color getGridYColor() {
    	return GRID_Y_COLOR;
    }
    
    /**
     * To set shadow color
     * @param color Color Color
     * @since JDK1.4.1
     */
    public void setShadowColor(Color color) {
        SHADOW_COLOR = color;
    }
    
    /**
     * To get shadow color
     * @return
     */
    public Color getShadowColor() {
    	return SHADOW_COLOR;
    }
    
    /**
     * To set default color
     * @param color Color
     * @see java.awt.Color
     * @since JDK1.4.1
     */
    public void setDefaultColor(Color color) {
        DEFAULT_COLOR = color;
    }
    
    /**
     * To get default color
     * @return
     */
    public Color getDefaultColor() {
    	return DEFAULT_COLOR;
    }
    
    /**
     * Set peek point color
     * @param color
     */
    public void setPeekColor(Color color) {
    	PEEK_COLOR = color;
    }
    
    /**
     * To get peek point color
     * @return
     */
    public Color getPeekColor() {
    	return PEEK_COLOR;
    }
    
    /**
     * To get graph 2d object
     * @return Graphics2D
     * @since JDK1.4.1
     */
    public Graphics2D getGraphics2D() {
        return super.GRAPHICS2D;
    }
    
    /**
     * To draw background
     * @param bgColor
     * @param graphics Graphics2D
     * @since JDK1.4.1
     */
    protected void drawBg(Color bgColor, Graphics2D graphics) {
        color(bgColor, graphics);
        graphics.fill(new Rectangle2D.Double(0, 0, IMG_WIDTH, IMG_HEIGHT));
    }
    
    /**
     * To draw background border
     * @param size float
     * @param borderColor Color
     * @param graphics Graphics2D
     * @since JDK1.4.1
     */
    protected void drawBgBorder(float size, Color borderColor, Graphics2D graphics) {
        color(borderColor, graphics);
        graphics.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics.draw(new Rectangle2D.Double(size/2, size/2, IMG_WIDTH-size, IMG_HEIGHT-size));
    }
    
    /**
     * To draw graph background
     * @param bgColor Color
     * @param graphics Graphics2D
     * @since JDK1.4.1
     */
    protected void drawGraphBg(Color bgColor, Graphics2D graphics) {
        color(bgColor, graphics);
        graphics.fill(new Rectangle2D.Double(GRAPH_X, GRAPH_Y-GRAPH_HEIGHT, GRAPH_WIDTH, GRAPH_HEIGHT));
    }
    
    /**
     * To draw graph border
     * @param size float
     * @param borderColor Color
     * @param rWidth float
     * @param rHeight float
     * @param graphics Graphics2D
     * @since JDK1.4.1
     */
    protected void drawGraphBorder(float size, Color borderColor, float rWidth, float rHeight, Graphics2D graphics) {
        color(borderColor, graphics);
        graphics.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics.draw(new RoundRectangle2D.Double(GRAPH_X, GRAPH_Y-GRAPH_HEIGHT, GRAPH_WIDTH, GRAPH_HEIGHT, rWidth, rHeight));
    }
    
    /**
     * To draw x, y axis
     * @param size float 
     * @param color Color
     * @param graphics Graphics2D
     * @since JDK1.4.1
     */
    protected void drawXY(float size, Color color, Graphics2D graphics) {
        color(color, graphics);
        graphics.setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics.draw(new Line2D.Double(GRAPH_X,GRAPH_Y,GRAPH_X,GRAPH_Y-GRAPH_HEIGHT));
        graphics.draw(new Line2D.Double(GRAPH_X,GRAPH_Y,GRAPH_X+GRAPH_WIDTH,GRAPH_Y));
    }
    
    /**
     * To draw title
     * @param title String
     * @param fontName String 
     * @param fontSize int
     * @param fontColor Color
     * @param graphics Graphics2D
     * @since JDK1.4.1
     */
    protected void drawTitle(String title, String fontName, int fontSize, Color fontColor, Graphics2D graphics) {
        FontMetrics fm = setFont(fontName, Font.BOLD, fontSize, graphics);
        float ascent = fm.getAscent();
        color(fontColor, graphics);
        if(this.IS_SHOW_TITLE_SHADOW) {
        	color(fontColor, 50, graphics);
        	graphics.drawString(title, GRAPH_X+17, GRAPH_Y-GRAPH_HEIGHT+ascent+2);
        }
        setComposite(1.0f, graphics);
        graphics.drawString(title, GRAPH_X+15, GRAPH_Y-GRAPH_HEIGHT+ascent);
    }
    
    /**
     * To set color to graphics 2d object
     * @param color Color
     * @param graphics Graphics2D
     * @since JDK1.4.1
     */
    protected void color(Color color, Graphics2D graphics) {
        graphics.setColor(color);
    }
    
    /** 
     * To set color to graphics object with density
     * @param color
     * @param density
     * @param graphics
     */
    protected void color(Color color, int density, Graphics2D graphics) {
    	int r = color.getRed()+density;
    	int g = color.getGreen()+density;
    	int b = color.getBlue()+density;
    	r = r > 255 ? 255 : r < 0 ? 0 : r;
    	g = g > 255 ? 255 : g < 0 ? 0 : g;
    	b = b > 255 ? 255 : b < 0 ? 0 : b;
    	color = new Color(r, g, b);
    	graphics.setColor(color);
    }
    
    /**
     * Get contrast color
     * @param color
     * @return
     */
    protected Color getContrastColor(Color color) {
        int r = (int)(255 - color.getRed());
        int g = (int)(255 - color.getGreen());
        int b = (int)(255 - color.getBlue());
        r = r > 255 ? 255 : r;
        g = g > 255 ? 255 : g;
        b = b > 255 ? 255 : b;
        return new Color(r, g, b);
    }
    
    /**
     * Get polygon object by shapes point list
     * @param shapes
     * @return
     */
    public Polygon getPolygon(List<Point> shapes, boolean isReverse) {
    	if(isReverse) {
    		Collections.reverse(shapes);
    	}
    	if(shapes != null) {
	    	int[] xa = shapes.stream().mapToInt(p -> (int)p.getX()).toArray();
	    	int[] ya = shapes.stream().mapToInt(p -> (int)p.getY()).toArray();
	    	return new Polygon(xa, ya, shapes.size());
    	}
    	return null;
    }
    
    /**
     * Get polygon been scaled
     * @param shapes
     * @param scale
     * @return
     */
    public Polygon getScalePolygon(List<Point> shapes, float scale) {
    	Polygon polygon = getPolygon(shapes, false);
        double centerX = polygon.getBounds().getCenterX();
        double centerY = polygon.getBounds().getCenterY();
        for(int i=0; i<shapes.size(); i++) {
        	Point p = shapes.get(i);
        	int x, y;
	        	x = (int)Math.round(p.getX() > centerX ? p.getX() + scale : p.getX() -scale);
	        	y = (int)Math.round(p.getY() > centerY ? p.getY() + scale : p.getY() -scale);
        	shapes.set(i, new Point(x, y));
        }
        return getPolygon(shapes, false);
    }
    
    /**
     * To set font
     * @param fontName String
     * @param fontStyle int
     * @param fontSize int
     * @param graphics Graphics2D
     * @return FontMetrics
     * @since JDK1.4.1
     */
    protected FontMetrics setFont(String fontName, int fontStyle, int fontSize, Graphics2D graphics) {
        Font f = new Font(fontName, fontStyle, fontSize);
        FontMetrics fm = graphics.getFontMetrics(f);
        graphics.setFont(f);
        return fm;
    }
    
    /**
     * To convert double value to integer
     * @param doubleValue double
     * @return int int
     * @since JDK1.4.1
     */
    protected int intValue(double doubleValue) {
        int iValue = (int)(doubleValue+0.5);
        return iValue;
    }
    
    /**
     * To set composite transparency to graphics 2d object
     * @param alpha float
     * @param graphics Graphics2D
     * @since JDK1.4.1
     */
    protected void setComposite(float alpha, Graphics2D graphics) {
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }
    
    /**
     * To get whether color is dark
     * @param color
     * @return
     */
    protected boolean isDarkColor(Color color) {
    	if(color.getRed() < 90 && color.getGreen() < 90 && color.getBlue() < 90) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Draw peek point shape on element value
     * @param peekStyle
     * @param peekPoint
     * @param thickness
     * @param color
     * @param radius
     * @param graphics
     */
    protected void drawPeek(PEEK_STYLE peekStyle, Point peekPoint, float thickness, double radius, Color color, Graphics2D graphics) {
    	color(color, 20, graphics);
    	//setComposite(0.5f, graphics);
    	graphics.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    	if(peekStyle == PEEK_STYLE.CIRCLE) {
    		graphics.fillOval((int)(peekPoint.x-radius), (int)(peekPoint.y-radius), (int)(radius*2), (int)(radius*2));
    		color(color, -50, graphics);
    		setComposite(1.0f, graphics);
    		graphics.drawOval((int)(peekPoint.x-radius), (int)(peekPoint.y-radius), (int)(radius*2), (int)(radius*2));
    	} else if(peekStyle == PEEK_STYLE.RECTANGLE) {
    		graphics.drawRect((int)(peekPoint.x-radius), (int)(peekPoint.y-radius), (int)(radius*2), (int)(radius*2));
    	} else {
    		
    	}
    	color(color, graphics);
    	setComposite(GRAPH_ALPHA, graphics);
    }
    
    /**
     * Draw pop up
     * @param popPoint
     * @param bgColor
     * @param ge
     * @param graphics
     */
    protected void drawPopup(Point popPoint, Color bgColor, GraphElement ge, Graphics2D graphics) {
        String longStr = "";
        int nmSize = ge.getElementName().length();
        int valSize = (ge.getSelectedValue()+"").length();
        longStr = nmSize > valSize ? ge.getElementName() : ge.getSelectedValue()+"";
        double scale = this.GRAPH_WIDTH * (SCALED_WIDTH) / 400d;
        FontMetrics fm = setFont(FONT_NAME, Font.BOLD, (int)scale, graphics);
        double ascent = fm.getAscent();
        double width = fm.stringWidth(longStr) + ascent * 3 ;
        double height = fm.getHeight() * 2 + ascent / 2 ;
        
        double x = popPoint.getX() - width;
        x = x < GRAPH_X ? x + width : x;
        double y = popPoint.getY() - height;

        if(IS_SHOW_POPUP_BACKGROUND) {
        	if(isDarkColor(GRAPH_BG_COLOR)) {
        		color(bgColor, 160, graphics);
    	        setComposite(0.8f, super.GRAPHICS2D);
        	} else {
        		color(bgColor, graphics);
    	        setComposite(POPUP_BG_ALPHA, super.GRAPHICS2D);
        	}
	        if(POPUP == POPUP_STYLE.RECTANGLE) {
	        	graphics.fill(new Rectangle2D.Double(x, y, width, height));
	        } else if(POPUP == POPUP_STYLE.ROUND) {
	        	graphics.fill(new RoundRectangle2D.Double(x, y, width, height, 20d, 20d));
	        }
	        graphics.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	        color(ge.getElementColor(), 30, super.GRAPHICS2D);
	        setComposite(1.0f, graphics);
	        if(POPUP == POPUP_STYLE.RECTANGLE) {
	        	graphics.draw(new Rectangle2D.Double(x, y, width, height));
	        } else if(POPUP == POPUP_STYLE.ROUND) {
	        	graphics.draw(new RoundRectangle2D.Double(x, y, width, height, 20d, 20d));
	        }
        }
        color(ge.getElementColor(), graphics);
        setComposite(1.0f, graphics);        
        graphics.setFont(fm.getFont());
        graphics.drawString(ge.getElementName(), (int)(x + ascent), (int)Math.round(y + ascent * 1.5));
        double value = ge.getSelectedValue();
        double scale1 = Math.pow(10, ROUND_PLACE);
        String valueStr = (Math.round(value * scale1) / scale1)+this.INDEX_Y_UNIT;
        graphics.drawString(valueStr, (int)(x + ascent), (int)Math.round(y + ascent * 2.5));
    }
    
    /**
     * To draw label 
     * @param fontName
     * @param fontSize
     * @param fontStyle
     * @param bgColor
     * @param elements
     * @param graphics
     */
    protected void drawLabel(String fontName, int fontSize, int fontStyle, Color bgColor, List<GraphElement> elements, Graphics2D graphics) {
        String longStr="", tmp="";
        int i=0;
        for (i=0; i<elements.size(); i++) {
            tmp = elements.get(i).getLabel();
            if (tmp == null) {
            	break;
            }
            if (tmp.length() > longStr.length()) {
            	longStr = tmp;
            }
        }
        double scale = this.GRAPH_WIDTH * (SCALED_WIDTH) / 480;
        FontMetrics fm = setFont(fontName, fontStyle, (int)scale, graphics);
        int spacing = 40;
        double labelWidth = fm.stringWidth(longStr) + spacing;
        double labelHeight = fm.getHeight() * i;
        LABEL_X = (int)(LABEL_X-(labelWidth + 10));
        float ascent = fm.getAscent();
        
        if(IS_SHOW_LABEL_BACKGROUND) {
        	if(isDarkColor(GRAPH_BG_COLOR)) {
        		color(bgColor, 100, graphics);
    	        setComposite(0.9f, super.GRAPHICS2D);
        	} else {
        		color(bgColor, graphics);
    	        setComposite(LABEL_BG_ALPHA, super.GRAPHICS2D);
        	}
	        graphics.fill(new RoundRectangle2D.Double(LABEL_X, LABEL_Y, labelWidth, labelHeight, 10d, 10d));
	        //setComposite(1.0f, graphics);
	        color(GRAPH_BORDER_COLOR, graphics);
	        graphics.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	        graphics.draw(new RoundRectangle2D.Double(LABEL_X, LABEL_Y, labelWidth, labelHeight, 10d, 10d));
        }
        int unit = (int)(fm.getHeight());
        for (int a=0; a<elements.size(); a++) {        
        	GraphElement  ge = elements.get(a);
            if(ge == null) {
            	 break;
            }
            List<Point> labelShapes = new ArrayList<Point>();
            labelShapes.add(new Point(LABEL_X, LABEL_Y + unit * a));
            labelShapes.add(new Point(LABEL_X+(int)labelWidth, LABEL_Y + unit * a));
            labelShapes.add(new Point(LABEL_X+(int)labelWidth, LABEL_Y+(int)(unit * (a+1))));
            labelShapes.add(new Point(LABEL_X, LABEL_Y+(int)(unit * (a+1))));
            ge.setLabelShapes(labelShapes);
            
            Polygon ploygon = getScalePolygon(labelShapes, - 1f);
            if(GRAPH_ELEMENTS.getSelectedElement() != null && GRAPH_ELEMENTS.getSelectedElement().getElementName().equals(ge.getElementName())) {
	            color(bgColor, graphics);
	            setComposite(0.5f, graphics);
	            graphics.fill(ploygon);
            }
            color(ge.getLabelColor(), graphics);
            setComposite(1.0f, graphics);
            String label = ge.getLabel() == null ? ge.getElementName() : ge.getLabel();
            graphics.drawString(label, LABEL_X + spacing / 2, (int)Math.round(LABEL_Y + (labelHeight / i) * a + ascent));
        }
    }
    
    /**
     * Draw indexes of x axis
     * @param graphType int
     * @param fontName String
     * @param fontSize int
     * @param fontColor Color
     * @param xIndex String[] 
     * @param graphics Graphics2D
     * @since JDK1.4.1
     */
    protected void drawIndexX(GRAPH graphType, String fontName, int fontSize, Color fontColor, List<Object> xIndex, Graphics2D graphics) {
        color(fontColor, graphics);
        for (int i=0; i<xIndex.size(); i++)
        {
            FontMetrics fm = setFont(fontName, Font.BOLD, fontSize, graphics);
            Object obj = xIndex.get(i);
            if(obj != null) {
            	String str = obj.toString();
	            float indent = fm.stringWidth(str)/2;
	            float ascent = fm.getAscent();
	            float x = 0;
	            if(graphType == GRAPH.LINE) {
	                x = i * (GRAPH_WIDTH / xIndex.size()) + GRAPH_X;
	            } else {
	                x = i * (GRAPH_WIDTH / xIndex.size()) + GRAPH_X;
	            }
	            float y = GRAPH_Y+1;
	            graphics.drawString(str, x-indent, y+ascent+3);
            }
        }
    }
    
    /**
     * Draw indexes of y axis
     * @param fontName
     * @param fontSize
     * @param fontColor
     * @param yIndex float[]
     * @param limit float
     * @param maxValue float
     * @param graphics Graphics2D
     * @since JDK1.4.1
     */
    protected void drawIndexY(String fontName, int fontSize, Color fontColor, List<Double> yIndex, double limit, double maxValue, Graphics2D graphics) {
        color(fontColor, graphics);
        for (int i=0; i<yIndex.size(); i++)
        {
            FontMetrics fm = setFont(fontName, Font.BOLD, fontSize, graphics);
            String str =yIndex.get(i)+"";
            if(str.equals("0.0"))
                continue;
            int colIdx;
            if((colIdx = str.lastIndexOf(".")) != -1)
            {
            	String suffix = str.substring(colIdx+1);
            	if(suffix.equals("0"))
            		str = str.substring(0, colIdx); 
            }
            str += this.INDEX_Y_UNIT;
            Object obj = yIndex.get(i);
            double y = 0;
            if(obj instanceof Number) {
            	y = ((Double)obj).doubleValue();
            	double scale = Math.pow(10, ROUND_PLACE);
            	str = Math.round(y * scale) / scale + this.INDEX_Y_UNIT;
            } else if(obj instanceof String) {
            	y = Double.parseDouble(obj+"");
            } else {
            	y = 0;
            }
        	y = (limit < maxValue) ? (GRAPH_Y - y * GRAPH_HEIGHT / maxValue) : (GRAPH_Y - y * GRAPH_HEIGHT / limit);
            float indent = fm.stringWidth(str) + 5;
            float ascent = fm.getAscent() / 2;
            if (y > GRAPH_Y-GRAPH_HEIGHT)
            {
                double x = ((GRAPH_X - indent) < 0) ? GRAPH_X + 5:GRAPH_X - indent;
                graphics.drawString(str, (float)x, (float)y+2);
            }        
        }
    }
    
    /**
     * Draw grid x 
     * @param xIndex String[]
     * @param color Color
     * @param style int
     * @param graphics Graphics2D
     * @since JDK1.4.1
     */
    protected void drawGridX(List<Object> xIndex, Color color, GRID style, Graphics2D graphics) {
        color(color, graphics);
        double unitWidth = GRAPH_WIDTH / xIndex.size();
        for (int i=0; i<xIndex.size(); i++)
        {
            double x = i*unitWidth + GRAPH_X;
            Object xv = xIndex.get(i);
            if (xv != null && !xv.equals(""))
            {
                drawGrid(GRID_VISIBLE.X, style, x, GRAPH_Y, x, (GRAPH_Y+1)-GRAPH_HEIGHT, graphics);
            }
        }
    }
    
    /**
     * Draw grid y
     * @param yIndex float[]
     * @param color Color 
     * @param style int
     * @param limit float
     * @param maxValue
     * @param graphics 
     * @since JDK1.4.1
     */
    protected void drawGridY(List<Double> yIndex, Color color, GRID style, double limit, double maxValue, Graphics2D graphics) {
        color(color, graphics);
        for (int i=0; i<yIndex.size(); i++) {
        	Object obj = yIndex.get(i);
        	double y = 0;
        	if(obj instanceof Number) {
        		y = ((Double)obj).doubleValue();
        	} else if(obj instanceof String) {
        		y = Double.parseDouble(obj+"");
        	} else {
        		y = 0;
        	}
            y = (limit < maxValue) ? (GRAPH_Y - y * GRAPH_HEIGHT / maxValue) : (GRAPH_Y - y * GRAPH_HEIGHT / limit);
            if (y != 0f && y > GRAPH_Y-GRAPH_HEIGHT) {
                drawGrid(GRID_VISIBLE.Y, style, GRAPH_X+1, intValue(y), GRAPH_X+GRAPH_WIDTH, intValue(y), graphics);
        	}
        }
    }
    
    /**
     * Draw grid
     * @param xy int
     * @param gridStyle
     * @param x1 float
     * @param y1 float
     * @param x2 float
     * @param y2 float
     * @param graphics
     * @since JDK1.4.1
     */
    private void drawGrid(GRID_VISIBLE xy, GRID gridStyle, double x1, double y1, double x2, double y2, Graphics2D graphics) {
        if (gridStyle == GRID.LINE)
        {
            graphics.draw(new Line2D.Double(x1, y1, x2, y2));
        } 
        else if(gridStyle == GRID.DOT)
        {
            if (xy == GRID_VISIBLE.X)
            {
                for (int a=0; y1-a*5 > y2; a++)
                {
                    graphics.draw(new Line2D.Double(x1, y1-a*5, x1, y1-a*5-3));
                }
            }
            else if (xy == GRID_VISIBLE.Y)
            {
                for (int b=0; x1+b*5 < x2; b++)
                {
                    graphics.draw(new Line2D.Double(x1+b*5, y1, x1+b*5+3, y1));
                }
            }
        }
    }
    
    /**
     * Draw water mark with 9ins
     * @param graphics
     * @since JDK1.4.1
     */
    protected void drawRight(Graphics2D graphics) {
        String logo = "ChaosToCosmos®";
        FontMetrics fm = setFont(graphics.getFont().getName(), Font.BOLD, 9, graphics);
        float ascent = fm.getAscent();
        float width = fm.stringWidth(logo);
        setComposite(1.0f, graphics);
        graphics.drawString(logo, IMG_WIDTH-width-5, IMG_HEIGHT-ascent);
    }
    
    /**
     * Resize and drawing current Graphics2D object
     */
    public void resizeImage(int width, int height) {
    	resizeImage(this.GRAPHICS2D, width, height);
    }
    
    /**
     * This abstract method is part of resizing graph image. Threrefore it must be implemented on inherited class.
     */
    public void resizeImage(Graphics2D g2d, int width, int height) {
    	if(width < 100 || height < 100) {
    		return;
    	}
    	this.GRAPHICS2D = g2d;
        initGraph(g2d, width, height); 
        repaint();  
    }
    
    @Override
    public BufferedImage getBufferedImage() {
    	return getBufferedImage(this.IMG_WIDTH, this.IMG_HEIGHT);
    }
    
    /**
     * Get buffered image painted graph
     * @return
     */
    public BufferedImage getBufferedImage(int width, int height) {
    	BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //BufferedImage 
    	resizeImage((Graphics2D)buffImg.getGraphics(), width, height);
    	return buffImg;
    }
}
