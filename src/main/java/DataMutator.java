import model.ModelBuilder;
import model.PointsContainer;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;

public class DataMutator {
    private final JPanel controlPanel = new JPanel();
    private final XYChart xyChart;
    private final ModelBuilder modelBuilder;
    private SwingWrapper<?> parent;

    public DataMutator(XYChart xyChart, ModelBuilder modelBuilder) {
        this.xyChart = xyChart;
        this.modelBuilder = modelBuilder;
        composePanel();
    }

    public void setParent(SwingWrapper<?> parent) {
        this.parent = parent;
    }

    public void setupSlider(JSlider slider) {
        slider.addChangeListener(e -> {
            modelBuilder.setTotalDays(slider.getValue());
            redraw();
        });
        slider.setMajorTickSpacing(60);
        slider.setMinorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
    }

    private void composePanel() {
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));

        SpinnerNumberModel nModel = new SpinnerNumberModel(Long.valueOf(70_000L), Long.valueOf(1), Long.valueOf(18_000_000_000L), Long.valueOf(100));

        JSpinner spinN = new JSpinner(nModel);
        spinN.addChangeListener(e -> {
            modelBuilder.setPeopleAmount((Long) spinN.getValue());
        });
        //hack
        spinN.setMaximumSize(spinN.getPreferredSize());
        JLabel popLabel = new JLabel("Population");
        popLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlPanel.add(popLabel);
        controlPanel.add(spinN);

        String cont = "a 0 0.00001\nb 0 0.1\nm 0 0.01";
        // hack
        modelBuilder.parse(cont);
        updateData();
        JTextArea textArea = new JTextArea(cont);
        JScrollPane jsp = new JScrollPane(textArea);
        JLabel parLabel = new JLabel("Parameters");
        parLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlPanel.add(parLabel);
        controlPanel.add(jsp);

        JLabel result = new JLabel("");
        result.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btn = new JButton("Parse!");
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(e -> {
            String value = textArea.getText();
            boolean res = modelBuilder.parse(value);
            if (res) {
                result.setText("Success!");
                redraw();
            } else {
                result.setText("Invalid input!");
            }
        });
        controlPanel.add(btn);
        controlPanel.add(result);
    }

    private void redraw() {
        updateData();
        javax.swing.SwingUtilities.invokeLater(parent::repaintChart);
    }

    public void updateData() {
        PointsContainer data = modelBuilder.build();
        xyChart.updateXYSeries("I (infectious)", null, data.getI(), null);
        xyChart.updateXYSeries("S (susceptible)", null, data.getS(), null);
        xyChart.updateXYSeries("R (recovered)", null, data.getR(), null);
        xyChart.updateXYSeries("D (deceased)", null, data.getD(), null);
    }

    public JPanel getControlPanel() {
        return controlPanel;
    }
}
