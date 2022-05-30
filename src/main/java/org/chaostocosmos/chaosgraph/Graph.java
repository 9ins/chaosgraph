package org.chaostocosmos.chaosgraph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;   

/**
 * 
 * Graph
 * 
 * This class contribute to describe attributes of graph. tst
 *
 * @author Kooin-Shin
 * 2020. 9. 23.
 */
public abstract class Graph implements IGraph, GraphConstants {
	protected int ORIGIN_WIDTH, ORIGIN_HEIGHT;
	protected double SCALED_WIDTH = 8d;
	protected double SCALED_HEIGHT= 6d;
    protected int INDENT_LEFT = 50;                         //Left indent
    protected int INDENT_RIGHT = 50;                        //Right indent
    protected int INDENT_TOP = 30;                          //Top indent
    protected int INDENT_BOTTOM = 30;                       //Bottom indent
    protected int GRAPH_X, GRAPH_Y;                      	//Graph X/Y position
    protected int LABEL_X, LABEL_Y;							//Label X/Y position
    protected int GRAPH_WIDTH, GRAPH_HEIGHT;				//Graph width, height
    protected int IMG_WIDTH = 600, IMG_HEIGHT = 300;		//Image or canvas width, height
    protected boolean IS_SHOW_GRID_Y = true;				//Y axis grid whether to be shown
    protected boolean IS_SHOW_GRID_X = true;				//X axis grid whether to be shown
    protected boolean IS_SHOW_INDEX_Y = true;				//Y indent whether to be shown
    protected boolean IS_SHOW_INDEX_X = true;				//X indent whether to be shown
    protected boolean IS_SHOW_GRAPH_XY = true;				//Is show xy axis
    protected boolean IS_SHOW_TITLE = true;					//Is show title
    protected boolean IS_SHOW_TITLE_SHADOW = false;			//Is show title shadow
    protected boolean IS_SHOW_BG = true;					//Is show background
    protected boolean IS_SHOW_LABEL = true;					//Is show label area
    protected boolean IS_SHOW_LABEL_BACKGROUND = true;		//is show label background
    protected boolean IS_SHOW_POPUP = true;					//Is show popup window
    protected boolean IS_SHOW_POPUP_BACKGROUND = true;		//Is show popup background
    protected boolean IS_SHOW_SHADOW = true;				//Is show shadow
    protected boolean IS_SHOW_IMG_BORDER = true;			//is show image border
    protected boolean IS_SHOW_GRAPH_BORDER = true;			//is show graph border
    protected boolean IS_SHOW_BORDER = true;                //is show background border
    protected boolean IS_IMG_FIXED = true;                 //is to fix the image
    protected boolean IS_SELECTION_ENABLE = true;			//is able to element selection
    protected boolean IS_SHOW_PEEK = false;					//is show peek point
    protected boolean IS_SHOW_ELEMENT_NAME = false;			//is show element name

    protected float IMG_BG_ALPHA = 1.0f;					//Transparency of background
    protected float GRAPH_BG_ALPHA = 1.0f;					//Transparency of graph background
    protected float TITLE_FONT_ALPHA = 0.7f;                //Transparency title font
    protected float GRAPH_XY_ALPHA = 0.8f;                  //Transparency xy axis
    protected float INDEX_FONT_ALPHA = 0.7f;				//Transparency of index font
    protected float GRID_ALPHA = 0.5f;                      //Transparency of grid
    protected float SHADOW_ALPHA = 0.2f;					//Transparency of shadow
    protected float GRAPH_ALPHA = 0.9f;						//Transparency of graph
    protected float LABEL_BG_ALPHA = 0.5f;					//Transparency of background of label area
    protected float POPUP_BG_ALPHA = 0.5f;					//Transparency of popup windows background
    protected float TMP_ALPHA;
    protected int TITLE_FONT_SIZE = 25;						//Size of title font
    protected int LABEL_FONT_SIZE = 10;						//Size of label font
    protected int INDEX_FONT_SIZE = 10;						//Size of index font
    protected int SHADOW_DIST = 5;							//Distance of shadow on graph
    protected int SHADOW_ANGLE = 300;						//Angle of shadow on graph
    protected float IMG_BORDER_SIZE = 3f;					//Image border size
    protected float GRAPH_BORDER_SIZE = 5f;				    //Graph border size
    protected float BORDER_SIZE = 2.0f;                       //Border size
    protected float GRAPH_XY_SIZE = 2f;					    //Stroke size of xy axis
    protected float GRID_SIZE = 0.1f;						//Stroke size of grid
    protected int ROUND_PLACE = 2;							//Round digits;

