package org.chaostocosmos.chaosgraph.swt2d;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.chaostocosmos.chaosgraph.DefaultGraphFactory;
import org.chaostocosmos.chaosgraph.Graph;
import org.chaostocosmos.chaosgraph.GraphConstants.GRAPH;
import org.chaostocosmos.chaosgraph.GraphConstants.SELECTION_BORDER;
import org.chaostocosmos.chaosgraph.GraphElement;
import org.chaostocosmos.chaosgraph.GraphElements;
import org.chaostocosmos.chaosgraph.GraphOverEvent;
import org.chaostocosmos.chaosgraph.GraphPressEvent;
import org.chaostocosmos.chaosgraph.GraphReleaseEvent;
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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
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
public class GraphCanvas extends Canvas {
	Graph graph;
	GraphElements elements;
    BufferedImage buffImg;     
    Graphics2D g2d;	
	GRAPH graphType;
	Canvas canvas;
	
	boolean autoResizing = false;
	int width, height;
	
	final Graphics2DRenderer renderer = new Graphics2DRenderer();

	/**
	 * constructor
	 * @param parent
	 * @param graphType
	 * @param elements
	 */
	public GraphCanvas(Composite parent, GRAPH graphType, GraphElements elements, boolean autoResizing) {
		super(parent, SWT.DOUBLE_BUFFERED);
        //System.out.println(getClientArea().width+" =======================  "+getClientArea().height);
        setLayout(new GridLayout(1, true));
 		GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
 		setLayoutData(gridData);
        
        setCanvasSize(getClientArea().width, getClientArea().height); 
        
		this.graphType = graphType;
		this.elements = elements;
		this.autoResizing = autoResizing;
		this.graph = DefaultGraphFactory.createGraph(this.graphType, this.elements, getClientArea().width, getClientArea().height);
        
        addPaintListener( new PaintAdpater(this.graph));
		
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
					return;
			    }
			    graph.getGraphElements().setSelectedElement(null);
			}
		});
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent me) {
			}
			@Override
			public void mouseDown(MouseEvent me) {
			    GraphElement ge = graph.isPointOnShapes(me.x, me.y);
			    if(ge != null) {
				    graph.getGraphSelectionListenerList().stream().forEach(gl -> {
						try {
							gl.onMousePressedGraph(new GraphPressEvent(graph, ge));
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					graph.getGraphElements().setSelectedElement(ge);
					//return;
			    }
			    if(me.button == 1) {
			    	graph.getGraphElements().circulateElement(true);
			    } else if(me.button == 3) {
			    	graph.getGraphElements().circulateElement(false);
			    }
			    graph.getGraphElements().setSelectedElement(null);
			}
			@Override
			public void mouseUp(MouseEvent me) {
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
					return;
			    }
			    graph.getGraphElements().setSelectedElement(null);
			}
			
		});

		this.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseScrolled(MouseEvent e) {
				//System.out.println("COUNT: "+e.count);
				double scale = 1.0d + (e.count > 0 ? graph.getWheelUnitScale() : -graph.getWheelUnitScale());
				//System.out.println("SCALE: "+scale);
				//System.out.println("IMG "+graph.getImageWidth()+"   "+graph.getImageHeight());
				width = (int)(graph.getImageWidth() * scale);
				height = (int)(graph.getImageHeight() * scale);
			
				if(width <= graph.getIndentLeft()+graph.getIndentRight()+100 || width > getShell().getDisplay().getPrimaryMonitor().getClientArea().width) {
					return;
				}
			}			
		});
		
		getShell().addListener(SWT.Resize, new Listener() {

			@Override
			public void handleEvent(Event e) {
				int w = getParent().getClientArea().width;
				int h = getParent().getClientArea().height;
				setCanvasSize(w, h);
				graph.resizeImage(g2d, w, h);
			}
			
		});
	}
	
	public void setAutoResizing(boolean is) {
		this.autoResizing = is;
	}
	
	public void setCanvasSize(int width, int height) {
		//System.out.println("CANVAS SIZE CHANGED: "+width+"   "+height);
		this.width = width;
		this.height = height;
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
			try {
				//this.graph.drawGraph(g2d);
				//System.out.println("PAINT: "+e.width+"   "+e.height);
				int w = 0;
				int h = 0;
				if(!autoResizing) {
					w = width;
					h = height;
				} else {			
					w = getParent().getClientArea().width;
					h = getParent().getClientArea().height;
					//System.out.println("PARENT SIZE: "+w+"   "+h);
				}
				setCanvasSize(w, h);
				graph.resizeImage(g2d, w, h);
				renderer.render(gc);
				redraw();
				gc.dispose();
			} catch (NotMatchGraphTypeException e1) {
				e1.printStackTrace();
			}
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
 		
        GraphCanvas canvas = new GraphCanvas(parent, type, GraphElements.newSimpleGraphElements(type), true);
        if(type == GRAPH.CIRCLE) {
        	CircleGraph cg = (CircleGraph)canvas.getGraph();
        	cg.setSelectionBorder(SELECTION_BORDER.DOT);
        	cg.setShowPercent(true);
        	cg.setShowElementName(true);
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
