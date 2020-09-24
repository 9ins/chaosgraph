# ChaosGraph API Ver 2.0.0


## �Ұ�
�� ������Ʈ�� �׷��� ��Ʈ�� �׷��ִ� API�Դϴ�.
������ ���� �׷���, ����׷���, ���� ���� �׷���, ���׷���, ���̱׷����� ���� 1.0���� ���� ���� �׷����� �߰��� 5���� �Դϴ�.
���� 1.0�� �޶��� �κ��� 1.0���� ��Ʈ ��ҿ� ���� �迭�� ó�������� 2.0������ ��ü�� ��ҿ� ���� �����ϵ��� �Ͽ����ϴ�.
�׿ܿ� ��Ʈ �׷����� ��ӱ����� �߻�ȭ ������ ����Ǿ����� ���� ��ƿ��Ƽ �޼���� ����� �߰��Ǿ����ϴ�.


## �߰� ��� ��
- Bar Ratio Graph �߰�
- ���� 1.0���� AWT/Swing�� ���ɿ��� SWT(Native Widget)���� ������ ��� �߰�
- Graph ���ý� �˾�(Popup)���� ��� ���� ���ñ�� �߰�
- �󺧼��� �� Graph ���õǵ��� ��� �߰�
- ���콺 �¿� Ŭ���� Graph ��� ���(Toggle) ���
- ���콺 �ٷ� Graph ������ ����
- Graph ��ü�� �̺�Ʈ�� �߰��Ͽ� ��Ұ��̳� ��Ÿ ������ ���� ����
- Graph�� ����� �ܰ���(Border) ��� �߰�
- Graph�� AWT/Swing ������Ʈ�� ��������ϴ� GraphPanel �߰�
- Graph�� SWT ĵ���� ������Ʈ�� ��������ϴ� GraphCanvas �߰�


## ���� ��� ��
- Graph ��Ҹ� ��ü ���·� ó�� �ϵ��� ����
- Graph ��ü�� ���� �� ���� ���ü� ��� ����
- Graph Ȯ�� ��ҽ� ������Ʈ ũ�� ������ ��� ����
- Graphics ��ü�� Drawing ���� ����


## �߻���
IGraph
   |
Graph
   |
AbstractGraph
   |----------------------|----------------------|-------------------|-------------------|
AreaGraph	BarGraph		BarRatioGraph	CircleGraph	LineGraph   ->   GraphPanel
   +
   |
GraphElements <- GraphElement


## �ڵ� ���
1. [GRAPH_HOME]/build/libs/chaosgraph-2.0.0.jar�� classpath�� �߰� �Ѵ�.
2. x�� y�� �ε����� �����Ѵ�.
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
3. x, y ���ؽ��� GraphElements ��ü�� �����Ѵ�.
	GraphElements graphElements = new GraphElements(GRAPH.AREA, xIndex, yIndex);
4. GraphElement(��� ��ü) ��ü�� �����Ͽ� GraphElements�� �߰��Ѵ�.
	double[] values = {1024d*1024d*45d, 1024d*1024d*55d, 1024d*1024d*3d, 1024d*1024d*66d, 1024d*1024d*33d, 1024d*1024d*6d, 1024d*1024d*9d, 1024d*1024d*600d, 1024d*1024d* 500d, 1024d*1024d*980d, 1024d*1024d*103d, 1024d*1024d*666d}
	GraphElement ge = new GraphElement("Kafka", Color.GRAY, values);
	graphElements.add(ge);
	...
5. �� ������ GraphElements ��ü�� GraphPanel�� �����Ѵ�.
	//���� 600 ���� 400�� Area �׷��� �г�
	GraphPanel gpArea = new GraphPanel(GRAPH.AREA, graphElements, 600, 400);
6. GraphPanel���� Graph��ü�� ��� �����ο� �´� ������ �����Ѵ�.
	AbstractGraph graph = (AreaGraph)gpArea.getGraph();
	graph.setTitle("This is simple area graph.");
	graph.setShowShadow(false);
	graph.setGridStyle(GRID.DOT);
	graph.setLimit(1000);
	graph.setPopupStyle(POPUP_STYLE.ROUND);
	graph.setSelectionEnable(true);
	graph.setSelectionBorder(SELECTION_BORDER.DOT);
	graph.setShowGraphXY(false);
6. Java�� �г��̳� ������ ��ü�� GraphPanel�� �߰��Ѵ�.
	getContentPane().add(gpArea , BorderLayout.CENTER);
	getContentPane().validate();
	gpArea .repaint();
 

## ����
������ AWT/Swing���ε� 2������ SWT�ε� 1������ �ֽ��ϴ�.

### AWTGraphSimple1
"�׷��� ��� �� ��������" ���α׷����� 5���� ������ �׷����� �гο� �����ϰ�
���õ� �̹����� ���Ϸ� ��������ִ� ���α׷� �Դϴ�. 
(��ü���� ������ org.chaostocosmos.chaosgraph.awt2d.AWTGraphSimple1.java�� ����)

### AWTGraphSimple2
"JVM �޸� �����"�� �ǽð����� �޸� ������ �پ��� �׷��� ��Ʈ�� �����ϴ� �����Դϴ�.
(��ü���� ������ org.chaostocosmos.chaosgraph.awt2d.AWTGraphSimple2.java�� ����)

### SWTGraphSimple
AWTGraphSimple ������ SWT(Standard Widget Toolkit)���� �������� ���� �Դϴ�.
(��ü���� ������ org.chaostocosmos.chaosgraph.swt2d.SWTGraphSimple.java�� ����)


Developed by 9ins. 
Mail: chaos930@gmail.com