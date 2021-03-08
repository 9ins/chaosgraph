/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph;

import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.PlanarImage;

import org.chaostocosmos.chaosgraph.awt2d.AreaGraph;
import org.chaostocosmos.chaosgraph.awt2d.BarGraph;
import org.chaostocosmos.chaosgraph.awt2d.BarRatioGraph;
import org.chaostocosmos.chaosgraph.awt2d.CircleGraph;
import org.chaostocosmos.chaosgraph.awt2d.LineGraph;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import javax.media.jai.JAI;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import com.sun.media.jai.codec.ImageEncoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.media.jai.codec.ImageCodec;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.io.File;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class GraphUtility
{
    /**
     * Supported codec list
     */
    public static enum CODEC {JPEG, TIFF, PNG, BMP};
    
    /**
     * Save image to file
     * @param image
     * @param saveFile
     * @param codec
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NotSuppotedEncodingFormatException
     * @throws FileNotFoundException
     * @throws IOException
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
     * @param image BufferedImage
     * @param saveFile File
     * @param codec
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NotSuppotedEncodingFormatException
     * @since JDK1.4.1
     */
    public static void saveBufferedImage(BufferedImage image, File saveFile, CODEC codec) throws IOException, NotSuppotedEncodingFormatException {
        Enumeration enu = ImageCodec.getCodecs();
    	String ext = saveFile.getName().substring(saveFile.getName().lastIndexOf(".")+1);
    	if(!Stream.of(CODEC.values()).anyMatch(c -> c.name().equalsIgnoreCase(ext))) {
    		throw new NotSuppotedEncodingFormatException("Given file extention isn't exist in supported codec list.");
    	}
        ParameterBlock pb = new ParameterBlock();
        pb.add(image);
        PlanarImage tPlanarImage = (PlanarImage)JAI.create("awtImage", pb );
        ImageCodec ic = ImageCodec.getCodec(codec.name());
        ImageEncoder tEncoder = ic.createImageEncoder(codec.name(), new FileOutputStream(saveFile),  null);
        tEncoder.encode(tPlanarImage);
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
    public static Graph createGraphWithJson(String json) throws JsonMappingException, JsonProcessingException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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
    public static Graph createGraphWithMap(Map<String, Object> map) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	GraphConstants.GRAPH type = GraphConstants.GRAPH.valueOf(map.get("GRAPH")+"");
    	INTERPOLATE interpolate = INTERPOLATE.valueOf(map.get("INTERPOLATE")+"");
    	int width = (int)Double.parseDouble(map.get("WIDTH")+"");
    	int height = (int)Double.parseDouble(map.get("HEIGHT")+"");
    	
    	List<Object> xIndex = (List<Object>)map.get("XINDEX");  
    	List<Object> list = (List<Object>)map.get("YINDEX");  
    	List<Double> yIndex = new ArrayList<Double>();
    	for(Object o : list) {
    		yIndex.add(Double.parseDouble(o+""));
    	}    	
    	List<Map<String, Object>> elementList = (List<Map<String, Object>>)map.get("ELEMENTS");
    	List<GraphElement> geList = elementList.stream().map(m -> {
   			String elementName = m.get("ELEMENT")+"";
   			String label = m.get("LABEL")+"";
   			List<Integer> colorList = ((List<Object>)m.get("COLOR")).stream().map(v -> (int)Double.parseDouble(v+"")).collect(Collectors.toList());
   			Color elementColor = new Color((int)colorList.get(0), (int)colorList.get(1), (int)colorList.get(2));
   			List<Double> valueList = ((List<Object>)m.get("VALUES")).stream().map(v -> Double.parseDouble(v+"")).collect(Collectors.toList());
   			return new GraphElement(elementName, elementColor, label, elementColor, valueList);
    	}).filter(el -> el != null).collect(Collectors.toList());
    	//elementList.stream().forEach(System.out::println);
    	GraphElements elements = new GraphElements(type, geList, xIndex, yIndex);
    	
    	AbstractGraph graph = null; 
    	switch(type) {
    		case LINE :
    			graph = new LineGraph(elements, width, height);
    			break;
    		case AREA :
    			graph = new AreaGraph(elements, width, height);
    			break;
    		case CIRCLE :
    			graph = new CircleGraph(elements, width, height);
    			break;
    		case BAR : 
    			graph = new BarGraph(elements, width, height);
    			break;
    		case BAR_RATIO :
    			graph = new BarRatioGraph(elements, width, height);
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
    public static List<Double> roundAvoid(List<Double> values, int places) {
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
    public static double roundAvoid(double value, int places) {
		double scale = Math.pow(10, places);
		double val = Math.round(value * scale) / scale;
		return val;
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
     */
    public static void main(String[] args) throws IOException, NotSuppotedEncodingFormatException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	File f = new File("C:\\Users\\chaos\\OneDrive\\문서\\신구인\\aws-api-lambda-chart-json.json");
    	String json = Files.lines(f.toPath()).collect(Collectors.joining(System.lineSeparator()));
    	Graph graph = createGraphWithJson(json);
    	System.out.println("width: "+graph.getBufferedImage().getWidth()+"   height: "+graph.getBufferedImage().getHeight());
    	GraphUtility.saveBufferedImage(graph.getBufferedImage(), new File("./line.png"), CODEC.PNG);
    }
}
