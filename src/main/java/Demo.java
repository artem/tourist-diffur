import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.*;

public class Demo {
    private final List<Integer> xAges = new ArrayList<>();
    private final List<Integer> yPercentile75th = new ArrayList<>();
    private final Random rand = new Random();
    private final XYChart xyChart;
    private int counter;

    public Demo() {
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
        timer.scheduleAtFixedRate(chartUpdaterTask, 0, 100);
    }

    public XYChart getChart() {
        // Create Chart
        XYChart chart =
                new XYChartBuilder()
                        .width(800)
                        .height(600)
                        .title(getClass().getSimpleName())
                        .xAxisTitle("Age")
                        .yAxisTitle("Amount")
                        .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
        //chart.getStyler().setYAxisDecimalPattern("$ #,###.##");
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        xAges.add(counter++);
        yPercentile75th.add(3234);
        // Series
        chart.addSeries("75th Percentile", xAges, yPercentile75th).setMarker(SeriesMarkers.NONE);

        return chart;
    }

    public void updateData() {
        xAges.add(counter++);
        yPercentile75th.add(Math.abs(rand.nextInt()) % 42069);
        if (xAges.size() > 20) {
            xAges.remove(0);
            yPercentile75th.remove(0);
        }
        xyChart.updateXYSeries("75th Percentile", xAges, yPercentile75th, null);
    }
}
