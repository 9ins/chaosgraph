/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph.awt2d;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.chaostocosmos.chaosgraph.AbstractGraph;
import org.chaostocosmos.chaosgraph.GraphConstants.GRAPH;
import org.chaostocosmos.chaosgraph.GraphConstants.GRID;
import org.chaostocosmos.chaosgraph.GraphConstants.POPUP_STYLE;
import org.chaostocosmos.chaosgraph.GraphConstants.SELECTION_BORDER;
import org.chaostocosmos.chaosgraph.GraphElement;
import org.chaostocosmos.chaosgraph.GraphElements;
import org.chaostocosmos.chaosgraph.GraphOverEvent;
import org.chaostocosmos.chaosgraph.GraphPressEvent;
import org.chaostocosmos.chaosgraph.GraphReleaseEvent;
import org.chaostocosmos.chaosgraph.GraphSelectionListener;
import org.chaostocosmos.chaosgraph.GraphUtility;
import org.chaostocosmos.chaosgraph.GraphUtility.CODEC;
import org.chaostocosmos.chaosgraph.INTERPOLATE;
import org.chaostocosmos.chaosgraph.NotMatchArrayException;
import org.chaostocosmos.chaosgraph.NotMatchGraphTypeException;
import org.chaostocosmos.chaosgraph.NotSuppotedEncodingFormatException;

//import com.iproject.wbmpcreator.*
;
/**
* <p>Title: Graph example </p>
* <p>Description: </p>
* <br>
* <img src="pic/area_interpolate.jpg"  alt=""> 
* <p>Copyright: Copyleft (c) 2006</p>
* <p>Company: ChaosToCosmos</p>
* @author 9ins
* @version 1.2, 2006/7/5 First draft
* @since JDK1.4.1
*/
public class AWTGraphSimple1 extends JFrame implements GraphSelectionListener<Double, String, Double> {
    //abstract graph object
    public AbstractGraph<Double, String, Double> graph;
    //x index list
    public List<String> xIndex = null;
    //y index list
    public List<Double> yIndex = null;
    //graph element list
    public String[] elements = {"Kafa", "elastic search", "Oracle", "Maria", "S3"};
    //elements color array
    public Color[] colors = {new Color(130,180,130), 
    						new Color(180,130,130), 
    						new Color(180,180,140), 
    						new Color(150,150,150), 
    						new Color(150,200,158)};
    //elements values array
    public Double[][] values = {{44d,35d,0d,32d,0d,33d,29d,43d,25d,22d,32d,43d,23d},
                        {43d,25d,10d,32d,0d,23d,52d,32d,32d,23d,54d,23d,48d, 20d, 60d, 140d, 500d, 10d},
                        {500d,93d,0d,49d,0d,24d,93d,63d,92d,84d,69d,46d,28d},
                        {300d,25d,0d,32d,0d,23d, 9d,19d,32d,70d,93d,29d,15d},
                        {20d,36d,0d,24d,22d,37d,33d,54d,23d,48d,53d,150d,22d}};

    //y index value array
    public float[] value = {3, 43.6f, 40f, 10, 5};

    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    JPanel jPanel2 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    JPanel jPanel3 = new JPanel();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    JButton jButton4 = new JButton();
    JTextField jTextField1 = new JTextField();
    JButton jButton5 = new JButton();
    BorderLayout borderLayout2 = new BorderLayout();
    JButton jButton6 = new JButton();
    JButton jButton7 = new JButton();
    JButton jButton8 = new JButton();
    
    GraphPanel<Double, String, Double> gpArea, gpBar, gpBarRatio, gpCircle, gpLine;
    