    protected POPUP_STYLE POPUP = POPUP_STYLE.ROUND; 		//popup style
    protected GRID GRID_STYLE = GRID.LINE;					//Grid line style(1.line, 2. dot)
    protected SELECTION_BORDER SEL_BORDER = SELECTION_BORDER.LINE;		//Selection border
    protected String INDEX_Y_UNIT = "";						//Y index unit string
    protected double VALUE_DIVISION_RATIO = 1.0d;			//Y index values division ratio(1.0 is same ratio)
    protected String FONT_NAME;								//Font name
    protected String TITLE;									//Graph title
    protected double LIMIT;                                 //Graph scale(Higher value to get small graph image)
    protected double WHEEL_UNIT_SCALE = 0.02;    
    protected int SELECTED_COLOR_DENSITY = -20;		
    protected GraphElements GRAPH_ELEMENTS = null;          //Graph elements object    
    protected Graphics2D GRAPHICS2D = null;                 //Graphics2D object to draw
    protected List<GraphSelectionListener> listenerList = new ArrayList<GraphSelectionListener>(); //Graph selection listeners
    
    protected INTERPOLATE interpolateType;
    protected int interpolateScale;
    
    /**
     * Constructor
     * @param graphElements
     * @param title
     * @param width
     * @param height
     */
    protected Graph(GraphElements graphElements, String title, int width, int height) {
    	this.TITLE = title;
    	if(this.ORIGIN_WIDTH == 0 && this.ORIGIN_HEIGHT == 0) {
    		this.ORIGIN_WIDTH = width;
    		this.ORIGIN_HEIGHT = height;
    	}
    	this.GRAPH_ELEMENTS = graphElements;
    	this.IMG_WIDTH = width;
    	this.IMG_HEIGHT = height;
        this.GRAPH_WIDTH = IMG_WIDTH-(INDENT_LEFT+INDENT_RIGHT);	//Width of graph in image
        this.GRAPH_HEIGHT = IMG_HEIGHT-(INDENT_TOP+INDENT_BOTTOM);  //Height of graph in image
    	this.GRAPH_ELEMENTS.setGraph(this);
    	this.interpolateScale = 5;
    	this.ROUND_PLACE = GraphConstants.ROUND_PLACE;
    }
    
    /**
     * Initialize 
     * @param width int
     * @param height int
     * @since JDK1.4.1
     */
    @Override
    public void initGraph(Graphics2D g2d, int width, int height) {
    	if(width <= 0 || height <= 0) {
    		return;
    	}
    	//System.out.println(width+"   "+height+"   "+this.IMG_WIDTH+"   "+this.IMG_HEIGHT);
    	//System.out.println(this.SCALED_WIDTH+"   "+this.SCALED_HEIGHT);
    	this.GRAPHICS2D = g2d;
        this.setImgSize(width, height);
        this.GRAPH_WIDTH = IMG_WIDTH-(INDENT_LEFT+INDENT_RIGHT);	//Width of graph in image
        this.GRAPH_HEIGHT = IMG_HEIGHT-(INDENT_TOP+INDENT_BOTTOM);  //Height of graph in image
        this.GRAPH_X = (IMG_WIDTH)-(INDENT_RIGHT+GRAPH_WIDTH);		//Graph x position
        this.GRAPH_Y = (IMG_HEIGHT)-INDENT_BOTTOM;                  //Graph y position
        this.LABEL_X = GRAPH_X+GRAPH_WIDTH;                         //Label x position
        this.LABEL_Y = GRAPH_Y-GRAPH_HEIGHT+10;                     //Label y position
        //Interplate y values of graph elements
        setElementsInterpolates(this.interpolateType, this.interpolateScale);
    }
    
