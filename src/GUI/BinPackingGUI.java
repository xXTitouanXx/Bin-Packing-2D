package GUI;

import Algorithms.Heuristic.Heuristic;
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
    private Heuristic heuristic;
    private boolean isSolving;

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
        itemPanel = new ItemPanel(dataSet.getItems());

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

        controlPanel.getSolveMetaheuristicButton().addActionListener(e -> {
            controlPanel.disableButtons();
            binPanel.clearBins();
            solver.setBinPanel(binPanel);
            this.metaheuristic = solver.getMetaheuristic(controlPanel.getMetaheuristicComboBox().getSelectedItem().toString());
            this.solveBinPacking2DWithMetaheuristic(dataSet, metaheuristic);
        });
        controlPanel.getSolveHeuristicButton().addActionListener(e -> {
            controlPanel.disableButtons();
            binPanel.clearBins();
            solver.setBinPanel(binPanel);
            this.heuristic = solver.getHeuristic(controlPanel.getHeuristicComboBox().getSelectedItem().toString());
            this.solveBinPacking2DWithHeuristic(dataSet, heuristic);
        });
    }

    public void solveBinPacking2DWithMetaheuristic(DataSet dataSet, Metaheuristic metaheuristic) {
        new Thread(() -> {
            solver.solveMetaheuristic(dataSet, metaheuristic, controlPanel);
            SwingUtilities.invokeLater(() -> {
                itemPanel.setVisible(false);
                add(binPanel);
                revalidate();
                repaint();
                controlPanel.enableButtons();
            });
        }).start();
    }

    public void solveBinPacking2DWithHeuristic(DataSet dataSet, Heuristic heuristic) {
        new Thread(() -> {
            solver.solveHeuristic(dataSet, heuristic, controlPanel);
            SwingUtilities.invokeLater(() -> {
                itemPanel.setVisible(false);
                add(binPanel);
                revalidate();
                repaint();
                controlPanel.enableButtons();
            });
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BinPackingGUI().setVisible(true);
            }
        });
    }
}
