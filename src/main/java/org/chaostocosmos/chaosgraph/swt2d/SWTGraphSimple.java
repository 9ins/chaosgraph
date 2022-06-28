/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph.swt2d;

import org.chaostocosmos.chaosgraph.GraphConstants.GRAPH;
import org.chaostocosmos.chaosgraph.GraphElements;
import org.chaostocosmos.chaosgraph.INTERPOLATE;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
* <p>Title: SWT graph example</p>
* <p>Description:</p>
* <br>
* <img src="pic/BAR_RATIO.jpg" alt="">
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* @author 9ins
* @version 1.2, 2006/7/5 first draft
* @since JDK1.4.1
*/
public class SWTGraphSimple {
	
	Shell shell;
	
	Button areaBtn, barBtn, barRatioBtn, circleBtn, lineBtn;
	
	GraphCanvas canvas;
	
	Composite centerComp;
	
	StackLayout stackLayout;
	
    /**
     * Constructor
     */
    public SWTGraphSimple(Shell shell) {
    	this.shell = shell;
    	initGUI();
    }
    
    /**
     * init GUI components
     */
    private void initGUI() {
    	Composite topComp = new Composite(shell, SWT.NONE);
    	GridLayout gridLayout = new GridLayout();
 		gridLayout.numColumns = 5;
 		topComp.setLayout(gridLayout);
 		topComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
 		
 		this.areaBtn = new Button(topComp, SWT.NONE);
 		this.areaBtn.setText("AREA GRAPH");
 		this.areaBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				placeGraph(centerComp, GRAPH.AREA);
			}
 		});
 		
 		this.barBtn = new Button(topComp, SWT.NONE);
 		this.barBtn.setText("BAR GRAPH");
 		this.barBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				placeGraph(centerComp, GRAPH.BAR);
			}
 		});
 		
 		this.barRatioBtn = new Button(topComp, SWT.NONE);
 		this.barRatioBtn.setText("BAR RATIO GRAPH");
 		this.barRatioBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				placeGraph(centerComp, GRAPH.BAR_RATIO);
			}
 		});
 		
 		this.circleBtn = new Button(topComp, SWT.NONE);
 		this.circleBtn.setText("CIRCLE GRAPH");
 		this.circleBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				placeGraph(centerComp, GRAPH.CIRCLE);
			}
 		}); 		
 		
 		this.lineBtn = new Button(topComp, SWT.NONE);
 		this.lineBtn.setText("LINE GRAPH");
 		this.lineBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				placeGraph(centerComp, GRAPH.LINE);
			}
 		}); 
 		
 		this.centerComp = new Composite(shell, SWT.FILL);
 		//this.centerComp.setLayout(new FillLayout());
    	GridLayout gridLayout1 = new GridLayout(1, true);
 		gridLayout1.numColumns = 1;
 		this.centerComp.setLayout(gridLayout1);
 		GridData gridData = new GridData(GridData.FILL_VERTICAL | GridData.FILL_HORIZONTAL);
 		this.centerComp.setLayoutData(gridData);
 		placeGraph(centerComp, GRAPH.AREA);
    }
    
    public void placeGraph(Composite parent, GRAPH type) {
    	if(this.canvas == null) {
    		this.canvas = new GraphCanvas(parent, type, GraphElements.newSimpleGraphElements(type), false, 1000, 600); 
    		return; 
    	}
    	//System.out.println("Placed..");
    	if(!this.canvas.isDisposed()) {
    		this.canvas.dispose();
    		//System.out.println("Disposed.....................");
    		this.canvas = new GraphCanvas(parent, type, GraphElements.newSimpleGraphElements(type), false, 1000, 600); 
    		this.canvas.getGraph().setInterpolateType(INTERPOLATE.SPLINE);
    		this.canvas.getGraph().setShowPeak(true);
    	}
    }
    
    /**
     * Dispose this object
     */
    public void dispose() {
    	/*
    	this.areaCanvas.dispose();
    	this.barCanvas.dispose();
    	this.barRatioCanvas.dispose();
    	this.circleCanvas.dispose();
    	this.lineCanvas.dispose();
    	*/
    }

    /**
     * Main
     * @param args
     */
    public static void main(String[] args) {
    	Display display = new Display();
        Shell shell = new Shell(display);
        shell.setSize(1100, 800);
        shell.setLayout(new GridLayout(1, true));
        shell.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
        
        GRAPH type = GRAPH.CIRCLE;
        SWTGraphSimple ex = new SWTGraphSimple(shell);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
              display.sleep();
        }
        ex.dispose();
        display.dispose();
    }
}