import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.internal.chartpart.Chart;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * A convenience class used to display a Chart in a barebones Swing application
 *
 * @author timmolter
 */
public class SwingWrapper<T extends Chart<?, ?>> {

    private final List<XChartPanel<T>> chartPanels = new ArrayList<>();
    private final DataMutator ctrl;
    private String windowTitle = "XChart";
    private boolean isCentered = true;
    private List<T> charts = new ArrayList<T>();
    private int numRows;
    private int numColumns;

    /**
     * Constructor
     *
     * @param chart
     * @param ctrl
     */
    public SwingWrapper(T chart, DataMutator ctrl) {
        this.ctrl = ctrl;
        this.charts.add(chart);
    }

    /**
     * Constructor - The number of rows and columns will be calculated automatically Constructor
     *
     * @param charts
     * @param ctrl
     */
    public SwingWrapper(List<T> charts, DataMutator ctrl) {
        this.charts = charts;

        this.numRows = (int) (Math.sqrt(charts.size()) + .5);
        this.numColumns = (int) ((double) charts.size() / this.numRows + 1);
        this.ctrl = ctrl;
    }

    /**
     * Constructor
     *
     * @param charts
     * @param numRows    - the number of rows
     * @param numColumns - the number of columns
     * @param ctrl
     */
    public SwingWrapper(List<T> charts, int numRows, int numColumns, DataMutator ctrl) {
        this.charts = charts;
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.ctrl = ctrl;
    }

    /**
     * Display the chart in a Swing JFrame
     */
    public JFrame displayChart() {
        // Create and set up the window.
        final JFrame frame = new JFrame(windowTitle);
        final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 365 * 3, 365);
        ctrl.setupSlider(slider);
        final JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        try {
            javax.swing.SwingUtilities.invokeAndWait(
                    () -> {
                        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        frame.add(ctrl.getControlPanel(), BorderLayout.WEST);

                        XChartPanel<T> chartPanel = new XChartPanel<T>(charts.get(0));
                        chartPanels.add(chartPanel);
                        rightPanel.add(chartPanel);
                        JLabel sliderLabel = new JLabel("Days to visualize", JLabel.CENTER);
                        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        rightPanel.add(sliderLabel);
                        rightPanel.add(slider);
                        frame.add(rightPanel);

                        // Display the window.
                        frame.pack();
                        if (isCentered) {
                            frame.setLocationRelativeTo(null);
                        }
                        frame.setVisible(true);
                    });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return frame;
    }

    /**
     * Display the chart in a Swing JFrame
     */
    public JFrame displayChartMatrix() {
        // Create and set up the window.
        final JFrame frame = new JFrame(windowTitle);

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(
                () -> {
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    frame.getContentPane().setLayout(new GridLayout(numRows, numColumns));

                    for (T chart : charts) {
                        if (chart != null) {
                            XChartPanel<T> chartPanel = new XChartPanel<T>(chart);
                            chartPanels.add(chartPanel);
                            frame.add(chartPanel);
                        } else {
                            JPanel chartPanel = new JPanel();
                            frame.getContentPane().add(chartPanel);
                        }
                    }

                    // Display the window.
                    frame.pack();
                    if (isCentered) {
                        frame.setLocationRelativeTo(null);
                    }
                    frame.setVisible(true);
                });

        return frame;
    }

    /**
     * Get the default XChartPanel. This is the only one for single panel chart displays and the first
     * panel in matrix chart displays
     *
     * @return the XChartPanel
     */
    public XChartPanel<T> getXChartPanel() {
        return getXChartPanel(0);
    }

    /**
     * Repaint the default XChartPanel. This is the only one for single panel chart displays and the
     * first panel in matrix chart displays
     */
    public void repaintChart() {
        repaintChart(0);
    }

    /**
     * Get the XChartPanel given the provided index.
     *
     * @param index
     * @return the XChartPanel
     */
    public XChartPanel<T> getXChartPanel(int index) {
        return chartPanels.get(index);
    }

    /**
     * Repaint the XChartPanel given the provided index.
     *
     * @param index
     */
    public void repaintChart(int index) {
        chartPanels.get(index).revalidate();
        chartPanels.get(index).repaint();
    }

    /**
     * Set the Window in the center of screen
     *
     * @param isCentered
     * @return
     */
    public SwingWrapper<T> isCentered(boolean isCentered) {
        this.isCentered = isCentered;
        return this;
    }

    /**
     * Set the Window Title
     *
     * @param windowTitle
     * @return
     */
    public SwingWrapper<T> setTitle(String windowTitle) {
        this.windowTitle = windowTitle;
        return this;
    }
}