    /**
     * constructor
     * @since JDK1.4.1
     */
    public AWTGraphSimple1() {
        try {
        	xIndex = new ArrayList<String>();
        	for(int i=0; i<=16; i++) {
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
        	
            GraphElements<Double, String, Double> graphElements = new GraphElements<Double, String, Double>(GRAPH.AREA, xIndex, yIndex);
            for(int i=0; i<elements.length; i++) {
            	GraphElement<Double, String, Double> ge = new GraphElement<Double, String, Double>(elements[i], colors[i], elements[i]+" - sev"+i, Color.GRAY, values[i]);
            	graphElements.addElement(ge);
            }
            if(gpArea == null) {
            	gpArea = new GraphPanel<Double, String, Double>(GRAPH.AREA, graphElements, 600, 400);
            }
            GraphElements<Double, String, Double> graphElements1 = new GraphElements<Double, String, Double>(GRAPH.BAR, xIndex, yIndex);
            for(int i=0; i<elements.length; i++) {        	
            	GraphElement<Double, String, Double> ge = new GraphElement<Double, String, Double>(elements[i], colors[i], elements[i], colors[i], values[i]);
            	graphElements1.addElement(ge);
            }
            if(gpBar == null) {
            	gpBar = new GraphPanel<Double, String, Double>(GRAPH.BAR, graphElements1, 600, 400);
            }
            GraphElements<Double, String, Double> graphElements2 = new GraphElements<Double, String, Double>(GRAPH.BAR_RATIO, xIndex, yIndex);
            for(int i=0; i<elements.length; i++) {
            	GraphElement<Double, String, Double> ge = new GraphElement<Double, String, Double>(elements[i], colors[i], elements[i], colors[i], values[i]);
            	graphElements2.addElement(ge);
            }
            if(gpBarRatio == null) {
            	gpBarRatio = new GraphPanel<Double, String, Double>(GRAPH.BAR_RATIO, graphElements2, 600, 400);
            }
            GraphElements<Double, String, Double> graphElements3 = new GraphElements<Double, String, Double>(GRAPH.CIRCLE, xIndex, yIndex);
            for(int i=0; i<elements.length; i++) {
            	GraphElement<Double, String, Double> ge = new GraphElement<Double, String, Double>(elements[i], colors[i], elements[i], colors[i], values[i]);
            	graphElements3.addElement(ge);
            }
            if(gpCircle == null) {
            	gpCircle = new GraphPanel<Double, String, Double>(GRAPH.CIRCLE, graphElements3, 600, 400);
            }
            GraphElements<Double, String, Double> graphElements4 = new GraphElements<Double, String, Double>(GRAPH.LINE, xIndex, yIndex);
            for(int i=0; i<elements.length; i++) {        	
            	GraphElement<Double, String, Double> ge = new GraphElement<Double, String, Double>(elements[i], colors[i], elements[i], colors[i], values[i]);
            	graphElements4.addElement(ge);
            }
            if(gpLine == null) {
            	gpLine = new GraphPanel<Double, String, Double>(GRAPH.LINE, graphElements4, 600, 400);
            }
            jbInit();
            showAreaGraph();

            setVisible(true);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * show area graph on panel
     * @throws NotMatchArrayException
     * @throws NotMatchGraphTypeException
     * @since JDK1.4.1
     */
    public void showAreaGraph() throws NotMatchArrayException, NotMatchGraphTypeException {
        //graph = (AreaGraph)gpArea.getGraph();        
        String json = null;
		try {
			json = Files.lines(Paths.get("D:\\Github\\chaosgraph\\aws-api-lambda-chart-json.json")).collect(Collectors.joining(System.lineSeparator()));
	        graph = (AbstractGraph<Double, String, Double>)GraphUtility.<Double, String, Double>createGraphWithJson(json);
	        GraphUtility.saveBufferedImage(graph.getBufferedImage(), new File("./line.png"), CODEC.PNG);
		} catch (Exception e) {
			e.printStackTrace();
		}        
        graph.setTitle("This is simple area graph.");
        graph.setShowShadow(false);
        graph.setGridStyle(GRID.DOT);
        graph.setLimit(1000d);
        graph.setPopupStyle(POPUP_STYLE.ROUND);
        graph.setSelectionEnable(true);
        graph.setSelectionBorder(SELECTION_BORDER.DOT);
        graph.setInterpolateType(INTERPOLATE.SPLINE);
        graph.setInterPolateScale(9);
        graph.setShowGraphXY(false);
        graph.setShowPeek(true);
        graph.addGraphSelectionListener(new GraphSelectionListener<Double, String, Double>() {
			@Override
			public void onMouseOverGraph(GraphOverEvent<Double, String, Double> goe) throws Exception {
				//Get selected element
				GraphElement<Double, String, Double> ge = goe.getGraphElement();
				//Get values of selected element
				List<Double> values = ge.getValues();
				//Get selected value in values
				double value = (double)ge.getSelectedValue();
				//Get selected index in values
				int index = ge.getSelectedValueIndex();
			}
			@Override
			public void onMousePressedGraph(GraphPressEvent<Double, String, Double> gpe) throws Exception {
				GraphElement<Double, String, Double> ge = gpe.getGraphElement();
				AbstractGraph<Double, String, Double> graph = (AbstractGraph<Double, String, Double>)ge.getGraph();
				//Toggle graph background color when user select a element and click.
				if(graph.getGraphBgColor().equals(Color.white)) {
					graph.setGraphBgColor(Color.black);
				} else {
					graph.setGraphBgColor(Color.white);
				}
			}
			@Override
			public void onMouseReleasedGraph(GraphReleaseEvent<Double, String, Double> gre)	throws Exception {
			}
        });
        getContentPane().remove(2);
        getContentPane().add(gpArea, BorderLayout.CENTER);
        getContentPane().validate();
        graph.resizeImage(gpArea.getWidth(), gpArea.getHeight());
        gpArea.repaint();
    }
    /**
     * show bar graph on panel
     * @throws NotMatchArrayException
     * @throws NotMatchGraphTypeException
     * @since JDK1.4.1
     */
    public void showBarGraph() throws NotMatchArrayException, NotMatchGraphTypeException {
    	graph = (BarGraph<Double, String, Double>)gpBar.getGraph();
    	graph.setTitle("This is simple bar graph.");
        graph.setLimit(300d);
        graph.setUnit("m");
        graph.setGraphBgColor(Color.yellow);
        graph.addGraphSelectionListener(this);
        
        getContentPane().remove(2);
        getContentPane().add(gpBar, BorderLayout.CENTER);
        getContentPane().validate();
        gpBar.repaint();
    }
    
    /**
     * show bar ratio graph on panel
     * @throws NotMatchArrayException
     * @throws NotMatchGraphTypeException
     * @since JDK1.4.1
     */
    public void showBarRatioGraph() throws NotMatchArrayException, NotMatchGraphTypeException {
    	graph = (BarRatioGraph<Double, String, Double>)gpBarRatio.getGraph();
    	graph.setTitle("This is simple bar ratio graph.");
        graph.setLimit(300d);
        graph.setUnit(" m");
        graph.addGraphSelectionListener(this);
        
        getContentPane().remove(2);
        getContentPane().add(gpBarRatio, BorderLayout.CENTER);
        getContentPane().validate();
        gpBarRatio.repaint();
    }
    
    /**
     * show circle graph on panel
     * @throws NotMatchArrayException
     * @throws NotMatchGraphTypeException
     * @since JDK1.4.1
     */
    public void showCircleGraph() throws NotMatchArrayException, NotMatchGraphTypeException {
    	graph = (CircleGraph<Double, String, Double>)gpCircle.getGraph();
    	graph.setTitle("This is simple circle graph.");
        graph.setShadowAngle(280);
        graph.setRightIndent(100);
        graph.setSelectionBorder(SELECTION_BORDER.DOT);
        ((CircleGraph<Double, String, Double>)graph).setShowPercent(true);
        graph.addGraphSelectionListener(this);
        //graph.setShowLabel(false);
        
        getContentPane().remove(2);
        getContentPane().add(gpCircle, BorderLayout.CENTER);
        getContentPane().validate();
        gpCircle.repaint();
    }
    /**
     * show line graph on panel
     * @throws NotMatchArrayException
     * @throws NotMatchGraphTypeException
     * @since JDK1.4.1
     */
    public void showLineGraph() throws NotMatchArrayException, NotMatchGraphTypeException {
    	graph = (LineGraph<Double, String, Double>)gpLine.getGraph();
    	graph.setTitle("This is simple line graph.");
        graph.setGridSize(2);
        graph.setGridStyle(GRID.DOT);
        graph.setShowPeek(true);
        graph.setInterpolateType(INTERPOLATE.SPLINE);
        graph.addGraphSelectionListener(this);
        
        getContentPane().remove(2);
        getContentPane().add(gpLine, BorderLayout.CENTER);
        getContentPane().validate();
    }
    /**
     * init swing components
     * @throws Exception
     * @since JDK1.4.1
     */
    private void jbInit() throws Exception  {
        this.getContentPane().setLayout(borderLayout1);
        jPanel1.setLayout(flowLayout1);
        jPanel2.setLayout(flowLayout2);
        jPanel3.setLayout(borderLayout2);
        jButton1.setText("AREA");
        jButton1.addActionListener(new GraphTest_jButton1_actionAdapter(this));
        jButton2.setText("BAR");
        jButton2.addActionListener(new GraphTest_jButton2_actionAdapter(this));
        jButton7.setText("BAR RATIO");
        jButton7.addActionListener(new GraphTest_jButton7_actionAdapter(this));
        jButton3.setText("CIRCLE");
        jButton3.addActionListener(new GraphTest_jButton3_actionAdapter(this));
        jButton4.setText("LINE");
        jButton4.addActionListener(new GraphTest_jButton4_actionAdapter(this));
        jButton5.setText("Choose...");
        jButton5.addActionListener(new GraphTest_jButton5_actionAdapter(this));
        jButton8.setText("SAVE");
        jButton8.addActionListener(new GraphTest_jButton8_actionAdapter(this));
        jTextField1.setMinimumSize(new Dimension(150, 22));
        jTextField1.setOpaque(true);
        jTextField1.setPreferredSize(new Dimension(150, 22));
        jTextField1.setText("");
        jTextField1.setColumns(40);
        jTextField1.setScrollOffset(0);
        jTextField1.setEditable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocale(java.util.Locale.getDefault());
        this.setResizable(true);
        this.setTitle("ChaosGraph");
        jButton6.setText("CLOSE");
        jButton6.addActionListener(new GraphTest_jButton6_actionAdapter(this));
        this.getContentPane().add(jPanel1, BorderLayout.NORTH);
        jPanel1.add(jButton1, null);
        jPanel1.add(jButton2, null);
        jPanel1.add(jButton7, null);
        jPanel1.add(jButton3, null);
        jPanel1.add(jButton4, null);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(jTextField1, null);
        jPanel2.add(jButton5, null);
        jPanel2.add(jButton8, null);
        jPanel2.add(jButton6, null);
        this.getContentPane().add(jPanel3, BorderLayout.CENTER);
        setSize(800, 600);
    }
    /**
     * Perform area graph selection
     * @param ae ActionEvent
     * @throws NotMatchGraphTypeException
     * @throws NotMatchArrayException
     * @since JDK1.4.1
     */
    public void jButton1_actionPerformed(ActionEvent ae) throws NotMatchGraphTypeException, NotMatchArrayException {
        showAreaGraph();
    }
    /**
     * Perform bar graph selection
     * @param ae ActionEvent
     * @throws NotMatchGraphTypeException
     * @throws NotMatchArrayException
     * @since JDK1.4.1
     */
    public void jButton2_actionPerformed(ActionEvent ae) throws NotMatchGraphTypeException, NotMatchArrayException {
        showBarGraph();
    }
    /**
     * Perform bar ratio graph selection
     * @param ae ActionEvent
     * @throws NotMatchGraphTypeException
     * @throws NotMatchArrayException
     * @since JDK1.4.1
     */
    public void jButton7_actionPerformed(ActionEvent ae) throws NotMatchGraphTypeException, NotMatchArrayException {
        showBarRatioGraph();
    }   
    /**
     * Perform circle graph selection
     * @param ae ActionEvent
     * @throws NotMatchGraphTypeException
     * @throws NotMatchArrayException
     * @since JDK1.4.1
     */
    public void jButton3_actionPerformed(ActionEvent ae) throws NotMatchGraphTypeException, NotMatchArrayException {
        showCircleGraph();
    }
    /**
     * Perform line graph selection
     * @param ae ActionEvent
     * @throws NotMatchGraphTypeException
     * @throws NotMatchArrayException
     * @since JDK1.4.1
     */
    public void jButton4_actionPerformed(ActionEvent ae) throws NotMatchGraphTypeException, NotMatchArrayException {
        showLineGraph();
    }
    /**
     * Perform file chooser selection
     * @param ae ActionEvent
     * @since JDK1.4.1
     */
    public void jButton5_actionPerformed(ActionEvent ae) {
       JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
       fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
       int returnVal = fileChooser.showOpenDialog(this);
       File tFile = null;
       if(returnVal == JFileChooser.APPROVE_OPTION) {
    	   tFile = fileChooser.getSelectedFile();
    	   jTextField1.setText(tFile.getAbsolutePath()+File.separator+graph.getGraphType().name()+".png");
       } else {
    	   return;
       }       
    }
    /**
     * Perform close button selection
     * @param e ActionEvent
     * @since JDK1.4.1
     */
    public void jButton6_actionPerformed(ActionEvent e) {
        System.exit(0);
    }
    
    /**
     * Perform save button selection
     * @param ae
     */
    public void jButton8_actionPerformed(ActionEvent ae) {
    	String filePath = jTextField1.getText();
    	File tFile = new File(filePath);
        //Get buffered image from graph object
        BufferedImage img = this.graph.getBufferedImage();
        try {
            //Save buffered image to file
            GraphUtility.saveBufferedImage(img, tFile, CODEC.PNG);
        } catch (NotSuppotedEncodingFormatException ex) {
            JOptionPane.showConfirmDialog(this, ex.getMessage(), "Error", JOptionPane.CLOSED_OPTION);
            return;
        } catch (FileNotFoundException ex) {
            JOptionPane.showConfirmDialog(this, ex.getMessage(), "Error", JOptionPane.CLOSED_OPTION);
            return;
        } catch (IOException ex) {
            JOptionPane.showConfirmDialog(this, ex.getMessage(), "Error", JOptionPane.CLOSED_OPTION);
            return;
        }
        JOptionPane.showConfirmDialog(this, "Success to save image...", "Error", JOptionPane.CLOSED_OPTION);
    }

	@Override
	public void onMouseOverGraph(GraphOverEvent<Double, String, Double> goe) throws Exception {
		//System.out.println(goe.toString());
		
	}
	@Override
	public void onMousePressedGraph(GraphPressEvent<Double, String, Double> gpe) throws Exception {
		System.out.println(gpe.toString());		
	}
	@Override
	public void onMouseReleasedGraph(GraphReleaseEvent<Double, String, Double> gre) throws Exception {
		JOptionPane.showConfirmDialog(this,"You just select "+gre.getGraphElement().getElementName()+" element.   value: "+gre.getGraphElement().getSelectedValue(),  "Select", JOptionPane.YES_OPTION);
	}
	
    /**
     * Main
     * @param args String[]
     * @throws UnsupportedLookAndFeelException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     * @since JDK1.4.1
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        new AWTGraphSimple1();
    }
}

/**
 * Buffon action adapter class
 */
class GraphTest_jButton1_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple1 adaptee;

    GraphTest_jButton1_actionAdapter(AWTGraphSimple1 adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            adaptee.jButton1_actionPerformed(e);
        }
        catch (NotMatchArrayException ex)
        {
            ex.printStackTrace();
        }
        catch (NotMatchGraphTypeException ex)
        {
            ex.printStackTrace();
        }
    }
}
/**
 * Buffon action adapter class
 */
class GraphTest_jButton2_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple1 adaptee;

    GraphTest_jButton2_actionAdapter(AWTGraphSimple1 adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            adaptee.jButton2_actionPerformed(e);
        }
        catch (NotMatchArrayException ex)
        {
            ex.printStackTrace();
        }
        catch (NotMatchGraphTypeException ex)
        {
            ex.printStackTrace();
        }
    }
}
/**
 * Buffon action adapter class
 */
