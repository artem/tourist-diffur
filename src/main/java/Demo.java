import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.Timer;
import java.util.TimerTask;

public class Demo {
    private final static double[] xAges =
            new double[]{
                    60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81,
                    82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100
            };

    private final static double[] yPercentile75th =
            new double[]{
                    800000, 878736, 945583, 1004209, 1083964, 1156332, 1248041, 1340801, 1440138, 1550005,
                    1647728, 1705046, 1705032, 1710672, 1700847, 1683418, 1686522, 1674901, 1680456, 1679164,
                    1668514, 1672860, 1673988, 1646597, 1641842, 1653758, 1636317, 1620725, 1589985, 1586451,
                    1559507, 1544234, 1529700, 1507496, 1474907, 1422169, 1415079, 1346929, 1311689, 1256114,
                    1221034
            };

    private final static double[] xAges1 =
            new double[]{
                    60, 61, 62, 63, 64, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81,
                    82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97
            };

    private final static double[] yPercentile75th1 =
            new double[]{
                    800000, 1083964, 1156332, 1248041, 1340801, 1440138, 1550005,
                    1647728, 1705046, 1705032, 1710672, 1700847, 1683418, 1686522, 1674901, 1680456, 1679164,
                    1668514, 1672860, 1673988, 1646597, 1641842, 1653758, 1636317, 1620725, 1589985, 1586451,
                    1559507, 1544234, 1422169, 1415079, 1346929, 1311689, 1256114,
                    1221034
            };
    private final XYChart xyChart;

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
        timer.scheduleAtFixedRate(chartUpdaterTask, 2000, 500);
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
        chart.getStyler().setYAxisDecimalPattern("$ #,###.##");
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        // Series
        chart.addSeries("75th Percentile", xAges, yPercentile75th).setMarker(SeriesMarkers.NONE);

        return chart;
    }

    public void updateData() {
        xyChart.updateXYSeries("75th Percentile", xAges1, yPercentile75th1, null);
    }
}
