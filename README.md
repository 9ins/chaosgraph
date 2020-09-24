# <b>chaosgraph v2.0</b>
======================
## <b>Introduction</b>
This library contribute to make chart on AWT/Swing/SWT components.
It can be used for AWT/Swing application and Eclipse SWT(Standard Widget Tookit) application also if you would try to build a service of RAP dashboard web application, This library will provide great UX experience and excellent interective functionality to the service for users.  
<br>

## <b>What is changed and improved?</b>
There is 5 type of chart be supported in this v2.0.0 version.  
Area/Bar/BarRatio/Circle/line chart is currently supported for public user.
This version of the artifacts is quit changed then old version 1.x that was not suppoted interactive event, just only show chart shapes and handling mutil-dimension array to easily cause error.  
Current 2.0.0 version is providing more object-oriented, more interactive and more good-looked graphical methods and functions.
<br>  
<br>
## <b> Specification of additionals and improved</b>
### <b> Additional functionality </b>
* Adding BarRatioGraph  
* SWT(Native Widget) rendering object is provided now in constrast old version. therefore the current version can be used to SWT, Eclipse appplications and RAP web applications.  
* To add function that showing popup windows in chart if When graph be selected by mouse pointer.  
* adding function that a graph be selected when if user select the label of chart element on label placed.
* Whenever user click mouse right or left button, to toggle chart element to circulate clockwise or counter-clokwise
* To spin mouse wheel, to resize chart
* Because chart(Graph) object can be added event listener, Chart engineer can develop for more interactive service to users.
* Adding chart and label border effect. e.g. Showing line or dot style, transpart contrast background on label.
* Adding GraphPanel class that a chart would be drawn in AWT/Swing area.
* Adding GraphCanvas class that a chart would be drawn in SWT/Eclipse area.
### <b> Improved functionality </b>
* Improving that the chart's abstractness and object-oriented concept is to be more resonable and more efficent.
* To make chart's graphical effiect more beautiful.
* When the chart's size to be resize, To make chart's inner element to resize at same ratio.
* Improving chart drawing/Rendering structure.
<br>
<br>
## <b>Structure of chart objects</b>
![UMLDiagram1](./pic/UMLDiagram1.PNG)
<br>
<br>
## <b> How to code </b>  
1. You need JRE over 1.8 or more.
2. Add [GRAPH_HOME]/build/libs/chaosgraph-2.0.0.jar to classpath on your project.
3. Create indexes of x, y axis.  
```
	List<Object> xIndex = new ArrayList<Object>();  
	List<Double> yIndex = new ArrayList<Double>();  
	xIndex.add("Jan");  
	xIndex.add("Feb");  
	xIndex.add("Mar");  
	xIndex.add("Apr");  
	xIndex.add("May");  
	xIndex.add("Jun");  
	xIndex.add("Jul");  
	xIndex.add("Aug");  
	xIndex.add("Seb");  
	xIndex.add("Oct");  
	xIndex.add("Nov");  
	xIndex.add("Dec");  
	yIndex.add(1024d*1024d*100d);  
	yIndex.add(1024d*1024d*500d);  
	yIndex.add(1024d*1024d*1000d);  
	yIndex.add(1024d*1024d*5000d);  
```
4. Create GraphElements object with x, y indexes.  
```
>   GraphElements graphElements = new GraphElements(GRAPH.AREA, xIndex, yIndex);
```
5. Add GraphElement objects being created to GraphElements object created above.
```
	double[] values = {1024d*1024d*45d, 1024d*1024d*55d, 1024d*1024d*3d, 1024d*1024d*66d, 1024d*1024d*33d, 1024d*1024d*6d, 1024d*1024d*9d, 1024d*1024d*600d, 1024d*1024d* 500d, 1024d*1024d*980d, 1024d*1024d*103d, 1024d*1024d*666d}
	GraphElement ge = new GraphElement("Kafka", Color.GRAY, values);
	graphElements.add(ge);
    .... (could be added more elements)
```
6. Create GraphPanel object with pre-created GraphElements object.
```
	//Area graph type with width 600 pixel, height 400 pixel
	GraphPanel gpArea = new GraphPanel(GRAPH.AREA, graphElements, 600, 400);
```
7. To setting proper or wanted option at Graph object retrived from GraphPanel object.
```
	AbstractGraph graph = (AreaGraph)gpArea.getGraph();
	graph.setTitle("This is simple area graph.");
	graph.setShowShadow(false);
	graph.setGridStyle(GRID.DOT);
	graph.setLimit(1000);
	graph.setPopupStyle(POPUP_STYLE.ROUND);
	graph.setSelectionEnable(true);
	graph.setSelectionBorder(SELECTION_BORDER.DOT);
	graph.setShowGraphXY(false);
```
8. Add GraphPanel object to be required target UI component.
```
	getContentPane().add(gpArea , BorderLayout.CENTER);
	getContentPane().validate();
	gpArea .repaint();
```

## <b>Example snippet</b>
This project contains 3 example of the library.  
<br>
> ### AWTGraphSimple1.java
> This example is chart viewer. 
> To select the chart be wanted, then draw the chart on window.  
> You can output chart image to file with specific image format. e.g. PNG, JPG, TIFF, BMP  
> Location : [PROJECT-PATH]/src/main/java/org/chaostocosmos/chaosgraph/awt2d/AWTGraphSimple1.java  

<br>

> ### AWTGraphSimple2.java
> This example is JVM memory monitor.  This show that current memory useage of this application on real-time.  
> You can see memory status with some chart types. Also as pushing 'populate object' button, you will see more dynamic progress of memory status.
> Location : [PROJECT-PATH]/src/main/java/org/chaostocosmos/chaosgraph/awt2d/AWTGraphSimple2.java  

<br>

> ### SWTGraphSimple.java
> This example is converting SWT version of AWTGraphSimple1 above.  
> Location : [PROJECT-PATH]/src/main/java/org/chaostocosmos/chaosgraph/swt2d/SWTGraphSimple.java

<br>
<br>

## <b> Image of Chart </b>

## Area graph for AWT/Swing
![screenshot_20200923-01](./pic/AREA.png)  

<br>

## Bar graph for AWT/Swing
![screenshot_20200923-02](./pic/BAR.png)  

<br>

## Bar ratio graph for AWT/Swing
![screenshot_20200923-03](./pic/BAR_RATIO.png)  

<br>

## Circle graph for AWT/Swing
![screenshot_20200923-04](./pic/CIRCLE.png)  

<br>

## Line graph for AWT/Swing
![screenshot_20200923-05](./pic/LINE.png)  

<br>

## JVM Memory viewer(Movie)
[![IMAGE ALT TEXT](./pic/JVM_Memory_Viewer.png)](./pic/JVM_Memory_Viewer.mp4 "JVM Memory")  

