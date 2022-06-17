/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
* <p>Title: GraphElements class</p>
* <p>Description:</p>
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* 
* @author Kooin-Shin(9ins)
* @version 1.0, 2001/8/13 19:30 First draft
* @version 1.2, 2006/7/5 
* @since JDK1.4.1
*/
public class GraphElements <V, X, Y> implements GraphConstants {
	/**
	 * This represent graph element ordering
	 */
	private List<Object> elementOrder;
	/**
	 * Graph element map
	 */
    private Map<Object, GraphElement<V, X, Y>> elementMap;
    /**
     * Selected graph element object
     */
    private GraphElement<V, X, Y> selectedElement;
    /**
     * Selected x index of graph
     */
    private int selectedIndex = -1;
	/**
	 * X indexes
	 */
    private List<X> xIndex;
    /**
     * Y indexes
     */
    private List<Y> yIndex;					
    /**
     * Graph type
     */
    private GRAPH graphType;
    /**
     * Graph object
     */
    private Graph<V, X, Y> graph;
    /**
     * Constructor
     * @param graphType
     * @param xIndex
     * @param yIndex
     */
    public GraphElements(GRAPH graphType, X[] xIndex, Y[] yIndex) {
        this(graphType, Arrays.asList(xIndex), Arrays.asList(yIndex));
    }
    
    /**
     * Constructor
     * @param graphType
     * @param xIndex
     * @param yIndex
     */
    public GraphElements(GRAPH graphType, List<X> xIndex, List<Y> yIndex) {
    	this(graphType, null, xIndex, yIndex);
    }
    
    /**
     * Constructor
     * @param graphType
     * @param elements
     * @param xIndex
     * @param yIndex
     */
    public GraphElements(GRAPH graphType, List<GraphElement<V, X, Y>> elements, List<X> xIndex, List<Y> yIndex) {
    	this.graphType = graphType;
    	this.xIndex = xIndex;
    	this.yIndex = yIndex;
		this.elementMap = new TreeMap<Object, GraphElement<V, X, Y>>();
    	this.elementOrder = new ArrayList<Object>();
    	if(elements != null) {
    		elements.stream().forEach(e -> addElement(e));
    	}
    }
    
    /**
     * Get graph object
     * @return
     */
    public Graph<V, X, Y> getGraph() {
    	return this.graph;
    }
    
    /**
     * Set graph object
     * @param graph
     */
    public void setGraph(Graph<V, X, Y> graph) {
    	this.graph = graph;
    	this.elementMap.values().stream().forEach(e -> e.setGraph(graph));
    }
    
    /**
     * Get maximum in double list
     * @param value
     * @return double
     * @since JDK1.4.1
     */
    public double calMax(List<V> value) {
        return value.stream().mapToDouble(v -> v == null ? 0d : Double.valueOf(v+"")).max().getAsDouble();
    }
    
    /**
     * Get minimum in double list
     * @param value
     * @return double
     * @since JDK1.4.1
     */
    public double calMin(List<V> value) {
        return value.stream().mapToDouble(v -> v == null ? 0d : Double.valueOf(v+"")).min().getAsDouble();
    }
    
    /**
     * Get maximum in map
     * @param map
     * @return
     */
    public double calMax(Map<Object, GraphElement<V, X, Y>> map) {
        return map.values().stream().mapToDouble(ev -> calMax(ev.getValues())).max().getAsDouble();
    }
    
    /**
     * Get minimum in map
     * @param map
     * @return
     */
    public double calMin(Map<Object, GraphElement<V, X, Y>> map)	{
        return map.values().stream().mapToDouble(ev -> calMin(ev.getValues())).min().getAsDouble();
    }
    
    /**
     * Get graph type
     * @return int
     * @since JDK1.4.1
     */
    public GRAPH getGraphType() {
        return this.graphType;
    }
    
    /**
     * get GraphElement 
     * @param elementName
     * @return
     */
    public GraphElement<V, X, Y> getGraphElement(String elementName) {
    	return this.elementMap.get(elementName);
    }
    
    /**
     * Set graph element by name
     * @param elementName
     * @param ge
     */
    public void setGraphElement(Object elementName, GraphElement<V, X, Y> ge) {
    	addGraphElement(elementName, ge);
    }
    
    /**
     * Add graph element
     * @param ge
     * @exception NotMatchGraphTypeException
     * @since JDK1.4.1
     */
    public void addElement(GraphElement<V, X, Y> ge) {
    	addGraphElement(ge.getElementName(), ge);
    }
    
