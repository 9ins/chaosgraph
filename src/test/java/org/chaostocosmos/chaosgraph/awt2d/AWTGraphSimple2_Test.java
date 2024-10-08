/**
 * <i>Chaos Graph API </i><br>
 */
package org.chaostocosmos.chaosgraph.awt2d; 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import org.chaostocosmos.chaosgraph.GraphConstants.GRAPH;
import org.chaostocosmos.chaosgraph.GraphConstants.GRID;
import org.chaostocosmos.chaosgraph.GraphElement;
import org.chaostocosmos.chaosgraph.GraphElements;
import org.chaostocosmos.chaosgraph.NotMatchGraphTypeException;

/**
 * <p>Title: JVM Memory Viewer</p>
 * <p>Description:</p>
 * <br>
 * <img src="pic/area_interpolate1.jpg" alt="">
 * <p>Copyright: Copyleft (c) 2006</p>
 * <p>Company: ChaosToCosmos</p>
 * 
 * @author 9ins
 * @version 1.2, 2006/7/5 First draft
 * @since JDK1.4.1
 */
public class AWTGraphSimple2_Test extends JFrame implements Runnable {
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JButton jButton4 = new JButton();
    JButton jButton3 = new JButton();
    JButton jButton7 = new JButton();
    JButton jButton5 = new JButton();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JButton jButton6 = new JButton();
    JButton jButton8 = new JButton();
    BorderLayout borderLayout2 = new BorderLayout();
    Border border1;
    FlowLayout flowLayout2 = new FlowLayout();

    public Thread thr = null;
    public boolean isThread = false;
    long interval = 100;
    public GraphElements<Double, String, Double> ge1 = null, ge2 = null, ge3 = null;
    public String[] elements = {"Used Memory", "Free Memory"};
    public Color[] colors = {new Color(230, 150, 150), new Color(150, 150, 230)};
    public List<String> xIndex = new ArrayList<String>();
    public List<String> xIndex_ = new ArrayList<String>();
    public List<Double> yIndex = new ArrayList<Double>();
    public GRAPH sel = GRAPH.AREA;
    
    long unit = 1024 * 1024;
    GraphPanel<Double, String, Double> gp;

