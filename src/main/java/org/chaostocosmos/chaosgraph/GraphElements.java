/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
* <p>Title: GraphElements class</p>
* <p>Description:</p>
* <pre>
* </pre>
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* 
* @author Kooin-Shin(9ins)
* @version 1.0, 2001/8/13 19:30 First draft
* @version 1.2, 2006/7/5 
* @since JDK1.4.1
*/
public class GraphElements implements GraphConstants
{
	/**
	 * This represent graph element ordering
	 */
	private List<Object> elementOrder;
	/**
	 * Graph element map
	 */
    private Map<Object, GraphElement> elementMap;
    /**
     * Selected graph element object
     */
    private GraphElement selectedElement;
    /**
     * Selected x index of graph
     */
    private int selectedIndex = -1;
	/**
	 * X indexes
	 */
    private List<Object> xIndex;
    /**
     * Y indexes
     */
    private List<Double> yIndex;					
    /**
     * Graph type
     */
    private GRAPH graphType;
    /**
     * Graph object
     */
    private Graph graph;
    /**
     * Maximum value of graph elements
     */
    private double maxValue;
    /**
     * Minimum value of graph elements
     */
    private double minValue;

    /**
     * Constructor
     * @param graphType
     * @param xIndex
     * @param yIndex
     */
    public GraphElements(GRAPH graphType, Object[] xIndex, Double[] yIndex) {
        this(graphType, Arrays.asList(xIndex), Arrays.asList(yIndex));
    }
    
    /**
     * Constructor
     * @param graphType
     * @param xIndex
     * @param yIndex
     */
    public GraphElements(GRAPH graphType, List<Object> xIndex, List<Double> yIndex) {
    	this.graphType = graphType;
    	this.xIndex = xIndex;
    	this.yIndex = yIndex;
    	this.elementMap = new TreeMap<Object, GraphElement>();
    	this.elementOrder = new ArrayList<Object>();
    }
    
    /**
     * Get graph object
     * @return
     */
    public Graph getGraph() {
    	return this.graph;
    }
    
    /**
     * Set graph object
     * @param graph
     */
    public void setGraph(Graph graph) {
    	this.graph = graph;
    	this.elementMap.values().stream().forEach(e -> e.setGraph(graph));
    }
    
    /**
     * Get maximum in double list
     * @param value
     * @return double
     * @since JDK1.4.1
     */
    public double calMax(List<Double> value) {
        return value.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
    }
    
    /**
     * Get minimum in double list
     * @param value
     * @return double
     * @since JDK1.4.1
     */
    public double calMin(List<Double> value) {
        return value.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
    }
    
    /**
     * Get maximum in map
     * @param values float[][]
     * @return float
     * @since JDK1.4.1
     */
    public double calMax(Map<Object, GraphElement> map) {
        return map.values().stream().flatMap(ge -> ge.getValues().stream()).mapToDouble(Double::doubleValue).max().getAsDouble();
    }
    
    /**
     * Get minimum in map
     * @param values 
     * @return float
     * @since JDK1.4.1
     */
    public double calMin(Map<Object, GraphElement> map)	{
    	return map.values().stream().flatMap(ge -> ge.getValues().stream()).mapToDouble(Double::doubleValue).min().getAsDouble();
    }
    
    /**
     * Get graph type
     * @return int �׷��� ����
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
    public GraphElement getGraphElement(String elementName) {
    	return this.elementMap.get(elementName);
    }
    
    /**
     * Set graph element by name
     * @param elementName
     * @param ge
     */
    public void setGraphElement(Object elementName, GraphElement ge) {
    	addGraphElement(elementName, ge);
    }
    
    /**
     * Add graph element
     * @param elements String[]
     * @exception NotMatchGraphTypeException
     * @since JDK1.4.1
     */
    public void addElement(GraphElement ge) {
    	addGraphElement(ge.getElementName(), ge);
    }
    
    /**
     * Add graph element
     * @param elementName
     * @param ge
     */
    public void addGraphElement(Object elementName, GraphElement ge) {
    	ge.setGraphType(this.graphType);
    	ge.setGraph(this.graph);
    	this.elementMap.put(elementName, ge);
    	if(!this.elementOrder.contains(elementName)) {
        	this.elementOrder.add(ge.getElementName());
    	}
    	if(this.elementMap.size() > this.xIndex.size()) {
    	    this.xIndex.add("");
    	}
    }
    
