/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph;

import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.PlanarImage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import javax.media.jai.JAI;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.ImageCodec;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import java.util.Enumeration;
import java.util.List;
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
    	System.out.println(ext);
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
}