    /**
     * Constructor
     */
    public AWTGraphSimple2_Test() {
        try {
            yIndex.add(0d);
            this.ge1 = new GraphElements<Double, String, Double>(GRAPH.AREA, xIndex, yIndex);
            this.ge2 = new GraphElements<Double, String, Double>(GRAPH.LINE, xIndex, yIndex);
            this.ge3 = new GraphElements<Double, String, Double>(GRAPH.BAR, xIndex_, yIndex);
            int i=0;
            for(String element : elements) {
	        	GraphElement<Double, String, Double> ge = new GraphElement<Double, String, Double>(element, colors[i], element, colors[i]);
	        	this.ge1.addElement(ge);
	        	this.ge2.addElement(ge);
	        	this.ge3.addElement(ge);
	        	i++;
            }
            jbInit();
            jButton4_actionPerformed(null);
            setVisible(true);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * init visual components
     * @throws Exception
     */
    private void jbInit() throws Exception {
        border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        this.getContentPane().setLayout(borderLayout1);
        jPanel1.setLayout(flowLayout1);
        jButton1.setText("STOP");
        jButton1.addActionListener(new GraphExample2_Test_jButton1_actionAdapter(this));
        jButton2.setText("START");
        jButton2.addActionListener(new GraphExample2_Test_jButton2_actionAdapter(this));
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        jButton3.setAlignmentX((float) 0.0);
        jButton3.setText("CLOSE");
        jButton3.addActionListener(new GraphExample2_Test_jButton3_actionAdapter(this));
        jPanel2.setLayout(flowLayout2);
        jPanel3.setLayout(borderLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel2.setBorder(BorderFactory.createEtchedBorder());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("JVM Memory Viewer");
        jButton4.setText("AREA STYLE");
        jButton4.addActionListener(new GraphExample2_Test_jButton4_actionAdapter(this));
        jButton5.setText("LINE STYLE");
        jButton5.addActionListener(new GraphExample2_Test_jButton5_actionAdapter(this));
        jButton7.setText("BAR STYLE");
        jButton7.addActionListener(new GraphExample2_Test_jButton7_actionAdapter(this));
        jButton6.setText("Populate objects");
        jButton6.addActionListener(new GraphExample2_Test_jButton6_actionAdapter(this));
        jButton8.setText("GC");
        jButton8.addActionListener(new GraphExample2_Test_jButton8_actionAdapter(this));
        
        this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
        jPanel1.add(jButton3, null);
        this.getContentPane().add(jPanel2,  BorderLayout.NORTH);
        jPanel2.add(jButton2, null);
        jPanel2.add(jButton1, null);
        jPanel2.add(jButton4, null);
        jPanel2.add(jButton5, null);
        jPanel2.add(jButton7, null);
        jPanel2.add(jButton6, null);
        jPanel2.add(jButton8, null);
        jPanel2.add(jLabel1, null);
        this.getContentPane().add(jPanel3, BorderLayout.CENTER);
        this.setSize(1400, 650);
    }    
    /**
     * Thread run with loop
     */
    @Override
    public void run() {
        while(this.isThread) {
            try {
                draw();
                Thread.sleep(this.interval);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * This drawing graph part
     * 
     * @throws NotMatchGraphTypeException
     */
    public void draw() throws NotMatchGraphTypeException {
        //total memory
        long totalMemory = Runtime.getRuntime().totalMemory() / unit;
        //maximum maemory
        long maxMemory = Runtime.getRuntime().maxMemory() / unit;
        //usable memory
        long freeMemory = Runtime.getRuntime().freeMemory() / unit;
        //used memory
        long usedMemory = totalMemory - freeMemory;
        //element values array
        long[] memory = {usedMemory, freeMemory};
        //if selected area graph
        if(sel == GRAPH.AREA) {
            for(int i=0; i<this.ge1.getGraphElementMap().size(); i++) {
	        	GraphElement<Double, String, Double> ge = this.ge1.getGraphElement(this.elements[i]);
	        	List<Double> values = ge.getValues();
	        	if(values.size() > 300) {
	        		values.remove(0);
	        	}
	        	values.add((double)memory[i]);
	        	ge.setValues(values);
	        	this.ge1.setGraphElement(ge.getElementName(), ge);
            }
            this.ge1.setYIndex(new Double[] {(double)totalMemory, this.ge1.getMaximum()});
            this.gp.getGraph().setTitleFontSize(12);
            this.gp.getGraph().setTitle("JVM Memory TOTAL:"+totalMemory+" MB   FREE:"+freeMemory+" MB   USED:"+usedMemory+" MB");
            this.gp.repaint();  
        } else if(sel == GRAPH.LINE) {
            for(int i=0; i<this.ge2.getGraphElementMap().size(); i++) {
	        	GraphElement<Double, String, Double> ge = this.ge2.getGraphElement(this.elements[i]);
	        	List<Double> values = ge.getValues();
	        	if(values.size() > 300) {
	        		values.remove(0);
	        	}
	        	values.add((double)memory[i]);
	        	ge.setValues(values);
            }
            this.ge2.setYIndex(new Double[] {(double)totalMemory, this.ge2.getMaximum()});
            this.gp.getGraph().setTitleFontSize(12);
            this.gp.getGraph().setTitle("JVM Memory TOTAL:"+totalMemory+" MB   FREE:"+freeMemory+" MB   USED:"+usedMemory+" MB");
            this.gp.repaint();
        } else if(sel == GRAPH.BAR) {
            for(int i=0; i<this.ge3.getGraphElementMap().size(); i++) {
	        	GraphElement<Double, String, Double> ge = this.ge3.getGraphElement(this.elements[i]);
	        	List<Double> values = ge.getValues();
	        	if(values.size() > 30) {
	        		values.remove(0);
	        	}
	        	values.add((double)memory[i]);
	        	ge.setValues(values);
            }
            this.ge3.setYIndex(new Double[] {(double)totalMemory, this.ge3.getMaximum()}); 
            this.gp.getGraph().setTitleFontSize(12);
            this.gp.getGraph().setTitle("JVM Memory TOTAL:"+totalMemory+" MB   FREE:"+freeMemory+" MB   USED:"+usedMemory+" MB");
            this.gp.repaint();
        } else {
        	throw new NotMatchGraphTypeException();
        }
    }
    /**
     * Perform start button selection
     * @param e ActionEvent
     */
    public void jButton2_actionPerformed(ActionEvent e) {
        this.isThread = true;
        this.thr = new Thread(this);
        this.thr.start();
    }
    /**
     * Perform area graph  type selection
     * @param e ActionEvent
     */
    public void jButton4_actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
        try {
            this.sel = GRAPH.AREA;
            long limit = Runtime.getRuntime().maxMemory() / unit;
            this.gp = new GraphPanel<Double, String, Double>(this.sel, this.ge1, 600, 400);
            this.gp.getGraph().setGridStyle(GRID.DOT);
            this.gp.getGraph().setLimit((double)limit);
            this.gp.getGraph().setLeftIndent(80);
            this.gp.getGraph().setShowShadow(false);
            this.gp.getGraph().setUnit("MB");
            this.getContentPane().remove(2);
            this.getContentPane().add(this.gp, "Center");
            this.getContentPane().validate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Perform line type selection
     * @param e ActionEvent
     */
    public void jButton5_actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
        try {
            this.sel = GRAPH.LINE;
            float limit = Runtime.getRuntime().maxMemory() / unit;
            this.gp = new GraphPanel<Double, String, Double>(this.sel, this.ge2, 600, 400);
            this.gp.getGraph().setLimit(limit);
            this.gp.getGraph().setShowBg(false);
            this.gp.getGraph().setIndent(20, 20, 20, 20);            
           	this.getContentPane().remove(2);
            this.getContentPane().add(this.gp, "Center");
            this.getContentPane().validate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Perform bar type selection
     * @param e ActionEvent
     */
    public void jButton7_actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
        try {
            this.sel = GRAPH.BAR;
            float limit = Runtime.getRuntime().maxMemory() / unit;
            this.gp = new GraphPanel<Double, String, Double>(this.sel, this.ge3, 600, 400);
            this.gp.getGraph().setLimit(limit);
            this.gp.getGraph().setShowBg(false);
            this.gp.getGraph().setIndent(20, 20, 20, 20);            
           	this.getContentPane().remove(2);
            this.getContentPane().add(this.gp, "Center");
            this.getContentPane().validate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Perform poplulate object selection
     * @param e ActionEvent
     */
    public void jButton6_actionPerformed(ActionEvent e) {
    	if(!this.isThread) {
    		return;
    	}
    	new Thread(new Runnable() {
			@Override
			public void run() {
				ConcurrentLinkedQueue<String> buffer = new ConcurrentLinkedQueue<String>();
				try {
					for(int i=0; i<10000; i++) {
						buffer.offer(IntStream.range(0, 10000).mapToObj(a -> "buff data: "+a).collect(Collectors.joining()).toString());
					}
					buffer = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
    	}).start();
    }
    /**
     * Perform remove objects selection
     * @param e
     */
    public void jButton8_actionPerformed(ActionEvent e) {
    	System.gc();
    }
    /**
     * Perform stop selection
     * @param e ActionEvent
     */
    public void jButton1_actionPerformed(ActionEvent e) {
        this.isThread = false;
    }
    /**
     * Perform close selection
     * @param e ActionEvent
     */
    public void jButton3_actionPerformed(ActionEvent e) {
        this.isThread = false;
        System.exit(0);
    }
    /**
     * Main
     * @param args String[]
     * @throws UnsupportedLookAndFeelException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        new AWTGraphSimple2_Test();
    }
}

/**
 * Button adapter class
 */
class GraphExample2_Test_jButton2_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple2_Test adaptee;

    GraphExample2_Test_jButton2_actionAdapter(AWTGraphSimple2_Test adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton2_actionPerformed(e);
    }
}
/**
 * Button adapter class
 */
class GraphExample2_Test_jButton1_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple2_Test adaptee;

    GraphExample2_Test_jButton1_actionAdapter(AWTGraphSimple2_Test adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton1_actionPerformed(e);
    }
}
/**
 * Button adapter class
 */
class GraphExample2_Test_jButton3_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple2_Test adaptee;

    GraphExample2_Test_jButton3_actionAdapter(AWTGraphSimple2_Test adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton3_actionPerformed(e);
    }
}
/**
 * Button adapter class
 */
class GraphExample2_Test_jButton4_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple2_Test adaptee;

    GraphExample2_Test_jButton4_actionAdapter(AWTGraphSimple2_Test adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton4_actionPerformed(e);
    }
}
/**
 * Button adapter class
 */
class GraphExample2_Test_jButton5_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple2_Test adaptee;

    GraphExample2_Test_jButton5_actionAdapter(AWTGraphSimple2_Test adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton5_actionPerformed(e);
    }
}
/**
 * Button adapter class
 */
class GraphExample2_Test_jButton6_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple2_Test adaptee;

    GraphExample2_Test_jButton6_actionAdapter(AWTGraphSimple2_Test adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton6_actionPerformed(e);
    }
}
/**
 * Button adapter class
 */
class GraphExample2_Test_jButton8_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple2_Test adaptee;

    GraphExample2_Test_jButton8_actionAdapter(AWTGraphSimple2_Test adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton8_actionPerformed(e);
    }
}
/**
 * Button adapter class
 */
class GraphExample2_Test_jButton7_actionAdapter implements java.awt.event.ActionListener
{
    AWTGraphSimple2_Test adaptee;

    GraphExample2_Test_jButton7_actionAdapter(AWTGraphSimple2_Test adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton7_actionPerformed(e);
    }
}