    /**
     * Sweeping background
     */
    public void sweepBg(int width, int height) {
        this.GRAPH_WIDTH = IMG_WIDTH-(INDENT_LEFT+INDENT_RIGHT);	
        this.GRAPH_HEIGHT = IMG_HEIGHT-(INDENT_TOP+INDENT_BOTTOM);  
        this.GRAPH_X = (IMG_WIDTH)-(INDENT_RIGHT+GRAPH_WIDTH);		
        this.GRAPH_Y = (IMG_HEIGHT)-INDENT_BOTTOM;                  
        this.LABEL_X = GRAPH_X+GRAPH_WIDTH;                         
        this.LABEL_Y = GRAPH_Y-GRAPH_HEIGHT+10;                     
        this.GRAPHICS2D.setColor(Color.black);
        this.GRAPHICS2D.fill(new Rectangle2D.Double(0, 0, width, height));
    }
    
    /**
     * Get graph type text
     * @param graphType
     * @return
     */
    public static String getGraphStr(GRAPH graphType) {
        String type = null;
        if(graphType == GRAPH.LINE)
            type = "LINE GRAPH";
        else if(graphType == GRAPH.BAR)
            type = "BAR GRAPH";
        else if(graphType == GRAPH.CIRCLE)
            type = "CIRCLE GRAPH";
        else if(graphType == GRAPH.AREA)
            type = "AREA GRAPH";
        return type;
    }
    
    @Override
    public void repaint(Graphics2D g2d) {
    	//System.out.println(g2d);
    	if(this.GRAPHICS2D == null) {
    		this.GRAPHICS2D = g2d;
    	}
    	drawGraph(g2d);
    }
    
    /**
     * Set graph elements interpolation
     * @param interpolateType
     * @param interpolateScale
     */
    public void setElementsInterpolates(INTERPOLATE interpolateType, int interpolateScale) {
    	this.interpolateType = interpolateType;
    	this.interpolateScale = interpolateScale;
    	InterpolateTransform.populateInterpolateWithOneType(interpolateType, this.GRAPH_ELEMENTS, interpolateScale);
    }
    
    /**
     * Repaint to graphics object
     */
    public void repaint() {
    	if(this.GRAPHICS2D != null) {
    		this.repaint(this.GRAPHICS2D);
    	}
    }
    
    /**
     * Set interpolate type to be set to graph elements
     * @param interpolateType
     */
    public void setInterpolateType(INTERPOLATE interpolateType) {
    	this.interpolateType = interpolateType;
    }
    
    /**
     * Set interpolate points count
     * @param interpolateScale
     */
    public void setInterPolateScale(int interpolateScale) {
    	this.interpolateScale = interpolateScale;
    }
    
    /**
     * Get graphics 2d object
     * @return
     */
    public Graphics2D getGraphics2D() {
    	return this.GRAPHICS2D;
    }
    
    /**
     * Set graphics 2d object
     * @param g2d
     */
    public void setGraphics2D(Graphics2D g2d) {
    	this.GRAPHICS2D = g2d;
    }
    
    /**
     * Get graph type
     */
    public GRAPH getGraphType() {
    	return this.GRAPH_ELEMENTS.getGraphType();
    }
	   
    /**
     * Get graph x point
     * @return float
     * @since JDK1.4.1
     */
    public double getGraphX() {
        return this.GRAPH_X;
    }
    
    /**
     * Get graph y point
     * @return float
     * @since JDK1.4.1
     */
    public double getGraphY() {
        return this.GRAPH_Y;
    }
    
