package GUI.Component;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private JComboBox<String> dataSetComboBox;
    private JComboBox<String> metaheuristicComboBox;
    private JButton solveButton;

    public ControlPanel() {
        setBackground(new Color(63, 81, 181));
        setLayout(new FlowLayout());

        dataSetComboBox = new JComboBox<>(new String[]{
                "binpacking2d-01", "binpacking2d-02", "binpacking2d-03",
                "binpacking2d-04", "binpacking2d-05", "binpacking2d-06",
                "binpacking2d-07", "binpacking2d-08", "binpacking2d-09",
                "binpacking2d-10", "binpacking2d-11", "binpacking2d-12",
                "binpacking2d-13"
        });
        add(new JLabel("Dataset:"));
        add(dataSetComboBox);

        metaheuristicComboBox = new JComboBox<>(new String[]{
                "Tabu search",
                "Genetic algorithm",
                "GRASP",
                "FFF",
        });
        add(new JLabel("Metaheuristic:"));
        add(metaheuristicComboBox);

        solveButton = new JButton("Solve");
        solveButton.setBackground(new Color(0, 150, 136));
        solveButton.setForeground(Color.WHITE);
        add(solveButton);
    }

    public JComboBox<String> getDataSetComboBox() {
        return dataSetComboBox;
    }

    public JComboBox<String> getMetaheuristicComboBox() { return metaheuristicComboBox; }

    public JButton getSolveButton() { return solveButton; }
}
