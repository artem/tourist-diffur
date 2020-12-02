import model.ModelBuilder;
import model.PointsContainer;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;

public class DataMutator {
    private final JPanel controlPanel = new JPanel();
    private final XYChart xyChart;
    private final ModelBuilder modelBuilder;

    public DataMutator(XYChart xyChart, ModelBuilder modelBuilder) {
        this.xyChart = xyChart;
        this.modelBuilder = modelBuilder;
        composePanel();
    }

    private void composePanel() {
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;

        SpinnerNumberModel nModel = new SpinnerNumberModel(Long.valueOf(70_000L), Long.valueOf(1), Long.valueOf(18_000_000_000L), Long.valueOf(100));

        JSpinner spinN = new JSpinner(nModel);
        spinN.addChangeListener(e -> {
            modelBuilder.setPeopleAmount((Long) spinN.getValue());
        });
        controlPanel.add(new JLabel("Население:"));
        controlPanel.add(spinN, constraints);
    }

    public void updateData() {
        PointsContainer data = modelBuilder.build();
        xyChart.updateXYSeries("Variable I", null, data.getI(), null);
        xyChart.updateXYSeries("Variable S", null, data.getS(), null);
        xyChart.updateXYSeries("Variable R", null, data.getR(), null);
        xyChart.updateXYSeries("Variable D", null, data.getD(), null);
    }

    public JPanel getControlPanel() {
        return controlPanel;
    }
}