    /**
     * Get image width
     * @return
     */
    public int getImageWidth()  {
    	return this.IMG_WIDTH;
    }
    
    /**
     * Get image height
     * @return
     */
    public int getImageHeight() {    	
    	return this.IMG_HEIGHT;
    }
    
    /**
     * Get label area x point
     * @return float
     * @since JDK1.4.1
     */
    public double getLabelX() {
        return this.LABEL_X;
    }
    
    /**
     * Get label area y point
     * @return double 
     * @since JDK1.4.1
     */
    public double getLabelY() {
        return this.LABEL_Y;
    }
    
    /**
     * Get title string
     * @return
     */
    public String getTitle() {
    	return this.TITLE;
    }
    
    /**
     * Get whether showing title shadow
     * @return
     */
    public boolean getShowTitleShadow() {
    	return IS_SHOW_TITLE_SHADOW;
    }
    
    /**
     * Get graph width
     * @return
     */
    public int getGraphWidth() {
    	return this.GRAPH_WIDTH;
    }
    
    /**
     * Get graph height
     * @return
     */
    public int getGraphHeight() {
    	return this.GRAPH_HEIGHT;
    }
    
    /**
     * Get whether image is fixed size
     * @return
     */
    public boolean isImgFixed() {
    	return this.IS_IMG_FIXED;
    }
    
    /**
     * Get round place digits
     * @return
     */
    public int getRoundDigits() {
    	return this.ROUND_PLACE;
    }
    
    /**
     * Set round digits
     * @param digits
     */
    public void setRoundDigits(int digits) {
    	this.ROUND_PLACE = digits;
    }
    
    /**
     * To show grid y axis
     * @param is boolean true or false
     * @since JDK1.4.1
     */
    public void setShowGridY(boolean is) {
        IS_SHOW_GRID_Y = is;
    }
    
    /**
     * To show x axis
     * @param is boolean true or false
     * @since JDK1.4.1
     */
    public void setShowGridX(boolean is) {
        IS_SHOW_GRID_X = is;
    }
    
    /**
     * To show indentation of x
     * @param is boolean true or false
     * @since JDK1.4.1
     */
    public void setShowIndexX(boolean is) {
        IS_SHOW_INDEX_X= is;
    }
    
    /**
     * To show indentation of y
     * @param is boolean true or false
     * @since JDK1.4.1
     */
    public void setShowIndexY(boolean is) {
        IS_SHOW_INDEX_Y = is;
    }
    
    /**
     * To show indentation of x, y
     * @param is boolean true or false
     * @since JDK1.4.1
     */
    public void setShowGraphXY(boolean is) {
        IS_SHOW_GRAPH_XY = is;
    }
    
    /**
     * To show title text
     * @param is boolean true or false
     * @since JDK1.4.1
     */
    public void setShowTitle(boolean is) {
        IS_SHOW_TITLE = is;
    }
    
    /**
     * To show title shadow
     * @param is
     */
    public void setShowTitleShadow(boolean is) {
    	IS_SHOW_TITLE_SHADOW = is;
    }
    
    /**
     * To show background
     * @param is boolean true or false
     * @since JDK1.4.1
     */
    public void setShowBg(boolean is) {
        IS_SHOW_BG = is;
    }
    
    /**
     * To show label area
     * @param is boolean true or false
     * @since JDK1.4.1
     */
    public void setShowLabel(boolean is) {
        IS_SHOW_LABEL = is;
    }
    
    /**
     * set show label background
     * @param is
     */
    public void setShowLabelBackground(boolean is) {
    	IS_SHOW_LABEL_BACKGROUND = is;
    }
    
    /**
     * To show popup window
     * @param is
     */
    public void setShowPopup(boolean is) {
    	IS_SHOW_POPUP = is;
    }
    
    /**
     * To show popup background
     * @param is
     */
    public void setShowPopupBackgraound(boolean is) {
    	IS_SHOW_POPUP_BACKGROUND = is;
    }
    