    /**
     * Remove graph element
     * @param elementName
     */
    public GraphElement removeGraphElement(Object elementName) {
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
     * Get graph element map
     * @return
     */
    public Map<Object, GraphElement> getGraphElementMap() {
    	return this.elementMap;
    }    
    
    /**
     * Set graph element map
     * @param elementMap
     */
    public void setGraphElementMap(Map<Object, GraphElement> elementMap) {
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
    public List<Object> getXIndex() {
        return this.xIndex;
    }
    
    /**
     * Get minimum size of index
     * @return
     */
    public int getMinimumXIndexSize() {
    	return this.elementMap.entrySet().stream().map(e -> e.getValue().getValues().size()).max(Integer::compare).get();
    }
    
    /**
     * Get y indexes
     * @return float
     * @since JDK1.4.1
     */
    public List<Double> getYIndex() {
        return this.yIndex;
    }
    
    /**
     * Get graph element value to double value
     * @param elementName
     * @param valueIndex
     * @return
     */
    public double getGraphElementValue(String elementName, int valueIndex) {
		List<Double> values = this.elementMap.get(elementName).getValues();
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
    	return this.elementMap.values().stream().mapToDouble(ge -> ge.getValues().get(index)).max().getAsDouble();
    }
    
    /**
     * Get minimum value of specific index fo graph elements
     * @param index
     * @return
     */
    public double getIndexMinimum(int index) {
    	return this.elementMap.values().stream().mapToDouble(ge -> ge.getValues().get(index)).min().getAsDouble();
    }
    
    /**
     * Get element values
     * @return float[][]
     * @since JDK1.4.1
     */
    public List<Double> getElementsValues(String elementName) {
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
    public void setValues(String elementName, double[] values) {
    	this.elementMap.get(elementName).setValues(DoubleStream.of(values).boxed().collect(Collectors.toList()));
    }
    
    /**
     * Set X index
     * @param xIndex String[]
     * @since JDK1.4.1
     */
    public void setXIndex(List<Object> xIndex) {
        this.xIndex = xIndex;
    }
    
    /**
     * Set y index
     * @param xIndex float[]
     * @since JDK1.4.1
     */
    public void setYIndex(List<Double> yIndex) {
        this.yIndex = GraphUtility.roundAvoid(yIndex, ROUND_PLACE);
    }
    
    /**
     * Set y index
     * @param yIndex
     */
    public void setYIndex(double[] yIndex) {
		List<Double> list = DoubleStream.of(yIndex).boxed().collect(Collectors.toList());
		this.yIndex = GraphUtility.roundAvoid(list, ROUND_PLACE);
    }
    
    /**
     * Get selected graph element
     * @return
     */
    public GraphElement getSelectedElement() {
    	return this.selectedElement;
    }
    
    /**
     * Set selected graph element
     * @param ge
     */
    public void setSelectedElement(GraphElement ge) {
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
     * Create and get sample GraphElements object
     * @param type
     * @return
     */
    public static GraphElements newSimpleGraphElements(GRAPH type) {
        List<Object> xIndex =null;
        List<Double> yIndex =null;
    	xIndex = new ArrayList<Object>();
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
        double[][] values = {{44,35,0,32,0,33,29,43,25,22,32,43,23},
                            {43,25,10,32,0,23,52,32,32,23,54,23,48, 20, 60, 140, 500, 10},
                            {500,93,0,49,0,24,93,63,92,84,69,46,28},
                            {300,25,0,32,0,23, 9,19,32,70,93,29,15},
                            {20,36,0,24,22,37,33,54,23,48,53,150,22}};
        float[] value = {3, 43.6f, 40f, 10, 5};
        
        GraphElements graphElements = new GraphElements(type, xIndex, yIndex);
        for(int i=0; i<elements.length; i++) {        	
        	GraphElement ge = new GraphElement(elements[i], colors[i], elements[i], colors[i], values[i]);
        	graphElements.addElement(ge);
        }
        return graphElements;
    }
}
