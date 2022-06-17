package org.chaostocosmos.chaosgraph.swt2d;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.chaostocosmos.chaosgraph.AbstractGraph;
import org.chaostocosmos.chaosgraph.DefaultGraphFactory;
import org.chaostocosmos.chaosgraph.Graph;
import org.chaostocosmos.chaosgraph.GraphConstants.GRAPH;
import org.chaostocosmos.chaosgraph.GraphConstants.SELECTION_BORDER;
import org.chaostocosmos.chaosgraph.GraphElement;
import org.chaostocosmos.chaosgraph.GraphElements;
import org.chaostocosmos.chaosgraph.GraphOverEvent;
import org.chaostocosmos.chaosgraph.GraphPressEvent;
import org.chaostocosmos.chaosgraph.NotMatchGraphTypeException;
import org.chaostocosmos.chaosgraph.awt2d.CircleGraph;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * GraphCanvas
 *
 * @author Kooin-Shin
 * 2020. 9. 21.
 */
public class GraphCanvas<V, X, Y> extends Canvas {
	AbstractGraph<V, X, Y> graph;
	GraphElements<V, X, Y> elements;
    BufferedImage buffImg;     
    Graphics2D g2d;	
	GRAPH graphType;
	Canvas canvas;
	
	boolean autoResizing = false;
	int width, height;
	
	final Graphics2DRenderer renderer = new Graphics2DRenderer(this);
	
	/**
	 * Constructor
	 * @param parent
	 * @param graphType
	 * @param elements
	 * @param autoResizing
	 * @param w
	 * @param h
	 */
	public GraphCanvas(Composite parent, GRAPH graphType, GraphElements<V, X, Y> elements, boolean autoResizing, int w, int h) {
		this(parent, graphType, elements, autoResizing, w, h, false);
	}

	/**
	 * Constructor
	 * @param parent
	 * @param graphType
	 * @param elements
	 */
	public GraphCanvas(Composite parent, GRAPH graphType, GraphElements<V, X, Y> elements, boolean autoResizing, int w, int h, boolean isRAPMode) {
		super(parent, SWT.DOUBLE_BUFFERED);
		this.width = w;
		this.height = h;
        //System.out.println(getClientArea().width+" =======================  "+getClientArea().height);
        setLayout(new GridLayout(1, true));
 		GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
 		gridData.widthHint = this.width;
 		gridData.heightHint = this.height;
 		setLayoutData(gridData);
        
		this.graphType = graphType;
		this.elements = elements;
		this.autoResizing = autoResizing;
		this.graph = DefaultGraphFactory.createGraph(this.graphType, this.elements, getClientArea().width, getClientArea().height);
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent me) {
			}
			
			@Override
			public void mouseDown(MouseEvent me) {
			    GraphElement<V, X, Y> ge = graph.isPointOnShapes(me.x, me.y);
			    if(ge != null && graph.getGraphElements().getLabelRectangle().contains(me.x, me.y)) {
				    graph.getGraphSelectionListenerList().stream().forEach(gl -> {
						try {
							gl.onMousePressedGraph(new GraphPressEvent(graph, ge));
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					graph.getGraphElements().setSelectedElement(ge);
			    } else {
				    if(me.button == 1) {
				    	graph.getGraphElements().circulateElement(true);
				    } else if(me.button == 3) {
				    	graph.getGraphElements().circulateElement(false);
				    }
				    graph.getGraphElements().setSelectedElement(null);
			    }
			    redraw();
			}
			
			@Override
			public void mouseUp(MouseEvent me) {
				/*
			    GraphElement ge = graph.isPointOnShapes(me.x, me.y);
			    if(ge != null) {
					//System.out.println(ge.getElementName()+"   selected index: "+ge.getSelectedValueIndex()+"  value : "+ge.getSelectedValue());
				    graph.getGraphSelectionListenerList().stream().forEach(gl -> {
						try {
							gl.onMouseReleasedGraph(new GraphReleaseEvent(graph, ge));
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					graph.getGraphElements().setSelectedElement(ge);
					redraw();
					return;
			    }
			    graph.getGraphElements().setSelectedElement(null);
			    redraw();
			    */
			}
		});
		if(!isRAPMode) {
    		this.addMouseMoveListener(new MouseMoveListener () { 
				@Override
				public void mouseMove(MouseEvent me) {
				    GraphElement ge = graph.isPointOnShapes(me.x, me.y);
				    if(ge != null) {
						//System.out.println(ge.getElementName()+"   selected index: "+ge.getSelectedValueIndex()+"  value : "+ge.getSelectedValue());
					    graph.getGraphSelectionListenerList().stream().forEach(gl -> {
							try {
								gl.onMouseOverGraph(new GraphOverEvent(graph, ge));
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
						graph.getGraphElements().setSelectedElement(ge);
					    redraw();
						return;
				    }
				    graph.getGraphElements().setSelectedElement(null); 
				    redraw();
				}
			});
			this.addMouseWheelListener(new MouseWheelListener() {
				@Override
				public void mouseScrolled(MouseEvent e) {
					//System.out.println("COUNT: "+e.count);
					double scale = 1.0d + (e.count > 0 ? graph.getWheelUnitScale() : -graph.getWheelUnitScale());
					//System.out.println("SCALE: "+scale);
					//System.out.println("IMG "+graph.getImageWidth()+"   "+graph.getImageHeight());
					//System.out.println("SCALED : "+graph.getImageWidth() * scale+"   "+graph.getImageHeight() * scale);
					width = (int)(graph.getImageWidth() * scale);
					height = (int)(graph.getImageHeight() * scale);
					//System.out.println(width+"   "+height);
					if(width <= graph.getIndentLeft()+graph.getIndentRight()+100 || width > getShell().getDisplay().getPrimaryMonitor().getClientArea().width) {
						//return;
					}
					redraw();
				}			
			});
		}
		
		parent.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if(!isDisposed()) {
					int w = getParent().getClientArea().width;
					int h = getParent().getClientArea().height;
					if(autoResizing) {
						setCanvasSize(w, h);
						graph.resizeImage(g2d, w, h);
						redraw();
					}
				}
			}
		});
        addPaintListener( new PaintAdpater(this.graph));
        parent.layout(true, true);
		redraw();
	}
	