    /**
     * To show shadow
     * @param is boolean true or false
     * @since JDK1.4.1
     */
    public void setShowShadow(boolean is) {
        IS_SHOW_SHADOW = is;
    }
    
    /**
     * To show image border
     * @param is boolean true or false
     * @since JDK1.4.1
     */
    public void setShowImgBorder(boolean is) {
        IS_SHOW_IMG_BORDER = is;
    }
    
    /**
     * To show graph border
     * @param is boolean true or false
     * @since JDK1.4.1
     */
    public void setShowGraphBorder(boolean is) {
        IS_SHOW_GRAPH_BORDER = is;
    }
    
    /**
     * To show border
     * @param is boolean true or false
     * @since JDK1.4.1
     */
    public void setShowBorder(boolean is) {
        IS_SHOW_BORDER = is;
    }
    
    /**
     * To make image be fixed
     * @param is boolean true or false
     * @since = JDK1.4.1
     */
    public void setImgFixed(boolean is) {
        IS_IMG_FIXED = is;
    }
    
    /**
     * To set graph element selection enable
     * @param is
     */
    public void setSelectionEnable(boolean is) {
    	IS_SELECTION_ENABLE = is;
    }
    
    /**
     * To get graph element selection enable
     * @return
     */
    public boolean getSelectionEnable() {
    	return IS_SELECTION_ENABLE;
    }
    
    /**
     * Whether showing peek point
     * @param is
     */
    public void setShowPeek(boolean is) {
    	IS_SHOW_PEEK = is;
    }
    
    /**
     * Get whether showing peek point
     * @return
     */
    public boolean getShowPeek() {
    	return IS_SHOW_PEEK;
    }
    
    /**
     * Set whether shwing element name on graph
     * @param is
     */
    public void setShowElementName(boolean is) {
    	this.IS_SHOW_ELEMENT_NAME = is;
    }
    
    /**
     * Get whether shwing element name on graph
     * @return
     */
    public boolean getShowElementName() {
    	return IS_SHOW_ELEMENT_NAME;
    }
    
    /**
     * To set image background alpha transparency
     * @param alpha float
     * @since JDK1.4.1
     */
    public void setImgBgAlpha(float alpha) {
        IMG_BG_ALPHA = alpha;
    }
    
    /**
     * To set graph background alpha transparency
     * @param alpha float
     * @since JDK1.4.1
     */
    public void setGraphBgAlpha(float alpha) {
        GRAPH_BG_ALPHA = alpha;
    }
    
    /**
     * To set title font alpha transparency
     * @param alpha float
     * @since JDK1.4.1
     */
    public void setTitleFontAlpha(float alpha) {
        TITLE_FONT_ALPHA = alpha;
    }
    
    /**
     * To set graph x, y alpha transparency
     * @param alpha float
     * @since JDK1.4.1
     */
    public void setGraphXYAlpha(float alpha) {
        GRAPH_XY_ALPHA = alpha;
    }
    
    /**
     * To set indexes font transparency
     * @param alpha float
     * @since JDK1.4.1
     */
    public void setIndexFontAlpha(float alpha) {
        INDEX_FONT_ALPHA = alpha;
    }
    
    /**
     * To set grid alpha transparency
     * @param alpha float
     * @since JDK1.4.1
     */
    public void setGridAlpha(float alpha) {
        GRID_ALPHA = alpha;
    }
    
    /**
     * To set shadow alpha transparency
     * @param alpha float
     * @since JDK1.4.1
     */
    public void setShadowAlpha(float alpha) {
        SHADOW_ALPHA = alpha;
    }
    
    /**
     * To set graph alpha transparency
     * @param alpha float
     * @since JDK1.4.1
     */
    public void setGraphAlpha(float alpha) {
        GRAPH_ALPHA = alpha;
    }
    
    /**
     * To set label background alpha transparency
     * @param alpha float
     * @since JDK1.4.1
     */
    public void setLabelBgAlpha(float alpha) {
        LABEL_BG_ALPHA = alpha;
    }
    
