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
        SpinnerNumberModel iModel = new SpinnerNumberModel(Long.valueOf(1), Long.valueOf(1), Long.valueOf(70_000L), Long.valueOf(100));

        JSpinner spinN = new JSpinner(nModel);
        ((JSpinner.NumberEditor) spinN.getEditor()).getTextField().setColumns(10);
        spinN.addChangeListener(e -> {
            modelBuilder.setN((Long) spinN.getValue());
            iModel.setMaximum((Long) spinN.getValue());
        });
        controlPanel.add(spinN, constraints);

        JSpinner spinInfected = new JSpinner(iModel);
        ((JSpinner.NumberEditor) spinInfected.getEditor()).getTextField().setColumns(10);
        spinInfected.addChangeListener(e -> {
            modelBuilder.setI((Long) spinInfected.getValue());
            nModel.setMinimum((Long) spinInfected.getValue());
        });
        controlPanel.add(spinInfected, constraints);

        JSpinner spinAlpha = new JSpinner(new SpinnerNumberModel(0.0005, 0, 100, 0.00005f));
        spinAlpha.setEditor(new JSpinner.NumberEditor(spinAlpha, "0.00000"));
        ((JSpinner.NumberEditor) spinAlpha.getEditor()).getTextField().setColumns(10);
        spinAlpha.addChangeListener(e -> modelBuilder.setAlpha((Double) spinAlpha.getValue()));
        controlPanel.add(spinAlpha, constraints);

        JSpinner spinBeta = new JSpinner(new SpinnerNumberModel(1, 0, 100, 0.00005f));
        spinBeta.setEditor(new JSpinner.NumberEditor(spinBeta, "0.00000"));
        ((JSpinner.NumberEditor) spinBeta.getEditor()).getTextField().setColumns(10);
        spinBeta.addChangeListener(e -> modelBuilder.setBeta((Double) spinBeta.getValue()));
        controlPanel.add(spinBeta, constraints);

        JSpinner spinMu = new JSpinner(new SpinnerNumberModel(0.5, 0, 100, 0.00005f));
        spinMu.setEditor(new JSpinner.NumberEditor(spinMu, "0.00000"));
        ((JSpinner.NumberEditor) spinMu.getEditor()).getTextField().setColumns(10);
        spinMu.addChangeListener(e -> modelBuilder.setMu((Double) spinMu.getValue()));
        controlPanel.add(spinMu, constraints);
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
