package org.chaostocosmos.chaosgraph;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.chaostocosmos.chaosgraph.GraphConstants.GRAPH;

/**
 * 
 * GraphElement
 * This object represent each graph element.
 *
 * @author Kooin-Shin
 * 2020. 8. 12.
 */
public class GraphElement implements Serializable {
    private String elementName;
    private Color elementColor;
    private String label;
    private Color labelColor;
    private List<Double> values;
    private List<Point> shapes;
    private List<Point> labelShapes;
    private int selectedValueIndex = -1;
    private double selectedValue;
    private Point selectedPoint;
    private boolean isSWT = false;
    private GRAPH graphType;
    private Graph graph;
    private int interpolateScale = -1;
    private List<Double> interpolateValues;
    private List<Point2D.Double> interpolates;
    private INTERPOLATE interpolationType;
	
    /**
     * Constructor
     * @param elementName
     * @param elementColor
     * @param label
     * @param labelColor
     */
    public GraphElement(String elementName, Color elementColor, String label, Color labelColor) {
    	this(elementName, elementColor, label, labelColor, new ArrayList<Double>());
    }	
    
    /**
     * Constructor
     * @param elementName
     * @param elementColor
     * @param label
     * @param labelColor
     * @param values
     */
    public GraphElement(String elementName, Color elementColor, String label, Color labelColor, double[] values) {
    	this(elementName, elementColor, label, labelColor, Arrays.stream(values).boxed().collect(Collectors.toList()));
    }
	
    /**
     * Constructor
     * @param elementName
     * @param elementColor
     * @param label
     * @param labelColor
     * @param values
     */
    public GraphElement(String elementName, Color elementColor, String label, Color labelColor, List<Double> values) {
		this(elementName, elementColor, label, labelColor, values, null);
    }
    
	/**
	 * Constructor
	 * @param elementName
	 * @param elementColor
	 * @param label
	 * @param labelColor
	 * @param values
	 * @param interpolationType
	 */
    public GraphElement(String elementName, Color elementColor, String label, Color labelColor, List<Double> values, INTERPOLATE interpolationType) {
		super();
		this.elementName = elementName;
		this.elementColor = elementColor;
		this.label = label;
		this.labelColor = labelColor;
		this.values = values;
		if(this.values.size() < 1) {
		    this.values.add(0d);
		}
		this.shapes = new ArrayList<Point>();
		this.labelShapes = new ArrayList<Point>();
		this.interpolationType = interpolationType;
    }    
	
    /**
     * Get element name
     * @return
     */
    public String getElementName() {
		return elementName;
    }
	
    /**
     * Set element name
     * @param elementName
     */
    public void setElementName(String elementName) {
    	this.elementName = elementName;
    }	

    /**
     * Get element color
     * @return
     */
    public Color getElementColor() {
    	return elementColor;	
    }

    /**
     * Set element color
     * @param elementColor
     */
    public void setElementColor(Color elementColor) {
    	this.elementColor = elementColor;
    }

    /**
     * Get label text
     * @return
     */
    public String getLabel() {
    	return label;
    }

    /**
     * Set label text
     * @param label
     */
    public void setLabel(String label) {
    	this.label = label;
    }

    /**
     * Get label color
     * @return
     */
    public Color getLabelColor() {
    	return labelColor;
    }

    /**
     * Set label color
     * @param labelColor
     */
    public void setLabelColor(Color labelColor) {
    	this.labelColor = labelColor;
    }

    /**
     * Get element values
     * @return
     */
    public List<Double> getValues() {
    	return values;
    }

    /**
     * Set element values
     * @param values
     */
    public void setValues(List<Double> values) {
    	this.values = GraphUtility.roundAvoid(values, GraphConstants.ROUND_PLACE);
    }

    /**
     * Get element shape point list
     * @return
     */
    public List<Point> getShapes() {
    	return shapes;
    }

    /**
     * Set element shape point list
     * @param shapes
     */
    public void setShapes(List<Point> shapes) {
    	this.shapes = shapes;
    }
    
    /**
     * Get label shape point list
     * @return
     */
    public List<Point> getLabelShapes() {
    	return this.labelShapes;
    }
    
