package GUI;

import Algorithms.Metaheuristic.Metaheuristic;
import Algorithms.Solver;
import GUI.Component.BinPanel;
import GUI.Component.ControlPanel;
import GUI.Component.ItemPanel;
import Model.DataSet;
import Util.DataSetLoader;

import javax.swing.*;
import java.awt.*;

public class BinPackingGUI extends JFrame {
    public ItemPanel itemPanel;
    private ControlPanel controlPanel;
    private BinPanel binPanel;
    private Solver solver;
    private DataSet dataSet;
    private Metaheuristic metaheuristic;

    public BinPackingGUI() {
        setTitle("Bin Packing 2D Solver");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);
        
        binPanel = new BinPanel(null);
        controlPanel = new ControlPanel();
        solver = new Solver(binPanel);

        this.dataSet = DataSetLoader.loadDataSet("src/data/" + controlPanel.getDataSetComboBox().getSelectedItem() + ".bp2d");
        itemPanel = new ItemPanel(dataSet.getItems(), dataSet.getMaxWidth(), dataSet.getMaxHeight());

        add(controlPanel, BorderLayout.NORTH);
        add(itemPanel, BorderLayout.CENTER);

        controlPanel.getDataSetComboBox().addActionListener(e -> {
            this.dataSet = DataSetLoader.loadDataSet("src/data/" + controlPanel.getDataSetComboBox().getSelectedItem() + ".bp2d");
            // Print dataset information
            System.out.println("Loaded dataset: " + dataSet.getName());
            System.out.println("Comment: " + dataSet.getComment());
            System.out.println("Number of items: " + dataSet.getNbItems());
            System.out.println("Bin width: " + dataSet.getBinWidth());
            System.out.println("Bin height: " + dataSet.getBinHeight());
            itemPanel.setItems(dataSet.getItems());
            remove(binPanel);
            itemPanel.setVisible(true);
            revalidate();
            repaint();
        });

        controlPanel.getSolveButton().addActionListener(e -> {
            binPanel.clearBins();
            solver.setBinPanel(binPanel);
            this.metaheuristic = solver.getMetaheuristic(controlPanel.getMetaheuristicComboBox().getSelectedItem().toString());
            this.solveBinPacking2D(dataSet, metaheuristic);
        });
    }

    public void solveBinPacking2D(DataSet dataSet, Metaheuristic metaheuristic) {
        solver.solve(dataSet, metaheuristic);
        itemPanel.setVisible(false);
        add(binPanel);
        revalidate();
        repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BinPackingGUI().setVisible(true);
            }
        });
    }
}
