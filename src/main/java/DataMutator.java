import model.ModelBuilder;
import model.PointsContainer;
import org.knowm.xchart.XYChart;

import javax.swing.*;

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
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        SpinnerNumberModel nModel = new SpinnerNumberModel(Long.valueOf(70_000L), Long.valueOf(1), Long.valueOf(18_000_000_000L), Long.valueOf(100));

        JSpinner spinN = new JSpinner(nModel);
        spinN.addChangeListener(e -> {
            modelBuilder.setPeopleAmount((Long) spinN.getValue());
        });
        //hack
        spinN.setMaximumSize(spinN.getPreferredSize());
        controlPanel.add(new JLabel("Население:"));
        controlPanel.add(spinN);

        String cont = "a 0 0.00001\nb 0 0.1\nm 0 0.01";
        // hack
        modelBuilder.parse(cont);
        JTextArea textArea = new JTextArea(cont);
        JScrollPane jsp = new JScrollPane(textArea);
        controlPanel.add(new JLabel("Коэффиценты"));
        controlPanel.add(jsp);

        JLabel result = new JLabel("");
        JButton btn = new JButton("Parse!");
        btn.addActionListener(e -> {
            String value = textArea.getText();
            boolean res = modelBuilder.parse(value);
            if (res) {
                result.setText("Success!");
                //TODO smart redraw
            } else {
                result.setText("Invalid input!");
            }
        });
        controlPanel.add(btn);
        controlPanel.add(result);
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