    /**
     * Set label shape point list
     * @param labelShapes
     */
    public void setLabelShapes(List<Point> labelShapes) {
    	this.labelShapes = labelShapes;
    }

    /**
     * Add value to element value list
     * @param value
     */
    public void addValue(double value) {
    	this.values.add(GraphUtility.roundAvoid(value, GraphConstants.ROUND_PLACE));
    }
	
    /**
     * Delete value at specific index
     * @param index
     */
    public void deleteValue(int index) {
    	this.values.remove(index);
    }

    /**
     * Get selected value index
     * @return
     */
    public int getSelectedValueIndex() {
		return selectedValueIndex;
	}

    /**
     * Set selected value index
     * @param selectedValueIndex
     */
	public void setSelectedValueIndex(int selectedValueIndex) {
		this.selectedValueIndex = selectedValueIndex;
	}

	/**
	 * Get selected value on mouse pointer
	 * @return
	 */
	public double getSelectedValue() {
		return selectedValue;
	}

	/**
	 * Set value on element by mouse pointer
	 * @param selectedValue
	 */
	public void setSelectedValue(double selectedValue) {
		this.selectedValue = selectedValue;
	}	

	/**
	 * Whether SWT component
	 * @return
	 */
	public boolean isSWT() {
		return isSWT;
	}

	/**
	 * Set whether SWT component
	 * @param isSWT
	 */
	public void setSWT(boolean isSWT) {
		this.isSWT = isSWT;
	}

	/**
	 * Get graph type
	 * @return
	 */
	public GRAPH getGraphType() {
		return graphType;
	}

	/**
	 * Set graph type
	 * @param graphType
	 */
	public void setGraphType(GRAPH graphType) {
		this.graphType = graphType;
	}

	/**
	 * Get graph object
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * Set graph object
	 * @param graph
	 */
	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	/**
	 * Get selected point
	 * @return
	 */
	public Point getSelectedPoint() {
		return selectedPoint;
	}

	/**
	 * Set selected point
	 * @param selectedPoint
	 */
	public void setSelectedPoint(Point selectedPoint) {
		this.selectedPoint = selectedPoint;
	}

	/**
	 * Get interpolate Scale
	 * @return
	 */
	public int getInterpolateScale() {
		return interpolateScale;
	}

	/**
	 * Set interpolate points count
	 * @param interpolateScale
	 */
	public void setInterpolateScale(int interpolateScale) {
		this.interpolateScale = interpolateScale;
	}

	/**
	 * Get interpolated values
	 * @return
	 */
	public List<Double> getInterpolateValues() {
		return interpolateValues;
	}

	/**
	 * Set interpolated values
	 * @param interpolateValues
	 */
	public void setInterpolateValues(List<Double> interpolateValues) {
		this.interpolateValues = interpolateValues;
	}

	/**
	 * Get interpolated values
	 * @return
	 */
	public List<Point2D.Double> getInterpolates() {
		return interpolates;
	}

	/**
	 * Set interpolated values
	 * @param interpolates
	 */
	public void setInterpolates(List<Point2D.Double> interpolates) {
		this.interpolates = interpolates;
	}

	/**
	 * Get interpolation type
	 * @return
	 */
	public INTERPOLATE getInterpolationType() { 
		return interpolationType;
	}

	/**
	 * Set interpolation type
	 * @param interpolationType
	 */
	public void setInterpolationType(INTERPOLATE interpolationType) {
		this.interpolationType = interpolationType;
	}

	@Override
	public String toString() {
		return "GraphElement [elementName=" + elementName + ", elementColor="
				+ elementColor + ", label=" + label + ", labelColor="
				+ labelColor + ", values=" + values + ", shapes=" + shapes
				+ ", labelShapes=" + labelShapes + ", selectedValueIndex="
				+ selectedValueIndex + ", selectedValue=" + selectedValue
				+ ", selectedPoint=" + selectedPoint + ", isSWT=" + isSWT
				+ ", graphType=" + graphType + ", graph=" + graph
				+ ", interpolateScale=" + interpolateScale
				+ ", interpolateValues=" + interpolateValues + ", interpolates="
				+ interpolates + ", interpolationType=" + interpolationType
				+ "]";
	}
}