    /**
     * To set title font size
     * @param size int
     * @since JDK1.4.1
     */
    public void setTitleFontSize(int size) {
        TITLE_FONT_SIZE = size;
    }
    
    /**
     * To set label font size
     * @param size int
     * @since JDK1.4.1
     */
    public void setLabelFontSize(int size) {
        LABEL_FONT_SIZE = size;
    }
    
    /**
     * To set indexes font size
     * @param size int 
     * @since JDK1.4.1
     */
    public void setIndexFontSize(int size) {
        INDEX_FONT_SIZE = size;
    }
    
    /**
     * To set shadow distance
     * @param size int
     * @since JDK1.4.1
     */
    public void setShadowDist(int size) {
        SHADOW_DIST = size;
    }
    
    /**
     * To set shadow angle
     * @param size int
     * @since JDK1.4.1
     */
    public void setShadowAngle(int size) {
        SHADOW_ANGLE = size;
    }
    
    /**
     * To set image border size
     * @param size int
     * @since JDK1.4.1
     */
    public void setImgBorderSize(float size) {
        IMG_BORDER_SIZE = size;
    }
    
    /**
     * To set graph border size
     * @param size float
     * @since JDK1.4.1
     */
    public void setGraphBorderSize(float size) {
        GRAPH_BORDER_SIZE = size;
    }
    
    /**
     * To set border size
     * @param size float
     * @since JDK1.4.1
     */
    public void setBorderSize(float size) {
        BORDER_SIZE = size;
    }
    
    /**
     * To set graph x, y size 
     * @param size float
     * @since JDK1.4.1
     */
    public void setGraphXYSize(float size) {
        GRAPH_XY_SIZE = size;
    }
    
    /**
     * To set grid size
     * @param size float
     * @since JDK1.4.1
     */
    public void setGridSize(float size) {
        GRID_SIZE = size;
    }
    
    /**
     * To set element color density when element be selected
     * @param density
     */
    public void setSelectionColorDensity(int density) {
    	SELECTED_COLOR_DENSITY = density;
    }
    
    /**
     * To get selection color density
     * @return
     */
    public int getSelectionColorDensity() {
    	return SELECTED_COLOR_DENSITY;
    }
    
    /**
     * To set grid stype(line, dot)
     * @param style int
     * @since JDK1.4.1
     */
    public void setGridStyle(GRID style) {
        GRID_STYLE = style;
    }
    
    /**
     * To set font
     * @param fontName String
     * @since JDK1.4.1
     */
    public void setFont(String fontName) {
        FONT_NAME = fontName;
    }
    
    /**
     * To set title
     * @param title String
     * @since JDK1.4.1
     */
    public void setTitle(String title) {
        TITLE = title;
    }
    
    /**
     * To get limit value
     * @return
     */
    public double getLimit() {
    	return this.LIMIT;
    }
    
    /**
     * To set limit of value
     * @param valueLimit
     */
    public void setLimit(double valueLimit) {
        LIMIT = valueLimit;
    }
    
    /**
     * To get top indent size
     * @return int
     * @since JDK1.4.1
     */
    public int getIndentTop() {
        return INDENT_TOP;
    }
    
    /**
     * To get left indent size
     * @return int
     * @since JDK1.4.1
     */
    public int getIndentLeft() {
        return INDENT_LEFT;
    }
    
    /**
     * To get bottom indent
     * @return int
     * @since JDK1.4.1
     */
    public int getIndentBottom() {
        return INDENT_BOTTOM;
    }
    
    /**
     * To get right indent
     * @return int
     */
    public int getIndentRight() {
        return INDENT_RIGHT;
    }
    
    /**
     * Set Y index value division ratio
     * @param ratio
     */
    public void setValueDivisionRatio(double ratio) {
    	VALUE_DIVISION_RATIO = ratio;
    }
    
    /**
     * Get Y index value division ratio
     * @return
     */
    public double getValueDivisionRatio() {
    	return this.VALUE_DIVISION_RATIO;
    }
    
