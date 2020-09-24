# ChaosGraph API Ver 2.0.0


## 소개
이 컨포넌트는 그래프 차트를 그려주는 API입니다.
구성은 영역 그래프, 막대그래프, 막대 비율 그래프, 원그래프, 라이그래프로 버전 1.0에서 막대 배율 그래프가 추가된 5가지 입니다.
버전 1.0과 달라진 부분은 1.0에서 차트 요소와 값을 배열로 처리했지만 2.0에서는 객체로 요소와 값을 설정하도록 하였습니다.
그외에 차트 그래프의 상속구조와 추상화 레벨이 변경되었으며 각종 유틸리티 메서드와 기능이 추가되었습니다.


## 추가 기능 상세
- Bar Ratio Graph 추가
- 기존 1.0에서 AWT/Swing만 가능에서 SWT(Native Widget)으로 랜더링 기능 추가
- Graph 선택시 팝업(Popup)으로 요소 값을 전시기능 추가
- 라벨선택 시 Graph 선택되도록 기능 추가
- 마우스 좌우 클릭시 Graph 요소 토글(Toggle) 기능
- 마우스 휠로 Graph 사이즈 조정
- Graph 객체에 이벤트를 추가하여 요소값이나 기타 정보를 수신 가능
- Graph와 요소의 외곽선(Border) 요과 추가
- Graph를 AWT/Swing 컴포넌트로 생성사용하는 GraphPanel 추가
- Graph를 SWT 캔버스 컴포넌트로 생성사용하는 GraphCanvas 추가


## 개선 기능 상세
- Graph 요소를 객체 형태로 처리 하도록 개선
- Graph 객체를 선택 및 도시 가시성 기능 개선
- Graph 확대 축소시 컴포넌트 크기 재조정 기능 개선
- Graphics 객체의 Drawing 구조 개선


## 추상구조
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


## 코딩 방법
1. [GRAPH_HOME]/build/libs/chaosgraph-2.0.0.jar를 classpath에 추가 한다.
2. x축 y축 인덱스를 생성한다.
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
3. x, y 인텍스로 GraphElements 객체를 생성한다.
	GraphElements graphElements = new GraphElements(GRAPH.AREA, xIndex, yIndex);
4. GraphElement(요소 객체) 객체를 생생하여 GraphElements에 추가한다.
	double[] values = {1024d*1024d*45d, 1024d*1024d*55d, 1024d*1024d*3d, 1024d*1024d*66d, 1024d*1024d*33d, 1024d*1024d*6d, 1024d*1024d*9d, 1024d*1024d*600d, 1024d*1024d* 500d, 1024d*1024d*980d, 1024d*1024d*103d, 1024d*1024d*666d}
	GraphElement ge = new GraphElement("Kafka", Color.GRAY, values);
	graphElements.add(ge);
	...
5. 기 생성된 GraphElements 객체로 GraphPanel를 생성한다.
	//가로 600 세로 400의 Area 그래프 패널
	GraphPanel gpArea = new GraphPanel(GRAPH.AREA, graphElements, 600, 400);
6. GraphPanel에서 Graph객체를 얻어 디자인에 맞는 설정을 수행한다.
	AbstractGraph graph = (AreaGraph)gpArea.getGraph();
	graph.setTitle("This is simple area graph.");
	graph.setShowShadow(false);
	graph.setGridStyle(GRID.DOT);
	graph.setLimit(1000);
	graph.setPopupStyle(POPUP_STYLE.ROUND);
	graph.setSelectionEnable(true);
	graph.setSelectionBorder(SELECTION_BORDER.DOT);
	graph.setShowGraphXY(false);
6. Java의 패널이나 프레임 객체에 GraphPanel를 추가한다.
	getContentPane().add(gpArea , BorderLayout.CENTER);
	getContentPane().validate();
	gpArea .repaint();
 

## 예제
예제는 AWT/Swing으로된 2가지와 SWT로된 1가지가 있습니다.

### AWTGraphSimple1
"그래프 뷰어 및 파일저장" 프로그램으로 5가지 종류의 그래프를 패널에 도시하고
도시된 이미지를 파일로 저장시켜주는 프로그램 입니다. 
(구체적인 내용은 org.chaostocosmos.chaosgraph.awt2d.AWTGraphSimple1.java를 참조)

### AWTGraphSimple2
"JVM 메모리 모니터"로 실시간으로 메모리 사용랑을 다양한 그래프 차트로 전시하는 예제입니다.
(구체적인 내용은 org.chaostocosmos.chaosgraph.awt2d.AWTGraphSimple2.java를 참조)

### SWTGraphSimple
AWTGraphSimple 예제를 SWT(Standard Widget Toolkit)으로 컨버팅한 예제 입니다.
(구체적인 내용은 org.chaostocosmos.chaosgraph.swt2d.SWTGraphSimple.java를 참조)


Developed by 9ins. 
Mail: chaos930@gmail.com