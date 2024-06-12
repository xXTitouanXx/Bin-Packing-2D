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

        dataSetComboBox = new JComboBox<>(new String[]{
                "binpacking2d-01", "binpacking2d-02", "binpacking2d-03",
                "binpacking2d-04", "binpacking2d-05", "binpacking2d-06",
                "binpacking2d-07", "binpacking2d-08", "binpacking2d-09",
                "binpacking2d-10", "binpacking2d-11", "binpacking2d-12",
                "binpacking2d-13"
        });
        metaheuristicComboBox = new JComboBox<>(new String[]{"Tabu search", "GRASP", "FFF", "Tabu Search"});
        JButton solveButton = new JButton("Solve");
        solveButton.setBackground(new Color(0, 150, 136));
        solveButton.setForeground(Color.WHITE);
        add(new JLabel("Select Model.DataSet:"));
        add(dataSetComboBox);
        add(new JLabel("Select Algorithms.Metaheuristic.Metaheuristic:"));
        add(metaheuristicComboBox);
        add(solveButton);

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dataSetName = (String) dataSetComboBox.getSelectedItem();
                String metaheuristicName = (String) metaheuristicComboBox.getSelectedItem();
                itemPanel.clearItems();
                revalidate();
                repaint();
                parent.solveBinPacking2D(dataSetName, metaheuristicName);
            }
        });
        dataSetComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dataSetName = (String) dataSetComboBox.getSelectedItem();
//                String dataSetPath = "C:/Polytech_ingenieur/OptDiscrete/" + dataSetName + ".bp2d";
                String dataSetPath = "E:/Polytech/4A/OptDiscrete/" + dataSetName + ".bp2d";
                DataSet dataSet = DataSetLoader.loadDataSet(dataSetPath);
                itemPanel.setItems(dataSet.getItems());
                itemPanel.repaint();
            }
        });
    }

    public JComboBox<String> getDataSetComboBox() {
        return dataSetComboBox;
    }

    public void setDataSetComboBox(JComboBox<String> dataSetComboBox) {
        this.dataSetComboBox = dataSetComboBox;
    }
}
