package GUI.Component;

import GUI.BinPackingGUI;
import Model.DataSet;
import Util.DataSetLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private JComboBox<String> dataSetComboBox;
    private JComboBox<String> metaheuristicComboBox;
    private BinPackingGUI parent;
    private ItemPanel itemPanel;

    public ControlPanel(BinPackingGUI parent, ItemPanel itemPanel) {
        this.parent = parent;
        this.itemPanel = itemPanel;

        setBackground(new Color(63, 81, 181));
        setLayout(new FlowLayout());

        dataSetComboBox = new JComboBox<>(new String[]{"binpacking2d-01", "binpacking2d-02", "binpacking2d-03"});
        metaheuristicComboBox = new JComboBox<>(new String[]{"FFF", "GRASP", "Tabu Search"});
        JButton solveButton = new JButton("Solve");
        solveButton.setBackground(new Color(0, 150, 136));
        solveButton.setForeground(Color.WHITE);

        add(new JLabel("Select Model.DataSet:"));
        add(dataSetComboBox);
        add(new JLabel("Select Algorithms.Metaheuristic:"));
        add(metaheuristicComboBox);
        add(solveButton);

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dataSetName = (String) dataSetComboBox.getSelectedItem();
                String metaheuristicName = (String) metaheuristicComboBox.getSelectedItem();
                itemPanel.clearItems();
                parent.solveBinPacking2D(dataSetName, metaheuristicName);
            }
        });
        dataSetComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dataSetName = (String) dataSetComboBox.getSelectedItem();
                String dataSetPath = "C:/Polytech_ingenieur/OptDiscrete/" + dataSetName + ".bp2d";
                DataSet dataSet = DataSetLoader.loadDataSet(dataSetPath);
                itemPanel.setItems(dataSet.getItems());
                //binPanel.clearBins
                itemPanel.repaint();
            }
        });
    }
}
