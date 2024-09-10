/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImagingConstants;
import org.chaostocosmos.chaosgraph.GraphUtility.CODEC;
import org.chaostocosmos.chaosgraph.awt2d.AreaGraph;
import org.chaostocosmos.chaosgraph.awt2d.BarGraph;
import org.chaostocosmos.chaosgraph.awt2d.BarRatioGraph;
import org.chaostocosmos.chaosgraph.awt2d.CircleGraph;
import org.chaostocosmos.chaosgraph.awt2d.LineGraph;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
* <p>Title: GraphUtility</p>
* <p>Description: </p>
* <pre>
* This is contribute that utility of image processing e.g. functionality of saving image
* </pre>
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* 
* @author 9ins
* @version 1.2, 2006/7/5 first draft
* @since JDK1.4.1
*/
public class GraphUtility {
    /**
     * Supported codec list
     */
    public static enum CODEC {JPEG, TIFF, PNG, BMP, ICO, DCX, PGM, GIF, PCX, WBMP, PSD};

    /**
     * Save image to file
     * @param image
     * @param saveFile
     * @param codec
     * @throws FileNotFoundException
     * @throws NotSuppotedEncodingFormatException
     * @throws FileNotFoundException
     * @since JDK1.4.1
     */
    public static void saveSWTImage(Image image, File saveFile, CODEC codec) throws NotSuppotedEncodingFormatException, FileNotFoundException {
    	ImageLoader loader = new ImageLoader();
    	loader.data = new ImageData[] {image.getImageData()};
    	String ext = saveFile.getName().substring(saveFile.getName().lastIndexOf(".")+1);
    	if(!Stream.of(CODEC.values()).anyMatch(c -> c.name().equals(ext))) {
    		throw new NotSuppotedEncodingFormatException("Given file extention isn't exist in supported codec list.");
    	}
    	int format = -1;
    	switch(codec.name()) {
    	case "JPEG" :
    		format = SWT.IMAGE_JPEG;
    		break;
    	case "TIFF" :
    		format = SWT.IMAGE_TIFF;
    		break;
    	case "PNG" :
    		format = SWT.IMAGE_PNG;
    		break;
    	case "BMP" :
    		format = SWT.IMAGE_BMP;
    		break;
    	}
    	loader.save(new FileOutputStream(saveFile), format);
    }
    
    /**
     * Save buffered image to file
     * @param bufferedImage
     * @param saveFile
     * @param codec
	 * @param compresstion
     * @throws FileNotFoundException
     * @throws IOException
	 * @throws ImageWriteException
     * @throws NotSuppotedEncodingFormatException
     * @since JDK1.4.1
     */
    public static void saveBufferedImage(BufferedImage bufferedImage, File saveFile, CODEC codec, float compresstion) throws IOException, NotSuppotedEncodingFormatException, ImageWriteException {		
        Map<String, Object> params = new HashMap<>();
        // Set the JPEG compression quality (0.0f - 1.0f)
        params.put(ImagingConstants.PARAM_KEY_COMPRESSION, compresstion);
		Imaging.writeImage(bufferedImage, saveFile, 
				codec == CODEC.JPEG ? ImageFormats.JPEG :
				codec == CODEC.PNG ? ImageFormats.PNG :
				codec == CODEC.BMP ? ImageFormats.BMP :
				codec == CODEC.TIFF ? ImageFormats.TIFF :
				codec == CODEC.WBMP ? ImageFormats.WBMP :
				codec == CODEC.PSD ? ImageFormats.PSD :
				codec == CODEC.PSD ? ImageFormats.ICO :
				codec == CODEC.DCX ? ImageFormats.DCX :				
				codec == CODEC.PGM ? ImageFormats.PGM :
				codec == CODEC.GIF ?  ImageFormats.GIF :
				codec == CODEC.PCX ? ImageFormats.PCX :
				ImageFormats.UNKNOWN, params);
    }
	

    /**
     * Create graph object with specified json
     * @param json
     * @return
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    public static <V, X, Y> Graph<Double, String, Double> createGraphWithJson(String json) throws JsonMappingException, JsonProcessingException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	ObjectMapper om = new ObjectMapper();
    	Map<String, Object> map = (Map<String, Object>)om.readValue(json, Map.class);
    	return createGraphWithMap(map);
    }
    
    /**
     * Create graph object with given map object
     * @param map
     * @return
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */ 
    public static <V, X, Y> Graph<Double, String, Double> createGraphWithMap(Map<String, Object> map) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	GraphConstants.GRAPH type = GraphConstants.GRAPH.valueOf(map.get("GRAPH")+"");
    	INTERPOLATE interpolate = INTERPOLATE.valueOf(map.get("INTERPOLATE")+"");
    	int width = (int)Double.parseDouble(map.get("WIDTH")+"");
    	int height = (int)Double.parseDouble(map.get("HEIGHT")+"");
    	