	public void setAutoResizing(boolean is) {
		this.autoResizing = is;
	}
	
	public void setCanvasSize(int width, int height) {
		//System.out.println("CANVAS SIZE CHANGED: "+width+"   "+height);
		GridData gd = (GridData)getLayoutData();
		if(gd != null) {
			gd.widthHint = width;
			gd.heightHint = height;
			gd.horizontalSpan = 0;
			gd.horizontalIndent = 0; 
			gd.verticalIndent = 0;
			gd.verticalSpan = 0;
			this.setLayoutData(gd);
		}
		Shell shell = getShell();
		if(shell != null) {
			shell.layout(true, true);
		}
	}
	
	public Canvas getCanvas() {
		return this.canvas;
	}
	
	public Graph getGraph() {
		return this.graph;
	}
	
	public void dispose() {
		super.dispose();
	}

	long rate = 0;
	class PaintAdpater implements PaintListener {
		Graph graph;
		
		public PaintAdpater(Graph graph) { 
			this.graph = graph;
		}

		@Override
		public void paintControl(PaintEvent e) {
			GC gc = e.gc;
			renderer.prepareRendering(gc);
	        Graphics2D g2d = renderer.getGraphics2D();
	        if(g2d == null) {
	        	gc.dispose();
	        	return;
	        }
			try {
				//this.graph.drawGraph(g2d);
				//System.out.println("PAINT: "+e.width+"   "+e.height);
				if(graph.isImgFixed() && !autoResizing) {
					//System.out.println("PAINT: "+width+"   "+height);
					graph.resizeImage(g2d, width, height);
				} else if(!graph.isImgFixed() && autoResizing) {
					width = getParent().getClientArea().width;
					height = getParent().getClientArea().height;
					setCanvasSize(width, height);
					graph.resizeImage(g2d, width, height);
				} else {			
					graph.repaint(g2d);
				}
				renderer.render(gc);
				//System.out.println("PARENT SIZE: "+w+"   "+h);
				//redraw();
				gc.dispose();
			} catch (NotMatchGraphTypeException e1) {
				e1.printStackTrace();
			}
			rate++;
		}
	}
	
	public static void main(String[] args) {
    	Display display = new Display();
        Shell shell = new Shell(display);
        GRAPH type = GRAPH.CIRCLE;
        shell.setSize(1000, 600);
        shell.setLayout(new FillLayout());
        
        Composite parent = new Composite(shell, SWT.FILL);
        parent.setLayout(new FillLayout());
        
        /*
    	GridLayout gridLayout1 = new GridLayout(1, false);
 		gridLayout1.numColumns = 1;
 		parent.setLayout(gridLayout1);
 		GridData gridData = new GridData();
 		gridData.widthHint = shell.getClientArea().width;
 		gridData.heightHint = shell.getClientArea().height;
 		parent.setLayoutData(gridData);
 		*/
 		 
        GraphCanvas canvas = new GraphCanvas(parent, type, GraphElements.newSimpleGraphElements(type), false, 600, 400, true);
        canvas.getGraph().setImgFixed(true);
        if(type == GRAPH.CIRCLE) {
        	CircleGraph cg = (CircleGraph)canvas.getGraph();
        	cg.setSelectionBorder(SELECTION_BORDER.DOT);
        	cg.setShowPercent(true);
        	cg.setShowElementName(true);
        	cg.setShowPopup(true);
        }
        shell.open();
        
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
              display.sleep();
        }
        display.dispose();
        canvas.dispose();
	}
}
