package GUI;

import Algorithms.Solver;
import GUI.Component.BinPanel;
import GUI.Component.ControlPanel;
import GUI.Component.ItemPanel;
import Model.DataSet;
import Util.DataSetLoader;

import javax.swing.*;
import java.awt.*;

public class BinPackingGUI extends JFrame {
    private JComboBox<String> dataSetComboBox;
    private JComboBox<String> metaheuristicComboBox;
    private BinPanel binPanel;
    private Solver solver;

    public BinPackingGUI() {
        setTitle("Model.Bin Packing 2D Algorithms.Solver");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);

        DataSet dataSet = DataSetLoader.loadDataSet("C:/Polytech_ingenieur/OptDiscrete/binpacking2d-01.bp2d");
        ItemPanel itemPanel = new ItemPanel(dataSet.getItems());
        ControlPanel controlPanel = new ControlPanel(this, itemPanel);

        BinPanel binPanel = new BinPanel(null);
        solver = new Solver(binPanel);

//        draw = new Draw();
//        solver = new Algorithms.Solver(draw);

        add(controlPanel, BorderLayout.NORTH);
        // add(binPanel, BorderLayout.CENTER);
        add(itemPanel, BorderLayout.CENTER);
    }

    public void solveBinPacking2D(String dataSetName, String metaheuristicName) {
        solver.solve(dataSetName, metaheuristicName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BinPackingGUI().setVisible(true);
            }
        });
    }
}