class GraphTest_jButton7_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple1 adaptee;

    GraphTest_jButton7_actionAdapter(AWTGraphSimple1 adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            adaptee.jButton7_actionPerformed(e);
        }
        catch (NotMatchArrayException ex)
        {
            ex.printStackTrace();
        }
        catch (NotMatchGraphTypeException ex)
        {
            ex.printStackTrace();
        }
    }
}
/**
 * Buffon action adapter class
 */
class GraphTest_jButton3_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple1 adaptee;

    GraphTest_jButton3_actionAdapter(AWTGraphSimple1 adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            adaptee.jButton3_actionPerformed(e);
        }
        catch (NotMatchArrayException ex)
        {
            ex.printStackTrace();
        }
        catch (NotMatchGraphTypeException ex)
        {
            ex.printStackTrace();
        }
    }
}
/**
 * Buffon action adapter class
 */
class GraphTest_jButton4_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple1 adaptee;

    GraphTest_jButton4_actionAdapter(AWTGraphSimple1 adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            adaptee.jButton4_actionPerformed(e);
        }
        catch (NotMatchArrayException ex)
        {
            ex.printStackTrace();
        }
        catch (NotMatchGraphTypeException ex)
        {
            ex.printStackTrace();
        }
    }
}
/**
 * Buffon action adapter class
 */
class GraphTest_jButton5_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple1 adaptee;

    GraphTest_jButton5_actionAdapter(AWTGraphSimple1 adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton5_actionPerformed(e);
    }
}
/**
 * Buffon action adapter class
 */
class GraphTest_jButton6_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple1 adaptee;

    GraphTest_jButton6_actionAdapter(AWTGraphSimple1 adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton6_actionPerformed(e);
    }
}
/**
 * Buffon action adapter class
 */
class GraphTest_jButton8_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple1 adaptee;

    GraphTest_jButton8_actionAdapter(AWTGraphSimple1 adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton8_actionPerformed(e);
    }
}
