package org.chaostocosmos.chaosgraph;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import org.apache.commons.math3.analysis.interpolation.AkimaSplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.DividedDifferenceInterpolator;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.LoessInterpolator;
import org.apache.commons.math3.analysis.interpolation.NevilleInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionNewtonForm;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

/**
 * 
 * InterpolationTransform
 *
 * @author Kooin-Shin
 * 2020. 10. 14.
 */
public class InterpolateTransform {
	
    /**
     * To transform x interpolation values to get x interpolation values with type.
     * @param interpolation
     * @param x
     * @param y
     * @param xi
     * @return
     */
	public static double[] transform(INTERPOLATE interpolation, double[] x, double[] y, double[] xi) {
		switch(interpolation) {
			case NONE: 
				break;
			case LINEAR:
				return linearTransform(x, y, xi);
			case SPLINE:
				return splineTransform(x, y, xi);
			case LOESS:
				return loessTransform(x, y, xi);
			case AKIMA:
				return akimaTransform(x, y, xi);
			case DIVIDED_DIFFERENCE:
				return dividedDifferenceTransform(x, y, xi);
			case NEVILLE:
				return nevilleTransform(x, y, xi);
		}
		return null;
	}
	
	/**
	 * To transform x interpolations to get y interpolations with Liner way.
	 * @param x
	 * @param y
	 * @param xi
	 * @return
	 */
	public static double[] linearTransform(double[] x, double[] y, double[] xi) {
		LinearInterpolator interpolator = new LinearInterpolator();
		final PolynomialSplineFunction psf = interpolator.interpolate(x, y);
		return DoubleStream.of(xi).map(d -> psf.value(d)).toArray();
	}
	
	/**
	 * To transform x interpolations to get y interpolations with Spline way.
	 * @param x
	 * @param y
	 * @param xi
	 * @return
	 */
	public static double[] splineTransform(double[] x, double[] y, double[] xi) {
		SplineInterpolator interpolator = new SplineInterpolator();
		final PolynomialSplineFunction psf = interpolator.interpolate(x, y);
		return DoubleStream.of(xi).map(d -> psf.value(d)).toArray();
	}
	
	/**
	 * To transform x interpolations to get y interpolations with Loess way.
	 * @param x
	 * @param y
	 * @param xi
	 * @return
	 */
	public static double[] loessTransform(double[] x, double[] y, double[] xi) {
		LoessInterpolator interpolator = new LoessInterpolator();
		final PolynomialSplineFunction psf = interpolator.interpolate(x, y);
		return DoubleStream.of(xi).map(d -> psf.value(d)).toArray();
	}
	
	/**
	 * To transform x interpolations to get y interpolations with Akima way.
	 * @param x
	 * @param y
	 * @param xi
	 * @return
	 */
	public static double[] akimaTransform(double[] x, double[] y, double[] xi) {
		AkimaSplineInterpolator interpolator = new AkimaSplineInterpolator();
		final PolynomialSplineFunction psf = interpolator.interpolate(x, y);
		return DoubleStream.of(xi).map(d -> psf.value(d)).toArray();
	}
	
	/**
	 * To transform x interpolations to get y interpolations with DifidedDifference way.
	 * @param x
	 * @param y
	 * @param xi
	 * @return
	 */
    public static double[] dividedDifferenceTransform(double[] x, double[] y, double[] xi) {
    	DividedDifferenceInterpolator interpolator = new DividedDifferenceInterpolator();
    	PolynomialFunctionNewtonForm pfnf = interpolator.interpolate(x, y);
    	return DoubleStream.of(xi).map(d -> pfnf.value(d)).toArray();
    }

    /**
     * To transform x interpolations to get y interpolations with Neville way.
     * @param x
     * @param y
     * @param xi
     * @return
     */
    public static double[] nevilleTransform(double[] x, double[] y, double[] xi) {
    	NevilleInterpolator interpolator = new NevilleInterpolator();
    	PolynomialFunctionLagrangeForm pfnf = interpolator.interpolate(x, y);
    	return DoubleStream.of(xi).map(d -> pfnf.value(d)).toArray();
    }
    
    /**
     * To populate interpolate values int elements to single interpolate type
     * @param interpolateType
     * @param graphElements
     * @param interpolateScale
     * @return
     */
    public static GraphElements populateInterpolateWithOneType(INTERPOLATE interpolateType, GraphElements graphElements, int interpolateScale) {
    	graphElements.getGraphElementMap().values().forEach(e -> { 
    		e.setInterpolateScale(interpolateScale);
    		e.setInterpolationType(interpolateType);
    	});
    	return populateInterpolate(graphElements);
    }
    
    /**
     * To populate interpolate values in elements
     * @param graphElements
     * @param interpolateScale
     * @return
     */
    public static GraphElements populateInterpolate(GraphElements graphElements) {
		final double gx = graphElements.getGraph().getGraphX();
		final double gy = graphElements.getGraph().getGraphY();
		final double gw = graphElements.getGraph().getGraphWidth();
		final double gh = graphElements.getGraph().getGraphHeight();
		final double lim = graphElements.getGraph().getLimit();
		final double mx = graphElements.getMaximum();
		final double tab = gw / graphElements.getXIndexCount();
		//System.out.println("graph width: "+gw+"   gx: "+gx);
    	graphElements.getGraphElementMap().values().stream().forEach(e -> {
    		try {
    		if(e.getInterpolationType() != null && e.getInterpolateScale() != -1) {
    			final int vc = e.getValues().size();
    			double[] xv = IntStream.range(0, vc).mapToDouble(i -> i * tab + gx).toArray();
    			double[] yv = IntStream.range(0, vc).mapToDouble(d -> (d >= vc) ? gy : (lim < mx) ? gy - e.getValues().get(d) * gh / mx : gy - e.getValues().get(d) * gh / lim).toArray();
    			int interpolateCounts = vc * e.getInterpolateScale();
    			double gap = (tab * vc) / interpolateCounts;
    			double[] xi = IntStream.range(0, interpolateCounts - e.getInterpolateScale()+1).mapToDouble(i -> gap * i  + gx).toArray();
    			//System.out.println("xv count: "+xv.length+"   xi count: "+xi.length+"  tab: "+tab+"   gap: "+gap);
    			double[] yi = InterpolateTransform.transform(e.getInterpolationType(), xv, yv, xi);
    			List<Double> values = DoubleStream.of(yi).boxed().collect(Collectors.toList());
    			//Collections.reverse(values);
    			e.setInterpolateValues(values);
    			List<Point2D.Double> interpolateList = IntStream.range(0, xi.length).mapToObj(i -> new Point2D.Double(xi[i], yi[i])).collect(Collectors.toList());
    			e.setInterpolates(interpolateList);
    		}
    		} catch(Exception e1) {
    			e1.printStackTrace();
    		}
    	});
    	return graphElements;
    }
}