    /**
     * To set unit
     * @param unit
     */
    public void setUnit(String unit) {
    	this.INDEX_Y_UNIT = unit;
    }
    
    /**
     * To set top, left, bottom, right indent
     * @param top int
     * @param left int
     * @param bottom int
     * @param right int
     * @since JDK1.4.1
     */
    public void setIndent(int top, int left, int bottom, int right) {
        this.INDENT_TOP = top;
        this.INDENT_LEFT = left;
        this.INDENT_BOTTOM = bottom;
        this.INDENT_RIGHT = right;
        this.GRAPH_WIDTH = IMG_WIDTH-(INDENT_LEFT+INDENT_RIGHT);
        this.GRAPH_HEIGHT = IMG_HEIGHT-(INDENT_TOP+INDENT_BOTTOM);
        this.GRAPH_X = IMG_WIDTH-(INDENT_RIGHT+GRAPH_WIDTH);
        this.GRAPH_Y = IMG_HEIGHT-INDENT_BOTTOM;
        this.LABEL_X = GRAPH_X+GRAPH_WIDTH;
        this.LABEL_Y = GRAPH_Y-GRAPH_HEIGHT+10;
    }
    
    /**
     * To set top indent
     * @param top int
     */
    public void setTopIndent(int top) {
        setIndent(top, INDENT_LEFT, INDENT_BOTTOM, INDENT_RIGHT);
    }
    
    /**
     * To set left indent
     * @param left int
     */
    public void setLeftIndent(int left) {
        setIndent(INDENT_TOP, left, INDENT_BOTTOM, INDENT_RIGHT);
    }
    
    /**
     * To set bottom indent
     * @param bottom int
     */
    public void setBottomIndent(int bottom) {
        setIndent(INDENT_TOP, INDENT_LEFT, bottom, INDENT_RIGHT);
    }
    
    /**
     * To set right indent
     * @param right int
     */
    public void setRightIndent(int right) {
        setIndent(INDENT_TOP, INDENT_LEFT, INDENT_BOTTOM, right);
    }
    
    /**
     * Set image size
     * @param width
     * @param height
     */
    public void setImgSize(int width, int height) {
    	this.IMG_WIDTH = width;
    	this.IMG_HEIGHT = height;
    }
    
    /**
     * Get wheel unit scale
     * @return
     */
    public double getWheelUnitScale() {
    	return this.WHEEL_UNIT_SCALE;
    }
    
    /**
     * Get popup style
     * @return
     */
    public POPUP_STYLE getPopupStyle() {
    	return this.POPUP;
    }
    
    /**
     * Set popup style
     * @param popup
     */
    public void setPopupStyle(POPUP_STYLE popup) {
    	this.POPUP = popup;
    }
    
    /**
     * Get graph selection border
     * @return
     */
    public SELECTION_BORDER getSelectionBorder() {
    	return SEL_BORDER;
    }
    
    /**
     * Set graph selection border
     * @param border
     */
    public void setSelectionBorder(SELECTION_BORDER border) {
    	this.SEL_BORDER = border;
    }
    
    /**
     * To get graph elements
     * @return GraphElements
     * @since JDK1.4.1
     */
    public GraphElements getGraphElements() {
        return this.GRAPH_ELEMENTS;
    }
    
    /**
     * Get graph element listener list
     * @return
     */
    public List<GraphSelectionListener> getGraphSelectionListenerList() {
    	return this.listenerList;
    }
    
    /**
     * Add graph element listener
     * @param listener
     */
    public void addGraphSelectionListener(GraphSelectionListener listener) {
    	if(this.listenerList.contains(listener)) {
    		removeGraphSelectionListener(listener);
    	}
    	this.listenerList.add(listener);
    }
    
    /**
     * Remove graph element listener
     * @param listener
     */
    public void removeGraphSelectionListener(GraphSelectionListener listener) {
    	this.listenerList.remove(listener);
    }
}
