import model.ModelBuilder;
import model.PointsContainer;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.*;

public class Demo {
    private final XYChart xyChart;

    public Demo(ModelBuilder modelBuilder) {
        xyChart = getChart(modelBuilder);
    }

    public static void main(String[] args) {
        ModelBuilder modelBuilder = new ModelBuilder(70_000L);
        Demo exampleChart = new Demo(modelBuilder);
        exampleChart.run(modelBuilder);
    }

    private void run(ModelBuilder data) {
        final DataMutator ctrl = new DataMutator(xyChart, data);
        final SwingWrapper<XYChart> swingWrapper = new SwingWrapper<>(xyChart, ctrl).setTitle("Tourist-diffur");
        swingWrapper.displayChart();

        TimerTask chartUpdaterTask =
                new TimerTask() {
                    @Override
                    public void run() {
                        ctrl.updateData();
                        javax.swing.SwingUtilities.invokeLater(swingWrapper::repaintChart);
                    }
                };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(chartUpdaterTask, 0, 1000);
    }

    public XYChart getChart(ModelBuilder modelBuilder) {
        // Create Chart
        XYChart chart =
                new XYChartBuilder()
                        .title("Визуализация заболевших")
                        .xAxisTitle("Продолжительность")
                        .yAxisTitle("Население")
                        .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
        //chart.getStyler().setYAxisDecimalPattern("$ #,###.##");
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        PointsContainer data = modelBuilder.build();

        // Series
        chart.addSeries("Variable I", data.getX(), data.getI()).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Variable S", data.getX(), data.getS()).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Variable R", data.getX(), data.getR()).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Variable D", data.getX(), data.getD()).setMarker(SeriesMarkers.NONE);

        return chart;
    }


}
