package GUI.Component;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private final JComboBox<String> dataSetComboBox;
    private final JComboBox<String> metaheuristicComboBox;
    private final JComboBox<String> heuristicComboBox;
    private final JButton solveMetaheuristicButton;
    private final JButton solveHeuristicButton;
    private final Color enabledColor = new Color(0, 150, 136);
    private final Color disabledColor = Color.GRAY;

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
        });
        add(new JLabel("Metaheuristic:"));
        add(metaheuristicComboBox);

        solveMetaheuristicButton = new JButton("Solve metaheuristic");
        solveMetaheuristicButton.setBackground(enabledColor);
        solveMetaheuristicButton.setForeground(Color.WHITE);
        add(solveMetaheuristicButton);

        heuristicComboBox = new JComboBox<>(new String[]{
                "Finite First Fit",
                "First Fit Decreasing Height",
                "Next Fit Decreasing Height",
        });
        add(new JLabel("Heuristic:"));
        add(heuristicComboBox);

        solveHeuristicButton = new JButton("Solve heuristic");
        solveHeuristicButton.setBackground(enabledColor);
        solveHeuristicButton.setForeground(Color.WHITE);
        add(solveHeuristicButton);
    }

    public JComboBox<String> getDataSetComboBox() {
        return dataSetComboBox;
    }

    public JComboBox<String> getMetaheuristicComboBox() {
        return metaheuristicComboBox;
    }

    public JComboBox<String> getHeuristicComboBox() {
        return heuristicComboBox;
    }

    public JButton getSolveMetaheuristicButton() {
        return solveMetaheuristicButton;
    }

    public JButton getSolveHeuristicButton() {
        return solveHeuristicButton;
    }

    public void disableButtons() {
        solveMetaheuristicButton.setEnabled(false);
        solveMetaheuristicButton.setBackground(disabledColor);
        solveHeuristicButton.setEnabled(false);
        solveHeuristicButton.setBackground(disabledColor);
    }

    public void enableButtons() {
        solveMetaheuristicButton.setEnabled(true);
        solveMetaheuristicButton.setBackground(enabledColor);
        solveHeuristicButton.setEnabled(true);
        solveHeuristicButton.setBackground(enabledColor);
    }
}
