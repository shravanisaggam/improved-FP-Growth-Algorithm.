package com;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import org.jfree.chart.ChartUtilities;
public class Chart extends ApplicationFrame{
	static long ptime,ntime,fptime;
	int count;
	static String title;
public Chart(String paramString,long p,long n,long f,int c){
	super(paramString);
	ptime = p;
	ntime = n;
	fptime = f;
	count = c;
	Time.setTime(ptime,ntime,fptime,count);
	ptime = Time.ptime;
	ntime = Time.ntime;
	fptime = Time.fptime;
	Main.l2.setText("<html><body<center>Not Painting Execution Time : "+ntime+"<br/>Painting Execution Time : "+ptime+"<br/>FP Growth Execution Time : "+fptime);
	JPanel localJPanel = createDemoPanel();
    localJPanel.setPreferredSize(new Dimension(800, 370));
    JScrollPane jsp = new JScrollPane(localJPanel);
	setContentPane(localJPanel);
}
private static CategoryDataset createDataset(){
	DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
	localDefaultCategoryDataset.addValue(ptime,"Painting Growth","Painting Growth");
	localDefaultCategoryDataset.addValue(ntime,"Not Painting Growth","Not Painting Growth");
	localDefaultCategoryDataset.addValue(fptime,"FP Growth","FP Growth");
	return localDefaultCategoryDataset;
}
public void windowClosing(WindowEvent we){
	this.setVisible(false);
}
private static JFreeChart createChart(CategoryDataset paramCategoryDataset){
	JFreeChart localJFreeChart = ChartFactory.createBarChart(title,"Algorithm Name", "Execuion Time (M.S)", paramCategoryDataset, PlotOrientation.VERTICAL, true, true, false);
    CategoryPlot localCategoryPlot = (CategoryPlot)localJFreeChart.getPlot();
    localCategoryPlot.setDomainGridlinesVisible(true);
    localCategoryPlot.setRangeCrosshairVisible(true);
    localCategoryPlot.setRangeCrosshairPaint(Color.blue);
    NumberAxis localNumberAxis = (NumberAxis)localCategoryPlot.getRangeAxis();
    localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    BarRenderer localBarRenderer = (BarRenderer)localCategoryPlot.getRenderer();
    localBarRenderer.setDrawBarOutline(false);
    GradientPaint localGradientPaint1 = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F, new Color(0, 0, 64));
    GradientPaint localGradientPaint2 = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F, new Color(0, 64, 0));
    GradientPaint localGradientPaint3 = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F, new Color(64, 0, 0));
    localBarRenderer.setSeriesPaint(0, localGradientPaint1);
    localBarRenderer.setSeriesPaint(1, localGradientPaint2);
    localBarRenderer.setSeriesPaint(2, localGradientPaint3);
    localBarRenderer.setLegendItemToolTipGenerator(new StandardCategorySeriesLabelGenerator("Tooltip: {0}"));
    CategoryAxis localCategoryAxis = localCategoryPlot.getDomainAxis();
    localCategoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.5235987755982988D));
    return localJFreeChart;
}
public static JPanel createDemoPanel(){
	JFreeChart localJFreeChart = createChart(createDataset());
	return new ChartPanel(localJFreeChart);
}
}