    /**
     * Add graph element
     * @param elementName
     * @param ge
     */
    public void addGraphElement(Object elementName, GraphElement<V, X, Y> ge) {
    	ge.setGraphType(this.graphType);
    	ge.setGraph(this.graph);
    	this.elementMap.put(elementName, ge);
    	if(!this.elementOrder.contains(elementName)) {
        	this.elementOrder.add(ge.getElementName());
    	}
    }

    /**
     * Remove graph element
     * @param elementName
     */
    public GraphElement<V, X, Y> removeGraphElement(Object elementName) {
    	this.elementOrder = this.elementOrder.stream().filter(o -> !o.equals(elementName)).collect(Collectors.toList());
    	return this.elementMap.remove(elementName);
    }
    
    /**
     * Circulate elements
     * @param forword
     */
    public void circulateElement(boolean forword) {
    	if(forword) {
    		Object o = this.elementOrder.remove(0);
    		this.elementOrder.add(o);
    	} else {
    		Object o = this.elementOrder.remove(this.elementOrder.size()-1);
    		this.elementOrder.add(0, o);
    	}
    }

    /**
     * Sorting by last value of element
     */
    public void orderElementByLastValue() {        
        this.elementOrder = this.elementMap.values().stream().sorted((e2, e1) -> {
            V last1v = e1.getValues().get(e1.getValues().size()-1);
            V last2v = e2.getValues().get(e2.getValues().size()-1);
            double last1 = last1v == null ? 0d : (double)last1v;
            double last2 = last2v == null ? 0d : (double)last2v;
            return last1 > last2 ? 1 : last1 < last2 ? -1 : 0;
        }).map(e -> e.getElementName()).collect(Collectors.toList());
    }
    
    /**
     * Get graph element map
     * @return
     */
    public Map<Object, GraphElement<V, X, Y>> getGraphElementMap() {
    	return this.elementMap;
    }    
    
    /**
     * Set graph element map
     * @param elementMap
     */
    public void setGraphElementMap(Map<Object, GraphElement<V, X, Y>> elementMap) {
    	this.elementMap = elementMap;
    	this.elementOrder = this.elementMap.entrySet().stream().map(e -> e.getKey()).collect(Collectors.toList());
    }
    
    /**
     * Get element order by element names
     * @return
     */
    public List<Object> getElementOrder() {
    	return this.elementOrder;
    }
   
    /**
     * Get x indexes
     * @return String[]
     * @since JDK1.4.1
     */
    public List<X> getXIndex() {
        return this.xIndex;
    }
    
    /**
     * Get minimum size of index
     * @return
     */
    public int getMinimumXIndexSize() {
    	return this.elementMap.entrySet().stream().map(e -> e.getValue().getValues().size()).max(Integer::compare).get()-1;
    }
    
    /**
     * Get y indexes
     * @return float
     * @since JDK1.4.1
     */
    public List<Y> getYIndex() {
        return this.yIndex;
    }
    
    /**
     * Get graph element value to double value
     * @param elementName
     * @param valueIndex
     * @return
     */
    public V getGraphElementValue(String elementName, int valueIndex) {
		List<V> values = this.elementMap.get(elementName).getValues();
		if(values.size() < valueIndex) {
		    return values.get(valueIndex);
		} else {
		    throw new IndexOutOfBoundsException("Given index value is over than element value size.");
		}
    }
    
    /**
     * Get maximum value of specific index of graph elements
     * @param index
     * @return
     */
    public double getIndexMaximum(int index) {
    	return this.elementMap.values().stream().mapToDouble(e -> Double.valueOf(e.getValues().get(index)+"")).max().getAsDouble();
    }
    
    /**
     * Get minimum value of specific index fo graph elements
     * @param index
     * @return
     */
    public double getIndexMinimum(int index) {
    	return this.elementMap.values().stream().mapToDouble(e -> Double.valueOf(e.getValues().get(index)+"")).min().getAsDouble();
    }
    
    /**
     * Get element values
     * @return float[][]
     * @since JDK1.4.1
     */
    public List<V> getElementsValues(String elementName) {
        return this.elementMap.get(elementName).getValues();
    }
    
    /**
     * Get maximum value of elements
     * @return float
     * @since JDK1.4.1
     */
    public double getMaximum() {
        return calMax(this.elementMap); 
    }
    
