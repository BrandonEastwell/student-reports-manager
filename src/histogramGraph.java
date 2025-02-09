
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class histogramGraph {
    String xaxis;
    String yaxis;
    ChartPanel CP;
    JFreeChart histogram;

    public histogramGraph() {
    }
    public void createHistogram(List<List<String>> stuData, int[] selectedModulesIndex, List selectedModulesNames) {
        //creates a histogram object representing selected modules data
        xaxis = "student marks";
        yaxis = "students";
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        int students = 0;
        int moduleCount = 0;
        double[][] histogramRecords = new double[selectedModulesIndex.length][];
        //iterate through each selected module
        for (int y : selectedModulesIndex) {
            int x;
            List<Double> histogramValuesList = new ArrayList<Double>();
            //iterates through each student mark in a selected module
            for (int i = 1; i < stuData.size() - 1; i++) {
                x = Integer.parseInt(stuData.get(i).get(y + 2));

                if (x != -1) { //-1 = did not take module
                    students++;
                    histogramValuesList.add((double) x);

                }
            }
            double[] histogramRecord = new double[histogramValuesList.size()];
            for (int c = 0; c < histogramValuesList.size(); c++) {
                histogramRecord[c] = histogramValuesList.get(c);
            }
            histogramRecords[moduleCount] = histogramRecord;
            moduleCount++;
        }
        HistogramDataset dataset = new HistogramDataset();
        for (int i = 0; i < histogramRecords.length; i++) {
            dataset.addSeries((Comparable) selectedModulesNames.get(i), histogramRecords[i], 5, 0, 100);
        }
        String plotTitle = "Student Performance out of " + students + " students";
        histogram = ChartFactory.createHistogram(plotTitle, xaxis, yaxis,
                dataset, orientation, true, true, false);
        XYPlot xyplot = (XYPlot) histogram.getPlot();
        xyplot.setForegroundAlpha(0.40F);
        XYBarRenderer xybarrenderer = (XYBarRenderer) xyplot.getRenderer();
        xybarrenderer.setBarPainter(new StandardXYBarPainter());
        NumberAxis yaxis = new NumberAxis();
        yaxis.setTickUnit(new NumberTickUnit(1));
        yaxis.setAttributedLabel("Students");
        NumberAxis xaxis = new NumberAxis();
        xaxis.setAttributedLabel("Student Mark");
        xaxis.setTickUnit(new NumberTickUnit(5));
        xyplot.setRangeAxis(yaxis);
        xyplot.setDomainAxis(xaxis);
        CP = new ChartPanel(histogram);
        CP.setPreferredSize(new Dimension(900, 700));
    }
}
