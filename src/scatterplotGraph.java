
import java.awt.*;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class scatterplotGraph {
    ChartPanel CP;
    JFreeChart scatterPlot;
    public scatterplotGraph() {

    }
    public void createScatterplot(List<List<String>> stuData, int[] selectedModulesIndex, List selectedModulesNames) {
        XYDataset dataset = createDataset(stuData, selectedModulesIndex, selectedModulesNames);
        scatterPlot = ChartFactory.createScatterPlot("Modules Student Marks", "X-Axis", "Y-Axis", dataset);
        boolean data = true;
        double[] coeffs = new double[0];
        try {
            coeffs = Regression.getOLSRegression(dataset, 0);
        } catch (Exception e) {
            data = false;
        }
        if (data) {
            LineFunction2D linefunction2d = new LineFunction2D(coeffs[0], coeffs[1]);
            XYDataset series2 = DatasetUtilities.sampleFunction2D(linefunction2d, 10, 100, 5, "Linear Regression Line");
            XYPlot plot = scatterPlot.getXYPlot();
            plot.setDataset(2, series2);
            XYLineAndShapeRenderer line = new XYLineAndShapeRenderer(true, false);
            line.setSeriesPaint(0, Color.green);
            plot.setRenderer(2, line);
            NumberAxis yaxis = new NumberAxis();
            yaxis.setAttributedLabel("Student Average Mark across All Modules");
            yaxis.setTickUnit(new NumberTickUnit(1));
            NumberAxis xaxis = new NumberAxis();
            xaxis.setAttributedLabel("Student Mark");
            xaxis.setTickUnit(new NumberTickUnit(5));
            plot.setRangeAxis(yaxis);
            plot.setDomainAxis(xaxis);
        }
        CP = new ChartPanel(scatterPlot);
        CP.setPreferredSize(new Dimension(900, 700));
    }
    public XYDataset createDataset(List<List<String>> stuData, int[] selectedModulesIndex, List selectedModulesNames) {
        int moduleCount = 0;
        XYSeries[] series = new XYSeries[selectedModulesIndex.length];
        XYSeriesCollection dataset = new XYSeriesCollection();
        int x;
        int average;
        for (int y : selectedModulesIndex) {
            series[moduleCount] = new XYSeries((String) selectedModulesNames.get(moduleCount));
            for (int i = 1; i < stuData.size() - 1; i++) {
                int total = 0;
                x = Integer.parseInt(stuData.get(i).get(y + 2));
                if (x != -1) {
                    for (int s = 2; s < stuData.get(i).size(); s++) {
                        total += Integer.parseInt(stuData.get(i).get(s));
                    }
                    average = total / (stuData.get(i).size() - 2);
                    series[moduleCount].add(Integer.parseInt(stuData.get(i).get(y + 2)), average);
                }
            }
            moduleCount++;
        }
        for (XYSeries s : series) {
            dataset.addSeries(s);
        }
        return dataset;
    }
}