    /**
     * Get minimum value of elements
     * @return float
     * @since JDK1.4.1
     */
    public double getMin() {
        return calMin(this.elementMap);
    }
    
    /**
     * Get x index count
     * @return int
     * @since JDK1.4.1
     */
     public int getXIndexCount() {
        return this.xIndex.size();
    }
     
    /**
     * Get y index count
     * @return int
     * @since JDK1.4.1
     */
    public int getYIndexCount() {
        return this.yIndex.size();
    }
    
    /**
     * Set element values
     * @param values float[][]
     * @exception NotMatchGraphTypeException
     * @since JDK1.4.1
     */
    public void setValues(String elementName, V[] values) {
    	this.elementMap.get(elementName).setValues(Arrays.asList(values));
    }
    
    /**
     * Set X index
     * @param xIndex String[]
     * @since JDK1.4.1
     */
    public void setXIndex(List<X> xIndex) {
        this.xIndex = xIndex;
    }
    
    /**
     * Set y index
     * @param yIndex 
     * @since JDK1.4.1
     */
    public void setYIndex(List<Y> yIndex) {
        this.yIndex = yIndex;
    }
    
    /**
     * Set y index
     * @param yIndex
     */
    public void setYIndex(Y[] yIndex) {
		this.yIndex = Arrays.asList(yIndex);
    }
    
    /**
     * Get selected graph element
     * @return
     */
    public GraphElement<V, X, Y> getSelectedElement() {
    	return this.selectedElement;
    }
    
    /**
     * Set selected graph element
     * @param ge
     */
    public void setSelectedElement(GraphElement<V, X, Y> ge) {
    	this.selectedElement = ge;
    }
    
    /**
     * Get selected index
     * @return
     */
    public int getSelectedIndex() {
    	return this.selectedIndex;
    }
    
    /**
     * Set selected index
     * @param index
     */
    public void setSelectedIndex(int index) {
    	this.selectedIndex = index;
    }
    
    /**
     * Get whole label dimension
     * @return
     */
    public Rectangle getLabelRectangle() {
    	int width = this.elementMap.values().stream().map(ge -> ge.getLabelShapes()).flatMap(List<Point>::stream).map(p -> p.x).max(Integer::compare).get() - this.graph.LABEL_X;
    	int height = this.elementMap.values().stream().map(ge -> ge.getLabelShapes()).flatMap(List<Point>::stream).map(p -> p.y).max(Integer::compare).get() - this.graph.LABEL_Y; 
    	return new Rectangle(this.graph.LABEL_X, this.graph.LABEL_Y, width, height);
    }
    
    /**
     * Create and get sample GraphElements object
     * @param type
     * @return
     */
    public static GraphElements<?, ?, ?> newSimpleGraphElements(GRAPH type) {
        List<String> xIndex =null;
        List<Double> yIndex =null;
    	xIndex = new ArrayList<String>();
    	for(int i=0; i<17; i++) {
    		if(i % 2 == 0)
    			xIndex.add(i+"");
    		else {
    			xIndex.add(null);
    		}
    	}
    	yIndex = new ArrayList<Double>();
    	yIndex.add(50d);
    	yIndex.add(80d);
    	yIndex.add(500d);
    	String[] elements = {"Kafa", "elastic search", "Oracle", "Maria", "S3"};
        Color[] colors = {new Color(130,180,130), 
        				  new Color(180,130,130), 
        				  new Color(180,180,140), 
        				  new Color(150,150,150), 
        				  new Color(150,200,158)};
        Double[][] values = {{44d,35d,0d,32d,0d,33d,29d,43d,25d,22d,32d,43d,23d},
                            {43d,25d,10d,32d,0d,23d,52d,32d,32d,23d,54d,23d,48d,20d,60d,140d,500d,10d},
                            {500d,93d,0d,49d,0d,24d,93d,63d,92d,84d,69d,46d,28d},
                            {300d,25d,0d,32d,0d,23d,9d,19d,32d,70d,93d,29d,15d},
                            {20d,36d,0d,24d,22d,37d,33d,54d,23d,48d,53d,150d,22d}};
        
        GraphElements<Double, String, Double> graphElements = new GraphElements<>(type, xIndex, yIndex);
        for(int i=0; i<elements.length; i++) {        	
        	GraphElement<Double, String, Double> ge = new GraphElement<>(elements[i], colors[i], elements[i], colors[i], values[i]);
        	graphElements.addElement(ge);
        }
        return graphElements;
    }
}
