package com;
import javax.swing.JFrame;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.DirectedSubgraph;
import org.jgrapht.GraphPath;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import org.jgraph.graph.CellView;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphLayoutCache;
public class Graph extends JFrame{
	JGraph graph;
	JScrollPane jsp;
	JPanel p1,p2;
	JGraphModelAdapter adapter;
	int num;
	int xpos = 50;
	int ypos = 40;
	ListenableDirectedMultigraph directed;
	HashMap<String,HashMap<String,Integer>> map;
public Graph(HashMap<String,HashMap<String,Integer>> m){
	setTitle("Social Network Neighborhood Graph");
	map = m;
	directed = new ListenableDirectedMultigraph(DefaultEdge.class);
    adapter = new JGraphModelAdapter(directed);
    graph = new JGraph(adapter);
    adjustDisplaySettings();
	p1 = new JPanel();
	p1.setPreferredSize(new Dimension(600,(map.size()*150)));
	p1.setLayout(new BorderLayout());
	p1.add(graph,BorderLayout.CENTER);
	jsp = new JScrollPane(p1);
	getContentPane().add(jsp,BorderLayout.CENTER);
}
public void createTree(){
	int j = 0;
	for(Map.Entry<String,HashMap<String,Integer>> me : map.entrySet()){
		String key = me.getKey();
		directed.addVertex(key);
		positionVertexAt(key,xpos,ypos);
		xpos = xpos + 100;
		j = j + 1;
		if((j % 1) == 0)
			ypos = ypos + 30;
		else
			ypos = ypos - 30;
		if(j == 3){
			xpos = 50;
			ypos = ypos + 150;
			j = 0;
		}
	}
	try{
	for(Map.Entry<String,HashMap<String,Integer>> me : map.entrySet()){
		String key = me.getKey();
		HashMap<String,Integer> value = me.getValue();
		for(Map.Entry<String,Integer> temp : value.entrySet()){
			//System.out.println(key+" "+temp.getKey()+"=====");
			directed.addEdge(key.trim(),temp.getKey().trim());
		}
	}
	}catch(NullPointerException e){}
}
public void removeEdgeLabels(){
	GraphLayoutCache cache = graph.getGraphLayoutCache();
	CellView[] cells = cache.getCellViews();
	for (CellView cell : cells) {
		if (cell instanceof EdgeView) {
			EdgeView ev = (EdgeView) cell;
			DefaultEdge eval = (DefaultEdge) ev.getCell();
			eval.setUserObject("");
		}
	}
	cache.reload();
	graph.repaint();
}
private void positionVertexAt(Object name, int x, int y){
	DefaultGraphCell localDefaultGraphCell = adapter.getVertexCell(name);
	AttributeMap localAttributeMap1 = localDefaultGraphCell.getAttributes();
    Rectangle2D localRectangle2D = GraphConstants.getBounds(localAttributeMap1);
    Rectangle2D.Double localDouble = new Rectangle2D.Double(x, y, 40, localRectangle2D.getHeight());
    GraphConstants.setBounds(localAttributeMap1, localDouble);
	GraphConstants.setBackground(localAttributeMap1,Color.blue);
	GraphConstants.setForeground(localAttributeMap1,Color.white);
    AttributeMap localAttributeMap2 = new AttributeMap();
    localAttributeMap2.put(localDefaultGraphCell, localAttributeMap1);
    adapter.edit(localAttributeMap2, null, null, null);
}
public void adjustDisplaySettings(){
	graph.setPreferredSize(new Dimension(530, 600));
    Color color = Color.black;
    graph.setBackground(color);
}
private static class ListenableDirectedMultigraph<V, E> extends DefaultListenableGraph<V, E> implements DirectedGraph<V, E>{
	private static final long serialVersionUID = 1L;
ListenableDirectedMultigraph(Class<E> paramClass){
	super(new DirectedMultigraph(paramClass));
}
}
}
