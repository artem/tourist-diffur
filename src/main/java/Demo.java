import model.ModelBuilder;
import model.PointsContainer;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

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
        final SwingWrapper<XYChart> swingWrapper = new SwingWrapper<>(xyChart, ctrl).setTitle("SIRD");
        ctrl.setParent(swingWrapper);
        swingWrapper.displayChart();
    }

    public XYChart getChart(ModelBuilder modelBuilder) {
        // Create Chart
        XYChart chart =
                new XYChartBuilder()
                        .title("Infection spread")
                        .xAxisTitle("Time (in days)")
                        .yAxisTitle("Population")
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
        chart.addSeries("I (infectious)", data.getX(), data.getI()).setMarker(SeriesMarkers.NONE);
        chart.addSeries("S (susceptible)", data.getX(), data.getS()).setMarker(SeriesMarkers.NONE);
        chart.addSeries("R (recovered)", data.getX(), data.getR()).setMarker(SeriesMarkers.NONE);
        chart.addSeries("D (deceased)", data.getX(), data.getD()).setMarker(SeriesMarkers.NONE);

        return chart;
    }


}
