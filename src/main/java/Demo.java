import model.ModelBuilder;
import model.PointsContainer;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.*;

public class Demo {
    private final Random rand = new Random();
    private final XYChart xyChart;
    private final PointsContainer data;

    public Demo() {
        data = new ModelBuilder(5_000_000_000L, 1, 0.0005f, 1f, 0.5f).build();
        xyChart = getChart();
    }

    public static void main(String[] args) {
        Demo exampleChart = new Demo();
        exampleChart.run();
    }

    private void run() {
        final SwingWrapper<XYChart> swingWrapper = new SwingWrapper<>(xyChart);
        swingWrapper.displayChart();

        TimerTask chartUpdaterTask =
                new TimerTask() {
                    @Override
                    public void run() {
                        updateData();
                        javax.swing.SwingUtilities.invokeLater(swingWrapper::repaintChart);
                    }
                };

        Timer timer = new Timer();
        //timer.scheduleAtFixedRate(chartUpdaterTask, 0, 100);
    }

    public XYChart getChart() {
        // Create Chart
        XYChart chart =
                new XYChartBuilder()
                        .width(800)
                        .height(600)
                        .title(getClass().getSimpleName())
                        .xAxisTitle("Duration")
                        .yAxisTitle("People")
                        .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
        //chart.getStyler().setYAxisDecimalPattern("$ #,###.##");
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        // Series
        chart.addSeries("Variable I", data.getX(), data.getI()).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Variable S", data.getX(), data.getS()).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Variable R", data.getX(), data.getR()).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Variable D", data.getX(), data.getD()).setMarker(SeriesMarkers.NONE);

        return chart;
    }

    public void updateData() {
        /*
        xAges.add(counter++);
        yPercentile75th.add(Math.abs(rand.nextInt()) % 42069);
        if (xAges.size() > 20) {
            xAges.remove(0);
            yPercentile75th.remove(0);
        }
        xyChart.updateXYSeries("75th Percentile", xAges, yPercentile75th, null);
         */
    }
}