    	List<String> xIndex = (List<String>)map.get("XINDEX");  
    	List<Double> list = (List<Double>)map.get("YINDEX");  
    	List<Double> yIndex = new ArrayList<Double>();
    	for(Object o : list) {
    		yIndex.add(Double.parseDouble(o+""));
    	}
    	List<Map<String, Object>> elementList = (List<Map<String, Object>>)map.get("ELEMENTS");
    	List<GraphElement<Double, String, Double>> geList = elementList.stream().map(m -> {
   			String elementName = m.get("ELEMENT")+"";
   			String label = m.get("LABEL")+"";
   			List<Integer> colorList = ((List<Object>)m.get("COLOR")).stream().map(v -> (int)Double.parseDouble(v+"")).collect(Collectors.toList());
   			Color elementColor = new Color((int)colorList.get(0), (int)colorList.get(1), (int)colorList.get(2));
   			List<Double> valueList = ((List<Object>)m.get("VALUES")).stream().map(v -> Double.parseDouble(v+"")).collect(Collectors.toList());
   			return new GraphElement<Double, String, Double>(elementName, elementColor, label, elementColor, valueList);
    	}).filter(el -> el != null).collect(Collectors.toList());

    	GraphElements<Double, String, Double> elements = new GraphElements<>(type, geList, xIndex, yIndex, 2);    	
    	AbstractGraph<Double, String, Double> graph = null; 

    	switch(type) {
    		case LINE:
    			graph = new LineGraph<Double, String, Double>(elements, width, height);
    			break;
    		case AREA:
    			graph = new AreaGraph<Double, String, Double>(elements, width, height);
    			break;
    		case CIRCLE:
    			graph = new CircleGraph<Double, String, Double>(elements, width, height);
    			break;
    		case BAR: 
    			graph = new BarGraph<Double, String, Double>(elements, width, height);
    			break;
    		case BAR_RATIO:
    			graph = new BarRatioGraph<Double, String, Double>(elements, width, height);
    			break;
    	}
    	List<Map<String, Object>> configList = (List<Map<String, Object>>)map.get("CONFIGS");
    	if(configList != null) {
    		for(Map<String, Object> m : configList) {
    			String methodName = m.get("METHOD")+"";
    			List<Object> paramList = (List<Object>) m.get("PARAMS");
    			List<Class> classList = new ArrayList<Class>();
    			for(Object o : paramList) {
    				classList.add(o.getClass());
    			}
    			invokeMethod(graph, methodName, classList);
    		}
    	}    	
    	graph.setInterpolateType(interpolate);
    	return graph;
    }
    
	/**
	 * Invoke method with parameters
	 * @param object
	 * @param methodName
	 * @param params
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
    private static Object invokeMethod(Object object, String methodName, Object...params) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	Object[] paramClasses = Arrays.asList(params).stream().map(o -> o.getClass()).toArray();
    	Method[] methods = object.getClass().getMethods();
    	for(Method method : methods) {
    		if(method.getName().equalsIgnoreCase(methodName)) {
    			Class[] classes = new Class[paramClasses.length];
    			for(int i=0; i<classes.length; i++) {
    				classes[i] = paramClasses[i].getClass();
    			}
    			return object.getClass().getMethod(methodName, classes).invoke(object, params);
    		}
    	}
    	throw new NoSuchMethodException("There isn't exist method in Class. Class: "+object.getClass().getName()+" Specified: "+methodName);
    }
    
    /**
     * Round values
     * @param values
     * @param places
     * @return
     */
    public static <V extends Number> List<V> roundAvoid(List<V> values, int places) {
		for(int i=0; i< values.size(); i++) {
		    values.set(i, roundAvoid(values.get(i), places));
		}
		return values;
    }
    
    /**
     * Round
     * @param value
     * @param places
     * @return
     */
	@SuppressWarnings("unchecked")
    public static <V extends Number> V roundAvoid(V value, int places) {
		double scale = Math.pow(10, places);
		return (V) Double.valueOf(Math.round(value.doubleValue() * scale) / scale);
    }
    
    /**
     * main
     * @param args
     * @throws IOException
     * @throws NotSuppotedEncodingFormatException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws ImageWriteException 
     */
    public static void main(String[] args) throws IOException, NotSuppotedEncodingFormatException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ImageWriteException {
    	File f = new File("./aws-api-lambda-chart-json.json"); 
    	String json = Files.lines(f.toPath()).collect(Collectors.joining(System.lineSeparator()));
    	Graph<Double, String, Double> graph = createGraphWithJson(json);
    	System.out.println("width: "+graph.getBufferedImage().getWidth()+"   height: "+graph.getBufferedImage().getHeight());
    	GraphUtility.saveBufferedImage(graph.getBufferedImage(), new File("./circle.png"), CODEC.PNG, 0.5f);
    }
}
